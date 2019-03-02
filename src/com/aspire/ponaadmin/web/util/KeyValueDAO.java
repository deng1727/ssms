package com.aspire.ponaadmin.web.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;

/**
 * <p>Title: www_cms</p>
 * <p>Description: 用于提取两个字段表的数据</p>
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
     * 地区封装
     * @return
     */
    public static ArrayList selectT()
    {
        ArrayList retlist = new ArrayList();
        retlist.add(new LabelValueBean("全网", "G"));
        retlist.add(new LabelValueBean("本地", "L"));

        return retlist;
    }

    /**
     * 业务状态封装
     * @return
     */
    public static ArrayList selectState(){
    	 ArrayList retlist = new ArrayList();
         retlist.add(new LabelValueBean("商用", "A"));
         retlist.add(new LabelValueBean("预下线", "P"));
         return retlist;
    }

    /**
     * @return ArrayList
     */
    public static ArrayList selectType(){
   	 ArrayList retlist = new ArrayList();
        retlist.add(new LabelValueBean("UM服务", "Y"));
        retlist.add(new LabelValueBean("短信类服务", "S"));
        retlist.add(new LabelValueBean("Wap类服务", "W"));
        retlist.add(new LabelValueBean("下载类服务", "K"));
        retlist.add(new LabelValueBean("彩信服务", "M"));
        retlist.add(new LabelValueBean("Location类服务", "L"));
        retlist.add(new LabelValueBean("Mail类服务", "I"));
        retlist.add(new LabelValueBean("PDA服务", "P"));
        retlist.add(new LabelValueBean("www类服务", "E"));
        retlist.add(new LabelValueBean("任何服务", "O"));
        return retlist;
   }
}
