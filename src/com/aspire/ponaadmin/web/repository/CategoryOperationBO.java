package com.aspire.ponaadmin.web.repository;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.audit.dao.CheckLogDAO;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.common.usermanager.UserManagerBO;
import com.aspire.ponaadmin.common.usermanager.UserSessionVO;
import com.aspire.ponaadmin.common.usermanager.UserVO;
import com.aspire.ponaadmin.web.daemon.DaemonTaskRunner;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;

public class CategoryOperationBO {

	/**
	 * ��־����
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(CategoryOperationBO.class);

	private static CategoryOperationBO bo = new CategoryOperationBO();

	private CategoryOperationBO() {

	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryOperationBO getInstance() {

		return bo;
	}

	/**
	 * 
	 * @param request
	 * @param category
	 *            ����
	 * @param operationType
	 *            ����״̬
	 * @param type
	 *            ����ʽ: 1 ���ܷ������; 2 ������Ʒ����
	 * @param map
	 *            �����־��Ϣ mailTitle:�ʼ����� ; mailContent:�ʼ�����;status:����״̬ 0���༭ 1������
	 *            3����˲�ͨ��;categoryId:���ܱ���;operation:����ʽ
	 *            00��Ĭ�ϣ�10:MM���ܣ�20�����ݺ�������30
	 *            ��������Ϸ���ܣ�40�������Ķ����ܣ�50���������ֻ��ܣ�60��������Ƶ���ܣ�70�����ض�������;
	 *            operationtype:
	 *            ��������;operator:������;operationobj:��������;operationobjtype:������������;
	 * @throws BOException
	 * 
	 */
	public void operateInfo(HttpServletRequest request, Category category,
			String operationType, String type, Map<String, Object> map) throws BOException  {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		if(map.get("submit") != null && "all".equals(map.get("submit").toString().trim())){
			CategoryOperationDAO.getInstance().updateCategoryGoodsInfo(category.getCategoryID());
			category.setGoodsStatus("2");
			category.save();
		}else{
			CategoryOperationDAO.getInstance().operationCategoryGoods(category.getCategoryID(), (String[]) map.get("dealContent"));
		}
		CategoryOperationDAO.getInstance().operationCategory(category.getCategoryID(),
				operationType, operationType, logUser.getName());
		map.put("operator", logUser.getName());
		WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
		DaemonTaskRunner.getInstance().addTask(task);
	}

