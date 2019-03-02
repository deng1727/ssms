package com.aspire.dotcard.dcmpmgr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
/**
 * �����û��ֶ�������ȡDCMP������
 * @author zhangwei
 *
 */
public class FTPDCMPAction extends BaseAction
{
	JLogger logger=LoggerFactory.getLogger(FTPDCMPAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BOException
	{
		
		String  forward=Constants.FORWARD_COMMON_SUCCESS;
		
		try
		{
			FTPDCMPBO.syncNewsFromDCMP();
			saveMessagesValue(request, "DCMP��Ѷͬ���ɹ�");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "DCMP��Ѷͬ��ʧ��");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		
		return mapping.findForward(forward);
		
	}

}
