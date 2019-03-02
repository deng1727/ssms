package com.aspire.ponaadmin.web.lockLocation.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.web.lockLocation.config.Constant;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class LockLocationBO {
	private static LockLocationBO instance = null;

	private LockLocationBO() {
	}

	public static LockLocationBO getInstance() {
		if (instance == null)
			instance = new LockLocationBO();
		return instance;
	}

	public void queryLockLocationList(PageResult page, Map map)
			throws BOException {
		try {
			LockLocationDAO.getInstance().queryLockLocationList(page, map);
		} catch (DAOException e) {
			throw new BOException("��ҳ��ѯ���������б��쳣");
		}
	}
	/**
	 * ��ҳ��ѯ������Ʒ�����б�
	 * 
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryLockRefList(PageResult page, Map map)throws BOException{
		try {
			LockLocationDAO.getInstance().queryLockRefList(page, map);
		} catch (DAOException e) {
			throw new BOException("��ҳ��ѯ������Ʒ�����б��쳣");
		}
	}
	public void queryContentList(PageResult page, Map map)
	throws BOException {
		try {
			LockLocationDAO.getInstance().queryContentList(page, map);
		} catch (DAOException e) {
			throw new BOException("��ҳ��ѯ���е�Ӧ���б��쳣");
		}
	}
	public List<RefrenceVO> queryLockList(String categoryId)throws BOException{
		try {
			return LockLocationDAO.getInstance().queryLockList(categoryId);
		} catch (Exception e) {
			throw new BOException("��ѯ"+categoryId+"��������Ʒ�쳣");
		}
	}
	/**
	 * ��ѯ���ܵ���������λ��
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	public String getLockNums(Map<String,Object> map)throws BOException{
		try {
			return LockLocationDAO.getInstance().getLockNums(map);
		} catch (Exception e) {
			throw new BOException(e.getMessage());
		}
	}
	public void addContent(String cID,RefrenceVO refrenceVO)throws BOException{
		String transactionID = null;
		try {
			
			Category category = (Category) Repository.getInstance().getNode(
					cID, RepositoryConstants.TYPE_CATEGORY);	
			
	        //���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
	        //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");//��Ʒ���Ż�������
			String operateategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
			 if(SSMSDAO.isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ 
				||SSMSDAO.isOperateCategory(category.getId(), operateategoryId)){
					 transactionID = ContextUtil.getTransactionID();         
			 }
			//��ȡ�û����е���С�����
			int sortId = LockLocationDAO.getInstance().getMinSortId(category.getCategoryID());
			refrenceVO.setCategoryId(category.getCategoryID());
			refrenceVO.setSortId(sortId);
			refrenceVO.setLockTime(new SimpleDateFormat().format(new Date()));
			category.setLockTime(new SimpleDateFormat().format(new Date()));
			String rId = null;
             if(transactionID!=null){
            	 rId= CategoryTools.addGoodTran(category, refrenceVO,transactionID);
             }else{
            	 rId= CategoryTools.addGood(category, refrenceVO);
             }
             
             //���ô洢����
             LockLocationDAO.getInstance().callProcedureFixSortid(category.getCategoryID(),rId,refrenceVO.getLockNum(),Constant.OP_UPDATE);
		} catch (Exception e) {
			throw new BOException(e.getMessage());
		}
	}
	/**
	 * ���߻����µ���Ʒ
	 * @param categoryID	����ID
	 * @param refIDs	��Ҫ���ߵ���ƷID
	 * @return	�����ߵ���Ʒ�����
	 * @throws BOException
	 */
	public void removeContent(String cID, String[] refIDs,String removeType) throws BOException{
		Category category = null;
		try {
			// ��Ŀ��������Ƴ�
			category = (Category) Repository.getInstance().getNode(cID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (Exception e) {
			throw new BOException(cID+"��ȡ������Ϣ�쳣");
		}
		try {
			boolean sendMsg=false;
			//�����¼���Ϣ
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");//��Ʒ���Ż�������
			String operateategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
			 if(SSMSDAO.isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ 
				||SSMSDAO.isOperateCategory(category.getId(), operateategoryId)){
				 sendMsg = true;
			 }
			if(category!=null){
				if(refIDs!=null&&refIDs.length>0){
					for(String rId:refIDs){
						//���ô洢����ִ�����ߡ�����ȡ���ؽ��
						String result = LockLocationDAO.getInstance().callProcedureFixSortid(category.getCategoryID(), rId, -1, Constant.OP_DEL);
						//�����¼���Ϣ
						if(sendMsg){
							//SUCCESS|goodsId
							if(result.contains("SUCCESS")){
								String [] ss = result.split("\\|");
								if(ss!=null&&ss.length==2&&ss[1].length()>1){
									SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,null,
											ss[1]+"::::::9",com.aspire.dotcard.syncAndroid.common.Constant.MESSAGE_HANDLE_STATUS_INIT);
								}
							}
						}
					}
				}
				
			}
			
			
		} catch (Exception e) {
			throw new BOException(e.getMessage());
		}
	}
}
