package com.aspire.dotcard.hwcolorring.clrLoad;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>��Դ���еĲ���ڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author bihui
 */

public class GColorring 
{
    /**
     * ��Դ���ͣ��������ͣ�����
     */
    public static final String TYPE_COLORRING = "nt:gcontent:colorring";

    /**
     * ���췽��
     */
    public GColorring()
    {
      //  this.type = TYPE_COLORRING;
    }
	/**
     * ���췽��
     * @param nodeID����Դid
     */
    public GColorring(String nodeID)
    {
       // super(nodeID);
      //  this.type = TYPE_COLORRING;
    }
    /**
     *   600902000001964554
     	|�������(������Ͱ�)
     	|K
     	|Dj Sunny
     	|D
     	|
     	|200
     	|20080715
     	|1788
     	|4089
     	|http://12530.gmcc.net:8080/colorring/al/600/902/0/0000/1964/554.wav
     	|
     	|
     	|20110620
     */
    
    //����ID
    private String id;
    //��������  
    private String name;
    //������������ĸ
    private String tonenameletter;
    //�������
    private String singer;
    //�����������ĸ
    private String singerletter;
    //����˵������
    private String introduction;
    //����۸�
    private String price;
    //����������ʱ��
    private String lupdDate;
    //�������ش���
    private int downloadtimes;
    //�������ô���
    private int settimes;
    //��������URL
    private String auditionUrl;
    //��������
    private String tonebigtype;
    //������������
    private String cateName;
    //������Ч��
    private String expire;
    
   //-------------------------
    
    //����ʱ��
    private String createDate;
    //����ʱ��
    private String marketDate;
   
    //���岥��ʱ��
    private int averageMark;
//  ����ID
    private String contentID;
    //�ͻ�������URL
    private String clientAuditionUrl;  
  
    
    public void save() throws BOException {
    	try
		{
			ColorContentDAO.getInstance().saveColorring(this);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("���屣�����ݲ���ʧ�ܣ�",e);
		}
    	
    	//return 0;
    }
    
    /**
	 * @return Returns the auditionUrl.
	 */
	public String getAuditionUrl()
	{
		return getNoNullString(auditionUrl);
	}


	/**
	 * @param auditionUrl The auditionUrl to set.
	 */
	public void setAuditionUrl(String auditionUrl)
	{
		this.auditionUrl = auditionUrl;
	}





	/**
	 * @return Returns the averageMark.
	 */
	public int getAverageMark()
	{
		return averageMark;
	}





	/**
	 * @param averageMark The averageMark to set.
	 */
	public void setAverageMark(int averageMark)
	{
		this.averageMark = averageMark;
	}





	/**
	 * @return Returns the contentID.
	 */
	public String getContentID()
	{
		return contentID;
	}
	/**
	 * @param contentID The contentID to set.
	 */
	public void setContentID(String contentID)
	{
		this.contentID = contentID;
	}
	/**
	 * @return Returns the cateName.
	 */
	public String getCateName()
	{
		return getNoNullString(cateName);
	}





	/**
	 * @param cateName The cateName to set.
	 */
	public void setCateName(String cateName)
	{
		this.cateName = cateName;
	}





	/**
	 * @return Returns the clientAuditionUrl.
	 */
	public String getClientAuditionUrl()
	{
		return getNoNullString(clientAuditionUrl);
	}





	/**
	 * @param clientAuditionUrl The clientAuditionUrl to set.
	 */
	public void setClientAuditionUrl(String clientAuditionUrl)
	{
		this.clientAuditionUrl = clientAuditionUrl;
	}





	/**
	 * @return Returns the createdate.
	 */
	public String getCreateDate()
	{
		return getNoNullString(createDate);
	}





