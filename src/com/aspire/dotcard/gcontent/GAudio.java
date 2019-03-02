package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * ��Դ���е�A8ȫ���ڵ���
 * </p>
 * <p>
 * Copyright (c) 2008 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class GAudio extends GContent
{
    /**
     * ��Դ���ͣ��������ͣ�����
     */
    public static final String TYPE_AUDIO = "nt:gcontent:audio";

    /**
     * ���췽��
     */
    public GAudio()
    {
        this.type = TYPE_AUDIO;
    }

    /**
     * ���췽��
     * @param nodeID����Դid
     */
    public GAudio(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_AUDIO;
    }

    /**
     * ��ȡ�������Է���
     * @return Returns the audioLanguage.
     */
    public String getAudioLanguage()
    {
        return getNoNullString((String) this.getProperty("audioLanguage").getValue());
    }


    /**
     * ���ø������Է���
     * @param audioLanguage The audioLanguage to set.
     */
    public void setAudioLanguage(String audioLanguage)
    {
        Property pro = new Property("audioLanguage", audioLanguage);
        this.setProperty(pro);
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
     * ��ȡaac����HTTP URL
     * @return Returns the aacAuditionUrl.
     */
    public String getAacAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("aacAuditionUrl").getValue());
    }

    /**
     * ����aac����HTTP URL
     * @param aacAuditionUrl The aacAuditionUrl to set.
     */
    public void setAacAuditionUrl(String aacAuditionUrl)
    {
        Property pro = new Property("aacAuditionUrl", aacAuditionUrl);
        this.setProperty(pro);
    }

    /**
     * ���� �������ֵ�������
     * @param value�� �������ֵ�������
     */
    public void setSingerZone(String singerZone)
    {
        Property pro = new Property("singerZone", singerZone);
        this.setProperty(pro);
    }

    /**
     * ��ȡ �������ֵ�������
     * @return�� �������ֵ�������
     */
    public String getSingerZone()
    {
        return getNoNullString((String) this.getProperty("singerZone").getValue());
    }
    
    /**
     * ���� ���HTTP URL
     * @param value�� ���HTTP URL
     */
    public void setLrcURL(String lrcURL)
    {
        Property pro = new Property("lrcURL", lrcURL);
        this.setProperty(pro);
    }

    /**
     * ��ȡ ���HTTP URL
     * @return�� ���HTTP URL
     */
    public String getLrcURL()
    {
        return getNoNullString((String) this.getProperty("lrcURL").getValue());
    }
      
    /**
     * ��ȡaac����HTTP URL
     * @return Returns the mp3AuditionUrl.
     */
    public String getMp3AuditionUrl()
    {
        return getNoNullString((String)this.getProperty("mp3AuditionUrl").getValue());
    }

    /**
     * ����aac����HTTP URL
     * @param mp3AuditionUrl The mp3AuditionUrl to set.
     */
    public void setMp3AuditionUrl(String mp3AuditionUrl)
    {
        Property pro = new Property("mp3AuditionUrl", mp3AuditionUrl);
        this.setProperty(pro);
    }
    
    /**
     * ���� ��������ר������
     * @param value�� ��������ר������
     */
    public void setSpecial(String special)
    {
        Property pro = new Property("special", special);
        this.setProperty(pro);
    }

    /**
     * ��ȡ ��������ר������
     * @return�� ��������ר������
     */
    public String getSpecial()
    {
        return getNoNullString((String) this.getProperty("special").getValue());
    }
    
    /**
     * ���� ��������ר��ͼƬurl
     * @param value����������ר��ͼƬurl
     */
    public void setSpecialURL(String specialURL)
    {
        Property pro = new Property("specialURL", specialURL);
        this.setProperty(pro);
    }

    /**
     * ��ȡ ��������ר��ͼƬurl
     * @return�� ��������ר��ͼƬurl
     */
    public String getSpecialURL()
    {
        return getNoNullString((String) this.getProperty("specialURL").getValue());
    }
    
    /**
     * ��ȡK���aac����HTTP URL
     * @return Returns the aacAuditionUrl.
     */
    public String getKAacAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("KAacAuditionUrl").getValue());
    }

    /**
     * ����aac����HTTP URL
     * @param aacAuditionUrl The aacAuditionUrl to set.
     */
    public void setKAacAuditionUrl(String kAacAuditionUrl)
    {
        Property pro = new Property("KAacAuditionUrl", kAacAuditionUrl);
        this.setProperty(pro);
    }
    /**
     * ��ȡk�� aac����HTTP URL
     * @return Returns the mp3AuditionUrl.
     */
    public String getKMp3AuditionUrl()
    {
        return getNoNullString((String)this.getProperty("KMp3AuditionUrl").getValue());
    }

    /**
     * ����k��aac����HTTP URL
     * @param mp3AuditionUrl The mp3AuditionUrl to set.
     */
    public void setKMp3AuditionUrl(String kMp3AuditionUrl)
    {
        Property pro = new Property("KMp3AuditionUrl", kMp3AuditionUrl);
        this.setProperty(pro);
    }
    /**
     * ��ȡ	K���������ʱ��
     * @return Returns the mp3AuditionUrl.
     */
    public int getAudioLength()
    {
        //return ((Integer)(this.getProperty("audioLength"))).intValue();
        return (( Integer ) this.getProperty("audioLength").getValue()).intValue();
    }

    /**
     * ���� K���������ʱ��
     * @param mp3AuditionUrl The mp3AuditionUrl to set.
     */
    public void setAudioLength(int audioLength)
    {
        Property pro = new Property("audioLength", new Integer(audioLength));
        this.setProperty(pro);
    }
    /**
     * ���� K�� ���HTTP URL
     * @param value�� ���HTTP URL
     */
    public void setKLrcURL(String kLrcURL)
    {
        Property pro = new Property("KLrcURL", kLrcURL);
        this.setProperty(pro);
    }

    /**
     * ��ȡK�� ���HTTP URL
     * @return�� K�� ���HTTP URL
     */
    public String getKLrcURL()
    {
        return getNoNullString((String) this.getProperty("KLrcURL").getValue());
    }
    /**
     * ���� K������
     * @param value�� ���HTTP URL
     */
    public void setIsKAudio(String isKAudio)
    {
        Property pro = new Property("subType", isKAudio);
        this.setProperty(pro);
    }

    /**
     * �����Ƿ���k��
     * @return�� K�� ���HTTP URL
     */
    public String getisKAudio()
    {
        return getNoNullString((String) this.getProperty("subType").getValue());
    }
}
