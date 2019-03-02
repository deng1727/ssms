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
 *MM内容同步多线程执行类
 */
public class DataSynOpration
{
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(DataSynOpration.class);
	/**
     * 记录同步应用记录
     */
    protected static JLogger RecordLog=LoggerFactory.getLogger("DataSyncLog");
    
    /**
     * cms内容同步时，内容上架到货架分类下的缓存。
     * key：categoryID; value：HashMap，其中是contentID的集合（使用hashMap主要是为了快速查询的需要）
     */
    private static Map ttMap = Collections.synchronizedMap(new HashMap());
    
    /**
     * 内容标签分隔的缓存
     * key：contentTag内容标签；value：内容tag分隔后的list
     */
    private static Map tagMap = Collections.synchronizedMap(new HashMap());
    
    /**
     * 获取标签的个数
     */
    private static int TAG_SIZE = Integer.parseInt(Config.getInstance()
                                                         .getModuleConfig()
                                                         .getItemValue("TAG_SIZE"));
    
    private List  tacticList = null;
    private List  mttacticList = null;
    private List  htctacticList = null;
    //触点泛化合作渠道商根货架列表
    private List  channelstacticList = null;
    private List[]  mailInfoList= null ;
 
    private ContentTmp tmp;
    private String isSyn;
 
    // ORA-01461 ：仅可以为插入LONG列的LONG值赋值
    private final int MAX_LENGTH = 1333;
    
