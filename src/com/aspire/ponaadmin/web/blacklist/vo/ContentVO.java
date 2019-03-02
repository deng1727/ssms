
package com.aspire.ponaadmin.web.blacklist.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.constant.Constants;

public class ContentVO implements PageVOInterface
{

    /**
     * ����ID
     */
    private String contentId;

    /**
     * ��������
     */
    private String name;

    /**
     * �ṩ��
     */
    private String spName;

    /**
     * ��Ч��
     */
    private String inDate;

    /**
     * ���������ͣ� 1.����ˢ�� 2.�״�ˢ�� 3.���ˢ�� ��2�����ϣ�
     */
    private int blackType;

    /**
     * ����ʱ��
     */
    private String createTime;

    private String marketDate;

    /**
     * �û�����
     */
    private String subType;

    public String getMarketDate()
    {
        return marketDate;
    }

    public void setMarketDate(String marketDate)
    {
        this.marketDate = marketDate;
    }

    private String modifyTime;

    public String getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public String getContentId()
    {
        return contentId;
    }

    public void setContentId(String contentId)
    {
        this.contentId = contentId;
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

    public String getInDate()
    {
        return inDate;
    }

    public void setInDate(String inDate)
    {
        this.inDate = inDate;
    }

    public int getBlackType()
    {
        return blackType;
    }

    public void setBlackType(int blackType)
    {
        this.blackType = blackType;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public void CopyValFromResultSet(Object vo, ResultSet rs)
                    throws SQLException
    {
        ContentVO black = ( ContentVO ) vo;
        black.setContentId(rs.getString("contentid"));
        black.setName(rs.getString("name"));
        black.setSpName(rs.getString("spname"));
        black.setMarketDate(rs.getString("marketdate"));
        black.setSubType(Constants.BLACK_LIST_CONTENT_SUBTYPE_CY.equals(rs.getString("subType")) 
                        ? Constants.BLACK_LIST_CY
                        : Constants.BLACK_LIST_MM);
    }

    public Object createObject()
    {
        return new ContentVO();
    }

    public String getSubType()
    {
        return subType;
    }

    public void setSubType(String subType)
    {
        this.subType = subType;
    }

}
