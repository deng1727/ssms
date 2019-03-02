
package com.aspire.ponaadmin.web.datafield.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.dao.KeyBaseDAO;

/**
 * <p>
 * ��չ����ֵVO��
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
public class ResourceVO implements PageVOInterface
{

    /**
     * ����id contentid
     */
    private String tid;

    /**
     * id
     */
    private String keyid;

    /**
     * �ֶ�����
     */
    private String keyname;

    /**
     * �ֶ�����
     */
    private String keydesc;

    /**
     * ������
     */
    private String keytable;

    /**
     * �ֶ�ֵ
     */
    private String value;

    /**
     * ����ʱ��
     */
    private String lupdate;
    
    /**
     * key���ͣ�1���ı����ͣ�2��ͼƬ����
     */
    private String keyType;
    

   
    
    public String getTid()
    {

        return tid;
    }

    public void setTid(String tid)
    {

        this.tid = tid;
    }

    public String getKeyid()
    {

        return keyid;
    }

    public void setKeyid(String keyid)
    {

        this.keyid = keyid;
    }

    public String getKeyname()
    {

        return keyname;
    }

    public void setKeyname(String keyname)
    {

        this.keyname = keyname;
    }

    public String getKeytable()
    {

        return keytable;
    }

    public void setKeytable(String keytable)
    {

        this.keytable = keytable;
    }

    public String getKeydesc()
    {

        return keydesc;
    }

    public void setKeydesc(String keydesc)
    {

        this.keydesc = keydesc;
    }

    public String getValue()
    {

        return value;
    }

    public void setValue(String value)
    {

        this.value = value;
    }

    public String getLupdate()
    {

        return lupdate;
    }

    public void setLupdate(String lupdate)
    {

        this.lupdate = lupdate;
    }

    public void CopyValFromResultSet(Object vo, ResultSet rs)
                    throws SQLException
    {

        KeyBaseDAO.getInstance().getKeyBaseFromRS(( ResourceVO ) vo, rs);
    }

    /**
     * 
     * @return VOObject
     * @todo Implement this com.aspire.ponaadmin.PageVOInterface method
     */
    public Object createObject()
    {

        return new ResourceVO();
    }

	/**
	 * @return Returns the keyType.
	 */
	public String getKeyType()
	{
		return keyType;
	}

	/**
	 * @param keyType The keyType to set.
	 */
	public void setKeyType(String keyType)
	{
		this.keyType = keyType;
	}

	
}
