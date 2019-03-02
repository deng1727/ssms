package com.aspire.dotcard.syncData.tactic;

import java.util.ArrayList;
import java.util.List;

public class TacticVO
{
	//主键id
	private int id;
	
	//内容货架分类ID
	private String categoryID;
	
	//内容类型
	private String contentType;
	
	//业务通道
	private String umFlag;
		
	//内容标签
	private String contentTag;
	
	//内容标签逻辑关系
	private int tagRelation;
		
	//创建时间
	private String creatTime;
	
	//最后修改时间
	private String lastUpdateTime;

	//策略内容tag分隔后的集合
	private List tagList = null;
    
    //应用分类名称
    private String appCateName;
	
	/**
	 * 取策略内容tag分隔后的集合。注意：该方法为内容同步时设计，不支持标签修改。
	 * @return
	 */
	public List getTagList()
	{
		if(null == tagList)
		{
			tagList = new ArrayList();
			String[] items = contentTag.split(";");
	        for(int i = 0; i < items.length; i++)
	        {
	        	if(items[i].length() == 0)
	        	{
	        		continue;
	        	}
	        	tagList.add(items[i]);
	        }
		}		 
		return tagList;
	}

	public String getCategoryID()
	{
		return categoryID;
	}

	public void setCategoryID(String categoryID)
	{
		this.categoryID = categoryID;
	}

	public String getContentTag()
	{
		return contentTag;
	}

	public void setContentTag(String contentTag)
	{
		this.contentTag = contentTag;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public String getCreatTime()
	{
		return creatTime;
	}

	public void setCreatTime(String creatTime)
	{
		this.creatTime = creatTime;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getTagRelation()
	{
		return tagRelation;
	}

	public void setTagRelation(int tagRelation)
	{
		this.tagRelation = tagRelation;
	}

	public String getUmFlag()
	{
		return umFlag;
	}

	public void setUmFlag(String umFlag)
	{
		this.umFlag = umFlag;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("TacticVO[id=");
		sb.append(id);
		sb.append(", categoryID=");
		sb.append(categoryID);
		sb.append(", contentType=");
		sb.append(contentType);
		sb.append(", umFlag=");
		sb.append(umFlag);
        sb.append(", appCateName=");
        sb.append(appCateName);
		sb.append(", contentTag=");
		sb.append(contentTag);
		sb.append(", tagRelation=");
		sb.append(tagRelation);
		sb.append(", creatTime=");
		sb.append(creatTime);
		sb.append(", lastUpdateTime=");
		sb.append(lastUpdateTime);
		sb.append("]");
		
		return sb.toString();
	}

	/**
	 * 转换为页面显示内容
	 * 0=空;  1=and;  2=or
	 * @return
	 */
	public String getShowTagRelation()
	{
		switch(this.tagRelation)
		{
		case 0:
			return "";
		case 1:
			return "AND";
		case 2:
			return "OR";
		default:
			return "";
		}
	}

	/**
	 * 转换为页面显示内容
	 * W:WAP，S:SMS，M:MMS，E:WWW，A:CMNET。
	 * @return
	 */
	public String getShowUmFlag()
	{
		if("W".equalsIgnoreCase(this.umFlag))
		{
			return "WAP";
		}
		if("S".equalsIgnoreCase(this.umFlag))
		{
			return "SMS";
		}
		if("M".equalsIgnoreCase(this.umFlag))
		{
			return "MMS";
		}
		if("E".equalsIgnoreCase(this.umFlag))
		{
			return "WWW";
		}
		if("A".equalsIgnoreCase(this.umFlag))
		{
			return "CMNET";
		}		
		if("0".equals(this.umFlag))
		{
			return "全部";
		}
		return "错误类型";
	}
	
	/**
	 *  <OPTION value="all" selected >全部</OPTION>
	    <OPTION value="nt:gcontent:news" >资讯</OPTION> 
	    <OPTION value="nt:gcontent:colorring" >彩铃</OPTION> 
	    <OPTION value="nt:gcontent:appSoftWare" >软件</OPTION>
	    <OPTION value="nt:gcontent:appGame" >游戏</OPTION>
	    <OPTION value="nt:gcontent:appTheme" >主题</OPTION> 
	 * @return
	 */
	public String getShowContentType()
	{		
		if("nt:gcontent:news".equalsIgnoreCase(this.contentType))
		{
			return "资讯";
		}
        if("nt:gcontent:colorring".equalsIgnoreCase(this.contentType))
        {
            return "彩铃";
        }
		if("nt:gcontent:appGame".equalsIgnoreCase(this.contentType))
		{
			return "游戏";
		}
        if("nt:gcontent:appTheme".equalsIgnoreCase(this.contentType))
        {
            return "主题";
        }
        if("nt:gcontent:appSoftWare".equalsIgnoreCase(this.contentType))
        {
            return "软件";
        }
        if("nt:gcontent:audio".equalsIgnoreCase(this.contentType))
        {
            return "全曲";
        }
		if("all".equalsIgnoreCase(this.contentType))
		{
			return "全部";
		}
		return "错误类型";		
	}

    /**
     * 获取应用分类名称
     * @return
     */
    public String getAppCateName()
    {    
        return appCateName;
    }

    /**
     * 设置应用分类名称
     * @param appCateName
     */
    public void setAppCateName(String appCateName)
    {   
        this.appCateName = appCateName;
    }
	
}
