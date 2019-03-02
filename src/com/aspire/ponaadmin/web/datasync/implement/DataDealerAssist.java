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
 * ���ݴ���ĸ����ࡣ��ȡ���ݴ���Ĺ���������
 * @author zhangwei
 *
 */
public class DataDealerAssist
{
	/**
     * ��־����
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
	 * ɾ�����࣬���¼ܸ÷����µ�������Ʒ��
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
	public String createCategory(String pCateId,String cateName)throws BOException
	{
		Category pCate=(Category)Repository.getInstance().getNode(pCateId, RepositoryConstants.TYPE_CATEGORY);
		Category childCategory = createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
		
		
	}
	/**
	 * ��ĳһ�������´������ܣ�������ڷ�����id
	 * @param pCate ���������
	 * @param cateName  �������Ļ�������
	 * @return ������Ļ���id
	 * @throws BOException ����ʧ��
	 */
	public String createCategory(Category pCate,String cateName)throws BOException
	{
		Category childCategory = this.createNewCategoryVO(cateName, pCate.getCategoryID());
		return createCategory(pCate,childCategory);
	}
	/**
	 * ��ĳһ�������´������ܣ�������ڷ�����id
	 * @param pCate ���������
	 * @param subCate  �������Ļ���,�ò������Բ������û��ܺ͸����ܱ��롣
	 * @return ������Ļ���id
	 * @throws BOException ����ʧ��,���ߵ�ǰ�����Ѵ���ͬ���Ļ��ܡ�
	 */
	public String createCategory(Category pCate,Category subCate)throws BOException
	{
		logger.info("��鵱ǰ�������Ƿ���ں��½�����ͬ���Ļ��ܡ�");
		Searchor search = new Searchor();
		// �������������ͬ����������
		search.getParams().add(
				new SearchParam("name", RepositoryConstants.OP_EQUAL, subCate.getName()));
		List list = pCate.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
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
				subCate.setId(cate.getId());
				subCate.setParentCategoryID(pCate.getCategoryID());
				subCate.setCategoryID(cate.getCategoryID());
				subCate.save();
			}
			else //ͬ�����ܴ��ڣ������ٽ���
			{
				throw new BOException("�����������ܣ���������ʧ�ܣ���cateName=" + subCate.getName(),
						ErrorCode.CATE_NAME_EXIST);
			}
			return subCate.getId();
		}
		//��Ҫ�½���
		if (logger.isDebugEnabled())
		{
			logger.debug("����������Ϊ" + subCate.getName() + "�Ļ��ܣ����´���һ���»��ܡ�");
		}
		//�鿴��ǰ���ܵĻ��ܱ����Ƿ���䡣
		if(subCate.getCategoryID()==null)
		{
			int categoryId;
			try
			{
				categoryId=CategoryDAO.getInstance().getSeqCategoryID();
			} catch (DAOException e)
			{
				throw new BOException("��ȡ��ǰ���ܵĻ��ܱ������",e);
			}
			subCate.setCategoryID(String.valueOf(categoryId));
		}
		subCate.setParentCategoryID(pCate.getCategoryID());
		pCate.addNode(subCate);
		pCate.saveNode();

		return subCate.getId();
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
	public int delCateGoods(Category pCate,String refId)throws BOException
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
	public int delAllGoodsByRefId(String refId)throws BOException
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
	 * �ڸ������´�������ĳ���ݵ���Ʒ��
	 * @param pCate ���������
	 * @param content  ��Ʒ���õ�����
	 * @return �Ƿ�ɾ���ɹ�
	 * @throws ����ʧ�� 
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
