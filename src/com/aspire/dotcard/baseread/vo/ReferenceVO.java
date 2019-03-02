
package com.aspire.dotcard.baseread.vo;

import java.util.Map;

/**
 * ����ͼ����Ʒ��
 * 
 * @author x_zhailiqing
 * 
 */
public class ReferenceVO
{

    /**
     * ������ID
     */
    private int cId;

    /**
     * ר�������¡����ж�Ӧ��id
     */
    private String categoryId;

    /**
     * ͼ��ID
     */
    private String bookId;

    /**
     * ������ ����
     */
    private int sortNumber;
    
    /**
     * ��������
     */
    private String cateName;
    
    /**
     * ��������ʡ��
     */
    private String province;
    
    /**
     * �����������
     */
    private String city;
    
    /**
     * 1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ���
     */
    private int changeType;

    public int getChangeType()
    {
        return changeType;
    }

    public void setChangeType(int changeType)
    {
        this.changeType = changeType;
    }

    public int getcId()
    {
        return cId;
    }

    public void setcId(int cId)
    {
        this.cId = cId;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getBookId()
    {
        return bookId;
    }

    public void setBookId(String bookId)
    {
        this.bookId = bookId;
    }

    public int getSortNumber()
    {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber)
    {
        this.sortNumber = sortNumber;
    }

    /**
     * ��ֵ ר����
     * 
     * @param data
     * @return
     */
    public boolean setValue(String[] data)
    {
        if (data.length != 4)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()) || null == data[3]
            || "".equals(data[3].trim()))
        {
            return false;
        }
        int changeType;
        try
        {
            if (null != data[2] && !"".equals(data[2].trim()))
            {
                sortNumber = Integer.parseInt(data[2]);
            }
            changeType = Integer.parseInt(data[3]);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        if (changeType != 1 && changeType != 2 && changeType != 3)
        {
            return false;
        }
        this.categoryId = data[0].trim();
        this.bookId = data[1].trim();
        this.changeType = changeType;
        return true;

    }
    
    /**
     * ��ֵ ר����
     * 
     * @param data
     * @param provinceMap ʡ��Ӧ��ϵ
     * @param cityMap �ж�Ӧ��ϵ
     * @return
     */
    public boolean setValue(String[] data, Map<String, String> provinceMap, Map<String, String> cityMap, String errorArea)
    {
        if (data.length != 6)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || data[0].trim().length() > 20
        		|| null == data[1] || "".equals(data[1].trim()) || data[1].trim().length() > 20
        		|| null == data[2] || "".equals(data[2].trim()) || data[2].trim().length() > 256
        		|| null == data[3] || "".equals(data[3].trim()) || data[3].trim().length() > 4000
        		|| null == data[5] || "".equals(data[5].trim()) || data[5].trim().length() > 2)
		{
			return false;
		}
        int changeType;
        try
        {
            if (null != data[4] && !"".equals(data[4].trim()))
            {
            	if(data[4].trim().length() > 9)
            		return false;
                sortNumber = Integer.parseInt(data[4].trim());
            }
            changeType = Integer.parseInt(data[5]);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        if (changeType != 1 && changeType != 2 && changeType != 3)
        {
            return false;
        }
        this.categoryId = data[0].trim();
        this.bookId = data[1].trim();
        String pro = this.getMMProvinceCity(data[2].trim(), provinceMap, "province", errorArea);
        if("".equals(pro))
        {
        	errorArea = "��ǰ����ID:" + bookId + "������ϢΪ�գ�" + errorArea;
        	return false;
        }
        this.province = pro;
        
        String cit =  this.getMMProvinceCity(data[3].trim(), cityMap, "city", errorArea);
        if("".equals(cit))
        {
        	errorArea = "��ǰ����ID:" + bookId + "������ϢΪ�գ�" + errorArea;
        	return false;
        }
        this.city = cit;
        
        this.changeType = changeType;
        return true;
    }
    
    /**
     * ���ݻ���ʡ����Ϣ���ض�Ӧ��MMʡ����Ϣ
     * @param province
     * @param provinceMap
     * @return
     */
    private String getMMProvinceCity(String proCity, Map<String, String> proCityMap, String area, String errorArea)
    {
    	StringBuffer proCityStr = new StringBuffer();
    	StringBuffer errCityStr = new StringBuffer();
    	
    	String[] proCitys = proCity.split(";", -1);
    	
    	for(int i=0; i<proCitys.length; i++)
    	{
    		String temp = proCityMap.get(proCitys[i]);
    		
    		// �����$�򷵻�000
    		if("$".equals(proCitys[i]))
    		{
    			temp = "000";
    		}
    		
    		// �����$�򷵻�000
    		if("000".equals(proCitys[i]))
    		{
    			temp = "000";
    		}
    		
    		// ����ǿյġ�����û�ж�Ӧ��ʡ����Ϣ
    		if(null == temp | "".equals(temp))
    		{
    			errCityStr.append(proCitys[i]).append("�����ڡ�");
    			continue;
    		}
    		
    		proCityStr.append(temp).append(";");
    	}
    	
    	if(errCityStr.length() > 0)
    	{
    		errorArea = "<br>" + area + ":" + errCityStr.toString();
    	}
    	if(proCityStr.length() != 0)
    	{
    		String temp = ";" + proCityStr.toString();
        	return temp;
    	}
    	else
    	{
    		return proCityStr.toString();
    	}
    	
    }
    
    /**
     * ��ֵ ר����
     * 
     * @param data
     * @return
     */
    public boolean setValueByRank(String[] data, String type)
    {
        if (data.length != 4)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
            || "".equals(data[1].trim()) || null == data[2]
            || "".equals(data[2].trim()))
        {
            return false;
        }
        try
        {
            if (null != data[3] && !"".equals(data[3].trim()))
            {
                sortNumber = Integer.parseInt(data[3]);
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        
        if("month".equals(type))
        {
            this.categoryId = data[0].trim()+"11";
            this.cateName = data[1].trim() + "�°�";
        }
        else if ("total".equals(type))
        {
            this.categoryId = data[0].trim()+"12";
            this.cateName = data[1].trim() + "�ܰ�";
        }
        else if ("week".equals(type))
        {
            this.categoryId = data[0].trim()+"13";
            this.cateName = data[1].trim() + "�ܰ�";
        }
        else
        {
            this.categoryId = data[0].trim();
            this.cateName = data[1].trim();
        }
        this.bookId = data[2].trim();
        return true;

    }

    public String getCateName()
    {
        return cateName;
    }

    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}
}
