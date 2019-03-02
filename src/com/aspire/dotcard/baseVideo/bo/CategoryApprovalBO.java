package com.aspire.dotcard.baseVideo.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideo.WriteActionLogAndEmailTask;
import com.aspire.dotcard.baseVideo.dao.CategoryApprovalDAO;
import com.aspire.dotcard.baseVideo.dao.CategoryDAO;
import com.aspire.dotcard.baseVideo.dao.VideoDAO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.web.SEQCategoryUtil;

public class CategoryApprovalBO {
private final static JLogger LOGGER = LoggerFactory.getLogger(CategoryApprovalBO.class);
	
	private static CategoryApprovalBO instance = new CategoryApprovalBO();
	private CategoryApprovalBO(){
	}
	
	public static CategoryApprovalBO getInstance(){
		return instance;
	}
	
	/**
	 * ��Ƶ������������
	 * 
	 * @param request
	 * @param categoryId ��Ƶ���ܱ���
	 * @throws BOException
	 */
	public void categoryApprovalPass(HttpServletRequest request,String categoryId) throws BOException{
		
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			String categoryIdArray[] = categoryId.split(",");
				for (int i = 0; i < categoryIdArray.length; i++) {
				Map<String,Object> itemMap = CategoryApprovalDAO.getInstance().queryCategoryListItem(categoryIdArray[i]);
				if(itemMap != null){
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 2);
					if(!"2".equals(itemMap.get("delpro_status"))){
						VideoDAO.getInstance().delVideoCategory(tdb,categoryIdArray[i]);

						VideoDAO.getInstance().delVideoCategoryKeyResource(tdb,categoryIdArray[i]);
					}else{
						CategoryApprovalDAO.getInstance().categoryApprovalPass(tdb, "1",categoryIdArray[i]);
					}
					CategoryDAO.getInstance().approvalCategory(tdb, categoryIdArray[i], "1", "1", logUser.getName());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailTitle", "�����ѷ������ͻ����Ż���Ӫ�����ѷ���");
					//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����";
					map.put("mailContent", value);
					map.put("status", "1");
					map.put("categoryId", categoryIdArray[i]);
					map.put("operation", "60");
					map.put("operationtype", "��Ƶ���ܹ�����������");
					map.put("operationobj", "��Ƶ���ܹ���");
					map.put("operationobjtype", "��Ƶ����Id��" + categoryIdArray[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("��Ƶ�����������������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ�����������������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("��Ƶ�����������������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ�����������������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
		
	}
	/**
	 * ��Ƶ����������ͨ��
	 * 
	 * @param request
	 * @param categoryId ��Ƶ���ܱ���
	 * @throws BOException
	 */
	public void categoryApprovalNoPass(HttpServletRequest request,
			String categoryId) throws BOException {

		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			String categoryIdArray[] = categoryId.split(",");
			for (int i = 0; i < categoryIdArray.length; i++) {
				Map<String, Object> itemMap = CategoryApprovalDAO.getInstance()
						.queryCategoryListItem(categoryIdArray[i]);
				if (itemMap != null) {
					if (!"2".equals(itemMap.get("delpro_status"))) {
						CategoryApprovalDAO.getInstance().deleteNoPass(tdb,
								categoryIdArray[i]);
					} else {
						CategoryApprovalDAO.getInstance().categoryApprovalPass(
								tdb, "3",categoryIdArray[i]);
					}
					CategoryDAO.getInstance().approvalCategory(tdb,
							categoryIdArray[i], "3", "1", logUser.getName());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailTitle", "��˲��أ��ͻ����Ż���Ӫ������������");
					//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�" +  this.getUserInfo(request) + "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" ;
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�" +  this.getUserInfo(request) + "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 2);
					map.put("mailContent", value);
					map.put("status", "3");
					map.put("categoryId", categoryIdArray[i]);
					map.put("operation", "60");
					map.put("operationtype", "��Ƶ���ܹ���������ͨ��");
					map.put("operationobj", "��Ƶ���ܹ���");
					map.put("operationobjtype", "��Ƶ����Id��" + categoryIdArray[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
							map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("��Ƶ����������ͨ�������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ����������ͨ�������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("��Ƶ����������ͨ�������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ����������ͨ�������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
    /**
     * ��Ƶ��Ʒ������������
     * 
     * @param request
     * @param categoryId ��Ƶ���ܱ���
     * @throws BOException
     */
    public void goodsApprovalPass(HttpServletRequest request,String categoryId) throws BOException{
    	UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			String categoryIdArray[] = categoryId.split(",");
				for (int i = 0; i < categoryIdArray.length; i++) {
				List<Map<String,Object>> list = CategoryApprovalDAO.getInstance().queryCategoryGoodsList(categoryIdArray[i]);
				for(int j = 0;j < list.size();j++){
					Map<String,Object> itemMap = list.get(j);
					if(itemMap != null){
						if(!"2".equals(itemMap.get("delflag"))){
							CategoryApprovalDAO.getInstance().deleteCategoryGoodsItem(tdb, itemMap.get("id").toString());
						}else{
							CategoryApprovalDAO.getInstance().categoryGoodsAllApproval(tdb, categoryIdArray[i], "1", "2");
						}
					}
				}
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryIdArray[i], "1");
				CategoryDAO.getInstance().approvalCategory(tdb, categoryIdArray[i], "1", "2", logUser.getName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mailTitle", "�����ѷ������ͻ����Ż���Ӫ�����ѷ���");
				//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����";
				String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 2);
				map.put("mailContent", value);
				map.put("status", "1");
				map.put("categoryId", categoryIdArray[i]);
				map.put("operation", "60");
				map.put("operationtype", "��Ƶ��Ʒ������������");
				map.put("operationobj", "��Ƶ��Ʒ����");
				map.put("operationobjtype", "��Ƶ��Ʒ����Id��" + categoryIdArray[i]);
				map.put("operator", logUser.getName());
				WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
				DaemonTaskRunner.getInstance().addTask(task);
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("��Ƶ��Ʒ�����������������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ��Ʒ�����������������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("��Ƶ��Ʒ�����������������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ��Ʒ�����������������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
    }
    /**
     * ��Ƶ��Ʒ����������ͨ��
     * @param request
     * @param categoryId ��Ƶ���ܱ���
     * @throws BOException
     */
    public void goodsApprovalNoPass(HttpServletRequest request,String categoryId) throws BOException{
    	UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			String categoryIdArray[] = categoryId.split(",");
			for (int i = 0; i < categoryIdArray.length; i++) {
				List<Map<String,Object>> list = CategoryApprovalDAO.getInstance().queryCategoryGoodsList(categoryIdArray[i]);
				for(int j = 0;j < list.size();j++){				
					Map<String,Object> itemMap = list.get(j);
					if (itemMap != null) {
						if (!"2".equals(itemMap.get("delflag"))) {
							CategoryApprovalDAO.getInstance().callBackCategoryGoodsItem(tdb,itemMap.get("id").toString());
						} else {
							CategoryApprovalDAO.getInstance().categoryGoodsAllApproval(tdb, categoryIdArray[i], "3", "2");
						}
					}
				}
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryIdArray[i], "3");
				CategoryDAO.getInstance().approvalCategory(tdb,
						categoryIdArray[i], "3", "2", logUser.getName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mailTitle", "��˲��أ��ͻ����Ż���Ӫ������������");
				//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�" +  this.getUserInfo(request) + "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" ;
				String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�" +  this.getUserInfo(request) + "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" +SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 2);
				map.put("mailContent", value);
				map.put("status", "3");
				map.put("categoryId", categoryIdArray[i]);
				map.put("operation", "60");
				map.put("operationtype", "��Ƶ���ܹ���������ͨ��");
				map.put("operationobj", "��Ƶ���ܹ���");
				map.put("operationobjtype", "��Ƶ����Id��" + categoryIdArray[i]);
				map.put("operator", logUser.getName());
				WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
						map);
				DaemonTaskRunner.getInstance().addTask(task);
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("��Ƶ��Ʒ����������ͨ�������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ��Ʒ����������ͨ�������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("��Ƶ��Ʒ����������ͨ�������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ��Ʒ����������ͨ�������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
    }
    
    public String getUserInfo(HttpServletRequest request){
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		return logUser.getName();
	}
    
    /**
	 * ��ѯ��Ƶ����
	 * 
	 * @param categoryId ���ܱ��
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> queryCategoryListItem(String categoryId)
			throws BOException {
		return CategoryApprovalDAO.getInstance().queryCategoryListItem(categoryId);
	}
	/**
	 * ��Ƶ��Ʒ�����ύ����
	 * 
	 * @param request
	 * @param categoryId ��Ƶ���ܱ��
	 * @param approval  ��������part�����֣�all��ȫ��
	 * @param dealRef  ��Ƶ��Ʒ���е�Id+verify_status
	 * @throws BOException
	 */
	public void categoryGoodsApproval(HttpServletRequest request,
			String categoryId,String approval,String[] dealRef) throws BOException {

		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			if("part".equals(approval)){
				CategoryApprovalDAO.getInstance().categoryGoodsApproval(tdb, dealRef,"2", categoryId);
				if(!(CategoryApprovalDAO.getInstance().isApproval(categoryId, "0") || CategoryApprovalDAO.getInstance().isApproval(categoryId, "3"))){
					CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryId, "2");
				}
			}else{
				CategoryApprovalDAO.getInstance().categoryGoodsAllApproval(tdb, categoryId,"2","0");
				CategoryApprovalDAO.getInstance().categoryGoodsAllApproval(tdb, categoryId,"2","3");
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryId, "2");
			}
			CategoryDAO.getInstance().approvalCategory(tdb,categoryId, "2", "2", logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "��������ͻ����Ż���Ӫ���ݵ�������");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;����������Ƶ���ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����;<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;����������Ƶ���ܹ���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 2) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��" + "�ύ����";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "��Ƶ��Ʒ�����ύ����");
			map.put("operationobj", "��Ƶ��Ʒ����");
			map.put("operationobjtype", "��Ƶ��Ʒ����Id��" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);			
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("��Ƶ��Ʒ�����ύ���������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ��Ʒ�����ύ���������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("��Ƶ��Ʒ�����ύ���������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("��Ƶ��Ʒ�����ύ���������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	/**
	 * ������Ƶ��Ʒ��������
	 * 
	 * @param request
	 * @param categoryId ��Ƶ���ܱ��
	 * @param dealRef  ��Ƶ��Ʒ���е�Id+verify_status
	 * @throws BOException
	 */
	public void accept(HttpServletRequest request,
			String categoryId,String[] dealRef) throws BOException {

		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			CategoryApprovalDAO.getInstance().categoryGoodsApproval(tdb, dealRef,"1", categoryId);
			if(!(CategoryApprovalDAO.getInstance().isApproval(categoryId, "2") || CategoryApprovalDAO.getInstance().isApproval(categoryId, "0"))){
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryId, "1");
			}
			CategoryDAO.getInstance().approvalCategory(tdb,categoryId, "1", "2", logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "�����ѷ������ͻ����Ż���Ӫ�����ѷ���");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 2);
			map.put("mailContent", value);
			map.put("status", "1");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "��Ƶ���ܹ�����������");
			map.put("operationobj", "��Ƶ���ܹ���");
			map.put("operationobjtype", "��Ƶ��Ʒ����Id��" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);			
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("������Ƶ��Ʒ�������������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("������Ƶ��Ʒ�������������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("������Ƶ��Ʒ�������������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("������Ƶ��Ʒ�������������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	/**
	 * ������Ƶ��Ʒ������ͨ��
	 * @param request
	 * @param categoryId ��Ƶ���ܱ��
	 * @param dealRef  ��Ƶ��Ʒ���е�Id+verify_status
	 * @throws BOException
	 */
	public void refuse(HttpServletRequest request,
			String categoryId,String[] dealRef) throws BOException {

		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}

		// �����������
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			CategoryApprovalDAO.getInstance().categoryGoodsApproval(tdb, dealRef,"3", categoryId);
			if(!(CategoryApprovalDAO.getInstance().isApproval(categoryId, "2") || CategoryApprovalDAO.getInstance().isApproval(categoryId, "0"))){
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryId, "3");
			}
			CategoryDAO.getInstance().approvalCategory(tdb,categoryId, "3", "2", logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "��˲��أ��ͻ����Ż���Ӫ������������");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�" +  this.getUserInfo(request) + "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" ;
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�" +  this.getUserInfo(request) + "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;����Id��" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 2);
			map.put("mailContent", value);
			map.put("status", "3");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "��Ƶ��Ʒ����������ͨ��");
			map.put("operationobj", "��Ƶ��Ʒ����");
			map.put("operationobjtype", "��Ƶ��Ʒ����Id��" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);			
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("������Ƶ��Ʒ������ͨ�������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("������Ƶ��Ʒ������ͨ�������쳣:", e);
		} catch (Exception e) {
			LOGGER.error("������Ƶ��Ʒ������ͨ�������쳣:", e);
			// ִ�лع�
			tdb.rollback();
			throw new BOException("������Ƶ��Ʒ������ͨ�������쳣:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
}
