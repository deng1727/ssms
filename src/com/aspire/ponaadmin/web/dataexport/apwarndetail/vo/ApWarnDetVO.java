/*
 * 文件名：ApWarnDetVO.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述：  
 */
package com.aspire.ponaadmin.web.dataexport.apwarndetail.vo;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author x_wangml
 * @version 
 */
public class ApWarnDetVO
{
    /**
     * 内容id
     */
    private String contentId;
    
    /**
     * 内容名称
     */
    private String contentName;
    
    /**
     * 下载用户
     */
    private String userId;
    
    /**
     * 资费类型
     */
    private String payType;
    
    /**
     * sp名称
     */
    private String spName;
    
    /**
     * 预警等级
     */
    private String warnGrade;
    
    /**
     * 预警规则
     */
    private String warnRule;
    
    /**
     * 是否存在下载量波动异常
     */
    private String isDownWarn;
    
    /**
     * 单用户下载应用是否异常
     */
    private String isSingleUserDownloadWarn;
    
    /**
     * 是否存在连号消费现象
     */
    private String isSeriesNumWarn;
    
    /**
     * 是否存在城市下载消费增幅过大现象
     */
    private String isCityDownloadIncreaseWarn;
    
    /**
     * 是否有消费时间异常的现象
     */
    private String isDownloadTimeWarn;
    
    /**
     * 是否有消费用户复叠率过大的现象
     */
    private String isDownloadUserIteranceWarn;

    public String getContentId()
    {
        return contentId;
    }
    
    public void setContentId(String contentId)
    {
        this.contentId = contentId;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getSpName()
    {
        return spName;
    }
    
    public void setSpName(String spName)
    {
        this.spName = spName;
    }
    
    public String getContentName()
    {
        return contentName;
    }

    public void setContentName(String contentName)
    {
        this.contentName = contentName;
    }

    
    public String getIsSingleUserDownloadWarn()
    {
        return isSingleUserDownloadWarn;
    }

    
    public void setIsSingleUserDownloadWarn(String isSingleUserDownloadWarn)
    {
        this.isSingleUserDownloadWarn = isSingleUserDownloadWarn;
    }

    
    public String getIsCityDownloadIncreaseWarn()
    {
        return isCityDownloadIncreaseWarn;
    }

    
    public void setIsCityDownloadIncreaseWarn(String isCityDownloadIncreaseWarn)
    {
        this.isCityDownloadIncreaseWarn = isCityDownloadIncreaseWarn;
    }
    
    public String getIsSeriesNumWarn()
    {
        return isSeriesNumWarn;
    }
    
    public void setIsSeriesNumWarn(String isSeriesNumWarn)
    {
        this.isSeriesNumWarn = isSeriesNumWarn;
    }
    
    public String getIsDownloadTimeWarn()
    {
        return isDownloadTimeWarn;
    }
    
    public void setIsDownloadTimeWarn(String isDownloadTimeWarn)
    {
        this.isDownloadTimeWarn = isDownloadTimeWarn;
    }
    
    public String getIsDownloadUserIteranceWarn()
    {
        return isDownloadUserIteranceWarn;
    }

    public void setIsDownloadUserIteranceWarn(String isDownloadUserIteranceWarn)
    {
        this.isDownloadUserIteranceWarn = isDownloadUserIteranceWarn;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getIsDownWarn()
    {
        return isDownWarn;
    }
    
    public void setIsDownWarn(String isDownWarn)
    {
        this.isDownWarn = isDownWarn;
    }

    public String getWarnGrade()
    {
        return warnGrade;
    }

    public void setWarnGrade(String warnGrade)
    {
        this.warnGrade = warnGrade;
    }
    
    public String getWarnRule()
    {
        return warnRule;
    }

    public void setWarnRule(String warnRule)
    {
        this.warnRule = warnRule;
    }
}
