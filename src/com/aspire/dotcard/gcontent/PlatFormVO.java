package com.aspire.dotcard.gcontent;

/**
 * PATCH 134 ADD �ն�����vo
 * @author x_liyouli
 *
 */
public class PlatFormVO
{

    /**
     * ƽ̨����
     */
    private String platFormName;

    /**
     * ƽ̨����
     */
    private String platFormId;

    /**
     * ƽ̨��Ӧ��
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
