package com.aspire.dotcard.hwcolorring.clrLoad;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>资源树中的彩铃节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author bihui
 */

public class GColorring 
{
    /**
     * 资源类型：内容类型，彩铃
     */
    public static final String TYPE_COLORRING = "nt:gcontent:colorring";

    /**
     * 构造方法
     */
    public GColorring()
    {
      //  this.type = TYPE_COLORRING;
    }
	/**
     * 构造方法
     * @param nodeID，资源id
     */
    public GColorring(String nodeID)
    {
       // super(nodeID);
      //  this.type = TYPE_COLORRING;
    }
    /**
     *   600902000001964554
     	|康电情歌(温情放送版)
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
    
    //彩铃ID
    private String id;
    //彩铃名称  
    private String name;
    //彩铃名称首字母
    private String tonenameletter;
    //彩铃歌手
    private String singer;
    //彩铃歌手首字母
    private String singerletter;
    //彩铃说明描述
    private String introduction;
    //彩铃价格
    private String price;
    //彩铃最后更新时间
    private String lupdDate;
    //彩铃下载次数
    private int downloadtimes;
    //彩铃设置次数
    private int settimes;
    //彩铃音乐URL
    private String auditionUrl;
    //铃音大类
    private String tonebigtype;
    //铃音二级分类
    private String cateName;
    //彩铃有效期
    private String expire;
    
   //-------------------------
    
    //创建时间
    private String createDate;
    //上线时间
    private String marketDate;
   
    //彩铃播放时长
    private int averageMark;
//  内容ID
    private String contentID;
    //客户端试听URL
    private String clientAuditionUrl;  
  
    
    public void save() throws BOException {
    	try
		{
			ColorContentDAO.getInstance().saveColorring(this);
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BOException("彩铃保存数据操作失败！",e);
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
     * 得到不为null的String对象
     * @param name
     * @return
     */
    public static String getNoNullString(String name)
    {
        return name == null ? "" : name;
    }

    /**
    * 获取内容的有效日期的显示
    * @return String 内容的有效日期的显示
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
    * 获取彩铃的资费信息：元
    * @return
    */
   public float getPrice_Y()
   {
       return (Float.parseFloat(this.getPrice())/100);
   }

    /**
     * 设置内容的有效日期
     * @param value，内容的有效日期
     */
   /* public void setExpire(String value)
    {
        Property pro = new Property("expire", value);
        this.setProperty(pro);
    }

    *//**
     * 获取内容的有效日期
     * @return，内容的有效日期
     *//*
    public String getExpire()
    {
        return getNoNullString((String) this.getProperty("expire").getValue());
    }

    

    /**
     * 获取价格，以分为单位
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
     * 设置价格
     * @param price The price to set.
     *//*
    public void setPrice(String price)
    {
        Property pro = new Property("price", price);
        this.setProperty(pro);
    }


    *//**
     * 获取歌手名
     * @return Returns the singer.
     *//*
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    *//**
     * 设置歌手名
     * @param singer The singer to set.
     *//*
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }

    /**
     * 设置铃音名称检索首字母
     * @param value，铃音名称检索首字母
     *//*
    public void setTonenameletter(String value)
    {
        Property pro = new Property("tonenameletter", value);
        this.setProperty(pro);
    }

    *//**
     * 获取铃音名称检索首字母
     * @return，铃音名称检索首字母
     *//*
    public String getTonenameletter()
    {
        return getNoNullString((String) this.getProperty("tonenameletter").getValue());
    }

    *//**
     * 歌手名称检索首字母
     * @param value，歌手名称检索首字母
     *//*
    public void setSingerletter(String value)
    {
        Property pro = new Property("singerletter", value);
        this.setProperty(pro);
    }

    *//**
     * 获取歌手名称检索首字母
     * @return，歌手名称检索首字母
     *//*
    public String getSingerletter()
    {
        return getNoNullString((String) this.getProperty("singerletter").getValue());
    }

    *//**
     * 设置该铃音下载的次数
     * @param value，该铃音下载的次数
     *//*
    public void setDownloadtimes(int value)
    {
        Property pro = new Property("downloadtimes", new Integer(value));
        this.setProperty(pro);
    }

    *//**
     * 获取该铃音下载的次数
     * @return，该铃音下载的次数
     *//*
    public int getDownloadtimes()
    {
        return ((Integer) this.getProperty("downloadtimes").getValue()).intValue();
    }

    *//**
     * 设置该铃音设置次数
     * @param value，该铃音设置次数
     *//*
    public void setSettimes(int value)
    {
        Property pro = new Property("settimes", new Integer(value));
        this.setProperty(pro);
    }

    *//**
     * 获取该铃音设置次数
     * @return，该铃音设置次数
     *//*
    public int getSettimes()
    {
        return ((Integer) this.getProperty("settimes").getValue()).intValue();
    }

    *//**
     * 设置铃音大类
     * @param value，铃音大类
     *//*
    public void setTonebigtype(String value)
    {
        Property pro = new Property("tonebigtype", value);
        this.setProperty(pro);
    }

    *//**
     * 获取铃音大类
     * @return，铃音大类
     *//*
    public String getTonebigtype()
    {
        return getNoNullString((String) this.getProperty("tonebigtype").getValue());
    }
    
    *//**
     * 获取试听地址
     * @return Returns the auditionUrl.
     *//*
    public String getAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("auditionUrl").getValue());
    }


    *//**
     * 设置试听地址
     * @param auditionUrl The auditionUrl to set.
     *//*
    public void setAuditionUrl(String auditionUrl)
    {
        Property pro = new Property("auditionUrl", auditionUrl);
        this.setProperty(pro);
    }
    
    *//**
     * 获取终端试听地址
     * @return Returns the clientAuditionUrl.
     *//*
    public String getClientAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("clientAuditionUrl").getValue());
    }


    *//**
     * 设置终端试听地址
     * @param clientAuditionUrl The clientAuditionUrl to set.
     *//*
    public void setClientAuditionUrl(String clientAuditionUrl)
    {
        Property pro = new Property("clientAuditionUrl", clientAuditionUrl);
        this.setProperty(pro);
    }*/

}
