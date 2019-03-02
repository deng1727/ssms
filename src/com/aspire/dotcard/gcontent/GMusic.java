package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * ��Դ���еĻ������ֽڵ���
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class GMusic extends GContent
{
    /**
     * ��Դ���ͣ��������ͣ�����
     */
    public static final String TYPE_MUSIC = "nt:gcontent:music";

    /**
     * ���췽��
     */
    public GMusic()
    {
        this.type = TYPE_MUSIC;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GMusic(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_MUSIC;
    }

    /**
     * ��ȡ������
     * @return Returns the singer.
     */
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    /**
     * ���ø�����
     * @param singer The singer to set.
     */
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }
    
    /**
     * ���� ��������ר������
     * @param value�� ��������ר������
     */
    public void setAlbum(String album)
    {
        Property pro = new Property("album", album);
        this.setProperty(pro);
    }

    /**
     * ��ȡ ��������ר������
     * @return�� ��������ר������
     */
    public String getAlbum()
    {
        return getNoNullString((String) this.getProperty("album").getValue());
    }
    
    /**
     * ������ͣ�1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ��֡�
     * @return Returns the ChangeType.
     */
    public String getChangeType()
    {
        return getNoNullString((String) this.getProperty("changeType").getValue());
    }


    /**
     * ������ͣ�1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ��֡�
     * @param changetype
     */
    public void setChangeType(String changetype)
    {
        Property pro = new Property("changeType", changetype);
        this.setProperty(pro);
    }

}
