package com.aspire.dotcard.syncAndroid.ppms;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.commons.lang.time.DateUtils;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.searchfile.SearchFileConfig;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGUtil;
import com.aspire.dotcard.syncAndroid.unifiedPackage.vo.GoodsCenterMessagesVo;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticBO;
import com.aspire.dotcard.syncData.tactic.TacticConstants;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncGoodsCenter.vo.GcAppHotInfo;
import com.aspire.ponaadmin.web.channeladmin.dao.OpenChannelsCategoryDAO;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.repository.persistency.db.NodePersistencyDB;
import com.aspire.ponaadmin.web.repository.singlecategory.dao.SingleCategoryDAO;
import com.aspire.ponaadmin.web.repository.singlecategory.vo.SingleCategoryVO;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.jcraft.jsch.Logger;

public class PPMSDAO {
	/**
	 * ��־����
	 */
	private static JLogger LOG = LoggerFactory.getLogger(PPMSDAO.class);
	/**
	 * ֧����������ݿ�����������Ϊ�ձ�ʾ�Ƿ��������͵Ĳ���
	 */
	private TransactionDB transactionDB;

	// private Producer producer;

	private Map<MSGType, List<String>> feedback;// add by aiyan 2013-02-25
	// Ϊ����һ�������м�¼�����¼ܵ����ݶ���Ϣ��

	private char char01 = (char) Integer.parseInt("01", 16);
	private char char0c = (char) Integer.parseInt("0c", 16);

	/**
	 * ��ȡ�������͵�DAOʵ��
	 * 
	 * @return GoodsDAO
	 */
	public static PPMSDAO getTransactionInstance(TransactionDB transactionDB) {
		PPMSDAO dao = new PPMSDAO();
		dao.transactionDB = transactionDB;
		dao.feedback = new HashMap<MSGType, List<String>>();
		return dao;
	}

	//    
	// public static PPMSDAO getTransactionInstance(TransactionDB
	// transactionDB,Producer producer)
	// {
	// PPMSDAO dao = new PPMSDAO();
	// dao.transactionDB = transactionDB;
	// dao.feedback = new HashMap<MSGType,List<String>>();
	// dao.producer = producer;
	// return dao;
	// }

