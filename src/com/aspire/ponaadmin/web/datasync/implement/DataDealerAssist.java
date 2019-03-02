package com.aspire.ponaadmin.web.datasync.implement;

import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.constant.ErrorCode;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryDAO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * 数据处理的辅助类。抽取数据处理的公共方法。
 * @author zhangwei
 *
 */
public class DataDealerAssist
{
	/**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(DataDealerAssist.class) ;
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
	public int delCateAndGoods(String CateId) throws BOException
	{
		Category cate = new Category(CateId);
		return delCateAndGoods(cate);
	}

	public int delCateAndGoods(Node cate) throws BOException
	{
		int delCount = 0;
		List subNodes = cate.searchNodes(null, null, null);
		for (int i = 0; i < subNodes.size(); i++)
		{
			Node node=(Node)subNodes.get(i);
			if(node.getType().equals(RepositoryConstants.TYPE_CATEGORY))
			{
				delCount+=delCateAndGoods(node);
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
	public int clearCate(String cateId,boolean isRecursive)throws BOException
	{
		Category cate=new Category(cateId);
		return clearCate(cate, isRecursive);
	}
	public int clearCate(Node cate,boolean isRecursive)throws BOException
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
					delCount+=clearCate(node,isRecursive);
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
	public String createCategory(String pCateId,String cateName)throws BOException
	{
		Category pCate=(Category)Repository.getInstance().getNode(pCateId, RepositoryConstants.TYPE_CATEGORY);
		Category childCategory = createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
		
		
	}
	/**
	 * 再某一个分类下创建货架，如果存在返回其id
	 * @param pCate 父分类货架
	 * @param cateName  待创建的货架名称
	 * @return 创建后的货架id
	 * @throws BOException 创建失败
	 */
	public String createCategory(Category pCate,String cateName)throws BOException
	{
		Category childCategory = this.createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
	}
	/**
	 * 再某一个分类下创建货架，如果存在返回其id
	 * @param pCate 父分类货架
	 * @param subCate  待创建的货架,该参数可以不用设置货架和父货架编码。
	 * @return 创建后的货架id
	 * @throws BOException 创建失败,或者当前货架已存在同名的货架。
	 */
	public String createCategory(Category pCate,Category subCate)throws BOException
	{
		logger.info("检查当前货架下是否存在和新建货架同名的货架。");
		Searchor search = new Searchor();
		// 构造大类名称相同的搜索条件
		search.getParams().add(
				new SearchParam("name", RepositoryConstants.OP_EQUAL, subCate.getName()));
		List list = pCate.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
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
				subCate.setId(cate.getId());
				subCate.setParentCategoryID(pCate.getCategoryID());
				subCate.setCategoryID(cate.getCategoryID());
				subCate.save();
			}
			else //同名货架存在，不能再建。
			{
				throw new BOException("存在重名货架，创建货架失败，。cateName=" + subCate.getName(),
						ErrorCode.CATE_NAME_EXIST);
			}
			return subCate.getId();
		}
		//需要新建。
		if (logger.isDebugEnabled())
		{
			logger.debug("不存在名称为" + subCate.getName() + "的货架，重新创建一个新货架。");
		}
		//查看当前货架的货架编码是否分配。
		if(subCate.getCategoryID()==null)
		{
			int categoryId;
			try
			{
				categoryId=CategoryDAO.getInstance().getSeqCategoryID();
			} catch (DAOException e)
			{
				throw new BOException("获取当前货架的货架编码出错",e);
			}
			subCate.setCategoryID(String.valueOf(categoryId));
		}
		subCate.setParentCategoryID(pCate.getCategoryID());
		pCate.addNode(subCate);
		pCate.saveNode();

		return subCate.getId();
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
	public Category createNewCategoryVO(String cateName,String pCategoryID)throws BOException
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
	public int delCateGoods(Category pCate,String refId)throws BOException
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
	public int delAllGoodsByRefId(String refId)throws BOException
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
	 * 在父货架下创建引用某内容的商品。
	 * @param pCate 父分类货架
	 * @param content  商品引用的内容
	 * @return 是否删除成功
	 * @throws 新增失败 
	 */
	public void addCateGoods(Category pCate,String contId)throws BOException
	{
		ReferenceNode refNodeRoot = new ReferenceNode();
		refNodeRoot.setRefNodeID(contId);
		refNodeRoot.setSortID(0);
		refNodeRoot.setCategoryID(pCate.getCategoryID());
		refNodeRoot.setGoodsID(PublicUtil.rPad(pCate.getCategoryID() + "|"
				+ contId + "|", 39, "0"));
		refNodeRoot.setLoadDate(PublicUtil.getCurDateTime());
		refNodeRoot.setSortID(0);
		pCate.addNode(refNodeRoot);
		pCate.saveNode();
	}

}
