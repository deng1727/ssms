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
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(AddGoodsBo.class);

	/**
	 * ��¼ͬ��Ӧ�ü�¼
	 */
	protected static JLogger recordLog = LoggerFactory
			.getLogger("AddGoodsBoLog");

	private static AddGoodsBo instance = new AddGoodsBo();

	/**
	 * �õ�����ģʽ
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
					logger.error("����categoryID����Ʒ�ϼ�ʧ��", e);
				}
			}
		}
		TacticDAO tDao = new TacticDAO();
		int errorNum=0;
		if (!errorSet.isEmpty()) {
			errorNum=errorSet.size();
		}
		mailContent.append("<p>����ͬ���ܹ�����"+ toNum+"��Ӧ�ã����гɹ��ϼ�Ӧ��"+(toNum-errorNum)+"�����ϼ�ʧ��"+ errorNum +"����<br>");
		if (!errorSet.isEmpty()) {
			String[] errors = (String[]) errorSet.toArray();
			int maxnum=errorNum>100?100:errorNum;
			for (int i = 0; i < maxnum; i++) {
				String contentid = errors[i];
				logger.debug("contentid====================" + contentid);
				String contentname = tDao.getContentName(contentid);
				logger.debug("contentname==================="+ contentname);
				mailContent.append("<p>Ӧ���ϼ�ʧ�ܵ���ϢΪ:<br>");
				mailContent.append(i + ")���ݱ���" + contentid +",����: " +contentname +"<br>");
				if(i==99){
					mailContent.append("<p> ���� <>br  ");
				}
			}
		}
		
		
		
//		if (errorSet.size() > 0 || errorSet.size()<100) {
//			
//			for (Iterator iterator = errorSet.iterator(); iterator.hasNext();) {
//				String contentid = (String) iterator.next();
//				String contentname = tDao.getContentName(contentid);
//				int errorNum = 1;
//				mailContent.append("<p>Ӧ���ϼ�ʧ�ܵ���ϢΪ:<br>");
//				mailContent.append((errorNum++) + ")���ݱ���" + contentid +",����: " +contentname +"<br>");
//			}
//		}
	}

	

	private int exeTactic(String categoryID ,Set<String> errorSet) throws BOException {
		Category cate = CategoryBO.getInstance().getCategory(categoryID);
		// ����categoryid��ѯ��TacticVO
		List tacticList = queryByCategoryID(categoryID);
		List contList = null;
		List refList = null;

		// �¼���Ʒ
		downGood(categoryID);

		// ����ͬ�����Ի���ϼ���Ʒ�б�
		contList = categoryTactic(tacticList, cate);

		// ����ǻ��ͻ���ɸѡ�ϼ���Ʒ
		if (1 == cate.getDeviceCategory()) {
			refList = deviceCategoryTactic(contList, cate);
		} else {
			refList = deviceCategoryTactic(contList);
		}
		
		// �ϼܲ���
		addGood(cate, refList,errorSet);
		return refList.size();
	}

	/**
	 * ��ѯ���е�ͬ�����ԣ�����Ҫ��ҳ��������޸�ʱ�併������
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
			throw new BOException("��ѯ���е�ͬ������ʧ�ܣ�", e);
		}
	}

	/**
	 * ��ָ�������µ���Ʒ�¼�
	 * 
	 * @param cId
	 * @throws BOException
	 */
	private void downGood(String cId) throws BOException {
		logger.info("�¼���Ʒ");
		CategoryTools.clearCateGoods(cId, false);
	}

	/**
	 * ����ͬ�����Ի���ϼ���Ʒ�б�
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
			throw new BOException("����ͬ�����Ի���ϼ���Ʒ�б�ʧ�ܣ�", e);
		}
	}

	/**
	 * ���ͻ���ɸѡ�ϼ���Ʒ
	 * 
	 * @param contList
	 * @param cate
	 */
	private List deviceCategoryTactic(List contList, Category cate)
			throws BOException {
		List device = null;
		List retList = new ArrayList();
		try {
			// ����CategoryDeviceDAO���в�ѯ���л��ͻ����޶�����
			device = CategoryDeviceDAO.getInstance().queryDeviceByCategoryId(
					cate.getId());
		} catch (DAOException e) {
			throw new BOException("�õ���ǰ���ܹ�������Щ������Ϣʱ�������ݿ��쳣��", e);
		}

		// ɸѡƥ��
		for (Iterator iter = contList.iterator(); iter.hasNext();) {
			GContent con = (GContent) iter.next();
			String conDeviceId = con.getFulldeviceID();

			// ����ͻ��ܻ��ͽ���ƥ��
			for (Iterator iterator = device.iterator(); iterator.hasNext();) {
				DeviceVO deviceVO = (DeviceVO) iterator.next();
				String categoryDevice = "{" + deviceVO.getDeviceId() + "}";

				// �����һ������ƥ�䣬�����ϼ��б���ʼ��һ��
				if (conDeviceId.indexOf(categoryDevice) >= 0) {
					retList.add(con.getId());
					break;
				}
			}
		}

		return retList;
	}

	/**
	 * ת������
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
	 * �ϼ���Ʒ
	 * 
	 * @param cate
	 * @param contList
	 * @throws BOException
	 */
	private int addGood(Category cate, List contList , Set<String> errorSet)  {
		if (logger.isDebugEnabled()) {
			logger.debug("��ʼ�ϼ���Ʒ��");
		}
		// ��ʼ����ֵ
		 
		int sortId = 1000;
		for (int i = 0; i < contList.size(); i++) {
			String contId = (String) contList.get(i);
			
			try {
				CategoryTools.addGood(cate, contId, sortId--,
						RepositoryConstants.VARIATION_NEW, true, null);
				
			} catch (BOException e) {
//				details.append((errorNum++) + ")���ݱ���" + contList.get(i) +",����: " );
				errorSet.add(contId);
				logger.error(e);
			}
			
		}
		return contList.size();
	}

}