	/**
	 * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ���� ��������Ǵ�GoodsBO�������,Ϊ�˿緽�����������޸ģ�add by
	 * aiyan 2013-02-21
	 * 
	 * @param refID
	 * @throws BOException
	 */
	public void removeRefContentFromCategory(String refID) throws BOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("PPMSDAO.removeRefContentFromCategory(" + refID
					+ ")is starting !");
		}
		try {
			ReferenceNode refNode = (ReferenceNode) Repository.getInstance()
					.getNode(refID, "nt:reference");
			if (LOG.isDebugEnabled()) {
				LOG.debug("The refNodeID is " + refID);
			}
			// ֱ�ӿ��Դ���Ʒ�����л�ȡ��
			GContent content = (GContent) refNode.getRefNode();// (GContent)Repository.getInstance().getNode(refNode.getRefNodeID(),"nt:gcontent");
			if (LOG.isDebugEnabled()) {
				LOG.debug("Ҫ�Ƴ������ýڵ��Ӧ������IDΪ" + content.getId() + ",����������:"
						+ content.getName());
			}
			GoodsVO goodsVO = new GoodsVO();
			goodsVO.setGoodsID(refNode.getGoodsID());
			goodsVO.setIcpCode(content.getIcpCode());
			goodsVO.setIcpServId(content.getIcpServId());
			goodsVO.setContentID(content.getContentID());
			goodsVO.setCategoryID(refNode.getCategoryID());
			goodsVO.setGoodsName(content.getName());
			goodsVO.setState(9);
			goodsVO.setChangeDate(new Date());
			goodsVO.setActionType(9);
			goodsVO.setLastState(1);
			removeRefContentFromCategory(refID, goodsVO);
		} catch (Exception e) {
			LOG.error(e);
		}

	}

	/**
	 * ���񷽷���û������Ͳ��ü����ˡ� �Ƴ����ýڵ㲢��GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
	 * ��������Ǵ�GoodsDAO������ġ�add by aiyan 2013-02-21
	 * 
	 * @param vo
	 * @throws DAOException
	 */
	public void removeRefContentFromCategory(String refID, GoodsVO vo)
			throws DAOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("PPMSDAO.removeRefContentFromCategory() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		// ��Ʒ�¼�
		String sqlCode1 = "PPMSDAO.removeRefContentFromCategory.DELETE1";
		String sqlCode2 = "PPMSDAO.removeRefContentFromCategory.DELETE2";
		String sqlCode3 = "PPMSDAO.removeRefContentFromCategory.INSERT";
		Object[] paras1 = { refID };
		Object[] paras2 = { refID };
		Object[] paras3 = { vo.getGoodsID(), vo.getIcpCode(),
				vo.getIcpServId(), vo.getContentID(), vo.getCategoryID(),
				vo.getGoodsName(), new Integer(vo.getState()),
				new Timestamp(vo.getChangeDate().getTime()),
				new Integer(vo.getActionType()), new Integer(vo.getLastState()) };
		transactionDB.executeBySQLCode(sqlCode1, paras1);
		transactionDB.executeBySQLCode(sqlCode2, paras2);
		transactionDB.executeBySQLCode(sqlCode3, paras3);

		// MSGUtil.addMSG(MSGType.RefModifyReq,feedback,vo.getGoodsID()+":9");

		// ����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
		// Goodsid ���� String reference���е�goodsid
		// Categoryid ��ѡ String ����categoryid���½�ʱ������
		// Id ��ѡ String ����categoryid��Ӧ��Id���½�ʱ������
		// Refnodeid ��ѡ String Ӧ��ID���½�ʱ������
		// Sortid ��ѡ String �����ֶΣ��½�ʱ������
		// Loaddate ��ѡ String ����ʱ�䣬�½�ʱ������
		// Action ���� String 0���½�
		// 9��ɾ��
		// Transactionid ���� String ��������ID�����ڴ���ͬһ��������ʱʹ�á�
		MSGUtil.addMSG(MSGType.RefModifyReq, feedback, vo.getGoodsID()
				+ "::::::9");// ɾ����Щ��ѡ��Ϳ��Կ��ַ����ˡ��Ǻ�

	}

	/**
	 * �������ϼܵ����ܻ����� ��������Ǵ�GoodsBO������ġ�add by aiyan 2013-02-21
	 * 
	 * @param gc
	 * @param categoryID
	 */
	public void addCategory(CmContentVO gc, String categoryID)
			throws BOException {
		try {
			Category category = (Category) Repository.getInstance().getNode(
					categoryID, RepositoryConstants.TYPE_CATEGORY);

			// ������Ʒ���룽���ܱ��룫��ҵ����(6λ)��ҵ������(12λ)�����ݱ���(12λ)
			String goodsID = "";
			// �ŵ�Ŀ�������

			if (StringUtils.isEmpty(gc.getAppid())) {
				goodsID = category.getCategoryID()
						+ PublicUtil.lPad(gc.getCompanyid(), 6)
						+ PublicUtil.lPad(gc.getProductID(), 12)
						+ PublicUtil.lPad(gc.getContentid(), 12);
			} else {
				goodsID = category.getCategoryID() + "000000"
						+ PublicUtil.lPad(gc.getAppid(), 12)
						+ PublicUtil.lPad(gc.getContentid(), 12);
			}

			// ����һ���µ���Դid
			String newNodeID = category.getNewAllocateID();

			// ����һ�����ýڵ㣬��������������
			ReferenceNode ref = new ReferenceNode();
			ref.setId(newNodeID);
			ref.setParentID(category.getId());
			ref.setPath(category.getPath() + ".{" + newNodeID + "}");
			ref.setRefNodeID(gc.getId());
			ref.setSortID(0);
			ref.setGoodsID(goodsID);
			ref.setCategoryID(category.getCategoryID());
			ref.setVariation(RepositoryConstants.VARIATION_NEW);
			if (!StringUtils.isEmpty(gc.getAppid())) {
				ref.setAppId(gc.getAppid());
			}
			// ������Ʒ��ϢVO�࣬��������������
			GoodsVO goodsVO = new GoodsVO();
			goodsVO.setGoodsID(goodsID);
			goodsVO.setIcpCode(gc.getIcpcode());
			goodsVO.setIcpServId(gc.getIcpservid());
			goodsVO.setContentID(gc.getContentid());
			goodsVO.setCategoryID(category.getCategoryID());
			goodsVO.setGoodsName(gc.getName());
			goodsVO.setState(1);
			goodsVO.setChangeDate(new Date());
			goodsVO.setActionType(1);

			// ����GoodsBO�е�addNodeAndInsertGoodsInfor����ڵ㲢������Ʒ��Ϣ
			// add by aiyan
			// 2013-04-27�������categoryidʱt_r_category���idֵ,Ҳ���ǻ������롣���Ǹñ��CATEGORYID�ֶ�ֵ
			addNodeAndInsertGoodsInfo(ref, goodsVO, categoryID);
		} catch (Exception e) {
			LOG.error(e);
			LOG.debug(e);
			throw new BOException("�����ϼܳ���", e);
		}
	}

	public void add33AppCategroyByTactic(GcAppHotInfo dccVo, String type,
			String id) throws BOException {

		List tacticList = null;
		tacticList = new TacticBO().queryAndroidAll();
		for (int i = 0; i < tacticList.size(); i++) {
			TacticVO vo = (TacticVO) tacticList.get(i);
			if (vo.getAppCateName() != null && vo.getContentType() != null
					&& vo.getAppCateName().equals(dccVo.getGRADE2_TYPE())
					&& vo.getContentType().equals(type)) {
				add33AppToCategory(dccVo, vo.getCategoryID(), id);
			}
		}
	}

	/**
	 * 33Ӧ���ϼܶ�������
	 * 
	 * @param gc
	 * @param categoryID
	 * @throws BOException
	 */
	public void add33AppToCategory(GcAppHotInfo gc, String categoryID, String id)
			throws BOException {
		try {
			Category category = (Category) Repository.getInstance().getNode(
					categoryID, RepositoryConstants.TYPE_CATEGORY);

			// ������Ʒ���룽���ܱ��룫��ҵ����(6λ)��ҵ������(12λ)�����ݱ���(12λ)
			String goodsID = "";
			// �ŵ�Ŀ�������

			if (StringUtils.isEmpty(gc.getAPPID())) {
				goodsID = category.getCategoryID()
						+ PublicUtil.lPad("330000", 6)
						+ PublicUtil.lPad("100033003300", 12)
						+ PublicUtil.lPad(gc.getCONTENT_ID(), 12);
			} else {
				goodsID = category.getCategoryID() + "000000"
						+ PublicUtil.lPad(gc.getAPPID(), 12)
						+ PublicUtil.lPad(gc.getCONTENT_ID(), 12);
			}

			// ����һ���µ���Դid
			String newNodeID = category.getNewAllocateID();

			// ����һ�����ýڵ㣬��������������
			ReferenceNode ref = new ReferenceNode();
			ref.setId(newNodeID);
			ref.setParentID(category.getId());
			ref.setPath(category.getPath() + ".{" + newNodeID + "}");
			ref.setRefNodeID(id);
			ref.setSortID(0);
			ref.setGoodsID(goodsID);
			ref.setCategoryID(category.getCategoryID());
			ref.setVariation(RepositoryConstants.VARIATION_NEW);
			ref.setAppId(gc.getAPPID());// ���appid
			// ������Ʒ��ϢVO�࣬��������������
			GoodsVO goodsVO = new GoodsVO();
			goodsVO.setGoodsID(goodsID);
			goodsVO.setIcpCode("330000");
			goodsVO.setIcpServId("1000330000");
			goodsVO.setContentID(gc.getCONTENT_ID());
			goodsVO.setCategoryID(category.getCategoryID());
			goodsVO.setGoodsName(gc.getCONTENT_NAME());
			goodsVO.setState(1);
			goodsVO.setChangeDate(new Date());
			goodsVO.setActionType(1);

			// ����GoodsBO�е�addNodeAndInsertGoodsInfor����ڵ㲢������Ʒ��Ϣ
			// add by aiyan
			// 2013-04-27�������categoryidʱt_r_category���idֵ,Ҳ���ǻ������롣���Ǹñ��CATEGORYID�ֶ�ֵ
			addNodeAndInsertGoodsInfo(ref, goodsVO, categoryID);
		} catch (Exception e) {
			LOG.error(e);
			LOG.debug(e);
			throw new BOException("�����ϼܳ���", e);
		}
	}

	/**
	 * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
	 * 
	 * @param vo
	 *            ��ƷVO
	 * @param refNode
	 *            ���ýڵ�
	 * @throws DAOException
	 */
	public void addNodeAndInsertGoodsInfo(ReferenceNode refNode, GoodsVO vo,
			String categoryID) throws DAOException {

		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String updateTime = sdf.format(new Date());
		if (LOG.isDebugEnabled()) {
			LOG.debug("PPMSDAO.addNodeAndInsertGoodsInfo() is beginning...");
		}
		String sqlCode1 = "PPMSDAO.addNodeAndInsertGoodsInfo.INSERT1";
		String sqlCode2 = "PPMSDAO.addNodeAndInsertGoodsInfo.INSERT2";
		String sqlCode3 = "PPMSDAO.addNodeAndInsertGoodsInfo.INSERT3";
		Object[] paras1 = { refNode.getId(), refNode.getParentID(),
				refNode.getPath(), refNode.getType() };
		Object[] paras2 = { refNode.getId(), refNode.getRefNodeID(),
				new Integer(refNode.getSortID()),
				new Integer(refNode.getVariation()), refNode.getGoodsID(),
				refNode.getCategoryID(), updateTime, refNode.getAppId() };
		Object[] paras3 = { vo.getGoodsID(), vo.getIcpCode(),
				vo.getIcpServId(), vo.getContentID(), vo.getCategoryID(),
				vo.getGoodsName(), new Integer(vo.getState()),
				new Timestamp(vo.getChangeDate().getTime()),
				new Integer(vo.getActionType()) };
		transactionDB.executeBySQLCode(sqlCode1, paras1);
		transactionDB.executeBySQLCode(sqlCode2, paras2);
		transactionDB.executeBySQLCode(sqlCode3, paras3);

		// MSGUtil.addMSG(MSGType.RefModifyReq,feedback,vo.getGoodsID()+":0");
		// ����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
		// Goodsid ���� String reference���е�goodsid
		// Categoryid ��ѡ String ����categoryid���½�ʱ������
		// Id ��ѡ String ����categoryid��Ӧ��Id���½�ʱ������
		// Refnodeid ��ѡ String Ӧ��ID���½�ʱ������
		// Sortid ��ѡ String �����ֶΣ��½�ʱ������
		// Loaddate ��ѡ String ����ʱ�䣬�½�ʱ������
		// Action ���� String 0���½�
		// 9��ɾ��
		// Transactionid ���� String ��������ID�����ڴ���ͬһ��������ʱʹ�á�
		MSGUtil.addMSG(MSGType.RefModifyReq, feedback, vo.getGoodsID() + ":"
				+ vo.getCategoryID() + ":" + categoryID + ":" + refNode.getId()
				+ ":" + refNode.getSortID() + ":"
				+ DateUtil.formatDate(vo.getChangeDate(), "yyyyMMddHHmmss")
				+ ":0");

	}

	/**
	 * ����ĳӦ��(���ݲ����϶����������)
	 * 
	 * @param gc
	 * @throws BOException
	 */
	public void downCategoryByTactic(GContentVO gc) throws BOException {
		List refs = new ArrayList();
		try {

			String querySQLCode = "com.aspire.dotcard.syncAndroid.ppms.downCategory";
			// select r.id from t_r_reference r
			// where r.refnodeid=? and r.categoryid in
			// (select c.categoryid
			// from t_r_category c ,t_sync_tactic_android t
			// where t.categoryid=c.id)

			// (add 2014-05-07) ���HTCӦ�����⴦��
			if ("16".equals(gc.getSubtype())) {
				querySQLCode = "com.aspire.dotcard.syncAndroid.ppms.downCategory.HTC";
				// select r.id from t_r_reference r
				// where r.refnodeid=? and r.categoryid in
				// (select c.categoryid
				// from t_r_category c ,t_sync_tactic_moto t
				// where t.categoryid=c.id and t.subtype = '16')
			}
			refs = DataSyncDAO.getInstance().getRefContentsByCateName(
					querySQLCode, gc.getId());
		} catch (DAOException e) {
			LOG.error("�¼�ԭ���������", e);
			throw new BOException("��ѯ��Ӧ�ã�" + gc.getContentId() + " ����");

		}

		try {
			for (int i = 0; i < refs.size(); i++) {
				removeRefContentFromCategory((String) refs.get(i));

			}
		} catch (Exception e) {

			LOG.error("�¼�ԭ������: " + gc.getContentId());
			throw new BOException("�¼�ԭ������:" + gc.getContentId() + " ����", e);
		}

	}

	private boolean checkTransactionDB() {
		return transactionDB == null;
	}

	/**
	 * ����ĳӦ��(���ݲ����϶����������)
	 * 
	 * @param dccVo
	 * @throws BOException
	 */
	public void addCategroyByTactic(CmContentVO dccVo) throws BOException {

		// ��ȡ���е�CMS����ͬ������
		List tacticList = null;
		// (add 2014-05-07) ���HTCӦ�����⴦��
		if ("16".equals(dccVo.getSubtype())) {
			tacticList = new TacticBO().queryHTCAll();
			String appId = dccVo.getContentCode();
			String cateName = null;// MOTOһ������
			String appcateName = null;// MOTO��������
			try {
				String[] res = DataSyncDAO.getInstance().getHTCAppCateNameById(
						appId);
				if (null != res && res.length == 2) {
					cateName = res[0];
					appcateName = res[1];
				}
			} catch (DAOException e) {
				e.printStackTrace();
				throw new BOException("��ѯHTCӦ��HTCһ�����࣬��������ʧ��", e);
			}
			for (int i = 0; i < tacticList.size(); i++) {
				TacticVO vo = (TacticVO) tacticList.get(i);
				if (vo.getAppCateName() != null
						&& vo.getContentType() != null
						&& vo.getAppCateName().equals(appcateName)
						&& (TacticConstants.CONTENT_TYPE_ALL.equals(vo
								.getContentType()) || vo.getContentType()
								.equals(dccVo.getTypename()))) {
					addCategory(dccVo, vo.getCategoryID());

				}
			}
		} else {
			tacticList = new TacticBO().queryAndroidAll();
			for (int i = 0; i < tacticList.size(); i++) {
				TacticVO vo = (TacticVO) tacticList.get(i);
				// System.out.println(vo.getAppCateName()+"-"+dccVo.getAppCateName());
				// System.out.println(vo.getContentType()+"-"+dccVo.getTypename());
				if (vo.getAppCateName() != null && vo.getContentType() != null
						&& vo.getAppCateName().equals(dccVo.getAppCateName())
						&& vo.getContentType().equals(dccVo.getTypename())) {
					addCategory(dccVo, vo.getCategoryID());

				}
			}
		}
	}

	/**
	 * ���㷺����������������ĳӦ��(���ݻ���Id)
	 * 
	 * @param dccVo
	 * @throws BOException
	 */
	public void addCategroyByCategoryId(CmContentVO dccVo) throws BOException {
		// ��ȡ���еĺ��������̶�Ӧ�������б�
		List list = new TacticBO().queryChannelsCategoryAll();

		for (int i = 0; i < list.size(); i++) {
			OpenChannelsCategoryVo vo = (OpenChannelsCategoryVo) list.get(i);
			if (vo.getCategoryId() != null && vo.getChannelsId() != null
					&& vo.getChannelsId().equals(dccVo.getCompanyid())) {
				addCategory(dccVo, vo.getCategoryId());
			}
		}
	}

	/**
	 * �����Ϣ
	 * 
	 * @param type
	 * @param transactionID
	 * @throws DAOException
	 */
	public void addMessages(MSGType type, String transactionID)
			throws DAOException {
		List<String> msgList = feedback.get(type);
		if (msgList != null) {
			for (int i = 0; i < msgList.size(); i++) {
				addMessages(type, transactionID, msgList.get(i));
			}
		}

	}

	// {RefModifyReq=[300000001034:100000050:DOWN, 300000001034:100000030:UP]}
	// private Map ConvertData(String message) {
	// // TODO Auto-generated method stub
	// String[] arr = message.split(":");
	// String action = "0";
	// if("DOWN".equals(arr[2]))
	// action = "9";
	// else if("UP".equals(arr[2])){
	// action = "0";
	// }
	// Map data = new HashMap();
	// data.put("Contentid", arr[0]);
	// data.put("Catogoryid", arr[1]);
	// data.put("Action", action);
	// return data;
	// }

	/**
	 * �����Ϣ
	 * 
	 * @param transactionID
	 * @throws DAOException
	 */
	public void addMessages(MSGType type, String transactionID, String message)
			throws DAOException {
		String sqlCode = "PPMSDAO.addMessages";
		Object[] paras = { type.toString(), transactionID, message };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public static void addMessagesStatic(MSGType type, String transactionID,
			String message) {
		try {
			String sqlCode = "PPMSDAO.addMessages";
			Object[] paras = { type.toString(), transactionID, message };
			DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (Exception e) {
			LOG.error("��̬�����Ϣ����", e);
		}
	}

	// /**
	// * ���¼��ύ��
	// * @param type
	// * @param transactionID
	// * @throws JMSException
	// */
	// public void addJMS(MSGType type,String transactionID) throws
	// JMSException{
	// List<String> msgList = feedback.get(type);
	// for(int i=0;i<msgList.size();i++){
	// addJMS(new RefModifyReqMSG(msgList.get(i)));
	// }
	//		
	// }
	// /**
	// * �����ύ
	// * @param transactionID
	// * @throws JMSException
	// */
	// public void addJMS(String transactionID) throws JMSException{
	// addJMS(new CommitReqMSG(transactionID+":0"));
	// }

	// /**
	// * @param msg
	// * @throws JMSException
	// */
	// public void addJMS(MSG msg) throws JMSException{
	// producer.sendMessage(msg.getData());
	// }

	public void updateDeviceResourseByPid(String pid) throws DAOException {

		String sqlCodeInsert = "SyncAndroid.APPInfoDAO.updateDeviceResourse().INSERT";
		String sqlCodeDel = "SyncAndroid.APPInfoDAO.updateDeviceResourse().DEL";
		Object[] paras = { pid };
		transactionDB.executeBySQLCode(sqlCodeDel, paras);
		transactionDB.executeBySQLCode(sqlCodeInsert, paras);

	}

	public void updateDeviceResourseByCid(String cid) throws DAOException {
		// TODO Auto-generated method stub

		// select * from (select r.*,
		// row_number() over(partition by r.contentid, r.device_id order by
		// r.version*1 desc) rn
		// from t_a_cm_device_resource r where r.pid is not null)
		// where rn = 1 and contentid = '300000001713'

		String sqlCodeInsert = "SyncAndroid.APPInfoDAO.updateDeviceResourseByCid().INSERT";
		String sqlCodeDel = "SyncAndroid.APPInfoDAO.updateDeviceResourseByCid().DEL";
		Object[] paras = { cid };
		transactionDB.executeBySQLCode(sqlCodeDel, paras);
		transactionDB.executeBySQLCode(sqlCodeInsert, paras);
	}

	// id ��t_r_gcontent���е�id;
	public void downAllReference(String categoryid, GContentVO vo)
			throws DAOException {
		// TODO Auto-generated method stub
		// delete from t_r_base b where b.id in(
		// select r.id
		// from t_r_reference r,(select categoryid from t_r_category start with
		// categoryid='100000840' connect by prior categoryid =
		// parentcategoryid) c
		// where r.categoryid=c.categoryid
		// and r.refnodeid = '68873933'
		// )

		// delete from t_r_reference where id in
		// select r.id
		// from t_r_reference r,(select categoryid from t_r_category start with
		// categoryid='100000840' connect by prior categoryid =
		// parentcategoryid) c
		// where r.categoryid=c.categoryid
		// and r.refnodeid = '68873933'
		// )

		String sqlCodeDel1 = "SyncAndroid.APPInfoDAO.downAllReference().DEL1";
		String sqlCodeDel2 = "SyncAndroid.APPInfoDAO.downAllReference().DEL2";
		Object[] paras = { categoryid, vo.getId() };
		transactionDB.executeBySQLCode(sqlCodeDel1, paras);
		transactionDB.executeBySQLCode(sqlCodeDel2, paras);

		List<Map> childrenCategoryids = getChildrenCategoryId(categoryid, vo
				.getId());
		for (Map m : childrenCategoryids) {
			// MSGUtil.addMSG(MSGType.RefModifyReq,feedback,m.get("categoryid")+":"+m.get("goodsid")+":9");

			// ����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
			// Goodsid ���� String reference���е�goodsid
			// Categoryid ��ѡ String ����categoryid���½�ʱ������
			// Id ��ѡ String ����categoryid��Ӧ��Id���½�ʱ������
			// Refnodeid ��ѡ String Ӧ��ID���½�ʱ������
			// Sortid ��ѡ String �����ֶΣ��½�ʱ������
			// Loaddate ��ѡ String ����ʱ�䣬�½�ʱ������
			// Action ���� String 0���½�
			// 9��ɾ��
			// Transactionid ���� String ��������ID�����ڴ���ͬһ��������ʱʹ�á�
			MSGUtil.addMSG(MSGType.RefModifyReq, feedback, m.get("goodsid")
					+ "::::::9");// ɾ����Щ��ѡ��Ϳ��Կ��ַ����ˡ��Ǻ�
		}

	}

	public void downAllOperateReference(String operateCategoryId, GContentVO vo)
			throws DAOException {
		// TODO Auto-generated method stub
		// <ConfigItem>
		// <Name>OPERATE_ROOT_CATEGORYID</Name>
		// <Id>0</Id>
		// <Description>487659139(android��Ʒ�ٵ�)��218637126�ر���218637125ר����1782551ר����</Description>
		// <Value>487659139|218637126|218637125|1782551</Value>
		// <Reserved/>
		// </ConfigItem>

		if (operateCategoryId == null || operateCategoryId.equals("null")
				|| operateCategoryId.trim().length() == 0) {
			LOG.info("��Ӫ�������ݷǷ�:" + operateCategoryId);
			return;
		}
		String[] categoryIdArr = operateCategoryId.split("\\|");
		for (String categoryId : categoryIdArr) {
			String sqlCodeDel1 = "SyncAndroid.APPInfoDAO.downAllReference().DEL1";
			String sqlCodeDel2 = "SyncAndroid.APPInfoDAO.downAllReference().DEL2";
			Object[] paras = { categoryId, vo.getId() };
			transactionDB.executeBySQLCode(sqlCodeDel1, paras);
			transactionDB.executeBySQLCode(sqlCodeDel2, paras);

			List<Map> childrenCategoryids = getChildrenCategoryId(categoryId,
					vo.getId());
			for (Map m : childrenCategoryids) {
				// MSGUtil.addMSG(MSGType.RefModifyReq,feedback,m.get("categoryid")+":"+m.get("goodsid")+":9");

				// ����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
				// Goodsid ���� String reference���е�goodsid
				// Categoryid ��ѡ String ����categoryid���½�ʱ������
				// Id ��ѡ String ����categoryid��Ӧ��Id���½�ʱ������
				// Refnodeid ��ѡ String Ӧ��ID���½�ʱ������
				// Sortid ��ѡ String �����ֶΣ��½�ʱ������
				// Loaddate ��ѡ String ����ʱ�䣬�½�ʱ������
				// Action ���� String 0���½�
				// 9��ɾ��
				// Transactionid ���� String ��������ID�����ڴ���ͬһ��������ʱʹ�á�
				MSGUtil.addMSG(MSGType.RefModifyReq, feedback, m.get("goodsid")
						+ "::::::9");// ɾ����Щ��ѡ��Ϳ��Կ��ַ����ˡ��Ǻ�
			}
		}

	}

	// �˷����ǲ�ѯ����������������
	private List<Map> getChildrenCategoryId(String categoryid, String id)
			throws DAOException {

		// select r.categoryid
		// from t_r_reference r,(select categoryid from t_r_category start with
		// categoryid='100000840' connect by prior categoryid =
		// parentcategoryid) c
		// where r.categoryid=c.categoryid
		// and r.refnodeid = '68873933'

		List<Map> childrenCategoryids = new ArrayList<Map>();
		String sqlCode = "PPMSDAO.getChildrenCategoryId().SELECT";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { categoryid, id });
			while (rs != null && rs.next()) {
				Map m = new HashMap();
				m.put("categoryid", rs.getString("categoryid"));
				m.put("goodsid", rs.getString("goodsid"));
				childrenCategoryids.add(m);
			}
		} catch (Exception ex) {
			throw new DAOException("getChildrenCategoryId error!!", ex);
		} finally {
			DB.close(rs);
		}
		return childrenCategoryids;
	}

	// ���������Ŀ�ģ�Ϊ������ϵͳ��ɾ����Ӧ�õ����ݣ�
	public List<DelGoodsVO> getDelGoodsList(String categoryid, String id)
			throws DAOException {
		// select g.catename,r.goodsid,g.contentid
		// from t_r_gcontent g,t_r_reference r,(select categoryid from
		// t_r_category start with categoryid='100000751' connect by prior
		// categoryid = parentcategoryid) c
		// where r.categoryid=c.categoryid
		// and g.id = r.refnodeid
		// and r.refnodeid = '56688433'

		// select g.catename, r.goodsid, g.contentid
		// from t_r_gcontent g,
		// t_r_reference r,
		// (select categoryid
		// from t_r_category
		// start with id = ?
		// connect by prior categoryid = parentcategoryid) c
		// where r.categoryid = c.categoryid
		// and g.id = r.refnodeid
		// and r.refnodeid = ?

		List<DelGoodsVO> list = new ArrayList<DelGoodsVO>();
		String sqlCode = "PPMSDAO.getDelGoodsList().SELECT";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { categoryid, id });
			while (rs != null && rs.next()) {
				DelGoodsVO vo = new DelGoodsVO();
				vo.setCatename(rs.getString("catename"));
				vo.setGoodsid(rs.getString("goodsid"));
				vo.setContentid(rs.getString("contentid"));
				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getDelGoodsList error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;

	}

	public void updateGContent(CmContentVO vo) throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateGContent(" + vo + ")");
		}
		String sqlCode = "SyncAndroid.APPInfoDAO.updateGContentByDataCenterCmContentVo().UPDATE";
