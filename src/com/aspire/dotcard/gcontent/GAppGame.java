package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>��Դ���е���Ϸ�ڵ���</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author ����
 */
public class GAppGame extends GAppContent
{
    /**
     * ��Դ���ͣ�ҵ�����ݣ�Ӧ����Ϸ����
     */
    public static final String TYPE_APPGAME = "nt:gcontent:appGame";
    
    /**
     * ���췽��
     */   
    public GAppGame()
    {
        this.type = TYPE_APPGAME;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GAppGame(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_APPGAME;
    }
    
    /**
     * ��ȡ��Ϸ��Ƶ��ַ
     * @return Returns the gameVideo.
     */
    public String getGameVideo()
    {
        return getNoNullString((String) this.getProperty("gameVideo").getValue());
    }

    
    /**
     * ������Ϸ��Ƶ��ַ
     * @param gameVideo The gameVideo to set.
     */
    public void setGameVideo(String gameVideo)
    {
        Property pro = new Property("gameVideo", gameVideo);
        this.setProperty(pro);
    }
 
    /**
     * ��ȡ����ָ��
     * @return Returns the handBook.
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * ���ò���ָ�� 
     * @param handBook
     */
    public void setHandBook(String handBook)
    {
        Property pro = new Property("handBook", handBook);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����ָ��ͼƬ��ַ
     * @return Returns the handBookPicture.
     */
    public String getHandBookPicture()
    {
        return getNoNullString((String) this.getProperty("handBookPicture").getValue());
    }


    /**
     * ���ò���ָ��ͼƬ��ַ
     * @param handBookPicture
     */
    public void setHandBookPicture(String handBookPicture)
    {
        Property pro = new Property("handBookPicture", handBookPicture);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����˵��
     * @return Returns the userGuide.
     */
    public String getUserGuide()
    {
        return getNoNullString((String) this.getProperty("userGuide").getValue());
    }


    /**
     * ���ù���˵��
     * @param userGuide
     */
    public void setUserGuide(String userGuide)
    {
        Property pro = new Property("userGuide", userGuide);
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ����˵��ͼƬ��ַ
     * @return Returns the userGuidePicture.
     */
    public String getUserGuidePicture()
    {
        return getNoNullString((String) this.getProperty("userGuidePicture").getValue());
    }


    /**
     * ���ù���˵��ͼƬ��ַ
     * @param userGuidePicture
     */
    public void setUserGuidePicture(String userGuidePicture)
    {
        Property pro = new Property("userGuidePicture", userGuidePicture);
        this.setProperty(pro);
    }
	/**
	 * ����Ӧ���ۿۼ۸�
	 */
	public void setExpPrice(int expPrice) {
		Property pro = new Property("expPrice", new Integer(expPrice));
		this.setProperty(pro);
	}

	/**
	 * ��ȡӦ�� �ۿۼ۸�
	 * 
	 * @return
	 */
	public int getExpPrice() {
		return ((Integer) this.getProperty("expPrice").getValue()).intValue();
	}
	
	
	//add by aiyan 2012-11-10
	/**
	 * ���ڱ�ʾ��Ϸ����ҵ���Ƿ������ ���������ݿ������bigcatename
0������
1��������

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
