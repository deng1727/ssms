
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
 * <p>�༭���ݱ�ǩ��Action</p>
 * <p>ʵ�����ݱ�ǩ������,ɾ��</p>
 * <p>Copyright (c) 2007-2008 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author x_jimin
 * @version 1.1.0.0
 * @since 1.1.0.0
 */
public class ContentTagAction extends BaseAction
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentTagAction.class) ;

   //���ݱ�ǩ�ķָ���
    private static final String TAG_SPLIT = ";";
    //���ݱ�ǩ��������
    private static final int MAX_TAG_NUMBER = 50;
    //���ݱ�ǩ����󳤶�
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
        // �ж��Ƿ�Ϊȫѡ
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
            // ����
            actionType = "������ǩ";
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
                    // ����Ҫ���ӵĹؼ��ִ���,��������;����,���ӹؼ���
                    keywords = tempValue + TAG_SPLIT + keywords;
                    flag= true;
                }
                
                //������޸�,����Ҫ���ͱ���
                if(flag)
                {
                    String[] s = keywords.split(TAG_SPLIT);
                    if (s.length > MAX_TAG_NUMBER
                        || keywords.length() > MAX_TAG_LENGTH)
                    {
                        // ����Χ��
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
                message.append("���������ɹ�").append(nodes.size()).append("��!");
            }
            else
            {
                message.append("���������ɹ�")
                       .append(nodes.size() - count_fail)
                       .append("����");
                message.append("ʧ��")
                       .append(count_fail)
                       .append("����ʧ�ܵ�����IDΪ\"")
                       .append(fail_nodes)
                       .append("\"!");
            }
        }
        else if ("del".equals(action))
        {
            // ɾ��
            actionType = "ɾ����ǩ";
            actionTarget = tagValue;
            int delSize = 0;
            for (int i = 0; i < nodes.size(); i++)
            {
                GContent node = ( GContent ) nodes.get(i);
                String keywords = node.getKeywords();
                
                keywords = keywords == null ? "" : keywords;
                
                // ����Ҫɾ���Ĺؼ����ڵ�һ��,��ֺ��ں���,����ֺ���ǰ��
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
            message.append("����ɾ���ɹ�").append(delSize).append("��!");
        }

        // д������־
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
     * ���û�û��ѡ��ȫѡ�����,����contentID,ȡ���û�ѡ��������б�
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
     * ���û�ѡ��ȫѡʱ,�����û��Ĳ�ѯ����,ȡ���û�ѡ��������б�
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
        // ��ȡ������Ϣ
        Category category = ( Category ) Repository.getInstance()
                                                   .getNode(categoryID,
                                                            RepositoryConstants.TYPE_CATEGORY);

        if (logger.isDebugEnabled())
        {
            logger.debug("��������:" + category.getName());
        }

        if (categoryID.equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            category.setName("δ������Դ");
        }

        // ��������������
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
                // ������ǵ�һ��������or���͵�
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
                // ����ж��������Ҫ�������
                if (count > 1)
                {
                    // ��һ��Ҫ��һ��������
                    if (i == 0)
                    {
                        param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
                    }
                    // ���һ��Ҫ��һ��������
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

        // ��ѯ��Դ����
        if (cate.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            // ��ѯδ������Դ�����ڸ������µ�
            searchor.setIsNotIn(true);
        }
        else
        {
            // �ڱ������µ�
            searchor.setIsNotIn(false);
        }

        // ����ʽ:����1.1.1.044���󣨰�����ʱ�併������ guanzf 20071108
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
     * ���ݺ�������,��Ҫ����ѯ��������
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
