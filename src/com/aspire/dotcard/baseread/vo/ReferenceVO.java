
package com.aspire.dotcard.baseread.vo;

import java.util.Map;

/**
 * 基地图书商品表
 * 
 * @author x_zhailiqing
 * 
 */
public class ReferenceVO
{

    /**
     * 货架主ID
     */
    private int cId;

    /**
     * 专区、包月、排行对应的id
     */
    private String categoryId;

    /**
     * 图书ID
     */
    private String bookId;

    /**
     * 排行用 排序
     */
    private int sortNumber;
    
    /**
     * 货架名称
     */
    private String cateName;
    
    /**
     * 销售区域省份
     */
    private String province;
    
    /**
     * 销售区域地市
     */
    private String city;
    
    /**
     * 1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现
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
     * 赋值 专区用
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
     * 赋值 专区用
     * 
     * @param data
     * @param provinceMap 省对应关系
     * @param cityMap 市对应关系
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
        	errorArea = "当前内容ID:" + bookId + "地域信息为空：" + errorArea;
        	return false;
        }
        this.province = pro;
        
        String cit =  this.getMMProvinceCity(data[3].trim(), cityMap, "city", errorArea);
        if("".equals(cit))
        {
        	errorArea = "当前内容ID:" + bookId + "地域信息为空：" + errorArea;
        	return false;
        }
        this.city = cit;
        
        this.changeType = changeType;
        return true;
    }
    
    /**
     * 根据基地省市信息返回对应的MM省市信息
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
    		
    		// 如果是$则返回000
    		if("$".equals(proCitys[i]))
    		{
    			temp = "000";
    		}
    		
    		// 如果是$则返回000
    		if("000".equals(proCitys[i]))
    		{
    			temp = "000";
    		}
    		
    		// 如果是空的。或者没有对应的省市信息
    		if(null == temp | "".equals(temp))
    		{
    			errCityStr.append(proCitys[i]).append("不存在。");
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
     * 赋值 专区用
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
            this.cateName = data[1].trim() + "月榜";
        }
        else if ("total".equals(type))
        {
            this.categoryId = data[0].trim()+"12";
            this.cateName = data[1].trim() + "总榜";
        }
        else if ("week".equals(type))
        {
            this.categoryId = data[0].trim()+"13";
            this.cateName = data[1].trim() + "周榜";
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
