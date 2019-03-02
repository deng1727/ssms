package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;


public class GAppTheme extends GAppContent 
{
	/**
     * ��Դ���ͣ�ҵ�����ݣ�Ӧ���������
     */
    public static final String TYPE_APPTHEME = "nt:gcontent:appTheme";
    
    /**
     * ���췽��
     */
    public GAppTheme()
    {
       this.type=TYPE_APPTHEME;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GAppTheme(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_APPTHEME;
    }
    /**
     * ��ȡ����ָ��
     * @return Returns the handBook.  ���洴ҵ���������߸�У��Ϣ  add by dongke 2011
     */
    public String getHandBook()
    {
        return getNoNullString((String) this.getProperty("handBook").getValue());
    }


    /**
     * ���ò���ָ�� 
     * @param handBook  ���洴ҵ���������߸�У��Ϣ  add by dongke 2011
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
}
