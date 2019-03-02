package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

public class GAppSoftWare extends GAppContent 
{
	/**
     * ��Դ���ͣ�ҵ�����ݣ�Ӧ���������
     */
    public static final String TYPE_APPSOFTWARE = "nt:gcontent:appSoftWare";
    
    /**
     * ���췽��
     */
    public GAppSoftWare()
    {
       this.type=TYPE_APPSOFTWARE;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GAppSoftWare(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_APPSOFTWARE;
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
     * ��ȡʹ���ֲ���ļ���ַ
     * @return Returns the manual.
     */
    public String getManual()
    {
        return getNoNullString((String) this.getProperty("manual").getValue());
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
     * ����ʹ���ֲ���ļ���ַ 
     * @param manual
     */
    public void setManual(String manual)
    {
        Property pro = new Property("manual", manual);
        this.setProperty(pro);
    }
    


    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GSoftware[");
        sb.append(this.getProperty("detailDescURL"));
        sb.append(',');
        sb.append(this.getProperty("contentSize"));        
        sb.append(',');
        sb.append(this.getProperty("mobileURL"));
        sb.append(',');
        sb.append(this.getProperty("deviceName"));
        sb.append(',');
        sb.append(this.getProperty("terminalSupport"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }

    
    

}
