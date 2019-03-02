package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * ��Դ���е���Ƶ�ڵ���
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class GVideo extends GContent
{
    /**
     * ��Դ���ͣ�ҵ�����ݣ���Ƶ����
     */
    public static final String TYPE_VIDEO = "nt:gcontent:video";
    
    /**
     * ���췽��
     */
    public GVideo()
    {
        this.type = TYPE_VIDEO;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GVideo(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_VIDEO;
    }
    
    /**
     * ��Ʒ��pkgURL
     * @return Returns the pkgURL.
     */
    public String getPkgURL()
    {
        return getNoNullString((String) this.getProperty("pkgURL").getValue());
    }


    /**
     * ��Ʒ��pkgURL
     * @param pkgURL
     */
    public void setPkgURL(String pkgURL)
    {
        Property pro = new Property("pkgURL", pkgURL);
        this.setProperty(pro);
    }
    
    
    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl1����С��30x30 
     * @return Returns the pkgPICURL1.
     */
    public String getPkgPICURL1()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL1").getValue());
    }


    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl1����С��30x30
     * @param pkgPICURL1
     */
    public void setPkgPICURL1(String pkgPICURL1)
    {
        Property pro = new Property("pkgPICURL1", pkgPICURL1);
        this.setProperty(pro);
    }
    
    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl2����С��34x34 
     * @return Returns the pkgPICURL2.
     */
    public String getPkgPICURL2()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL2").getValue());
    }


    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl2����С��34x34
     * @param pkgPICURL2
     */
    public void setPkgPICURL2(String pkgPICURL2)
    {
        Property pro = new Property("pkgPICURL2", pkgPICURL2);
        this.setProperty(pro);
    }
    
    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl3����С��50x50 
     * @return Returns the pkgPICURL3.
     */
    public String getPkgPICURL3()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL3").getValue());
    }


    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl3����С��50x50
     * @param pkgPICURL3
     */
    public void setPkgPICURL3(String pkgPICURL3)
    {
        Property pro = new Property("pkgPICURL3", pkgPICURL3);
        this.setProperty(pro);
    }
    
    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl4����С��65x65 
     * @return Returns the pkgPICURL4.
     */
    public String getPkgPICURL4()
    {
        return getNoNullString((String) this.getProperty("pkgPICURL4").getValue());
    }


    /**
     * ��Ʒ��ID��Ʒ��ͼƬurl4����С��30x30
     * @param pkgPICURL4
     */
    public void setPkgPICURL4(String pkgPICURL4)
    {
        Property pro = new Property("pkgPICURL4", pkgPICURL4);
        this.setProperty(pro);
    }
    
    /**
     * ���ò�Ʒ����λ 
     */
    public void setSortnumber(int sortnumber)
    {
        Property pro = new Property("sortnumber", new Integer(sortnumber));
        this.setProperty(pro);
    }
    
    /**
     * ��ȡ��Ʒ����λ 
     * @return
     */
    public int getSortnumber()
    {
        return (( Integer ) this.getProperty("sortnumber").getValue()).intValue();
    }
    
    /**
     * ������ͣ�1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ��֡�
     * @return Returns the Changetype.
     */
    public String getChangetype()
    {
        return getNoNullString((String) this.getProperty("changetype").getValue());
    }


    /**
     * ������ͣ�1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ��֡�
     * @param changetype
     */
    public void setChangetype(String changetype)
    {
        Property pro = new Property("changetype", changetype);
        this.setProperty(pro);
    }
    
    /**
     * toString����
     * @return ������Ϣ
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("GVideo[");
        sb.append(this.getProperty("pkgPICURL1"));
        sb.append(',');
        sb.append(this.getProperty("pkgPICURL2"));
        sb.append(',');
        sb.append(this.getProperty("pkgPICURL3"));
        sb.append(',');
        sb.append(this.getProperty("pkgPICURL4"));
        sb.append(',');
        sb.append(this.getProperty("sortnumber"));
        sb.append(',');
        sb.append(this.getProperty("changetype"));
        sb.append(',');
        sb.append(super.toString());
        sb.append("]");
        return sb.toString();
    }
}
