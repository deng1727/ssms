
package com.aspire.ponaadmin.web.datafield.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.dao.ContentDAO;
import com.aspire.ponaadmin.web.util.StringTool;

/**
 * <p>
 * Ӧ������VO��
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
public class ContentVO implements PageVOInterface
{

    /**
     * id
     */
    private String id;

    /**
     * ����
     */
    private String name;

    /**
     * �ṩ��
     */
    private String spName;

    /**
     * ҵ�����
     */
    private String icpServId;

    /**
     * ��ҵ����
     */
    private String icpCode;

    /**
     * ����ID
     */
    private String contentID;

    /**
     * �ṩ��Χ
     */
    private String servattr;

    /**
     * ���ݱ�ʶ
     */
    private String contenttag;

    /**
     * ���ݱ�ǩ
     */
    private String keywords;

    /**
     * ���ݳ���
     */
    private String subType;

    /**
     * ����ʱ��
     */
    private String marketdate;

    /**
     * ��������
     */
    private String cateName;

    public String getId()
    {

        return id;
    }

    public void setId(String id)
    {

        this.id = id;
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

    public String getIcpServId()
    {

        return icpServId;
    }

    public void setIcpServId(String icpServId)
    {

        this.icpServId = icpServId;
    }

    public String getIcpCode()
    {

        return icpCode;
    }

    public void setIcpCode(String icpCode)
    {

        this.icpCode = icpCode;
    }

    public String getContentID()
    {

        return contentID;
    }

    public void setContentID(String contentID)
    {

        this.contentID = contentID;
    }

    public String getServattr()
    {

        return servattr;
    }

    public void setServattr(String servattr)
    {

        this.servattr = servattr;
    }

    public String getContenttag()
    {

        return contenttag;
    }

    public void setContenttag(String contenttag)
    {

        this.contenttag = contenttag;
    }

    public String getKeywords()
    {

        return keywords;
    }

    public void setKeywords(String keywords)
    {

        this.keywords = keywords;
    }

    public String getSubType()
    {

        return subType;
    }

    public void setSubType(String subType)
    {

        this.subType = subType;
    }

    public String getMarketdate()
    {

        return marketdate;
    }

    public void setMarketdate(String marketdate)
    {

        this.marketdate = marketdate;
    }

    public String getCateName()
    {

        return cateName;
    }

    public void setCateName(String cateName)
    {

        this.cateName = cateName;
    }

    /**
     * ���ڶ����йؼ��ֽ��зָ�
     * 
     * @param keywords �ؼ���
     * @return �ָ��ؼ���
     */
    public String getKeywordsFormat()
    {

        return StringTool.unPack(this.keywords);

    }

    public void CopyValFromResultSet(Object vo, ResultSet rs)
                    throws SQLException
    {

        ContentDAO.getInstance().getContentVOFromRS(( ContentVO ) vo, rs);
    }

    /**
     * 
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject()
    {

        return new ContentVO();
    }
}
