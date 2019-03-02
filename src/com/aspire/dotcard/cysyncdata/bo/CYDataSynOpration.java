/**
 * SSMS
 * com.aspire.dotcard.cysyncdata.bo CYDataSynOpration.java
 * Aug 18, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.cysyncdata.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.cysyncdata.dao.CYDataSyncDAO;
import com.aspire.dotcard.cysyncdata.tactic.CYTacticVO;
import com.aspire.dotcard.cysyncdata.vo.CYToMMMappingVO;
import com.aspire.dotcard.gcontent.GAppContent;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncData.dao.DataSyncDAO;
import com.aspire.dotcard.syncData.tactic.TacticConstants;
import com.aspire.dotcard.syncData.util.SyncDataConstant;
import com.aspire.dotcard.syncData.vo.ContentTmp;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.goods.GoodsVO;
import com.aspire.ponaadmin.web.util.PublicUtil;


/**
 * @author tungke
 *
 *创业大赛内容同步多线程执行类
 *
 */
public class CYDataSynOpration
{
	/**
	 * 日志引用
	 */
	protected static JLogger logger = LoggerFactory.getLogger(CYDataSynOpration.class);
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
    
    
 private List  CYtoMMcate = null;
 private List  tacticList = null;
 private List[]  mailInfoList= null ;
 
 private ContentTmp tmp;
 
 
 
 public  CYDataSynOpration(List  CYtoMMcate,List  tacticList,ContentTmp tmp, List[]  mailInfoList)
 {
	 if(null == this.CYtoMMcate){
		 this.CYtoMMcate = CYtoMMcate; 
	 }
     if(null == this.tacticList ){
    	 this.tacticList = tacticList; 
     }
if(null == this.mailInfoList ){
	 this.mailInfoList = mailInfoList; 
     }

	 this.tmp = tmp;
	 
 }
 
 
    
