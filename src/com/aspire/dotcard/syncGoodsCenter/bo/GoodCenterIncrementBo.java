package com.aspire.dotcard.syncGoodsCenter.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncGoodsCenter.dao.SyncDataDAO;
import com.aspire.dotcard.syncGoodsCenter.vo.GcAppHotInfo;
import com.aspire.dotcard.syncGoodsCenter.vo.RefModifyReqVo;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class GoodCenterIncrementBo {
	JLogger LOG = LoggerFactory.getLogger(GoodCenterIncrementBo.class);

	public void work() {
		TransactionDB tdb = null;
		
		//��ȡ������������
		List<GcAppHotInfo> gcAppHotInfoListAdd = SyncDataDAO
				.findGcAppHotInfoDataByInsrementAdd();
		
		//��ȡ������������
		List<GcAppHotInfo> gcAppHotInfoListUpdate = SyncDataDAO
				.findGcAppHotInfoDataByInsrementUpdate();

		//��ȡ�����¼�����
		List<String> contentidListByDelete = SyncDataDAO.getContentIdByDelete();
		
//		List<String> contentList = new ArrayList<String>();
		
		try {
			tdb = TransactionDB.getTransactionInstance();
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SyncDataDAO dao = SyncDataDAO.getTransactionInstance(tdb);
		
		
		for (int i = 0; i < gcAppHotInfoListAdd.size(); i++) {
			try {
//				String transactionID = ContextUtil.getTransactionID();
				
				GcAppHotInfo g = gcAppHotInfoListAdd.get(i);
				String contentid = g.getCONTENT_ID();
				String goodsid=dao.createGoodsid(g);
				g.setGOODSID(goodsid);
				
				{
					List<String> gcontentIdList=dao.tRGcontentInsert(g);
					dao.tADCPPMSServiceInsert(g);
					dao.tACmDeviceResourceInsert(g);
					List<String> referenceIdList=dao.newStockInsert(g);
					dao.baseInsert(g, gcontentIdList, referenceIdList);
					dao.vserviceDelete(g.getCONTENT_ID()); //����ɾ��
					dao.vserviceInsert(g);
					dao.vcontentlastInsert(g);
					tdb.commit();
					dao.addMessages(MSGType.ContentModifyReq, null,contentid + ":0");
					List<RefModifyReqVo> list=dao.findRefModifyReqVo(contentid);
					for (int j = 0; j < list.size(); j++) {
						RefModifyReqVo vo=list.get(j);
						 //loaddate����û������,���԰�ϵͳʱ��������
						Date d=new Date();
						String date=new SimpleDateFormat("yyyyMMddHHmmss").format(d);
						String loaddate=null;
						if(vo.getLoaddate()!=null)loaddate=new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyy--MM-dd HH:mm:ss").parse(vo.getLoaddate()));
						//���loaddate��ʽ��Ϊyyyy/MM/dd HH:mm:ss��parseexception
						String msg=(vo.getGoodsid()==null?"":vo.getGoodsid()) + ":"
								+ (vo.getCategoryid()==null?"":vo.getCategoryid() )+ ":" + (vo.getId()==null?"":vo.getId()) + ":" 
								+ (vo.getRefnodeid()==null?"":vo.getRefnodeid())+ ":" + vo.getSortid() + ":"
								+( vo.getLoaddate()==null?date:loaddate)+ ":0";
						dao.addMessages(MSGType.RefModifyReq, null,msg);
					}
					tdb.commit();
					//�����ϼ���Ϣ 
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				if (tdb != null)
					tdb.rollback();
				LOG.error(e);
			} finally {
				
			}
		}
		
		
		
		for (int i = 0; i < gcAppHotInfoListUpdate.size(); i++) {
			try {
//				String transactionID = ContextUtil.getTransactionID();
				
				GcAppHotInfo g = gcAppHotInfoListUpdate.get(i);
				String contentid = g.getCONTENT_ID();
				String goodsid=dao.createGoodsid(g);
				g.setGOODSID(goodsid);
				{
					List<RefModifyReqVo> list=dao.findRefModifyReqVo(contentid);
					for (int j = 0; j < list.size(); j++) {
						RefModifyReqVo vo=list.get(j);
						String msg=(vo.getGoodsid()==null?"":vo.getGoodsid() )+ "::::::9";
						dao.addMessages(MSGType.RefModifyReq,null, msg);
					}
					List<String> gcontentIdList1=dao.tRGcontentUpdate(g);
					List<String> referenceIdList1=dao.newStockUpdate(g,"");
					dao.baseDelete(gcontentIdList1, referenceIdList1);
					dao.tADCPPMSServiceDelete(contentid);
					dao.tACmDeviceResourceDelete(contentid);
					dao.vserviceDelete(contentid);
					dao.tADCPPMSServiceInsert(g);
					dao.tACmDeviceResourceInsert(g);
					dao.baseInsert(g, gcontentIdList1, referenceIdList1);
					dao.vserviceInsert(g);
					dao.addMessages(MSGType.ContentModifyReq, null,contentid + ":1");
					tdb.commit();
					List<RefModifyReqVo> list2=dao.findRefModifyReqVo(contentid);
					for (int j = 0; j < list2.size(); j++) {
						RefModifyReqVo vo=list2.get(j);
						 //loaddate����û������,���԰�ϵͳʱ��������
						Date d=new Date();
						String date=new SimpleDateFormat("yyyyMMddHHmmss").format(d);
						String loaddate=null;
						if(vo.getLoaddate()!=null)loaddate=new SimpleDateFormat("yyyyMMddHHmmss").format(new SimpleDateFormat("yyyy--MM-dd HH:mm:ss").parse(vo.getLoaddate()));
						//���loaddate��ʽ��Ϊyyyy/MM/dd HH:mm:ss��parseexception
						String msg=(vo.getGoodsid()==null?"":vo.getGoodsid()) + ":"
								+ (vo.getCategoryid()==null?"":vo.getCategoryid() )+ ":" + (vo.getId()==null?"":vo.getId()) + ":" 
								+ (vo.getRefnodeid()==null?"":vo.getRefnodeid())+ ":" + vo.getSortid() + ":"
								+( vo.getLoaddate()==null?date:loaddate)+ ":0";
						dao.addMessages(MSGType.RefModifyReq, null,msg);
					}
					tdb.commit();
				} 
				
			} catch (Exception e) {
				e.printStackTrace();
				if (tdb != null)
					tdb.rollback();
				LOG.error(e);
			} finally {
				
			}
		}
//		if (tdb != null)
//			tdb.close();
		
		/*
		 * mm������,��Ʒ����û�е�Ӧ��,�¼ܲ�ɾ��
		 */
		try {
			// gcontentContentidList��û���õ�contentid list����
			for (int i = 0; i < contentidListByDelete.size(); i++) {
				String gcontentContentid = contentidListByDelete.get(i);
//				if (!contentList.contains(gcontentContentid)) {
					String transactionID2 = ContextUtil.getTransactionID();
					try {
						
						
						//�����¼���Ϣ
						List<RefModifyReqVo> list=dao.findRefModifyReqVo(gcontentContentid);
						List<String>referenceIdList=dao.newStockDelete(gcontentContentid);
						List<String>gcontentIdList=dao.tRGcontentDelete(gcontentContentid);
						dao.tADCPPMSServiceDelete(gcontentContentid);
						dao.tACmDeviceResourceDelete(gcontentContentid);
						dao.baseDelete(gcontentIdList, referenceIdList);
						dao.vserviceDelete(gcontentContentid);
						dao.vcontentlastDelete(gcontentContentid);
						dao.addMessages(MSGType.ContentModifyReq,transactionID2, (gcontentContentid==null?"":gcontentContentid )+ ":9");
						for (int j = 0; j < list.size(); j++) {
							RefModifyReqVo vo=list.get(j);
							String msg=(vo.getGoodsid()==null?"":vo.getGoodsid() )+ "::::::9";
							dao.addMessages(MSGType.RefModifyReq,transactionID2, msg);
						}
						tdb.commit();
					} catch (Exception e) {
						if (tdb != null)
							tdb.rollback();
						LOG.error(e);
						e.printStackTrace();
					}
					
				}
//			}
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		} 
		finally {
			if (tdb != null)
				tdb.close();
		}
	}

}