	/**
	 * ��������������Ʒ��д��־�����ʼ���
	 * 
	 * @param request
	 * @param categoryId
	 *            ���ܱ���
	 * @param status
	 *            ����״̬
	 * @param operation
	 *            ����ʽ: 1 ���ܷ������; 2 ������Ʒ����
	 * @param map
	 *            �����־��Ϣ mailTitle:�ʼ����� ; mailContent:�ʼ�����;status:����״̬ 0���༭ 1������
	 *            3����˲�ͨ��;categoryId:���ܱ���;operation:����ʽ
	 *            00��Ĭ�ϣ�10:MM���ܣ�20�����ݺ�������30
	 *            ��������Ϸ���ܣ�40�������Ķ����ܣ�50���������ֻ��ܣ�60��������Ƶ���ܣ�70�����ض�������;
	 *            operationtype:
	 *            ��������;operator:������;operationobj:��������;operationobjtype:������������;
	 * 
	 */
	public void operateInfoforGoods(HttpServletRequest request,
			String categoryIds, String status, String operation,
			Map<String, Object> map) throws BOException {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		String categoryIdAndPath = "";
		String categoryIdArray[] = categoryIds.split(",");
		if (categoryIdArray != null && categoryIdArray.length > 0) {
			for (int i = 0; i < categoryIdArray.length; i++) {
				String categoryId = categoryIdArray[i];
				if (categoryId != null && !"".equals(categoryId)) {
					// ��ȡ������Ϣ
					Category category = (Category) Repository.getInstance()
							.getNode(categoryId,
									RepositoryConstants.TYPE_CATEGORY);
					if (category != null) {
						String statusFlag = status;
						// ��ȡ�û����µ�������Ʒ
						List<ReferenceVO> list = CategoryOperationDAO
								.getInstance()
								.getReferenceListByCategoryId(
										category.getCategoryID());
						if("3".equals(status)){//�ڰ�����ɾ����������ͨ��ʱ����������Ʒ����״̬�ָ�Ϊ�༭
							if (list != null && list.size() > 0) {
								for (ReferenceVO rVo : list) {
									if("3".equals(rVo.getDelFlag())){
										statusFlag = "0";
									}
								}
							}
						}
						
						category.setGoodsStatus(statusFlag);
						
						// ����
						category.save();
						// ������Ʒ��������ͨ����ͨ����ʱ��ͬʱҪ����������µ���Ʒ
						if ("1".equals(status) || "3".equals(status)) {
							String flag = "3".equals(status) ? "2" : status;
							CategoryOperationDAO.getInstance()
									.verifyRefrenceByCategoryId(
											category.getCategoryID(), flag);// ���ݻ���ID����������Ʒ��
							//sql=update T_R_REFERENCE r set r.verify_status = ? ,r.verify_date = sysdate where  r.categoryid = ?  and r.verify_status = 0
							if (list != null && list.size() > 0) {
								for (ReferenceVO rVo : list) {

									// ������Ʒ����ͨ��������Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
									if ("1".equals(status)) {
										String transactionID = null;

										// ���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
										// ����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
										ModuleConfig module = ConfigFactory
												.getSystemConfig()
												.getModuleConfig("syncAndroid");
										String rootCategoryId = module
												.getItemValue("ROOT_CATEGORYID");
										String operateCategoryId = module
												.getItemValue("OPERATE_ROOT_CATEGORYID");// ������Ʒ���Ż��������µ���Ӫ����
										if (SSMSDAO.isAndroidCategory(
														category.getId(),
														rootCategoryId)
												// ||operateCategoryId.indexOf(cateid)!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ
												// modify by aiyan
												// 2013-05-10
												|| SSMSDAO.isOperateCategory(
																category
																		.getId(),
																operateCategoryId)) {// modify
											// by
											// aiyan
											// 2013-05-18
											transactionID = ContextUtil
													.getTransactionID();
										}

										ReferenceNode refNode = (ReferenceNode) Repository
												.getInstance()
												.getNode(
														rVo.getId(),
														RepositoryConstants.TYPE_REFERENCE);
										String contId = rVo.getRefNodeID();
//										String contId = refNode.getRefNodeID();//null
										if("1".equals(rVo.getDelFlag()) || "2".equals(rVo.getDelFlag()) || "3".equals(rVo.getDelFlag()) ){
											//����ͨ��
											if(Integer.parseInt(status) == 1){
												if (contId.charAt(0) >= '0' && contId.charAt(0) <= '9')// Ӧ�á�
							                    {
							                        //GoodsBO.removeRefContentFromCategory(refID);//remve by aiyan 2013-04-18
							                        
							                    	//modified by aiyan
							                        if(transactionID!=null){
							                        	GoodsBO.removeRefContentFromCategory(rVo.getId(),transactionID);
							                        }else{
							                        	GoodsBO.removeRefContentFromCategory(rVo.getId());
							                        }
							                    }
							                    else
							                    {
							                        category.delNode(refNode);
							                        category.saveNode();
							                    }
											}else{
												
												refNode.setVerifyStatus(refNode.getDelFlag());
												refNode.setDelFlag("0");
												refNode.save();
											}
											
										}else{
											if (transactionID != null) {
												try {
													SSMSDAO.getInstance().addMessages(
															MSGType.RefModifyReq,
															transactionID
															// ,refNode.getGoodsID()+":"+refNode.getCategoryID()+":"+cateid+":"+refNode.getRefNodeID()+":"+refNode.getSortID()+":"+modifyStr(refNode.getLoadDate())+":0"
															// ���sortid�ĵط����������⡣modify by aiyan 2013-07-02
															,
															refNode.getGoodsID() + ":"
																	+ refNode.getCategoryID() + ":"
																	+ category.getId() + ":"
																	+ refNode.getRefNodeID() + ":"
																	+ refNode.getSortID() + ":"
																	+ modifyStr(refNode.getLoadDate())
																	+ ":0",
															Constant.MESSAGE_HANDLE_STATUS_INIT);
												} catch (DAOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}
									}
								}
							}

						}

						// �޸�������
						CategoryOperationDAO.getInstance().operationCategory(
								category.getCategoryID(), status, operation,
								logUser.getName());

						try {
							categoryIdAndPath = "&nbsp;&nbsp;&nbsp;&nbsp;����Id��"
									+ category.getCategoryID()
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����"
									+ CheckLogDAO.getInstance().getCategoryPath(category.getCategoryID())
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;";
						} catch (DAOException e) {
							logger.debug("��ȡ·���쳣");
						}

					}
					
					String mailTitle = "";
					String mailContent = "";
					String operationtype = "";
					if ("2".equals(status)) {
						mailTitle = "��������ͻ����Ż���Ӫ���ݵ�������";
						mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
								+ categoryIdAndPath
								+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��"
								+ "�ύ����";
						operationtype = "������Ʒ�����ύ����";
					} else if ("1".equals(status)) {
						mailTitle = "�����ѷ������ͻ����Ż���Ӫ�����ѷ���";
						mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
								+ categoryIdAndPath;
						operationtype = "������Ʒ������������";
					} else if ("3".equals(status)) {

						mailTitle = "��˲��أ��ͻ����Ż���Ӫ������������";
						mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�"
								+ logUser.getName()
								+ "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
								+ categoryIdAndPath;
						operationtype = "������Ʒ����������ͨ��";
					}
					map.put("status", status);
					map.put("mailTitle", mailTitle);
					map.put("mailContent", mailContent);
					map.put("categoryId", category.getCategoryID());
					map.put("operationtype", operationtype);
					map.put("operationobjtype", "����Id��" + category.getCategoryID());
					map.put("operator", logUser.getName());

					WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
							map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
		}
		
	}

	/**
	 * �����������ܷ��࣬д��־�����ʼ���
	 * 
	 * @param request
	 * @param categoryId
	 *            ���ܱ���
	 * @param status
	 *            ����״̬
	 * @param operation
	 *            ����ʽ: 1 ���ܷ������; 2 ������Ʒ����
	 * @param map
	 *            �����־��Ϣ mailTitle:�ʼ����� ; mailContent:�ʼ�����;status:����״̬ 0���༭ 1������
	 *            3����˲�ͨ��;categoryId:���ܱ���;operation:����ʽ
	 *            00��Ĭ�ϣ�10:MM���ܣ�20�����ݺ�������30
	 *            ��������Ϸ���ܣ�40�������Ķ����ܣ�50���������ֻ��ܣ�60��������Ƶ���ܣ�70�����ض�������;
	 *            operationtype:
	 *            ��������;operator:������;operationobj:��������;operationobjtype:������������;
	 * 
	 */
	public void operateInfoforCategory(HttpServletRequest request,
			String categoryIds, String status, String operation,
			Map<String, Object> map) throws BOException {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		String categoryIdAndPath = "";
		String categoryIdArray[] = categoryIds.split(",");
		if (categoryIdArray != null && categoryIdArray.length > 0) {
			for (int i = 0; i < categoryIdArray.length; i++) {
				String categoryId = categoryIdArray[i];
				if (categoryId != null && !"".equals(categoryId)) {
					// ��ȡ������Ϣ
					Category category = (Category) Repository.getInstance()
							.getNode(categoryId,
									RepositoryConstants.TYPE_CATEGORY);
					if (category != null) {
						// �޸Ļ��ܱ��ϵ�����״̬
						if ("1".equals(operation)) {
							category.setClassifyStatus(status);
							// ���������[ɾ������]��ͨ������Ҫ�����ܵ�ɾ����ʾ��Ϊ������
							if ("3".equals(status)
									&& category.getDelFlag() == 1) {
								category.setDelFlag(0);
								//���ɾ��ǰ�Ǳ༭״̬����������ͨ����Ҫ�ָ�Ϊ�༭״̬������ֱ�Ӹ�Ϊ�ѷ�����
								if("0".equals(category.getDelproStatus())){
									category.setClassifyStatus("0");
								}else{
									category.setClassifyStatus("1");
								}
								
							}
							// ����
							category.save();
						}
						map.put("categoryId", category.getCategoryID());
						map.put("operationobjtype", "����Id��" + category.getCategoryID());
						// ��������ͨ��������Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
						if ("1".equals(status) && "1".equals(operation)) {
							// ����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
							ModuleConfig module = ConfigFactory
									.getSystemConfig().getModuleConfig(
											"syncAndroid");
							String rootCategoryId = module
									.getItemValue("ROOT_CATEGORYID");
							String operateCategoryId = module
									.getItemValue("OPERATE_ROOT_CATEGORYID");// ������Ʒ���Ż��������µ���Ӫ����
							if (SSMSDAO.isAndroidCategory(
									category.getId(), rootCategoryId)
									|| SSMSDAO.isOperateCategory(
													category.getId(),
													operateCategoryId)) {// modify
								// by
								// aiyan
								// 2013-05-18
								// ){
								// Catogoryid ���� String ����ID
								// Action ���� String 0���½�
								// 1������������Ϣ�����������չ�ֶΣ�
								// 9��ɾ��
								// Transactionid ���� String ��������ID�����ڴ���ͬһ��������ʱʹ�á�

								try {
									SSMSDAO
											.getInstance()
											.addMessages(
													MSGType.CatogoryModifyReq,
													category.getId()
															+ (category
																	.getDelFlag() == 1 ? ":9"
																	: ":1"));
								} catch (DAOException e) {
									// TODO Auto-generated catch block
									logger.error("���ܼ�¼��Ϣʱ��������ID:"
											+ category.getId(), e);
								}
							}
						}

						// �޸�������
						CategoryOperationDAO.getInstance().operationCategory(
								category.getCategoryID(), status, operation,
								logUser.getName());

						try {
							categoryIdAndPath = "&nbsp;&nbsp;&nbsp;&nbsp;����Id��"
									+ category.getCategoryID()
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;·����"
									+ CheckLogDAO.getInstance().getCategoryPath(category.getCategoryID())
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;";
						} catch (DAOException e) {
							logger.debug("��ȡ·���쳣");
							e.printStackTrace();
						}

						String mailTitle = "";
						String mailContent = "";
						String operationtype = "";
						if ("2".equals(status)) {
							mailTitle = "��������ͻ����Ż���Ӫ���ݵ�������";
							mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѱ༭��ɣ������ϵͳ������<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
									+ categoryIdAndPath
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;�����ʽ��"
									+ "�ύ����";
							operationtype = "���ܷ�������ύ����";
						} else if ("1".equals(status)) {
							mailTitle = "�����ѷ������ͻ����Ż���Ӫ�����ѷ���";
							mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;���������ѷ�������ע����֤��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
									+ categoryIdAndPath;
							operationtype = "���ܷ��������������";
						} else if ("3".equals(status)) {

							mailTitle = "��˲��أ��ͻ����Ż���Ӫ������������";
							mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������ݱ�"
									+ logUser.getName()
									+ "���أ������ϵͳ���±༭��<br><br>&nbsp;&nbsp;&nbsp;&nbsp;�������󣺻���;<br>"
									+ categoryIdAndPath;
							operationtype = "���ܷ������������ͨ��";
						}
						map.put("status", status);
						map.put("mailTitle", mailTitle);
						map.put("mailContent", mailContent);
						
						map.put("operationtype", operationtype);
						
						map.put("operator", logUser.getName());

						WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
								map);
						DaemonTaskRunner.getInstance().addTask(task);
					}
				}
			}
		}
		
	}

	/**
	 * 
	 * @param request
	 * @param dealContent
	 *            ������Ʒ���id#Ӧ����Դid
	 * @param operationType
	 *            ����״̬
	 * @param type
	 *            ����ʽ: 1 ���ܷ������; 2 ������Ʒ����
	 * @param map
	 *            �����־��Ϣ mailTitle:�ʼ����� ; mailContent:�ʼ�����;status:����״̬ 0���༭ 1������
	 *            3����˲�ͨ��;categoryId:���ܱ���;operation:����ʽ
	 *            00��Ĭ�ϣ�10:MM���ܣ�20�����ݺ�������30
	 *            ��������Ϸ���ܣ�40�������Ķ����ܣ�50���������ֻ��ܣ�60��������Ƶ���ܣ�70�����ض�������;
	 *            operationtype:
	 *            ��������;operator:������;operationobj:��������;operationobjtype:������������;
	 * @throws BOException
	 * 
	 */
	public void operateItemContentInfo(HttpServletRequest request, String[] dealContent,
			String operationType, String type, Map<String, Object> map) throws BOException  {
		UserVO logUser = null;
		UserSessionVO userSessionVO = UserManagerBO.getInstance()
				.getUserSessionVO(request.getSession());

		if (userSessionVO != null) {
			logUser = userSessionVO.getUser();
		} else {
			logUser = new UserVO();
			logUser.setName("unknow");
		}
		CategoryOperationDAO.getInstance().operationCategoryGoods((Category)map.get("category"), dealContent, logUser.getName(), operationType);
		map.put("operator", logUser.getName());
		WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
		DaemonTaskRunner.getInstance().addTask(task);
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

	private String modifyStr(String dateStr) {
		if (dateStr == null) {
			return "";
		}
		return dateStr.replaceAll(" ", "").replaceAll("-", "").replaceAll(":",
				"");
	}
}
