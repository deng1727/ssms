
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
 * Ӧ�û����VO��
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
     * �������ͣ�1�Ź���2��ɱ
     */
    private int type;

    /**
     * �ۿ��ʣ�0��100֮�� 
     */
    private int discount;

    /**
     * ��ʼ���ڣ���ʽ(yyyyMMdd)
     */
    private String dateStart;

    /**
     * ����ʱ�䣺��ʽ(HH:mm:ss)
     */
    private String dateEnd;

    /**
     * ��ʼʱ�䣺��ʽ(HH:mm:ss)
     */
    private String timeStart;

    /**
     * ����ʱ�䣺��ʽ(HH:mm:ss)
     */
    private String timeEnd;

    /**
     * ������ʱ��
     */
    private String lupDate;

    /**
     * ����ԱID
     */
    private String userid;

    /**
     * ԭ�ۣ���λ(��)
     */
    private int mobilePrice;

    /**
     * �ۿۼ�=ԭ��X�ۿ���/100 ��λ(��)
     */
    private double expPrice;

    /**
     * ��ҵ����
     */
    private String icpcode;

    /**
     * �Ƿ��Ƽ� 0�����Ƽ���1���Ƽ�
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
