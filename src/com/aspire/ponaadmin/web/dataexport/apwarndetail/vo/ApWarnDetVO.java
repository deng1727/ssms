/*
 * �ļ�����ApWarnDetVO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
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
     * ����id
     */
    private String contentId;
    
    /**
     * ��������
     */
    private String contentName;
    
    /**
     * �����û�
     */
    private String userId;
    
    /**
     * �ʷ�����
     */
    private String payType;
    
    /**
     * sp����
     */
    private String spName;
    
    /**
     * Ԥ���ȼ�
     */
    private String warnGrade;
    
    /**
     * Ԥ������
     */
    private String warnRule;
    
    /**
     * �Ƿ���������������쳣
     */
    private String isDownWarn;
    
    /**
     * ���û�����Ӧ���Ƿ��쳣
     */
    private String isSingleUserDownloadWarn;
    
    /**
     * �Ƿ����������������
     */
    private String isSeriesNumWarn;
    
    /**
     * �Ƿ���ڳ�����������������������
     */
    private String isCityDownloadIncreaseWarn;
    
    /**
     * �Ƿ�������ʱ���쳣������
     */
    private String isDownloadTimeWarn;
    
    /**
     * �Ƿ��������û������ʹ��������
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
