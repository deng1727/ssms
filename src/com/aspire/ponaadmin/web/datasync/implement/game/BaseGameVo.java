package com.aspire.ponaadmin.web.datasync.implement.game;

/**
 * ������Ϸ����
 * @author zhangwei
 *
 */
public class BaseGameVo
{
    /**
     * ��Ϸ��ID
     */
    private String pkgId;
    
    /**
     * ��Ϸ������
     */
    private String pkgName;
    
    /**
     * ��Ϸ������
     */
    private String pkgDesc;
    
    /**
     * ������
     */
    private String cpName;
    
    /**
     * ҵ�����
     */
    private String serviceCode;
    
    /**
     * ����
     */
    private String fee;
    
    /**
     * ��Ϸ�����URL
     */
    private String pkgURL;
    
    /**
     * ��Ϸ��ͼƬurl1��� 30x30 
     */
    private String picurl1;

    /**
     * ��Ϸ��ͼƬurl2��� 34x34
     */
    private String picurl2;
    
    /**
     * ��Ϸ��ͼƬurl3��� 50x50
     */
    private String picurl3;
    
    /**
     * ��Ϸ��ͼƬurl4��� 65x65
     */
    private String picurl4;
    
    /**
     * ���״̬
     */
    private String changeType;
    
    /**
     * ��ʡ��ͣ
     */
    private String provinceCtrol;

    
    public String getChangeType()
    {
    
        return changeType;
    }

    
    public void setChangeType(String changeType)
    {
    
        this.changeType = changeType;
    }

    
    public String getCpName()
    {
    
        return cpName;
    }

    
    public void setCpName(String cpName)
    {
    
        this.cpName = cpName;
    }

    
    public String getFee()
    {
    
        return fee;
    }

    
    public void setFee(String fee)
    {
    
        this.fee = fee;
    }

    
    public String getPicurl1()
    {
    
        return picurl1;
    }

    
    public void setPicurl1(String picurl1)
    {
    
        this.picurl1 = picurl1;
    }

    
    public String getPicurl2()
    {
    
        return picurl2;
    }

    
    public void setPicurl2(String picurl2)
    {
    
        this.picurl2 = picurl2;
    }

    
    public String getPicurl3()
    {
    
        return picurl3;
    }

    
    public void setPicurl3(String picurl3)
    {
    
        this.picurl3 = picurl3;
    }

    
    public String getPicurl4()
    {
    
        return picurl4;
    }

    
    public void setPicurl4(String picurl4)
    {
    
        this.picurl4 = picurl4;
    }

    
    public String getPkgDesc()
    {
    
        return pkgDesc;
    }

    
    public void setPkgDesc(String pkgDesc)
    {
    
        this.pkgDesc = pkgDesc;
    }

    
    public String getPkgId()
    {
    
        return pkgId;
    }

    
    public void setPkgId(String pkgId)
    {
    
        this.pkgId = pkgId;
    }

    
    public String getPkgName()
    {
    
        return pkgName;
    }

    
    public void setPkgName(String pkgName)
    {
    
        this.pkgName = pkgName;
    }

    
    public String getPkgURL()
    {
    
        return pkgURL;
    }

    
    public void setPkgURL(String pkgURL)
    {
    
        this.pkgURL = pkgURL;
    }

    
    public String getServiceCode()
    {
    
        return serviceCode;
    }

    
    public void setServiceCode(String serviceCode)
    {
    
        this.serviceCode = serviceCode;
    }


    public String getProvinceCtrol()
    {
        return provinceCtrol;
    }


    public void setProvinceCtrol(String provinceCtrol)
    {
        this.provinceCtrol = provinceCtrol;
    }
}
