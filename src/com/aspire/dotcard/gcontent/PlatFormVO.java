package com.aspire.dotcard.gcontent;

/**
 * PATCH 134 ADD 终端类型vo
 * @author x_liyouli
 *
 */
public class PlatFormVO
{

    /**
     * 平台名称
     */
    private String platFormName;

    /**
     * 平台编码
     */
    private String platFormId;

    /**
     * 平台供应商
     */
    private String vendor;

    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("PlatFormVO[platFormName=");
        sb.append(platFormName);
        sb.append(", platFormId=");
        sb.append(platFormId);
        sb.append(", vendor=");
        sb.append(vendor);
        sb.append("]");

        return sb.toString();
    }

    public String getPlatFormId()
    {

        return platFormId;
    }

    public void setPlatFormId(String platFormId)
    {

        this.platFormId = platFormId;
    }

    public String getPlatFormName()
    {
        return platFormName;
    }

    public void setPlatFormName(String platFormName)
    {
        this.platFormName = platFormName;
    }

    public String getVendor()
    {
        return vendor;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }
}
