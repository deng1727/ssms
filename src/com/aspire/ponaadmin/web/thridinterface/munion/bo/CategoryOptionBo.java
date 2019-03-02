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
     * singletonģʽ��ʵ��
     */
    private static CategoryOptionBo instance = new CategoryOptionBo();

    /**
     * ���췽������singletonģʽ����
     */
    private CategoryOptionBo ()
    {
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
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
		
		
		int count=0;
		Map oldSortIdCollection= new HashMap();
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
	/**
	 * ����Ʒȥ�ء������ظ��ģ����������Ǹ���
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
		        	// ȫ���������¼ܸ��½ӿ�
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
		// ����EXECL�ļ�����ȡ�ն�����汾�б�
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
