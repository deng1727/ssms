package com.aspire.dotcard.syncData.tactic;

import java.util.ArrayList;
import java.util.List;

public class TacticVO
{
	//����id
	private int id;
	
	//���ݻ��ܷ���ID
	private String categoryID;
	
	//��������
	private String contentType;
	
	//ҵ��ͨ��
	private String umFlag;
		
	//���ݱ�ǩ
	private String contentTag;
	
	//���ݱ�ǩ�߼���ϵ
	private int tagRelation;
		
	//����ʱ��
	private String creatTime;
	
	//����޸�ʱ��
	private String lastUpdateTime;

	//��������tag�ָ���ļ���
	private List tagList = null;
    
    //Ӧ�÷�������
    private String appCateName;
	
	/**
	 * ȡ��������tag�ָ���ļ��ϡ�ע�⣺�÷���Ϊ����ͬ��ʱ��ƣ���֧�ֱ�ǩ�޸ġ�
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
	 * ת��Ϊҳ����ʾ����
	 * 0=��;  1=and;  2=or
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
	 * ת��Ϊҳ����ʾ����
	 * W:WAP��S:SMS��M:MMS��E:WWW��A:CMNET��
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
			return "ȫ��";
		}
		return "��������";
	}
	
	/**
	 *  <OPTION value="all" selected >ȫ��</OPTION>
	    <OPTION value="nt:gcontent:news" >��Ѷ</OPTION> 
	    <OPTION value="nt:gcontent:colorring" >����</OPTION> 
	    <OPTION value="nt:gcontent:appSoftWare" >���</OPTION>
	    <OPTION value="nt:gcontent:appGame" >��Ϸ</OPTION>
	    <OPTION value="nt:gcontent:appTheme" >����</OPTION> 
	 * @return
	 */
	public String getShowContentType()
	{		
		if("nt:gcontent:news".equalsIgnoreCase(this.contentType))
		{
			return "��Ѷ";
		}
        if("nt:gcontent:colorring".equalsIgnoreCase(this.contentType))
        {
            return "����";
        }
		if("nt:gcontent:appGame".equalsIgnoreCase(this.contentType))
		{
			return "��Ϸ";
		}
        if("nt:gcontent:appTheme".equalsIgnoreCase(this.contentType))
        {
            return "����";
        }
        if("nt:gcontent:appSoftWare".equalsIgnoreCase(this.contentType))
        {
            return "���";
        }
        if("nt:gcontent:audio".equalsIgnoreCase(this.contentType))
        {
            return "ȫ��";
        }
		if("all".equalsIgnoreCase(this.contentType))
		{
			return "ȫ��";
		}
		return "��������";		
	}

    /**
     * ��ȡӦ�÷�������
     * @return
     */
    public String getAppCateName()
    {    
        return appCateName;
    }

    /**
     * ����Ӧ�÷�������
     * @param appCateName
     */
    public void setAppCateName(String appCateName)
    {   
        this.appCateName = appCateName;
    }
	
}