//		if (!StringUtils.isEmpty(vo.getDeveloper())) {
//			vo.setCompanyname(vo.getDeveloper());
//		}
		if (StringUtils.isEmpty(vo.getDeveloper())) {
			vo.setDeveloper(vo.getCompanyname());
		} else {
			String swapTemp = vo.getCompanyname();
			vo.setCompanyname(vo.getDeveloper());
			vo.setDeveloper(swapTemp);
		}

		Object[] paras = { vo.getHandbook(), vo.getHandbookPicture(),
				vo.getPvcid(), vo.getCopyrightFlag(), vo.getIsmmtoevent(),
				vo.getCateName(), vo.getName(), vo.getContentCode(),
				vo.getKeywords(), vo.getServAttr(), vo.getCreateDate(),
				vo.getLupddate(), vo.getPlupddate(), vo.getMarketdate(),
				vo.getIcpcode(), vo.getIcpservid(), vo.getCompanyid(),
				vo.getProductID(), vo.getCompanyname(), vo.getChargeTime(),
				vo.getCityid(), vo.getAppCateName(), vo.getAppCateID(),
				vo.getIntroduction(), vo.getWWWPropaPicture1(),
				vo.getWWWPropaPicture2(), vo.getWWWPropaPicture3(),
				vo.getProvider(), vo.getLanguage(),
				vo.getLogo1(),
				vo.getLogo2(),
				vo.getLogo3(),
				vo.getLogo4(),
				vo.getLogo5(),
				vo.getLogo6(),
				vo.getOnlinetype(),
				vo.getVersion(),
				vo.getPicture1(),
				vo.getPicture2(),
				vo.getPicture3(),
				vo.getPicture4(),
				this.getPlatformByContentID(vo.getContentid()), // change by
				// tungke
				// 20140723
				vo.getMapname(), vo.getChanneldisptype(), vo.getIsCmpassApp(),
				vo.getLogo7(), vo.getRisktag(), vo.getApptype(),
				vo.getCtrldev(), vo.getDeveloper(), vo.getAppid(),
				vo.getContentid() };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public void updateReceiveChange(String id, String status, String stats)
			throws DAOException {
		// TODO Auto-generated method stub
		String sqlCode = "SyncAndroid.APPInfoDAO.updateReceiveChange().UPDATE";
		transactionDB.executeBySQLCode(sqlCode, new Object[] { status, stats,
				id });
	}

	// ��������ġ�
	public void updateReceiveChangeNoTran(String id, String stats, String status)
			throws DAOException {

		// TODO Auto-generated method stub
		String sqlCode = "SyncAndroid.APPInfoDAO.updateReceiveChange().UPDATE";
		DB.getInstance().executeBySQLCode(sqlCode,
				new Object[] { status, stats, id });
	}

	public String insertGContent(CmContentVO vo) throws DAOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("insertGContentByDataCenterCmContentVo(" + vo + " )");
		}
		String id = new NodePersistencyDB(null).allocateNewNodeID();
		LOG.debug("DAO vo.getIsmmtoevent():" + vo.getIsmmtoevent());
		String sqlCode = "SyncAndroid.APPInfoDAO.insertGContentByDataCenterCmContentVo().INSERT";
