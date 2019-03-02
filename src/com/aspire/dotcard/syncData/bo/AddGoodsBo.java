package com.aspire.dotcard.syncData.bo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.dao.AddGoodsDAO;
import com.aspire.dotcard.syncData.tactic.TacticDAO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.ponaadmin.web.mail.Mail;
import com.aspire.ponaadmin.web.mail.MailConfig;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryDeviceDAO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;

public class AddGoodsBo {

	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(AddGoodsBo.class);

	/**
	 * 记录同步应用记录
	 */
	protected static JLogger recordLog = LoggerFactory
			.getLogger("AddGoodsBoLog");

	private static AddGoodsBo instance = new AddGoodsBo();

	/**
	 * 得到单例模式
	 * 
	 */
	public static AddGoodsBo getInstance() {

		return instance;
	}

	@SuppressWarnings("unchecked")
	public void goodsServices(StringBuffer mailContent) {

		List<String> categoryids = AddGoodsDAO.getInstance().getCategoryId();
		Set<String> errorSet=new HashSet<String>();
		int toNum = 0 ;
		
		if(categoryids!=null&&categoryids.size()>0)
		{
	 		for (String categoryID : categoryids) {
				try {
					toNum +=exeTactic(categoryID ,errorSet);
				} catch (Exception e) {
					logger.error("货架categoryID的商品上架失败", e);
				}
			}
		}
		TacticDAO tDao = new TacticDAO();
		int errorNum=0;
		if (!errorSet.isEmpty()) {
			errorNum=errorSet.size();
		}
		mailContent.append("<p>本次同步总共处理"+ toNum+"个应用，其中成功上架应用"+(toNum-errorNum)+"个，上架失败"+ errorNum +"个。<br>");
		if (!errorSet.isEmpty()) {
			String[] errors = (String[]) errorSet.toArray();
			int maxnum=errorNum>100?100:errorNum;
			for (int i = 0; i < maxnum; i++) {
				String contentid = errors[i];
				logger.debug("contentid====================" + contentid);
				String contentname = tDao.getContentName(contentid);
				logger.debug("contentname==================="+ contentname);
				mailContent.append("<p>应用上架失败的信息为:<br>");
				mailContent.append(i + ")内容编码" + contentid +",名称: " +contentname +"<br>");
				if(i==99){
					mailContent.append("<p> …… <>br  ");
				}
			}
		}
		
		
		
//		if (errorSet.size() > 0 || errorSet.size()<100) {
//			
//			for (Iterator iterator = errorSet.iterator(); iterator.hasNext();) {
//				String contentid = (String) iterator.next();
//				String contentname = tDao.getContentName(contentid);
//				int errorNum = 1;
//				mailContent.append("<p>应用上架失败的信息为:<br>");
//				mailContent.append((errorNum++) + ")内容编码" + contentid +",名称: " +contentname +"<br>");
//			}
//		}
	}

	

	private int exeTactic(String categoryID ,Set<String> errorSet) throws BOException {
		Category cate = CategoryBO.getInstance().getCategory(categoryID);
		// 根據categoryid查询出TacticVO
		List tacticList = queryByCategoryID(categoryID);
		List contList = null;
		List refList = null;

		// 下架商品
		downGood(categoryID);

		// 根据同步策略获得上架商品列表
		contList = categoryTactic(tacticList, cate);

		// 如果是机型货架筛选上架商品
		if (1 == cate.getDeviceCategory()) {
			refList = deviceCategoryTactic(contList, cate);
		} else {
			refList = deviceCategoryTactic(contList);
		}
		
		// 上架操作
		addGood(cate, refList,errorSet);
		return refList.size();
	}

	/**
	 * 查询所有的同步策略，不需要分页，按最后修改时间降序排列
	 * 
	 * @return
	 */
	private List queryByCategoryID(String categoryID) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticBO.queryByCategoryID().categoryID="
					+ categoryID);
		}
		try {
			return new TacticDAO().queryByCategoryID(categoryID);
		} catch (DAOException e) {
			throw new BOException("查询所有的同步策略失败！", e);
		}
	}

	/**
	 * 把指定货架下的商品下架
	 * 
	 * @param cId
	 * @throws BOException
	 */
	private void downGood(String cId) throws BOException {
		logger.info("下架商品");
		CategoryTools.clearCateGoods(cId, false);
	}

	/**
	 * 根据同步策略获得上架商品列表
	 * 
	 * @param tacticList
	 * @param cate
	 */
	private List categoryTactic(List tacticList, Category cate)
			throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("TacticBO.categoryTactic(). cid=" + cate.getId());
		}

		try {
			return new TacticDAO().categoryTactic(tacticList);
		} catch (DAOException e) {
			throw new BOException("根据同步策略获得上架商品列表失败！", e);
		}
	}

	/**
	 * 机型货架筛选上架商品
	 * 
	 * @param contList
	 * @param cate
	 */
	private List deviceCategoryTactic(List contList, Category cate)
			throws BOException {
		List device = null;
		List retList = new ArrayList();
		try {
			// 调用CategoryDeviceDAO进行查询所有机型货架限定机型
			device = CategoryDeviceDAO.getInstance().queryDeviceByCategoryId(
					cate.getId());
		} catch (DAOException e) {
			throw new BOException("得到当前货架关联了哪些机型信息时发生数据库异常！", e);
		}

		// 筛选匹配
		for (Iterator iter = contList.iterator(); iter.hasNext();) {
			GContent con = (GContent) iter.next();
			String conDeviceId = con.getFulldeviceID();

			// 与机型货架机型进行匹配
			for (Iterator iterator = device.iterator(); iterator.hasNext();) {
				DeviceVO deviceVO = (DeviceVO) iterator.next();
				String categoryDevice = "{" + deviceVO.getDeviceId() + "}";

				// 如果有一个机型匹配，加入上架列表。开始下一个
				if (conDeviceId.indexOf(categoryDevice) >= 0) {
					retList.add(con.getId());
					break;
				}
			}
		}

		return retList;
	}

	/**
	 * 转换数据
	 * 
	 * @param contList
	 * @param cate
	 */
	private List deviceCategoryTactic(List contList) throws BOException {
		List retList = new ArrayList();

		for (Iterator iter = contList.iterator(); iter.hasNext();) {
			GContent con = (GContent) iter.next();
			retList.add(con.getId());
		}

		return retList;
	}

	/**
	 * 上架商品
	 * 
	 * @param cate
	 * @param contList
	 * @throws BOException
	 */
	private int addGood(Category cate, List contList , Set<String> errorSet)  {
		if (logger.isDebugEnabled()) {
			logger.debug("开始上架商品！");
		}
		// 起始排序值
		 
		int sortId = 1000;
		for (int i = 0; i < contList.size(); i++) {
			String contId = (String) contList.get(i);
			
			try {
				CategoryTools.addGood(cate, contId, sortId--,
						RepositoryConstants.VARIATION_NEW, true, null);
				
			} catch (BOException e) {
//				details.append((errorNum++) + ")内容编码" + contList.get(i) +",名称: " );
				errorSet.add(contId);
				logger.error(e);
			}
			
		}
		return contList.size();
	}

}