	/**
	 * @param createdate The createdate to set.
	 */
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}



	/**
	 * @return Returns the downloadtimes.
	 */
	public int getDownloadtimes()
	{
		return downloadtimes;
	}
	/**
	 * @param downloadtimes The downloadtimes to set.
	 */
	public void setDownloadtimes(int downloadtimes)
	{
		this.downloadtimes = downloadtimes;
	}
	/**
	 * @return Returns the expire.
	 */
	public String getExpire()
	{
		return getNoNullString(expire);
	}





	/**
	 * @param expire The expire to set.
	 */
	public void setExpire(String expire)
	{
		this.expire = expire;
	}





	/**
	 * @return Returns the id.
	 */
	public String getId()
	{
		return getNoNullString(id);
	}





	/**
	 * @param id The id to set.
	 */
	public void setId(String id)
	{
		this.id = id;
	}





	/**
	 * @return Returns the introduction.
	 */
	public String getIntroduction()
	{
		return   getNoNullString(introduction);
	}





	/**
	 * @param introduction The introduction to set.
	 */
	public void setIntroduction(String introduction)
	{
		this.introduction = introduction;
	}





	/**
	 * @return Returns the lupddate.
	 */
	public String getLupdDate()
	{
		return getNoNullString(lupdDate);
	}





	/**
	 * @param lupddate The lupddate to set.
	 */
	public void setLupdDate(String lupdDate)
	{
		this.lupdDate = lupdDate;
	}





	/**
	 * @return Returns the marketdate.
	 */
	public String getMarketDate()
	{
		return getNoNullString(marketDate);
	}





	/**
	 * @param marketdate The marketdate to set.
	 */
	public void setMarketDate(String marketDate)
	{
		this.marketDate = marketDate;
	}





	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return getNoNullString(name);
	}





	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}





	/**
	 * @return Returns the price.
	 */
	public String getPrice()
	{
		String price = getNoNullString((String) this.price);
        if(price==null || "".equals(price))
        {
            return "0";
        }
		return price;
	}





	/**
	 * @param price The price to set.
	 */
	public void setPrice(String price)
	{
		this.price = price;
	}



	/**
	 * @return Returns the settimes.
	 */
	public int getSettimes()
	{
		return settimes;
	}
	/**
	 * @param settimes The settimes to set.
	 */
	public void setSettimes(int settimes)
	{
		this.settimes = settimes;
	}
	/**
	 * @return Returns the singer.
	 */
	public String getSinger()
	{
		return getNoNullString(singer);
	}





	/**
	 * @param singer The singer to set.
	 */
	public void setSinger(String singer)
	{
		this.singer = singer;
	}





	/**
	 * @return Returns the singerletter.
	 */
	public String getSingerletter()
	{
		return getNoNullString(singerletter);
	}





	/**
	 * @param singerletter The singerletter to set.
	 */
	public void setSingerletter(String singerletter)
	{
		this.singerletter = singerletter;
	}





	/**
	 * @return Returns the tonebigtype.
	 */
	public String getTonebigtype()
	{
		return getNoNullString(tonebigtype);
	}





	/**
	 * @param tonebigtype The tonebigtype to set.
	 */
	public void setTonebigtype(String tonebigtype)
	{
		this.tonebigtype = tonebigtype;
	}





	/**
	 * @return Returns the tonenameletter.
	 */
	public String getTonenameletter()
	{
		return getNoNullString(tonenameletter);
	}





	/**
	 * @param tonenameletter The tonenameletter to set.
	 */
	public void setTonenameletter(String tonenameletter)
	{
		this.tonenameletter = tonenameletter;
	}



	  /**
     * �õ���Ϊnull��String����
     * @param name
     * @return
     */
    public static String getNoNullString(String name)
    {
        return name == null ? "" : name;
    }

    /**
    * ��ȡ���ݵ���Ч���ڵ���ʾ
    * @return String ���ݵ���Ч���ڵ���ʾ
    */
   public String getDisplayExpire()
   {
       try
       {
           String createTime = this.getExpire();
           String display = createTime.substring(0, 4) + '-' +
               createTime.substring(4, 6) + '-'
               + createTime.substring(6, 8);
           return display;
       }
       catch(Exception e)
       {
           return "";
       }
   }

   /**
    * ��ȡ������ʷ���Ϣ��Ԫ
    * @return
    */
   public float getPrice_Y()
   {
       return (Float.parseFloat(this.getPrice())/100);
   }

    /**
     * �������ݵ���Ч����
     * @param value�����ݵ���Ч����
     */
   /* public void setExpire(String value)
    {
        Property pro = new Property("expire", value);
        this.setProperty(pro);
    }

    *//**
     * ��ȡ���ݵ���Ч����
     * @return�����ݵ���Ч����
     *//*
    public String getExpire()
    {
        return getNoNullString((String) this.getProperty("expire").getValue());
    }

    

    /**
     * ��ȡ�۸��Է�Ϊ��λ
     * @return Returns the price.
     *//*
    public String getPrice()
    {
        String price = getNoNullString((String) this.getProperty("price").getValue());
        if(price==null || "".equals(price))
        {
            return "0";
        }
        return price;
    }


    *//**
     * ���ü۸�
     * @param price The price to set.
     *//*
    public void setPrice(String price)
    {
        Property pro = new Property("price", price);
        this.setProperty(pro);
    }


    *//**
     * ��ȡ������
     * @return Returns the singer.
     *//*
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    *//**
     * ���ø�����
     * @param singer The singer to set.
     *//*
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }

    /**
     * �����������Ƽ�������ĸ
     * @param value���������Ƽ�������ĸ
     *//*
    public void setTonenameletter(String value)
    {
        Property pro = new Property("tonenameletter", value);
        this.setProperty(pro);
    }

    *//**
     * ��ȡ�������Ƽ�������ĸ
     * @return���������Ƽ�������ĸ
     *//*
    public String getTonenameletter()
    {
        return getNoNullString((String) this.getProperty("tonenameletter").getValue());
    }

    *//**
     * �������Ƽ�������ĸ
     * @param value���������Ƽ�������ĸ
     *//*
    public void setSingerletter(String value)
    {
        Property pro = new Property("singerletter", value);
        this.setProperty(pro);
    }

    *//**
     * ��ȡ�������Ƽ�������ĸ
     * @return���������Ƽ�������ĸ
     *//*
    public String getSingerletter()
    {
        return getNoNullString((String) this.getProperty("singerletter").getValue());
    }

    *//**
     * ���ø��������صĴ���
     * @param value�����������صĴ���
     *//*
    public void setDownloadtimes(int value)
    {
        Property pro = new Property("downloadtimes", new Integer(value));
        this.setProperty(pro);
    }

    *//**
     * ��ȡ���������صĴ���
     * @return�����������صĴ���
     *//*
    public int getDownloadtimes()
    {
        return ((Integer) this.getProperty("downloadtimes").getValue()).intValue();
    }

    *//**
     * ���ø��������ô���
     * @param value�����������ô���
     *//*
    public void setSettimes(int value)
    {
        Property pro = new Property("settimes", new Integer(value));
        this.setProperty(pro);
    }

    *//**
     * ��ȡ���������ô���
     * @return�����������ô���
     *//*
    public int getSettimes()
    {
        return ((Integer) this.getProperty("settimes").getValue()).intValue();
    }

    *//**
     * ������������
     * @param value����������
     *//*
    public void setTonebigtype(String value)
    {
        Property pro = new Property("tonebigtype", value);
        this.setProperty(pro);
    }

    *//**
     * ��ȡ��������
     * @return����������
     *//*
    public String getTonebigtype()
    {
        return getNoNullString((String) this.getProperty("tonebigtype").getValue());
    }
    
    *//**
     * ��ȡ������ַ
     * @return Returns the auditionUrl.
     *//*
    public String getAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("auditionUrl").getValue());
    }


    *//**
     * ����������ַ
     * @param auditionUrl The auditionUrl to set.
     *//*
    public void setAuditionUrl(String auditionUrl)
    {
        Property pro = new Property("auditionUrl", auditionUrl);
        this.setProperty(pro);
    }
    
    *//**
     * ��ȡ�ն�������ַ
     * @return Returns the clientAuditionUrl.
     *//*
    public String getClientAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("clientAuditionUrl").getValue());
    }


    *//**
     * �����ն�������ַ
     * @param clientAuditionUrl The clientAuditionUrl to set.
     *//*
    public void setClientAuditionUrl(String clientAuditionUrl)
    {
        Property pro = new Property("clientAuditionUrl", clientAuditionUrl);
        this.setProperty(pro);
    }*/

}
