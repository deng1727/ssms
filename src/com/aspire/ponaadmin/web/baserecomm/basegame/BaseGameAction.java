/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basegame;


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
import com.aspire.ponaadmin.web.baserecomm.BaseRecommBO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.system.PageSizeConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseGameAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseGameAction.class);

    private String DATATYPE = "baseGame";

    public ActionForward doPerform(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request,
                                   HttpServletResponse response)
                    throws BOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        // �������л�ȡ��������
        String action = this.getParameter(request, "actionType").trim();

        if ("add".equals(action))
        {
            return add(mapping, form, request, response);
        }
        if ("del".equals(action))
        {
            return del(mapping, form, request, response);
        }
        if ("toFile".equals(action))
        {
            return toFile(mapping, form, request, response);
        }
        else
        {
            return query(mapping, form, request, response);
        }

    }

    /**
     * �ѻ�����Ϸ���ݴ���Ự��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // �������л�ȡ������Ϸ��ѯ����
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // ������Ϸid�б�
        String gameId[] = request.getParameterValues("selectGame");

        // �������Ϊ��ʱ
        if (null == gameId && gameId.length == 0)
        {
            this.saveMessagesValue(request, "û�пɼ���Ļ�����Ϸ���ݣ�");
            return mapping.findForward(forward);
        }

        // ���ݴ�����ʱ����
        try
        {
            BaseRecommBO.getInstance().addBaseData(gameId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // ������Ϣ
        this.saveMessagesValue(request, "����������Ϸ��Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseGameQuery.do");
        return mapping.findForward(forward);
    }

    /**
     * ��ѯ������Ϸ������ʱ����Ϣ
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException 
     * @throws BOException
     */
    public ActionForward query(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("doPerform()");
        }

        String forward = "list";

        // �������л�ȡ������Ϸ��ѯ����
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ѯ������Ϸ����Ϣ
        BaseRecommBO.getInstance().queryBaseDateTempList(page,DATATYPE);

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("PageResult", page);
        
        return mapping.findForward(forward);
    }

    /**
     * �ѻ�����Ϸ���ݴ���ʱ����ɾ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward del(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // �������л�ȡ������Ϸ��ѯ����
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // ������Ϸid�б�
        String gameId[] = request.getParameterValues("selectGame");

        // ���ݴ�����ʱ����
        try
        {
            BaseRecommBO.getInstance().delBaseData(gameId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // ������Ϣ
        this.saveMessagesValue(request, "ɾ��������Ϸ��Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseGameTemp.do");
        return mapping.findForward(forward);
    }
    
    /**
     * �ѻ�����Ϸ���ݵ������ļ�
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward toFile(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // �������л�ȡ������Ϸ��ѯ����
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // ���ݴ�����ʱ����
        try
        {
            BaseRecommBO.getInstance().toFile(DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // ������Ϣ
        this.saveMessagesValue(request, "����������Ϸ�Ƽ��ļ���Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseGameTemp.do");
        return mapping.findForward(forward);
    }
}
