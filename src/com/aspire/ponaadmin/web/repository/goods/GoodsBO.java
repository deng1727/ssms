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
 * ����Ʒ������BO��
 * @author bihui
 *
 */
public class GoodsBO
{
    
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(GoodsBO.class);
    
    /**
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
     * @param vo
     * @throws BOException
     */
    public static void addNodeAndInsertGoodsInfo(ReferenceNode refNode,GoodsVO vo) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.addNodeAndInsertGoodsInfo is starting !");
        }
        // �����������
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
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����.Ŀǰֻ��Ӧ��������Ҫд����ʷ��
     * @param category ��Ʒ�ϼܵ�Ŀ����ܶ��󡣸ö��������ֵ��Ҫʱ������
     * @param
     * @throws BOException �ϼܲ��ɹ���
     * @throws NullPointerException ���category�����ֵ����ȫ��
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
        	throw new BOException("û�д����ݣ�id="+contId);
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
    			//������Զ�ˢ�µ��ϼܲ�����ֻ������ʷ����ڵ�goodsid���Ż�д����ʷ��
				if(GoodsDAO.getInstance().isExistedGoodsID(goodsID))
				{
					category.addNode(refNode);
					category.saveNode();
					return;
				}
			} catch (DAOException e)
			{
				throw new BOException("�ϼ���Ʒ����contId="+contId,e);
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
        // �����������
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
            logger.error("�ϼ���Ʒ��������ʷ������contId="+contId,e);
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
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����.Ŀǰֻ��Ӧ��������Ҫд����ʷ��
     * @param category ��Ʒ�ϼܵ�Ŀ����ܶ��󡣸ö��������ֵ��Ҫʱ������
     * @param
     * @throws BOException �ϼܲ��ɹ���
     * @throws NullPointerException ���category�����ֵ����ȫ��
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
        	throw new BOException("û�д����ݣ�id="+refrenceVO.getRefNodeId());
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
        // �����������
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.addNodeAndInsertGoodsInfoForLock(refNode,goodsVO);
            tdb.commit();
            //�����ϼ���Ϣ
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
            logger.error("�ϼ���Ʒ��������ʷ������contId="+refrenceVO.getRefNodeId(),e);
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
        	throw new BOException("û�д����ݣ�id="+contId);
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
    			//������Զ�ˢ�µ��ϼܲ�����ֻ������ʷ����ڵ�goodsid���Ż�д����ʷ��
				if(GoodsDAO.getInstance().isExistedGoodsID(goodsID))
				{
					category.addNode(refNode);
					category.saveNode();
					return;
				}
			} catch (DAOException e)
			{
				throw new BOException("�ϼ���Ʒ����contId="+contId,e);
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
        // �����������
        TransactionDB tdb = null;
        try
        {
            tdb = TransactionDB.getTransactionInstance() ;
            GoodsDAO goodsDAO = GoodsDAO.getTransactionInstance(tdb) ;
            goodsDAO.addNodeAndInsertGoodsInfo(refNode,goodsVO);
            tdb.commit();
            
            
            //��Ʒ���Ż�����Ϣ���͵Ĵ��롣
            try{
            	
            	//SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID,goodsID+":0");
            	
            	//����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
//            	Goodsid	����	String	reference���е�goodsid
//            	Categoryid	��ѡ	String	����categoryid���½�ʱ������
//            	Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//            	Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//            	Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//            	Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������
//            	Action	����	String	0���½�
//            	9��ɾ��
//            	Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
            	logger.error("�ֹ��ϼ��Ƿ�����Ϣ����",e);
            }
            

        }
        catch (DAOException e)
        {
            tdb.rollback();
            logger.error("�ϼ���Ʒ��������ʷ������contId="+contId,e);
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
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
     * @param refID
     * @throws BOException
     */
    public static void removeRefContentFromCategory(String refID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.removeRefContentFromCategory(" + refID + ")is starting !");
        }
        // �����������
        TransactionDB tdb = null;
        try
        {
            ReferenceNode refNode = (ReferenceNode)Repository.getInstance().getNode(refID,"nt:reference");
            if(logger.isDebugEnabled())
            {
                logger.debug("The refNodeID is " + refNode.getRefNodeID());
            }
            //ֱ�ӿ��Դ���Ʒ�����л�ȡ��
            GContent content = (GContent)refNode.getRefNode();//(GContent)Repository.getInstance().getNode(refNode.getRefNodeID(),"nt:gcontent");
            if(logger.isDebugEnabled())
            {
                logger.debug("Ҫ�Ƴ������ýڵ��Ӧ������IDΪ" + content.getId() + ",����������:" + content.getName());
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
    
    
    /**  ˵���������������Ʒ���Ż���ʱ��ӵġ�Ŀ�������ֹ�������Ʒ��ʱ����Ҫ���������ķ���Ϣ�����Ǻǡ�����2013-04-18
     * ��ӽڵ㣬����GoodsVO�е���Ϣд�뵽���ݿ��е���Ʒ��ʷ��Ϣ����
     * @param refID
     * @param transactionID �������Ʒ���Ż��Ļ��ܣ���������ID�ǲ�Ϊ�յġ�add by aiyan 
     * @throws BOException
     */
    public static void removeRefContentFromCategory(String refID,String transactionID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("GoodsBO.removeRefContentFromCategory(" + refID + ")is starting !");
        }
        // �����������
        TransactionDB tdb = null;
        try
        {
            ReferenceNode refNode = (ReferenceNode)Repository.getInstance().getNode(refID,"nt:reference");
            if(logger.isDebugEnabled())
            {
                logger.debug("The refNodeID is " + refNode.getRefNodeID());
            }
            //ֱ�ӿ��Դ���Ʒ�����л�ȡ��
            GContent content = (GContent)refNode.getRefNode();//(GContent)Repository.getInstance().getNode(refNode.getRefNodeID(),"nt:gcontent");
            if(logger.isDebugEnabled())
            {
                logger.debug("Ҫ�Ƴ������ýڵ��Ӧ������IDΪ" + content.getId() + ",����������:" + content.getName());
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
            
//            goodid	���� 	��ƷID
//            Action	����	String	0��������ֱ�Ӹ���
//            9��ɾ��
//            Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
            try{
            	//SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID, goodsVO.getGoodsID()+":9");
            	
                //����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
//              Goodsid	����	String	reference���е�goodsid
//              Categoryid	��ѡ	String	����categoryid���½�ʱ������
//              Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//              Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//              Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//              Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������
//              Action	����	String	0���½�
//              9��ɾ��
//              Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
//            	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID,
//            			goodsVO.getGoodsID()+"::::::9",Constant.MESSAGE_HANDLE_STATUS_RUNNING);
            	
            	//aiyan cancle -5
            	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,null,
            			goodsVO.getGoodsID()+"::::::9",Constant.MESSAGE_HANDLE_STATUS_INIT);

              
              
            }catch(Exception e){
            	logger.error("�ֹ������ʱ����Ϣ���쳣!",e);
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
     * �����������Ӧ�����ýڵ�ȫ�������ݿ���ɾ��������Ʒ�ı����¼�
     * @param contentID ��������
     * @throws BOException
     */
    public static int removeAllRefContentByContentID(String contentID) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeAllRefContentByContentID() is beginning ...." );
        }
        //�����������
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
     * ������Ʒ���������ƷID����t_r_reference���id��������Ҳ�����˵������Ʒ���벻������ϵͳ��ǰ��Ʒ����
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
