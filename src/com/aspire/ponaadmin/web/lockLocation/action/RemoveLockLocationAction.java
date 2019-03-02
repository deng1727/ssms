package com.aspire.ponaadmin.web.lockLocation.action;

import java.util.List;

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
import com.aspire.ponaadmin.web.lockLocation.bo.LockLocationBO;
import com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

/**
 * 解除绑定，解除后，将删除该货架下所有已经锁定的商品。
 * @author shiyangwang
 *
 */
public class RemoveLockLocationAction extends BaseAction{
	private static JLogger logger = LoggerFactory.getLogger(RemoveLockLocationAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		//货架内码ID
		String nodeId = this.getParameter(request, "nodeId");
		/**
		 * 删除类型：1 解除锁定货架（货架下的所以已被锁定的商品都会被删除），rId为空
		 * 			2 删除货架下某商品，rId不为空
		 */
		String removeType = this.getParameter(request, "removeType");
		//被删除的商品ID，只有removeType=2时存在
		String rId = this.getParameter(request, "rId");
		String lockNums = this.getParameter(request, "lockNums");
		request.setAttribute("lockNums", lockNums);
		String goURL=this.getParameter(request, "goURL");
        String actionType = "商品下架";
        String actionTarget = "";
        String actionDesc = "";
        boolean actionResult = true;
        String forward = null;
        
		LockLocationBO lockLocationBO = LockLocationBO.getInstance();
		String nodeIds[] = nodeId.split(",");
		for(int i=0;i<nodeIds.length;i++){
			String id = nodeIds[i];
			if(id!=null&&!"".equals(id)){
				Category category = (Category) Repository.getInstance().getNode(id,
						RepositoryConstants.TYPE_CATEGORY);
				try {
					this.actionLog(request, "开始商品下架", "货架ID:"+id, true, "开始商品下架");
					if((rId==null||"".equals(rId.trim()))&&"1".equals(removeType)){
						goURL="../../web/lockLocation/lockLocationListAction.do?isLock=1";
						//获取该货架下所有被锁定的商品
						List<RefrenceVO> list = lockLocationBO.queryLockList(category.getCategoryID());
						if(list!=null&&list.size()>0){
							String rIds[] = new String[list.size()];
							for(int j=0;j<list.size();j++){
								RefrenceVO vo = list.get(j);
								rIds[j] = vo.getId();
							}
							lockLocationBO.removeContent(id, rIds,removeType);
						}
						request.setAttribute(Constants.PARA_GOURL,goURL) ;
					}else{
						goURL="../../web/lockLocation/lockRefListAction.do?categoryId="+category.getCategoryID()+"&nodeId=" + nodeId;
						String rIds[] = rId.split(",");
						lockLocationBO.removeContent(id, rIds,removeType);
					}
					this.saveMessagesValue(request, "商品删除成功");
		            forward = Constants.FORWARD_COMMON_SUCCESS ;
				} catch (Exception e) {
					logger.error(e);
		            actionResult = false;
				}
				
				// 写操作日志
                this.actionLog(request,
                               actionType,
                               actionTarget,
                               actionResult,
                               actionDesc);
			}
		}
		request.setAttribute("goURL", goURL);
		return mapping.findForward(forward);
	}

}
