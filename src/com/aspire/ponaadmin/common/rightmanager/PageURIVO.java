package com.aspire.ponaadmin.common.rightmanager ;

/**
 * <p>ҳ��Ȩ����ϢVO��</p>
 * <p>ҳ��Ȩ����ϢVO��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class PageURIVO
{

    /**
     * ҳ��URI
     */
    private String pageURI;

    /**
     * ҳ������
     */
    private String desc;

    /**
     * ��Ӧ��Ȩ��ID
     */
    private String rightID;

    /**
     * ��ȡҳ��URI
     * @return String ҳ��URI
     */
    public String getPageURI ()
    {
        return pageURI ;
    }

    /**
     * ����ҳ��URI
     * @param URI String ҳ��URI
     */
    public void setPageURI (String URI)
    {
        this.pageURI = URI ;
    }

    /**
     * ��ȡҳ������
     * @return String ҳ������
     */
    public String getDesc ()
    {
        return desc ;
    }

    /**
     * ����ҳ������
     * @param desc String ҳ������
     */
    public void setDesc (String desc)
    {
        this.desc = desc ;
    }

    /**
     * ��ȡ��Ӧ��Ȩ��ID
     * @param rightID String ��Ӧ��Ȩ��ID
     */
    public void setRightID (String rightID)
    {
        this.rightID = rightID ;
    }

    /**
     * ���ö�Ӧ��Ȩ��ID
     * @return String ��Ӧ��Ȩ��ID
     */
    public String getRightID ()
    {
        return rightID ;
    }
}
