
package com.aspire.ponaadmin.web.datafield.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.dao.ContentExtDAO;

/**
 * <p>
 * ��������VO��
 * </p>
 * <p>
 * Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * 
 * @author biran
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class ContentExtQueryVO implements PageVOInterface
{

    /**
     * ����ID
     */
    private String contentID;

    /**
     * ����
     */
    private String name;

    /**
     * �ṩ��
     */
    private String spName;

    /**
     * ԭ�ۣ���λ(��)
     */
    private int mobilePrice;

    /**
     * 1����ʱ�Ʒ� 2������󸶷�
     */
    private String chargetime;

    /**
     * ��ҵ����
     */
    private String icpcode;

    private String subType;

    private String servAttr;

    private String marketDate;

    public String getContentID()
    {

        return contentID;
    }

    public void setContentID(String contentID)
    {

        this.contentID = contentID;
    }

    public String getName()
    {

        return name;
    }

    public void setName(String name)
    {

        this.name = name;
    }

    public String getSpName()
    {

        return spName;
    }

    public void setSpName(String spName)
    {

        this.spName = spName;
    }

    public int getMobilePrice()
    {

        return mobilePrice;
    }

    public String getMobilePriceStr()
    {

        double d = mobilePrice / 1000.0;
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(d);
    }

    public void setMobilePrice(int mobilePrice)
    {

        this.mobilePrice = mobilePrice;
    }

    public String getIcpcode()
    {

        return icpcode;
    }

    public void setIcpcode(String icpcode)
    {

        this.icpcode = icpcode;
    }

    public String getSubType()
    {

        return subType;
    }

    public void setSubType(String subType)
    {

        this.subType = subType;
    }

    public String getServAttr()
    {

        return servAttr;
    }

    public void setServAttr(String servAttr)
    {

        this.servAttr = servAttr;
    }

    public String getMarketDate()
    {

        return marketDate;
    }

    public void setMarketDate(String marketDate)
    {

        this.marketDate = marketDate;
    }

    public String getChargetime()
    {

        return chargetime;
    }

    public void setChargetime(String chargetime)
    {

        this.chargetime = chargetime;
    }

    public void CopyValFromResultSet(Object vo, ResultSet rs)
                    throws SQLException
    {

        ContentExtDAO.getInstance()
                     .getContentExtQueryVOFromRS(( ContentExtQueryVO ) vo, rs);
    }

    /**
     * 
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject()
    {

        return new ContentExtQueryVO();
    }
}
