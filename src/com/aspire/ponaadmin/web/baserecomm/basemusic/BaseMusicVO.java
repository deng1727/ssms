/*
 * �ļ�����BaseMusic.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ��������id
     */
    private String musicId;
    
    /**
     * ����id
     */
    private String contentId;
    
    /**
     * ��������
     */
    private String songName;
    
    /**
     * ��������
     */
    private String singer;
    
    /**
     * �Ƿ�����Ч����
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
