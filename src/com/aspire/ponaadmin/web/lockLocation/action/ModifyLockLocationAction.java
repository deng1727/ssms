package com.aspire.ponaadmin.web.lockLocation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.lockLocation.config.Constant;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class ModifyLockLocationAction extends BaseAction{

	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		String forward = Constants.FORWARD_COMMON_SUCCESS;
		String goURL=this.getParameter(request, "goURL");
		String nodeId =  this.getParameter(request, "nodeId");
		String categoryId = this.getParameter(request, "categoryId");
		String lockNums = this.getParameter(request, "lockNums");
		String changedValues = this.getParameter(request, "changedValues");
		Category category = (Category) Repository.getInstance().getNode(nodeId,
				RepositoryConstants.TYPE_CATEGORY);
		String message = "";
		if(changedValues!=null&&!"".equals(changedValues.trim())){
			String [] changeds = changedValues.split(":",-1);
			for(String change:changeds){
				String rId = change.split("#")[0];
				String lockNum = change.split("#")[1];
				try {
					LockLocationDAO.getInstance().callProcedureFixSortid(category.getCategoryID(), rId, Integer.parseInt(lockNum), Constant.OP_UPDATE);
					this.actionLog(request, "变更货架"+categoryId+"的商品锁定位置成功", "", true,
							"商品ID"+rId+"的锁定位置变更为"+lockNum);
				} catch (Exception e) {
					this.actionLog(request, "变更货架"+categoryId+"的商品锁定位置失败", "", false,
							"商品ID"+rId+"的锁定位置变更为"+lockNum);
				} 
			}
		}
		this.saveMessagesValue(request, "商品锁定位置保存成功");
		goURL="../../web/lockLocation/lockRefListAction.do?categoryId="+category.getCategoryID()+"&nodeId=" + nodeId;
		request.setAttribute(Constants.PARA_GOURL,goURL);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("lockNums", lockNums);
		this.saveMessagesValue(request, message);
		return mapping.findForward(forward);
	}

}
