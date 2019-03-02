/*
 * 文件名：MusicDescVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.datasync.implement.music;

import java.util.Date;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class MusicDescVO
{
    /**
     * 音乐id
     */
    private String musicId;
    
    /**
     * 歌曲名称
     */
    private String songName;
    
    /**
     * 歌手名称
     */
    private String singer;
    
    /**
     * 专辑名称
     */
    private String specialName;
    
    /**
     * 专辑介绍
     */
    private String specialDesc;
    
    /**
     * 图片名称
     */
    private String imageName;
    
    /**
     * 内容编号
     */
    private String contentId;
    
    /**
     * 内容名称
     */
    private String contentName;
    
    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 修改时间
     */
    private Date editDate;

    
    public String getContentId()
    {
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    
    public String getContentName()
    {
        return contentName;
    }

    
    public void setContentName(String contentName)
    {
        this.contentName = contentName;
    }

    
    public Date getCreateDate()
    {
        return createDate;
    }

    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    
    public Date getEditDate()
    {
        return editDate;
    }

    
    public void setEditDate(Date editDate)
    {
        this.editDate = editDate;
    }

    
    public String getImageName()
    {
        return imageName;
    }

    
    public void setImageName(String imageName)
    {
        this.imageName = imageName;
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

    
    public String getSpecialDesc()
    {
        return specialDesc;
    }

    
    public void setSpecialDesc(String specialDesc)
    {
        this.specialDesc = specialDesc;
    }

    
    public String getSpecialName()
    {
        return specialName;
    }

    
    public void setSpecialName(String specialName)
    {
        this.specialName = specialName;
    }
}
