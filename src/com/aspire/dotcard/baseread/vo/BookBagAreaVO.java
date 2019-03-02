package com.aspire.dotcard.baseread.vo;

import java.util.Map;

public class BookBagAreaVO
{	
    /**
     * ������
     */
    private String bookBagId;

    /**
     * �����
     */
    private String bookBagName;

    /**
     * �����ʾ��
     */
    private String viewBagName;
    
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

	public String getBookBagId()
	{
		return bookBagId;
	}

	public void setBookBagId(String bookBagId)
	{
		this.bookBagId = bookBagId;
	}

	public String getBookBagName()
	{
		return bookBagName;
	}

	public void setBookBagName(String bookBagName)
	{
		this.bookBagName = bookBagName;
	}

	public String getViewBagName()
	{
		return viewBagName;
	}

	public void setViewBagName(String viewBagName)
	{
		this.viewBagName = viewBagName;
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

	public int getChangeType()
	{
		return changeType;
	}

	public void setChangeType(int changeType)
	{
		this.changeType = changeType;
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
        if (data.length != 5)
        {
            return false;
        }
        if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
				|| "".equals(data[1].trim()) || null == data[2]
				|| "".equals(data[2].trim()) || null == data[3]
				|| "".equals(data[3].trim()) || null == data[4]
				|| "".equals(data[4].trim()))
		{
			return false;
		}
        this.bookBagId = data[0].trim();
        this.bookBagName = data[1].trim();
        this.viewBagName = data[2].trim();
        
        String pro = this.getMMProvinceCity(data[3].trim(), provinceMap, "province", errorArea);
        if("".equals(pro))
        {
        	errorArea = "��ǰ���ID:" + bookBagId + "������ϢΪ�գ�" + errorArea;
        	return false;
        }
        this.province = pro;
        
        String cit =  this.getMMProvinceCity(data[4].trim(), cityMap, "city", errorArea);
        if("".equals(cit))
        {
        	errorArea = "��ǰ���ID:" + bookBagId + "������ϢΪ�գ�" + errorArea;
        	return false;
        }
        this.city = cit;
        
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
    	
    	if(proCityStr.length() > 0)
    	{
    		String temp = ";" + proCityStr.toString();
        	return temp;
    	}
    	else
    	{
    		return proCityStr.toString();
    	}
    }
    
}
