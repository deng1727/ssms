/*
 * �ļ�����BaseVideo.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.ponaadmin.web.baserecomm.basevideo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class BaseVideoVO
{
    /**
     * ר������
     */
    private String videoName;
    
    /**
     * ����id
     */
    private String contentId;
    
    /**
     * ר��URL����
     */
    private String videoURL;

    
    public String getContentId()
    {
        return contentId;
    }

    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }

    
    public String getVideoName()
    {
        return videoName;
    }

    
    public void setVideoName(String videoName)
    {
        this.videoName = videoName;
    }

    
    public String getVideoURL()
    {
        return videoURL;
    }

    
    public void setVideoURL(String videoURL)
    {
        this.videoURL = videoURL;
    }
}
