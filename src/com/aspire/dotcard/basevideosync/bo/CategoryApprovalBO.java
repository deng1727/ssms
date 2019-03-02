
package com.aspire.dotcard.basevideosync.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basevideosync.WriteActionLogAndEmailTask;
import com.aspire.dotcard.basevideosync.dao.CategoryApprovalDAO;
import com.aspire.dotcard.basevideosync.dao.CategoryDAO;
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
	 * 视频货架审批发布
	 * 
	 * @param request
	 * @param categoryId 视频货架编码
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

		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			String categoryIdArray[] = categoryId.split(",");
				for (int i = 0; i < categoryIdArray.length; i++) {
				Map<String,Object> itemMap = CategoryApprovalDAO.getInstance().queryCategoryListItem(categoryIdArray[i]);
//				map.put("categoryId", rs.getString("categoryid"));
//				map.put("goods_status", rs.getString("goods_status"));
//				map.put("delpro_status", rs.getInt("delpro_status")+"");
//				map.put("video_status", rs.getString("video_status"));
				if(itemMap != null){
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" /*+ SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 5)*/;
					if(!"2".equals(itemMap.get("delpro_status"))){
						//delpro_status不为2(2为正常)则说明这条数据已经被删除(逻辑删除,非物理删除)
						// 用于删除指定货架
			            NewVideoCategoryBO.getInstance().delNewVideoCategory(tdb,categoryIdArray[i]);
			          //sql:update t_v_category c set c.delflag=1 where c.categoryid=?
			            //delflag为一为删除状态,为零为未删除状态
			            
			            CategoryApprovalDAO.getInstance().categoryApprovalPass(tdb, "1",categoryIdArray[i]);
			          //sql:update t_v_category c set c.video_status = ?,c.LUPDATE=sysdate,c.DELPRO_STATUS=2
					  //where c.categoryid = ?
					}else{
						CategoryApprovalDAO.getInstance().categoryApprovalPass(tdb, "1",categoryIdArray[i]);
						//sql:update t_v_category c set c.video_status = ?,c.LUPDATE=sysdate,c.DELPRO_STATUS=2 where c.categoryid = ?
					}
					CategoryDAO.getInstance().approvalCategory(tdb, categoryIdArray[i], "1", "1", logUser.getName());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailTitle", "数据已发布：客户端门户运营内容已发布");
					//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：";
					map.put("mailContent", value);
					map.put("status", "1");
					map.put("categoryId", categoryIdArray[i]);
					map.put("operation", "60");
					map.put("operationtype", "视频货架管理审批发布");
					map.put("operationobj", "视频货架管理");
					map.put("operationobjtype", "视频货架Id：" + categoryIdArray[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("视频审批发布发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("视频货架审批发布发生异常:", e);
		} catch (Exception e) {
			LOGGER.error("视频货架审批发布发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("视频货架审批发布发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
		
	}
	/**
	 * 视频货架审批不通过
	 * 
	 * @param request
	 * @param categoryId 视频货架编码
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

		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			String categoryIdArray[] = categoryId.split(",");
			for (int i = 0; i < categoryIdArray.length; i++) {
				Map<String, Object> itemMap = CategoryApprovalDAO.getInstance()
						.queryCategoryListItem(categoryIdArray[i]);
				if (itemMap != null) {
					if (!"2".equals(itemMap.get("delpro_status"))) {
						//delpro_status不为2(2为正常)则说明这条数据已经被删除(逻辑删除,非物理删除)
						CategoryApprovalDAO.getInstance().deleteNoPass(tdb,
								categoryIdArray[i]);
						//sql:update t_v_category c set c.video_status = c.delpro_status,c.delpro_status = 2 ,c.LUPDDATE=sysdate
						//where c.categoryid = ?
					} else {
						CategoryApprovalDAO.getInstance().categoryApprovalPass(
								tdb, "3",categoryIdArray[i]);
						//sql:update t_v_category c set c.video_status = ?,c.LUPDDATE=sysdate,c.DELPRO_STATUS=2
						//where c.categoryid = ?
					}
					CategoryDAO.getInstance().approvalCategory(tdb,
							categoryIdArray[i], "3", "1", logUser.getName());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("mailTitle", "审核驳回：客户端门户运营内容审批驳回");
					//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  this.getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" ;
					String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  this.getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 5);
					map.put("mailContent", value);
					map.put("status", "3");
					map.put("categoryId", categoryIdArray[i]);
					map.put("operation", "60");
					map.put("operationtype", "视频货架管理审批不通过");
					map.put("operationobj", "视频货架管理");
					map.put("operationobjtype", "视频货架Id：" + categoryIdArray[i]);
					map.put("operator", logUser.getName());
					WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
							map);
					DaemonTaskRunner.getInstance().addTask(task);
				}
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("视频货架审批不通过发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("视频货架审批不通过发生异常:", e);
		} catch (Exception e) {
			LOGGER.error("视频货架审批不通过发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("视频货架审批不通过发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	/**
	 * 
	 * @param request
	 * @return用户信息
	 */
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
	 * 查询视频货架
	 * 
	 * @param categoryId 货架编号
	 * @return
	 * @throws BOException
	 */
	public Map<String, Object> queryCategoryListItem(String categoryId)
			throws BOException {
		return CategoryApprovalDAO.getInstance().queryCategoryListItem(categoryId);
	}
    /**
     * POMS商品批量审批发布
     * 
     * @param request
     * @param categoryId POMS货架编码
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

		// 进行事务操作
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
							CategoryApprovalDAO.getInstance().deleteCategoryGoodsItem(tdb, itemMap.get("id").toString(),categoryId);
						}else{
							CategoryApprovalDAO.getInstance().categoryGoodsAllApproval(tdb, categoryIdArray[i], "1", "2");
						}
					}
				}
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryIdArray[i], "1");
				CategoryDAO.getInstance().approvalCategory(tdb, categoryIdArray[i], "1", "2", logUser.getName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mailTitle", "数据已发布：客户端门户运营内容已发布");
				//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：";
				String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 5);
				map.put("mailContent", value);
				map.put("status", "1");
				map.put("categoryId", categoryIdArray[i]);
				map.put("operation", "60");
				map.put("operationtype", "POMS商品管理审批发布");
				map.put("operationobj", "POMS商品管理");
				map.put("operationobjtype", "POMS商品货架Id：" + categoryIdArray[i]);
				map.put("operator", logUser.getName());
				WriteActionLogAndEmailTask task = new  WriteActionLogAndEmailTask(map);
				DaemonTaskRunner.getInstance().addTask(task);
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("POMS商品批量审批发布发布异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("视频商品批量审批发布发布异常:", e);
		} catch (Exception e) {
			LOGGER.error("POMS商品批量审批发布发布异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("POMS商品批量审批发布发布异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
    }
    /**
     * POMS商品批量审批不通过
     * @param request
     * @param categoryId POMS货架编码
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

		// 进行事务操作
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
							CategoryApprovalDAO.getInstance().callBackCategoryGoodsItem(tdb,itemMap.get("id").toString(),categoryId);
						} else {
							CategoryApprovalDAO.getInstance().categoryGoodsAllApproval(tdb, categoryIdArray[i], "3", "2");
						}
					}
				}
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryIdArray[i], "3");
				CategoryDAO.getInstance().approvalCategory(tdb,
						categoryIdArray[i], "3", "2", logUser.getName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mailTitle", "审核驳回：客户端门户运营内容审批驳回");
				//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  this.getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" ;
				String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  this.getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryIdArray[i] + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" +SEQCategoryUtil.getInstance().getPathByCategoryId(categoryIdArray[i], 5);
				map.put("mailContent", value);
				map.put("status", "3");
				map.put("categoryId", categoryIdArray[i]);
				map.put("operation", "60");
				map.put("operationtype", "POMS商品管理审批不通过");
				map.put("operationobj", "POMS商品管理");
				map.put("operationobjtype", "POMS货架Id：" + categoryIdArray[i]);
				map.put("operator", logUser.getName());
				WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(
						map);
				DaemonTaskRunner.getInstance().addTask(task);
			}
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("POMS商品批量审批不通过发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("POMS商品批量审批不通过发生异常:", e);
		} catch (Exception e) {
			LOGGER.error("POMS商品批量审批不通过发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("POMS商品批量审批不通过发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
    }
    

	/**
	 * POMS商品管理提交审批
	 * 
	 * @param request
	 * @param categoryId POMS货架编号
	 * @param approval  是批量：part：部分；all：全部
	 * @param dealRef  POMS商品表中的Id+verify_status
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

		// 进行事务操作
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
			map.put("mailTitle", "待审事项：客户端门户运营内容调整审批");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：POMS商品管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：;<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已编辑完成，请进入系统审批：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：POMS商品管理;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5) + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;变更方式：" + "提交审批";
			map.put("mailContent", value);
			map.put("status", "0");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "POMS商品管理提交审批");
			map.put("operationobj", "POMS商品管理");
			map.put("operationobjtype", "POMS商品货架Id：" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);			
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("POMS商品管理提交审批发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("POMS商品管理提交审批发生异常:", e);
		} catch (Exception e) {
			LOGGER.error("POMS商品管理提交审批发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("POMS商品管理提交审批发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	/**
	 * 单条POMS商品审批发布
	 * 
	 * @param request
	 * @param categoryId POMS货架编号
	 * @param dealRef  POMS商品表中的Id+verify_status
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

		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			CategoryApprovalDAO.getInstance().categoryGoodsApproval(tdb, dealRef,"1", categoryId);
			if(!(CategoryApprovalDAO.getInstance().isApproval(categoryId, "2") || CategoryApprovalDAO.getInstance().isApproval(categoryId, "0"))){
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryId, "1");
			}
			CategoryDAO.getInstance().approvalCategory(tdb,categoryId, "1", "2", logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "数据已发布：客户端门户运营内容已发布");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：";
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容已发布，请注意验证：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5);
			map.put("mailContent", value);
			map.put("status", "1");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "POMS货架管理审批发布");
			map.put("operationobj", "POMS货架管理");
			map.put("operationobjtype", "POMS商品货架Id：" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);			
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("单条POMS商品审批发布发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("单条POMS商品审批发布发生异常:", e);
		} catch (Exception e) {
			LOGGER.error("单条POMS商品审批发布发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("单条POMS商品审批发布发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}
	/**
	 * 单条POMS商品审批不通过
	 * @param request
	 * @param categoryId POMS货架编号
	 * @param dealRef  POMS商品表中的Id+verify_status
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

		// 进行事务操作
		TransactionDB tdb = null;
		try {
			tdb = TransactionDB.getInstance();
			CategoryApprovalDAO.getInstance().categoryGoodsApproval(tdb, dealRef,"3", categoryId);
			if(!(CategoryApprovalDAO.getInstance().isApproval(categoryId, "2") || CategoryApprovalDAO.getInstance().isApproval(categoryId, "0"))){
				CategoryApprovalDAO.getInstance().categoryApproval(tdb, categoryId, "3");
			}
			CategoryDAO.getInstance().approvalCategory(tdb,categoryId, "3", "2", logUser.getName());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mailTitle", "审核驳回：客户端门户运营内容审批驳回");
			//String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  this.getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" ;
			String value = "<br><br>&nbsp;&nbsp;&nbsp;&nbsp;以下内容被" +  this.getUserInfo(request) + "驳回，请进入系统重新编辑：<br><br>&nbsp;&nbsp;&nbsp;&nbsp;审批对象：货架;<br>" + "&nbsp;&nbsp;&nbsp;&nbsp;货架Id：" + categoryId + ";<br>&nbsp;&nbsp;&nbsp;&nbsp;路径：" + SEQCategoryUtil.getInstance().getPathByCategoryId(categoryId, 5);
			map.put("mailContent", value);
			map.put("status", "3");
			map.put("categoryId", categoryId);
			map.put("operation", "60");
			map.put("operationtype", "POMS商品管理审批不通过");
			map.put("operationobj", "POMS商品管理");
			map.put("operationobjtype", "POMS商品货架Id：" + categoryId);
			map.put("operator", logUser.getName());
			WriteActionLogAndEmailTask task = new WriteActionLogAndEmailTask(map);
			DaemonTaskRunner.getInstance().addTask(task);			
			tdb.commit();
		} catch (BOException e) {
			LOGGER.error("单条POMS商品审批不通过发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("单条POMS商品审批不通过发生异常:", e);
		} catch (Exception e) {
			LOGGER.error("单条POMS商品审批不通过发生异常:", e);
			// 执行回滚
			tdb.rollback();
			throw new BOException("单条POMS商品审批不通过发生异常:", e);
		} finally {
			if (tdb != null) {
				tdb.close();
			}
		}
	}

}
