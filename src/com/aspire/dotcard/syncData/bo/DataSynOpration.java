/**
 * SSMS
 * com.aspire.dotcard.syncData.bo DataSynOpration.java
 * Jan 20, 2011
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.syncData.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.DeviceVO;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticConstants;
import com.aspire.dotcard.syncData.tactic.TacticVO;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.web.channeladmin.vo.OpenChannelsCategoryVo;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDeviceBO;
import com.aspire.ponaadmin.web.repository.GoodsChanegHis;
import com.aspire.ponaadmin.web.repository.GoodsChanegHisBO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.system.Config;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *MM����ͬ�����߳�ִ����
 */
public class DataSynOpration
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(DataSynOpration.class);
	/**
     * ��¼ͬ��Ӧ�ü�¼
     */
    protected static JLogger RecordLog=LoggerFactory.getLogger("DataSyncLog");
    
    /**
     * cms����ͬ��ʱ�������ϼܵ����ܷ����µĻ��档
     * key��categoryID; value��HashMap��������contentID�ļ��ϣ�ʹ��hashMap��Ҫ��Ϊ�˿��ٲ�ѯ����Ҫ��
     */
    private static Map ttMap = Collections.synchronizedMap(new HashMap());
    
    /**
     * ���ݱ�ǩ�ָ��Ļ���
     * key��contentTag���ݱ�ǩ��value������tag�ָ����list
     */
    private static Map tagMap = Collections.synchronizedMap(new HashMap());
    
    /**
     * ��ȡ��ǩ�ĸ���
     */
    private static int TAG_SIZE = Integer.parseInt(Config.getInstance()
                                                         .getModuleConfig()
                                                         .getItemValue("TAG_SIZE"));
    
    private List  tacticList = null;
    private List  mttacticList = null;
    private List  htctacticList = null;
    //���㷺�����������̸������б�
    private List  channelstacticList = null;
    private List[]  mailInfoList= null ;
 
    private ContentTmp tmp;
    private String isSyn;
 
    // ORA-01461 ��������Ϊ����LONG�е�LONGֵ��ֵ
    private final int MAX_LENGTH = 1333;
    
    /**
     * ���������
     */
    private static int MAX_OFFLINENUMBER = Integer.parseInt(Config.getInstance()
                                                           .getModuleConfig()
                                                           .getItemValue("MAXOffLineNuber"));
 
 
 public  DataSynOpration(List  tacticList,List mttacticList,List htctacticList,List channelstacticList,ContentTmp tmp, List[]  mailInfoList, String isSyn)
 {
     if(null == this.tacticList ){
    	 this.tacticList = tacticList; 
     }
     if(null == this.mttacticList ){
    	 this.mttacticList = mttacticList; 
     }
     if(null == this.htctacticList ){
    	 this.htctacticList = htctacticList; 
     }
     if(null == this.channelstacticList ){
    	 this.channelstacticList = channelstacticList; 
     }
     if(null == this.mailInfoList ){
    	 this.mailInfoList = mailInfoList; 
     }

	 this.tmp = tmp;
	 this.isSyn = isSyn;
 }
 

 public void dataSynOp(){
		
		 //���ڼ�¼Ӧ�ô���Ľ��
        StringBuffer record=null;
        TransactionDB tdb = null;
        // �õ�ContentTmp����
       // ContentTmp tmp = ( ContentTmp ) list.get(i);
        try
        {
            // �����������
            tdb = TransactionDB.getTransactionInstance();
            DataSyncDAO dao = DataSyncDAO.getTransactionInstance(tdb);
            
            record = new StringBuffer(tmp.getContentId());
            record.append(" | ");
            record.append(tmp.getLupdDate());
            record.append(" | ");
            record.append(tmp.getStatus());
            record.append(" | ");
            if (SyncDataConstant.CONTENT_TYPE_RELEASE.equals(tmp.getStatus()))
            {
                logger.info("#########################��ʼ�����������ݣ�contentId="
                            + tmp.getContentId());
                // ����editSyncContent��PAS�е��������ݽ��б༭
                // int result = this.editSyncContent(tmp.getContentId(),
                // tmp.getContentType(),tacticList);
                int result = this.editSyncContent(tmp,
                                                  tacticList,
                                                  mttacticList,
                                                  htctacticList,
                                                  channelstacticList,
                                                  record,
                                                  mailInfoList[2]);

                record.append('1');
                if (result == SyncDataConstant.SYNC_UPDATE)
                {
                    mailInfoList[0].add(tmp);
                }
                else if (result == SyncDataConstant.SYNC_DEL)
                {
                    // �޻��������ϵ ����Ӧ��
                    // mailInfoList[2].add(tmp);
                }
                else
                {
                    mailInfoList[1].add(tmp);
                }
            }
            // �������Ϊ����״̬
            else if (SyncDataConstant.CONTENT_TYPE_OVERDUE.equals(tmp.getStatus()))
            {
                logger.info("��ǰ����������Ϊ��" + mailInfoList[2].size() + ", �������ֵΪ:"
                            + +MAX_OFFLINENUMBER + "��������ڷ�ֵ�˳��˲�����");

                // ������ڷ�ֵ
                synchronized(mailInfoList){//add by aiyan 2012-07-24 Ϊ��MAX_OFFLINENUMBER��׼ȷ���ơ�
                	logger.info("����mailInfoList��ͬ�������ˡ�����in dataSynOp");
	                if (mailInfoList[2].size() >= MAX_OFFLINENUMBER)
	                {
	                    logger.info("�ѳ���ͬ���������߷�ֵ�������߹��ܱ��ò��账����������Ҫ��Ϊֻ��¼������contentId="
	                                + tmp.getContentId());
	
	                    this.mailInfoList[4].add(tmp);
	                }
                    // ���߲���
                    offLineContent(tmp, record, mailInfoList[2]);
                }

            }
            else
            {
                logger.error("��״̬���Ϸ������ݳ������Ը����ݡ�status=" + tmp.getStatus());
                mailInfoList[3].add(tmp);
                record.append('0');
            }
            // ����DataSyncDAO��delSynccontetTmp����ɾ��t_syncContent_tmp���е���ʼ�¼
            dao.delSynccontetTmp(tmp.getId());
            // �ύ����
            tdb.commit();
            RecordLog.info(record);
        }
        catch (Exception e)
        {
            // �ع�
            tdb.rollback();
            logger.error(e);
            // ��¼��������ContentTmp����
            mailInfoList[3].add(tmp);

            record.append('0');
            RecordLog.info(record);
        }
        finally
        {
            if (tdb != null)
            {
                tdb.close();
            }
        }
    
	}
 /**
  * ��CMSͬ�����������ݽ��б༭
  * 
  * @param ContentId,���ݱ���
  * @param contentType,��������
  * @param tacticList,ͬ�������б�
  * @param newContentList,���������б�
  * @return int 1 ��ʾ�ɹ����£�2��ʾ�ɹ�����
  * @throws DAOException 
  * @throws Exception
  */
 // private int editSyncContent(String contentId, String contentType,List
 // tacticList)
 private int editSyncContent(ContentTmp tmp, List tacticList,List mttacticList,List htctacticList,List channelstacticList,
                             StringBuffer record, List mailInfoList)

 throws BOException, DAOException
 {
     String contentId = tmp.getContentId();
     String contentType = tmp.getContentType();
     if (logger.isDebugEnabled())
     {
         logger.debug("editSyncContent(" + contentId + "," + contentType
                      + ")");
     }
     // ����contentId���������͹������Ҫ�༭���������Զ���
     Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
     Searchor searchor = new Searchor();
     searchor.getParams().add(new SearchParam("contentID",
                                              RepositoryConstants.OP_EQUAL,
                                              contentId));
     // searchor.getParams().add(new SearchParam("type",
     // RepositoryConstants.OP_LIKE,
     // "nt:gcontent:app%"));
     // ����contentId��ѯpas���ݱ����Ƿ���ڸ�����
     List list = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
     // ����dao��getGcontentFromCMS����
     GContent gc;
     try
     {
         gc = DataSyncDAO.getInstance().getGcontentFromCMS(contentId,
                                                           contentType);
         if (gc != null)
         {
             GAppContent gap = ( GAppContent ) gc;
             if (gap.getDeviceName() == null
                 || gap.getDeviceName().equals(""))
             {
                 // �����ϵΪ��
                 // ���߲���
                 logger.error("------devicename is null contentid="
                              + tmp.getContentId());
                 
                 logger.info("��ǰ����������Ϊ��" + this.mailInfoList[2].size() + ", �������ֵΪ:"
                             + +MAX_OFFLINENUMBER + "��������ڷ�ֵ�˳��˲�����");

                 // ������ڷ�ֵ
                 synchronized(this.mailInfoList){ //add by aiyan 2012-07-24 Ϊ��MAX_OFFLINENUMBER��׼ȷ���ơ�
                 	logger.info("����mailInfoList��ͬ�������ˡ�����in editSyncContent");
	                 if (this.mailInfoList[2].size() >= MAX_OFFLINENUMBER)
	                 {
	                     logger.info("�ѳ���ͬ���������߷�ֵ�������߹��ܱ��ò��账����������Ҫ��Ϊֻ��¼������contentId="
	                                 + tmp.getContentId());
	
	                     this.mailInfoList[4].add(tmp);
	                 }
                     // ���߲���
                     offLineContent(tmp, record, mailInfoList);
                 }
                 
                 // �޻��������ϵ ����Ӧ��
                 return SyncDataConstant.SYNC_DEL;
             }
         }
     }
     catch (DAOException e)
     {
         throw new BOException("��CMS���޷���ȡ����ʧ�ܡ�contentId=" + contentId, e);
     }
     // �޸�keywords,����ʽת��Ϊ���ǵĸ�ʽ.(�� ; �ָ� ת���� };{ �ָ�)
     processKeywords(gc);

     // ��ӱ�ǩ��Ϣ
     try
     {
         String oldTag = formatKeywords(gc.getKeywords());
         String newTag = DataSyncDAO.getInstance()
                                    .getOPTagAndAPTagByContentID(gc.getContentID());// getTagByContentID(gc.getContentID());
         String fullTag = newTag + oldTag;
         Object[] checkNewTagObject = PublicUtil.getConentArray(fullTag,
                                                                ";",
                                                                MAX_LENGTH);
         if (checkNewTagObject != null && checkNewTagObject.length > 0)
         {
             fullTag = ( String ) checkNewTagObject[0];
         }
         if (fullTag.endsWith(";"))
         {
             fullTag = fullTag.substring(0, fullTag.length() - 1);
         }
         gc.setKeywords(fullTag);
     }
     catch (DAOException e)
     {
         logger.error("ƴװtagʧ�ܣ�" + e);
         e.printStackTrace();
     }

     // �������,�������Դ�����еĸ��½ӿڶ��������Զ�����и���
     if (list.size() != 0)
     {

         Node node = ( Node ) list.get(0);
         logger.debug("the node is:" + node);
         String id = node.getId();
         gc.setId(id);

         // ���ݶ�������Ҫ����ԭֵ
         GContent oldContent = ( GContent ) node;
         gc.setAverageMark(oldContent.getAverageMark());
         gc.setOrderTimes(oldContent.getOrderTimes());
         gc.setWeekOrderTimes(oldContent.getWeekOrderTimes());
         gc.setMonthOrderTimes(oldContent.getMonthOrderTimes());
         gc.setDayOrderTimes(oldContent.getDayOrderTimes());
         gc.setScanTimes(oldContent.getScanTimes());
         gc.setWeekScanTimes(oldContent.getWeekScanTimes());
         gc.setMonthScanTimes(oldContent.getMonthScanTimes());
         gc.setDayScanTimes(oldContent.getDayScanTimes());
         gc.setSearchTimes(oldContent.getSearchTimes());
         gc.setWeekSearchTimes(oldContent.getWeekSearchTimes());
         gc.setMonthSearchTimes(oldContent.getMonthSearchTimes());
         gc.setDaySearchTimes(oldContent.getDaySearchTimes());
         gc.setCommentTimes(oldContent.getCommentTimes());
         gc.setWeekCommentTimes(oldContent.getWeekCommentTimes());
         gc.setMonthCommentTimes(oldContent.getMonthCommentTimes());
         gc.setDayCommentTimes(oldContent.getDayCommentTimes());
         gc.setMarkTimes(oldContent.getMarkTimes());
         gc.setWeekMarkTimes(oldContent.getWeekMarkTimes());
         gc.setMonthMarkTimes(oldContent.getMonthMarkTimes());
         gc.setDayMarkTimes(oldContent.getDayMarkTimes());
         gc.setCommendTimes(oldContent.getCommendTimes());
         gc.setWeekCommendTimes(oldContent.getWeekCommendTimes());
         gc.setMonthCommendTimes(oldContent.getMonthCommendTimes());
         gc.setDayCommendTimes(oldContent.getDayCommendTimes());
         gc.setCollectTimes(oldContent.getCollectTimes());
         gc.setWeekCollectTimes(oldContent.getWeekCollectTimes());
         gc.setMonthCollectTimes(oldContent.getMonthCollectTimes());
         gc.setDayCollectTimes(oldContent.getDayCollectTimes());
         
         boolean staus;
         //2015-09-30 add ,��������������ַ�����(channeldisptype)��0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�
 		//channeldisptypeΪ1:��ѡ�����зַ���Ӧ�ò��ϼܵ�MM�ͻ��˷�����ܣ�
         if("1".equals(gc.getChannelDispType())){
        	 staus = true;
         }else{	 
        	 // �¼�
        	 staus = checkAndUpdateCategory(gc, oldContent);
         }
         
         // ���и��²���
         gc.save();
         // �ϼ�
         if (staus)
         {
             // �޸��˷�����Ҫ�����ϼܷ���
             checkAndAddCategory(contentType, gc, tacticList,mttacticList, htctacticList,channelstacticList);
         }

         return SyncDataConstant.SYNC_UPDATE;
     }
     // ���������,�����ϵͳ����Դ����Ĳ������ݽӿڽ��������Զ������
     else
     {
         Category node = new Category();
         node.setId(RepositoryConstants.ROOT_CONTENT_ID);
         node.setPath("{100}.{702}");
         node.addNode(gc);
         node.saveNode();
         checkAndAddCategory(contentType, gc, tacticList,mttacticList, htctacticList,channelstacticList);
         return SyncDataConstant.SYNC_ADD;
     }
 }

 /**
  * �¼�ԭ�������¸���Ʒ
  * 
  * @param gc
  * @param oldGc
  * @throws BOException
  */
 private boolean checkAndUpdateCategory(GContent gc, GContent oldGc)
                 throws BOException
 {

		if (logger.isDebugEnabled())
		{
			logger.debug("DataSyncBO.checkAndUpdateCategory");
		}
		GAppContent n = (GAppContent) gc;
		String oldApp = "";
        List hisList = null;
		try
		{
			oldApp = DataSyncDAO.getInstance().getGcAppCateNameById(gc.getId());
			if (oldApp == null)
			{// ���û���ϼ�
				return true;
			}
		} catch (DAOException e1)
		{
			logger.error("��ȡԭ�����������Ƴ���", e1);
		}
		logger.debug("n.getAppCateName()=" + n.getAppCateName());
		logger.debug("oldApp=" + oldApp);

		if (gc.getSubType().equals("12") || "16".equals(gc.getSubType()))
		{
			// MOTOӦ��
			int motoc = 0;
			logger.debug("MOTO ��HTC Ӧ�ü���Ƿ�����;subtype=" + gc.getSubType());
			try
			{
				RowSet rs = DB.getInstance().queryBySQLCode(
						"DataSyncDAO.getMOTOAppCateNameById().SELECT2",
						new Object[] { gc.getId(), gc.getSubType() });
				if (rs.next())
				{
					motoc = rs.getInt(1);
				}

			} catch (Exception e1)
			{
				logger.error("�����������Ʒ�Ƿ���ȫ������" + e1);
			}

			if ((null != n.getAppCateName() && !n.getAppCateName().equals(oldApp))
					|| (motoc < 1))
			{
				List refs = new ArrayList();
				try
				{
					String querySQLCode = "DataSyncDAO.getMOTORefContentsByCateName().SELECT";
					if ("16".equals(gc.getSubType())) {
						querySQLCode = "DataSyncDAO.getHTCRefContentsByCateName().SELECT";
					}
					
					refs = DataSyncDAO.getInstance().getRefContentsByCateName(
							querySQLCode, gc.getId());
				} catch (DAOException e)
				{
					logger.error("�¼�ԭ���������", e);
				}
                
                // ����ǽ�������Ӧ�ã�����¼���ʷ��Ϣ
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    hisList = GoodsChanegHisBO.getInstance().addDelHisByListId(refs);
                }
                
				for (int i = 0; i < refs.size(); i++)
				{
					GoodsBO.removeRefContentFromCategory((String) refs.get(i));
					logger.error("�¼�ԭ������:" + gc.getContentID());
				}
                
				// ����ǽ�������Ӧ�ã�����¼���ʷ��Ϣ
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    GoodsChanegHisBO.getInstance().addDataToHis(hisList);
                }
				// MOTO Ӧ�÷��������û����Ʒ
				return true;
			}
		}
		else
		{

			int count = 0;
			try
			{
				RowSet rs = DB.getInstance().queryBySQLCode(
						"DataSyncDAO.getGcAppCateNameById().SELECT2",
						new Object[] { gc.getId() });
				if (rs.next())
				{
					count = rs.getInt(1);
				}

			} catch (DAOException e1)
			{
				logger.error("�����������Ʒ�Ƿ���ȫ������" + e1);
			} catch (SQLException e)
			{
				logger.error("�����������Ʒ�Ƿ���ȫ������" + e);
			}
			if ((null != n.getAppCateName() && !n.getAppCateName().equals(oldApp))
					|| (count < 3))
			{
				List refs = new ArrayList();
				try
				{
					String querySQLCode = "DataSyncDAO.getRefContentsByCateName().SELECT";
					refs = DataSyncDAO.getInstance().getRefContentsByCateName(
							querySQLCode, gc.getId());
				} catch (DAOException e)
				{
					logger.error("�¼�ԭ���������", e);
				}
                
				// ����ǽ�������Ӧ�ã�����¼���ʷ��Ϣ
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    hisList = GoodsChanegHisBO.getInstance().addDelHisByListId(refs);
                }
                
				for (int i = 0; i < refs.size(); i++)
				{
					GoodsBO.removeRefContentFromCategory((String) refs.get(i));
					logger.error("�¼�ԭ������:" + gc.getContentID());
				}
                
				// ����ǽ�������Ӧ�ã�����¼���ʷ��Ϣ
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    GoodsChanegHisBO.getInstance().addDataToHis(hisList);
                }
				return true;
			}
		}
		return false;
	}

 	/**
	 * ƥ�������ϼܲ���
	 * 
	 * @param contentType
	 * @param gc
	 */
	private void checkAndAddCategory(String type, GContent gc, List tacticList,
			List mttacticList, List htctacticList,List channelstacticList) throws BOException {
		if (logger.isDebugEnabled()) {
			logger.debug("DataSyncBO.checkAndAddCategory(). contentType="
					+ type);
		}
		//2015-09-30 add ,��������������ַ�����(channeldisptype)��0��δѡ�����зַ���MM�ͻ��ˣ���1����ѡ�����зַ���������ֻ�пͻ��ˣ�
		//channeldisptypeΪ1:��ѡ�����зַ���Ӧ�ò��ϼܵ�MM�ͻ��˻��ܣ�
		if("1".equals(gc.getChannelDispType())){
			
			return;
		}
		//(add 2014-01-07) ������App���ϼ�
		if ("11".equals(gc.getSubType()) || "21".equals(gc.getSubType())) {
			// Ʒ�Ƶ��ײ�Ӧ�ò����з����ϼ�,21������App���ϼ�
			return;
		} else if ("12".equals(gc.getSubType())) {
			// MOTO �Ǽ�԰����ͬ������
			MOTOcheckTacticAddRef(type, gc, mttacticList, gc.getSubType());
		} else if ("16".equals(gc.getSubType())) {
			// htc
			MOTOcheckTacticAddRef(type, gc, htctacticList, gc.getSubType());
		} else {
			// MM����ͬ������
			MMcheckTacticAddRef(type, gc, tacticList);
		}
	}

 
 /**
	 * 
	 * @desc MotoӦ������ͬ�������ϼ�
	 * @author dongke Apr 8, 2011
	 */
 public void MOTOcheckTacticAddRef(String type, GContent gc, List tacticList, String subType)
			throws BOException
	{

		TacticVO vo;
		int size = tacticList.size();
		String temp;
		
		String appId = gc.getContentTag();
		String cateName = null;//MOTOһ������
		String appcateName = null;//MOTO��������
		if("12".equals(subType))
		{
			try {
				String[] res = DataSyncDAO.getInstance()
						.getMOTOAppCateNameById(appId);
				if (res.length == 2) {
					cateName = res[0];
					appcateName = res[1];
				}
			} catch (DAOException e) {
				e.printStackTrace();
				throw new BOException("��ѯMOTOӦ��MOTOһ�����࣬��������ʧ��", e);
			}
		}
		else
		{
			//cateName = type;
			
            // �ж�Ӧ�÷��������Ƿ���ͬ�������е�Ӧ�÷�������ƥ��
			//GAppContent app = ( GAppContent ) gc;
			//appcateName = app.getAppCateName();
			
			try {
				String[] res = DataSyncDAO.getInstance()
						.getHTCAppCateNameById(appId);
				if (res.length == 2) {
					cateName = res[0];
					appcateName = res[1];
				}
			} catch (DAOException e) {
				e.printStackTrace();
				throw new BOException("��ѯHTCӦ��HTCһ�����࣬��������ʧ��", e);
			}
		}
		String categoryID;
		// ������ȥƥ��ÿһ������
		for (int i = 0; i < size; i++)
		{
			vo = (TacticVO) tacticList.get(i);
			categoryID = vo.getCategoryID();

			// �ȼ��ò��Զ�Ӧ�����ݻ��ܷ����Ƿ��Ѿ��ϼ��˸�����
			if (checkTTMap(categoryID, gc.getContentID()))
			{
				continue;
			}

			temp = vo.getContentType();

			// ��һ����������������Ƿ�ƥ��
			if (!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || cateName.equals(temp)))
			{
				// ���Ͳ�ƥ��,�����һ������
				continue;
			}

			// �ڶ��������umFlagҵ��ͨ���Ƿ�ƥ��
			temp = vo.getUmFlag();
			if (!TacticConstants.UMFLAG_ALL.equals(temp))
			{
				// ���ݶ�Ӧҵ���ҵ��ͨ������
				String gcUmFlag = null;
				gcUmFlag = getGContentUmflag(gc);
				if (!temp.equals(gcUmFlag))
				{
					// ҵ��ͨ�����Ͳ�ƥ�䣬�����һ������
					continue;
				}
			}

			// ��������������ݱ�ʶ�Ƿ�ƥ��
			//String keywords = gc.getKeywords();
			//int relation = vo.getTagRelation();
			//temp = vo.getContentTag();
			//if (!(null == temp || "".equals(temp)
			//		|| relation == TacticConstants.TABRELATION_NULL || checkContentTag(
			//		keywords, vo.getTagList(), relation)))
			//{
				// ���ݱ�ʶ��ƥ�䣬�����һ������
			//	continue;
			//}

			// ���Ĳ������Ӧ�÷��������Ƿ�ƥ��
			//if (gc instanceof GAppContent)
			//{
				// ��gcת����Ӧ�����ݻ�ȡӦ�÷�������
				//GAppContent app = (GAppContent) gc;
				// �ж�Ӧ�÷��������Ƿ���ͬ�������е�Ӧ�÷�������ƥ��
				if (!appcateName.equals(vo.getAppCateName()))
				{
					continue;
				}
		//	}

			// ���岽�����м��ͨ�����������ϼܵ����Զ�Ӧ�Ļ��ܻ�����
			addCategory(gc, categoryID);
			// д��������
			HashMap map = (HashMap) ttMap.get(categoryID);
			if (null == map)
			{
				map = new HashMap();
				ttMap.put(categoryID, map);
			}
			map.put(gc.getContentID(), "");
		}
	}
 /**
	 * 
	 * @desc ���㷺������������Ӧ��ͬ�������ϼ�
	 * @author dongke Apr 8, 2011
	 */
 public void channelsCategorycheckTacticAddRef(String type, GContent gc, List channelstacticList)
			throws BOException
	{

	    OpenChannelsCategoryVo vo;
		int size = channelstacticList.size();
		String temp;
		
		String appId = gc.getContentTag();

		String categoryID;
		// ������ȥƥ��ÿһ������
		for (int i = 0; i < size; i++)
		{
			vo = (OpenChannelsCategoryVo) channelstacticList.get(i);
			categoryID = vo.getCategoryId();

			// �ȼ��ò��Զ�Ӧ�����ݻ��ܷ����Ƿ��Ѿ��ϼ��˸�����
			if (checkTTMap(categoryID, gc.getContentID()))
			{
				continue;
			}

			temp = vo.getChannelsId();

			// ��һ����������������Ƿ�ƥ��
			//�������ݵ�companyid��Ӧ����������id��
			//˫��Э�̺����������Ӻ��������̵ĺ�����id�����Ƕ�Ӧ��������companyid
			if (!gc.getCompanyID().equals(temp))
			{
				// ���Ͳ�ƥ��,�����һ������
				continue;
			}

			// ���岽�����м��ͨ�����������ϼܵ����Զ�Ӧ�Ļ��ܻ�����
			addCategory(gc, categoryID);
			// д��������
			HashMap map = (HashMap) ttMap.get(categoryID);
			if (null == map)
			{
				map = new HashMap();
				ttMap.put(categoryID, map);
			}
			map.put(gc.getContentID(), "");
		}
	}
 
 /**
	 * 
	 * @desc MMӦ������ͬ�������ϼ�
	 * @author dongke Apr 8, 2011
	 */
 public  void  MMcheckTacticAddRef(String type, GContent gc, List tacticList)  throws BOException{
	 
	 TacticVO vo;
     int size = tacticList.size();
     String temp;

     String categoryID;
     // ������ȥƥ��ÿһ������
     for (int i = 0; i < size; i++)
     {
         vo = ( TacticVO ) tacticList.get(i);
         categoryID = vo.getCategoryID();

         // �ȼ��ò��Զ�Ӧ�����ݻ��ܷ����Ƿ��Ѿ��ϼ��˸�����
         if (checkTTMap(categoryID, gc.getContentID()))
         {
             continue;
         }

         temp = vo.getContentType();

         // ��һ����������������Ƿ�ƥ��
         if (!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || type.equals(temp)))
         {
             // ���Ͳ�ƥ��,�����һ������
             continue;
         }

         // �ڶ��������umFlagҵ��ͨ���Ƿ�ƥ��
         temp = vo.getUmFlag();
         if (!TacticConstants.UMFLAG_ALL.equals(temp))
         {
             // ���ݶ�Ӧҵ���ҵ��ͨ������
             String gcUmFlag = null;
             gcUmFlag = getGContentUmflag(gc);
             if (!temp.equals(gcUmFlag))
             {
                 // ҵ��ͨ�����Ͳ�ƥ�䣬�����һ������
                 continue;
             }
         }

         // ��������������ݱ�ʶ�Ƿ�ƥ��
         String keywords = gc.getKeywords();
         int relation = vo.getTagRelation();
         temp = vo.getContentTag();
         if (!(null == temp || "".equals(temp)
               || relation == TacticConstants.TABRELATION_NULL || checkContentTag(keywords,
                                                                                  vo.getTagList(),
                                                                                  relation)))
         {
             // ���ݱ�ʶ��ƥ�䣬�����һ������
             continue;
         }

         // ���Ĳ������Ӧ�÷��������Ƿ�ƥ��
         if (gc instanceof GAppContent)
         {
             // ��gcת����Ӧ�����ݻ�ȡӦ�÷�������
             GAppContent app = ( GAppContent ) gc;
             // �ж�Ӧ�÷��������Ƿ���ͬ�������е�Ӧ�÷�������ƥ��
             if (!app.getAppCateName().equals(vo.getAppCateName()))
             {
                 continue;
             }
         }

         // ���岽�����м��ͨ�����������ϼܵ����Զ�Ӧ�Ļ��ܻ�����
         
         //����������Ƿ��ǻ��ͻ��ܣ��ǻ��ͻ��ܻ���Ҫ����Ӧ���������ƥ����ܻ���
         
         Category category = ( Category ) Repository.getInstance()
         .getNode(categoryID,
                  RepositoryConstants.TYPE_CATEGORY);
         //if(category != null && 0==(category.getDeviceCategory())){      	 
          if (category != null && 1 == (category.getDeviceCategory()))
			{
				// ���ͻ���
				// ��ȡ���ܹ�������
				List deviceList = CategoryDeviceBO.getInstance().queryDeviceList(
						categoryID);
				if (deviceList != null)
				{
					for (int k = 0; k < deviceList.size(); k++)
					{
						DeviceVO devicev = (DeviceVO) deviceList.get(k);
						if (devicev != null)
						{
							String devicevs = "{" + devicev.getDeviceId() + "}";
							if (gc.getFulldeviceID().indexOf(devicevs) > -1)
							{
								// �����Ӧ������Ļ��Ͱ������������һ��������ϼ�
								addCategory(gc, categoryID);
								logger.debug("���ݻ����ϼܻ��ͻ��ܣ�"+categoryID+"Ӧ��contentID:" + gc.getContentID());
								break;
							}
							else
							{
								logger.debug("Ӧ��contentID:" + gc.getContentID()
										+ ";�������ID:" + gc.getFulldeviceID() + ";���������id��"
										+ categoryID);
							}
						}else
						{
							logger.error("�����쳣�����ͻ�����:" + categoryID
									+ ";��ĳһ�������������Ϊ��" );
						}
					}
				}else
				{
					logger.error("�����쳣�����ͻ���:" + categoryID
							+ ";�������Ϊ��" );
				}

			}else{
//        	�ǻ��ͻ��ܣ���ͨ����
        	 addCategory(gc, categoryID); 
         }
        
         // д��������
         HashMap map = ( HashMap ) ttMap.get(categoryID);
         if (null == map)
         {
             map = new HashMap();
             ttMap.put(categoryID, map);
         }
         map.put(gc.getContentID(), "");
     }
 }
 
 
 /**
  * ���ttMap���Ƿ���categoryID��Ӧ��contentID
  * 
  * @param categoryID
  * @param contentID
  * @return true ��ʾ������false ��ʾ������
  */
 private boolean checkTTMap(String categoryID, String contentID)
 {
     HashMap map = ( HashMap ) ttMap.get(categoryID);
     if (map == null)
     {
         return false;
     }
     else
     {
         return map.containsKey(contentID);
     }
 }

 /**
  * ȡ���ݶ�Ӧҵ���ҵ��ͨ������umFlag
  * 
  * @param gc
  * @return
  */
 private String getGContentUmflag(GContent gc)
 {
     if (logger.isDebugEnabled())
     {
         logger.debug("DataSyncBO.getGContentUmflag()");
     }
     try
     {
         return DataSyncDAO.getInstance()
                           .queryContentUmflag(gc.getIcpCode(),
                                               gc.getIcpServId());
     }
     catch (DAOException e)
     {
         logger.error("ͨ����ҵ�����ҵ������ȡUmflagʱ�������ݿ��쳣��", e);
         return null;
         // throw new BOException("ͨ����ҵ�����ҵ������ȡUmflagʱ�������ݿ��쳣��",e);
     }
 }

 /**
  * ������ݱ�ǩ�Ͳ��Ա�ǩ�Ƿ�ƥ��
  * 
  * @param gcTag ���ݱ�ǩ�ַ���,��ʽ�� {��ǩһ};{��ǩ��};{��ǩ��}
  * @param tacticTag ���Ա�ǩ�ַ�������ʽ�� ��ǩһ;��ǩ��;��ǩ��
  * @param relation ���Թ�ϵ��1=and; 2=or
  * @return true:ƥ�� false:��ƥ��
  */
 private boolean checkContentTag(String gcTag, List ttList, int relation)
 {
     if (logger.isDebugEnabled())
     {
         logger.debug("DataSyncBO.checkContentTag()");
         logger.debug("���ݱ�ǩ��" + gcTag + " ���Ա�ǩ��" + ttList + " ���Թ�ϵ��"
                      + relation);
     }
     if (!(relation == TacticConstants.TABRELATION_OR || relation == TacticConstants.TABRELATION_AND))
     {
         return false;
     }

     List gcList = ( List ) tagMap.get(gcTag);
     if (null == gcList)
     {
         gcList = new ArrayList();
         String[] items = gcTag.split(";");
         String item;
         for (int i = 0; i < items.length; i++)
         {
             item = items[i];
             if (item.length() == 0)
             {
                 continue;
             }
             try
             {
                 item = item.substring(item.indexOf("{") + 1,
                                       item.lastIndexOf("}"));
                 gcList.add(item);
             }
             catch (Exception e)
             {
                 logger.error("���ݱ�ǩ��ʽ����contentTag=" + gcTag);
                 logger.error(e);
             }
         }
         tagMap.put(gcTag, gcList);
     }

     if (relation == TacticConstants.TABRELATION_OR) // or
     {
         for (int i = 0; i < ttList.size(); i++)
         {
             if (gcList.contains(ttList.get(i)))
             {
                 // ������������һһ����ǩ
                 return true;
             }
         }
     }
     else if (relation == TacticConstants.TABRELATION_AND
              && gcList.containsAll(ttList)) // and
     {
         // �������������еı�ǩ
         return true;
     }

     return false;
 }

 /**
  * �������ϼܵ����ܻ�����
  * 
  * @param gc
  * @param categoryID
  */
 private void addCategory(GContent gc, String categoryID) throws BOException
 {
     if (logger.isDebugEnabled())
     {
         logger.debug("=====addCategory: categoryID=" + categoryID
                      + " contentID=" + gc.getContentID());
     }
     try
     {
         Category category = ( Category ) Repository.getInstance()
                                                    .getNode(categoryID,
                                                             RepositoryConstants.TYPE_CATEGORY);

         // ������Ʒ���룽���ܱ��룫��ҵ����(6λ)��ҵ������(12λ)�����ݱ���(12λ)
         String goodsID = category.getCategoryID()
                          + PublicUtil.lPad(gc.getCompanyID(), 6)
                          + PublicUtil.lPad(gc.getProductID(), 12)
                          + PublicUtil.lPad(gc.getContentID(), 12);
         // �ŵ�Ŀ�������

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
         ref.setVerifyStatus("1");

         // ������Ʒ��ϢVO�࣬��������������
         GoodsVO goodsVO = new GoodsVO();
         goodsVO.setGoodsID(goodsID);
         goodsVO.setIcpCode(gc.getIcpCode());
         goodsVO.setIcpServId(gc.getIcpServId());
         goodsVO.setContentID(gc.getContentID());
         goodsVO.setCategoryID(category.getCategoryID());
         goodsVO.setGoodsName(gc.getName());
         goodsVO.setState(1);
         goodsVO.setChangeDate(new Date());
         goodsVO.setActionType(1);
         
         // ����GoodsBO�е�addNodeAndInsertGoodsInfor����ڵ㲢������Ʒ��Ϣ
         GoodsBO.addNodeAndInsertGoodsInfo(ref, goodsVO);
         
         // �Ƿ��ǽ���Ӧ�������߲���
         if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
         {
             GoodsChanegHis his = new GoodsChanegHis();
             his.setCid(category.getId());
             his.setType(category.getRelation());
             his.setGoodsId(goodsID);
             his.setSubType(gc.getSubType());
             his.setAction(RepositoryConstants.SYN_ACTION_ADD);
             // �������Ӧ�������߲�����ʷ����
             GoodsChanegHisBO.getInstance().addDataToHis(his);
         }
     }
     catch (Exception e)
     {
         logger.error(e);
         throw new BOException("�����ϼܳ���", e);
     }
 }

 /**
  * ��keywords�ĸ�ʽ����PAS�ĸ�ʽ
  * 
  * @param gc
 * @throws DAOException 
  */
 private void processKeywords(GContent gc) throws DAOException
 {
     if (logger.isDebugEnabled())
     {
         logger.debug("processKeywords:" + gc);
     }
     if (gc == null)
         return;

     String keywords = gc.getKeywords();
     if (keywords != null && !"".equals(keywords))
     {
         // �������ķֺ�
         while (keywords.endsWith(";"))
         {
             keywords = keywords.substring(0, keywords.length() - 1);
         }
       
         //���ܲ������ͻ����ص�Ӧ�á���ǩ�ŵ�������Ϣ�е�keyword�ֶ��У��Դﵽ�ͻ�������������ҳ��չʾ�ñ�ǩĿ��
         if(keywords.toString().trim().contains("�ͻ����ص�Ӧ��")){
        	 DataSyncDAO.getInstance().insertKeyContent(gc.getContentID()); 
        	 keywords = keywords.replace(";�ͻ����ص�Ӧ��", "");
        	 keywords = keywords.replace("�ͻ����ص�Ӧ��", "");
        	 if(keywords.startsWith(";")){
        		 keywords = keywords.substring(1);
        	 }
         }
            if(keywords != null && !"".equals(keywords)){
            	keywords = "{" + keywords.replaceAll(";", "};{") + "}";
            }
            if (logger.isDebugEnabled())
            {
                logger.debug("keywords:" + keywords);
            }
            gc.setKeywords(keywords);
     }
 }
 
 
 /**
  * ���ڶ����йؼ��ֽ��зָ��ȡ�������������
  * 
  * @param keywords �ؼ���
  * @return �ָ��ؼ���
  */
 private String formatKeywords(String keywords)
 {
     if (keywords == null)
     {
         return "";
     }
     String t[] = keywords.split(";");

     if (t.length > TAG_SIZE)
     {
         keywords = "";

         for (int i = 0; i < TAG_SIZE; i++)
         {
             keywords += t[i] + ";";
         }

         keywords = keywords.substring(0, keywords.length() - 1);
     }

     return keywords;
 }
 /**
  * ���߲���
  * 
  * @param tmp
  * @param record
  * @param mailInfoList
  * @throws BOException
  */
 private void offLineContent(ContentTmp tmp, StringBuffer record,
                             List mailInfoList) throws BOException
 {
     logger.info("#########################��ʼ�����������ݣ�contentId="
                 + tmp.getContentId());
     // ����contentId���������͹������Ҫ�༭���������Զ���
     Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
     Searchor searchor = new Searchor();
     searchor.getParams().add(new SearchParam("contentID",
                                              RepositoryConstants.OP_EQUAL,
                                              tmp.getContentId()));
     searchor.getParams().add(new SearchParam("type",
                                              RepositoryConstants.OP_LIKE,
                                              "nt:gcontent:app%"));
     // ����contentId��ѯpas���ݱ����Ƿ���ڸ�����
     List nodeList = rootNode.searchNodes(GContent.TYPE_CONTENT,
                                          searchor,
                                          null);
     if (nodeList.size() != 0)
     {
         List hisList = null;
         
         // ����ǽ�������Ӧ�ã���ӱ����¼���ʷ��Ϣ
         if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
         {
             hisList = GoodsChanegHisBO.getInstance().addDelHisToList(tmp.getContentId());
         }
         
         // �����¼�
         GoodsBO.removeAllRefContentByContentID(tmp.getContentId());
         // ͬʱ����delSyncContent����ɾ��pas�е���������
         this.delSyncContent(tmp.getContentId(), tmp.getContentType());
         
         // ����ǽ�������Ӧ�ã�����¼���ʷ��Ϣ
         if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
         {
             GoodsChanegHisBO.getInstance().addDataToHis(hisList);
         }
         
         // mailInfoList[2].add(tmp);
         mailInfoList.add(tmp);
         record.append('1');
     }
     record.append('2');// ��ʾû�з���ͬ����������cms�ͻ���ϵͳ���Ѿ����ߣ�����Ҫͬ��

 }

 /**
  * ��PAS���ݱ��е�����ɾ��
  * 
  * @param Contentid,���ݱ���
  */
 public void delSyncContent(String Contentid, String contentType)
                 throws BOException
 {

     if (logger.isDebugEnabled())
     {
         logger.debug("delSyncContent(" + Contentid + "," + contentType
                      + ")");
     }
     // ����contentid��ѯ�õ�pas���ݱ���Ψһ����id
     Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
     Searchor searchor = new Searchor();
     searchor.getParams().add(new SearchParam("contentID",
                                              RepositoryConstants.OP_EQUAL,
                                              Contentid));
     List list = rootNode.searchNodes(contentType, searchor, null);
     // ������Դ�����ɾ�����ݽӿڽ�������ɾ��
     if (list != null && list.size() != 0)
     {
         // ѭ��������702�ڵ���ɾ��������Դ
         for (int i = 0; i < list.size(); i++)
         {
             GContent gc = ( GContent ) list.get(i);
             rootNode.delNode(gc);
             rootNode.saveNode();
         }
     }

     try
     {
         DataSyncDAO.getInstance()
                    .addContentIdHis(Contentid,
                                     SyncDataConstant.DEL_CONTENT_TYPE_MM);
     }
     catch (DAOException e)
     {
         throw new BOException("�����¼�ʱ����������Ӧ�ü�¼�����", e);
     }
 }
 
     /**
      * �������������Ի��������
      */
     public static void cleanMap()
     {
         ttMap.clear();
         tagMap.clear();
     }
 
}
