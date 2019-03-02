
package com.aspire.ponaadmin.web.repository.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * <p>编辑内容标签的Action</p>
 * <p>实现内容标签的增加,删除</p>
 * <p>Copyright (c) 2007-2008 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author x_jimin
 * @version 1.1.0.0
 * @since 1.1.0.0
 */
public class ContentTagAction extends BaseAction
{
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentTagAction.class) ;

   //内容标签的分隔符
    private static final String TAG_SPLIT = ";";
    //内容标签的最多个数
    private static final int MAX_TAG_NUMBER = 50;
    //内容标签的最大长度
    private static final int MAX_TAG_LENGTH = 1500;

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        logger.debug("doPerform()");

        String actionType = "";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        String forward = Constants.FORWARD_COMMON_SUCCESS;

        String opt = this.getParameter(request, "opt");
        String tagValue = this.getParameter(request, "tagValue");
        String action = this.getParameter(request, "action");
        String categoryID = this.getParameter(request, "categoryID");

        if (logger.isDebugEnabled())
        {
            logger.debug("opt:" + opt);
            logger.debug("tagValue:" + tagValue);
            logger.debug("action:" + action);
            logger.debug("categoryID:" + categoryID);
        }

        List nodes = null;
        // 判断是否为全选
        if ("all".equals(opt))
        {
            nodes = processSelectAll(request);
        }
        else
        {
            nodes = processSelect(request);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("nodes.size():" + nodes.size());
        }

        tagValue = tagValue.trim();
        String tempValue = "{" + tagValue + "}";
        int count_fail = 0;
        StringBuffer message = new StringBuffer();
        StringBuffer fail_nodes = new StringBuffer();
        
        
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        String updateTime = sdf.format(new Date());
        
        if ("add".equals(action))
        {
            // 增加
            actionType = "新增标签";
            actionTarget = tagValue;

            GContent node = null;
            String keywords = null;
            boolean flag = false;
            
            for (int i = 0; i < nodes.size(); i++)
            {
                node = ( GContent ) nodes.get(i);
                node.setLupdDate(updateTime);
                keywords = node.getKeywords();
                flag = false;

                if (keywords == null || "".equals(keywords))
                {
                    keywords = tempValue;
                    flag = true;
                }
                else if (keywords.indexOf(tempValue) == -1)
                {
                    // 假如要增加的关键字存在,则不作操作;否则,增加关键字
                    keywords = tempValue + TAG_SPLIT + keywords;
                    flag= true;
                }
                
                //如果有修改,才需要检查和保存
                if(flag)
                {
                    String[] s = keywords.split(TAG_SPLIT);
                    if (s.length > MAX_TAG_NUMBER
                        || keywords.length() > MAX_TAG_LENGTH)
                    {
                        // 超范围了
                        if (count_fail != 0)
                        {
                            fail_nodes.append(",");
                        }
                        fail_nodes.append(node.getId());
                        count_fail++;
                    }
                    else
                    {
                        node.setKeywords(keywords);
                        node.setLupdDate(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss"));
                        node.save();
                    }
                }
            }
            
            if (count_fail == 0)
            {
                message.append("本次新增成功").append(nodes.size()).append("条!");
            }
            else
            {
                message.append("本次新增成功")
                       .append(nodes.size() - count_fail)
                       .append("条，");
                message.append("失败")
                       .append(count_fail)
                       .append("条，失败的内容ID为\"")
                       .append(fail_nodes)
                       .append("\"!");
            }
        }
        else if ("del".equals(action))
        {
            // 删除
            actionType = "删除标签";
            actionTarget = tagValue;
            int delSize = 0;
            for (int i = 0; i < nodes.size(); i++)
            {
                GContent node = ( GContent ) nodes.get(i);
                String keywords = node.getKeywords();
                
                keywords = keywords == null ? "" : keywords;
                
                // 假如要删除的关键字在第一个,则分号在后面,否则分号在前面
                int index = keywords.indexOf(tempValue);
                logger.debug("del tag 's index:" + index);

                if (tempValue.equals(keywords))
                {
                    keywords = "";
                }
                else if (index == 0)
                {
                    keywords = StringUtils.replace(keywords, tempValue
                                                             + TAG_SPLIT, "");
                }
                else
                {
                    keywords = StringUtils.replace(keywords, TAG_SPLIT
                                                             + tempValue, "");
                }

                if (index != -1)
                {
                    node.setKeywords(keywords);
                    node.setLupdDate(PublicUtil.getCurDateTime("yyyy-MM-dd HH:mm:ss"));
                    node.save();
                    delSize++;
                }
            }
            message.append("本次删除成功").append(delSize).append("条!");
        }

        // 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
        this.saveMessagesValue(request, message.toString());
        request.setAttribute(Constants.PARA_GOURL,getPara(request));

        return mapping.findForward(forward);
    }

    /**
     * 当用户没有选择全选的情况,根据contentID,取得用户选择的内容列表
     * 
     * @param request
     * @return
     * @throws BOException
     */
    private List processSelect(HttpServletRequest request) throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("processSelect()");
        }
        String[] dealContents = request.getParameterValues("dealContent");
        List list = new ArrayList();
        for (int i = 0; i < dealContents.length; i++)
        {
            String contentID = dealContents[i];
            GContent content = ( GContent ) Repository.getInstance()
                                                      .getNode(contentID,
                                                               GContent.TYPE_CONTENT);
            list.add(content);
        }
        return list;
    }

    /**
     * 当用户选择全选时,根据用户的查询条件,取得用户选择的内容列表
     * 
     * @param request
     * @return
     * @throws BOException
     */
    private List processSelectAll(HttpServletRequest request)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("processSelectAll()");
        }
        String categoryID = this.getParameter(request, "categoryID");
        String cate = categoryID;
        if(categoryID.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            categoryID = RepositoryConstants.ROOT_CATEGORY_ID;
        }
        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");
        String cateName = this.getParameter(request, "cateName");
        String icpCode = this.getParameter(request, "icpCode");
        String icpServId = this.getParameter(request, "icpServId");
        String type = this.getParameter(request, "type");
        String contentID = this.getParameter(request, "contentID");
        String isRecursiveStr = this.getParameter(request, "isRecursive");
        boolean isRecursive = Boolean.valueOf(isRecursiveStr).booleanValue();
        String contentTag = this.getParameter(request, "contentTag");
        String tagLogic = this.getParameter(request, "tagLogic");

        if (logger.isDebugEnabled())
        {
            logger.debug("name:" + name);
            logger.debug("spName:" + spName);
            logger.debug("cateName:" + cateName);
            logger.debug("icpCode:" + icpCode);
            logger.debug("icpServId:" + icpServId);
            logger.debug("type:" + type);
            logger.debug("contentID:" + contentID);
            logger.debug("isRecursive:" + isRecursive);
            logger.debug("contentTag:" + contentTag);
            logger.debug("tagLogic:" + tagLogic);
        }
        // 获取分类信息
        Category category = ( Category ) Repository.getInstance()
                                                   .getNode(categoryID,
                                                            RepositoryConstants.TYPE_CATEGORY);

        if (logger.isDebugEnabled())
        {
            logger.debug("内容名称:" + category.getName());
        }

        if (categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            category.setName("未分类资源");
        }

        // 设置搜索条件：
        Searchor searchor = new Searchor();
        if (!name.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("name",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + name + '%'));
        }
        if (!spName.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("spName",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + spName + '%'));
        }
        if (!cateName.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("cateName",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + cateName + '%'));
        }
        if (!type.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("type",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + type + '%'));
        }
        if (!icpCode.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("icpCode",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + icpCode + '%'));
        }
        if (!icpServId.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("icpServId",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + icpServId + '%'));
        }

        if (!contentTag.equals(""))
        {
            StringTokenizer st = new StringTokenizer(contentTag, " ");
            int count = st.countTokens();
            for (int i = 0; st.hasMoreTokens(); i++)
            {
                String keyword = st.nextToken();
                SearchParam param = new SearchParam("keywords",
                                                    RepositoryConstants.OP_LIKE,
                                                    "%{" + keyword + "}%");
                // 如果不是第一个，就是or类型的
                if (i > 0)
                {
                    if ("OR".equals(tagLogic))
                    {
                        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
                    }
                    else
                    {
                        param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_AND);
                    }

                }
                // 如果有多个条件，要组合括号
                if (count > 1)
                {
                    // 第一个要加一个左括号
                    if (i == 0)
                    {
                        param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
                    }
                    // 最后一个要加一个右括号
                    if (i == count - 1)
                    {
                        param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
                    }
                }
                searchor.getParams().add(param);
            }
        }

        if (!contentID.equals(""))
        {
            searchor.getParams()
                    .add(new SearchParam("id",
                                         RepositoryConstants.OP_LIKE,
                                         '%' + contentID + '%'));
        }

        searchor.setIsRecursive(isRecursive);

        // 查询资源内容
        if (cate.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            // 查询未分类资源：不在根分类下的
            searchor.setIsNotIn(true);
        }
        else
        {
            // 在本分类下的
            searchor.setIsNotIn(false);
        }

        // 排序方式:根据1.1.1.044需求（按接入时间降序排序） guanzf 20071108
        Taxis taxis = new Taxis();
        taxis.getParams()
             .add(new TaxisParam("createDate",
                                 RepositoryConstants.ORDER_TYPE_DESC));

        List list = category.searchRefNodes(RepositoryConstants.TYPE_REFERENCE,
                                            searchor,
                                            taxis);
        return list;
    }
    
    /**
     * 根据后来需求,需要将查询参数带回
     * @param request
     */
    private String getPara(HttpServletRequest request)
    {
        
        String categoryID = this.getParameter(request, "categoryID");
        String name = this.getParameter(request, "name");
        String spName = this.getParameter(request, "spName");
        String cateName = this.getParameter(request, "cateName");
        String icpCode = this.getParameter(request, "icpCode");
        String icpServId = this.getParameter(request, "icpServId");
        String type = this.getParameter(request, "type");
        String contentID = this.getParameter(request, "contentID");
        String isRecursiveStr = this.getParameter(request, "isRecursive");
        boolean isRecursive = Boolean.valueOf(isRecursiveStr).booleanValue();
        String contentTag = this.getParameter(request, "contentTag");
        String tagLogic = this.getParameter(request, "tagLogic");
        
        String currPageNo = this.getParameter(request, "CurrPageNo");
        
        StringBuffer sb = new StringBuffer();
        sb.append("../../web/resourcemgr/contentList.do?categoryID=").append(categoryID);
        sb.append("&name=").append(name);
        sb.append("&spName=").append(spName);
        sb.append("&cateName=").append(cateName);
        sb.append("&icpCode=").append(icpCode);
        sb.append("&icpServId=").append(icpServId);
        sb.append("&type=").append(type);
        sb.append("&contentID=").append(contentID);
        sb.append("&isRecursive=").append(isRecursive);
        sb.append("&contentTag=").append(contentTag);
        sb.append("&tagLogic=").append(tagLogic);
        sb.append("&CurrPageNo=").append(currPageNo);
        
        return sb.toString(); 
    }
    
}