	//public void cyDataSynOp(ContentTmp tmp,List tacticList,List[] mailInfoList){
 public void cyDataSynOp(){
		
		 //用于记录应用处理的结果
        StringBuffer record=null;
        TransactionDB tdb = null;
        // 得到ContentTmp对象
       // ContentTmp tmp = ( ContentTmp ) list.get(i);
        try
        {
            // 进行事务调用
            tdb = TransactionDB.getTransactionInstance();
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);
            
            record=new StringBuffer(tmp.getContentId());
            record.append(" | ");
            record.append(tmp.getLupdDate());
            record.append(" | ");
            record.append(tmp.getStatus());
            record.append(" | ");
            // 如果内容为发布状态
            if (SyncDataConstant.CONTENT_TYPE_RELEASE.equals(tmp.getStatus()))
            {
            	logger.info("#########################创业大赛开始处理上线内容，contentId="+tmp.getContentId());
                // 调用editSyncContent对PAS中的内容数据进行编辑
               // int result = this.editSyncContent(tmp.getContentId(),
                //                     tmp.getContentType(),tacticList);
            	int result = this.editSyncContent(tmp,tacticList,record,mailInfoList[2]);
	
            	
                record.append('1');
                if(result==SyncDataConstant.SYNC_UPDATE)
                {
                	mailInfoList[0].add(tmp);
                }else if(result==SyncDataConstant.SYNC_DEL){
                	//无机型适配关系 下线应用
                	//mailInfoList[2].add(tmp);
                }else
                {
                	mailInfoList[1].add(tmp);
                }
            }
            // 如果内容为过期状态
            else if (SyncDataConstant.CONTENT_TYPE_OVERDUE.equals(tmp.getStatus()))
            {
            		// 下线操作
            	offLineContent(tmp,record,mailInfoList[2]);
            	/*
            	logger.info("#########################开始处理下线内容，contentId="+tmp.getContentId());
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
                List nodeList = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
                if(nodeList.size()!=0)
                {
                	GoodsBO.removeAllRefContentByContentID(tmp.getContentId());
                    // 同时调用delSyncContent方法删除pas中的内容数据
                    this.delSyncContent(tmp.getContentId(),
                                         tmp.getContentType());
                    mailInfoList[2].add(tmp);
                    record.append('1');
                }
                record.append('2');//表示没有发生同步。该内容cms和货架系统都已经下线，不必要同步
           */ }else
            {
            	logger.error("该状态不合法，数据出错，忽略该数据。status="+tmp.getStatus());
            	mailInfoList[3].add(tmp);
            	record.append('0');
            }
            // 调用CYDataSyncDAO的delSynccontetTmp方法删除t_syncContent_tmp表中的这笔记录
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
     * @throws Exception
     */
  //  private int editSyncContent(String contentId, String contentType,List tacticList)
    private int editSyncContent(ContentTmp tmp,List tacticList,StringBuffer record,List mailInfoList)

                    throws BOException
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
        searchor.getParams().add(new SearchParam("type",
                RepositoryConstants.OP_LIKE,
                "nt:gcontent:app%"));
        // 根据contentId查询pas内容表中是否存在该内容
        List list = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
        // 调用dao的getGcontentFromCMS方法
        GContent gc;
		try
		{
			gc = CYDataSyncDAO.getInstance().getGcontentFromCMS(contentId,
					contentType);
			if (gc != null) {
				GAppContent gap = (GAppContent) gc;
				if (gap.getDeviceName() == null
						|| gap.getDeviceName().equals("")) {
					// 适配关系为空
					// 下线操作
					logger.error("------devicename is null contentid="+tmp.getContentId());
					offLineContent(tmp, record, mailInfoList);
					//无机型适配关系 下线应用
					return SyncDataConstant.SYNC_DEL;
				}
			}
		} catch (DAOException e)
		{
			throw new BOException("从CMS中无法获取内容失败。contentId="+contentId,e);
		}
        // 修改keywords,将格式转化为我们的格式.(将 ; 分隔 转换成 };{ 分隔)
        processKeywords(gc);
        
        
       if (gc.getSubType().equals("6"))
		{
			// 类型为创业大赛应用类型
			// 根据映射替换二级分类名称，添加MM创业大赛一级分类ID,2001,应用类；2002，文艺类
			if (CYtoMMcate == null || CYtoMMcate.size() <= 0)
			{
				try
				{
					CYtoMMcate = CYDataSyncDAO.getInstance().getAllCYToMMMapping();
				} catch (DAOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			GAppContent appGc = (GAppContent)gc;
			for(int i =0 ; i < CYtoMMcate.size(); i ++){
				CYToMMMappingVO vo =  (CYToMMMappingVO)  CYtoMMcate.get(i);
				if(vo.getAppCateId().equals(appGc.getAppCateID())){
					//匹配映射关系
					appGc.setAppCateName(vo.getCYAppCateName());
					//appGc.setProgramID(vo.getCYCateId());//设置创业大赛一级分类ID,2001,应用类，2002，文艺类
				}
			}
			
		}

        
        
        // 如果存在,则调用资源管理中的更新接口对内容属性对象进行更新
        if (list.size() != 0)
        {
            
            Node node = ( Node ) list.get(0);
            logger.debug("the node is:" + node);
            String id = node.getId();
            gc.setId(id);
            
            //内容订购量需要保留原值
            GContent oldContent = (GContent)node;
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

            //下架
         //  boolean staus = checkAndUpdateCategory(gc,oldContent);
            // 进行更新操作
            gc.save();            
            //上架
//            if(staus){
//            	//修改了分类需要重新上架分类
//            	checkAndAddCategory(contentType, gc, tacticList);
//            }
            
            
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
            if(gc.getAuditionUrl() == null || gc.getAuditionUrl().length()==0){
            	//AuditionUrl 非空并且长度大于0，则为WP应用，不需要上架分类货架  add by dongke 20130709
            	checkAndAddCategory(contentType, gc, tacticList);	
            }
            
            return SyncDataConstant.SYNC_ADD;
        }
    }
    /**
     * 下线操作
     * @param tmp
     * @param record
     * @param mailInfoList
     * @throws BOException
     */
    private void offLineContent(ContentTmp tmp,StringBuffer record,List mailInfoList) throws BOException{

    	logger.info("#########################开始处理下线内容，contentId="+tmp.getContentId());
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
        List nodeList = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
        if(nodeList.size()!=0)
        {
        	GoodsBO.removeAllRefContentByContentID(tmp.getContentId());
            // 同时调用delSyncContent方法删除pas中的内容数据
            this.delSyncContent(tmp.getContentId(),
                                 tmp.getContentType());
           // mailInfoList[2].add(tmp);
            mailInfoList.add(tmp);
            record.append('1');
        }
        record.append('2');//表示没有发生同步。该内容cms和货架系统都已经下线，不必要同步
    	
    }
    
    /**
     * 将keywords的格式换成PAS的格式
     * 
     * @param gc
     */
    private void processKeywords(GContent gc)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("processKeywords:" +gc);
        }
        if(gc == null)
            return;
            
        String keywords = gc.getKeywords();
        if (keywords != null && !"".equals(keywords))
        {
            // 处理最后的分号
            while (keywords.endsWith(";"))
            {
                keywords = keywords.substring(0, keywords.length() - 1);
            }
            keywords = "{" + keywords.replaceAll(";", "};{") + "}";
            gc.setKeywords(keywords);
        }
    }
    /**
     * 匹配内容上架策略
     * @param contentType
     * @param gc
     */
    private void checkAndAddCategory(String type,GContent gc,List tacticList) throws BOException
    {
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("DataSyncBO.checkAndAddCategory(). contentType=" + type);
    	}
    	CYTacticVO vo;
    	int size = tacticList.size();
    	String temp;
    	
