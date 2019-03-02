/**
 * �ļ�����NewMusicCategoryAction.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.basevideosync.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.dao.CategoryDAO;
import com.aspire.dotcard.basevideosync.vo.POMSCategoryQueryVO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author duyongchun
 * @version
 */
public class NewVideoPOMSCategoryAction extends BaseAction
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(NewVideoPOMSCategoryAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.BaseAction#doPerform(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {
        LOG.debug("doPerform()");
        // �������л�ȡ��������
        String perType = this.getParameter(request, "Type").trim();
        if ("query".equals(perType))
        {
        	
//        	System.err.println("*****"+this.getParameter(request, "categoryId").trim());
            return query(mapping, form, request, response);
        }
   
        else 
        {
            String forward = Constants.FORWARD_COMMON_FAILURE;
            this.saveMessagesValue(request, "�Բ��������ʵ�·��������");
            return mapping.findForward(forward);
        }
    }
   
 
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        LOG.debug("doPerform()");
        String categoryId=this.getParameter(request, "categoryId").trim();
        Object[]obj={categoryId};
        POMSCategoryQueryVO pcq=CategoryDAO.getInstance().query(obj);
//        System.err.println("+++"+pcq.getPath());
        request.setAttribute("POMSCategoryQueryVO", pcq);
        String forward = "query";
        return mapping.findForward(forward);
    }

}