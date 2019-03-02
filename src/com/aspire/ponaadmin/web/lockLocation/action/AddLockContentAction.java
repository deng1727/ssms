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
		String actionType = "新增锁定位置";
		String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
		try {
			//先判断该商品是否已经存在，若已存在，则只更新锁定状态
			String rId = LockLocationDAO.getInstance().getRIdFromCategory(categoryId, contentId);
			if(rId!=null){
				LockLocationDAO.getInstance().updateRefrence(refrenceVO);
				//调用存储过程
	             LockLocationDAO.getInstance().callProcedureFixSortid(categoryId,rId,refrenceVO.getLockNum(),Constant.OP_UPDATE);
			}else{
				logger.info("");
				LockLocationBO.getInstance().addContent(nodeId, refrenceVO);
			}
			//更新t_r_category表的锁定状态
            LockLocationDAO.getInstance().updateCategoryLockStatus(categoryId, 1);
            this.saveMessagesValue(request, "商品增加成功");
            this.actionLog(request, "添加锁定商品成功", "货架categoryId:"+categoryId+"应用ID:"+contentId, true, "添加锁定商品成功");
		} catch (Exception e) {
			this.actionLog(request, "添加锁定商品失败", "货架categoryId:"+categoryId+"应用ID:"+contentId, false, "添加锁定商品失败");
			forward = Constants.FORWARD_COMMON_FAILURE;
			this.saveMessages(request, "添加失败");
			return mapping.findForward(forward);
		}
		// 写操作日志
        this.actionLog(request,
                       actionType,
                       actionTarget,
                       actionResult,
                       actionDesc);
		
		forward = "showResult";
		
		return mapping.findForward(forward);
	}

}