    	GAppContent appGc = (GAppContent)gc;
    	String cyYear = appGc.getContestYear();
    	if(cyYear == null || cyYear.equals("") ){
    		logger.error("数据不合法，ContestYear is null "+appGc.getContentID() );
    		return;
    	}else if(cyYear.equals("2010")){
    		logger.error("old  CY  app ，ContestYear is 2010 "+appGc.getContentID() );
    		return;		
    	}
    	String categoryID;
    	//用内容去匹配每一个策略
    	for(int i = 0; i < size; i++)
    	{
    		vo = (CYTacticVO)tacticList.get(i);
    		categoryID = vo.getCategoryID();
    		
    		//先检查该策略对应的内容货架分类是否已经上架了该内容
    		if(checkTTMap(categoryID,gc.getContentID()))
    		{
    			continue;
    		}
    		
    		temp = vo.getContentType();
    		
    		//第一步：检查内容类型是否匹配
    		if(!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || type.equals(temp)))
    		{
    			//类型不匹配,检查下一个策略
    			continue;    			
    		}
    		    		
    		//第二步：检查umFlag业务通道是否匹配
    		temp = vo.getUmFlag();
    		if(!TacticConstants.UMFLAG_ALL.equals(temp))
    		{
                // 内容对应业务的业务通道类型
                String gcUmFlag = null;
                gcUmFlag = getGContentUmflag(gc);
                if (!temp.equals(gcUmFlag))
    			{
    				//业务通道类型不匹配，检查下一个策略
    				continue;
    			}
    		}
    		
    		//第三步：检查内容标识是否匹配
    		String keywords = gc.getKeywords();
    		int relation = vo.getTagRelation();
    		temp = vo.getContentTag();
    		if(!(null == temp || "".equals(temp) || 
    				relation == TacticConstants.TABRELATION_NULL || 
    				checkContentTag(keywords,vo.getTagList(),relation)))
    		{
    			//内容标识不匹配，检查下一个策略
    			continue;
    		}
    		
            //第四步：检查应用分类名称是否匹配
            if (gc instanceof GAppContent)
            {
                //将gc转化成应用内容获取应用分类属性
                GAppContent app = (GAppContent)gc;
                //判断应用分类属性是否与同步策略中的应用分类名称匹配
                if (!app.getAppCateName().equals(vo.getAppCateName()))
                {
                    continue;
                }
            }
      
