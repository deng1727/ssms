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
 * 货架的工具类。主要针对一些不需要处理历史表的货架简单操作(所有的上架操作需要处理历史表)。既一些非应用的应用类的货架。
 * 包含对货架和货架下商品的操作。
 * @author zhangwei
 *
 */
public class CategoryTools
{ 
	/**
     * 日志引用
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
	 * 删除分类，并下架该分类下的所有商品。
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
	 * 删除货架（包含子货架），并下架该货架下所有商品，
	 * @param cate 待删除的货架结点对象
	 * @return 返回共删除多少个商品。
	 * @throws BOException 删除失败。
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
			delCount++;//只计算删除多少商品
		}
		cate.saveNode();
		//使用根分类删除分类节点即可
		Category rootCategory=new Category(RepositoryConstants.ROOT_CATEGORY_ID);
		rootCategory.delNode(cate);
		rootCategory.saveNode();
		
		return delCount;
	}
	/**
	 * 删除该分类下的所有的商品。
	 * @param cateId 分类id
	 * @param isRecursive 是否递推删除子分类的商品
	 * @return
	 */
	public static int clearCateGoods(String cateId,boolean isRecursive)throws BOException
	{
		Category cate=new Category(cateId);
		return clearCateGoods(cate, isRecursive);
	}
	/**
	 * 删除该分类下的所有的商品。
	 * @param cate 分类对象
	 * @param isRecursive 是否递推删除子分类的商品。
	 * @return 删除商品的个数。
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
				delCount++;//只计算删除多少商品
			}
			
		}
		cate.saveNode();
		return delCount;
	}
	/**
	 * 再某一个分类下创建货架，如果存在返回其id
	 * @param pCateId 父分类id
	 * @param cateName  待创建的货架名称
	 * @return 创建后的货架id
	 * @throws BOException 创建失败
	 */
	public static  String createCategory(String pCateId,String cateName)throws BOException
	{
		Category pCate=(Category)Repository.getInstance().getNode(pCateId, RepositoryConstants.TYPE_CATEGORY);
		Category childCategory = createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
	}
	/**
	 * 再某一个分类下创建货架，如果存在返回其id
	 * @param pCateId 父分类id
	 * @param cateName  待创建的货架名称
	 * @return 创建后的货架id
	 * @throws BOException 创建失败
	 */
	public static  String createCategory(String pCateId,Category childCategory)throws BOException
	{
		Category pCate=(Category)Repository.getInstance().getNode(pCateId, RepositoryConstants.TYPE_CATEGORY);
		return createCategory(pCate,childCategory);
	}
	/**
	 * 再某一个分类下创建货架，如果存在返回其id
	 * @param pCate 父分类货架
	 * @param cateName  待创建的货架名称
	 * @return 创建后的货架id
	 * @throws BOException 创建失败
	 */
	public static  String createCategory(Category pCate,String cateName)throws BOException
	{
		Category childCategory = createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
	}
	/**
	 * 再某一个分类下创建货架，如果存在返回其id
	 * @param pCate 父分类货架
	 * @param childCategory  待创建的货架,该参数可以不用设置货架和父货架编码。
	 * @return 创建后的货架id
	 * @throws BOException 创建失败,或者当前货架已存在同名的货架。
	 */
	public static String createCategory(Category pCate,Category childCategory)throws BOException
	{
		logger.info("检查当前货架下是否存在和新建货架同名的货架。");
		Searchor search = new Searchor();
		// 构造大类名称相同的搜索条件
		search.getParams().add(
				new SearchParam("name", RepositoryConstants.OP_EQUAL, childCategory.getName()));
		Taxis taxis= new Taxis();
		taxis.getParams().add(new TaxisParam("delFlag",RepositoryConstants.ORDER_TYPE_ASC));//
		List list = pCate.searchNodes(RepositoryConstants.TYPE_CATEGORY, search,taxis);
		if (list.size() != 0)
		{
			Category cate = (Category) list.get(0);
			// 判断一下delflag是否为1
			if (cate.getDelFlag() == 1)
			{
				if (logger.isDebugEnabled())
				{
					logger.debug("更改该货架的delflag标志位为0。id=" + cate.getId());
				}
				// 重新利用delflag为1的货架id.货架编码就使用原来的编码。
				childCategory.setDelFlag(0);
				childCategory.setState(1);
				childCategory.setId(cate.getId());
				childCategory.setParentCategoryID(pCate.getCategoryID());
				childCategory.setCategoryID(cate.getCategoryID());
				childCategory.save();
			}
			else //同名货架存在，不能再建。
			{
				throw new BOException("存在重名货架，创建货架失败，。cateName=" + childCategory.getName(),
						RepositoryBOCode.CATEGORY_NAME_EXISTED);
			}
			return childCategory.getId();
		}
		//需要新建。
		if (logger.isDebugEnabled())
		{
			logger.debug("不存在名称为" + childCategory.getName() + "的货架，重新创建一个新货架。");
		}
		//查看当前货架的货架编码是否分配。
		if(childCategory.getCategoryID()==null)
		{
			int categoryId;
			try
			{
				categoryId=CategoryDAO.getInstance().getSeqCategoryID();
			} catch (DAOException e)
			{
				throw new BOException("获取当前货架的货架编码出错",e);
			}
			childCategory.setCategoryID(String.valueOf(categoryId));
		}
		childCategory.setParentCategoryID(pCate.getCategoryID());
		pCate.addNode(childCategory);
		pCate.saveNode();

		return childCategory.getId();
	}
	/**
	 * 创建一个全新的包含默认值的货架对象。
	 * 
	 * @param cateName
	 *            当前货架的名称
	 * @param pCategoryID
	 *            父货架的货架编码。
	 * @return 货架对象。
	 * @throws BOException
	 *             当无法分配货架编码时。
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
			throw new BOException("获取当前货架的货架编码出错",e);
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
	 * 下架货架下引用id为refId的商品
	 * @param pCate 父分类货架
	 * @param refId  引用的内容id
	 * @return 是否删除成功
	 * @throws BOException 删除失败
	 */
	public static  int delGoodsByRefId(Category pCate,String refId)throws BOException
	{
		int count=0;
		Searchor search = new Searchor();
		// 构造大类名称相同的搜索条件
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
	 * 下架所有引用id为refId的商品
	 * @param refId  引用的内容id
	 * @return 是否删除成功
	 * @throws BOException 删除失败
	 */
	public static  int delAllGoodsByRefId(String refId)throws BOException
	{
		int count=0;
		Searchor search = new Searchor();
		// 构造大类名称相同的搜索条件
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
	 * 在cate货架上上架商品。如果是应用的类型，需要写入历史表。
	 * @param pCate 父分类货架
	 * @param conId  商品引用的内容的内部id
	 * @return 是否删除成功
	 * @throws 新增失败 
	 */
	public static  void addGood(Category cate,String contId,String menuStatus)throws BOException
	{
		int sortId = 0;
		try {
			//由于添加了商品位置锁定功能，尽量不让sorti重复，故取sortid的最大值作为新的排序号
			sortId = LockLocationDAO.getInstance().getMaxSortId(cate.getCategoryID());
		} catch (Exception e) {
			// TODO: handle exception
		}
		addGood(cate,contId,sortId,menuStatus);
	}
	
	//COPY上面的方法，这个是商品库优化里面上架时候发消息而加的方法 ADD BY AIYAN 2013-04-18
	public static  void addGoodTran(Category cate,String contId,String transactionID,String menuStatus)throws BOException
	{
		int sortId = 0;
		try {
			//由于添加了商品位置锁定功能，尽量不让sorti重复，故取sortid的最大值作为新的排序号
			sortId = LockLocationDAO.getInstance().getMaxSortId(cate.getCategoryID());
		} catch (Exception e) {
			// TODO: handle exception
		}
		addGood(cate,contId,sortId,RepositoryConstants.VARIATION_NEW,false,transactionID,menuStatus);
	}
	/**
	 * 在cate货架上上架商品。如果是应用的类型，需要写入历史表。
	 * @param cate 分类货架
	 * @param conId  商品引用的内容的内部id
	 * @param sortId 商品在该货架下的排序值。
	 * @throws 新增失败 
	 */
	public static  void addGood(Category cate,String contId,int sortId,String menuStatus)throws BOException
	{
		addGood(cate,contId,sortId,RepositoryConstants.VARIATION_NEW,false,menuStatus);
	}
	/**
	 * 在cate货架上上架商品。如果是应用的类型，需要写入历史表。
	 * @param cate 分类货架
	 * @param conId  商品引用的内容的内部id
	 * @param sortId 商品在该货架下的排序值。
	 * @param variation 排序值变更信息
	 * @throws 新增失败 
	 */
	public static  void addGood(Category cate,String contId,int sortId,int variation,String menuStatus)throws BOException
	{
		addGood(cate,contId,sortId,variation,false,menuStatus);
	}
	
	//COPY上面的方法，这个是商品库优化里面上架时候发消息而加的方法 ADD BY AIYAN 2013-04-18
	public static  void addGood(Category cate,String contId,int sortId,int variation,String transactionID,String menuStatus)throws BOException
	{
		addGood(cate,contId,sortId,variation,false,transactionID,menuStatus);
	}

	/**
	 * 在cate货架上上架商品。如果是应用的类型，需要写入历史表。
	 * @param cate   分类货架
	 * @param conId  商品引用的内容的内部id
	 * @param sortId 商品在该货架下的排序值。
	 * @param variation 排序id变更信息
	 * @param isAutoReflesh  是否是自动上架。（如果是自动上架，只有历史表不存在该goodsid才写入历史表）
	 * @throws BOException 新增失败 
	 */
	public static void addGood(Category cate,String contId,int sortId,int variation,boolean isAutoReflesh,String menuStatus)throws BOException
	{
		if(contId.charAt(0)>='0'&&contId.charAt(0)<='9')//应用。
        {
			GoodsBO.addNodeAndInsertGoodsInfo(cate, contId, sortId,variation, isAutoReflesh,menuStatus);
        }
        //非应用类。彩铃、资讯、A8音乐等id以非数字开发的内容: 商品编码=货架编码+"|"+内容ID+"|"，不够39位的话后补0；
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
		if(contId.charAt(0)>='0'&&contId.charAt(0)<='9')//应用。
        {
			GoodsBO.addNodeAndInsertGoodsInfo(cate, contId, sortId,variation, isAutoReflesh,transactionID, menuStatus);
        }
        //非应用类。彩铃、资讯、A8音乐等id以非数字开发的内容: 商品编码=货架编码+"|"+内容ID+"|"，不够39位的话后补0；
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
	 * 把某货架下的商品限制在max数量范围内，当执行这个方法后会按照t_r_reference表的sortid降序后，把末尾的超过max数的下架。
	 * 
	 * 下架步伐：
	 * 1，删除对应的 t_r_base的记录
	 * 2，删除对应的 t_r_reference的记录
	 * 3，满足事务
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
	        // 进行事务操作
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

	            // 提交事务操作
	            tdb.commit();
	            
	          // System.out.println(a+"-------"+b);
	        }
	        catch (Exception e)
	        {
	            // 执行回滚
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
		  if(refrenceVO.getRefNodeId().charAt(0)>='0'&&refrenceVO.getRefNodeId().charAt(0)<='9')//应用。
	        {
			  rId = GoodsBO.addNodeAndInsertGoodsInfo(category, refrenceVO,false);
	        }
	        //非应用类。彩铃、资讯、A8音乐等id以非数字开发的内容: 商品编码=货架编码+"|"+内容ID+"|"，不够39位的话后补0；
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
		  if(refrenceVO.getRefNodeId().charAt(0)>='0'&&refrenceVO.getRefNodeId().charAt(0)<='9')//应用。
	        {
			  rId = GoodsBO.addNodeAndInsertGoodsInfo(category, refrenceVO,true);
	        }
	        //非应用类。彩铃、资讯、A8音乐等id以非数字开发的内容: 商品编码=货架编码+"|"+内容ID+"|"，不够39位的话后补0；
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
