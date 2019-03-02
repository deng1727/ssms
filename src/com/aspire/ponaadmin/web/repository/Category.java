package com.aspire.ponaadmin.web.repository ;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>资源树中的分类节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.1.0
 * @since 1.0.1.0
 */
public class Category extends EntityNode
{

    /**
     * 日志引用
     */
    private static final JLogger LOG = LoggerFactory.getLogger(Category.class) ;

    /**
     * 构造方法
     */
    public Category()
    {
        this.type = "nt:category";
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public Category(String nodeID)
    {
        super(nodeID);
        this.type = "nt:category";
    }

    /**
     * 设置分类名称路径
     * @param namePath，分类名称路径
     */
    public void setNamePath(String namePath)
    {
        Property pro = new Property("namePath", namePath);
        this.setProperty(pro);
    }

    /**
     * 获取分类名称路径
     * @return，分类名称路径
     */
    public String getNamePath()
    {
        Property prop = this.getProperty("namePath");
        if(prop.getValue() != null)
        {
            return (String) prop.getValue() ;
        }
        String namePath = null;
        if(this.getId().equals(RepositoryConstants.ROOT_CATEGORY_ID))
        {
            namePath = this.getName();
        }
        else
        {
            try
            {
                Category category = (Category) Repository.getInstance().getNode(this.parentID
                		    , RepositoryConstants.TYPE_CATEGORY);
                namePath = category.getNamePath() + ">>" + this.getName();
            }
            catch (Exception e)
            {
                LOG.error(e);
            }
        }
        this.setNamePath(namePath);
        return namePath;
    }

    /**
     * 设置分类名称
     * @param value，分类名称
     */
    public void setName(String value)
    {
        Property pro = new Property("name", value);
        this.setProperty(pro);
    }

    /**
     * 获取分类名称
     * @return，分类名称
     */
    public String getName()
    {
        return (String) this.getProperty("name").getValue();
    }
    
    /**
     * 设置分类描述
     * @param value，分类描述
     */
    public void setDesc(String value)
    {
        Property pro = new Property("desc", value);
        this.setProperty(pro);
    }

    /**
     * 获取分类描述
     * @return，分类描述
     */
    public String getDesc()
    {
        return (String) this.getProperty("desc").getValue();
    }

    /**
     * 设置排序id
     * @param value，排序id
     */
    public void setSortID(int value)
    {
        Property pro = new Property("sortID", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 获取排序id
     * @return，排序id
     */
    public int getSortID()
    {
        return ((Integer) this.getProperty("sortID").getValue()).intValue();
    }

    /**
     * 设置分类隐含类型，即是否系统分类
     * @param value，分类隐含类型
     */
    public void setCtype(int value)
    {
        Property pro = new Property("ctype", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 获取分类隐含类型
     * @return，分类隐含类型
     */
    public int getCtype()
    {
        return ((Integer) this.getProperty("ctype").getValue()).intValue();
    }
    
    /**
     * 设置机型货架信息
     * @param value
     */
    public void setDeviceCategory(int value)
    {
        Property pro = new Property("deviceCategory", new Integer(value));
        this.setProperty(pro);
    }
    
    /**
     * 返回机型货架信息
     * @return
     */
    public int getDeviceCategory()
    {
        return ((Integer) this.getProperty("deviceCategory").getValue()).intValue();
    }
    
    /**
     * 设置货架平台信息
     * @param value
     */
    public void setPlatForm(String platForm)
    {
        Property pro = new Property("platForm", platForm);
        this.setProperty(pro);
    }
    
    /**
     * 返回货架平台信息
     * @return
     */
    public String getPlatForm()
    {
        return (String)this.getProperty("platForm").getValue();
    }
    
    /**
     * 设置货架限定城市信息
     * @param value
     */
    public void setCityId(String cityId)
    {
        Property pro = new Property("cityId", cityId);
        this.setProperty(pro);
    }
    
    /**
     * 返回货架限定城市信息
     * @return
     */
    public String getCityId()
    {
        return (String)this.getProperty("cityId").getValue();
    }
    
    /**
     * 是否被锁定 0是   1否
     * @return
     */
    public int getIsLock(){
    	return   ((Integer)this.getProperty("isLock").getValue()).intValue();
    }
    public void setIsLock(int value){
    	Property pro = new Property("isLock", new Integer(value));
        this.setProperty(pro);
    }
    /**
     * 锁定时间
     * @return
     */
    public String getLockTime()
    {
    	return  (String) this.getProperty("lockTime").getValue();
    }
    /**
     * 锁定时间
     * @param valuelockTime
     */
    public void setLockTime(String value)
    {
    	Property pro = new Property("lockTime", value);
        this.setProperty(pro);
    }
    /**
     * toString方法
     * @return 描述信息
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Category[");
        sb.append(this.getProperty("name"));
        sb.append(',');
        sb.append(this.getProperty("desc"));
        sb.append(',');
        sb.append(this.getProperty("sortID"));
        sb.append(',');
        sb.append(this.getProperty("ctype"));
        sb.append(',');
        sb.append(this.getProperty("categoryID"));
        sb.append(',');
        sb.append(this.getProperty("delFlag"));
        sb.append(',');
        sb.append(this.getProperty("changeDate"));
        sb.append(',');
        sb.append(this.getProperty("state"));
        sb.append(',');
        sb.append(this.getProperty("parentCategoryID"));
        sb.append(',');
        sb.append(this.getProperty("relation"));
        sb.append(',');
        sb.append(this.getProperty("picURl"));
        sb.append(',');
        sb.append(this.getProperty("statistic"));
        sb.append(',');
        sb.append(this.getProperty("platForm"));
        sb.append(',');
        sb.append(this.getProperty("cityId"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 设置货架编码
     * @param value，货架编码
     */
    public void setCategoryID(String value)
    {
        Property pro = new Property("categoryID", value);
        this.setProperty(pro);
    }

    /**
     * 获取货架编码
     * @return，货架编码
     */
    public String getCategoryID()
    {
        return (String) this.getProperty("categoryID").getValue();
    }
    
    /**
     * 设置删除标志位
     * @param value，删除标志位
     */
    public void setDelFlag(int value)
    {
        Property pro = new Property("delFlag", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 获取删除标志位
     * @return，删除标志位
     */
    public int getDelFlag()
    {
        return ((Integer) this.getProperty("delFlag").getValue()).intValue();
    }
    
    /**
     * 设置变更时间
     * @param value，变更时间
     */
    public void setChangeDate(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String value = sdf.format(date);
        Property pro = new Property("changeDate", value);
        this.setProperty(pro);
    }

    /**
     * 获取变更时间
     * @return，变更时间
     */
    public String getChangeDate()
    {
        return (String) this.getProperty("changeDate").getValue();
    }
    
    /**
     * 设置货架状态， 1,正常，在门户展示；0 不在门户展示        -----1：正常，9：下线
     * @param value，货架状态
     */
    public void setState(int value)
    {
        Property pro = new Property("state", new Integer(value));
        this.setProperty(pro);
    }

    /**
     * 获取货架状态
     * @return，货架状态
     */
    public int getState()
    {
        return ((Integer) this.getProperty("state").getValue()).intValue();
    }
    
    /**
     * 设置父货架编码
     * @param value，父货架编码
     */
    public void setParentCategoryID(String value)
    {
        Property pro = new Property("parentCategoryID", value);
        this.setProperty(pro);
    }

    /**
     * 获取父货架编码
     * @return，父货架编码
     */
    public String getParentCategoryID()
    {
        return (String) this.getProperty("parentCategoryID").getValue();
    }

    /**
     * 设置关联门店
     * @param value，关联门店
     */
    public void setRelation(String value)
    {
        Property pro = new Property("relation", value);
        this.setProperty(pro);
    }

    /**
     * 获取关联门店
     * @return，关联门店
     */
    public String getRelation()
    {
        return (String) this.getProperty("relation").getValue();
    }
    
    /**
     * 获取关联门店展示
     * @return，关联门店展示结果
     */
    public String getDisplayRelation()
    {
        return getRelation().replaceAll("W", "WWW门店")
                            .replaceAll("O", "终端门店")
                            .replaceAll("P", "PC门店")
                            .replaceAll("A", "WAP门店");
    }
    /**
     * 设置货架预览图
     * @param picURL
     */
    public void setPicURL(String picURL)
    {
    	Property pro = new Property("picURL", picURL);
        this.setProperty(pro);
    }
    /**
     * 获取货架预览图地址
     * @return
     */
    public String getPicURL()
    {
    	return (String) this.getProperty("picURL").getValue();
    }
    /**
     * 设置货架统计信息
     * @param statistic
     */
    public void setStatistic(long statistic)
    {
    	Property pro = new Property("statistic", new Long(statistic));
        this.setProperty(pro);
    }
    /**
     * 获取货架统计信息
     * @return
     */
    public long getStatistic()
    {
    	return ((Long) this.getProperty("statistic").getValue()).longValue();
    }
    
   
    
    
    /**
     * 
     *@desc 
     *@author dongke
     *May 23, 2011
     * @param keyName
     * @param value
     * @throws BOException 
     */
    public void setKeyValue(String keyName,String value) throws BOException{
    	
		if (this.getCategoryID() != null && keyName  != null && value!= null )
		{
			KeyResourceVO oldKeyResource = this.getKeyResByName(keyName);
			int result = 0;
			
			if(oldKeyResource == null){
				//insert into
				try
				{
					result = CategoryDAO.getInstance().insertCategoryKeyRes(this.getCategoryID(),keyName,value);
				} catch (DAOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BOException("插入货架图片失败!",RepositoryBOCode.ADD_CATEGORY_PIC);
				}
			}else{
				//update to
				try
				{
					result = CategoryDAO.getInstance().updateCategoryKeyRes(this.getCategoryID(),oldKeyResource.getKeyId(),value);
				} catch (DAOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new BOException("更新货架图片失败!",RepositoryBOCode.UPDATE_CATEGORY_PIC);
				}
			}		
		}
    }
    
    
    
    /**
     * 设置货架开始生效时间
     * @param startDate
     */
    public void setStartDate(String startDate)
    {
    	Property pro = new Property("startDate", startDate);
        this.setProperty(pro);
    }
    /**
     * 获取货架开始/生效时间
     * @return
     */
    public String getStartDate()
    {
    	return (String) this.getProperty("startDate").getValue();
    }
    
    /**
     * 设置货架结束/失效时间
     * @param endDate
     */
    public void setEndDate(String endDate)
    {
    	Property pro = new Property("endDate", endDate);
        this.setProperty(pro);
    }
    /**
     * 获取货架开始生效时间
     * @return
     */
    public String getEndDate()
    {
    	return (String) this.getProperty("endDate").getValue();
    }
    
    /**
     * 
     *@desc 
     *@author dongke
     *May 21, 2011
     * @param keyName
     * @return
     */
  public KeyResourceVO getKeyResByName(String keyName)
	{
	  KeyResourceVO kr = null;
		if (this.getCategoryID() != null)
		{
			try
			{
				 kr = CategoryDAO.getInstance().getCategoryKeyResByKey(
						this.getCategoryID(), keyName);
			} catch (DAOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return kr;
	}
  /**
   * 
   *@desc 
   *@author dongke
   *May 21, 2011
   * @param id
   * @return
   */
//    public List getAllKeyResourceByCid()
//	{
//		if (this.getCategoryID() != null)
//		{
//			try
//			{
//				return CategoryDAO.getInstance().getCategoryKeyResourceByCid(
//						this.getCategoryID());
//			} catch (DAOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return null;
//			}
//		}
//		else
//		{
//			return null;
//		}
//	}
    /**
     * 
     *@desc 
     *@author dongke
     *May 21, 2011
     * @param id
     * @return
     */
//      public List getKeyBase()
//  	{
//  		
//  			try
//  			{
//  				return CategoryDAO.getInstance().getCategoryKeyBase();
//  			} catch (DAOException e)
//  			{
//  				// TODO Auto-generated catch block
//  				e.printStackTrace();
//  				return null;
//  			}
//  		
//  	}
  
  
  
		  /**
     * 货架URL(目前是为终端门户混排URL)
     * @param value，终端门户混排URL
     */
    public void setMultiurl(String multiurl)
    {
        Property pro = new Property("multiurl", multiurl);
        this.setProperty(pro);
    }

    /**
     * 货架URL(目前是为终端门户混排URL)
     * @return，终端门户混排URL
     */
    public String getMultiurl()
    {
        return ( String ) this.getProperty("multiurl").getValue();
    }

    /**
     * 是否支持异网
     * @param othernet
     */
    public void setOthernet(String othernet)
    {
        Property pro = new Property("othernet", othernet);
        this.setProperty(pro);
    }

    /**
     * 是否支持异网
     * @return
     */
    public String getOthernet()
    {
        return ( String ) this.getProperty("othernet").getValue();
    }
    /**
     * 是否需要审批
     * flag="yes"表示需要审批
     * 用于渠道商用户添加货架时作标识
     * @param othernet
     */
    public void setVerifyFlag(String flag)
    {
        Property pro = new Property("verifyFlag", flag);
        this.setProperty(pro);
    }

    /**
     * 是否需要审批
     *  flag="yes"表示需要审批
     *  用于渠道商用户添加货架时作标识
     * @return
     */
    public String getVerifyFlag()
    {
        return ( String ) this.getProperty("verifyFlag").getValue();
    }
    /**
     * 货架分类审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @param othernet
     */
    public void setClassifyStatus(String classifyStatus)
    {
        Property pro = new Property("classifyStatus", classifyStatus);
        this.setProperty(pro);
    }

    /**
     * 货架分类审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @return
     */
    public String getClassifyStatus()
    {
        return ( String ) this.getProperty("classifyStatus").getValue();
    }
    
    /**
     * 货架商品审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @param goodsStatus
     */
    public void setGoodsStatus(String goodsStatus)
    {
        Property pro = new Property("goodsStatus", goodsStatus);
        this.setProperty(pro);
    }

    /**
     * 货架商品审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @return
     */
    public String getGoodsStatus()
    {
        return ( String ) this.getProperty("goodsStatus").getValue();
    }
    /**
     * 备份被删除前的审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @param goodsStatus
     */
    public void setDelproStatus(String delproStatus)
    {
        Property pro = new Property("delproStatus", delproStatus);
        this.setProperty(pro);
    }

    /**
     * 备份被删除前的审批状态-- 0 编辑；1 已发布；2 待审批;3 审批不通过
     * @return
     */
    public String getDelproStatus()
    {
        return ( String ) this.getProperty("delproStatus").getValue();
    }
}
