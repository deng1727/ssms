package com.aspire.dotcard.a8;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.mail.Mail;
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
import com.aspire.ponaadmin.web.util.StringTool;

public class A8TopListImport extends A8DataImport
{
	
	private static final JLogger logger = LoggerFactory.getLogger(A8TopListImport.class);
	/**
	 * ���ڱ���ɾ���������Ϣ
	 */
	HashMap categoryList=new HashMap();
	private static final String TOPLISTCATENAME="�ն��������а�";
	private Category topListCate;
	/**
	 * ��¼����֮ǰ����ÿһ��������sortId.
	 */
	private HashMap oldSordIdCollection;
	public A8TopListImport() throws BOException
	{
		super();
	}

	void init()throws BOException
	{
		this.ftpDir =A8ParameterConfig.A8TopListDir;
		this.localDir=getPathWithSlash(A8ParameterConfig.BackupDir)+A8ParameterConfig.A8TopListDir;
		this.title="A8�񵥵���";
		String date = PublicUtil.getDateString(new Date(), "yyyyMMdd");
		this.regex="A8_list_"+date+"_[a-zA-Z0-9]+\\.txt";//A8_list_yyyymmd_*.txt
		topListCate=getTopListCate();
		oldSordIdCollection=new HashMap();//��¼����֮ǰ����ÿһ��������sortId.

	}

	int insertDB(Object object)
	{
		TopListVO vo=(TopListVO)object;
		String cateName=vo.getCateName();
		Category category=(Category)categoryList.get(cateName);
		//Ϊnull��ʾ��һ�η��ʸð�
		try
		{
			if(category==null)
			{
				//�ð񵥵ĵ�һ��������Ҫ����ѯ�����Ƿ���ڣ�������ھ��¼ܸð��µ�������Ʒ
				
				// �õ��ն�����Ƶ����Ƶ�������
			   
			    Searchor search = new Searchor();
			    // �������������ͬ����������
			    search.getParams().add(new SearchParam("name",
			                                           RepositoryConstants.OP_EQUAL,
			                                           vo.getCateName()));
			    List list = topListCate.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
			    //�������
			    Category childCategory = null;
			    if ( null == list || list.size() == 0)
			    {
			        if (logger.isDebugEnabled())
			        {
			            logger.debug("���ַ��಻���ڣ���Ҫ������" + cateName);
			        }
			        // ��������
			        childCategory = new Category();
			        childCategory.setName(cateName);
			        childCategory.setDesc(cateName);
			        childCategory.setRelation("O");
			        String childCategoryID = CategoryBO.getInstance()
			                                           .addCategory(topListCate.getId(),
			                                                        childCategory);
			        childCategory = ( Category ) Repository.getInstance()
			                                               .getNode(childCategoryID,
			                                                        RepositoryConstants.TYPE_CATEGORY);
			    }else
			    {
			    	childCategory=(Category)list.get(0);
			    	//�Ѿ����ڵİ񵥡���һ����Ҫ�¼ܸð񵥵��������ݡ�
			    	List goods=childCategory.searchNodes(RepositoryConstants.TYPE_REFERENCE, null, null);
			    	for(int i=0;i<goods.size();i++)
			    	{
			    		ReferenceNode node=(ReferenceNode)goods.get(i);
			    		oldSordIdCollection.put(node.getGoodsID(), new Integer(node.getSortID()));
			    		childCategory.delNode(node);
			    		successDelete++;
			    	}
			    	
			    	//childCategory.saveNode();
			    }
			  //���浽hashmap���´ο���ֱ�ӵ���
			    categoryList.put(cateName, childCategory);	
			  //��Ҫ����Ĵ���ִ�б������ݵ��ϼܲ���
		    	category=childCategory;
			}
			ReferenceNode ref=new ReferenceNode();
			ref.setRefNodeID(vo.getId());
			ref.setSortID(vo.getSortId());
			ref.setCategoryID(category.getCategoryID());
			ref.setGoodsID(PublicUtil.rPad(category.getCategoryID() + "|"
					+ vo.getId() + "|", 39, "0"));
			ref.setLoadDate(DateUtil.formatDate(new Date(),
			"yyyy-MM-dd HH:mm:ss"));
			
			if(oldSordIdCollection.containsKey(ref.getGoodsID()))
			{
				int oldSortId=((Integer)oldSordIdCollection.get(ref.getGoodsID())).intValue();
				ref.setVariation(ref.getSortID()-oldSortId);
			}else
			{
				ref.setVariation(RepositoryConstants.VARIATION_NEW);
			}
			
			category.addNode(ref);
			category.saveNode();
				
			
		} catch (BOException e)
		{
			logger.error("�ϼܰ����ݳ���"+vo,e);
			return A8ParameterConfig.failure;
		}
		return A8ParameterConfig.success_add;
	}

