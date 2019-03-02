package com.aspire.dotcard.gcontent;

import com.aspire.ponaadmin.web.repository.Property;

/**
 * <p>
 * 资源树中的A8全曲节点类
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
     * 资源类型：内容类型，彩铃
     */
    public static final String TYPE_AUDIO = "nt:gcontent:audio";

    /**
     * 构造方法
     */
    public GAudio()
    {
        this.type = TYPE_AUDIO;
    }

    /**
     * 构造方法
     * @param nodeID，资源id
     */
    public GAudio(String nodeID)
    {
        super(nodeID);
        this.type = TYPE_AUDIO;
    }

    /**
     * 获取歌曲语言分类
     * @return Returns the audioLanguage.
     */
    public String getAudioLanguage()
    {
        return getNoNullString((String) this.getProperty("audioLanguage").getValue());
    }


    /**
     * 设置歌曲语言分类
     * @param audioLanguage The audioLanguage to set.
     */
    public void setAudioLanguage(String audioLanguage)
    {
        Property pro = new Property("audioLanguage", audioLanguage);
        this.setProperty(pro);
    }
    
    /**
     * 获取歌手名
     * @return Returns the singer.
     */
    public String getSinger()
    {
        return getNoNullString((String) this.getProperty("singer").getValue());
    }


    /**
     * 设置歌手名
     * @param singer The singer to set.
     */
    public void setSinger(String singer)
    {
        Property pro = new Property("singer", singer);
        this.setProperty(pro);
    }
    
    /**
     * 获取aac播放HTTP URL
     * @return Returns the aacAuditionUrl.
     */
    public String getAacAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("aacAuditionUrl").getValue());
    }

    /**
     * 设置aac播放HTTP URL
     * @param aacAuditionUrl The aacAuditionUrl to set.
     */
    public void setAacAuditionUrl(String aacAuditionUrl)
    {
        Property pro = new Property("aacAuditionUrl", aacAuditionUrl);
        this.setProperty(pro);
    }

    /**
     * 设置 歌曲歌手地区分类
     * @param value， 歌曲歌手地区分类
     */
    public void setSingerZone(String singerZone)
    {
        Property pro = new Property("singerZone", singerZone);
        this.setProperty(pro);
    }

    /**
     * 获取 歌曲歌手地区分类
     * @return， 歌曲歌手地区分类
     */
    public String getSingerZone()
    {
        return getNoNullString((String) this.getProperty("singerZone").getValue());
    }
    
    /**
     * 设置 歌词HTTP URL
     * @param value， 歌词HTTP URL
     */
    public void setLrcURL(String lrcURL)
    {
        Property pro = new Property("lrcURL", lrcURL);
        this.setProperty(pro);
    }

    /**
     * 获取 歌词HTTP URL
     * @return， 歌词HTTP URL
     */
    public String getLrcURL()
    {
        return getNoNullString((String) this.getProperty("lrcURL").getValue());
    }
      
    /**
     * 获取aac播放HTTP URL
     * @return Returns the mp3AuditionUrl.
     */
    public String getMp3AuditionUrl()
    {
        return getNoNullString((String)this.getProperty("mp3AuditionUrl").getValue());
    }

    /**
     * 设置aac播放HTTP URL
     * @param mp3AuditionUrl The mp3AuditionUrl to set.
     */
    public void setMp3AuditionUrl(String mp3AuditionUrl)
    {
        Property pro = new Property("mp3AuditionUrl", mp3AuditionUrl);
        this.setProperty(pro);
    }
    
    /**
     * 设置 歌曲归属专辑名称
     * @param value， 歌曲归属专辑名称
     */
    public void setSpecial(String special)
    {
        Property pro = new Property("special", special);
        this.setProperty(pro);
    }

    /**
     * 获取 歌曲归属专辑名称
     * @return， 歌曲归属专辑名称
     */
    public String getSpecial()
    {
        return getNoNullString((String) this.getProperty("special").getValue());
    }
    
    /**
     * 设置 歌曲归属专辑图片url
     * @param value，歌曲归属专辑图片url
     */
    public void setSpecialURL(String specialURL)
    {
        Property pro = new Property("specialURL", specialURL);
        this.setProperty(pro);
    }

    /**
     * 获取 歌曲归属专辑图片url
     * @return， 歌曲归属专辑图片url
     */
    public String getSpecialURL()
    {
        return getNoNullString((String) this.getProperty("specialURL").getValue());
    }
    
    /**
     * 获取K歌的aac播放HTTP URL
     * @return Returns the aacAuditionUrl.
     */
    public String getKAacAuditionUrl()
    {
        return getNoNullString((String)this.getProperty("KAacAuditionUrl").getValue());
    }

    /**
     * 设置aac播放HTTP URL
     * @param aacAuditionUrl The aacAuditionUrl to set.
     */
    public void setKAacAuditionUrl(String kAacAuditionUrl)
    {
        Property pro = new Property("KAacAuditionUrl", kAacAuditionUrl);
        this.setProperty(pro);
    }
    /**
     * 获取k歌 aac播放HTTP URL
     * @return Returns the mp3AuditionUrl.
     */
    public String getKMp3AuditionUrl()
    {
        return getNoNullString((String)this.getProperty("KMp3AuditionUrl").getValue());
    }

    /**
     * 设置k歌aac播放HTTP URL
     * @param mp3AuditionUrl The mp3AuditionUrl to set.
     */
    public void setKMp3AuditionUrl(String kMp3AuditionUrl)
    {
        Property pro = new Property("KMp3AuditionUrl", kMp3AuditionUrl);
        this.setProperty(pro);
    }
    /**
     * 获取	K歌歌曲播放时长
     * @return Returns the mp3AuditionUrl.
     */
    public int getAudioLength()
    {
        //return ((Integer)(this.getProperty("audioLength"))).intValue();
        return (( Integer ) this.getProperty("audioLength").getValue()).intValue();
    }

    /**
     * 设置 K歌歌曲播放时长
     * @param mp3AuditionUrl The mp3AuditionUrl to set.
     */
    public void setAudioLength(int audioLength)
    {
        Property pro = new Property("audioLength", new Integer(audioLength));
        this.setProperty(pro);
    }
    /**
     * 设置 K歌 歌词HTTP URL
     * @param value， 歌词HTTP URL
     */
    public void setKLrcURL(String kLrcURL)
    {
        Property pro = new Property("KLrcURL", kLrcURL);
        this.setProperty(pro);
    }

    /**
     * 获取K歌 歌词HTTP URL
     * @return， K歌 歌词HTTP URL
     */
    public String getKLrcURL()
    {
        return getNoNullString((String) this.getProperty("KLrcURL").getValue());
    }
    /**
     * 设置 K歌属性
     * @param value， 歌词HTTP URL
     */
    public void setIsKAudio(String isKAudio)
    {
        Property pro = new Property("subType", isKAudio);
        this.setProperty(pro);
    }

    /**
     * 测试是否是k歌
     * @return， K歌 歌词HTTP URL
     */
    public String getisKAudio()
    {
        return getNoNullString((String) this.getProperty("subType").getValue());
    }
}
