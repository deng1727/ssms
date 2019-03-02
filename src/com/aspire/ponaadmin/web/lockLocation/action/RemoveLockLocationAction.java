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
 * ����󶨣�����󣬽�ɾ���û����������Ѿ���������Ʒ��
 * @author shiyangwang
 *
 */
public class RemoveLockLocationAction extends BaseAction{
	private static JLogger logger = LoggerFactory.getLogger(RemoveLockLocationAction.class);
	@Override
	public ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BOException {
		//��������ID
		String nodeId = this.getParameter(request, "nodeId");
		/**
		 * ɾ�����ͣ�1 ����������ܣ������µ������ѱ���������Ʒ���ᱻɾ������rIdΪ��
		 * 			2 ɾ��������ĳ��Ʒ��rId��Ϊ��
		 */
		String removeType = this.getParameter(request, "removeType");
		//��ɾ������ƷID��ֻ��removeType=2ʱ����
		String rId = this.getParameter(request, "rId");
		String lockNums = this.getParameter(request, "lockNums");
		request.setAttribute("lockNums", lockNums);
		String goURL=this.getParameter(request, "goURL");
        String actionType = "��Ʒ�¼�";
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
					this.actionLog(request, "��ʼ��Ʒ�¼�", "����ID:"+id, true, "��ʼ��Ʒ�¼�");
					if((rId==null||"".equals(rId.trim()))&&"1".equals(removeType)){
						goURL="../../web/lockLocation/lockLocationListAction.do?isLock=1";
						//��ȡ�û��������б���������Ʒ
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
					this.saveMessagesValue(request, "��Ʒɾ���ɹ�");
		            forward = Constants.FORWARD_COMMON_SUCCESS ;
				} catch (Exception e) {
					logger.error(e);
		            actionResult = false;
				}
				
				// д������־
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
