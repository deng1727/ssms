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
	 * 用于保存删除分类的信息
	 */
	HashMap categoryList=new HashMap();
	private static final String TOPLISTCATENAME="终端音乐排行榜";
	private Category topListCate;
	/**
	 * 记录导入之前榜单中每一个歌曲的sortId.
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
		this.title="A8榜单导入";
		String date = PublicUtil.getDateString(new Date(), "yyyyMMdd");
		this.regex="A8_list_"+date+"_[a-zA-Z0-9]+\\.txt";//A8_list_yyyymmd_*.txt
		topListCate=getTopListCate();
		oldSordIdCollection=new HashMap();//记录导入之前榜单中每一个歌曲的sortId.

	}

	int insertDB(Object object)
	{
		TopListVO vo=(TopListVO)object;
		String cateName=vo.getCateName();
		Category category=(Category)categoryList.get(cateName);
		//为null表示第一次访问该榜单
		try
		{
			if(category==null)
			{
				//该榜单的第一个数据需要，查询榜单是是否存在，如果存在就下架该榜单下的所有商品
				
				// 得到终端音乐频道子频道－风格
			   
			    Searchor search = new Searchor();
			    // 构造大类名称相同的搜索条件
			    search.getParams().add(new SearchParam("name",
			                                           RepositoryConstants.OP_EQUAL,
			                                           vo.getCateName()));
			    List list = topListCate.searchNodes(RepositoryConstants.TYPE_CATEGORY, search, null);
			    //子类对象
			    Category childCategory = null;
			    if ( null == list || list.size() == 0)
			    {
			        if (logger.isDebugEnabled())
			        {
			            logger.debug("音乐分类不存在，需要创建：" + cateName);
			        }
			        // 创建大类
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
			    	//已经存在的榜单。第一次需要下架该榜单的所有数据。
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
			  //保存到hashmap中下次可以直接调用
			    categoryList.put(cateName, childCategory);	
			  //需要下面的代码执行本次数据的上架操作
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
			logger.error("上架榜单数据出错："+vo,e);
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
            logger.error(line + " 格式错，只能有四个属性。系统忽略此数据！");
            return null;
        }
        TopListVO vo = new TopListVO();
        if(StringTool.lengthOfHZ(arrColorContent[0])>100)
        {
        	logger.error("榜单名称"+arrColorContent[0]+"不能超过100个字符。系统忽略此数据");
        	return null;
        }
        //判断歌曲ID是否只包括字母和数字
        if (!arrColorContent[1].matches("[0-9A-Za-z]{1,25}"))
        {
            logger.error("歌曲ID " + arrColorContent[1] + " 只能含有数字和字母，且最大不超过25个字符。系统忽略此数据！");
            return null;
        }
        if(!arrColorContent[3].matches("-?\\d{1,6}"))
        {
        	logger.error("排序值 " + arrColorContent[3] + " 只能含有数字，最多6位数字。系统忽略此数据！");
            return null;
        }
        while(--size >= 0)
        {
            if("".equals(arrColorContent[size].trim()))
            {
                logger.error(line + " 中有空字段，此条内容无法导入！");
                return null;
            }
        }
        // 设置VO的字段值，注意ID做了特别处理，前面加了字符串"a8"
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
        			logger.debug("该榜单数据的歌曲id="+vo.getId()+"在本系统不存在，忽略这条数据");
        		}
        		return null;
        	}else
        	{
        		return vo;
        	}
		} catch (BOException e)
		{
			logger.error("验证榜单数据的歌曲id="+vo.getId()+"是否在系统中存在发生异常。系统忽略此数据", e);
			return null;
		}
	}

	private  Category getTopListCate() throws BOException
	{
        Category rootCate = (Category) Repository.getInstance().getNode(RepositoryConstants.ROOT_CATEGORY_ID,
				RepositoryConstants.TYPE_CATEGORY);

		Searchor search = new Searchor();
		// 构造大类名称相同的搜索条件
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
	 * 返回空的List
	 */
	List getDBDate() throws DAOException
	{
		return new ArrayList();
	}
    //不需要下架操作
	void deleteFromDB(List list)
	{
		
	}
	/**
	 * 重写此方法，主要是体现下架商品的次数
	 */
	protected void mailToAdmin(String reason, boolean result)
    {

        String mailTitle;
        // 发送邮件表示本次处理结束
        endDate = new Date();
        StringBuffer sb = new StringBuffer();
        if (result)
        {
        	
            // MailUtil.sen
            mailTitle = this.title + "成功";

            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<p>处理结果：<br>");
            sb.append("本次A8榜单导入任务总共成功处理");
            sb.append(this.successAdd+this.successDelete+this.successUpdate);
            sb.append("条。<br>其中成功新增");
            sb.append(successAdd);
            sb.append("条，成功更新");
            sb.append(this.successUpdate);
            sb.append("条，成功下架的榜单商品个数");
            sb.append(successDelete);
            sb.append("条\r\n");
            if(logger.isDebugEnabled())
            {
            	logger.debug("本次任务失败处理的条数为："+failureCount);
            }

        }
        else
        {
            mailTitle = this.title + "失败";
            sb.append("开始时间：");
            sb.append(PublicUtil.getDateString(startDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append(",结束时间：");
            sb.append(PublicUtil.getDateString(endDate, "yyyy-MM-dd HH:mm:ss"));
            sb.append("。<p>失败原因：<br>");
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
