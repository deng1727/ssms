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
			throw new BOException("分页查询货架锁定列表异常");
		}
	}
	/**
	 * 分页查询货架商品锁定列表
	 * 
	 * @param page
	 * @param map
	 * @throws DAOException
	 */
	public void queryLockRefList(PageResult page, Map map)throws BOException{
		try {
			LockLocationDAO.getInstance().queryLockRefList(page, map);
		} catch (DAOException e) {
			throw new BOException("分页查询货架商品锁定列表异常");
		}
	}
	public void queryContentList(PageResult page, Map map)
	throws BOException {
		try {
			LockLocationDAO.getInstance().queryContentList(page, map);
		} catch (DAOException e) {
			throw new BOException("分页查询所有的应用列表异常");
		}
	}
	public List<RefrenceVO> queryLockList(String categoryId)throws BOException{
		try {
			return LockLocationDAO.getInstance().queryLockList(categoryId);
		} catch (Exception e) {
			throw new BOException("查询"+categoryId+"的锁定商品异常");
		}
	}
	/**
	 * 查询货架的所有锁定位置
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
			
	        //这里加上商品库优化，如果是商品库优化为根货架的货架，则要放消息进去。
	        //这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");//商品库优化根货架
			String operateategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
			 if(SSMSDAO.isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息 
				||SSMSDAO.isOperateCategory(category.getId(), operateategoryId)){
					 transactionID = ContextUtil.getTransactionID();         
			 }
			//获取该货架中的最小排序号
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
             
             //调用存储过程
             LockLocationDAO.getInstance().callProcedureFixSortid(category.getCategoryID(),rId,refrenceVO.getLockNum(),Constant.OP_UPDATE);
		} catch (Exception e) {
			throw new BOException(e.getMessage());
		}
	}
	/**
	 * 下线货架下的商品
	 * @param categoryID	货架ID
	 * @param refIDs	需要下线的商品ID
	 * @return	被下线的商品排序号
	 * @throws BOException
	 */
	public void removeContent(String cID, String[] refIDs,String removeType) throws BOException{
		Category category = null;
		try {
			// 从目标分类下移除
			category = (Category) Repository.getInstance().getNode(cID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (Exception e) {
			throw new BOException(cID+"获取货架信息异常");
		}
		try {
			boolean sendMsg=false;
			//发送下架消息
			ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
			String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");//商品库优化根货架
			String operateategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
			 if(SSMSDAO.isAndroidCategory(category.getId(), rootCategoryId)
				//||operateCategoryId.indexOf(category.getId())!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息 
				||SSMSDAO.isOperateCategory(category.getId(), operateategoryId)){
				 sendMsg = true;
			 }
			if(category!=null){
				if(refIDs!=null&&refIDs.length>0){
					for(String rId:refIDs){
						//调用存储过程执行下线。并获取返回结果
						String result = LockLocationDAO.getInstance().callProcedureFixSortid(category.getCategoryID(), rId, -1, Constant.OP_DEL);
						//发送下架消息
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