    		//第五步：所有检查通过，将内容上架到策略对应的货架货架下
    		addCategory(gc,categoryID); 
    		//写到缓存中
    		HashMap map = (HashMap)ttMap.get(categoryID);
    		if(null == map)
    		{
    			map = new HashMap();   
    			ttMap.put(categoryID, map);
    		}
    		map.put(gc.getContentID(), "");    		
    	}
    }
    /**
     * 检查ttMap中是否有categoryID对应的contentID
     * @param categoryID
     * @param contentID
     * @return true 表示包换，false 表示不包含
     */
    private boolean checkTTMap(String categoryID,String contentID)
    {
    	HashMap map = (HashMap)ttMap.get(categoryID);
    	if(map==null)
    	{
    		return false;
    	}else
    	{
    		return map.containsKey(contentID);
    	}
    }
    
    
    /**
     * 取内容对应业务的业务通道类型umFlag
     * @param gc
     * @return
     */
    private String getGContentUmflag(GContent gc) 
    {
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("DataSyncBO.getGContentUmflag()");
    	}
    	try
        {
            return CYDataSyncDAO.getInstance().queryContentUmflag(gc.getIcpCode(), gc.getIcpServId());
        }
        catch (DAOException e)
        {
        	logger.error("通过企业代码和业务代码获取Umflag时发生数据库异常！",e);
        	return null;
            //throw new BOException("通过企业代码和业务代码获取Umflag时发生数据库异常！",e);
        }
    }
    
    /**
     * 检查内容标签和策略标签是否匹配
     * @param gcTag 内容标签字符串,格式： {标签一};{标签二};{标签三}
     * @param tacticTag 策略标签字符串，格式： 标签一;标签二;标签三
     * @param relation 策略关系：1=and; 2=or
     * @return true:匹配  false:不匹配
     */
    private boolean checkContentTag(String gcTag,List ttList, int relation)
    {
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("DataSyncBO.checkContentTag()");
    		logger.debug("内容标签：" + gcTag + " 策略标签：" + ttList + " 策略关系：" + relation);
    	}
    	if(!(relation == TacticConstants.TABRELATION_OR || relation == TacticConstants.TABRELATION_AND))
    	{
    		return false;
    	}    	
		
		List gcList = (List)tagMap.get(gcTag);
		if(null == gcList)
		{
			gcList = new ArrayList();
			String[] items = gcTag.split(";");
			String item;
			for(int i = 0; i < items.length; i++)
	        {
	        	item = items[i];
	        	if(item.length() == 0)
	        	{
	        		continue;
	        	}
	        	try
	        	{
	        		item = item.substring(item.indexOf("{")+1,item.lastIndexOf("}"));
	        		gcList.add(item);
	        	}
	        	catch(Exception e)
	        	{
	        		logger.error("内容标签格式错误！contentTag="+gcTag);
	        		logger.error(e);
	        	}
	        }
			tagMap.put(gcTag, gcList);
		}

        if(relation == TacticConstants.TABRELATION_OR) // or
        {
        	for(int i = 0; i < ttList.size(); i++)
        	{
        		if(gcList.contains(ttList.get(i)))
        		{
            		//包含策略中任一一个标签
            		return true;
        		}
        	}
        }        
        else if(relation == TacticConstants.TABRELATION_AND && gcList.containsAll(ttList)) // and
        {
    		//包含策略中所有的标签
        	return true;
        }
    	
    	return false;
    }
    
    /**
     * 将内容上架到货架货架下
     * @param gc
     * @param categoryID
     */
    private void addCategory(GContent gc,String categoryID) throws BOException
    {
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("=====addCategory: categoryID="+categoryID +  " contentID=" + gc.getContentID());
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
		}
		catch (Exception e)
		{
			logger.error(e);
            throw new BOException("内容上架出错！", e);
		}
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
            DataSyncDAO.getInstance().addContentIdHis(Contentid, SyncDataConstant.DEL_CONTENT_TYPE_CY);
        }
        catch (DAOException e)
        {
            throw new BOException("内容下架时，新增下线应用记录表出错！", e);
        }
    }
}
