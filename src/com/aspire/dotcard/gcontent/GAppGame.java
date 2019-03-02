package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>资源树中的游戏节点类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author 张敏
 */
public class GAppGame extends GAppContent
{
    /**
     * 资源类型：业务内容，应用游戏类型
     */
    public static final String TYPE_APPGAME = "nt:gcontent:appGame";
    
    /**
     * 构造方法
     */   
    public GAppGame()
    {
        this.type = TYPE_APPGAME;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GAppGame(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_APPGAME;
    }
    
    /**
     * 获取游戏视频地址
     * @return Returns the gameVideo.
     */
    public String getGameVideo()
    {
        return getNoNullString((String) this.getProperty("gameVideo").getValue());
    }

    
    /**
     * 设置游戏视频地址
     * @param gameVideo The gameVideo to set.
     */
    public void setGameVideo(String gameVideo)
    {
        Property pro = new Property("gameVideo", gameVideo);
        this.setProperty(pro);
    }
 
    /**
     * 获取操作指南
     * @return Returns the handBook.
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * 设置操作指南 
     * @param handBook
     */
    public void setHandBook(String handBook)
    {
        Property pro = new Property("handBook", handBook);
        this.setProperty(pro);
    }
    
    /**
     * 获取操作指南图片地址
     * @return Returns the handBookPicture.
     */
    public String getHandBookPicture()
    {
        return getNoNullString((String) this.getProperty("handBookPicture").getValue());
    }


    /**
     * 设置操作指南图片地址
     * @param handBookPicture
     */
    public void setHandBookPicture(String handBookPicture)
    {
        Property pro = new Property("handBookPicture", handBookPicture);
        this.setProperty(pro);
    }
    
    /**
     * 获取功能说明
     * @return Returns the userGuide.
     */
    public String getUserGuide()
    {
        return getNoNullString((String) this.getProperty("userGuide").getValue());
    }


    /**
     * 设置功能说明
     * @param userGuide
     */
    public void setUserGuide(String userGuide)
    {
        Property pro = new Property("userGuide", userGuide);
        this.setProperty(pro);
    }
    
    /**
     * 获取攻略说明图片地址
     * @return Returns the userGuidePicture.
     */
    public String getUserGuidePicture()
    {
        return getNoNullString((String) this.getProperty("userGuidePicture").getValue());
    }


    /**
     * 设置攻略说明图片地址
     * @param userGuidePicture
     */
    public void setUserGuidePicture(String userGuidePicture)
    {
        Property pro = new Property("userGuidePicture", userGuidePicture);
        this.setProperty(pro);
    }
	/**
	 * 设置应用折扣价格
	 */
	public void setExpPrice(int expPrice) {
		Property pro = new Property("expPrice", new Integer(expPrice));
		this.setProperty(pro);
	}

	/**
	 * 获取应用 折扣价格
	 * 
	 * @return
	 */
	public int getExpPrice() {
		return ((Integer) this.getProperty("expPrice").getValue()).intValue();
	}
	
	
	//add by aiyan 2012-11-10
	/**
	 * 用于表示游戏包内业务是否计数。 复用了数据库里面的bigcatename
0：计数
1：不计数

	 */
	public void setCountFlag(String countFlag) {
        Property pro = new Property("countFlag", countFlag);
        this.setProperty(pro);;
	}
    public String getCountFlag()
    {
        return getNoNullString((String) this.getProperty("countFlag").getValue());
    }

	
	

	
}
