package com.aspire.ponaadmin.web.baserecomm.basegame;

/**
 * 基地游戏类型
 * @author wml
 *
 */
public class BaseGameVO
{
    /**
     * 游戏包ID
     */
    private String pkgId;
    
    /**
     * 游戏包名称
     */
    private String pkgName;
    
    /**
     * 游戏包介绍
     */
    private String pkgDesc;
    
    /**
     * 游戏包类型
     */
    private String pkgType;
    
    /**
     * 是否是有效数据
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