    /**
     * 最大下线数
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
		
		 //用于记录应用处理的结果
        StringBuffer record=null;
        TransactionDB tdb = null;
        // 得到ContentTmp对象
       // ContentTmp tmp = ( ContentTmp ) list.get(i);
        try
        {
            // 进行事务调用
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
                logger.info("#########################开始处理上线内容，contentId="
                            + tmp.getContentId());
                // 调用editSyncContent对PAS中的内容数据进行编辑
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
                    // 无机型适配关系 下线应用
                    // mailInfoList[2].add(tmp);
                }
                else
                {
                    mailInfoList[1].add(tmp);
                }
            }
            // 如果内容为过期状态
            else if (SyncDataConstant.CONTENT_TYPE_OVERDUE.equals(tmp.getStatus()))
            {
                logger.info("当前已下线数量为：" + mailInfoList[2].size() + ", 配置最大阀值为:"
                            + +MAX_OFFLINENUMBER + "。如果大于阀值退出此操作。");

                // 如果大于阀值
                synchronized(mailInfoList){//add by aiyan 2012-07-24 为了MAX_OFFLINENUMBER的准确控制。
                	logger.info("进入mailInfoList的同步区域了。。。in dataSynOp");
	                if (mailInfoList[2].size() >= MAX_OFFLINENUMBER)
	                {
	                    logger.info("已超出同步允许下线阀值，此下线功能本该不予处理，但是现在要改为只记录个数，contentId="
	                                + tmp.getContentId());
	
	                    this.mailInfoList[4].add(tmp);
	                }
                    // 下线操作
                    offLineContent(tmp, record, mailInfoList[2]);
                }

            }
            else
            {
                logger.error("该状态不合法，数据出错，忽略该数据。status=" + tmp.getStatus());
                mailInfoList[3].add(tmp);
                record.append('0');
            }
            // 调用DataSyncDAO的delSynccontetTmp方法删除t_syncContent_tmp表中的这笔记录
            dao.delSynccontetTmp(tmp.getId());
            // 提交事务
            tdb.commit();
            RecordLog.info(record);
        }
        catch (Exception e)
        {
            // 回滚
            tdb.rollback();
            logger.error(e);
            // 记录插入出错的ContentTmp对象
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
  * 对CMS同步过来的内容进行编辑
  * 
  * @param ContentId,内容编码
  * @param contentType,内容类型
  * @param tacticList,同步策略列表
  * @param newContentList,新增内容列表
  * @return int 1 表示成功更新，2表示成功上线
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
     // 根据contentId和内容类型构造出需要编辑的内容属性对象
     Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
     Searchor searchor = new Searchor();
     searchor.getParams().add(new SearchParam("contentID",
                                              RepositoryConstants.OP_EQUAL,
                                              contentId));
     // searchor.getParams().add(new SearchParam("type",
     // RepositoryConstants.OP_LIKE,
     // "nt:gcontent:app%"));
     // 根据contentId查询pas内容表中是否存在该内容
     List list = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
     // 调用dao的getGcontentFromCMS方法
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
                 // 适配关系为空
                 // 下线操作
                 logger.error("------devicename is null contentid="
                              + tmp.getContentId());
                 
                 logger.info("当前已下线数量为：" + this.mailInfoList[2].size() + ", 配置最大阀值为:"
                             + +MAX_OFFLINENUMBER + "。如果大于阀值退出此操作。");

                 // 如果大于阀值
                 synchronized(this.mailInfoList){ //add by aiyan 2012-07-24 为了MAX_OFFLINENUMBER的准确控制。
                 	logger.info("进入mailInfoList的同步区域了。。。in editSyncContent");
	                 if (this.mailInfoList[2].size() >= MAX_OFFLINENUMBER)
	                 {
	                     logger.info("已超出同步允许下线阀值，此下线功能本该不予处理，但是现在要改为只记录个数，contentId="
	                                 + tmp.getContentId());
	
	                     this.mailInfoList[4].add(tmp);
	                 }
                     // 下线操作
                     offLineContent(tmp, record, mailInfoList);
                 }
                 
                 // 无机型适配关系 下线应用
                 return SyncDataConstant.SYNC_DEL;
             }
         }
     }
     catch (DAOException e)
     {
         throw new BOException("从CMS中无法获取内容失败。contentId=" + contentId, e);
     }
     // 修改keywords,将格式转化为我们的格式.(将 ; 分隔 转换成 };{ 分隔)
     processKeywords(gc);

     // 添加标签信息
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
         logger.error("拼装tag失败，" + e);
         e.printStackTrace();
     }

     // 如果存在,则调用资源管理中的更新接口对内容属性对象进行更新
     if (list.size() != 0)
     {

         Node node = ( Node ) list.get(0);
         logger.debug("the node is:" + node);
         String id = node.getId();
         gc.setId(id);

         // 内容订购量需要保留原值
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
         //2015-09-30 add ,触点合作商渠道分发类型(channeldisptype)：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）
 		//channeldisptype为1:已选择自有分发的应用不上架到MM客户端分类货架，
         if("1".equals(gc.getChannelDispType())){
        	 staus = true;
         }else{	 
        	 // 下架
        	 staus = checkAndUpdateCategory(gc, oldContent);
         }
         
         // 进行更新操作
         gc.save();
         // 上架
         if (staus)
         {
             // 修改了分类需要重新上架分类
             checkAndAddCategory(contentType, gc, tacticList,mttacticList, htctacticList,channelstacticList);
         }

         return SyncDataConstant.SYNC_UPDATE;
     }
     // 如果不存在,则调用系统中资源管理的插入内容接口将内容属性对象插入
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
  * 下架原来分类下该商品
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
			{// 如果没有上架
				return true;
			}
		} catch (DAOException e1)
		{
			logger.error("获取原二级分类名称出错！", e1);
		}
		logger.debug("n.getAppCateName()=" + n.getAppCateName());
		logger.debug("oldApp=" + oldApp);

		if (gc.getSubType().equals("12") || "16".equals(gc.getSubType()))
		{
			// MOTO应用
			int motoc = 0;
			logger.debug("MOTO 或HTC 应用检查是否上线;subtype=" + gc.getSubType());
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
				logger.error("检查分类货架商品是否齐全，出错" + e1);
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
					logger.error("下架原来分类出错！", e);
				}
                
                // 如果是紧急上线应用，添加下架历史信息
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    hisList = GoodsChanegHisBO.getInstance().addDelHisByListId(refs);
                }
                
				for (int i = 0; i < refs.size(); i++)
				{
					GoodsBO.removeRefContentFromCategory((String) refs.get(i));
					logger.error("下架原来分类:" + gc.getContentID());
				}
                
				// 如果是紧急上线应用，添加下架历史信息
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    GoodsChanegHisBO.getInstance().addDataToHis(hisList);
                }
				// MOTO 应用分类货架上没有商品
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
				logger.error("检查分类货架商品是否齐全，出错" + e1);
			} catch (SQLException e)
			{
				logger.error("检查分类货架商品是否齐全，出错" + e);
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
					logger.error("下架原来分类出错！", e);
				}
                
				// 如果是紧急上线应用，添加下架历史信息
                if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
                {
                    hisList = GoodsChanegHisBO.getInstance().addDelHisByListId(refs);
                }
                
				for (int i = 0; i < refs.size(); i++)
				{
					GoodsBO.removeRefContentFromCategory((String) refs.get(i));
					logger.error("下架原来分类:" + gc.getContentID());
				}
                
				// 如果是紧急上线应用，添加下架历史信息
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
	 * 匹配内容上架策略
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
		//2015-09-30 add ,触点合作商渠道分发类型(channeldisptype)：0：未选择自有分发（MM客户端）、1：已选择自有分发（合作商只有客户端）
		//channeldisptype为1:已选择自有分发的应用不上架到MM客户端货架，
		if("1".equals(gc.getChannelDispType())){
			
			return;
		}
		//(add 2014-01-07) 定制类App不上架
		if ("11".equals(gc.getSubType()) || "21".equals(gc.getSubType())) {
			// 品牌店套餐应用不进行分类上架,21定制类App不上架
			return;
		} else if ("12".equals(gc.getSubType())) {
			// MOTO 智件园内容同步策略
			MOTOcheckTacticAddRef(type, gc, mttacticList, gc.getSubType());
		} else if ("16".equals(gc.getSubType())) {
			// htc
			MOTOcheckTacticAddRef(type, gc, htctacticList, gc.getSubType());
		} else {
			// MM内容同步策略
			MMcheckTacticAddRef(type, gc, tacticList);
		}
	}

 
 /**
	 * 
	 * @desc Moto应用内容同步策略上架
	 * @author dongke Apr 8, 2011
	 */
 public void MOTOcheckTacticAddRef(String type, GContent gc, List tacticList, String subType)
			throws BOException
	{

		TacticVO vo;
		int size = tacticList.size();
		String temp;
		
		String appId = gc.getContentTag();
		String cateName = null;//MOTO一级分类
		String appcateName = null;//MOTO二级分类
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
				throw new BOException("查询MOTO应用MOTO一级分类，二级分类失败", e);
			}
		}
		else
		{
			//cateName = type;
			
            // 判断应用分类属性是否与同步策略中的应用分类名称匹配
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
				throw new BOException("查询HTC应用HTC一级分类，二级分类失败", e);
			}
		}
		String categoryID;
		// 用内容去匹配每一个策略
		for (int i = 0; i < size; i++)
		{
			vo = (TacticVO) tacticList.get(i);
			categoryID = vo.getCategoryID();

			// 先检查该策略对应的内容货架分类是否已经上架了该内容
			if (checkTTMap(categoryID, gc.getContentID()))
			{
				continue;
			}

			temp = vo.getContentType();

			// 第一步：检查内容类型是否匹配
			if (!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || cateName.equals(temp)))
			{
				// 类型不匹配,检查下一个策略
				continue;
			}

			// 第二步：检查umFlag业务通道是否匹配
			temp = vo.getUmFlag();
			if (!TacticConstants.UMFLAG_ALL.equals(temp))
			{
				// 内容对应业务的业务通道类型
				String gcUmFlag = null;
				gcUmFlag = getGContentUmflag(gc);
				if (!temp.equals(gcUmFlag))
				{
					// 业务通道类型不匹配，检查下一个策略
					continue;
				}
			}

			// 第三步：检查内容标识是否匹配
			//String keywords = gc.getKeywords();
			//int relation = vo.getTagRelation();
			//temp = vo.getContentTag();
			//if (!(null == temp || "".equals(temp)
			//		|| relation == TacticConstants.TABRELATION_NULL || checkContentTag(
			//		keywords, vo.getTagList(), relation)))
			//{
				// 内容标识不匹配，检查下一个策略
			//	continue;
			//}

			// 第四步：检查应用分类名称是否匹配
			//if (gc instanceof GAppContent)
			//{
				// 将gc转化成应用内容获取应用分类属性
				//GAppContent app = (GAppContent) gc;
				// 判断应用分类属性是否与同步策略中的应用分类名称匹配
				if (!appcateName.equals(vo.getAppCateName()))
				{
					continue;
				}
		//	}

			// 第五步：所有检查通过，将内容上架到策略对应的货架货架下
			addCategory(gc, categoryID);
			// 写到缓存中
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
	 * @desc 触点泛化合作渠道商应用同步策略上架
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
		// 用内容去匹配每一个策略
		for (int i = 0; i < size; i++)
		{
			vo = (OpenChannelsCategoryVo) channelstacticList.get(i);
			categoryID = vo.getCategoryId();

			// 先检查该策略对应的内容货架分类是否已经上架了该内容
			if (checkTTMap(categoryID, gc.getContentID()))
			{
				continue;
			}

			temp = vo.getChannelsId();

			// 第一步：检查内容类型是否匹配
			//这里内容的companyid对应合作渠道商id，
			//双方协商好我们这边添加合作渠道商的合作商id，就是对应电子流的companyid
			if (!gc.getCompanyID().equals(temp))
			{
				// 类型不匹配,检查下一个策略
				continue;
			}

			// 第五步：所有检查通过，将内容上架到策略对应的货架货架下
			addCategory(gc, categoryID);
			// 写到缓存中
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
	 * @desc MM应用内容同步策略上架
	 * @author dongke Apr 8, 2011
	 */
 public  void  MMcheckTacticAddRef(String type, GContent gc, List tacticList)  throws BOException{
	 
	 TacticVO vo;
     int size = tacticList.size();
     String temp;

     String categoryID;
     // 用内容去匹配每一个策略
     for (int i = 0; i < size; i++)
     {
         vo = ( TacticVO ) tacticList.get(i);
         categoryID = vo.getCategoryID();

         // 先检查该策略对应的内容货架分类是否已经上架了该内容
         if (checkTTMap(categoryID, gc.getContentID()))
         {
             continue;
         }

         temp = vo.getContentType();

         // 第一步：检查内容类型是否匹配
         if (!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || type.equals(temp)))
         {
             // 类型不匹配,检查下一个策略
             continue;
         }

         // 第二步：检查umFlag业务通道是否匹配
         temp = vo.getUmFlag();
         if (!TacticConstants.UMFLAG_ALL.equals(temp))
         {
             // 内容对应业务的业务通道类型
             String gcUmFlag = null;
             gcUmFlag = getGContentUmflag(gc);
             if (!temp.equals(gcUmFlag))
             {
                 // 业务通道类型不匹配，检查下一个策略
                 continue;
             }
         }

         // 第三步：检查内容标识是否匹配
         String keywords = gc.getKeywords();
         int relation = vo.getTagRelation();
         temp = vo.getContentTag();
         if (!(null == temp || "".equals(temp)
               || relation == TacticConstants.TABRELATION_NULL || checkContentTag(keywords,
                                                                                  vo.getTagList(),
                                                                                  relation)))
         {
             // 内容标识不匹配，检查下一个策略
             continue;
         }

         // 第四步：检查应用分类名称是否匹配
         if (gc instanceof GAppContent)
         {
             // 将gc转化成应用内容获取应用分类属性
             GAppContent app = ( GAppContent ) gc;
             // 判断应用分类属性是否与同步策略中的应用分类名称匹配
             if (!app.getAppCateName().equals(vo.getAppCateName()))
             {
                 continue;
             }
         }

         // 第五步：所有检查通过，将内容上架到策略对应的货架货架下
         
         //第六步检查是否是机型货架，是机型货架还需要根据应用适配机型匹配货架机型
         
         Category category = ( Category ) Repository.getInstance()
         .getNode(categoryID,
                  RepositoryConstants.TYPE_CATEGORY);
         //if(category != null && 0==(category.getDeviceCategory())){      	 
          if (category != null && 1 == (category.getDeviceCategory()))
			{
				// 机型货架
				// 获取货架关联机型
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
								// 如果有应用适配的机型包含货架适配的一款机型则上架
								addCategory(gc, categoryID);
								logger.debug("根据机型上架机型货架："+categoryID+"应用contentID:" + gc.getContentID());
								break;
							}
							else
							{
								logger.debug("应用contentID:" + gc.getContentID()
										+ ";适配机型ID:" + gc.getFulldeviceID() + ";不适配货架id："
										+ categoryID);
							}
						}else
						{
							logger.error("数据异常，机型货架里:" + categoryID
									+ ";的某一个机型适配机型为空" );
						}
					}
				}else
				{
					logger.error("数据异常，机型货架:" + categoryID
							+ ";适配机型为空" );
				}

			}else{
//        	非机型货架，普通货架
        	 addCategory(gc, categoryID); 
         }
        
         // 写到缓存中
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
  * 检查ttMap中是否有categoryID对应的contentID
  * 
  * @param categoryID
  * @param contentID
  * @return true 表示包换，false 表示不包含
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
  * 取内容对应业务的业务通道类型umFlag
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
         logger.error("通过企业代码和业务代码获取Umflag时发生数据库异常！", e);
         return null;
         // throw new BOException("通过企业代码和业务代码获取Umflag时发生数据库异常！",e);
     }
 }

 /**
  * 检查内容标签和策略标签是否匹配
  * 
  * @param gcTag 内容标签字符串,格式： {标签一};{标签二};{标签三}
  * @param tacticTag 策略标签字符串，格式： 标签一;标签二;标签三
  * @param relation 策略关系：1=and; 2=or
  * @return true:匹配 false:不匹配
  */
 private boolean checkContentTag(String gcTag, List ttList, int relation)
 {
     if (logger.isDebugEnabled())
     {
         logger.debug("DataSyncBO.checkContentTag()");
         logger.debug("内容标签：" + gcTag + " 策略标签：" + ttList + " 策略关系："
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
                 logger.error("内容标签格式错误！contentTag=" + gcTag);
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
                 // 包含策略中任一一个标签
                 return true;
             }
         }
     }
     else if (relation == TacticConstants.TABRELATION_AND
              && gcList.containsAll(ttList)) // and
     {
         // 包含策略中所有的标签
         return true;
     }

     return false;
 }

 /**
  * 将内容上架到货架货架下
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

         // 构造商品编码＝货架编码＋企业内码(6位)＋业务内码(12位)＋内容编码(12位)
         String goodsID = category.getCategoryID()
                          + PublicUtil.lPad(gc.getCompanyID(), 6)
                          + PublicUtil.lPad(gc.getProductID(), 12)
                          + PublicUtil.lPad(gc.getContentID(), 12);
         // 放到目标分类中

         // 分配一个新的资源id
         String newNodeID = category.getNewAllocateID();

         // 创建一个引用节点，并设置所需属性
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

         // 创建商品信息VO类，并设置所需属性
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
         
         // 调用GoodsBO中的addNodeAndInsertGoodsInfor保存节点并储存商品信息
         GoodsBO.addNodeAndInsertGoodsInfo(ref, goodsVO);
         
         // 是否是紧急应用上下线操作
         if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
         {
             GoodsChanegHis his = new GoodsChanegHis();
             his.setCid(category.getId());
             his.setType(category.getRelation());
             his.setGoodsId(goodsID);
             his.setSubType(gc.getSubType());
             his.setAction(RepositoryConstants.SYN_ACTION_ADD);
             // 存入紧急应用上下线操作历史表中
             GoodsChanegHisBO.getInstance().addDataToHis(his);
         }
     }
     catch (Exception e)
     {
         logger.error(e);
         throw new BOException("内容上架出错！", e);
     }
 }

 /**
  * 将keywords的格式换成PAS的格式
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
         // 处理最后的分号
         while (keywords.endsWith(";"))
         {
             keywords = keywords.substring(0, keywords.length() - 1);
         }
       
         //货架不将“客户端重点应用”标签放到内容信息中的keyword字段中，以达到客户端上内容详情页不展示该标签目的
         if(keywords.toString().trim().contains("客户端重点应用")){
        	 DataSyncDAO.getInstance().insertKeyContent(gc.getContentID()); 
        	 keywords = keywords.replace(";客户端重点应用", "");
        	 keywords = keywords.replace("客户端重点应用", "");
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
  * 用于对现有关键字进行分割，获取数由配置项决定
  * 
  * @param keywords 关键字
  * @return 分割后关键字
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
  * 下线操作
  * 
  * @param tmp
  * @param record
  * @param mailInfoList
  * @throws BOException
  */
 private void offLineContent(ContentTmp tmp, StringBuffer record,
                             List mailInfoList) throws BOException
 {
     logger.info("#########################开始处理下线内容，contentId="
                 + tmp.getContentId());
     // 根据contentId和内容类型构造出需要编辑的内容属性对象
     Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
     Searchor searchor = new Searchor();
     searchor.getParams().add(new SearchParam("contentID",
                                              RepositoryConstants.OP_EQUAL,
                                              tmp.getContentId()));
     searchor.getParams().add(new SearchParam("type",
                                              RepositoryConstants.OP_LIKE,
                                              "nt:gcontent:app%"));
     // 根据contentId查询pas内容表中是否存在该内容
     List nodeList = rootNode.searchNodes(GContent.TYPE_CONTENT,
                                          searchor,
                                          null);
     if (nodeList.size() != 0)
     {
         List hisList = null;
         
         // 如果是紧急上线应用，添加被动下架历史信息
         if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
         {
             hisList = GoodsChanegHisBO.getInstance().addDelHisToList(tmp.getContentId());
         }
         
         // 被动下架
         GoodsBO.removeAllRefContentByContentID(tmp.getContentId());
         // 同时调用delSyncContent方法删除pas中的内容数据
         this.delSyncContent(tmp.getContentId(), tmp.getContentType());
         
         // 如果是紧急上线应用，添加下架历史信息
         if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
         {
             GoodsChanegHisBO.getInstance().addDataToHis(hisList);
         }
         
         // mailInfoList[2].add(tmp);
         mailInfoList.add(tmp);
         record.append('1');
     }
     record.append('2');// 表示没有发生同步。该内容cms和货架系统都已经下线，不必要同步

 }

 /**
  * 将PAS内容表中的内容删除
  * 
  * @param Contentid,内容编码
  */
 public void delSyncContent(String Contentid, String contentType)
                 throws BOException
 {

     if (logger.isDebugEnabled())
     {
         logger.debug("delSyncContent(" + Contentid + "," + contentType
                      + ")");
     }
     // 根据contentid查询得到pas内容表中唯一编码id
     Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
     Searchor searchor = new Searchor();
     searchor.getParams().add(new SearchParam("contentID",
                                              RepositoryConstants.OP_EQUAL,
                                              Contentid));
     List list = rootNode.searchNodes(contentType, searchor, null);
     // 调用资源管理的删除内容接口将该内容删除
     if (list != null && list.size() != 0)
     {
         // 循环遍历从702节点下删除内容资源
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
         throw new BOException("内容下架时，新增下线应用记录表出错！", e);
     }
 }
 
     /**
      * 用于任务结束后对缓存的清理
      */
     public static void cleanMap()
     {
         ttMap.clear();
         tagMap.clear();
     }
 
}
