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
 * <p>������ܵ�BO�ӿ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr 
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class CategoryBO
{

    /**
     * ��־����
     */
    private static final JLogger LOG = LoggerFactory.getLogger(CategoryBO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CategoryBO instance = new CategoryBO();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryBO ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static CategoryBO getInstance()
    {
        return instance;
    }

    /**
     * ����id��ȡһ�����ܷ���
     * @param categoryID String������id
     * @return Category����Ӧ�ķ���
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
     * �������Ʋ����ӻ����µķ���
     * @param pCateId  �����ܵ�id
     * @param name �����ҵ��ӻ��ܵ�����
     * @return �ӻ���Ϊname�Ļ��ܣ����û�У�����null
     * @throws BOException
     */
    public Category getCategoryByName(String pCateId,String name)throws BOException
    {
    	    Searchor search = new Searchor();
		    // �������������ͬ����������
		    search.getParams().add(new SearchParam("name",
		                                           RepositoryConstants.OP_EQUAL,name));
		    List list = new Category(pCateId).searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
		    //�������
		    if(list.size()==0)
		    {
		    	return null;
		    }else
		    {
		    	return (Category)list.get(0);
		    }
    }

    /**
     * ��һ������������ӷ���ķ���
     * @param pCategoryID String��������ID
     * @param category Category��Ҫ��ӵķ���
     * @return String�������ӵķ����id�������������������RepositoryBOCode���еĶ���
     * @throws BOException
     */
    public String addCategory(String pCategoryID, Category category) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("addCategory("+pCategoryID+","+category+")");
        }
        String name = category.getName();
        //��������Ƿ��Ѿ�����
        if (this.checkNameIsExisted(pCategoryID, null, name))
        {
            throw new BOException("category name existed!", RepositoryBOCode.CATEGORY_NAME_EXISTED);
        }
        category.setSortID(0) ;
        category.setCtype(0) ;
        //���������������ܱ���
        int categoryID = -1;
        String parentCategoryID;
        try
        {
            categoryID = CategoryDAO.getInstance().getSeqCategoryID();
            // �ҵ���������ĸ�����Ļ��ܱ���
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
     * ��һ�����������޸ķ���ķ���
     * @param pCategoryID String��������ID
     * @param category Category��Ҫ�޸ĵķ���
     * @throws BOException
     */
    public void modCategory(String pCategoryID, Category category) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("modCategory("+pCategoryID+","+category+")");
        }
        String name = category.getName();
        //��������Ƿ��Ѿ�����
        if (this.checkNameIsExisted(pCategoryID, category.getId(), name))
        {
            throw new BOException("category name existed!", RepositoryBOCode.CATEGORY_NAME_EXISTED);
        }
        Category pCategory = this.getCategory(pCategoryID);
        if(pCategory != null && category != null){
        boolean checkpar =	this.checkRelationRevert(pCategory,category);
        if(!checkpar){
        	throw new BOException("�޷���Ӹ����ܲ������Ĺ����ŵ�", RepositoryBOCode.CATEGORY_RELATION_PARERR);       	
        }
        boolean checksub = this.checkRelationOutOfSub(category);
        if(!checksub){
        	throw new BOException("�޷�ɾ���ӻ��ܰ����Ĺ����ŵ�", RepositoryBOCode.CATEGORY_RELATION_SUBERR);      
        	
        }	
        }     
        category.save();
    }

    /**
     * ��һ����������ɾ��һ������
     * @param pCategoryID String��������id
     * @param categoryID String��Ҫɾ���ķ���id
     * @throws BOException
     */
    public void delCategory(String pCategoryID, Category category) throws BOException
    {
        if(LOG.isDebugEnabled())
        {
            LOG.debug("delCategory("+pCategoryID+","+category.getId()+")");
        }
       
        //���������Ƿ�����Դ����
        Searchor searchor = new Searchor();
        searchor.setIsRecursive(false);
        int count = category.countNodes(RepositoryConstants.TYPE_REFERENCE, searchor, null);
        if(LOG.isDebugEnabled())
        {
            LOG.debug("count====" + count);
        }
        //���������Ƿ����ӻ���
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
            // ����������Դ���ӻ��ܴ��ڣ�����ɾ����
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
     * ��鸸�����µ�ͬ�������Ƿ��Ѿ�����
     * @param pCategoryID String��������ID
     * @param categoryID String���������ID
     * @param name String����������
     * @return boolean���жϽ��
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
     * �������޸Ļ��ܵĹ����ŵ꣬�Ƿ񳬹������ܵĹ����ŵ꣬ҵ���߼��ǲ����������������
     * ���ƴ�������Ϸ���
     * ���ܶ��Ĺ����ŵ��ǻ���һ���Ӽ�������true
     * @param pCategory   Category������1
     * @param category     Category ������2
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
     *����޸ĸ����ܵĹ����ŵ꣬�����ܵ������ӻ��ܹ����ŵ��Ƿ񳬹�������(ҵ���߼��ǲ��������������)
     *��������false,���򷵻�true��
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
            LOG.debug("��ȡ�ӻ���ʧ�ܣ�subCategorys is null ") ;
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
	            LOG.debug("��ȡ�ӻ���ʧ�ܣ�subCategorys is null ") ;
	        }
		return false;
	}
	 
  }
    
    
    
    /**
     * ͨ������id��ȡ��Ӧ�Ĳ�Ʒ��Ϣ
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
	 * �������ݵ���
	 * 
	 * @param in
	 * @param result
     * @param isSyn �Ƿ��ǽ�������Ӧ��
	 * @throws BOException
	 */
	public int importCateData(FormFile dataFile,Category category,String isSyn,String menuStatus) throws BOException
	{
		int count=0;
		Map oldSortIdCollection= new HashMap();
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
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
                    // ��ѯcontentID��Ӧ������id�������޽����Ĭ�ϲ���contentid
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
                    LOG.error("��ѯ����idʧ�ܣ�contentid=" + value, e);
                }
				refNode.setRefNodeID(value);
			
				return refNode;
			}});
        
		
        //���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
        //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
		String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
		String transactionID = null;
		 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
			//||operateCategoryId.indexOf(cateId)!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ modify by aiyan 2013-05-10		
			||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateCategoryId)){//modify by aiyan 2013-05-18	 
			 transactionID = ContextUtil.getTransactionID();
		 }
		//above add by aiyan 2013-04-18 && 2013-05-10
		 
		 
		LOG.info("�¼ܸ÷����µ�������Ʒ��cateId="+category.getId());
		//��Ʒ�¼ܡ�
		//Category category=(Category)Repository.getInstance().getNode(cateId, RepositoryConstants.TYPE_CATEGORY);
		List subNodes = category.searchNodes(RepositoryConstants.TYPE_REFERENCE, null, null);
		//�ѱ���������Ʒ
		Map<String,String> lockedRefMap = new HashMap<String, String>();
		//��excel�������ȥ��һ��
		deduplication(list);

		for (int i = 0; i < subNodes.size(); i++)
		{
			ReferenceNode node = (ReferenceNode) subNodes.get(i);
			//�ѱ���������Ʒ������ɾ����
			if(node.getIsLock()==1){
				lockedRefMap.put(node.getRefNodeID(), "");
				LOG.info("ȫ���¼ܣ������¼���������Ʒ"+node.getRefNodeID());
				continue;
			}
			oldSortIdCollection.put(node.getRefNodeID(),new Integer(node.getSortID()));
			//String id = node.getId();//����Ӧ�ú���������
			String id = node.getRefNodeID();//ֻ����Ӧ��
			if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
				boolean flag = false;
				//�ų�ȫ���ļ��к��У�����Ҳ�Ѿ��ϼ��˵���Ʒ��
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
            	if (id.charAt(0) >= '0' && id.charAt(0) <= '9')// �����Ӧ����Ҫ��д��Ʒ��ʷ��
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
            
            // ����ǽ�������Ӧ��, ����Ӧ����Ϣ
            if(RepositoryConstants.SYN_HIS_YES.equals(isSyn))
            {

                GoodsChanegHisBO.getInstance()
                                .addDelHisToList(category,
                                                 node,
                                                 content.getSubType());
            }
            
		}
		category.saveNode();

		LOG.info("�����ϼܲ�����ʼ��");
		for(int i=0;i<list.size();i++)
		{
            ReferenceNode refNode = ( ReferenceNode ) list.get(i);
            //�Ѿ�����������Ʒ�����������ϼ�
            if(lockedRefMap.containsKey(refNode.getRefNodeID())){
            	LOG.info("ȫ���¼ܣ������ϼ���������Ʒ"+refNode.getRefNodeID());
            	continue;
            }
            Node node = ( Node ) Repository.getInstance()
                                           .getNode(refNode.getRefNodeID());
            if (node == null)
            {
                LOG.error("�޷��ҵ�����Ʒ��id=" + refNode.getRefNodeID());
                continue;
            }
            if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// �ж��Ƿ������ݡ�
            {
                LOG.error("id������id���ǲ�Ʒ���͵�id,���Ը����ݡ�id=" + node.getId());
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
            // ����ǽ�������Ӧ��, ����Ӧ����Ϣ
            if (RepositoryConstants.SYN_HIS_YES.equals(isSyn))
            {
                GContent content = (GContent)Repository.getInstance().getNode(node.getId(),node.getType());//remove by aiyan
                GoodsChanegHisBO.getInstance().addAddHisToList(category,
                                                               node.getId(),
                                                               content.getSubType());
            }

            count++;
            
////          Catogoryid	����	String	����ID
////          Contentid	����	String	Ӧ��ID
////          Action	����	String	0���½�
////          9��ɾ��
////          Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
//          SSMSDAO.getInstance().addRefMessages(category.getId(), content.getContentID(), "0");  //add by aiyan 2013-03-12
        }
        
		if(transactionID!=null){
			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
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
		        	// ȫ���������¼ܸ��½ӿ�
		    		PPMSDAO.addMessagesStatic(MSGType.BatchRefModifyReq, transactionID,
		    				categoryId);
		    }
		 
		    @Override
		    public String toString(){
		        return this.transactionID;
		    }
		}
	/**
	 * ����Ʒȥ�ء������ظ��ģ����������Ǹ���
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
	 * ����EXECL�ļ�����ȡҪ��ӵ���Ʒ��Ϣ
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
//		        	LOG.error("sheet("+i+")�ǿյģ�");
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
//										LOG.error("��"+(j+1)+"��,���ݣ�����Ų�����8λ����");
//										continue;
//									}
//								} catch (NumberFormatException e)
//								{
//									LOG.error("��"+(j+1)+"��,���ݣ���������");
//									continue;
//								}
//								break;
//							case 2:	
//								String value="";
//							    try
//                                {
//                                    // ��ѯcontentID��Ӧ������id�������޽����Ĭ�ϲ���contentid
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
//                                    LOG.error("��ѯ����idʧ�ܣ�contentid=" + value, e);
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
//			LOG.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
//			throw new BOException("���������ļ������쳣", e);
//		}
//		return list;
//	}
	
	//��������xls add by aiyan 2012-12-19
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
//		        	LOG.error("sheet("+i+")�ǿյģ�");
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
//										LOG.error("��"+(j+1)+"��,���ݣ�����Ų�����8λ����");
//										continue;
//									}
//								} catch (NumberFormatException e)
//								{
//									LOG.error("��"+(j+1)+"��,���ݣ���������");
//									continue;
//								}
//								break;
//							case 2:	
//
//								String value="";
//							    try
//                                {
//                                    // ��ѯcontentID��Ӧ������id�������޽����Ĭ�ϲ���contentid
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
//                                    LOG.error("��ѯ����idʧ�ܣ�contentid=" + value, e);
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
//			LOG.error("���������ļ������쳣,fineName:" + dataFile.getFileName(), e);
//			throw new BOException("���������ļ������쳣", e);
//		}
//		return list;
//	}
	
	/**
	 * �ϴ����ܵ�Ԥ��ͼ��ͼƬ
	 * @param request
	 * @throws BOException
	 */
	public String uploadCatePicURL(FormFile uploadFile,String cateId)throws BOException
	{


    	   //
    	String tempDir = ServerInfo.getAppRootPath() + File.separator+"temp"+File.separator;
    	String ftpDir = SystemConfig.FTPDIR_CATEGORYPICURL;
		IOUtil.checkAndCreateDir(tempDir);
		
		// ����ļ���������ļ�������·����
		String fileName = uploadFile.getFileName();
		String suffex = fileName.substring(fileName.lastIndexOf('.'));
		String oldFileName = tempDir + "temp" + suffex;// ƴװ��ʱ�ļ���Ϊ temp.png

		try
		{
			IOUtil.writeToFile(oldFileName, uploadFile.getFileData());
		} catch (Exception e1)
		{
			throw new BOException("д����ʱ�ļ�ʧ�ܡ�tempFilePath="+oldFileName,RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
		} 

		// ��Ҫ�������ֲ�ͬ����ͼƬ
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
			throw new BOException("ͼƬת������FileName=" + oldFileName, e,RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
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
			throw new BOException("�ϴ�����Դ�����������쳣��", e,RepositoryBOCode.CATEGORY_CATE_PIC_UPLOAD);
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
     *@desc ����
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
				throw new BOException("������չֵʧ��", e);
			}
		}
	}
 /**************************/
    
    
    /**
     * ���ڷ���ͼ�������չ�ֶ��б�
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
            // ����CategoryDAO���в�ѯ
            return CategoryDAO.getInstance()
                                      .getCategoryKeyResourceByCid(tid);
        }
        catch (DAOException e)
        {
        	LOG.error(e);
            throw new BOException("����MM������չ�ֶ��б�ʱ�������ݿ��쳣��",e);
        }
    }

    //add by aiyan 2012-12-19 ����������Ʒ��
	public int importCateData2(FormFile dataFile, Category category, String isSyn,String menuStatus) throws BOException{
		// TODO Auto-generated method stub
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
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
                    // ��ѯcontentID��Ӧ������id�������޽����Ĭ�ϲ���contentid
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
                    LOG.error("��ѯ����idʧ�ܣ�contentid=" + value, e);
                }
				refNode.setRefNodeID(value);
			
				return refNode;
			}});
		
		
		
        //���������Ʒ���Ż����������Ʒ���Ż�Ϊ�����ܵĻ��ܣ���Ҫ����Ϣ��ȥ��
        //����ӵ���Ϣ����,��������½����ܵĶ���Ҫ֪ͨ���������ģ��Ȱ����ݼӵ�T_a_messages��Ȼ��ר���̷߳�����Ϣ��
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("syncAndroid");
		String rootCategoryId = module.getItemValue("ROOT_CATEGORYID");
		String operateCategoryId = module.getItemValue("OPERATE_ROOT_CATEGORYID");//������Ʒ���Ż��������µ���Ӫ����
		String transactionID = null;
		 if(SSMSDAO.getInstance().isAndroidCategory(category.getId(), rootCategoryId)
			//||operateCategoryId.indexOf(cateId)!=-1){//������Ʒ���Ż������ܻ�����Ҫ�������Ӫ�����¶�������Ϣ modify by aiyan 2013-05-10		 		
			||SSMSDAO.getInstance().isOperateCategory(category.getId(), operateCategoryId)){//modify by aiyan 2013-05-18
				 transactionID = ContextUtil.getTransactionID();
		 }
		//above add by aiyan 2013-04-18 && 2013-05-10	
		 
		LOG.info("�����ϼܲ�����ʼ��");
		int count=0;
		for(int i=0;i<list.size();i++)
		{
            ReferenceNode refNode = ( ReferenceNode ) list.get(i);
            Node node = ( Node ) Repository.getInstance()
                                           .getNode(refNode.getRefNodeID());
            if (node == null)
            {
                LOG.error("�޷��ҵ�����Ʒ��id=" + refNode.getRefNodeID());
                continue;
            }
            if (!(node.getType().startsWith(RepositoryConstants.TYPE_GCONTENT)))// �ж��Ƿ������ݡ�
            {
                LOG.error("id������id���ǲ�Ʒ���͵�id,���Ը����ݡ�id=" + node.getId());
                continue;
            }
            String id= ReferenceTools.getInstance().isExistRef(category.getCategoryID(), refNode.getRefNodeID());//�鿴��Ʒ�����Ƿ������ID
            if(id==null){//����
            	
            	//remove by aiyan 2013-04-18
//                CategoryTools.addGood(category,
//                        node.getId(),
//                        refNode.getSortID(),
//                        0);
                
            	//modified  Ϊ����Ʒ���Ż��ܹ����������ķ�����Ϣ��
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
            }else{//�޸����
            	ReferenceTools.getInstance().updateSortid(id, refNode.getSortID(), 0,menuStatus);
                //��Ʒ���Ż�����Ϣ���͵Ĵ��롣
            	
            	
                try{
//                	String goodsID = GoodsBO.getGoodsIDbyRefID(id);
//                	SSMSDAO.getInstance().addMessages(MSGType.RefModifyReq,transactionID,goodsID+":0");
                	
                    //����˵Ҫ�����¼���Ϣ���,����ע������ģ��������¡� add by aiyan 2013-04-27
//                  Goodsid	����	String	reference���е�goodsid
//                  Categoryid	��ѡ	String	����categoryid���½�ʱ������
//                  Id	��ѡ	String	����categoryid��Ӧ��Id���½�ʱ������
//                  Refnodeid	��ѡ	String	Ӧ��ID���½�ʱ������
//                  Sortid	��ѡ	String	�����ֶΣ��½�ʱ������
//                  Loaddate	��ѡ	String	����ʱ�䣬�½�ʱ������
//                  Action	����	String	0���½�
//                  9��ɾ��
//                  Transactionid	����	String	��������ID�����ڴ���ͬһ��������ʱʹ�á�
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
								LOG.error("���t_r_reference���ID��Ȼû��ֵ,ID:" + id);
							}
						}

                }catch(Exception e){
                	LOG.error("�ֹ��ϼ��Ƿ�����Ϣ����",e);
                }
            }
            


            // ����ǽ�������Ӧ��, ����Ӧ����Ϣ
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
			MessageSendDAO.getInstance().changeStatus(transactionID,Constant.MESSAGE_HANDLE_STATUS_RUNNING, Constant.MESSAGE_HANDLE_STATUS_INIT);//add by aiyan 2013-05-20����EXECLE�����ʱ����д-5�������״̬��Ϊ-1
		}
        
		return count;
	}

}
