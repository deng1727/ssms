package com.aspire.ponaadmin.web.repository ;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.struts.upload.FormFile;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.util.StringUtils;
import com.aspire.dotcard.gcontent.GContent;
import com.aspire.dotcard.gcontent.GContentDAO;
import com.aspire.dotcard.syncAndroid.common.Constant;
import com.aspire.dotcard.syncAndroid.common.ContextUtil;
import com.aspire.dotcard.syncAndroid.dc.MessageSendDAO;
import com.aspire.dotcard.syncAndroid.dc.jms.MSGType;
import com.aspire.dotcard.syncAndroid.ppms.PPMSDAO;
import com.aspire.dotcard.syncAndroid.ssms.SSMSDAO;
import com.aspire.ponaadmin.web.datafield.dao.KeyResourceDAO;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.datasync.DataSyncTools;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.repository.web.ExcelHandle;
import com.aspire.ponaadmin.web.repository.web.ExcelRowConvert;
import com.aspire.ponaadmin.web.system.SystemConfig;
import com.aspire.ponaadmin.web.util.IOUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.enterprisedt.net.ftp.FTPClient;

/**
 * <p>分类货架的BO接口类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr 
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategoryBO
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryBO.class);

    /**
     * singleton模式的实例
     */
    private static CategoryBO instance = new CategoryBO();

    /**
     * 构造方法，由singleton模式调用
     */
    private CategoryBO ()
    {
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static CategoryBO getInstance()
    {
        return instance;
    }

    /**
     * 根据id获取一个货架分类
     * @param categoryID String，分类id
     * @return Category，对应的分类
     * @throws BOException
     */
    public Category getCategory(String categoryID) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("getCategory("+categoryID+")");
        }
        Category category = (Category) Repository.getInstance().getNode(
            categoryID,
            RepositoryConstants.TYPE_CATEGORY) ;
        return category ;
    }
    /**
     * 根据名称查找子货架下的分类
     * @param pCateId  父货架的id
     * @param name 待查找的子货架的名称
     * @return 子货架为name的货架，如果没有，返回null
     * @throws BOException
     */
    public Category getCategoryByName(String pCateId,String name)throws BOException
    {
    	    Searchor search = new Searchor();
		    // 构造大类名称相同的搜索条件
		    search.getParams().add(new SearchParam("name",
		                                           RepositoryConstants.OP_EQUAL,name));
		    List list = new Category(pCateId).searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
		    //子类对象
		    if(list.size()==0)
		    {
		    	return null;
		    }else
		    {
		    	return (Category)list.get(0);
		    }
    }

    /**
     * 在一个父分类下添加分类的方法
     * @param pCategoryID String，父分类ID
     * @param category Category，要添加的分类
     * @return String，新增加的分类的id处理结果，具体意义请见RepositoryBOCode类中的定义
     * @throws BOException
     */
    public String addCategory(String pCategoryID, Category category) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("addCategory("+pCategoryID+","+category+")");
        }
        String name = category.getName();
        //检查名字是否已经存在
        if (this.checkNameIsExisted(pCategoryID, null, name))
        {
            throw new BOException("category name existed!", RepositoryBOCode.CATEGORY_NAME_EXISTED);
        }
        category.setSortID(0) ;
        category.setCtype(0) ;
        //给新增分类分配货架编码
        int categoryID = -1;
        String parentCategoryID;
        try
        {
            categoryID = CategoryDAO.getInstance().getSeqCategoryID();
            // 找到新增分类的父分类的货架编码
            parentCategoryID = CategoryDAO.getInstance()
                                          .getCategoryIDByID(pCategoryID);
        }
        catch (DAOException e)
        {
            throw new BOException("CategoryBO.getCategoryID failed!");
        }
        
        if (LOG.isDebugEnabled())
        {
            LOG.debug("the categoryID is " + categoryID + " && the parentCategoryID is " + parentCategoryID);
        }
        
        category.setCategoryID(String.valueOf(categoryID));
        category.setDelFlag(0);
        category.setChangeDate(new Date());
        category.setState(1);
        category.setParentCategoryID(parentCategoryID);
        Node node = Repository.getInstance().getNode(pCategoryID) ;
        String newCategoryID = node.addNode(category) ;
        node.saveNode() ;
        return newCategoryID;
    }

    /**
     * 在一个父分类下修改分类的方法
     * @param pCategoryID String，父分类ID
     * @param category Category，要修改的分类
     * @throws BOException
     */
    public void modCategory(String pCategoryID, Category category) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("modCategory("+pCategoryID+","+category+")");
        }
        String name = category.getName();
        //检查名字是否已经存在
        if (this.checkNameIsExisted(pCategoryID, category.getId(), name))
        {
            throw new BOException("category name existed!", RepositoryBOCode.CATEGORY_NAME_EXISTED);
        }
        Category pCategory = this.getCategory(pCategoryID);
        if(pCategory != null && category != null){
        boolean checkpar =	this.checkRelationRevert(pCategory,category);
        if(!checkpar){
        	throw new BOException("无法添加父货架不包含的关联门店", RepositoryBOCode.CATEGORY_RELATION_PARERR);       	
        }
        boolean checksub = this.checkRelationOutOfSub(category);
        if(!checksub){
        	throw new BOException("无法删除子货架包含的关联门店", RepositoryBOCode.CATEGORY_RELATION_SUBERR);      
        	
        }	
        }     
        category.save();
    }

    /**
     * 在一个父分类下删除一个分类
     * @param pCategoryID String，父分类id
     * @param categoryID String，要删除的分类id
     * @throws BOException
     */
    public void delCategory(String pCategoryID, Category category) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("delCategory("+pCategoryID+","+category.getId()+")");
        }
       
        //检查分类下是否有资源存在
        Searchor searchor = new Searchor();
        searchor.setIsRecursive(false);
        int count = category.countNodes(RepositoryConstants.TYPE_REFERENCE, searchor, null);
        if(LOG.isDebugEnabled())
        {
            LOG.debug("count====" + count);
        }
        //检查货架下是否有子货架
        Taxis taxis = new Taxis();
        taxis.getParams().add(new TaxisParam("path", RepositoryConstants.ORDER_TYPE_DESC));
        Searchor childSearchor = new Searchor();
        childSearchor.setIsRecursive(true);
        childSearchor.getParams().add(new SearchParam("delFlag",RepositoryConstants.OP_NOT_EQUAL,String.valueOf(1)));
        List childCategoryList = category.searchNodes(RepositoryConstants.TYPE_CATEGORY, childSearchor, taxis) ;
        if(LOG.isDebugEnabled())
        {
            LOG.debug("found " + childCategoryList.size() + " child category!") ;
        }
        if (count > 0 || childCategoryList.size() > 1)
        {
            // 货架下有资源或子货架存在，不能删除！
            throw new BOException("category content existed!",
                                  RepositoryBOCode.CATEGORY_CONTENT_EXISTED);
        }
        else
        {
            category.setDelFlag(1);
            category.setChangeDate(new Date());
           // category.setState(9); //del by tungke 20100322
            category.save();
        }
    }

    /**
     * 检查父分类下的同名分类是否已经存在
     * @param pCategoryID String，父分类ID
     * @param categoryID String，自身分类ID
     * @param name String，分类名称
     * @return boolean，判断结果
     * @throws BOException
     */
    private boolean checkNameIsExisted (String pCategoryID, String categoryID,
                                        String name)
        throws BOException
    {
        Node node = new Node(pCategoryID);
        Searchor searchor = new Searchor();
        SearchParam param1 = new SearchParam();
        param1.setProperty("name");
        param1.setOperator(RepositoryConstants.OP_EQUAL);
        param1.setValue(name);
        searchor.getParams().add(param1);
        if (categoryID != null && !categoryID.equals(""))
        {
            SearchParam param2 = new SearchParam();
            param2.setProperty("id");
            param2.setOperator(RepositoryConstants.OP_NOT_EQUAL);
            param2.setValue(categoryID);
            searchor.getParams().add(param2);
        }
        searchor.getParams().add(new SearchParam("delFlag",RepositoryConstants.OP_NOT_EQUAL,String.valueOf(1)));
        return node.countNodes(RepositoryConstants.TYPE_CATEGORY, searchor, null) >
                0 ;
    }
    
    
    
    /**
     * 检查添加修改货架的关联门店，是否超过父货架的关联门店，业务逻辑是不允许这样的情况。
     * 限制传入参数合法性
     * 货架二的关联门店是货架一的子集，返回true
     * @param pCategory   Category，货架1
     * @param category     Category 本货架2
     * @return
     * add by tungke 20090911
     * @throws BOException 
     * 
     */
    private boolean checkRelationRevert(Category pCategory, Category category) throws BOException{
    	//  if(null != pCategoryID && null != category ){
    		//  Category pCategory = this.getCategory(pCategoryID);
    		  String pRelation = pCategory.getRelation();
    		  String sRelation = category.getRelation();
    		  String sr[] = sRelation.split(",");
    		 for(int i = 0 ; i <sr.length;i ++){
    			 if(pRelation.indexOf(sr[i]) == -1 ){
    				return false;
    			 }
    		 }
    		// return indexofStatus; 
    	//  }
    	
    	return true;
    }
    
    /**
     *检查修改父货架的关联门店，父货架的所有子货架关联门店是否超过父货架(业务逻辑是不允许这样的情况)
     *超过返回false,否则返回true，
     * @param category
     * @return
     * @throws BOException
     * @throws DAOException 
     * add by tungke 20090911
     */
  private boolean checkRelationOutOfSub(Category category) throws BOException{	  
	  List subCategorys = null;
	try
	{
		subCategorys = CategoryDAO.getInstance().getAllSubCategorys(category.getCategoryID());
	} catch (DAOException e)
	{
		e.printStackTrace();
		if(LOG.isDebugEnabled())
        {
            LOG.debug("获取子货架失败，subCategorys is null ") ;
        }
		 throw new BOException("",e);

	}	 
	if(subCategorys != null){
			 for(int i = 0 ; i < subCategorys.size(); i ++){
				  Category subCategory = (Category) subCategorys.get(i);
				  boolean checkStatus = this.checkRelationRevert(category,subCategory);
				  if(checkStatus == false){
					  return false;
				  }
			  }		  
			  return true;
	}else{	
		   if(LOG.isDebugEnabled())
	        {
	            LOG.debug("获取子货架失败，subCategorys is null ") ;
	        }
		return false;
	}
	 
  }
    
    
    
    /**
     * 通过内容id获取对应的产品信息
     * @param contentId
     * @return
     * @throws BOException
     */
    public List getGoodsByContentId(String contentId) throws BOException
    {
    	List resultList;
        try
        {
            resultList = CategoryDAO.getInstance().getGoodsByContentId(contentId);
        }
        catch (DAOException e)
        {
            throw new BOException("",e);
        }
    	if(resultList!=null )
    	{
    		 ContentGoodsInfo vo = null;
    		 Category category  = null;
    		for(int i=0; i< resultList.size(); i++)
    		{
                vo = ( ContentGoodsInfo ) resultList.get(i);
                category = CategoryBO.getInstance()
                                     .getCategory(vo.getCategoryId());
                vo.setCategoryPath(category.getNamePath());
            }
    	}
    	return resultList;
    }
    
    /**
	 * 货架数据导入
	 * 
	 * @param in
	 * @param result
     * @param isSyn 是否是紧急上线应用
	 * @throws BOException
	 */
	public int importCateData(FormFile dataFile,Category category,String isSyn,String menuStatus) throws BOException
	{
		int count=0;
		Map oldSortIdCollection= new HashMap();
		// 解析EXECL文件，获取终端软件版本列表
		//List list = this.paraseDataFile(dataFile);
		List list = new ExcelHandle().paraseDataFile(dataFile, new ExcelRowConvert(){
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
            
////          Catogoryid	必须	String	货架ID
////          Contentid	必须	String	应用ID
////          Action	必须	String	0：新建
////          9：删除
////          Transactionid	必须	String	事务序列ID，用于处理同一事务请求时使用。
//          SSMSDAO.getInstance().addRefMessages(category.getId(), content.getContentID(), "0");  //add by aiyan 2013-03-12
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
	/**
	 * 对商品去重。对于重复的，保留最后的那个。
	 * @param list
	 */
	private void deduplication(List list){
		Set<String> set = new HashSet<String>();
		Set<String> set1 = new HashSet<String>();
		if(list!=null&&list.size()>0){
			for(int i=list.size()-1;i>=0;i--)
			{
	            ReferenceNode refNode = ( ReferenceNode ) list.get(i);
	            String rNodeId = refNode.getRefNodeID();
	            if(!set.add(rNodeId)){
	            	list.remove(i);
	            }
	            
	            
	            try {
					GContent content = ( GContent ) Repository.getInstance()
					        .getNode(rNodeId,
					                 "nt:gcontent");
					if (null != null ) {
						String appid = content.getAppId();
						if (!StringUtils.isEmpty(appid)) {
							if(!set1.add(appid)){
				            	list.remove(i);
				            }
						}
					}
					
				} catch (BOException e) {
					
				}
			}
		}
		
	}
	/**
	 * 解析EXECL文件，获取要添加的商品信息
	 * 
	 * @param in
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
//	private List paraseDataFile(FormFile dataFile) throws BOException
//
//	{
//
//		LOG.info("paraseDataFile is start!");
//		List list = new ArrayList();
//		String fileName = dataFile.getFileName();
//		
//		org.apache.poi.ss.usermodel.Workbook book = null;
//		try
//		{
//			 InputStream stream=dataFile.getInputStream();
//			 if(fileName.endsWith("xls")){
//				 book=new HSSFWorkbook(stream);
//			 }else if(fileName.endsWith("xlsx")){
//				 book=new XSSFWorkbook(stream);
//			 }
//			
//			 
//			int sheetNum = book.getNumberOfSheets();
//			if (LOG.isDebugEnabled())
//			{
//				LOG.debug("paraseSoftVersion.sheetNum==" + sheetNum);
//			}
//
//			for (int i = 0; i < sheetNum; i++)
//			{
//				org.apache.poi.ss.usermodel.Sheet sheet=book.getSheetAt(i);
//		        int rows=sheet.getLastRowNum();
//		        org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);
//		        if(row==null){
//		        	LOG.error("sheet("+i+")是空的！");
//		        	continue;
//		        }
//		        int cells=row.getPhysicalNumberOfCells();
//				for (int j = 0; j <= rows; j++)
//				{
//					row = sheet.getRow(j);
//					ReferenceNode refNode = new ReferenceNode();
//					for (int k = 0; k < cells; k++)
//					{
//						org.apache.poi.ss.usermodel.Cell cell = row.getCell(k);
//						switch (k + 1)
//						{
//							case 1:
//								try
//								{
//									int value = (int)cell.getNumericCellValue();
//									refNode.setSortID(-1*value);
//									if(refNode.getSortID()>100000000)
//									{
//										LOG.error("第"+(j+1)+"行,数据，排序号不超过8位数。");
//										continue;
//									}
//								} catch (NumberFormatException e)
//								{
//									LOG.error("第"+(j+1)+"行,数据，解析出错。");
//									continue;
//								}
//								break;
//							case 2:	
//								String value="";
//							    try
//                                {
//                                    // 查询contentID对应的主键id，返回无结果则默认插入contentid
//							    	value=cell.getStringCellValue();
//                                    String id = GContentDAO.getInstance()
//                                                           .getContentByID(value);
//                                    if (!"".equals(id))
//                                    {
//                                        value = id;
//                                    }
//                                }
//                                catch (Exception e)
//                                {
//                                    LOG.error("查询主键id失败：contentid=" + value, e);
//                                }
//								refNode.setRefNodeID(value);
//								break;
//						}
//					}
//
//					if (refNode.getRefNodeID() != null)
//					{
//
//						list.add(refNode);
//					}
//				}
//				}
//			
//		} catch (Exception e)
//		{
//			LOG.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
//			throw new BOException("解析导入文件出现异常", e);
//		}
//		return list;
//	}
	
	//增量解析xls add by aiyan 2012-12-19
//	private List paraseDataFile2(FormFile dataFile) throws BOException {
//
//		LOG.info("paraseDataFile2 is start!");
//		List list = new ArrayList();
//		String fileName = dataFile.getFileName();
//		
//		org.apache.poi.ss.usermodel.Workbook book = null;
//		try
//		{
//			 InputStream stream=dataFile.getInputStream();
//			 if(fileName.endsWith("xls")){
//				 book=new HSSFWorkbook(stream);
//			 }else if(fileName.endsWith("xlsx")){
//				 book=new XSSFWorkbook(stream);
//			 }
//			
//			 
//			int sheetNum = book.getNumberOfSheets();
//			if (LOG.isDebugEnabled())
//			{
//				LOG.debug("paraseSoftVersion.sheetNum==" + sheetNum);
//			}
//
//			for (int i = 0; i < sheetNum; i++)
//			{
//				org.apache.poi.ss.usermodel.Sheet sheet=book.getSheetAt(i);
//		        int rows=sheet.getLastRowNum();
//		        org.apache.poi.ss.usermodel.Row row = sheet.getRow(0);
//		        if(row==null){
//		        	LOG.error("sheet("+i+")是空的！");
//		        	continue;
//		        }
//		        int cells=sheet.getRow(0).getPhysicalNumberOfCells();
//				for (int j = 0; j <= rows; j++)
//				{
//					row = sheet.getRow(j);
//					ReferenceNode refNode = new ReferenceNode();
//					for (int k = 0; k < cells; k++)
//					{
//						org.apache.poi.ss.usermodel.Cell cell = row.getCell(k);
//						switch (k + 1)
//						{
//							case 1:
//								try
//								{
//									int value = (int)cell.getNumericCellValue();
//									refNode.setSortID(value);
//									if(refNode.getSortID()>100000000)
//									{
//										LOG.error("第"+(j+1)+"行,数据，排序号不超过8位数。");
//										continue;
//									}
//								} catch (NumberFormatException e)
//								{
//									LOG.error("第"+(j+1)+"行,数据，解析出错。");
//									continue;
//								}
//								break;
//							case 2:	
//
//								String value="";
//							    try
//                                {
//                                    // 查询contentID对应的主键id，返回无结果则默认插入contentid
//							    	value=cell.getStringCellValue();
//                                    String id = GContentDAO.getInstance()
//                                                           .getContentByID(value);
//                                    if (!"".equals(id))
//                                    {
//                                        value = id;
//                                    }
//                                }
//                                catch (Exception e)
//                                {
//                                    LOG.error("查询主键id失败：contentid=" + value, e);
//                                }
//								refNode.setRefNodeID(value);
//								break;
//						}
//					}
//
//					if (refNode.getRefNodeID() != null)
//					{
//
//						list.add(refNode);
//					}
//				}
//				}
//			
//		} catch (Exception e)
//		{
//			LOG.error("解析导入文件出现异常,fineName:" + dataFile.getFileName(), e);
//			throw new BOException("解析导入文件出现异常", e);
//		}
//		return list;
//	}
	
	/**
	 * 上传货架的预览图的图片
	 * @param request
	 * @throws BOException
	 */
	public String uploadCatePicURL(FormFile uploadFile,String cateId)throws BOException
	{


    	   //
    	String tempDir = ServerInfo.getAppRootPath() + File.separator+"temp"+File.separator;
    	String ftpDir = SystemConfig.FTPDIR_CATEGORYPICURL;
		IOUtil.checkAndCreateDir(tempDir);
		
		// 获得文件名，这个文件名包括路径：
		String fileName = uploadFile.getFileName();
		String suffex = fileName.substring(fileName.lastIndexOf('.'));
		String oldFileName = tempDir + "temp" + suffex;// 拼装临时文件名为 temp.png

		try
		{
			IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
		} catch (Exception e1)
		{
			throw new BOException("写入临时文件失败。tempFilePath="+oldFileName,RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		} 

		// 需要生成四种不同规格的图片
		String logo1Path = tempDir + "logo1.png";
		String logo2Path = tempDir + "logo2.png";
		String logo3Path = tempDir + "logo3.png";
		String logo4Path = tempDir + "logo4.png";
		String logo5Path = tempDir + "logo5.png";

		try
		{
//			DataSyncTools.scaleIMG(oldFileName, logo1Path, 60, 45, "png");
//			DataSyncTools.scaleIMG(oldFileName, logo2Path, 80, 60, "png");
//			DataSyncTools.scaleIMG(oldFileName, logo3Path, 120, 90, "png");
//			DataSyncTools.scaleIMG(oldFileName, logo4Path, 120, 120, "png");
			DataSyncTools.scaleIMG(oldFileName, logo1Path, 30, 30, "png");
			DataSyncTools.scaleIMG(oldFileName, logo2Path, 34, 34, "png");
			DataSyncTools.scaleIMG(oldFileName, logo3Path, 50, 50, "png");
			DataSyncTools.scaleIMG(oldFileName, logo4Path, 65, 65, "png");
			DataSyncTools.scaleIMG(oldFileName, logo5Path, 70, 70, "png");
		} catch (Exception e)
		{
			throw new BOException("图片转化出错，FileName=" + oldFileName, e,RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		}

		FTPClient ftp = null;
		try
		{
			ftp = PublicUtil.getFTPClient(SystemConfig.SOURCESERVERIP,
					SystemConfig.SOURCESERVERPORT, SystemConfig.SOURCESERVERUSER,
					SystemConfig.SOURCESERVERPASSWORD, ftpDir);
			PublicUtil.checkAndCreateDir(ftp, cateId);
			ftp.chdir(cateId);
			ftp.put(logo1Path, "logo1.png");
			ftp.put(logo2Path, "logo2.png");
			ftp.put(logo3Path, "logo3.png");
			ftp.put(logo4Path, "logo4.png");
			ftp.put(logo5Path, "logo5.png");
		} catch (Exception e)
		{
			throw new BOException("上传到资源服务器出现异常。", e,RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		} finally
		{
			if (ftp != null)
			{
				try
				{
					ftp.quit();
				} catch (Exception e)
				{
				}
			}

		}
		return SystemConfig.URL_PIC_VISIT+SystemConfig.FTPDIR_CATEGORYPICURL+cateId+"/";
	}
	
	/**
     * 
     *@desc 保存
     *@author dongke
     *Aug 9, 2011
     * @param keyBaseList
     * @throws BOException
     */
    public void saveKeyResource(List keyBaseList) throws BOException
	{
		for (int i = 0; i < keyBaseList.size(); i++)
		{
			ResourceVO vo = (ResourceVO) keyBaseList.get(i);
			try
			{
				if (vo != null && vo.getValue() != null && !vo.getValue().equals(""))
				{
					KeyResourceDAO.getInstance().updateResourceValue(vo.getKeyid(),
							vo.getValue(), vo.getTid());
				}
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BOException("保存扩展值失败", e);
			}
		}
	}
 /**************************/
    
    
    /**
     * 用于返回图书货架扩展字段列表
     * @return
     * @throws BOException
     */
    public List queryCategoryKeyBaseList(String tid) throws BOException
    {
        if (LOG.isDebugEnabled())
        {
        	LOG.debug("CategoryBO.queryCategoryKeyBaseList( ) is start...");
        }

        try
        {
            // 调用CategoryDAO进行查询
            return CategoryDAO.getInstance()
                                      .getCategoryKeyResourceByCid(tid);
        }
        catch (DAOException e)
        {
        	LOG.error(e);
            throw new BOException("返回MM货架扩展字段列表时发生数据库异常！",e);
        }
    }

    //add by aiyan 2012-12-19 增量导入商品。
	public int importCateData2(FormFile dataFile, Category category, String isSyn,String menuStatus) throws BOException{
		// TODO Auto-generated method stub
		// 解析EXECL文件，获取终端软件版本列表
		//List list = this.paraseDataFile2(dataFile);
		List list = new ExcelHandle().paraseDataFile(dataFile, new ExcelRowConvert(){
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
