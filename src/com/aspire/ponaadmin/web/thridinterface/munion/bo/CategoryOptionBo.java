package com.aspire.ponaadmin.web.thridinterface.munion.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.gcontent.GContentDAO;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.MessageSendDAO;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.CategoryTools;
import com.aspire.ponaadmin.web.repository.GoodsChanegHisBO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.ReferenceTools;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.repository.web.ExcelRowConvert;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class CategoryOptionBo {
    /**
     * singleton模式的实例
     */
    private static CategoryOptionBo instance = new CategoryOptionBo();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryOptionBo ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static CategoryOptionBo getInstance()
    {
        return instance;
    }
	 private static final JLogger LOG = LoggerFactory.getLogger(CategoryOptionBo.class);
	 
	 
	 public Object convertData(Map m) {
			// TODO Auto-generated method stub
			ReferenceNode refNode = new ReferenceNode();
			refNode.setSortID(-1*Integer.parseInt(m.get(0)+""));
			String value="";
		    try
         {
             // 查询contentID对应的主键id，返回无结果则默认插入contentid
		    	value=m.get(1)+"";
             String id = GContentDAO.getInstance()
                                    .getContentByID(value);
             if (!"".equals(id))
             {
                 value = id;
             }
         }
         catch (Exception e)
         {
             LOG.error("查询主键id失败：contentid=" + value, e);
         }
			refNode.setRefNodeID(value);
		
			return refNode;
		}
	 
	 
	public int importCateData(String[] contentids,Category category,String isSyn,String menuStatus) throws BOException
	{
		
		List list = new ExcelHandle().paraseDataMap(contentids, new ExcelRowConvert(){
			@Override
			public Object convertData(Map m) {
				// TODO Auto-generated method stub
				ReferenceNode refNode = new ReferenceNode();
				refNode.setSortID(-1*Integer.parseInt(m.get(0)+""));
				String value="";
			    try
                {
                    // 查询contentID对应的主键id，返回无结果则默认插入contentid
			    	value=m.get(1)+"";
                    String id = GContentDAO.getInstance()
                                           .getContentByID(value);
                    if (!"".equals(id))
                    {
                        value = id;
                    }
                }
                catch (Exception e)
                {
                    LOG.error("查询主键id失败：contentid=" + value, e);
                }
				refNode.setRefNodeID(value);
			
				return refNode;
			}});
		
		
		int count=0;
		Map oldSortIdCollection= new HashMap();
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
		String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
		String transactionID = null;
		 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
			//||operateCategoryId.indexOf(cateId)!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息 modify by aiyan 2013-05-10		
			||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateCategoryId)){//modify by aiyan 2013-05-18	 
			 transactionID = ContextUtil.getTransactionID();
		 }
		//above add by aiyan 2013-04-18 && 2013-05-10
		 
		 
		LOG.info("下架该分类下的所有商品。cateId="+category.getId());
		//商品下架。
		//Category category=(Category)Repository.getInstance().getNode(cateId, RepositoryConstants.TYPE_CATEGORY);
		List subNodes = category.searchNodes(RepositoryConstants.TYPE_REFERENCE, null, null);
		//已被锁定的商品
		Map<String,String> lockedRefMap = new HashMap<String, String>();
		//对excel里的内容去重一下
		deduplication(list);
		for (int i = 0; i < subNodes.size(); i++)
		{
			ReferenceNode node = (ReferenceNode) subNodes.get(i);
			//已被锁定的商品，不可删除。
			if(node.getIsLock()==1){
				lockedRefMap.put(node.getRefNodeID(), "");
				LOG.info("全量下架：忽略下架已锁定商品"+node.getRefNodeID());
				continue;
			}
			oldSortIdCollection.put(node.getRefNodeID(),new Integer(node.getSortID()));
			//String id = node.getId();//包括应用和数字内容
			String id = node.getRefNodeID();//只包含应用
			if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
				boolean flag = false;
				//排除全量文件中含有，并且也已经上架了的商品。
				if(list!=null&&list.size()>0){
					for(int k=list.size()-1;k>=0;k--){
						ReferenceNode r = (ReferenceNode)list.get(k);
						if(r.getRefNodeID().equals(node.getRefNodeID())){
							list.remove(k);
							node.setSortID(r.getSortID());
							node.setLoadDate(PublicUtil.getCurDateTime());
							flag = true;
							count++;
							break;
						}
					}
				}
            	category.setGoodsStatus("0");
            	category.save();
            	if(!flag){
            		node.setDelFlag(node.getVerifyStatus());
                	node.setLoadDate(PublicUtil.getCurDateTime());
            	}
            	node.setVerifyStatus("3");
            	node.save();
            	
            }else{
            	if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// 如果是应用需要填写商品历史表。
    			{
    				//GoodsBO.removeRefContentFromCategory(node.getId());//remove by aiyan 2013-04-18
    				//below modified 
    				if(transactionID!=null){
    					GoodsBO.removeRefContentFromCategory(node.getId(),transactionID);
    				}else{
    					GoodsBO.removeRefContentFromCategory(node.getId());
    				}
    				
    			}
    			else
    			{
    				category.delNode(node);
    			}
			}
            GContent content = ( GContent ) Repository.getInstance()
            .getNode(node.getRefNodeID(),
                     "nt:gcontent");
            
            // 如果是紧急上线应用, 保存应用信息
            if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
            {

                GoodsChanegHisBO.getInstance()
                                .addDelHisToList(category,
                                                 node,
                                                 content.getSubType());
            }
            
		}
		category.saveNode();

		LOG.info("进行上架操作开始。");
		for(int i=0;i<list.size();i++)
		{
            ReferenceNode refNode = ( ReferenceNode ) list.get(i);
            //已经被锁定的商品。不再重新上架
            if(lockedRefMap.containsKey(refNode.getRefNodeID())){
            	LOG.info("全量下架：忽略上架已锁定商品"+refNode.getRefNodeID());
            	continue;
            }
            Node node = ( Node ) Repository.getInstance()
                                           .getNode(refNode.getRefNodeID());
            if (node == null)
            {
                LOG.error("无法找到该商品，id=" + refNode.getRefNodeID());
                continue;
            }
            if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// 判断是否是内容。
            {
                LOG.error("id出错，该id不是产品类型的id,忽略该数据。id=" + node.getId());
                continue;
            }
            int newSortId;
            if (oldSortIdCollection.containsKey(refNode.getRefNodeID()))
            {
                int oldSortId = (( Integer ) oldSortIdCollection.get(refNode.getRefNodeID())).intValue();
                newSortId = refNode.getSortID() - oldSortId;
            }
            else
            {
                newSortId = RepositoryConstants.VARIATION_NEW;
            }
            
            // remove by aiyan 2013-04-18
//            CategoryTools.addGood(category,
//                                  node.getId(),
//                                  refNode.getSortID(),
//                                  newSortId);

            //add below
            if(transactionID!=null){
                CategoryTools.addGood(category,
                        node.getId(),
                        refNode.getSortID(),
                        newSortId,transactionID,menuStatus);  
            }else{
              CategoryTools.addGood(category,
				          node.getId(),
				          refNode.getSortID(),
				          newSortId,menuStatus);           	
            }
          
            //GContent content = (GContent)Repository.getInstance().getNode(node.getId(),node.getType());//add by aiyan 
            // 如果是紧急上线应用, 保存应用信息
            if (RepositoryConstants.SYN_HIS_YES.equals(isSyn))
            {
                GContent content = (GContent)Repository.getInstance().getNode(node.getId(),node.getType());//remove by aiyan
                GoodsChanegHisBO.getInstance().addAddHisToList(category,
                                                               node.getId(),
                                                               content.getSubType());
            }

            count++;
            
        }
        
		if(transactionID!=null){
			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20，在EXECLE导入的时候先写-5，而后把状态改为-1
		}
		lockedRefMap.clear();
		
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);  
		WorkerThread worker = new WorkerThread(transactionID,category.getCategoryID());
        scheduledThreadPool.schedule(worker, 10, TimeUnit.MINUTES);
		return count;
	}
	/**
	 * 对商品去重。对于重复的，保留最后的那个。
	 * @param list
	 */
	private void deduplication(List list){
		Set<String> set = new HashSet<String>();
		if(list!=null&&list.size()>0){
			for(int i=list.size()-1;i>=0;i--)
			{
	            ReferenceNode refNode = ( ReferenceNode ) list.get(i);
	            String rNodeId = refNode.getRefNodeID();
	            if(!set.add(rNodeId)){
	            	list.remove(i);
	            }
	            
			}
		}
		
	}
	
	public class WorkerThread implements Runnable{
		 
		private String transactionID;
		private String categoryId;
		 
		    public WorkerThread(String transactionID,String categoryId){
		        this.transactionID=transactionID;
		        this.categoryId=categoryId;
		    }
		 
		    @Override
		    public void run() {
		        System.out.println(Thread.currentThread().getName()+" Start. Time = "+new Date());
		        processCommand();
		        System.out.println(Thread.currentThread().getName()+" End. Time = "+new Date());
		    }
		 
		    private void processCommand() {
		        	// 全量货架上下架更新接口
		    		PPMSDAO.addMessagesStatic(MSGType.BatchRefModifyReq, transactionID,
		    				categoryId);
		    }
		 
		    @Override
		    public String toString(){
		        return this.transactionID;
		    }
		}
	
	
	public int importCateData2(String[] contentids, Category category, String isSyn,String menuStatus) throws BOException{
		// TODO Auto-generated method stub
		// 解析EXECL文件，获取终端软件版本列表
		//List list = this.paraseDataFile2(dataFile);
		List list = new ExcelHandle().paraseDataMap(contentids, new ExcelRowConvert(){
			@Override
			public Object convertData(Map m) {
				// TODO Auto-generated method stub
				ReferenceNode refNode = new ReferenceNode();
				refNode.setSortID(-1*Integer.parseInt(m.get(0)+""));
				String value="";
			    try
                {
                    // 查询contentID对应的主键id，返回无结果则默认插入contentid
			    	value=m.get(1)+"";
                    String id = GContentDAO.getInstance()
                                           .getContentByID(value);
                    if (!"".equals(id))
                    {
                        value = id;
                    }
                }
                catch (Exception e)
                {
                    LOG.error("查询主键id失败：contentid=" + value, e);
                }
				refNode.setRefNodeID(value);
			
				return refNode;
			}});
		
		
		
        //这里加上商品库优化，如果是商品库优化为根货架的货架，则要放消息进去。
        //这里加点消息动作,就是这个新建货架的动作要通知到数据中心，先把数据加到T_a_messages表，然后专门线程发送消息。
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
		String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//不在商品库优化根货架下的运营货架
		String transactionID = null;
		 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
			//||operateCategoryId.indexOf(cateId)!=-1){//凡是商品库优化根货架或者需要处理的运营货架下都有有消息 modify by aiyan 2013-05-10		 		
			||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateCategoryId)){//modify by aiyan 2013-05-18
				 transactionID = ContextUtil.getTransactionID();
		 }
		//above add by aiyan 2013-04-18 && 2013-05-10	
		 
		LOG.info("进行上架操作开始。");
		int count=0;
		for(int i=0;i<list.size();i++)
		{
            ReferenceNode refNode = ( ReferenceNode ) list.get(i);
            Node node = ( Node ) Repository.getInstance()
                                           .getNode(refNode.getRefNodeID());
            if (node == null)
            {
                LOG.error("无法找到该商品，id=" + refNode.getRefNodeID());
                continue;
            }
            if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// 判断是否是内容。
            {
                LOG.error("id出错，该id不是产品类型的id,忽略该数据。id=" + node.getId());
                continue;
            }
            String id= ReferenceTools.getInstance().isExistRef(category.getCategoryID(), refNode.getRefNodeID());//查看商品表中是否有这个ID
            if(id==null){//新增
            	
            	//remove by aiyan 2013-04-18
//                CategoryTools.addGood(category,
//                        node.getId(),
//                        refNode.getSortID(),
//                        0);
                
            	//modified  为了商品库优化能够向数据中心发送消息。
                if(transactionID!=null){
                    CategoryTools.addGood(category,
                            node.getId(),
                            refNode.getSortID(),
                            0,transactionID,menuStatus);
                }else{
                    CategoryTools.addGood(category,
                            node.getId(),
                            refNode.getSortID(),
                            0,menuStatus);
                }
            }else{//修改序号
            	ReferenceTools.getInstance().updateSortid(id, refNode.getSortID(), 0,menuStatus);
                //商品库优化加消息发送的代码。
            	
            	
                try{
//                	String goodsID = GoodsBO.getGoodsIDbyRefID(id);
//                	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID,goodsID+":0");
                	
                    //大胖说要把上下架消息变大,所以注销上面的，改下以下。 add by aiyan 2013-04-27
//                  Goodsid	必须	String	reference表中的goodsid
//                  Categoryid	可选	String	货架categoryid，新建时必须有
//                  Id	可选	String	货架categoryid对应的Id，新建时必须有
//                  Refnodeid	可选	String	应用ID，新建时必须有
//                  Sortid	可选	String	排序字段，新建时必须有
//                  Loaddate	可选	String	更新时间，新建时必须有
//                  Action	必须	String	0：新建
//                  9：删除
//                  Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
					if (!(menuStatus != null && !"".equals(menuStatus) && "1"
							.equals(menuStatus)))
						if (transactionID != null) {
							Map myRefMap = GoodsBO.getRefNodebyRefID(id);
							if (myRefMap != null) {
								SSMSDAO.getInstance().addMessages(
										MSGType.RefModifyReq,
										transactionID,
										myRefMap.get("Goodsid") + ":"
												+ myRefMap.get("Categoryid")
												+ ":" + myRefMap.get("Id")
												+ ":"
												+ myRefMap.get("Refnodeid")
												+ ":" + myRefMap.get("Sortid")
												+ ":"
												+ myRefMap.get("Loaddate")
												+ ":0", -5);

							} else {
								LOG.error("这个t_r_reference表的ID居然没有值,ID:" + id);
							}
						}

                }catch(Exception e){
                	LOG.error("手工上架是发送消息出错！",e);
                }
            }
            


            // 如果是紧急上线应用, 保存应用信息
            if (RepositoryConstants.SYN_HIS_YES.equals(isSyn))
            {
                GContent content = (GContent)Repository.getInstance().getNode(node.getId(),node.getType());
                GoodsChanegHisBO.getInstance().addAddHisToList(category,
                                                               node.getId(),
                                                               content.getSubType());
            }

            count++;
        }
		
		if(transactionID!=null){
			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20，在EXECLE导入的时候先写-5，而后把状态改为-1
		}
        
		return count;
	}
}
