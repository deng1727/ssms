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
	 * 日志引用
	 */
	protected static final JLogger logger = LoggerFactory
			.getLogger(CategoryOperationBO.class);

	private static CategoryOperationBO bo = new CategoryOperationBO();

	private CategoryOperationBO() {

	}

	/**
	 * 获取实例
	 * 
	 * @return 实例
	 */
	public static CategoryOperationBO getInstance() {

		return bo;
	}

	/**
	 * 
	 * @param request
	 * @param category
	 *            货架
	 * @param operationType
	 *            审批状态
	 * @param type
	 *            处理方式: 1 货架分类管理; 2 货架商品管理
	 * @param map
	 *            添加日志信息 mailTitle:邮件主题 ; mailContent:邮件内容;status:处理状态 0：编辑 1：发布
	 *            3：审核不通过;categoryId:货架编码;operation:处理方式
	 *            00：默认，10:MM货架，20：内容黑名单，30
	 *            ：基地游戏货架，40：基地阅读货架，50：基地音乐货架，60：基地视频货架，70：基地动漫货架;
	 *            operationtype:
	 *            操作类型;operator:操作人;operationobj:操作对象;operationobjtype:操作对象类型;
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
	 * 批量审批货架商品，写日志、发邮件。
	 * 
	 * @param request
	 * @param categoryId
	 *            货架编码
	 * @param status
	 *            审批状态
	 * @param operation
	 *            处理方式: 1 货架分类管理; 2 货架商品管理
	 * @param map
	 *            添加日志信息 mailTitle:邮件主题 ; mailContent:邮件内容;status:处理状态 0：编辑 1：发布
	 *            3：审核不通过;categoryId:货架编码;operation:处理方式
	 *            00：默认，10:MM货架，20：内容黑名单，30
	 *            ：基地游戏货架，40：基地阅读货架，50：基地音乐货架，60：基地视频货架，70：基地动漫货架;
	 *            operationtype:
	 *            操作类型;operator:操作人;operationobj:操作对象;operationobjtype:操作对象类型;
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
					// 获取分类信息
					Category category = (Category) Repository.getInstance()
							.getNode(categoryId,
									RepositoryConstants.TYPE_CATEGORY);
					if (category != null) {
						String statusFlag = status;
						// 获取该货架下的所有商品
						List<ReferenceVO> list = CategoryOperationDAO
								.getInstance()
								.getReferenceListByCategoryId(
										category.getCategoryID());
						if("3".equals(status)){//在包含有删除的审批不通过时，将货架商品审批状态恢复为编辑
							if (list != null && list.size() > 0) {
								for (ReferenceVO rVo : list) {
									if("3".equals(rVo.getDelFlag())){
										statusFlag = "0";
									}
								}
							}
						}
						
						category.setGoodsStatus(statusFlag);
						
						// 更新
						category.save();
						// 货架商品管理审批通过或不通过的时候，同时要审批其货架下的商品
						if ("1".equals(status) || "3".equals(status)) {
							String flag = "3".equals(status) ? "2" : status;
							CategoryOperationDAO.getInstance()
									.verifyRefrenceByCategoryId(
											category.getCategoryID(), flag);// 根据货架ID批量审批商品表
							//sql=update T_R_REFERENCE r set r.verify_status = ? ,r.verify_date = sysdate where  r.categoryid = ?  and r.verify_status = 0
							if (list != null && list.size() > 0) {
								for (ReferenceVO rVo : list) {

									// 货架商品审批通过，动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
									if ("1".equals(status)) {
										String transactionID = null;

										// 这里加上商品库优化，如果是商品库优化为根货架的货架，则要放消息进去。
										// 这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
										ModuleConfig module = ConfigFactory
												.getSystemConfig()
												.getModuleConfig("syncAndroid");
										String rootCategoryId = module
												.getItemValue("ROOT_CATEGORYID");
										String operateCategoryId = module
												.getItemValue("OPERATE_ROOT_CATEGORYID");// 不在商品库优化根货架下的运营货架
										if (SSMSDAO.isAndroidCategory(
														category.getId(),
														rootCategoryId)
												// ||operateCategoryId.indexOf(cateid)!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息
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
											//审批通过
											if(Integer.parseInt(status) == 1){
												if (contId.charAt(0) >= '0' && contId.charAt(0) <= '9')// 应用。
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
															// 这个sortid的地方设置有问题。modify by aiyan 2013-07-02
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

						// 修改审批表
						CategoryOperationDAO.getInstance().operationCategory(
								category.getCategoryID(), status, operation,
								logUser.getName());

						try {
							categoryIdAndPath = "&nbsp;&nbsp;&nbsp;&nbsp;货架Id："
									+ category.getCategoryID()
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径："
									+ CheckLogDAO.getInstance().getCategoryPath(category.getCategoryID())
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;";
						} catch (DAOException e) {
							logger.debug("获取路径异常");
						}

					}
					
					String mailTitle = "";
					String mailContent = "";
					String operationtype = "";
					if ("2".equals(status)) {
						mailTitle = "待审事项：客户端门户运营内容调整审批";
						mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
								+ categoryIdAndPath
								+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式："
								+ "提交审批";
						operationtype = "货架商品管理提交审批";
					} else if ("1".equals(status)) {
						mailTitle = "数据已发布：客户端门户运营内容已发布";
						mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
								+ categoryIdAndPath;
						operationtype = "货架商品管理审批发布";
					} else if ("3".equals(status)) {

						mailTitle = "审核驳回：客户端门户运营内容审批驳回";
						mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被"
								+ logUser.getName()
								+ "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
								+ categoryIdAndPath;
						operationtype = "货架商品管理审批不通过";
					}
					map.put("status", status);
					map.put("mailTitle", mailTitle);
					map.put("mailContent", mailContent);
					map.put("categoryId", category.getCategoryID());
					map.put("operationtype", operationtype);
					map.put("operationobjtype", "货架Id：" + category.getCategoryID());
					map.put("operator", logUser.getName());

					WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
							map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
		}
		
	}

	/**
	 * 批量审批货架分类，写日志、发邮件。
	 * 
	 * @param request
	 * @param categoryId
	 *            货架编码
	 * @param status
	 *            审批状态
	 * @param operation
	 *            处理方式: 1 货架分类管理; 2 货架商品管理
	 * @param map
	 *            添加日志信息 mailTitle:邮件主题 ; mailContent:邮件内容;status:处理状态 0：编辑 1：发布
	 *            3：审核不通过;categoryId:货架编码;operation:处理方式
	 *            00：默认，10:MM货架，20：内容黑名单，30
	 *            ：基地游戏货架，40：基地阅读货架，50：基地音乐货架，60：基地视频货架，70：基地动漫货架;
	 *            operationtype:
	 *            操作类型;operator:操作人;operationobj:操作对象;operationobjtype:操作对象类型;
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
					// 获取分类信息
					Category category = (Category) Repository.getInstance()
							.getNode(categoryId,
									RepositoryConstants.TYPE_CATEGORY);
					if (category != null) {
						// 修改货架表上的审批状态
						if ("1".equals(operation)) {
							category.setClassifyStatus(status);
							// 如果是审批[删除货架]不通过，则要将货架的删除表示改为正常。
							if ("3".equals(status)
									&& category.getDelFlag() == 1) {
								category.setDelFlag(0);
								//如果删除前是编辑状态，则审批不通过后要恢复为编辑状态。否则直接改为已发布。
								if("0".equals(category.getDelproStatus())){
									category.setClassifyStatus("0");
								}else{
									category.setClassifyStatus("1");
								}
								
							}
							// 更新
							category.save();
						}
						map.put("categoryId", category.getCategoryID());
						map.put("operationobjtype", "货架Id：" + category.getCategoryID());
						// 货架审批通过，动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
						if ("1".equals(status) && "1".equals(operation)) {
							// 这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
							ModuleConfig module = ConfigFactory
									.getSystemConfig().getModuleConfig(
											"syncAndroid");
							String rootCategoryId = module
									.getItemValue("ROOT_CATEGORYID");
							String operateCategoryId = module
									.getItemValue("OPERATE_ROOT_CATEGORYID");// 不在商品库优化根货架下的运营货架
							if (SSMSDAO.isAndroidCategory(
									category.getId(), rootCategoryId)
									|| SSMSDAO.isOperateCategory(
													category.getId(),
													operateCategoryId)) {// modify
								// by
								// aiyan
								// 2013-05-18
								// ){
								// Catogoryid 必须 String 货架ID
								// Action 必须 String 0：新建
								// 1：货架描述信息变更（包含扩展字段）
								// 9：删除
								// Transactionid 必须 String 事务序列ID，用于处理同一事务请求时使用。

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
									logger.error("货架记录消息时出错，货架ID:"
											+ category.getId(), e);
								}
							}
						}

						// 修改审批表
						CategoryOperationDAO.getInstance().operationCategory(
								category.getCategoryID(), status, operation,
								logUser.getName());

						try {
							categoryIdAndPath = "&nbsp;&nbsp;&nbsp;&nbsp;货架Id："
									+ category.getCategoryID()
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径："
									+ CheckLogDAO.getInstance().getCategoryPath(category.getCategoryID())
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;";
						} catch (DAOException e) {
							logger.debug("获取路径异常");
							e.printStackTrace();
						}

						String mailTitle = "";
						String mailContent = "";
						String operationtype = "";
						if ("2".equals(status)) {
							mailTitle = "待审事项：客户端门户运营内容调整审批";
							mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
									+ categoryIdAndPath
									+ ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式："
									+ "提交审批";
							operationtype = "货架分类管理提交审批";
						} else if ("1".equals(status)) {
							mailTitle = "数据已发布：客户端门户运营内容已发布";
							mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
									+ categoryIdAndPath;
							operationtype = "货架分类管理审批发布";
						} else if ("3".equals(status)) {

							mailTitle = "审核驳回：客户端门户运营内容审批驳回";
							mailContent = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被"
									+ logUser.getName()
									+ "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>"
									+ categoryIdAndPath;
							operationtype = "货架分类管理审批不通过";
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
	 *            货架商品表的id#应用资源id
	 * @param operationType
	 *            审批状态
	 * @param type
	 *            处理方式: 1 货架分类管理; 2 货架商品管理
	 * @param map
	 *            添加日志信息 mailTitle:邮件主题 ; mailContent:邮件内容;status:处理状态 0：编辑 1：发布
	 *            3：审核不通过;categoryId:货架编码;operation:处理方式
	 *            00：默认，10:MM货架，20：内容黑名单，30
	 *            ：基地游戏货架，40：基地阅读货架，50：基地音乐货架，60：基地视频货架，70：基地动漫货架;
	 *            operationtype:
	 *            操作类型;operator:操作人;operationobj:操作对象;operationobjtype:操作对象类型;
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
