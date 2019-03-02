/*
 * 文件名：BaseVideo.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
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
     * 专题名称
     */
    private String videoName;
    
    /**
     * 内容id
     */
    private String contentId;
    
    /**
     * 专题URL链接
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
