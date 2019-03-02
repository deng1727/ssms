package com.aspire.ponaadmin.web.datasync.implement.game;
/**
 * 用于记录基地游戏主键的VO类。icpCode和icpServid决定基地游戏的主键。
 * @author zhangwei
 *
 */
public class TwGameKeyVO
{
    /**
     * 产品归属的CP
     */
	private String cpId;
    
    /**
     * CP名称
     */
    private String cpName;
    
    /**
     * 产品的业务代码
     */
    private String cpServiceId;
    
    /**
     * 产品名称
     */
    private String serviceName;
    
    /**
     * 产品简称
     */
    private String serviceShortName;
    
    /**
     * 产品简介
     */
    private String serviceDesc;
    
    /**
     * 操作简介
     */
    private String operationDesc;
    
    /**
     * 业务类型
     */
    private String serviceType;
    
    /**
     * 支付方式
     */
    private String servicePayType;
    
    /**
     * 产品生效日期
     */
    private String serviceStartDate;
    
    /**
     * 产品失效日期
     */
    private String serviceEndDate;
    
    /**
     * 业务状态
     */
    private String serviceStatus;
    
    /**
     * 资费
     */
    private String fee;
    
    /**
     * 资费描述
     */
    private String serviceFeeDesc;
    
    /**
     * 图文游戏的链接地址
     */
    private String serviceUrl;
    
    /**
     * 计费方式
     */
    private String serviceFeeType;
    
    /**
     * 游戏分类
     */
    private String gameType;
    
    /**
     * MM游戏分类
     */
    private String mmGameType;
    
    /**
     * 游戏分类名称
     */
    private String gameTypeDesc;
    
    /**
     * 业务标识
     */
    private String serviceFlag;
    
    /**
     * 业务推广方式
     */
    private String ptypeId;

    public String getCpId()
    {
        return cpId;
    }

    public void setCpId(String cpId)
    {
        this.cpId = cpId;
    }

    public String getCpName()
    {
        return cpName;
    }

    public void setCpName(String cpName)
    {
        this.cpName = cpName;
    }

    public String getCpServiceId()
    {
        return cpServiceId;
    }

    public void setCpServiceId(String cpServiceId)
    {
        this.cpServiceId = cpServiceId;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getGameType()
    {
        return gameType;
    }

    public void setGameType(String gameType)
    {
        this.gameType = gameType;
    }

    public String getGameTypeDesc()
    {
        return gameTypeDesc;
    }

    public void setGameTypeDesc(String gameTypeDesc)
    {
        this.gameTypeDesc = gameTypeDesc;
    }

    public String getMmGameType()
    {
        return mmGameType;
    }

    public void setMmGameType(String mmGameType)
    {
        this.mmGameType = mmGameType;
    }

    public String getOperationDesc()
    {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc)
    {
        this.operationDesc = operationDesc;
    }

    public String getPtypeId()
    {
        return ptypeId;
    }

    public void setPtypeId(String ptypeId)
    {
        this.ptypeId = ptypeId;
    }

    public String getServiceDesc()
    {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc)
    {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceEndDate()
    {
        return serviceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate)
    {
        this.serviceEndDate = serviceEndDate;
    }

    public String getServiceFeeDesc()
    {
        return serviceFeeDesc;
    }

    public void setServiceFeeDesc(String serviceFeeDesc)
    {
        this.serviceFeeDesc = serviceFeeDesc;
    }

    public String getServiceFeeType()
    {
        return serviceFeeType;
    }

    public void setServiceFeeType(String serviceFeeType)
    {
        this.serviceFeeType = serviceFeeType;
    }

    public String getServiceFlag()
    {
        return serviceFlag;
    }

    public void setServiceFlag(String serviceFlag)
    {
        this.serviceFlag = serviceFlag;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServicePayType()
    {
        return servicePayType;
    }

    public void setServicePayType(String servicePayType)
    {
        this.servicePayType = servicePayType;
    }

    public String getServiceShortName()
    {
        return serviceShortName;
    }

    public void setServiceShortName(String serviceShortName)
    {
        this.serviceShortName = serviceShortName;
    }

    public String getServiceStartDate()
    {
        return serviceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate)
    {
        this.serviceStartDate = serviceStartDate;
    }

    public String getServiceStatus()
    {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceType()
    {
        return serviceType;
    }

    public void setServiceType(String serviceType)
    {
        this.serviceType = serviceType;
    }

    public String getServiceUrl()
    {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl)
    {
        this.serviceUrl = serviceUrl;
    }
	
}
