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
 * 接收用户手动触发读取DCMP数据类
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
			saveMessagesValue(request, "DCMP资讯同步成功");
		} catch (Exception e)
		{
			logger.error(e);
			saveMessagesValue(request, "DCMP资讯同步失败");
			forward=Constants.FORWARD_COMMON_FAILURE;
		}
		
		return mapping.findForward(forward);
		
	}

}
