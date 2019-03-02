package com.aspire.ponaadmin.web.repository;

import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;
import com.aspire.ponaadmin.web.lockLocation.dao.LockLocationDAO;
import com.aspire.ponaadmin.web.lockLocation.vo.RefrenceVO;
import com.aspire.ponaadmin.web.repository.goods.GoodsBO;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * ���ܵĹ����ࡣ��Ҫ���һЩ����Ҫ������ʷ��Ļ��ܼ򵥲���(���е��ϼܲ�����Ҫ������ʷ��)����һЩ��Ӧ�õ�Ӧ����Ļ��ܡ�
 * �����Ի��ܺͻ�������Ʒ�Ĳ�����
 * @author zhangwei
 *
 */
public class CategoryTools
{ 
	/**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(CategoryTools.class) ;
    /**
     * rootCategory
     */
    private static Category rootCate;
    static {
    	try
		{
			rootCate= ( Category ) Repository.getInstance()
			.getNode(RepositoryConstants.ROOT_CATEGORY_ID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (BOException e)
		{
			logger.error(e);
		}
    }
   

	/**
	 * ɾ�����࣬���¼ܸ÷����µ�������Ʒ��
	 * @param CateId
	 * @return
	 * @throws BOException
	 */
	public static int delCategory(String CateId) throws BOException
	{
		Category cate = new Category(CateId);
		return delCategory(cate);
	}

	/**
	 * ɾ�����ܣ������ӻ��ܣ������¼ܸû�����������Ʒ��
	 * @param cate ��ɾ���Ļ��ܽ�����
	 * @return ���ع�ɾ�����ٸ���Ʒ��
	 * @throws BOException ɾ��ʧ�ܡ�
	 */
	public static int delCategory(Node cate) throws BOException
	{
		int delCount = 0;
		List subNodes = cate.searchNodes(null, null, null);
		for (int i = 0; i < subNodes.size(); i++)
		{
			Node node=(Node)subNodes.get(i);
			if(node.getType().equals(RepositoryConstants.TYPE_CATEGORY))
			{
				delCount+=delCategory(node);
			}
			cate.delNode(node);
			delCount++;//ֻ����ɾ��������Ʒ
		}
		cate.saveNode();
		//ʹ�ø�����ɾ������ڵ㼴��
		Category rootCategory=new Category(RepositoryConstants.ROOT_CATEGORY_ID);
		rootCategory.delNode(cate);
		rootCategory.saveNode();
		
		return delCount;
	}
	/**
	 * ɾ���÷����µ����е���Ʒ��
	 * @param cateId ����id
	 * @param isRecursive �Ƿ����ɾ���ӷ������Ʒ
	 * @return
	 */
	public static int clearCateGoods(String cateId,boolean isRecursive)throws BOException
	{
		Category cate=new Category(cateId);
		return clearCateGoods(cate, isRecursive);
	}
	/**
	 * ɾ���÷����µ����е���Ʒ��
	 * @param cate �������
	 * @param isRecursive �Ƿ����ɾ���ӷ������Ʒ��
	 * @return ɾ����Ʒ�ĸ�����
	 * @throws BOException
	 */
	public static int clearCateGoods(Node cate,boolean isRecursive)throws BOException
	{
		int delCount = 0;
		List subNodes = cate.searchNodes(null, null, null);
		for (int i = 0; i < subNodes.size(); i++)
		{
			Node node=(Node)subNodes.get(i);
			if(isRecursive)
			{
				if(node.getType().equals(RepositoryConstants.TYPE_CATEGORY))
				{
					delCount+=clearCateGoods(node,isRecursive);
				}
			}
			if(node.getType().equals(RepositoryConstants.TYPE_REFERENCE))
			{
				cate.delNode(node);
				delCount++;//ֻ����ɾ��������Ʒ
			}
			
		}
		cate.saveNode();
		return delCount;
	}
	/**
	 * ��ĳһ�������´������ܣ�������ڷ�����id
	 * @param pCateId ������id
	 * @param cateName  �������Ļ�������
	 * @return ������Ļ���id
	 * @throws BOException ����ʧ��
	 */
	public static  String createCategory(String pCateId,String cateName)throws BOException
	{
		Category pCate=(Category)Repository.getInstance().getNode(pCateId, RepositoryConstants.TYPE_CATEGORY);
		Category childCategory = createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
	}
	/**
	 * ��ĳһ�������´������ܣ�������ڷ�����id
	 * @param pCateId ������id
	 * @param cateName  �������Ļ�������
	 * @return ������Ļ���id
	 * @throws BOException ����ʧ��
	 */
	public static  String createCategory(String pCateId,Category childCategory)throws BOException
	{
		Category pCate=(Category)Repository.getInstance().getNode(pCateId, RepositoryConstants.TYPE_CATEGORY);
		return createCategory(pCate,childCategory);
	}
	/**
	 * ��ĳһ�������´������ܣ�������ڷ�����id
	 * @param pCate ���������
	 * @param cateName  �������Ļ�������
	 * @return ������Ļ���id
	 * @throws BOException ����ʧ��
	 */
	public static  String createCategory(Category pCate,String cateName)throws BOException
	{
		Category childCategory = createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
	}
	/**
	 * ��ĳһ�������´������ܣ�������ڷ�����id
	 * @param pCate ���������
	 * @param childCategory  �������Ļ���,�ò������Բ������û��ܺ͸����ܱ��롣
	 * @return ������Ļ���id
	 * @throws BOException ����ʧ��,���ߵ�ǰ�����Ѵ���ͬ���Ļ��ܡ�
	 */
	public static String createCategory(Category pCate,Category childCategory)throws BOException
	{
		logger.info("��鵱ǰ�������Ƿ���ں��½�����ͬ���Ļ��ܡ�");
		Searchor search = new Searchor();
		// �������������ͬ����������
		search.getParams().add(
				new SearchParam("name", RepositoryConstants.OP_EQUAL, childCategory.getName()));
		Taxis taxis= new Taxis();
		taxis.getParams().add(new TaxisParam("delFlag",RepositoryConstants.ORDER_TYPE_ASC));//
		List list = pCate.searchNodes(RepositoryConstants.TYPE_CATEGORY, search,taxis);
		if (list.size() != 0)
		{
			Category cate = (Category) list.get(0);
			// �ж�һ��delflag�Ƿ�Ϊ1
			if (cate.getDelFlag() == 1)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("���ĸû��ܵ�delflag��־λΪ0��id=" + cate.getId());
				}
				// ��������delflagΪ1�Ļ���id.���ܱ����ʹ��ԭ���ı��롣
				childCategory.setDelFlag(0);
				childCategory.setState(1);
				childCategory.setId(cate.getId());
				childCategory.setParentCategoryID(pCate.getCategoryID());
				childCategory.setCategoryID(cate.getCategoryID());
				childCategory.save();
			}
			else //ͬ�����ܴ��ڣ������ٽ���
			{
				throw new BOException("�����������ܣ���������ʧ�ܣ���cateName=" + childCategory.getName(),
						RepositoryBOCode.CATEGORY_NAME_EXISTED);
			}
			return childCategory.getId();
		}
		//��Ҫ�½���
		if (logger.isDebugEnabled())
		{
			logger.debug("����������Ϊ" + childCategory.getName() + "�Ļ��ܣ����´���һ���»��ܡ�");
		}
		//�鿴��ǰ���ܵĻ��ܱ����Ƿ���䡣
		if(childCategory.getCategoryID()==null)
		{
			int categoryId;
			try
			{
				categoryId=CategoryDAO.getInstance().getSeqCategoryID();
			} catch (DAOException e)
			{
				throw new BOException("��ȡ��ǰ���ܵĻ��ܱ������",e);
			}
			childCategory.setCategoryID(String.valueOf(categoryId));
		}
		childCategory.setParentCategoryID(pCate.getCategoryID());
		pCate.addNode(childCategory);
		pCate.saveNode();

		return childCategory.getId();
	}
	/**
	 * ����һ��ȫ�µİ���Ĭ��ֵ�Ļ��ܶ���
	 * 
	 * @param cateName
	 *            ��ǰ���ܵ�����
	 * @param pCategoryID
	 *            �����ܵĻ��ܱ��롣
	 * @return ���ܶ���
	 * @throws BOException
	 *             ���޷�������ܱ���ʱ��
	 */
	public static  Category createNewCategoryVO(String cateName,String pCategoryID)throws BOException
	{
		Category childCategory = new Category();
		childCategory.setName(cateName);
		childCategory.setDesc(cateName);
		childCategory.setSortID(0) ;
		childCategory.setCtype(0) ;
		int categoryId;
		try
		{
			categoryId=CategoryDAO.getInstance().getSeqCategoryID();
		} catch (DAOException e)
		{
			throw new BOException("��ȡ��ǰ���ܵĻ��ܱ������",e);
		}
		childCategory.setCategoryID(String.valueOf(categoryId));
		childCategory.setDelFlag(0);
		childCategory.setChangeDate(new Date());
		childCategory.setState(1);
		childCategory.setRelation("O");
		childCategory.setParentCategoryID(pCategoryID);
		childCategory.setDelFlag(0);
		return childCategory;
	}
	