//		if (!StringUtils.isEmpty(vo.getDeveloper())) {
//			vo.setCompanyname(vo.getDeveloper());
//		}
		// MMC-1790 ��� developer û��ֵ������� Comapnyname����Ϊ���򽻻�
		if (StringUtils.isEmpty(vo.getDeveloper())) {
			vo.setDeveloper(vo.getCompanyname());
		} else {
			String swapTemp = vo.getCompanyname();
			vo.setCompanyname(vo.getDeveloper());
			vo.setDeveloper(swapTemp);
		}

		Object[] paras = { vo.getHandbook(), vo.getHandbookPicture(),
				vo.getPvcid(), vo.getCopyrightFlag(), vo.getIsmmtoevent(), id,
				vo.getCateName(), vo.getName(), vo.getContentCode(),
				vo.getKeywords(), vo.getServAttr(), vo.getCreateDate(),
				vo.getLupddate(), vo.getPlupddate(), vo.getMarketdate(),
				vo.getIcpcode(), vo.getIcpservid(), vo.getCompanyid(),
				vo.getProductID(), vo.getCompanyname(), vo.getChargeTime(),
				vo.getCityid(), vo.getAppCateName(), vo.getAppCateID(),
				vo.getIntroduction(), vo.getWWWPropaPicture1(),
				vo.getWWWPropaPicture2(), vo.getWWWPropaPicture3(),
				vo.getProvider(), vo.getLanguage(), vo.getLogo1(),
				vo.getLogo2(), vo.getLogo3(), vo.getLogo4(), vo.getLogo5(),
				vo.getLogo6(), vo.getOnlinetype(), vo.getVersion(),
				vo.getPicture1(), vo.getPicture2(), vo.getPicture3(),
				vo.getPicture4(), vo.getSubtype(),
				this.getPlatformByContentID(vo.getContentid()),
				vo.getContentid(), vo.getMapname(), vo.getChanneldisptype(),
				vo.getIsCmpassApp(), vo.getLogo7(), vo.getRisktag(),
				vo.getApptype(), vo.getCtrldev(), vo.getDeveloper(),
				vo.getAppid() };

		try {
			Object[] paras_base = { id, RepositoryConstants.ROOT_CONTENT_ID,
					"{100}.{702}.{" + id + "}", vo.getTypename() };

			// inserBaseSql=insert into T_R_BASE (ID, PARENTID, PATH,
			// TYPE)values (?, ?, ?, ?)
			transactionDB.executeBySQLCode("inserBaseSql", paras_base);
			transactionDB.executeBySQLCode(sqlCode, paras);

		} catch (DAOException e) {
			try {
				LOG.error("�����ִ����䣺"
						+ getDebugSql(SQLCode.getInstance().getSQLStatement(
								sqlCode), paras));
			} catch (DataAccessException e1) {
				// TODO Auto-generated catch block
				LOG.error(e1);
			}
			throw new DAOException(e);
		}

		return id;
	}

	private String getDebugSql(String sql, Object[] para) {
		if (para != null) {
			for (int i = 0; i < para.length; i++) {
				sql = sql.replaceFirst("\\?", "'" + para[i] + "'");
			}
		}
		return sql;
	}

	public void putMessages(Set<String> contentIdSet) {
		// TODO Auto-generated method stub
		// ����Ϊ�����ϵ�ı��CONTENTID��Ҫ��Ϣ���������ġ�
		LOG.info("syncAndroid:putMessages(����Ϊ�����ϵ�ı��CONTENTID��Ҫ��Ϣ����������)��ʼ...");
		if (contentIdSet.size() > 0) {
			String[][] mutiParas = new String[contentIdSet.size()][3];
			int i = 0;
			for (Iterator<String> it = contentIdSet.iterator(); it.hasNext();) {
				String contentId = it.next();
				mutiParas[i][0] = "ContentModifyReq";
				// 0��������
				// 1��Ӧ����Ϣ�����������չ�ֶΣ�
				// 2�������ϵ�������TypeΪRESOURCEʱ�Ż���ڣ�
				// 9������
				mutiParas[i][1] = "";
				mutiParas[i][2] = contentId + ":2";
				i++;
			}
			// PPMSDAO.addMessages=insert into
			// t_a_messages(id,type,transactionid,message)
			// values(SEQ_T_A_MESSAGES.NEXTVAL,?,?,?)
			String sqlCode = "PPMSDAO.addMessages";
			try {
				DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
			} catch (Exception ex) {
				// throw new DAOException("putMessages error!!", ex);
				LOG.error(ex);
			}
		}

	}

	public void updateVService(String contentid) throws DAOException {
		// TODO Auto-generated method stub
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateVService is beginning...");
		}
		// tdb = TransactionDB.getTransactionInstance();
		// PPMSDAO.updateVService.del=delete from t_a_dc_ppms_service s where
		// s.contentid =?
		// PPMSDAO.updateVService.insert=insert into t_a_dc_ppms_service select
		// * from v_dc_ppms_service s where s.contentid =?
		transactionDB.executeBySQLCode("PPMSDAO.updateVService.del",
				new Object[] { contentid });
		transactionDB.executeBySQLCode("PPMSDAO.updateVService.insert",
				new Object[] { contentid });
	}

	// public void updateVServiceOld(String cids) throws DAOException {
	// // TODO Auto-generated method stub
	// //delete from v_service s where s.contentid in (?)
	// //insert into v_service select * from ppms_v_service s where s.contentid
	// in (?)
	//		
	// // TODO Auto-generated method stub
	// TransactionDB tdb = null;
	// try{
	// if (LOG.isDebugEnabled())
	// {
	// LOG.debug("updateVService is beginning...");
	// }
	//	        
	// if(cids.length()>0){
	// tdb = TransactionDB.getTransactionInstance();
	// //String sqlCode1 = "PPMSDAO.updateVService.del";//delete from v_service
	// s where s.contentid in (?)
	// //String sqlCode2 = "PPMSDAO.updateVService.insert";//insert into
	// v_service select * from ppms_v_service s where s.contentid in (?)"
	// String sql_del =
	// SQLCode.getInstance().getSQLStatement("PPMSDAO.updateVService.del");
	// String sql_add =
	// SQLCode.getInstance().getSQLStatement("PPMSDAO.updateVService.insert");
	//			        
	// sql_del = sql_del+" ( "+cids+") ";
	// sql_add = sql_add+" ( "+cids+") ";
	//			        
	// tdb.execute(sql_del,null);
	// tdb.execute(sql_add,null);
	// tdb.commit();
	// }
	//
	// }catch(Exception e){
	// e.printStackTrace();
	// tdb.rollback();
	// throw new DAOException("����V_SERVICE���ݳ���",e);
	// }finally{
	// if(tdb!=null){
	// tdb.close();
	// }
	// }
	// }

	// public void updateResource(String pids) throws DAOException {
	// // TODO Auto-generated method stub
	// //delete from t_a_cm_device_resource s where s.pid in (?)
	// //insert into t_a_cm_device_resource s select * from
	// ppms_a_cm_device_resource s where s.pid in (?)
	// TransactionDB tdb = null;
	// try{
	// if (LOG.isDebugEnabled())
	// {
	// LOG.debug("updateResource is beginning...");
	// }
	// tdb = TransactionDB.getTransactionInstance();
	// String sqlCode1 = "PPMSDAO.updateResource.del";//delete from v_service s
	// where s.contentid in (?)
	// String sqlCode2 = "PPMSDAO.updateResource.insert";//insert into v_service
	// select * from ppms_v_service s where s.contentid in (?)
	// tdb.executeBySQLCode(sqlCode1,new Object[]{pids});
	// tdb.executeBySQLCode(sqlCode2,new Object[]{pids});
	// tdb.commit();
	// }catch(DAOException e){
	// tdb.rollback();
	// throw new DAOException("����t_a_cm_device_resource���ݳ���",e);
	// }finally{
	// if(tdb!=null){
	// tdb.close();
	// }
	// }
	// }

	public void updateLastTime(String contentid) throws DAOException {
		// TODO Auto-generated method stub
		// String sqlCode1 = "PPMSDAO.updateLastTime.del";//delete from
		// v_content_last l where l.contentid in(?) and l.osid=9;
		// String sqlCode2 = "PPMSDAO.updateLastTime.insert";//insert into
		// v_content_last(contentid,osid,createtime,updatetime) select
		// p.contentid,p.platform,p.prolupddate,p.prosubmitdate from
		// ppms_v_cm_content_program p where p.contentid in(?) and platform=9
		// tdb.executeBySQLCode(sqlCode1,new Object[]{cids});
		// tdb.executeBySQLCode(sqlCode2,new Object[]{cids});

		//
		if (LOG.isDebugEnabled()) {
			LOG.debug("����v_content_last���� beginning...");
		}
		transactionDB.executeBySQLCode("PPMSDAO.updateLastTime.del",
				new Object[] { contentid });
		transactionDB.executeBySQLCode("PPMSDAO.updateLastTime.insert",
				new Object[] { contentid });

	}

	//	
	//
	//
	// public void updateLastTime(String cids) throws DAOException {
	// // TODO Auto-generated method stub
	// //delete from v_content_last l where l.contentid in(?) and l.osid=9;
	// //insert into v_content_last(contentid,osid,createtime,updatetime) select
	// p.contentid,p.platform,p.prolupddate,p.prosubmitdate from
	// ppms_v_cm_content_program p where p.contentid in(?) and platform=9
	// TransactionDB tdb = null;
	// try{
	// if (LOG.isDebugEnabled())
	// {
	// LOG.debug("updateLastTime is beginning...");
	// }
	// tdb = TransactionDB.getTransactionInstance();
	// //String sqlCode1 = "PPMSDAO.updateLastTime.del";//delete from
	// v_content_last l where l.contentid in(?) and l.osid=9;
	// //String sqlCode2 = "PPMSDAO.updateLastTime.insert";//insert into
	// v_content_last(contentid,osid,createtime,updatetime) select
	// p.contentid,p.platform,p.prolupddate,p.prosubmitdate from
	// ppms_v_cm_content_program p where p.contentid in(?) and platform=9
	// //tdb.executeBySQLCode(sqlCode1,new Object[]{cids});
	// //tdb.executeBySQLCode(sqlCode2,new Object[]{cids});
	//	        
	//	        
	// String sql_del =
	// SQLCode.getInstance().getSQLStatement("PPMSDAO.updateLastTime.del");
	// String sql_add =
	// SQLCode.getInstance().getSQLStatement("PPMSDAO.updateLastTime.insert");
	//	        
	// sql_del = sql_del+" ( "+cids+") ";
	// sql_add = sql_add+" ( "+cids+") ";
	//	        
	// tdb.execute(sql_del,null);
	// tdb.execute(sql_add,null);
	//	        
	//	        
	// tdb.commit();
	// }catch(Exception e){
	// tdb.rollback();
	// throw new DAOException("����v_content_last���ݳ���",e);
	// }finally{
	// if(tdb!=null){
	// tdb.close();
	// }
	// }
	// }

	// select g.catename,
	// V.VALUE AS servattr,
	// r.goodsid,
	// g.contentid,
	// g.name,
	// 1 as nameboost,
	// g.singer,
	// 1 as singerboost,
	// g.spname,
	// g.fulldevicename,
	// 'O' as type,
	// s.mobileprice,
	// g.averagemark,
	// g.marketdate,
	// g.keywords,
	// g.introduction,
	// g.scantimes,
	// g.appcateid,
	// g.chargetime,
	// g.pvcid,
	// g.cityid,
	// g.othernet,
	// decode(g.provider, 'B', '99', g.subtype) as subtype,
	// g.platform,
	// '' marketingcha
	// from t_r_gcontent g,
	// t_r_reference r,
	// t_r_base b,
	// v_service s,
	// (select 'G' servattr, 'JT' VALUE
	// FROM DUAL
	// UNION ALL
	// select 'G' servattr, 'GD' VALUE
	// FROM DUAL
	// UNION ALL
	// select 'L' servattr, 'GD' VALUE FROM DUAL) v
	//	      
	// where
	//	     
	//	      
	// g.id = r.refnodeid
	// and g.icpcode = s.icpcode
	// and g.icpservid = s.icpservid
	// and g.servattr = v.servattr
	// and r.id = b.id
	// and (g.subtype is null or (g.subtype <> '6' and g.subtype <> '11' and
	// g.subtype <> '12' and g.subtype <> '16'))
	// and b.path like '{100}.{701}.{1011}%'
	// and g.contentid in (?)

	public List<SearcharFileVO> getSearchFileData(List<String> listCid,
			String option) throws DAOException, SQLException {
		List<SearcharFileVO> list = new ArrayList<SearcharFileVO>();
		LOG
				.info("getSearchFileData:getSearchFileData(List<String> listCid,String option) start..."
						+ listCid.size() + "---" + option);
		if (listCid != null && listCid.size() > 0) {
			int fromIndex = 0;
			int toIndex = 0;
			int stepSize = 900;
			LOG.info("getSearchFileData��ʼ...��SQL����IN�����1000���ͱ��������IN���涨��900��");
			do {
				toIndex = fromIndex + stepSize;
				if (toIndex > listCid.size()) {
					toIndex = listCid.size();
				}
				LOG.info("getSearchFileData �� " + fromIndex + " �� " + toIndex
						+ " stepSize:" + stepSize);
				List<String> sub = listCid.subList(fromIndex, toIndex);
				LOG.info("getSearchFileData:sub:size" + sub.size());
				StringBuilder sb = new StringBuilder();
				for (String contentid : sub) {
					sb.append(",").append("'" + contentid + "'");
				}
				LOG.info("getSearchFileData:sb.length()" + sb.length());
				if (sb.length() > 0) {
					String contentids = sb.substring(1);
					try {
						List<SearcharFileVO> subList = null;
						if ("A".equals(option)) {
							LOG.info("getSearchFileData:A.equals(option)"
									+ contentids);
							subList = getSubSearchFileDataA(contentids, option);
						} else if ("U".equals(option)) {
							LOG.info("getSearchFileData:U.equals(option)"
									+ contentids);
							subList = getSubSearchFileDataU(contentids, option);
						}
						LOG
								.info("getSearchFileData:list.addAll(subList) before-> list.size:"
										+ list.size()
										+ "  subList.size:"
										+ subList.size());
						list.addAll(subList);
						LOG
								.info("getSearchFileData:list.addAll(subList) end-> list.size:"
										+ list.size()
										+ "  subList.size:"
										+ subList.size());
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						LOG.error("getSearchFileData���ִ���", e);
					}
				}
				LOG.info("getSearchFileData:fromIndex and toIndex-> "
						+ fromIndex + "---" + toIndex);
				fromIndex = toIndex;
			} while (toIndex < listCid.size());

		}
		LOG
				.info("getSearchFileData:getSearchFileData(List<String> listCid,String option) end...list.size:"
						+ list.size());
		return list;
	}

	private List<SearcharFileVO> getSubSearchFileDataA(String contentids,
			String option) throws DAOException, SQLException {
		// TODO Auto-generated method stub
		LOG
				.info("getSearchFileData:getSubSearchFileDataA(String contentids,String option) start...contentids:"
						+ contentids + "---option:" + option);
		List<SearcharFileVO> list = new ArrayList<SearcharFileVO>();
		String sql = "select g.catename, V.VALUE AS servattr, r.goodsid, g.contentid, g.name, 1 as nameboost, g.singer, 1 as singerboost, g.spname, g.fulldevicename, 'O' as type, s.mobileprice, g.averagemark, g.marketdate, g.keywords, g.introduction, g.scantimes, g.appcateid, g.chargetime, g.pvcid, g.cityid, g.othernet, decode(g.provider, 'B', '99', g.subtype) as subtype, g.platform,'',g.logo3,g.logo4,g.COPYRIGHTFLAG from  t_r_gcontent g, t_r_reference r, t_r_base b, v_a_service s, (select 'G' servattr,'JT' VALUE FROM DUAL UNION ALL select 'G' servattr,'GD' VALUE FROM DUAL UNION ALL select 'L' servattr,'GD' VALUE FROM DUAL) v where g.id = r.refnodeid and g.icpcode = s.icpcode and g.icpservid = s.icpservid and g.contentid = s.contentid and g.servattr = v.servattr and r.id = b.id and (g.subtype is null or (g.subtype <> '6' and g.subtype <> '11' and g.subtype <> '12' and g.subtype <> '16')) and b.path like '{100}.{701}.{1257453}.{678936436}.{678936507}%'";// DB.getInstance().getSQLByCode("PPMSDAO.getSearchFileDataA");
		sql = sql + " and g.CHANNELDISPTYPE=0";
		sql = sql + " and g.contentid in (" + contentids + ")";

		RowSet rs = DB.getInstance().query(sql, null);
		LOG.info("search sql:" + sql);
		while (rs.next()) {
			SearcharFileVO vo = new SearcharFileVO();
			vo.setOption(option);
			vo.setFatherCategory(getCateType(rs.getString("catename")));
			vo.setRange(rs.getString("servattr"));
			String goodsid = rs.getString("goodsid");// change by tungke
			// 20130819

			vo.setGoodsID(getGoodsIdByVirtual(goodsid));
			vo.setProdID(rs.getString("contentid"));
			vo.setName(rs.getString("name"));

			vo.setNameBoost(rs.getString("nameboost"));
			vo.setSinger(rs.getString("singer"));
			vo.setSingerBoost(rs.getString("singerboost"));
			vo.setSpName(replaceString(rs.getString("spname")));//
			vo.setFullDevice(formatNewFullDevice(getNewFullDevice(rs
					.getString("contentid"))));

			vo.setType(rs.getString("type"));
			vo.setPrice(rs.getString("mobileprice"));
			vo.setStar(rs.getString("averagemark"));
			vo.setDate(rs.getString("marketdate"));
			vo.setLabel(replaceString(rs.getString("keywords")));//

			vo.setBrief(replaceString(rs.getString("introduction")));//
			vo.setHot(rs.getString("scantimes"));
			vo.setChildCategory(rs.getString("appcateid"));
			vo.setChargeTime(rs.getString("chargetime"));
			vo.setProvince(rs.getString("pvcid"));

			vo.setCity(rs.getString("cityid"));
			vo.setOtherNet(rs.getString("othernet"));
			vo.setSubType(rs.getString("subtype"));
			vo.setPlatform(rs.getString("platform"));
			vo.setMarketingCha("");// ����ط��͸����ֵ��
			vo.setImgurl1(rs.getString("logo3"));
			vo.setImgurl2(rs.getString("logo4"));
			vo.setCopyRightFlag(rs.getString("COPYRIGHTFLAG"));
			list.add(vo);
		}
		LOG
				.info("getSearchFileData:getSubSearchFileDataA(String contentids,String option) end...list.size"
						+ list.size());
		return list;
	}

	private List<SearcharFileVO> getSubSearchFileDataU(String contentids,
			String option) throws DAOException, SQLException {
		// TODO Auto-generated method stub
		LOG
				.info("getSearchFileData:getSubSearchFileDataU(String contentids,String option) start...contentids:"
						+ contentids + "---option:" + option);
		List<SearcharFileVO> list = new ArrayList<SearcharFileVO>();
		String sql = "select g.catename, V.VALUE AS servattr, r.goodsid, g.contentid, g.name, 1 as nameboost, g.singer, 1 as singerboost, g.spname, g.fulldevicename, 'O' as type, s.mobileprice, g.averagemark, g.marketdate, g.keywords, g.introduction, g.scantimes, g.appcateid, g.chargetime, g.pvcid, g.cityid, g.othernet, decode(g.provider, 'B', '99', g.subtype) as subtype, g.platform,'',g.logo3,g.logo4,g.COPYRIGHTFLAG  from  t_r_gcontent g, t_r_reference r, t_r_base b, v_service s, (select 'G' servattr,'JT' VALUE FROM DUAL UNION ALL select 'G' servattr,'GD' VALUE FROM DUAL UNION ALL select 'L' servattr,'GD' VALUE FROM DUAL) v where g.id = r.refnodeid and g.icpcode = s.icpcode and g.icpservid = s.icpservid and g.contentid = s.contentid and g.servattr = v.servattr and r.id = b.id and (g.subtype is null or (g.subtype <> '6' and g.subtype <> '11' and g.subtype <> '12' and g.subtype <> '16')) and b.path like '{100}.{701}.{1011}%'";// DB.getInstance().getSQLByCode("PPMSDAO.getSearchFileDataU");
		sql = sql + " and g.CHANNELDISPTYPE=0";
		sql = sql + " and g.contentid in (" + contentids + ")";
		LOG.info("search sql:heh..." + sql);
		ResultSet rs = DB.getInstance().query(sql, null);
		LOG.info("search sql:hah" + sql);
		while (rs.next()) {
			SearcharFileVO vo = new SearcharFileVO();
			vo.setOption(option);
			vo.setFatherCategory(getCateType(rs.getString("catename")));
			vo.setRange(rs.getString("servattr"));

			String goodsid = rs.getString("goodsid");// change by tungke
			// 20130819

			vo.setGoodsID(getGoodsIdByVirtual(goodsid));

			vo.setProdID(rs.getString("contentid"));
			vo.setName(rs.getString("name"));

			vo.setNameBoost(rs.getString("nameboost"));
			vo.setSinger(rs.getString("singer"));
			vo.setSingerBoost(rs.getString("singerboost"));
			vo.setSpName(replaceString(rs.getString("spname")));//
			// vo.setFullDevice(formatNewFullDevice(getNewFullDevice(rs
			// .getString("contentid"))));

			vo.setFullDevice("");
			try {
				vo.setFullDevice(getClobString(rs.getClob("fulldevicename")));
			} catch (Exception e) {
				LOG.error(e + "�����ԡ�����hehe....", e);
			}

			vo.setType(rs.getString("type"));
			vo.setPrice(rs.getString("mobileprice"));
			vo.setStar(rs.getString("averagemark"));
			vo.setDate(rs.getString("marketdate"));
			vo.setLabel(replaceString(rs.getString("keywords")));//

			vo.setBrief(replaceString(rs.getString("introduction")));//
			vo.setHot(rs.getString("scantimes"));
			vo.setChildCategory(rs.getString("appcateid"));
			vo.setChargeTime(rs.getString("chargetime"));
			vo.setProvince(rs.getString("pvcid"));

			vo.setCity(rs.getString("cityid"));
			vo.setOtherNet(rs.getString("othernet"));
			vo.setSubType(rs.getString("subtype"));
			vo.setPlatform(rs.getString("platform"));
			vo.setMarketingCha("");// ����ط��͸����ֵ��
			vo.setImgurl1(rs.getString("logo3"));
			vo.setImgurl2(rs.getString("logo4"));
			vo.setCopyRightFlag(rs.getString("COPYRIGHTFLAG"));

			list.add(vo);
		}
		LOG
				.info("getSearchFileData:getSubSearchFileDataU(String contentids,String option) end...list.size:"
						+ list.size());

		return list;
	}

	private String getClobString(Clob clob) {

		LOG.debug("clob == null:" + (clob == null));
		if (clob == null) {
			return "";
		}
		long len;
		try {
			LOG.debug("clob.length()" + clob.length());
			len = clob.length();
			return clob.getSubString(0, (int) len);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
		}
		return "";

	}

	/**
	 * �����滻Ҫ����������ַ�
	 * 
	 * @param temp
	 * @return
	 */
	private String replaceString(String temp) {
		if (temp == null)
			return "";

		temp = temp.replaceAll("\r", "");
		temp = temp.replaceAll("\f", "");
		temp = temp.replaceAll(String.valueOf(char01), "");
		temp = temp.replaceAll(String.valueOf(char0c), "");
		temp = temp.replaceAll("\n", "");

		return temp;
	}

	private List<String> getNewFullDevice(String contentid) {
		// TODO Auto-generated method stub
		// select device_name from T_A_CM_DEVICE_RESOURCE dd where
		// dd.contentid=300001510997
		List<String> list = new ArrayList<String>();
		String sqlCode = "PPMSDAO.getNewFullDevice().SELECT";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentid });
			while (rs != null && rs.next()) {
				list.add(rs.getString("device_name"));
			}
		} catch (Exception ex) {
			// throw new DAOException("getNewFullDevice error!!", ex);
			LOG.error("getNewFullDevice error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	// {HTCLEGEND},{dopodA8188},{Ħ������MILESTONE},{�ȸ�NEXUSONE}
	// ��LIST<String> ���ݸ�ʽ�����������ʽ��
	private String formatNewFullDevice(List<String> list) {
		if (list == null || list.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String deviceName : list) {
			sb.append(",").append("{").append(
					PublicUtil.filterMbrandEspecialChar(deviceName))
					.append("}");
		}
		if (sb.length() > 0) {
			return sb.substring(1);
		}
		return "";
	}

	// �����������ߵ����ݣ�Ϊ�˽��������߽ӿ���ɾ����Ʒ��������׼������������ֻ��Ҫ׼��catename��goodsid��contentid�Ϳ����ˡ�
	// dataexport.basefile.TempExigenceFile.getODelData=
	// select 'D' as options,
	// b.catetype as catename,
	// '' AS servattr,
	// b.goodsid,
	// b.contentid,
	// '' as name,
	// 1 as nameboost,
	// '' as singer,
	// 1 as singerboost,
	// '' as spname,
	// '' as fulldevicename,
	// 'O' as type,
	// 0 as mobileprice,
	// 0 as averagemark,
	// '' as marketdate,
	// '' as keywords,
	// '' as introduction,
	// '' as scantimes,
	// '' as appcateid,
	// '' as chargetime,
	// '' as pvcid,
	// '' as cityid,
	// '' as othernet,
	// '' as subtype,
	// 'all' as platform,
	// h.marketingcha
	// from t_exigencecontent t, t_goods_change_his b, V_CM_CONTENT_DARKHORSE h
	// where b.contentid = t.contentid
	// and b.contentid = h.contentid(+)
	// and t.type = 3
	// and b.path like '{100}.{701}.{1011}%'
	public List<SearcharFileVO> getSearchFileDataDel(List<DelGoodsVO> list)
			throws DAOException, SQLException {
		// TODO Auto-generated method stub

		List<SearcharFileVO> l = new ArrayList<SearcharFileVO>();
		for (int i = 0; i < list.size(); i++) {
			DelGoodsVO v = list.get(i);
			SearcharFileVO vo = new SearcharFileVO();
			vo.setOption("D");
			vo.setFatherCategory(getCateType(v.getCatename()));
			vo.setRange("");

			String goodsid = v.getGoodsid();// change by tungke 20130819

			vo.setGoodsID(getGoodsIdByVirtual(goodsid));

			// vo.setGoodsID(v.getGoodsid());
			vo.setProdID(v.getContentid());
			vo.setName("");

			vo.setNameBoost("1");
			vo.setSinger("");
			vo.setSingerBoost("1");
			vo.setSpName("");
			vo.setFullDevice("");

			vo.setType("O");
			vo.setPrice("0");
			vo.setStar("0");
			vo.setDate("");
			vo.setLabel("");

			vo.setBrief("");
			vo.setHot("");
			vo.setChildCategory("");
			vo.setChargeTime("");
			vo.setProvince("");

			vo.setCity("");
			vo.setSubType("");
			vo.setPlatform("all");
			vo.setMarketingCha("");// ����ط��͸����ֵ��

			l.add(vo);
		}
		return l;
	}

	/**
	 * �Á�ēQһ��������
	 * 
	 * @param cateName
	 * @return
	 */
	private String getCateType(String cateName) {
		String temp = "";

		if ("���".equals(cateName)) {
			temp = "appSoftWare";
		} else if ("��Ϸ".equals(cateName)) {
			temp = "appGame";
		} else if ("����".equals(cateName)) {
			temp = "appTheme";
		}
		return temp;
	}

	private String getContenttype(String cateName) {
		String temp = "";

		if ("���".equals(cateName)) {
			temp = "nt:gcontent:appSoftWare";
		} else if ("��Ϸ".equals(cateName)) {
			temp = "nt:gcontent:appGame";
		} else if ("����".equals(cateName)) {
			temp = "nt:gcontent:appTheme";
		}
		return temp;
	}

	// public static void main(String[] argv){
	// List<DelGoodsVO> l = new ArrayList<DelGoodsVO>();//new
	// ArrayList<DelGoodsVO>();
	//
	// for(DelGoodsVO s:l){
	// System.out.println(s);
	// }
	// }

	public boolean isExistRefInAndrodCategory(GContentVO vo) {
		// TODO Auto-generated method stub

		ResultSet rs = null;
		try {
			int k = 1;
			int num1 = 0, num2 = 0;
			String sqlCode1 = "PPMSDAO.isExistRefInAndrodCategory.SELECT1";
			// select count(1)
			// from t_sync_tactic_android s
			// left join t_r_category c on s.categoryid \= c.id
			// where s.contenttype \= ? and s.appcatename \= ?
			String sqlCode2 = "PPMSDAO.isExistRefInAndrodCategory.SELECT2";
			// select count(distinct r.categoryid)
			// from t_r_reference r,t_r_gcontent g
			// where r.refnodeid\=g.id and r.categoryid in
			// (select c.categoryid
			// from t_sync_tactic_android s
			// left join t_r_category c on s.categoryid \= c.id
			// where s.contenttype \= ? and s.appcatename \= ?)
			// and g.contentid\=?

			// (add 2014-05-07) ���HTCӦ�����⴦��
			if ("16".equals(vo.getSubtype())) {
				sqlCode1 = "PPMSDAO.isExistRefInHTCCategory.SELECT1";
				// select count(1)
				// from t_sync_tactic_moto s
				// left join t_r_category c on s.categoryid \= c.id
				// where s.appcatename \= ? and s.subtype \= '16'
				String appId = vo.getContenttag();
				String cateName = null;// MOTOһ������
				String appcateName = null;// MOTO��������
				try {
					String[] res = DataSyncDAO.getInstance()
							.getHTCAppCateNameById(appId);
					if (null != res && res.length == 2) {
						cateName = res[0];
						appcateName = res[1];
					}
				} catch (DAOException e) {
					e.printStackTrace();
					throw new BOException("��ѯHTCӦ��HTCһ�����࣬��������ʧ��", e);
				}
				if (null == appcateName)
					return false;
				rs = DB.getInstance().queryBySQLCode(sqlCode1,
						new String[] { appcateName });
				if (rs.next()) {
					num1 = rs.getInt(1);
				}

				sqlCode2 = "PPMSDAO.isExistRefInHTCCategory.SELECT2";
				// select count(distinct r.categoryid)
				// from t_r_reference r,t_r_gcontent g
				// where r.refnodeid\=g.id and r.categoryid in
				// ( select c.categoryid
				// from t_sync_tactic_moto s
				// left join t_r_category c on s.categoryid \= c.id
				// where s.appcatename \= ? and s.subtype = '16') and
				// g.contentid\=?
				rs = DB.getInstance()
						.queryBySQLCode(
								sqlCode2,
								new String[] { vo.getAppcatename(),
										vo.getContentId() });
				if (rs.next()) {
					num2 = rs.getInt(1);
				}
			} else {
				rs = DB.getInstance().queryBySQLCode(
						sqlCode1,
						new String[] { getContenttype(vo.getCatename()),
								vo.getAppcatename() });
				if (rs.next()) {
					num1 = rs.getInt(1);
				}

				rs = DB.getInstance().queryBySQLCode(
						sqlCode2,
						new String[] { getContenttype(vo.getCatename()),
								vo.getAppcatename(), vo.getContentId() });
				if (rs.next()) {
					num2 = rs.getInt(1);
				}
			}

			LOG.debug("��Ʒ���Ż����жϻ������Ƿ�����Ʒ��" + num1 + "---" + num2);
			if (num1 != 0 && num2 != 0 && num1 == num2) {
				return true;
			}
		} catch (Exception ex) {
			// throw new DAOException("getNewFullDevice error!!", ex);
			LOG.error("isExistRefInAndrodCategory error!!", ex);
		} finally {
			DB.close(rs);
		}
		return false;
	}

	public static boolean validateGcontent(String contentid) {
		// TODO Auto-generated method stub
		// select 1 from t_r_gcontent where contentid =?
		ResultSet rs = null;
		try {
			String sqlCode1 = "PPMSDAO.validateGcontent";
			rs = DB.getInstance().queryBySQLCode(sqlCode1,
					new String[] { contentid });
			return rs.next();
		} catch (Exception ex) {
			LOG.error("validateGcontent error!!", ex);
		} finally {
			DB.close(rs);
		}
		return false;
	}

	public static boolean validateVService(String contentid) {
		// TODO Auto-generated method stub
		// select 1 from ppms_v_service where contentid =?
		ResultSet rs = null;
		try {
			String sqlCode1 = "PPMSDAO.validateVService";
			rs = DB.getInstance().queryBySQLCode(sqlCode1,
					new String[] { contentid });
			return rs.next();
		} catch (Exception ex) {
			// throw new DAOException("getNewFullDevice error!!", ex);
			LOG.error("validateVService error!!", ex);
		} finally {
			DB.close(rs);
		}
		return false;
	}

	public static boolean validateResource(String contentid) {
		// TODO Auto-generated method stub
		// select 1 from V_DC_CM_DEVICE_RESOURCE where contentid=?
		long startTime = System.currentTimeMillis();
		ResultSet rs = null;
		try {
			// String sqlCode1 = "PPMSDAO.validateResource";
			// rs = DB.getInstance().queryBySQLCode(sqlCode1,
			// new String[] { contentid });

			// ȷ����һ��������������
			// �»�ƽ˵select 1 from V_DC_CM_DEVICE_RESOURCE where
			// contentid=?��Դ���Ĵ�������ʾ�ļ������ţ�����Ч���� add by aiyan 2013-07-24
			rs = DB.getInstance().query(
					"select 1 from V_DC_CM_DEVICE_RESOURCE where contentid=?",
					new Object[] { contentid });

			return rs.next();
		} catch (Exception ex) {
			// throw new DAOException("getNewFullDevice error!!", ex);
			LOG.error("validateResource error!!", ex);
		} finally {
			DB.close(rs);
		}
		LOG.debug("cost time:�ӵ������õ��Ƿ�����Դ��ʱ�䣺"
				+ (System.currentTimeMillis() - startTime));
		return false;
	}

	public void updateFullDevice(String contentId,
			List<String> fullDeviceIdList, List<String> fullDeviceNameList) {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		if (fullDeviceIdList == null || fullDeviceIdList.size() == 0) {
			return;
		}
		try {
			// update t_r_gcontent set fulldeviceid=empty_clob() where
			// contentid='"+contentId+"'"

			String sqlCode = "PPMSDAO.updateFullDevice";
			String sqlClobQuery = "select fulldeviceid,fulldevicename from t_r_gcontent  where contentid='"
					+ contentId + "' for update";
			String sqlClobUpdate = "update t_r_gcontent set fulldeviceid=?,fulldevicename=? where contentid='"
					+ contentId + "'";
			String fullDeviceid = list2String(fullDeviceIdList);
			String fullDeviceName = PublicUtil
					.filterMbrandEspecialChar(list2String(fullDeviceNameList));

			DB.getInstance().executeBySQLCodeWithClob(sqlCode,
					new String[] { contentId }, sqlClobQuery, sqlClobUpdate,
					new String[] { fullDeviceid, fullDeviceName });
			LOG.debug("���� " + contentId + " �ɹ�����updateFullDevice:hehe...");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			LOG.error("�ڴ���FULLDEVICEID��ʱ��������⡣", e);
		}
		LOG.debug("cost time:����FULLDEVICE��ʱ�䣺"
				+ (System.currentTimeMillis() - startTime));
	}

	/**
	 * ��list�е�����ƴװΪ {devicename},{devicename}�� ����ʽ
	 * 
	 * @param list
	 *            ����deviceName�ļ���
	 * @return ���磺{devicename1},{devicename2}�� ���ַ���
	 */
	private String list2String(Collection collection) {
		StringBuffer sb = new StringBuffer();
		Iterator iterator = collection.iterator();
		boolean dotFlag = false;// ��һ�β���Ҫ���붺��
		while (iterator.hasNext()) {
			if (dotFlag) {
				sb.append(",");
			} else {
				dotFlag = true;
			}
			sb.append("{");
			sb.append(iterator.next());
			sb.append("}");
		}
		return sb.toString();
	}

	public static boolean isFreeContent(String contentId) {
		// TODO Auto-generated method stub
		ResultSet rs = null;
		try {
			// select 1 from v_service v where v.contentid=? and( v.paytype ='0'
			// or v.paytype is null ) group by contentid having
			// sum(v.mobileprice)=0
			String sqlCode = "PPMSDAO.isFreeContent";
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentId });
			return rs.next();
		} catch (Exception ex) {
			// throw new DAOException("getNewFullDevice error!!", ex);
			LOG.error("isFreeContent error!!", ex);
		} finally {
			DB.close(rs);
		}
		return false;
	}

	public static Map<String, List<String>> getFullDeviceAll(String contentId) {
		// TODO Auto-generated method stub
		Map<String, List<String>> fullDeviceMap = new HashMap<String, List<String>>();
		List<String> fullDeviceIdList = new ArrayList<String>();
		List<String> fullDeviceNameList = new ArrayList<String>();
		try {// select distinct device_id,device_name from
			// v_cm_device_resource where contentid='300000070105'
			String sqlCode = "PPMSDAO.getFullDeviceAll";
			// RowSet rs = DB.getInstance().queryBySQLCode(sqlCode, new
			// String[]{contentId});
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentId });
			while (rs.next()) {
				fullDeviceIdList.add(rs.getString("device_id"));
				fullDeviceNameList.add(rs.getString("device_name"));
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		fullDeviceMap.put("fullDeviceIdList", fullDeviceIdList);
		fullDeviceMap.put("fullDeviceNameList", fullDeviceNameList);
		return fullDeviceMap;
	}

	public static Map<String, List<String>> getFullDeviceMatch(String contentId) {
		// TODO Auto-generated method stub
		Map<String, List<String>> fullDeviceMap = new HashMap<String, List<String>>();
		List<String> fullDeviceIdList = new ArrayList<String>();
		List<String> fullDeviceNameList = new ArrayList<String>();
		try {
			String sqlCode = "PPMSDAO.getFullDeviceMatch";
			RowSet rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentId });
			while (rs.next()) {
				fullDeviceIdList.add(rs.getString("device_id"));
				fullDeviceNameList.add(rs.getString("device_name"));
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		fullDeviceMap.put("fullDeviceIdList", fullDeviceIdList);
		fullDeviceMap.put("fullDeviceNameList", fullDeviceNameList);
		return fullDeviceMap;
	}

	public String getGoodsIdByVirtual(String goodsId) {
		String virtual = SearchFileConfig.VIRTUAL_CATEGORY_ID;
		virtual = virtual + goodsId.substring(9);
		return virtual;
	}

	/**
	 * 
	 * @param contentid
	 *            ����Ӧ�ò������������������Ϣ����
	 * 
	 */
	public void deldeviceUpgrade(String contentid) throws DAOException {
		String sqlCode = "PPMSDAO.deldeviceUpgrade";
		// insert into s_cm_ct_device_upgrade_del2 select
		// SEQ_CM_UPGRADE_DEL2_ID.Nextval,t.contentid as contentid,t.device_id,
		// sysdate,t.clientid from ( select distinct r.contentid as
		// contentid,r1.adapte_id as device_id,r.clientid from cm_ct_program
		// r,cm_ct_device r1 where r.pid=r1.pid and r1.teststatus=1 and
		// r.clientid is not null and r.publishtomm='1' and r.status in
		// ('0007','1007') and (r.expdate>sysdate+2 or r.expdate is null) and
		// r.cpsignstatus in('0','4') and r.platform in('3','9') and
		// r.channelproperty=0 and r1.adapte_id<1000 and r.contentid = ?) t
		Object[] paras = { contentid };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ��������id��ѯ����Ϣ�б�
	 * 
	 * @param contentid
	 * @param online
	 * @return
	 * @throws DAOException
	 */
	public List<PackageVO> getPackageInfoListByContentID(String contentid,
			int online) throws DAOException {

		List<PackageVO> list = new ArrayList<PackageVO>();
		// select distinct a.clientid as
		// pkgname,a.version,a.cermd5,decode((select s.mobileprice from
		// t_r_gcontent g,v_service s where g.contentid = s.contentid and
		// g.icpservid = s.icpservid and s.contentid =
		// a.contentid),null,1,0,1,0) as deduction from t_a_cm_device_resource a
		// where a.contentid = ?
		String sqlCode = "PPMSDAO.getPackageInfoListByContentID().SELECT";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentid });
			while (rs != null && rs.next()) {
				PackageVO vo = new PackageVO();
				vo.setPackageName(rs.getString("pkgname"));
				vo.setVersionCode(rs.getString("version"));
				vo.setCermd5(rs.getString("cermd5"));
				vo.setOnline(online);
				vo.setDeduction(rs.getString("deduction"));
				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getPackageInfoListByContentID error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;

	}

	/**
	 * ��������ID�õ�֧�ֵ�����ƽ̨���ϣ���{}���߽磬���ŷָ�
	 * 
	 * @param contentID
	 * @return
	 * @throws DAOException
	 */
	private String getPlatformByContentID(String contentID) throws DAOException {
		// ���������л�ȡkjavaƽ̨���͵���չ����
		String platformExt = "";
		try {
			platformExt = Config.getInstance().getModuleConfig().getItemValue(
					"platformExt");
		} catch (Exception e1) {
			LOG.error("���������л�ȡkjavaƽ̨���͵���չ�����ǳ���");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPlatformByContentID(" + contentID + ")");
		}
		if (contentID == null) {
			return null;
		}
		String sqlCode = "SyncData.DataSyncDAO.getPlatformByContentID().SELECT";
		Object[] paras = { contentID };
		ResultSet rs = null;
		rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		String platform = "";
		// ͳ��֧�ֵ�ƽ̨����
		int i = 0;
		try {
			String tmp = null;
			while (rs != null && rs.next()) {
				tmp = rs.getString("platform").toLowerCase();
				if (platform.indexOf(tmp) == -1) {
					if (i >= 1) {
						platform = platform + ",";
					}
					platform = platform + "{" + tmp + "}";
				}
				if ("kjava".equalsIgnoreCase(tmp)
						&& "1".equals(rs.getString("platformExt"))
						&& platform.indexOf(platformExt) == -1) {
					platform = platform + "," + "{" + platformExt + "}";
				}
				i++;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DB.close(rs);
		}
		return platform;
	}

	public void deleteContentid(String contentid) throws DAOException {
		String sqlCode1 = "SyncAndroid.APPInfoDAO.deleteContentid().DELETE1";
		String sqlCode2 = "SyncAndroid.APPInfoDAO.deleteContentid().DELETE2";
		String sqlCode3 = "SyncAndroid.APPInfoDAO.deleteContentid().DELETE3";
		String sqlCode4 = "SyncAndroid.APPInfoDAO.deleteContentid().DELETE4";
		String sqlCode5 = "SyncAndroid.APPInfoDAO.deleteContentid().DELETE5";
		String sqlCode6 = "SyncAndroid.APPInfoDAO.deleteContentid().DELETE6";
		Object[] paras = { contentid };
		try {
			transactionDB.executeBySQLCode(sqlCode1, paras);
			transactionDB.executeBySQLCode(sqlCode2, paras);
			transactionDB.executeBySQLCode(sqlCode3, paras);
			transactionDB.executeBySQLCode(sqlCode4, paras);
			transactionDB.executeBySQLCode(sqlCode5, paras);
			transactionDB.executeBySQLCode(sqlCode6, paras);
			// DB.getInstance().executeBySQLCode(sqlCode1, paras);
			// DB.getInstance().executeBySQLCode(sqlCode2, paras);
			// DB.getInstance().executeBySQLCode(sqlCode3, paras);
			// DB.getInstance().executeBySQLCode(sqlCode4, paras);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	/**
	 * ����contentid ��ѯ���ձ�ǩ
	 * 
	 * @param contentid
	 * @param online
	 * @return
	 * @throws DAOException
	 */
	public String getDeviceRiskTag(String contentid) throws DAOException {

		String riskTag = "";
		// select * from T_A_CM_DEVICE_RESOURCE where contentid \=?
		String sqlCode = "PPMSDAO.getDeviceRiskTag";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentid });
			while (rs != null && rs.next()) {
				riskTag = rs.getString("risktag");
			}
		} catch (Exception ex) {
			throw new DAOException("getPackageInfoListByContentID error!!", ex);
		} finally {
			DB.close(rs);
		}
		return riskTag;

	}

	/**
	 * ����contentid ��ѯ���ձ�ǩ
	 * 
	 * @param contentid
	 * @param online
	 * @return
	 * @throws DAOException
	 */
	public String getGcontentRiskTag(String contentid) throws DAOException {

		String riskTag = "";
		// select * from t_r_gcontent where contentid \=?
		String sqlCode = "PPMSDAO.getGcontentRiskTag";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode,
					new String[] { contentid });
			while (rs != null && rs.next()) {
				riskTag = rs.getString("risktag");
			}
		} catch (Exception ex) {
			throw new DAOException("getPackageInfoListByContentID error!!", ex);
		} finally {
			DB.close(rs);
		}
		return riskTag;

	}

	/**
	 * �޸�gcontent���е�risktag
	 * 
	 * @param contentid
	 * @param online
	 * @throws DAOException
	 */
	public void updateGcontentRiskTag(String contentid, String risktag)
			throws DAOException {

		// update t_r_gcontent set risktag \= ? where contentid \= ?
		String sql = "update t_r_gcontent set risktag =  ?  where contentid = ?";
		ResultSet rs = null;

		try {
			DB.getInstance().execute(sql, new String[] { risktag, contentid });
		} catch (Exception ex) {
			throw new DAOException("updateGcontentRiskTag error!!", ex);
		} finally {
			DB.close(rs);
		}
	}

	/**
	 * ����appid��ѯssmsϵͳ�»���������Ʒ
	 * 
	 * @param appid
	 * @return
	 * @throws DAOException
	 */
	public List<ReferenceVO> getReferenceByAppid(String appid)
			throws DAOException {
		List<ReferenceVO> list = new ArrayList<ReferenceVO>();
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getIdFromReferenceByCondition.SELECT");
		try {
			rs = DB.getInstance().query(sql, new String[] { appid });
			ReferenceVO vo;
			while (rs != null && rs.next()) {
				vo = new ReferenceVO();
				vo.setId(rs.getString("id"));
				vo.setRefnodeid(rs.getString("refnodeid"));
				vo.setCategoryid(rs.getString("categoryid"));
				vo.setGoodsid(rs.getString("goodsid"));
				vo.setSortid(rs.getInt("sortid"));
				vo.setLoaddate(rs.getString("loaddate"));
				vo.setDelflag(rs.getInt("delflag"));
				vo.setVariation(rs.getInt("variation"));
				vo.setVerify_status(rs.getString("verify_status"));
				vo.setVerify_date(rs.getDate("verify_date"));
				vo.setDelflag(rs.getInt("delflag"));
				vo.setIslock(rs.getInt("islock"));
				vo.setLocktime(rs.getDate("locktime"));
				vo.setLockuser(rs.getString("lockuser"));
				vo.setLocknum(rs.getInt("locknum"));
				vo.setAppid(rs.getString("appid"));
				vo.setParentid(rs.getString("parentid"));
				vo.setPath(rs.getString("path"));
				vo.setType(rs.getString("type"));
				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getDelGoodsList error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	/**
	 * ����appid��ȡ��newpas�����е���Ʒ
	 * 
	 * @param appid
	 * @return
	 * @throws DAOException
	 */
	public List<TDReferenceVO> getNewpasReferenceByAppid(String appid)
			throws DAOException {
		List<TDReferenceVO> list = new ArrayList<TDReferenceVO>();
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getIdFromNewpasReferenceByCondition.SELECT");
		try {
			rs = DB.getInstance().query(sql, new String[] { appid });
			TDReferenceVO vo;
			while (rs != null && rs.next()) {
				vo = new TDReferenceVO();
				vo.setId(rs.getString("id"));
				vo.setActivetime(rs.getString("activetime"));
				vo.setCid(rs.getString("cid"));
				vo.setCname(rs.getString("cname"));
				vo.setLabelfilter(rs.getString("labelfilter"));
				vo.setLupdate(rs.getDate("lupdate"));
				vo.setInvalidtime(rs.getString("invalidtime"));
				vo.setOpuser(rs.getString("opuser"));
				vo.setWeight(rs.getInt("weight"));
				vo.setCategoryid(rs.getString("categoryid"));
				vo.setGoodsid(rs.getString("goodsid"));
				vo.setSortid(rs.getInt("sortid"));
				vo.setRkey(rs.getString("rkey"));
				vo.setCtype(rs.getString("ctype"));
				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getDelGoodsList error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	public void insertNewpasReference(TDReferenceVO vo) throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("insertNewpasReference(" + vo.getCategoryid() + ")");
		}
		String sqlCode = "PPMSDAO.insertNewpasReference.insert";
		Object[] paras = {};
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public void deleteNewpasReference(TDReferenceVO vo) throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleteNewpasReference(" + vo.getCategoryid() + ")");
		}
		String sqlCode = "PPMSDAO.deleteNewpasReference.delete";
		Object[] paras = { vo.getGoodsid() };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public String getSecCategory(String cateName) throws DAOException {

		Object[] paras = { cateName };
		String sqlCode = "PPMSDAO.getSecCategory().SELECT";
		String cm = "";
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

			if (rs.next()) {
				cm = rs.getString("operatorseccate");
			}

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DB.close(rs);
		}
		return cm;
	}

	public SingleCategoryVO getSingleCategoryVo(String id) throws DAOException {

		Object[] paras = { id };
		String sqlCode = "PPMSDAO.getCategoryByCategoryId().SELECT";
		SingleCategoryVO cm = new SingleCategoryVO();
		ResultSet rs = null;
		try {
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

			if (rs.next()) {
				cm.setId(rs.getString("id"));
				cm.setCategoryid(rs.getString("categoryid"));
				cm.setParentcategoryid(rs.getString("parentcategoryid"));
				cm.setName(rs.getString("name"));
			}

		} catch (Exception e) {
			throw new DAOException(e);
		} finally {
			DB.close(rs);
		}
		return cm;
	}

	public void updateReferenceAppid(String appid, String contentid)
			throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateReferenceAppid(" + appid + ")");
		}
		String sqlCode = "PPMSDAO.updateReferenceAppid.update";
		Object[] paras = { appid, contentid };
		transactionDB.executeBySQLCode(sqlCode, paras);
		
	}
	
	
	public void updateTDReferenceAppid(String appid, String contentid)
			throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTDReferenceAppid(" + appid + ")");
		}
		String sqlCode = "PPMSDAO.updateTDReferenceAppid.update";
		Object[] paras = { appid, contentid };
		transactionDB.executeBySQLCode(sqlCode, paras);
		
	}
	
	public void updateTRGcontentAppid(String appid, String contentid)
			throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTRGcontentAppid(" + appid + ")");
		}
		String sqlCode = "PPMSDAO.updateTRGcontentAppid.update";
		Object[] paras = { appid, contentid };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public void deleteTRGappAppid(String appid, String contentid)
			throws DAOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTRGcontentAppid(" + appid + ")");
		}
		String sqlCode = "PPMSDAO.deleteTRGappAppid.delete";
		Object[] paras = { appid, contentid };
		transactionDB.executeBySQLCode(sqlCode, paras);
	}

	public List<GoodsCenterMessagesVo> getMessageByState() throws DAOException {
		List<GoodsCenterMessagesVo> list = new ArrayList<GoodsCenterMessagesVo>();
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getMessageByState().SELECT");

		try {
			rs = DB.getInstance().query(sql, null);
			GoodsCenterMessagesVo vo;
			while (rs != null && rs.next()) {
				vo = new GoodsCenterMessagesVo();
				vo.setId(rs.getString("id"));
				vo.setContentId(rs.getString("contentid"));
				vo.setAction(rs.getInt("action"));
				vo.setChangeTime(rs.getString("changetime"));
				vo.setSendTime(rs.getString("sendtime"));
				vo.setAppId(rs.getString("appid"));

				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getMessageByState error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	public PretreatmentVO getPretreatmentByAppid(String appid)
			throws DAOException {
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getPretreatmentByAppid().SELECT");
		PretreatmentVO vo = null ;
		try {
			rs = DB.getInstance().query(sql, new String[] { appid });
			
			if (rs != null && rs.next()) {
				vo = new PretreatmentVO();
				vo.setId(rs.getString("id"));
				vo.setChangetime(rs.getString("changetime"));
				vo.setAppid(rs.getString("appid"));
				vo.setStatus(rs.getInt("status"));

			}
		} catch (Exception ex) {
			throw new DAOException("getMessageByState error!!", ex);
		} finally {
			DB.close(rs);
		}
		return vo;
	}

	public List<PretreatmentVO> getPretreatmentByStatusAppid(String appid)
			throws DAOException {
		List<PretreatmentVO> list = new ArrayList<PretreatmentVO>();
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getPretreatmentByStatusAppid().SELECT");

		try {
			rs = DB.getInstance().query(sql, new String[] { appid });
			PretreatmentVO vo;
			while (rs != null && rs.next()) {
				vo = new PretreatmentVO();
				vo.setId(rs.getString("id"));
				vo.setChangetime(rs.getString("changetime"));
				vo.setAppid(rs.getString("appid"));
				vo.setStatus(rs.getInt("status"));

				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getMessageByState error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	public List<PretreatmentVO> getPretreatmentByStatus() throws DAOException {
		List<PretreatmentVO> list = new ArrayList<PretreatmentVO>();
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getPretreatmentByStatus().SELECT");

		try {
			rs = DB.getInstance().query(sql, null);
			PretreatmentVO vo;
			while (rs != null && rs.next()) {
				vo = new PretreatmentVO();
				vo.setId(rs.getString("id"));
				vo.setChangetime(rs.getString("changetime"));
				vo.setAppid(rs.getString("appid"));
				vo.setStatus(rs.getInt("status"));

				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getMessageByState error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	public List<PriorityOutputIncrVO> getCByAppid(String appid)
			throws DAOException {
		List<PriorityOutputIncrVO> list = new ArrayList<PriorityOutputIncrVO>();
		ResultSet rs = null;
		String sql = DB.getInstance().getSQLByCode(
				"PPMSDAO.getCByAppid().SELECT");

		try {
			rs = DB.getInstance().query(sql, new String[] { appid });
			PriorityOutputIncrVO vo;
			while (rs != null && rs.next()) {
				vo = new PriorityOutputIncrVO();
				vo.setId(rs.getString("id"));
				vo.setContentid(rs.getString("contentid"));
				vo.setAppid(rs.getString("appid"));
				vo.setCreatedate(rs.getDate("createdate"));
				vo.setAction(rs.getInt("action"));
				list.add(vo);
			}
		} catch (Exception ex) {
			throw new DAOException("getCByAppid error!!", ex);
		} finally {
			DB.close(rs);
		}
		return list;
	}

	public void updatePretreatment(PretreatmentVO pvo, int status, String id)
			throws DAOException {

		if (LOG.isDebugEnabled()) {
			LOG.debug("PPMSDAO.removeRefContentFromCategory() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		// ����T_A_PPMS_PRETREATMENT_MESSAGEԤ�����
		// insert into T_A_PPMS_PRETREATMENT_MESSAGE(id,appid,changetime,status)
		// values(SEQ_PRETREATMENT_MESSAGE_ID.NEXTVAL,?,?,?)
		String sqlCode1 = "PPMSDAO.addPretreatment.common";
		// ����T_A_PPMS_RECEIVE_MESSAGE��״̬
		// update T_A_PPMS_RECEIVE_MESSAGE m set m.status=?,m.changeTime=sysdate
		// where m.id=?
		String sqlCode2 = "PPMSDAO.updateMessage.changeStatus";

		if (pvo != null) {
			Object[] paras1 = { pvo.getAppid(), pvo.getChangetime(),
					pvo.getStatus() };
			Object[] paras2 = { status, id };

			transactionDB.executeBySQLCode(sqlCode1, paras1);
			transactionDB.executeBySQLCode(sqlCode2, paras2);
		} else {
			try {
				Object[] paras2 = { status, id };
				transactionDB.executeBySQLCode(sqlCode2, paras2);
			} catch (Exception ex) {
				throw new DAOException("updateMessage error!!", ex);
			}
		}

	}

	public void updateChange(int pstatus, String pid,
			List<PriorityOutputIncrVO> cList) throws Exception {

		if (LOG.isDebugEnabled()) {
			LOG.debug("PPMSDAO.removeRefContentFromCategory() is beginning...");
		}
		if (checkTransactionDB()) {
			throw new DAOException("�������Ϊ�գ�����");
		}
		// ����T_A_PPMS_PRETREATMENT_MESSAGEԤ�����״̬
		// update T_A_PPMS_PRETREATMENT_MESSAGE m set m.status=? where m.id=?
		String sqlCode1 = "PPMSDAO.updatePretreatment.changeStatus";

		// ����T_A_PPMS_RECEIVE_change��
		// insert into
		// T_A_PPMS_RECEIVE_change(imagetype,id,TransactionID,type,entityid,status)values(?,SEQ_T_A_PPMS_RECEIVE_change.Nextval,?,?,?,?)
		String sqlCode3 = "PPMSDAO.updateChange.INSERT";

		// ����T_R_GAPP
		// insert into
		// T_R_GAPP(ID,APPID,CONTENTID,CREATEDATE)values(SEQ_T_A_PPMS_RECEIVE_change.Nextval,?,?,sysdate)
		String sqlCode4 = "PPMSDAO.updateGapp.INSERT";

		// ɾ��T_R_GAPP
		// delete from T_R_GAPP t where t.APPID = ? and t.CONTENTID = ?
		String sqlCode5 = "PPMSDAO.delGapp.DELETE";

		String sqlCode6 = "PPMSDAO.updateGAPP.UPDATE";

		Object[] paras1 = { pstatus, pid };

		transactionDB.executeBySQLCode(sqlCode1, paras1);

		if (!cList.isEmpty()) {
			for (PriorityOutputIncrVO cvo : cList) {

				Object[] paras3 = { "0",
						DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"), "1",
						cvo.getContentid(), -1,
						String.valueOf(cvo.getAction()), cvo.getAppid() };
				transactionDB.executeBySQLCode(sqlCode3, paras3);

				Object[] paras4 = { cvo.getAppid(), cvo.getContentid() };
				Object[] paras6 = { cvo.getContentid(), cvo.getAppid() };
				if (cvo.getAction() == 1) {
					if (0 == checkGapp(cvo.getContentid())) {
						//transactionDB.executeBySQLCode(sqlCode4, paras4);
					} else {
						//transactionDB.executeBySQLCode(sqlCode6, paras6);
					}
					;

				} else if (cvo.getAction() == 2 || cvo.getAction() == 3) {
					//transactionDB.executeBySQLCode(sqlCode5, paras4);
				}
			}
		}

	}

	public int checkGapp(String contentid) {
		String sql = "SyncAndroid.APPInfoDAO.getGAPPVO().SELECT";
		Object[] paras = { contentid };
		RowSet rs = null;
		int k = 0;
		try {
			rs = DB.getInstance().queryBySQLCode(sql, paras);
			if (rs.next()) {
				k = rs.getInt("count");
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return k;
	}

	public void updateTRGapp(String appid, String contentid, String opt)
			throws DAOException {

		// ����T_R_GAPP
		// insert into
		// T_R_GAPP(ID,APPID,CONTENTID,CREATEDATE)values(SEQ_T_A_PPMS_RECEIVE_change.Nextval,?,?,sysdate)
		String sqlCode4 = "PPMSDAO.updateGapp.INSERT";

		// ɾ��T_R_GAPP
		// delete from T_R_GAPP t where t.APPID = ? and t.CONTENTID = ?
		String sqlCode5 = "PPMSDAO.delGapp.DELETE";

		String sqlCode6 = "PPMSDAO.updateGAPP.UPDATE";

		Object[] paras4 = { appid, contentid };
		Object[] paras6 = { contentid, appid };

		if ("1".equals(opt)) {
			if (0== checkGapp(contentid)) {
				DB.getInstance().executeBySQLCode(sqlCode4, paras4);
				// transactionDB.executeBySQLCode(sqlCode4, paras4);
			} else {
				// transactionDB.executeBySQLCode(sqlCode6, paras6);
				DB.getInstance().executeBySQLCode(sqlCode6, paras6);
			}
			;

		} else if ("2".equals(opt) || "3".equals(opt)) {
			// transactionDB.executeBySQLCode(sqlCode5, paras4);
			DB.getInstance().executeBySQLCode(sqlCode5, paras4);
		}
	}
	
	/**
	 * ����appid��ѯssmsϵͳ�»���������Ʒ
	 * 
	 * @param appid
	 * @return
	 * @throws DAOException
	 */
	public void updateAppidByNewAppid(String contentid ,String appid)
			throws DAOException {
		String sql = "PPMSDAO.updateFromReferenceByContentid.UPDATE";
		String sql2 = "PPMSDAO.updateFromTRGappByContentid.UPDATE";
		Object[] paras = { appid, contentid };
		
		try {
			transactionDB.executeBySQLCode(sql, paras);
			transactionDB.executeBySQLCode(sql2, paras);
			
		} catch (Exception ex) {
			throw new DAOException("getDelGoodsList error!!", ex);
		} 
	}
	
}
