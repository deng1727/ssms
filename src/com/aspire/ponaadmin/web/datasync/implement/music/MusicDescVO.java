/*
 * �ļ�����MusicDescVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ����id
     */
    private String musicId;
    
    /**
     * ��������
     */
    private String songName;
    
    /**
     * ��������
     */
    private String singer;
    
    /**
     * ר������
     */
    private String specialName;
    
    /**
     * ר������
     */
    private String specialDesc;
    
    /**
     * ͼƬ����
     */
    private String imageName;
    
    /**
     * ���ݱ��
     */
    private String contentId;
    
    /**
     * ��������
     */
    private String contentName;
    
    /**
     * ����ʱ��
     */
    private Date createDate;
    
    /**
     * �޸�ʱ��
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