	Object transformVOByLineText(String line, int lineNumber)
	{
		if (logger.isDebugEnabled())
        {
            logger.debug("transformVOByLineText is starting.....");
        }
        String[] arrColorContent = line.split("\\|");
        int size = arrColorContent.length;
        if (size != 4)
        {
            logger.error(line + " ��ʽ��ֻ�����ĸ����ԡ�ϵͳ���Դ����ݣ�");
            return null;
        }
        TopListVO vo = new TopListVO();
        if(StringTool.lengthOfHZ(arrColorContent[0])>100)
        {
        	logger.error("������"+arrColorContent[0]+"���ܳ���100���ַ���ϵͳ���Դ�����");
        	return null;
        }
        //�жϸ���ID�Ƿ�ֻ������ĸ������
        if (!arrColorContent[1].matches("[0-9A-Za-z]{1,25}"))
        {
            logger.error("����ID " + arrColorContent[1] + " ֻ�ܺ������ֺ���ĸ������󲻳���25���ַ���ϵͳ���Դ����ݣ�");
            return null;
        }
        if(!arrColorContent[3].matches("-?\\d{1,6}"))
        {
        	logger.error("����ֵ " + arrColorContent[3] + " ֻ�ܺ������֣����6λ���֡�ϵͳ���Դ����ݣ�");
            return null;
        }
        while(--size >= 0)
        {
            if("".equals(arrColorContent[size].trim()))
            {
                logger.error(line + " ���п��ֶΣ����������޷����룡");
                return null;
            }
        }
        // ����VO���ֶ�ֵ��ע��ID�����ر���ǰ������ַ���"a8"
        vo.setCateName(arrColorContent[0]);
        vo.setId("a8"+arrColorContent[1]);
        vo.setName(arrColorContent[2]);
        vo.setSortId(-Integer.parseInt(arrColorContent[3]));
        Node node;
        try
		{
        	node=Repository.getInstance().getNode(vo.getId());
        	if(node==null)
        	{
        		if(logger.isDebugEnabled())
        		{
        			logger.debug("�ð����ݵĸ���id="+vo.getId()+"�ڱ�ϵͳ�����ڣ�������������");
        		}
        		return null;
        	}else
        	{
        		return vo;
        	}
		} catch (BOException e)
		{
			logger.error("��֤�����ݵĸ���id="+vo.getId()+"�Ƿ���ϵͳ�д��ڷ����쳣��ϵͳ���Դ�����", e);
			return null;
		}
	}

	private  Category getTopListCate() throws BOException
	{
        Category rootCate = (Category) Repository.getInstance().getNode(RepositoryConstants.ROOT_CATEGORY_ID,
				RepositoryConstants.TYPE_CATEGORY);

		Searchor search = new Searchor();
		// �������������ͬ����������
		search.getParams().add(
				new SearchParam("name", RepositoryConstants.OP_EQUAL, TOPLISTCATENAME));
		List topListCate = rootCate.searchNodes(RepositoryConstants.TYPE_CATEGORY,
				search, null);
		if (topListCate == null || topListCate.size() == 0)
		{
			Category cate = new Category();
			cate.setName(TOPLISTCATENAME);
			cate.setDesc(TOPLISTCATENAME);
			cate.setRelation("O");
			String cateId = CategoryBO.getInstance().addCategory(RepositoryConstants.ROOT_CATEGORY_ID, cate);
			return (Category) Repository.getInstance().getNode(cateId,
					RepositoryConstants.TYPE_CATEGORY);

		}
		else
		{
			return (Category) topListCate.get(0);
		}
	}
    
	/**
	 * ���ؿյ�List
	 */
	List getDBDate() throws DAOException
	{
		return new ArrayList();
	}
    //����Ҫ�¼ܲ���
	void deleteFromDB(List list)
	{
		
	}
	/**
	 * ��д�˷�������Ҫ�������¼���Ʒ�Ĵ���
	 */
	protected void mailToAdmin(String reason, boolean result)
    {

        String mailTitle;
        // �����ʼ���ʾ���δ������
        endDate = new Date();
        StringBuffer sb = new StringBuffer();
        if (result)
        {
        	
            // MailUtil.sen
            mailTitle = this.title + "�ɹ�";

            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<p>��������<br>");
            sb.append("����A8�񵥵��������ܹ��ɹ�����");
            sb.append(this.successAdd+this.successDelete+this.successUpdate);
            sb.append("����<br>���гɹ�����");
            sb.append(successAdd);
            sb.append("�����ɹ�����");
            sb.append(this.successUpdate);
            sb.append("�����ɹ��¼ܵİ���Ʒ����");
            sb.append(successDelete);
            sb.append("��\r\n");
            if(logger.isDebugEnabled())
            {
            	logger.debug("��������ʧ�ܴ��������Ϊ��"+failureCount);
            }

        }
        else
        {
            mailTitle = this.title + "ʧ��";
            sb.append("��ʼʱ�䣺");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",����ʱ�䣺");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("��<p>ʧ��ԭ��<br>");
            sb.append(reason);

        }
        Mail.sendMail(mailTitle, sb.toString(), A8ParameterConfig.MailTo);
    }
	Object getVOKey(Object object)
	{
		TopListVO vo=(TopListVO)object;
		return vo.getCateName()+"&"+vo.getId();
	}

}
