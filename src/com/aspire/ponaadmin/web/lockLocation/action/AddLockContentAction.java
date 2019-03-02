package com.aspire.ponaadmin.web.lockLocation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.lockLocation.bo.LockLocationBO;
import com.aspire.ponaadmin.web.lockLocation.config.Constant;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO;

public class AddLockContentAction extends BaseAction {
	private JLogger logger = LoggerFactory.getLogger(AddLockContentAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = null;
		String backURL = this.getParameter(request, "backURL");
		
		String user = UserManagerBO.getInstance().getUserSessionVO(
				request.getSession()).getUser().getName();
		String nodeId = this.getParameter(request, "nodeId");
		String categoryId = this.getParameter(request, "categoryId");
		String contentId = this.getParameter(request, "contentId");
		Integer lockNum = Integer.parseInt(this.getParameter(request, "lockNum"));
		if (backURL == null || "".equals(backURL.trim()))
			backURL = "contentListAction.do?nodeId=" + nodeId;
		
		RefrenceVO refrenceVO = new RefrenceVO();
		refrenceVO.setCategoryId(categoryId);
		refrenceVO.setContentId(contentId);
		refrenceVO.setRefNodeId(contentId);
		refrenceVO.setIsLock(1);
		refrenceVO.setLockNum(lockNum);
		refrenceVO.setLockUser(user);
		request.setAttribute("backURL", backURL);
		request.setAttribute("nodeId", nodeId);
		String actionType = "��������λ��";
		String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
		try {
			//���жϸ���Ʒ�Ƿ��Ѿ����ڣ����Ѵ��ڣ���ֻ��������״̬
			String rId = LockLocationDAO.getInstance().getRIdFromCategory(categoryId, contentId);
			if(rId!=null){
				LockLocationDAO.getInstance().updateRefrence(refrenceVO);
				//���ô洢����
	             LockLocationDAO.getInstance().callProcedureFixSortid(categoryId,rId,refrenceVO.getLockNum(),Constant.OP_UPDATE);
			}else{
				logger.info("");
				LockLocationBO.getInstance().addContent(nodeId, refrenceVO);
			}
			//����t_r_category�������״̬
            LockLocationDAO.getInstance().updateCategoryLockStatus(categoryId, 1);
            this.saveMessagesValue(request, "��Ʒ���ӳɹ�");
            this.actionLog(request, "���������Ʒ�ɹ�", "����categoryId:"+categoryId+"Ӧ��ID:"+contentId, true, "���������Ʒ�ɹ�");
		} catch (Exception e) {
			this.actionLog(request, "���������Ʒʧ��", "����categoryId:"+categoryId+"Ӧ��ID:"+contentId, false, "���������Ʒʧ��");
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, "���ʧ��");
			return mapping.findForward(forward);
		}
		// д������־
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		
		forward = "showResult";
		
		return mapping.findForward(forward);
	}

}
