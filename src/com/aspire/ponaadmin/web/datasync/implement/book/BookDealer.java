package com.aspire.ponaadmin.web.datasync.implement.book;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.gcontent.GBook;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.repository.CategoryBO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.ReferenceNode;
import com.aspire.ponaadmin.web.repository.Repository;
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.repository.SearchParam;
import com.aspire.ponaadmin.web.repository.Searchor;
import com.aspire.ponaadmin.web.util.DateUtil;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BookDealer implements DataDealer
{

	 /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(BookDealer.class);
    /**
     * ����Ϊ�յ�ʱ����Ҫ�ϼܵĻ��ܷ���
     */
	private static final String OtherCate="����";
    private static Category contentRoot;//���ڻ���
    private static Category bookRoot;//���ڻ���
    private static Category categoryRoot;//���ڻ���
    private HashMap categoryList=new HashMap();
    	
    
	public int dealDataRecrod(DataRecord record)
	{
	
		GBook book=new GBook();
		//����ID��ֱ���û�������������
		book.setContentID(( String ) record.get(1));
        book.setId("b" + book.getContentID());
		book.setName((String)record.get(2));
		book.setBookTitle((String)record.get(2));
		book.setAuthor((String)record.get(3));
		book.setBookCat((String)record.get(4));
		book.setContentUrl((String)record.get(5));
		book.setInvalidTime(PublicUtil.formateDate((String)record.get(6)));
		book.setChangeType((String)record.get(7));
		String tmp=(String)record.get(8);
		if(!tmp.equals(""))
		{
			book.setMarketDate(PublicUtil.formateDate(tmp));
		}
		
		book.setCreateDate(PublicUtil.getCurDateTime());
		
		String cateName=book.getCateName();
		//�����������Ϊ�գ��������������С�
		if(cateName.trim().equals(""))
    	{
    		cateName=OtherCate;
    	}
		

        // Ϊ��ȷ��ϵͳ�����������ͬһ��Ʒ��ID�Ƿ������ϵͳ��
		 Node old=null;
        try
        {
            // ����contentId��ѯpas���ݱ����Ƿ���ڸ�����
            old = contentRoot.getNode(book.getId(), false);
        }
        catch (BOException e1)
        {
            logger.error("���ݲ�Ʒ��ID" + book.getId()
                         + "��ѯ�Ƿ������ϵͳ��ʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
        }
        // ���������ÿ�ܽ�������ӵ����ݸ������£��������ϼܵ���Ӧ����Ƶ����
        if ("1".equals(book.getChangeType()) )
        {
        	if(old != null)
			{
				logger.error("����ͼ������ʧ�ܣ�contentID="+book.getContentID()+"��ϵͳ�Ѵ��ڸ�����");
				return DataSyncConstants.FAILURE_ADD_EXIST;
			}
            try
            {
                // �������ݵ����ݿ�             
                contentRoot.addNode(book);
                contentRoot.saveNode();
            }
            catch (BOException e)
            {
                logger.error("��Ʒ��ID" + book.getContentID()
                         + "��Ӧ�ļ�¼���ʱ���������ݿ��쳣������ʧ��",e);
            }
            try
			{

				Category category = (Category) categoryList.get(cateName);
				// Ϊnull��ʾ��һ�η��ʸ÷���

				if (category == null)
				{
					//��ѯ��ǰ�����ݵ����������Ƿ���ڣ������ھ�Ҫ����
					Searchor search = new Searchor();
					// �������������ͬ����������
					search.getParams().add(
							new SearchParam("name", RepositoryConstants.OP_EQUAL,
									cateName));
					List list = bookRoot.searchNodes(RepositoryConstants.TYPE_CATEGORY,
							search, null);
					// �������
					Category childCategory = null;
					if (null == list || list.size() == 0)
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("���಻���ڣ���Ҫ������" + cateName);
						}
						// ��������
						childCategory = new Category();
						childCategory.setName(cateName);
						childCategory.setDesc(cateName);
						childCategory.setRelation("O");
						String childCategoryID = CategoryBO.getInstance().addCategory(
								bookRoot.getId(), childCategory);
						childCategory = (Category) Repository.getInstance().getNode(
								childCategoryID, RepositoryConstants.TYPE_CATEGORY);
					}
					else
					{
						childCategory = (Category) list.get(0);
					}
					// ���浽����categoryList���´ο���ֱ�ӵ���
					categoryList.put(cateName, childCategory);
					// ��Ҫ����Ĵ���ִ�б������ݵ��ϼܲ���
					category = childCategory;
				}
				// �ŵ�Ŀ�������
				ReferenceNode refNodeRoot = new ReferenceNode();
				refNodeRoot.setRefNodeID(book.getId());
				refNodeRoot.setSortID(0);
                refNodeRoot.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
                                                       + book.getId() + "|", 39, "0"));
                refNodeRoot.setCategoryID(category.getCategoryID());
                refNodeRoot.setLoadDate(DateUtil.formatDate(new Date(),
                                                            "yyyy-MM-dd HH:mm:ss"));
                refNodeRoot.setSortID(0);
                category.addNode(refNodeRoot);
                category.saveNode();
            }
            catch (BOException e)
            {
                logger.error("��Ʒ��ID" + book.getContentID()
                             + "��Ӧ�ļ�¼�ϼ�ʱ���������ݿ��쳣������ʧ��",e);
                return DataSyncConstants.FAILURE_ADD;
            }
            return DataSyncConstants.SUCCESS_ADD;
        }

        // ���£����ÿ�ܶ����ݽ��и��¡�
        else if ("2".equals(( String ) book.getChangeType()))
        {
        	if(old == null)
			{
				logger.error("����ͼ������ʧ�ܣ�contentID="+book.getContentID()+"��ϵͳ�����ڸ�����");
				return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
			}
            try
            {
               book.save();	
            }
            catch (BOException e)
            {
            	logger.error("����ͼ�����ݳ���,contentID="+book.getContentID(),e);
            	return DataSyncConstants.FAILURE_UPDATE;
            }
            return DataSyncConstants.SUCCESS_UPDATE;
        }
        // ���ߣ����ÿ�ܶ���Ʒ�����ݽ������ߡ�
        else if ("3".equals(book.getChangeType()))
        {
        	if(old == null)
			{
				logger.error("ɾ��ͼ������ʧ�ܣ�contentID="+book.getContentID()+"��ϵͳ�����ڸ�����");
				return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
			}
            // ����old���ݶ�Ӧ��id�����Ҷ�Ӧ����Ʒ
            try
            {
                Searchor searchor2 = new Searchor();
                //��Ҫ�ݹ��ѯ
                searchor2.setIsRecursive(true);
                searchor2.getParams().add(new SearchParam("refNodeID",
                                                         RepositoryConstants.OP_EQUAL,
                                                         book.getId()));
                List refList = categoryRoot.searchNodes(RepositoryConstants.TYPE_REFERENCE,
                                                     searchor2, null);               
                // ����Ʒȫ���¼�
                for (int i = 0; i < refList.size(); i++)
                {
                    ReferenceNode ref = ( ReferenceNode ) refList.get(i);
                    categoryRoot.delNode(ref);   
                }
                //һ�α������
                categoryRoot.saveNode();
                
                // ������Դ�����ɾ�����ݽӿڽ�������ɾ��
                contentRoot.delNode(book);
                contentRoot.saveNode();
            }
            catch (BOException e)
            {
            	logger.error("�¼��������ݳ���contentID="+book.getContentID(),e);
                return DataSyncConstants.FAILURE_DEL;
            }     
            return DataSyncConstants.SUCCESS_DEL;
        }
        else
        {
            // �쳣�����¼������Ϣ��������
        	logger.error("contentIDΪ" + book.getContentID() + "��Changetype��������,Changetype="+book.getChangeType());
        	return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
        }

	}

	public void init(DataSyncConfig config)
	{
		try
		{
			contentRoot=( Category ) Repository.getInstance()
			.getNode(RepositoryConstants.ROOT_CONTENT_ID,RepositoryConstants.TYPE_CATEGORY);
			bookRoot=( Category ) Repository.getInstance()
            .getNode(RepositoryConstants.ROOT_CATEGORY_BOOK_ID,
                    RepositoryConstants.TYPE_CATEGORY);
			categoryRoot=(Category) Repository.getInstance().getNode(
					RepositoryConstants.ROOT_CATEGORY_ID,
					RepositoryConstants.TYPE_CATEGORY);
		} catch (BOException e)
		{
			logger.error(e);
		}

	}

	public void clearDirtyData()
	{
		categoryList.clear();
		
	}

	public void prepareData() throws Exception
	{
		
	}

}
