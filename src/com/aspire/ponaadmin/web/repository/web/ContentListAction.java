package com.aspire.ponaadmin.web.repository.web ;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.Taxis;
import com.aspire.ponaadmin.web.repository.TaxisParam;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * <p>��ѯ�����������б��Action</p>
 * <p>categoryIDΪ�ձ�ʾ��ѯδ����ģ�δ����ľ��Ǹ������</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class ContentListAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(ContentListAction.class) ;

    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws BOException
     * @todo Implement this com.aspire.ponaadmin.web.BaseAction method
     */
    public ActionForward doPerform (ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request,
                                    HttpServletResponse response)
        throws BOException
    {
        if (LOG.isDebugEnabled())
        {
        LOG.debug("doPerform()");
        }

        String categoryID = this.getParameter(request, "categoryID");
        String cate = categoryID;
        if(categoryID.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            categoryID = RepositoryConstants.ROOT_CATEGORY_ID;
        }
        String name = this.getParameter(request, "name");
        String author = this.getParameter(request, "author");
        String source = this.getParameter(request, "source");
        String keywords = this.getParameter(request, "keywords");
        String isRecursiveStr = this.getParameter(request, "isRecursive");
        boolean isRecursive = true;
        String isModelWindow = this.getParameter(request, "isModelWindow");

        String spName = this.getParameter(request, "spName");
        String cateName = this.getParameter(request, "cateName");
        String type = this.getParameter(request, "type");
        String icpCode = this.getParameter(request, "icpCode"); 
        String icpServId = this.getParameter(request, "icpServId");
        String contentID = this.getParameter(request, "contentID");
        
        String contentTag = this.getParameter(request, "contentTag");
        String tagLogic = this.getParameter(request, "tagLogic");
        String pageSize=this.getParameter(request, "pageSize");
        
        String servAttr = this.getParameter(request, "servAttr").trim();
        if("".equals(pageSize.trim()))
        {
        	pageSize=PageSizeConstants.page_DEFAULT;
        }
        
        if(isRecursiveStr.equals("false"))
        {
            isRecursive = false;
        }

        //��ȡ������Ϣ
        Category category = (Category) Repository.getInstance().getNode(categoryID,
                RepositoryConstants.TYPE_CATEGORY) ;
        
        String forward = Constants.FORWARD_COMMON_FAILURE;
        if (LOG.isDebugEnabled())
        {
            LOG.debug("��������:" + category.getName());
        }
       
        if (cate.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            category.setName("δ������Դ");
        }

        //��������������
        Searchor searchor = new Searchor();
        if(!name.equals(""))
        {
            searchor.getParams().add(new SearchParam("name",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + name + '%')) ;
        }
        if(!spName.equals(""))
        {
            searchor.getParams().add(new SearchParam("spName",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + spName + '%')) ;
        }
        if(!cateName.equals(""))
        {
            searchor.getParams().add(new SearchParam("cateName",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + cateName + '%')) ;
        }   
        if(!type.equals(""))
        {
            searchor.getParams().add(new SearchParam("type",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + type + '%')) ;
        } 
        if(!icpCode.equals(""))
        {
            searchor.getParams().add(new SearchParam("icpCode",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + icpCode + '%')) ;
        }    
        if(!icpServId.equals(""))
        {
            searchor.getParams().add(new SearchParam("icpServId",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + icpServId + '%')) ;
        }
        if(!author.equals(""))
        {
            searchor.getParams().add(new SearchParam("author",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + author + '%')) ;
        }
        if(!source.equals(""))
        {
            searchor.getParams().add(new SearchParam("source",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + source + '%')) ;
        }
        if(!keywords.equals(""))
        {
            StringTokenizer st = new StringTokenizer(keywords, ",");
            int count = st.countTokens();
            for (int i = 0 ; st.hasMoreTokens() ; i++)
            {
                String keyword = st.nextToken();
                SearchParam param = new SearchParam("keywords", RepositoryConstants.
                                                     OP_LIKE, '%' + keyword + '%');
                //������ǵ�һ��������or���͵�
                if(i > 0)
                {
                    param.setMode(RepositoryConstants.SEARCH_PARAM_MODE_OR);
                }
                //����ж��������Ҫ�������
                if(count > 1)
                {
                    //��һ��Ҫ��һ��������
                    if(i == 0)
                    {
                        param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_LEFT);
                    }
                    //���һ��Ҫ��һ��������
                    if(i == count - 1)
                    {
                        param.setBracket(RepositoryConstants.SEARCH_PARAM_BRACKET_RIGHT);
                    }
                }
                searchor.getParams().add(param);
            }
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
        if(!contentID.equals(""))
        {
            searchor.getParams().add(new SearchParam("id",
                                                     RepositoryConstants.
                                                     OP_LIKE, '%' + contentID + '%')) ;
        }        
//      add by tungke 
        if(!servAttr.equals(""))
        {
            searchor.getParams().add(new SearchParam("servAttr",
                                                     RepositoryConstants.
                                                     OP_EQUAL, servAttr)) ;
        }
        searchor.setIsRecursive(isRecursive);

        //��ȡ�����µ�����
        PageResult page = new PageResult(request);
       // page.setPageSize(6+6);
        page.setPageSize(Integer.parseInt(pageSize));
        
        //���µ�ǰҳ��
        String currPageNo = this.getParameter(request, "CurrPageNo");
        if(!"".equals(currPageNo))
            page.setCurrentPageNo(Integer.parseInt(currPageNo));

        //��ѯ��������
        if (cate.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            //��ѯδ������ܣ����ڸ������µ�
            searchor.setIsNotIn(true);
        }
        else
        {
            //�ڱ������µ�
            searchor.setIsNotIn(false);
        }       
        //����ʽ:����1.1.1.044���󣨰�����ʱ�併�����򣩡�guanzf 20071108
        Taxis taxis = new Taxis();
        taxis.getParams().add(new TaxisParam("createDate", RepositoryConstants.ORDER_TYPE_DESC));

        category.searchRefNodes(page, RepositoryConstants.TYPE_REFERENCE,
                             searchor, taxis) ;

        //�����ѯδ����Դ,�򽫷�����Ϊδ������Դ
        if(cate.equals(RepositoryConstants.ROOT_CONTENT_ID))
        {
            category.setId(RepositoryConstants.ROOT_CONTENT_ID);
            category.setNamePath("δ������Դ");
        }
        
        request.setAttribute("PageResult", page);
        request.setAttribute("category", category);
        request.setAttribute("pageSize", pageSize);

        forward = "showList";
         
        if(isModelWindow.equals("true"))
        {
            forward = "modelWindow";
        }
        return mapping.findForward(forward);
    }
}
