/*
 * 文件名：BaseMusic.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.baserecomm.basemusic;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class BaseMusicVO
{
    /**
     * 基地音乐id
     */
    private String musicId;
    
    /**
     * 内容id
     */
    private String contentId;
    
    /**
     * 歌曲名称
     */
    private String songName;
    
    /**
     * 歌手名称
     */
    private String singer;
    
    /**
     * 是否是有效数据
     */
    private String validityData;

    
    
    public String getValidityData()
    {
        return validityData;
    }


    
    public void setValidityData(String validityData)
    {
        this.validityData = validityData;
    }


    public String getContentId()
    {
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    
    public String getMusicId()
    {
        return musicId;
    }

    
    public void setMusicId(String musicId)
    {
        this.musicId = musicId;
    }

    
    public String getSinger()
    {
        return singer;
    }

    
    public void setSinger(String singer)
    {
        this.singer = singer;
    }

    
    public String getSongName()
    {
        return songName;
    }

    
    public void setSongName(String songName)
    {
        this.songName = songName;
    }
}
