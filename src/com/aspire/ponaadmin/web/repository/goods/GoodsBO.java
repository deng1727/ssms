package com.aspire.ponaadmin.web.repository.goods;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 对商品操作的BO类
 * @author bihui
 *
 */
public class GoodsBO
{
    
    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(GoodsBO.class);
    
    /**
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中
     * @param vo
     * @throws BOException
     */
    public static void addNodeAndInsertGoodsInfo(ReferenceNode refNode,GoodsVO vo) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.addNodeAndInsertGoodsInfo is starting !");
        }
        // 用于事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.addNodeAndInsertGoodsInfo(refNode,vo);
            tdb.commit();
        }
        catch (DAOException e)
        {
            tdb.rollback();
            logger.error(e);
            throw new BOException("addContentToCategory failed!",e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
    }
    /**
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中.目前只有应用来才需要写入历史表
     * @param category 商品上架的目标货架对象。该对象的属性值需要时完整的
     * @param
     * @throws BOException 上架不成功。
     * @throws NullPointerException 如果category对象的值不完全。
     */
    public static void addNodeAndInsertGoodsInfo(Category category,String contId,int sortId,int variation,boolean isAutoReflesh,String menuStatus) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.addNodeAndInsertGoodsInfo is starting !");
        }
        GContent content = (GContent)Repository.getInstance().getNode(contId,RepositoryConstants.TYPE_GCONTENT);
        if(content==null)
        {
        	throw new BOException("没有此内容，id="+contId);
        }
        String goodsID = "";
        ReferenceNode refNode = new ReferenceNode();
        if(StringUtils.isEmpty(content.getAppId())){
        	goodsID = category.getCategoryID()
        	        + PublicUtil.lPad(content.getCompanyID(), 6)
        	        + PublicUtil.lPad(content.getProductID(), 12)
        	        + PublicUtil.lPad(content.getContentID(), 12);
        }else{
        	goodsID = category.getCategoryID()
        	        + "000000"
        	        + PublicUtil.lPad(content.getAppId(), 12)
        	        + PublicUtil.lPad(content.getContentID(), 12);
        	//refNode.setAppId(content.getAppId());
        }
    	
    	 
         refNode.setRefNode(content);
         
         refNode.setParentID(category.getId());
         if("yes".equals(category.getVerifyFlag())){
        	 refNode.setVerifyStatus("0");
         }else{
        	 refNode.setVerifyStatus("1");
         }
         if (logger.isDebugEnabled())
         {
             logger.debug("The addednode's type is " + content.getType());
         }
         refNode.setRefNodeID(contId);
         refNode.setSortID(sortId);
         refNode.setVariation(variation);
         refNode.setGoodsID(goodsID);
         refNode.setCategoryID(category.getCategoryID()); 
         refNode.setLoadDate(PublicUtil.getCurDateTime());
         if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
         	refNode.setVerifyStatus("3");
         	category.setGoodsStatus("0");
         	category.save();
  		} else{
  			refNode.setVerifyStatus("1");
  		} 
    	if(isAutoReflesh)
    	{
    		try
			{
    			//如果是自动刷新的上架操作，只有在历史表存在的goodsid，才会写入历史表。
				if(GoodsDAO.getInstance().isExistedGoodsID(goodsID))
				{
					category.addNode(refNode);
					category.saveNode();
					return;
				}
			} catch (DAOException e)
			{
				throw new BOException("上架商品出错。contId="+contId,e);
			}
    	}
        String newNodeID = category.getNewAllocateID();
        refNode.setId(newNodeID);
        refNode.setPath(category.getPath() + ".{" + newNodeID + "}");
       
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoodsID(goodsID);
        goodsVO.setIcpCode(content.getIcpCode());
        goodsVO.setIcpServId(content.getIcpServId());
        goodsVO.setContentID(content.getContentID());
        goodsVO.setCategoryID(category.getCategoryID());
        goodsVO.setGoodsName(content.getName());
        goodsVO.setState(1);
        goodsVO.setChangeDate(new Date());
        goodsVO.setActionType(1);
        // 用于事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.addNodeAndInsertGoodsInfo(refNode,goodsVO);
            tdb.commit();
        }
        catch (DAOException e)
        {
            tdb.rollback();
            logger.error("上架商品（包含历史表）出错，contId="+contId,e);
            throw new BOException("addContentToCategory failed!contId="+contId,e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
    }
    /**
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中.目前只有应用来才需要写入历史表
     * @param category 商品上架的目标货架对象。该对象的属性值需要时完整的
     * @param
     * @throws BOException 上架不成功。
     * @throws NullPointerException 如果category对象的值不完全。
     */
    public static String addNodeAndInsertGoodsInfo(Category category,RefrenceVO refrenceVO,boolean sendMsg) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.addNodeAndInsertGoodsInfo is starting !");
        }
        GContent content = (GContent)Repository.getInstance().getNode(refrenceVO.getRefNodeId(),RepositoryConstants.TYPE_GCONTENT);
        if(content==null)
        {
        	throw new BOException("没有此内容，id="+refrenceVO.getRefNodeId());
        }
        
    	String goodsID = category.getCategoryID()
        + PublicUtil.lPad(content.getCompanyID(), 6)
        + PublicUtil.lPad(content.getProductID(), 12)
        + PublicUtil.lPad(content.getContentID(), 12);
    	
    	 ReferenceNode refNode = new ReferenceNode();
         refNode.setRefNode(content);
         refNode.setParentID(category.getId());
         if("yes".equals(category.getVerifyFlag())){
        	 refNode.setVerifyStatus("0");
         }else{
        	 refNode.setVerifyStatus("1");
         }
         if (logger.isDebugEnabled())
         {
             logger.debug("The addednode's type is " + content.getType());
         }
         refNode.setIsLock(refrenceVO.getIsLock());
