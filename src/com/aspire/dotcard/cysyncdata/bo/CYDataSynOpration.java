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
 *��ҵ��������ͬ�����߳�ִ����
 *
 */
public class CYDataSynOpration
{
	/**
	 * ��־����
	 */
	protected static JLogger logger = LoggerFactory.getLogger(CYDataSynOpration.class);
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
		
		 //���ڼ�¼Ӧ�ô���Ľ��
        StringBuffer record=null;
        TransactionDB tdb = null;
        // �õ�ContentTmp����
       // ContentTmp tmp = ( ContentTmp ) list.get(i);
        try
        {
            // �����������
            tdb = TransactionDB.getTransactionInstance();
            CYDataSyncDAO dao = CYDataSyncDAO.getTransactionInstance(tdb);
            
            record=new StringBuffer(tmp.getContentId());
            record.append(" | ");
            record.append(tmp.getLupdDate());
            record.append(" | ");
            record.append(tmp.getStatus());
            record.append(" | ");
            // �������Ϊ����״̬
            if (SyncDataConstant.CONTENT_TYPE_RELEASE.equals(tmp.getStatus()))
            {
            	logger.info("#########################��ҵ������ʼ�����������ݣ�contentId="+tmp.getContentId());
                // ����editSyncContent��PAS�е��������ݽ��б༭
               // int result = this.editSyncContent(tmp.getContentId(),
                //                     tmp.getContentType(),tacticList);
            	int result = this.editSyncContent(tmp,tacticList,record,mailInfoList[2]);
	
            	
                record.append('1');
                if(result==SyncDataConstant.SYNC_UPDATE)
                {
                	mailInfoList[0].add(tmp);
                }else if(result==SyncDataConstant.SYNC_DEL){
                	//�޻��������ϵ ����Ӧ��
                	//mailInfoList[2].add(tmp);
                }else
                {
                	mailInfoList[1].add(tmp);
                }
            }
            // �������Ϊ����״̬
            else if (SyncDataConstant.CONTENT_TYPE_OVERDUE.equals(tmp.getStatus()))
            {
            		// ���߲���
            	offLineContent(tmp,record,mailInfoList[2]);
            	/*
            	logger.info("#########################��ʼ�����������ݣ�contentId="+tmp.getContentId());
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
                List nodeList = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
                if(nodeList.size()!=0)
                {
                	GoodsBO.removeAllRefContentByContentID(tmp.getContentId());
                    // ͬʱ����delSyncContent����ɾ��pas�е���������
                    this.delSyncContent(tmp.getContentId(),
                                         tmp.getContentType());
                    mailInfoList[2].add(tmp);
                    record.append('1');
                }
                record.append('2');//��ʾû�з���ͬ����������cms�ͻ���ϵͳ���Ѿ����ߣ�����Ҫͬ��
           */ }else
            {
            	logger.error("��״̬���Ϸ������ݳ������Ը����ݡ�status="+tmp.getStatus());
            	mailInfoList[3].add(tmp);
            	record.append('0');
            }
            // ����CYDataSyncDAO��delSynccontetTmp����ɾ��t_syncContent_tmp���е���ʼ�¼
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
        // ����contentId���������͹������Ҫ�༭���������Զ���
        Node rootNode = new Node(RepositoryConstants.ROOT_CONTENT_ID);
        Searchor searchor = new Searchor();
        searchor.getParams().add(new SearchParam("contentID",
                                                 RepositoryConstants.OP_EQUAL,
                                                 contentId));
        searchor.getParams().add(new SearchParam("type",
                RepositoryConstants.OP_LIKE,
                "nt:gcontent:app%"));
        // ����contentId��ѯpas���ݱ����Ƿ���ڸ�����
        List list = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
        // ����dao��getGcontentFromCMS����
        GContent gc;
		try
		{
			gc = CYDataSyncDAO.getInstance().getGcontentFromCMS(contentId,
					contentType);
			if (gc != null) {
				GAppContent gap = (GAppContent) gc;
				if (gap.getDeviceName() == null
						|| gap.getDeviceName().equals("")) {
					// �����ϵΪ��
					// ���߲���
					logger.error("------devicename is null contentid="+tmp.getContentId());
					offLineContent(tmp, record, mailInfoList);
					//�޻��������ϵ ����Ӧ��
					return SyncDataConstant.SYNC_DEL;
				}
			}
		} catch (DAOException e)
		{
			throw new BOException("��CMS���޷���ȡ����ʧ�ܡ�contentId="+contentId,e);
		}
        // �޸�keywords,����ʽת��Ϊ���ǵĸ�ʽ.(�� ; �ָ� ת���� };{ �ָ�)
        processKeywords(gc);
        
        
       if (gc.getSubType().equals("6"))
		{
			// ����Ϊ��ҵ����Ӧ������
			// ����ӳ���滻�����������ƣ����MM��ҵ����һ������ID,2001,Ӧ���ࣻ2002��������
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
					//ƥ��ӳ���ϵ
					appGc.setAppCateName(vo.getCYAppCateName());
					//appGc.setProgramID(vo.getCYCateId());//���ô�ҵ����һ������ID,2001,Ӧ���࣬2002��������
				}
			}
			
		}

        
        
        // �������,�������Դ�����еĸ��½ӿڶ��������Զ�����и���
        if (list.size() != 0)
        {
            
            Node node = ( Node ) list.get(0);
            logger.debug("the node is:" + node);
            String id = node.getId();
            gc.setId(id);
            
            //���ݶ�������Ҫ����ԭֵ
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

            //�¼�
         //  boolean staus = checkAndUpdateCategory(gc,oldContent);
            // ���и��²���
            gc.save();            
            //�ϼ�
//            if(staus){
//            	//�޸��˷�����Ҫ�����ϼܷ���
//            	checkAndAddCategory(contentType, gc, tacticList);
//            }
            
            
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
            if(gc.getAuditionUrl() == null || gc.getAuditionUrl().length()==0){
            	//AuditionUrl �ǿղ��ҳ��ȴ���0����ΪWPӦ�ã�����Ҫ�ϼܷ������  add by dongke 20130709
            	checkAndAddCategory(contentType, gc, tacticList);	
            }
            
            return SyncDataConstant.SYNC_ADD;
        }
    }
    /**
     * ���߲���
     * @param tmp
     * @param record
     * @param mailInfoList
     * @throws BOException
     */
    private void offLineContent(ContentTmp tmp,StringBuffer record,List mailInfoList) throws BOException{

    	logger.info("#########################��ʼ�����������ݣ�contentId="+tmp.getContentId());
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
        List nodeList = rootNode.searchNodes(GContent.TYPE_CONTENT, searchor, null);
        if(nodeList.size()!=0)
        {
        	GoodsBO.removeAllRefContentByContentID(tmp.getContentId());
            // ͬʱ����delSyncContent����ɾ��pas�е���������
            this.delSyncContent(tmp.getContentId(),
                                 tmp.getContentType());
           // mailInfoList[2].add(tmp);
            mailInfoList.add(tmp);
            record.append('1');
        }
        record.append('2');//��ʾû�з���ͬ����������cms�ͻ���ϵͳ���Ѿ����ߣ�����Ҫͬ��
    	
    }
    
    /**
     * ��keywords�ĸ�ʽ����PAS�ĸ�ʽ
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
            // �������ķֺ�
            while (keywords.endsWith(";"))
            {
                keywords = keywords.substring(0, keywords.length() - 1);
            }
            keywords = "{" + keywords.replaceAll(";", "};{") + "}";
            gc.setKeywords(keywords);
        }
    }
    /**
     * ƥ�������ϼܲ���
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
    		logger.error("���ݲ��Ϸ���ContestYear is null "+appGc.getContentID() );
    		return;
    	}else if(cyYear.equals("2010")){
    		logger.error("old  CY  app ��ContestYear is 2010 "+appGc.getContentID() );
    		return;		
    	}
    	String categoryID;
    	//������ȥƥ��ÿһ������
    	for(int i = 0; i < size; i++)
    	{
    		vo = (CYTacticVO)tacticList.get(i);
    		categoryID = vo.getCategoryID();
    		
    		//�ȼ��ò��Զ�Ӧ�����ݻ��ܷ����Ƿ��Ѿ��ϼ��˸�����
    		if(checkTTMap(categoryID,gc.getContentID()))
    		{
    			continue;
    		}
    		
    		temp = vo.getContentType();
    		
    		//��һ����������������Ƿ�ƥ��
    		if(!(TacticConstants.CONTENT_TYPE_ALL.equals(temp) || type.equals(temp)))
    		{
    			//���Ͳ�ƥ��,�����һ������
    			continue;    			
    		}
    		    		
    		//�ڶ��������umFlagҵ��ͨ���Ƿ�ƥ��
    		temp = vo.getUmFlag();
    		if(!TacticConstants.UMFLAG_ALL.equals(temp))
    		{
                // ���ݶ�Ӧҵ���ҵ��ͨ������
                String gcUmFlag = null;
                gcUmFlag = getGContentUmflag(gc);
                if (!temp.equals(gcUmFlag))
    			{
    				//ҵ��ͨ�����Ͳ�ƥ�䣬�����һ������
    				continue;
    			}
    		}
    		
    		//��������������ݱ�ʶ�Ƿ�ƥ��
    		String keywords = gc.getKeywords();
    		int relation = vo.getTagRelation();
    		temp = vo.getContentTag();
    		if(!(null == temp || "".equals(temp) || 
    				relation == TacticConstants.TABRELATION_NULL || 
    				checkContentTag(keywords,vo.getTagList(),relation)))
    		{
    			//���ݱ�ʶ��ƥ�䣬�����һ������
    			continue;
    		}
    		
            //���Ĳ������Ӧ�÷��������Ƿ�ƥ��
            if (gc instanceof GAppContent)
            {
                //��gcת����Ӧ�����ݻ�ȡӦ�÷�������
                GAppContent app = (GAppContent)gc;
                //�ж�Ӧ�÷��������Ƿ���ͬ�������е�Ӧ�÷�������ƥ��
                if (!app.getAppCateName().equals(vo.getAppCateName()))
                {
                    continue;
                }
            }
      
    		//���岽�����м��ͨ�����������ϼܵ����Զ�Ӧ�Ļ��ܻ�����
    		addCategory(gc,categoryID); 
    		//д��������
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
     * ���ttMap���Ƿ���categoryID��Ӧ��contentID
     * @param categoryID
     * @param contentID
     * @return true ��ʾ������false ��ʾ������
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
     * ȡ���ݶ�Ӧҵ���ҵ��ͨ������umFlag
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
        	logger.error("ͨ����ҵ�����ҵ������ȡUmflagʱ�������ݿ��쳣��",e);
        	return null;
            //throw new BOException("ͨ����ҵ�����ҵ������ȡUmflagʱ�������ݿ��쳣��",e);
        }
    }
    
    /**
     * ������ݱ�ǩ�Ͳ��Ա�ǩ�Ƿ�ƥ��
     * @param gcTag ���ݱ�ǩ�ַ���,��ʽ�� {��ǩһ};{��ǩ��};{��ǩ��}
     * @param tacticTag ���Ա�ǩ�ַ�������ʽ�� ��ǩһ;��ǩ��;��ǩ��
     * @param relation ���Թ�ϵ��1=and; 2=or
     * @return true:ƥ��  false:��ƥ��
     */
    private boolean checkContentTag(String gcTag,List ttList, int relation)
    {
    	if(logger.isDebugEnabled())
    	{
    		logger.debug("DataSyncBO.checkContentTag()");
    		logger.debug("���ݱ�ǩ��" + gcTag + " ���Ա�ǩ��" + ttList + " ���Թ�ϵ��" + relation);
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
	        		logger.error("���ݱ�ǩ��ʽ����contentTag="+gcTag);
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
            		//������������һһ����ǩ
            		return true;
        		}
        	}
        }        
        else if(relation == TacticConstants.TABRELATION_AND && gcList.containsAll(ttList)) // and
        {
    		//�������������еı�ǩ
        	return true;
        }
    	
    	return false;
    }
    
    /**
     * �������ϼܵ����ܻ�����
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
		}
		catch (Exception e)
		{
			logger.error(e);
            throw new BOException("�����ϼܳ���", e);
		}
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
            DataSyncDAO.getInstance().addContentIdHis(Contentid, SyncDataConstant.DEL_CONTENT_TYPE_CY);
        }
        catch (DAOException e)
        {
            throw new BOException("�����¼�ʱ����������Ӧ�ü�¼�����", e);
        }
    }
}
