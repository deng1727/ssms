package com.aspire.ponaadmin.web.baserecomm.basegame;

/**
 * ������Ϸ����
 * @author wml
 *
 */
public class BaseGameVO
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
     * ��Ϸ������
     */
    private String pkgType;
    
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
    
    public String getPkgType()
    {
        return pkgType;
    }
    
    public void setPkgType(String pkgType)
    {
        this.pkgType = pkgType;
    }
}