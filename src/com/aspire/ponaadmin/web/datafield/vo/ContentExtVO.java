
package com.aspire.ponaadmin.web.datafield.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.dao.ContentExtDAO;

/**
 * <p>
 * 应用活动属性VO类
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
public class ContentExtVO implements PageVOInterface
{

    /**
     * id
     */
    private String id;

    /**
     * 内容ID
     */
    private String contentID;

    /**
     * 名称
     */
    private String name;

    /**
     * 提供商
     */
    private String spName;

    /**
     * 内容类型：1团购，2秒杀
     */
    private int type;

    /**
     * 折扣率：0至100之间 
     */
    private int discount;

    /**
     * 开始日期：格式(yyyyMMdd)
     */
    private String dateStart;

    /**
     * 结束时间：格式(HH:mm:ss)
     */
    private String dateEnd;

    /**
     * 开始时间：格式(HH:mm:ss)
     */
    private String timeStart;

    /**
     * 结束时间：格式(HH:mm:ss)
     */
    private String timeEnd;

    /**
     * 最后更新时间
     */
    private String lupDate;

    /**
     * 操作员ID
     */
    private String userid;

    /**
     * 原价：单位(厘)
     */
    private int mobilePrice;

    /**
     * 折扣价=原价X折扣率/100 单位(厘)
     */
    private double expPrice;

    /**
     * 企业代码
     */
    private String icpcode;

    /**
     * 是否推荐 0，不推荐；1，推荐
     */
    private String isrecomm;

    public String getId()
    {

        return id;
    }

    public void setId(String id)
    {

        this.id = id;
    }

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

    public int getType()
    {

        return type;
    }

    public void setType(int type)
    {

        this.type = type;
    }

    public int getDiscount()
    {

        return discount;
    }

    public void setDiscount(int discount)
    {

        this.discount = discount;
    }

    public String getDateStart()
    {

        return dateStart;
    }

    public String getDateStartStr() throws ParseException
    {

        return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(dateStart));

    }

    public void setDateStart(String dateStart)
    {

        this.dateStart = dateStart;
    }

    public String getDateEnd()
    {

        return dateEnd;
    }

    public String getDateEndStr() throws ParseException
    {

        return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyyMMdd").parse(dateEnd));
    }

    public void setDateEnd(String dateEnd)
    {

        this.dateEnd = dateEnd;
    }

    public String getTimeStart()
    {

        return timeStart;
    }

    public void setTimeStart(String timeStart)
    {

        this.timeStart = timeStart;
    }

    public String getTimeEnd()
    {

        return timeEnd;
    }

    public void setTimeEnd(String timeEnd)
    {

        this.timeEnd = timeEnd;
    }

    public String getLupDate()
    {

        return lupDate;
    }

    public String getLupDateStr() throws ParseException
    {

        return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(lupDate));
    }

    public void setLupDate(String lupDate)
    {

        this.lupDate = lupDate;
    }

    public String getUserid()
    {

        return userid;
    }

    public void setUserid(String userid)
    {

        this.userid = userid;
    }

    public int getMobilePrice()
    {

        return mobilePrice;
    }

    public void setMobilePrice(int mobilePrice)
    {

        this.mobilePrice = mobilePrice;
    }

    public double getExpPrice()
    {

        return expPrice;
    }

    public void setExpPrice(double expPrice)
    {

        this.expPrice = expPrice;
    }

    public String getIcpcode()
    {

        return icpcode;
    }

    public void setIcpcode(String icpcode)
    {

        this.icpcode = icpcode;
    }

    public String getMobilePriceStr()
    {

        double d = mobilePrice / 1000.0;
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(d);
    }

    public String getExpPriceStr()
    {

        double d = expPrice / 1000.0;
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(d);
    }

    public String getIsrecomm()
    {

        return isrecomm;
    }

    public void setIsrecomm(String isrecomm)
    {

        this.isrecomm = isrecomm;
    }

    public void CopyValFromResultSet(Object vo, ResultSet rs)
                    throws SQLException
    {

        ContentExtDAO.getInstance().getContentExtVOFromRS(( ContentExtVO ) vo,
                                                          rs);
    }

    /**
     * 
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject()
    {

        return new ContentExtVO();
    }
}
