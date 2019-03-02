/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm.basevideo;


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
public class BaseVideoAction extends BaseAction
{

    /**
     * ��־����
     */
    protected static final JLogger logger = LoggerFactory.getLogger(BaseVideoAction.class);

    private String DATATYPE = "baseVideo";

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
        if ("addData".equals(action))
        {
            return addData(mapping, form, request, response);
        }
        else
        {
            return query(mapping, form, request, response);
        }

    }

    /**
     * �ѻ�����Ƶ���ݴ���Ự��
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
        // �������л�ȡ�������ֲ�ѯ����
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // ��������id�б�
        String videoId[] = request.getParameterValues("selectVideo");

        // �������Ϊ��ʱ
        if (null == videoId && videoId.length == 0)
        {
            this.saveMessagesValue(request, "û�пɼ���Ļ�����Ƶ���ݣ�");
            return mapping.findForward(forward);
        }

        // ���ݴ�����ʱ����
        try
        {
            BaseRecommBO.getInstance().addBaseData(videoId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // ������Ϣ
        this.saveMessagesValue(request, "����������Ƶ��Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseVideoQuery.do");
        return mapping.findForward(forward);
    }

    /**
     * ��ѯ������Ƶ������ʱ����Ϣ
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

        // �������л�ȡ������Ƶ��ѯ����
        PageResult page = new PageResult(request);
        page.setPageSize(Integer.parseInt(PageSizeConstants.page_DEFAULT));

        // ���ݻ�ȡ�Ĳ�����ѯ������Ƶ����Ϣ
        BaseRecommBO.getInstance().queryBaseDateTempList(page,DATATYPE);

        // ��list���õ�page�����ڷ�ҳչʾ
        request.setAttribute("PageResult", page);
        
        return mapping.findForward(forward);
    }

    /**
     * �ѻ�����Ƶ���ݴ���ʱ����ɾ��
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
        // �������л�ȡ������Ƶ��ѯ����
        String forward = Constants.FORWARD_COMMON_FAILURE;

        // ������Ƶid�б�
        String videoId[] = request.getParameterValues("selectVideo");

        // ���ݴ�����ʱ����
        try
        {
            BaseRecommBO.getInstance().delBaseData(videoId, DATATYPE);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // ������Ϣ
        this.saveMessagesValue(request, "ɾ��������Ƶ��Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseVideoTemp.do");
        return mapping.findForward(forward);
    }
    
    /**
     * �ѻ�����Ƶ���ݵ������ļ�
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
        // �������л�ȡ������Ƶ��ѯ����
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
        this.saveMessagesValue(request, "����������Ƶ�Ƽ��ļ���Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseVideoTemp.do");
        return mapping.findForward(forward);
    }
    
    
    /**
     * ����������Ƶ����
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws BOException
     */
    public ActionForward addData(ActionMapping mapping, ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
    {
        // �������л�ȡ������Ƶ��ѯ����
        String forward = Constants.FORWARD_COMMON_FAILURE;

        String videoName = this.getParameter(request, "videoName").trim();;
        String videoUrl = this.getParameter(request, "videoUrl").trim();;
        
        // ���ݴ�����ʱ����
        try
        {
            BaseRecommBO.getInstance().addData(videoName, videoUrl);
        }
        catch (BOException e)
        {
            this.saveMessagesValue(request, e.getMessage());
            return mapping.findForward(forward);
        }

        // ������Ϣ
        this.saveMessagesValue(request, "����������Ƶ������Ϣ�ɹ���");

        forward = Constants.FORWARD_COMMON_SUCCESS;
        request.setAttribute(Constants.PARA_GOURL, "baseVideoQuery.do");
        return mapping.findForward(forward);
    }
}
