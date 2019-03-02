package com.aspire.ponaadmin.web.blacklist.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.common.rightmanager.RightManagerConstant;
import com.aspire.ponaadmin.web.BaseAction;
import com.aspire.ponaadmin.web.blacklist.biz.BlackListBo;
import com.aspire.ponaadmin.web.blacklist.dao.BlackListDao;
import com.aspire.ponaadmin.web.blacklist.vo.BlackListVO;
import com.aspire.ponaadmin.web.constant.Constants;
import com.aspire.ponaadmin.web.constant.ResourceConstants;

/**
 * 新增
 * 
 * @author x_zhailiqing
 * 
 */
public class BlackAction extends BaseAction {
	private static final JLogger LOGGER = LoggerFactory
			.getLogger(BlackAction.class);

	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		LOGGER.debug("BlackAction()");

		// 获取参数
		String action = request.getParameter("action");
		String contentId = request.getParameter("contentId");
		String strBlackType = request.getParameter("blackType");

		LOGGER.debug("action======" + action);
		BlackListVO vo = new BlackListVO();
		vo.setContentId(contentId);
		//if(null!=strBlackType){
		vo.setBlackType(1);
		//}
//		Date d = new Date();
//		if(1==vo.getBlackType()){
//			//一周
//			vo.setInDate(DateUtil.dateChangeDays(DateUtil.formatDate(d, "yyyyMMdd"), "yyyyMMdd", 7));
//		}else if(2==vo.getBlackType()){
//			//一月
//			vo.setInDate(DateUtil.dateChangeDays(DateUtil.formatDate(d, "yyyyMMdd"), "yyyyMMdd", 30));
//		}else if(3==vo.getBlackType()){
//			//永久
//			vo.setInDate(DateUtil.dateChangeDays(DateUtil.formatDate(d, "yyyyMMdd"), "yyyyMMdd", 200*365));
//		}else if(4==vo.getBlackType()){
			String start = request.getParameter("startDate");
			String end = request.getParameter("endDate");
			if(null!=start){
				vo.setStartDate(start.replaceAll("-", ""));
			}
			if(null!=end){
				vo.setEndDate(end.replaceAll("-", ""));
			}
//		}
			
		String type = request.getParameter("type");
		if(null!=type && !"".equals(type)){
			vo.setType(Integer.parseInt(type));
		}
			
		String forward = null;
		boolean actionResult = false;
		String logActionType = "";
		if (action.equals(Constants.FORWARD_ADD_TOKEN)) {
			// 添加黑名单操作
			logActionType = "添加黑名单";
			List list = new ArrayList();
			if(null!=contentId){
				String t[] = contentId.split(",");
				for(int i=0;i<t.length;i++){
					BlackListVO tb = new BlackListVO();
					tb.setContentId(t[i]);
					tb.setType(vo.getType());
					tb.setStartDate(vo.getStartDate());
					tb.setEndDate(vo.getEndDate());
					list.add(tb);
				}
			}
			try {
				if(list.size()==1){
				    BlackListBo.getInstance().addBlack(vo);
				}else{
					//批量添加
					BlackListBo.getInstance().addBatchBlack(list);
				}
				forward = Constants.FORWARD_COMMON_SUCCESS;
				request.setAttribute(Constants.PARA_GOURL, "../blacklist/black_add.jsp");
				this.saveMessages(request,
						ResourceConstants.WEB_INF_ADD_BLACK_OK,vo.getContentId());
				actionResult = true;
			} catch (Exception e) {
				LOGGER.error(e);
				if(e instanceof BOException){
					BOException e1 = (BOException)e;
					if (e1.getErrorCode() == RightManagerConstant.ROLE_NAME_EXIST) {
						forward = Constants.FORWARD_COMMON_FAILURE;
						this.saveMessages(request,
								ResourceConstants.WEB_INF_BLACK_EXIST,vo.getContentId());
					} else {
						forward = Constants.FORWARD_COMMON_FAILURE;
						this.saveMessagesValue(request, e1.getMessage());
					}
				}
			}
		} else if (action.equals(Constants.FORWARD_UPDATE_TOKEN)) {
			// 更新黑名单信息操作
			logActionType = "修改黑名单";
			try {
				BlackListBo.getInstance().updateBlack(vo);
				forward = Constants.FORWARD_COMMON_SUCCESS;
				request.setAttribute(Constants.PARA_GOURL, "black.do");
				this.saveMessages(request,
						ResourceConstants.WEB_INF_MODIFY_BLACK_OK,vo.getContentId());
				actionResult = true;
			} catch (BOException e) {
					LOGGER.error(e);
					forward = Constants.FORWARD_COMMON_FAILURE;
					this.saveMessages(request,
							ResourceConstants.WEB_INF_MODIFY_BLACK_FAIL,vo.getContentId());
			}
		}else if(action.equals(Constants.FORWARD_DELETE_TOKEN)){
			logActionType = "删除黑名单";
			String blacklistStatus = request.getParameter("blacklistStatus");
			vo.setBlacklistStatus(blacklistStatus);
			try {
				//BlackListBo.getInstance().deleteBlack(vo);
				BlackListBo.getInstance().deleteBlackItem(vo);
				forward = Constants.FORWARD_COMMON_SUCCESS;
				request.setAttribute(Constants.PARA_GOURL, "black.do");
				this.saveMessages(request,
						ResourceConstants.WEB_INF_DEL_BLACK_OK,vo.getContentId());
				actionResult = true;
			} catch (BOException e) {

					LOGGER.error(e);
					forward = Constants.FORWARD_COMMON_FAILURE;
					this.saveMessages(request,
							ResourceConstants.WEB_INF_DEL_BLACK_FAIL,vo.getContentId());
			}			
		}else if("toUpdate".equals(action)){
			BlackListVO black;
			try {
				black = BlackListDao.getInstance().getBlackByContentId(vo.getContentId());
				request.setAttribute("black", black);
				return mapping.findForward("toUpdate");
			} catch (Exception e) {
				LOGGER.error(e);
				forward = Constants.FORWARD_COMMON_FAILURE;
			}
			
		}else{
			throw new RuntimeException("unknown action:" + action);
		}

		// 写操作日志
		if(!"".equals(logActionType)){
			String logActionTarget = vo.getContentId();
			String logDesc = "";
			this.actionLog(request, logActionType, logActionTarget,
					actionResult, logDesc);
		}

		return mapping.findForward(forward);
	}

}
