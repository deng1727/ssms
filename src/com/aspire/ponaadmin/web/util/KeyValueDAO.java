package com.aspire.ponaadmin.web.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;

/**
 * <p>Title: www_cms</p>
 * <p>Description: ������ȡ�����ֶα������</p>
 * <p>Copyright: Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author maobg
 * @version 1.0.0.0
 */

public class KeyValueDAO
{

    /**
     */
    private KeyValueDAO ()
    {
    }

    /**
     * @return ArrayList
     * @param sqlcode 
     */
    public static ArrayList select(String sqlcode) throws DAOException
    {
        ArrayList retlist = new ArrayList();
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlcode, null);
        try
        {
            while (rs.next())
            {
            	retlist.add(new LabelValueBean(rs.getString("label"), rs.getString("key")));
            }
        }
        catch(SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
        return retlist;
    }

    /**
     * ������װ
     * @return
     */
    public static ArrayList selectT()
    {
        ArrayList retlist = new ArrayList();
        retlist.add(new LabelValueBean("ȫ��", "G"));
        retlist.add(new LabelValueBean("����", "L"));

        return retlist;
    }

    /**
     * ҵ��״̬��װ
     * @return
     */
    public static ArrayList selectState(){
    	 ArrayList retlist = new ArrayList();
         retlist.add(new LabelValueBean("����", "A"));
         retlist.add(new LabelValueBean("Ԥ����", "P"));
         return retlist;
    }

    /**
     * @return ArrayList
     */
    public static ArrayList selectType(){
   	 ArrayList retlist = new ArrayList();
        retlist.add(new LabelValueBean("UM����", "Y"));
        retlist.add(new LabelValueBean("���������", "S"));
        retlist.add(new LabelValueBean("Wap�����", "W"));
        retlist.add(new LabelValueBean("���������", "K"));
        retlist.add(new LabelValueBean("���ŷ���", "M"));
        retlist.add(new LabelValueBean("Location�����", "L"));
        retlist.add(new LabelValueBean("Mail�����", "I"));
        retlist.add(new LabelValueBean("PDA����", "P"));
        retlist.add(new LabelValueBean("www�����", "E"));
        retlist.add(new LabelValueBean("�κη���", "O"));
        return retlist;
   }
}