//         refNode.setLockNum(refrenceVO.getLockNum());
         refNode.setLockUser(refrenceVO.getLockUser());
         refNode.setRefNodeID(refrenceVO.getRefNodeId());
         refNode.setSortID(refrenceVO.getSortId());
         refNode.setVariation(99999);
         refNode.setGoodsID(goodsID);
         refNode.setCategoryID(category.getCategoryID()); 
         refNode.setLoadDate(PublicUtil.getCurDateTime());
  			refNode.setVerifyStatus("1");
        String newNodeID = category.getNewAllocateID();
        refNode.setId(newNodeID);
        refNode.setPath(category.getPath() + ".{" + newNodeID + "}");
       
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoodsID(goodsID);
        goodsVO.setIcpCode(content.getIcpCode());
        goodsVO.setIcpServId(content.getIcpServId());
        goodsVO.setContentID(content.getContentID());
        goodsVO.setCategoryID(category.getCategoryID());
        goodsVO.setGoodsName(content.getName());
        goodsVO.setState(1);
        goodsVO.setChangeDate(new Date());
        goodsVO.setActionType(1);
        // 用于事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.addNodeAndInsertGoodsInfoForLock(refNode,goodsVO);
            tdb.commit();
            //发送上架消息
            if(sendMsg){
            	SSMSDAO.getInstance().addMessages(
						MSGType.RefModifyReq,
						null,
						goodsID
								+ ":"
								+ category.getCategoryID()
								+ ":"
								+ category.getId()
								+ ":"
								+ refNode.getRefNodeID()
								+ ":"
								+ refNode.getSortID()
								+ ":"
								+ DateUtil.formatDate(
										goodsVO.getChangeDate(),
										"yyyyMMddHHmmss") + ":0",
						Constant.MESSAGE_HANDLE_STATUS_INIT);
            }
            return newNodeID;
        }
        catch (DAOException e)
        {
            tdb.rollback();
            logger.error("上架商品（包含历史表）出错，contId="+refrenceVO.getRefNodeId(),e);
            throw new BOException("addContentToCategory failed!contId="+refrenceVO.getRefNodeId(),e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
    }
    
    public static void addNodeAndInsertGoodsInfo(Category category,String contId,int sortId,int variation,boolean isAutoReflesh,String transactionID,String menuStatus) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.addNodeAndInsertGoodsInfo is starting !");
        }
        GContent content = (GContent)Repository.getInstance().getNode(contId,RepositoryConstants.TYPE_GCONTENT);
        if(content==null)
        {
        	throw new BOException("没有此内容，id="+contId);
        }
        
    	String goodsID = category.getCategoryID()
        + PublicUtil.lPad(content.getCompanyID(), 6)
        + PublicUtil.lPad(content.getProductID(), 12)
        + PublicUtil.lPad(content.getContentID(), 12);
    	
    	 ReferenceNode refNode = new ReferenceNode();
    	 if("yes".equals(category.getVerifyFlag())){
    		 refNode.setVerifyStatus("0");
    	 }else{
    		 refNode.setVerifyStatus("1");
    	 }
         refNode.setRefNode(content);
         refNode.setParentID(category.getId());
         
         if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
        	refNode.setVerifyStatus("3");
        	category.setGoodsStatus("0");
        	category.save();
 		}else{
 			category.setGoodsStatus("1");
 			category.save();
 		}    	
         
         if (logger.isDebugEnabled())
         {
             logger.debug("The addednode's type is " + content.getType());
         }
         refNode.setRefNodeID(contId);
         refNode.setSortID(sortId);
         refNode.setVariation(variation);
         refNode.setGoodsID(goodsID);
         refNode.setCategoryID(category.getCategoryID()); 
         refNode.setLoadDate(PublicUtil.getCurDateTime());
    	if(isAutoReflesh)
    	{
    		try
			{
    			//如果是自动刷新的上架操作，只有在历史表存在的goodsid，才会写入历史表。
				if(GoodsDAO.getInstance().isExistedGoodsID(goodsID))
				{
					category.addNode(refNode);
					category.saveNode();
					return;
				}
			} catch (DAOException e)
			{
				throw new BOException("上架商品出错。contId="+contId,e);
			}
    	}
        String newNodeID = category.getNewAllocateID();
        refNode.setId(newNodeID);
        refNode.setPath(category.getPath() + ".{" + newNodeID + "}");
       
        GoodsVO goodsVO = new GoodsVO();
        goodsVO.setGoodsID(goodsID);
        goodsVO.setIcpCode(content.getIcpCode());
        goodsVO.setIcpServId(content.getIcpServId());
        goodsVO.setContentID(content.getContentID());
        goodsVO.setCategoryID(category.getCategoryID());
        goodsVO.setGoodsName(content.getName());
        goodsVO.setState(1);
        goodsVO.setChangeDate(new Date());
        goodsVO.setActionType(1);
        // 用于事务操作
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.addNodeAndInsertGoodsInfo(refNode,goodsVO);
            tdb.commit();
            
            
            //商品库优化加消息发送的代码。
            try{
            	
            	//SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID,goodsID+":0");
            	
            	//大胖说要把上下架消息变大,所以注销上面的，改下以下。 add by aiyan 2013-04-27
//            	Goodsid	必须	String	reference表中的goodsid
//            	Categoryid	可选	String	货架categoryid，新建时必须有
//            	Id	可选	String	货架categoryid对应的Id，新建时必须有
//            	Refnodeid	可选	String	应用ID，新建时必须有
//            	Sortid	可选	String	排序字段，新建时必须有
//            	Loaddate	可选	String	更新时间，新建时必须有
//            	Action	必须	String	0：新建
//            	9：删除
//            	Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
//            	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,
//            			transactionID,
//            			goodsID+":"+category.getCategoryID()+":"+category.getId()+":"+refNode.getRefNodeID()+":"+refNode.getSortID()+":"+DateUtil.formatDate(goodsVO.getChangeDate(), "yyyyMMddHHmmss")+":0"
//            			,Constant.MESSAGE_HANDLE_STATUS_RUNNING);
            	
            	//aiyan cancel -5
				if (!(menuStatus != null && !"".equals(menuStatus) && "1"
						.equals(menuStatus)))
					SSMSDAO.getInstance().addMessages(
							MSGType.RefModifyReq,
							null,
							goodsID
									+ ":"
									+ category.getCategoryID()
									+ ":"
									+ category.getId()
									+ ":"
									+ refNode.getRefNodeID()
									+ ":"
									+ refNode.getSortID()
									+ ":"
									+ DateUtil.formatDate(
											goodsVO.getChangeDate(),
											"yyyyMMddHHmmss") + ":0",
							Constant.MESSAGE_HANDLE_STATUS_INIT);
            	
            }catch(Exception e){
            	logger.error("手工上架是发送消息出错！",e);
            }
            

        }
        catch (DAOException e)
        {
            tdb.rollback();
            logger.error("上架商品（包含历史表）出错，contId="+contId,e);
            throw new BOException("addContentToCategory failed!contId="+contId,e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
    }
    
    /**
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中
     * @param refID
     * @throws BOException
     */
    public static void removeRefContentFromCategory(String refID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.removeRefContentFromCategory(" + refID + ")is starting !");
        }
        // 用于事务操作
        TransactionDB tdb = null;
        try
        {
            ReferenceNode refNode = (ReferenceNode)Repository.getInstance().getNode(refID,"nt:reference");
            if(logger.isDebugEnabled())
            {
                logger.debug("The refNodeID is " + refNode.getRefNodeID());
            }
            //直接可以从商品对象中获取。
            GContent content = (GContent)refNode.getRefNode();//(GContent)Repository.getInstance().getNode(refNode.getRefNodeID(),"nt:gcontent");
            if(logger.isDebugEnabled())
            {
                logger.debug("要移除的引用节点对应的内容ID为" + content.getId() + ",内容名称是:" + content.getName());
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
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.removeRefContentFromCategory(refID,goodsVO);
            tdb.commit();
        }
        catch (Exception e)
        {
            tdb.rollback();
            logger.error(e);
            throw new BOException("removeRefContentFromCategory failed!",e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
    }
    
    
    /**  说明：这个方法是商品库优化的时候加的。目的是在手工导入商品的时候需要向数据中心发消息。。呵呵。。。2013-04-18
     * 添加节点，并将GoodsVO中的信息写入到数据库中的商品历史信息表中
     * @param refID
     * @param transactionID 如果是商品库优化的货架，这里事务ID是不为空的。add by aiyan 
     * @throws BOException
     */
    public static void removeRefContentFromCategory(String refID,String transactionID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.removeRefContentFromCategory(" + refID + ")is starting !");
        }
        // 用于事务操作
        TransactionDB tdb = null;
        try
        {
            ReferenceNode refNode = (ReferenceNode)Repository.getInstance().getNode(refID,"nt:reference");
            if(logger.isDebugEnabled())
            {
                logger.debug("The refNodeID is " + refNode.getRefNodeID());
            }
            //直接可以从商品对象中获取。
            GContent content = (GContent)refNode.getRefNode();//(GContent)Repository.getInstance().getNode(refNode.getRefNodeID(),"nt:gcontent");
            if(logger.isDebugEnabled())
            {
                logger.debug("要移除的引用节点对应的内容ID为" + content.getId() + ",内容名称是:" + content.getName());
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
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.removeRefContentFromCategory(refID,goodsVO);
            tdb.commit();
            
//            goodid	必须 	商品ID
//            Action	必须	String	0：新增或直接更新
//            9：删除
//            Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
            try{
            	//SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID, goodsVO.getGoodsID()+":9");
            	
                //大胖说要把上下架消息变大,所以注销上面的，改下以下。 add by aiyan 2013-04-27
//              Goodsid	必须	String	reference表中的goodsid
//              Categoryid	可选	String	货架categoryid，新建时必须有
//              Id	可选	String	货架categoryid对应的Id，新建时必须有
//              Refnodeid	可选	String	应用ID，新建时必须有
//              Sortid	可选	String	排序字段，新建时必须有
//              Loaddate	可选	String	更新时间，新建时必须有
//              Action	必须	String	0：新建
//              9：删除
//              Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
//            	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID,
//            			goodsVO.getGoodsID()+"::::::9",Constant.MESSAGE_HANDLE_STATUS_RUNNING);
            	
            	//aiyan cancle -5
            	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,null,
            			goodsVO.getGoodsID()+"::::::9",Constant.MESSAGE_HANDLE_STATUS_INIT);

              
              
            }catch(Exception e){
            	logger.error("手工导入的时候发消息出异常!",e);
            }
        }
        catch (Exception e)
        {
            tdb.rollback();
            logger.error(e);
            throw new BOException("removeRefContentFromCategory failed!",e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
    }
    
    
    /**
     * 将内容内码对应的引用节点全部从数据库中删除，即商品的被动下架
     * @param contentID 内容内码
     * @throws BOException
     */
    public static int removeAllRefContentByContentID(String contentID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeAllRefContentByContentID() is beginning ...." );
        }
        //用于事务操作
        TransactionDB tdb = null;
        List list = null;
        int offLineGoods;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            list = goodsDAO.getAllRefIDFromContentID(contentID);
            offLineGoods = list.size();
            Iterator it = list.iterator();
            String refID;
            while(it.hasNext())
            {
                refID = (String)it.next();
                ReferenceNode refNode = (ReferenceNode)Repository.getInstance().getNode(refID,"nt:reference");
                GContent content =(GContent)refNode.getRefNode(); //(GContent)Repository.getInstance().getNode(refNode.getRefNodeID(),"nt:gcontent");
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
                goodsDAO.removeRefContentFromCategory(refID,goodsVO);
            }
            tdb.commit();
        }
        catch (Exception e)
        {
            tdb.rollback();
            logger.error(e);
            throw new BOException("removeAllRefContentByContentID failed!",e);
        }
        finally
        {
            if(tdb!=null)
            {
                tdb.close();
            }
        }
        return offLineGoods;
    }
    
    /**
     * 根据商品编码查找商品ID（即t_r_reference表的id），如果找不到则说明此商品编码不存在于系统当前商品表中
     * @param goodsID
     * @return
     */
    public static String getRefIDbyGoodsID(String goodsID)
    {
        String refID = null;
        if (logger.isDebugEnabled())
        {
            logger.debug("getRefIDbyGoodsID(" + goodsID + ") is beginning ...." );
        }
        try
        {
            refID = GoodsDAO.getInstance().getRefIDbyGoodsID(goodsID);
        }
        catch (DAOException e)
        {
            logger.error(e);
        }
        return refID;
    }
    
    public static Map getRefNodebyRefID(String id)
    {
    	Map refNode = null;
        if (logger.isDebugEnabled())
        {
            logger.debug("getRefNodebyRefID(" + id + ") is beginning ...." );
        }
        try
        {
        	refNode = GoodsDAO.getInstance().getRefNodebyRefID(id);
        }
        catch (DAOException e)
        {
            logger.error(e);
        }
        return refNode;
    }
}