	/**
	 * �¼ܻ���������idΪrefId����Ʒ
	 * @param pCate ���������
	 * @param refId  ���õ�����id
	 * @return �Ƿ�ɾ���ɹ�
	 * @throws BOException ɾ��ʧ��
	 */
	public static  int delGoodsByRefId(Category pCate,String refId)throws BOException
	{
		int count=0;
		Searchor search = new Searchor();
		// �������������ͬ����������
		search.getParams().add(
				new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, refId));
		List list = pCate.searchNodes(RepositoryConstants.TYPE_REFERENCE,search, null);
		Node good=null;
		for(int i=0;i<list.size();i++)
		{
			good=(Node)list.get(i);
			pCate.delNode(good);
			count++;
		}
		pCate.saveNode();
		return count;
	}
	/**
	 * �¼���������idΪrefId����Ʒ
	 * @param refId  ���õ�����id
	 * @return �Ƿ�ɾ���ɹ�
	 * @throws BOException ɾ��ʧ��
	 */
	public static  int delAllGoodsByRefId(String refId)throws BOException
	{
		int count=0;
		Searchor search = new Searchor();
		// �������������ͬ����������
		search.setIsRecursive(true);
		search.getParams().add(
				new SearchParam("refNodeID", RepositoryConstants.OP_EQUAL, refId));
		List list = rootCate.searchNodes(RepositoryConstants.TYPE_REFERENCE,search, null);
		Node good=null;
		for(int i=0;i<list.size();i++)
		{
			good=(Node)list.get(i);
			rootCate.delNode(good);
			count++;
		}
		rootCate.saveNode();
		return count;
	}	
	/**
	 * ��cate�������ϼ���Ʒ�������Ӧ�õ����ͣ���Ҫд����ʷ��
	 * @param pCate ���������
	 * @param conId  ��Ʒ���õ����ݵ��ڲ�id
	 * @return �Ƿ�ɾ���ɹ�
	 * @throws ����ʧ�� 
	 */
	public static  void addGood(Category cate,String contId,String menuStatus)throws BOException
	{
		int sortId = 0;
		try {
			//�����������Ʒλ���������ܣ���������sorti�ظ�����ȡsortid�����ֵ��Ϊ�µ������
			sortId = LockLocationDAO.getInstance().getMaxSortId(cate.getCategoryID());
		} catch (Exception e) {
			// TODO: handle exception
		}
		addGood(cate,contId,sortId,menuStatus);
	}
	
	//COPY����ķ������������Ʒ���Ż������ϼ�ʱ����Ϣ���ӵķ��� ADD BY AIYAN 2013-04-18
	public static  void addGoodTran(Category cate,String contId,String transactionID,String menuStatus)throws BOException
	{
		int sortId = 0;
		try {
			//�����������Ʒλ���������ܣ���������sorti�ظ�����ȡsortid�����ֵ��Ϊ�µ������
			sortId = LockLocationDAO.getInstance().getMaxSortId(cate.getCategoryID());
		} catch (Exception e) {
			// TODO: handle exception
		}
		addGood(cate,contId,sortId,RepositoryConstants.VARIATION_NEW,false,transactionID,menuStatus);
	}
	/**
	 * ��cate�������ϼ���Ʒ�������Ӧ�õ����ͣ���Ҫд����ʷ��
	 * @param cate �������
	 * @param conId  ��Ʒ���õ����ݵ��ڲ�id
	 * @param sortId ��Ʒ�ڸû����µ�����ֵ��
	 * @throws ����ʧ�� 
	 */
	public static  void addGood(Category cate,String contId,int sortId,String menuStatus)throws BOException
	{
		addGood(cate,contId,sortId,RepositoryConstants.VARIATION_NEW,false,menuStatus);
	}
	/**
	 * ��cate�������ϼ���Ʒ�������Ӧ�õ����ͣ���Ҫд����ʷ��
	 * @param cate �������
	 * @param conId  ��Ʒ���õ����ݵ��ڲ�id
	 * @param sortId ��Ʒ�ڸû����µ�����ֵ��
	 * @param variation ����ֵ�����Ϣ
	 * @throws ����ʧ�� 
	 */
	public static  void addGood(Category cate,String contId,int sortId,int variation,String menuStatus)throws BOException
	{
		addGood(cate,contId,sortId,variation,false,menuStatus);
	}
	
	//COPY����ķ������������Ʒ���Ż������ϼ�ʱ����Ϣ���ӵķ��� ADD BY AIYAN 2013-04-18
	public static  void addGood(Category cate,String contId,int sortId,int variation,String transactionID,String menuStatus)throws BOException
	{
		addGood(cate,contId,sortId,variation,false,transactionID,menuStatus);
	}

	/**
	 * ��cate�������ϼ���Ʒ�������Ӧ�õ����ͣ���Ҫд����ʷ��
	 * @param cate   �������
	 * @param conId  ��Ʒ���õ����ݵ��ڲ�id
	 * @param sortId ��Ʒ�ڸû����µ�����ֵ��
	 * @param variation ����id�����Ϣ
	 * @param isAutoReflesh  �Ƿ����Զ��ϼܡ���������Զ��ϼܣ�ֻ����ʷ�����ڸ�goodsid��д����ʷ��
	 * @throws BOException ����ʧ�� 
	 */
	public static void addGood(Category cate,String contId,int sortId,int variation,boolean isAutoReflesh,String menuStatus)throws BOException
	{
		if(contId.charAt(0)>='0'&&contId.charAt(0)<='9')//Ӧ�á�
        {
			GoodsBO.addNodeAndInsertGoodsInfo(cate, contId, sortId,variation, isAutoReflesh,menuStatus);
        }
        //��Ӧ���ࡣ���塢��Ѷ��A8���ֵ�id�Է����ֿ���������: ��Ʒ����=���ܱ���+"|"+����ID+"|"������39λ�Ļ���0��
        else
        {
        	String goodsID=PublicUtil.rPad(cate.getCategoryID() + "|" + contId + "|", 39, "0");
        	ReferenceNode refNodeRoot = new ReferenceNode();
    		refNodeRoot.setRefNodeID(contId);
    		refNodeRoot.setCategoryID(cate.getCategoryID());
    		refNodeRoot.setGoodsID(goodsID);
    		refNodeRoot.setLoadDate(PublicUtil.getCurDateTime());
    		refNodeRoot.setSortID(sortId);
    		refNodeRoot.setVariation(variation);
    		if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
    			refNodeRoot.setVerifyStatus("3");
    			cate.setGoodsStatus("0");
    			cate.save();
    		}else{
    			refNodeRoot.setVerifyStatus("1");
    		}    	
    		cate.addNode(refNodeRoot);
    		cate.saveNode();
            
        }
		
	}
	
	public static void addGood(Category cate,String contId,int sortId,int variation,boolean isAutoReflesh,String transactionID,String menuStatus)throws BOException
	{
		if(contId.charAt(0)>='0'&&contId.charAt(0)<='9')//Ӧ�á�
        {
			GoodsBO.addNodeAndInsertGoodsInfo(cate, contId, sortId,variation, isAutoReflesh,transactionID, menuStatus);
        }
        //��Ӧ���ࡣ���塢��Ѷ��A8���ֵ�id�Է����ֿ���������: ��Ʒ����=���ܱ���+"|"+����ID+"|"������39λ�Ļ���0��
        else
        {
        	String goodsID=PublicUtil.rPad(cate.getCategoryID() + "|" + contId + "|", 39, "0");
        	ReferenceNode refNodeRoot = new ReferenceNode();
    		refNodeRoot.setRefNodeID(contId);
    		refNodeRoot.setCategoryID(cate.getCategoryID());
    		refNodeRoot.setGoodsID(goodsID);
    		refNodeRoot.setLoadDate(PublicUtil.getCurDateTime());
    		refNodeRoot.setSortID(sortId);
    		refNodeRoot.setVariation(variation);
    		if(menuStatus != null && !"".equals(menuStatus) && "1".equals(menuStatus)){
    			refNodeRoot.setVerifyStatus("3");
    			cate.setGoodsStatus("0");
    			cate.save();
    		}else{
    			refNodeRoot.setVerifyStatus("1");
    		}    		
    		cate.addNode(refNodeRoot);
    		cate.saveNode();
            
        }
		
	}
	
	/**
	 * ��ĳ�����µ���Ʒ������max������Χ�ڣ���ִ�����������ᰴ��t_r_reference���sortid����󣬰�ĩβ�ĳ���max�����¼ܡ�
	 * 
	 * �¼ܲ�����
	 * 1��ɾ����Ӧ�� t_r_base�ļ�¼
	 * 2��ɾ����Ӧ�� t_r_reference�ļ�¼
	 * 3����������
	 * @param categoryid
	 * @param max
	 * @throws BOException
	 */
	
	  public static  void maxGoodsNum(String categoryid,int max) throws BOException
	    {

	        if (logger.isDebugEnabled())
	        {
	            logger.debug("maxGoodsNum("+categoryid+","+max+")");
	        }
	        // �����������
	        TransactionDB tdb = null; 
	        try
	        {
	            tdb = TransactionDB.getTransactionInstance();
//	            String sql =" delete from t_r_base where  id in "+
//	            "("+
//	             " select id from "+
//	             " ("+
//	             " select rownum rnum,T.id from "+
//	           " (select id from t_r_reference r  where categoryid =? order by sortid desc )T"+
//	           " )"+
//	           " where rnum>?"+
//	           " )";
	            String sql = tdb.getSQLByCode("com.aspire.ponaadmin.web.repository.CategoryTools.maxGoodsNum.base.DELETE");
	            tdb.execute(sql, new String[]{categoryid,max+""});
	            
//	            System.out.println("=============");
//	            System.out.println(sql);
//	            System.out.println("=============");
//	            
//	            System.out.println(sql);
//	            System.out.println(categoryid);
//	            System.out.println(max);
	            
//	            sql = "delete from t_r_reference where  id in"+
//	            " ("+
//	            " select id from "+
//	            " ("+
//	            " select rownum rnum,T.id from "+
//	           " (select id from t_r_reference r  where categoryid =? order by sortid desc )T"+
//	           " )"+
//	           " WHERE rnum>?"+
//	           " ) and categoryid =? ";
	            
	           sql = tdb.getSQLByCode("com.aspire.ponaadmin.web.repository.CategoryTools.maxGoodsNum.reference.DELETE");
	           tdb.execute(sql, new String[]{categoryid,max+"",categoryid});

	            // �ύ�������
	            tdb.commit();
	            
	          // System.out.println(a+"-------"+b);
	        }
	        catch (Exception e)
	        {
	            // ִ�лع�
	            tdb.rollback();
	            throw new BOException("db error!", e);
	        }
	        finally
	        {
	            if (tdb != null)
	            {
	                tdb.close();
	            }
	        }
	    }
	  public static String  addGood(Category category,RefrenceVO refrenceVO) throws BOException{
		  String rId = null;
		  if(refrenceVO.getRefNodeId().charAt(0)>='0'&&refrenceVO.getRefNodeId().charAt(0)<='9')//Ӧ�á�
	        {
			  rId = GoodsBO.addNodeAndInsertGoodsInfo(category, refrenceVO,false);
	        }
	        //��Ӧ���ࡣ���塢��Ѷ��A8���ֵ�id�Է����ֿ���������: ��Ʒ����=���ܱ���+"|"+����ID+"|"������39λ�Ļ���0��
	        else
	        {
	        	String goodsID=PublicUtil.rPad(category.getCategoryID() + "|" + refrenceVO.getRefNodeId() + "|", 39, "0");
	        	ReferenceNode refNodeRoot = new ReferenceNode();
	    		refNodeRoot.setRefNodeID(refrenceVO.getRefNodeId());
	    		refNodeRoot.setCategoryID(category.getCategoryID());
	    		refNodeRoot.setGoodsID(goodsID);
	    		refNodeRoot.setLoadDate(PublicUtil.getCurDateTime());
	    		refNodeRoot.setSortID(refrenceVO.getSortId());
	    		refNodeRoot.setVariation(99999);
	    		refNodeRoot.setVerifyStatus("1");
	    		
	    		refNodeRoot.setLockTime(refrenceVO.getLockTime());
//	    		refNodeRoot.setLockNum(refrenceVO.getLockNum());
	    		refNodeRoot.setLockUser(refrenceVO.getLockUser());
	    		refNodeRoot.setIsLock(refrenceVO.getIsLock());
	    		
	    		rId = category.addNode(refNodeRoot);
	    		category.saveNode();
	            
	        }
		  return rId;
	  }
	  public static String addGoodTran(Category category,RefrenceVO refrenceVO,String transactionID) throws BOException{
		  String rId = null;
		  if(refrenceVO.getRefNodeId().charAt(0)>='0'&&refrenceVO.getRefNodeId().charAt(0)<='9')//Ӧ�á�
	        {
			  rId = GoodsBO.addNodeAndInsertGoodsInfo(category, refrenceVO,true);
	        }
	        //��Ӧ���ࡣ���塢��Ѷ��A8���ֵ�id�Է����ֿ���������: ��Ʒ����=���ܱ���+"|"+����ID+"|"������39λ�Ļ���0��
	        else
	        {
	        	String goodsID=PublicUtil.rPad(category.getCategoryID() + "|" + refrenceVO.getRefNodeId() + "|", 39, "0");
	        	ReferenceNode refNodeRoot = new ReferenceNode();
	        	refNodeRoot.setIsLock(refrenceVO.getIsLock());
//	        	refNodeRoot.setLockNum(refrenceVO.getLockNum());
	        	refNodeRoot.setLockUser(refrenceVO.getLockUser());
	    		refNodeRoot.setRefNodeID(refrenceVO.getRefNodeId());
	    		refNodeRoot.setCategoryID(category.getCategoryID());
	    		refNodeRoot.setGoodsID(goodsID);
	    		refNodeRoot.setLoadDate(PublicUtil.getCurDateTime());
	    		refNodeRoot.setSortID(refrenceVO.getSortId());
	    		refNodeRoot.setVariation(99999);
	    		refNodeRoot.setVerifyStatus("1");
	    		rId = category.addNode(refNodeRoot);
	    		category.saveNode();
	            
	        }
		  return rId ;
	  }

}
