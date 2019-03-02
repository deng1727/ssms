/*
 * �ļ�����ConentDayComparedDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.reportdata.gcontent.task;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ConentDayComparedDAO
{
    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(ContentDayTask.class);

    private static ConentDayComparedDAO dao = new ConentDayComparedDAO();

    private ConentDayComparedDAO()
    {
    }

    public static ConentDayComparedDAO getInstance()
    {
        return dao;
    }

    /**
     * �ڿ�ʼ���뱨������ǰ�����t_r_servenday_temp��ǰһ��Ĺ�������
     */
    public void delOldRepData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delOldRepData() is starting ...");
        }

        // truncate table t_r_servenday_temp
        String sqlCode = "reportdata.ConentDayComparedDAO.delOldRepData().del";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("�ڿ�ʼ���뱨������ǰ�����t_r_servenday_temp��ǰһ��Ĺ�������ʱ�����쳣:",
                                   e);
        }
    }

    /**
     * ���뱨����report_servenday������t_r_servenday_temp
     */
    public void insertRepData() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("insertRepData() is starting ...");
        }

        // insert into t_r_servenday_temp value select * from report_servenday r
        String sqlCode = "reportdata.ConentDayComparedDAO.insertRepData().insert";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("���뱨����report_servenday������t_r_servenday_tempʱ�����쳣:",
                                   e);
        }
    }

    /**
     * t_r_sevendayscompared����������ű��Ϊ�������
     */
    public void updateNewNumberToOld() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateNewNumberToOld() is starting ...");
        }

        // update t_r_sevendayscompared d set d.aoldnumber = d.anewnumber,
        // d.ooldnumber = d.onewnumber, d.anewnumber = 0, d.onewnumber = 0
        String sqlCode = "reportdata.ConentDayComparedDAO.updateNewNumberToOld().up";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, null);
        }
        catch (DAOException e)
        {
            throw new DAOException("t_r_sevendayscompared����������ű��Ϊ�������ʱ�����쳣:",
                                   e);
        }
    }

    /**
     * �����ʱͳ�Ʊ�t_r_sevenday
     * 
     * @throws DAOException
     */
    public void delTempTable() throws DAOException
    {
        // truncate table t_r_sevenday
        String sqlCode = "reportdata.ConentDayComparedDAO.delTempTable().del";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("�����ʱͳ�Ʊ�t_r_sevendayʱ�����쳣:", e);
        }
    }

    /**
     * ͳ�Ʊ�����������ʱͳ�Ʊ�t_r_sevenday��
     * 
     * @throws DAOException
     */
    public void insertNewToTemp(String osId) throws DAOException
    {
        // insert into t_r_sevenday (contentid, ORDERBYID) select content_id,
        // rank() over(partition by 1 order by add_7days_down_count desc) rn
        // from t_r_servenday_temp r where r.os_id = ?
        String sqlCode = "reportdata.ConentDayComparedDAO.insertNewToTemp().insert";
        Object paras[] = { osId };

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("ͳ�Ʊ�����������ʱͳ�Ʊ�t_r_sevenday��ʱ�����쳣:", e);
        }
    }

    /**
     * �õ���ʱ����ȫ������
     * 
     * @return
     * @throws DAOException
     */
    public Map getTempDate() throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getTempDate() is starting ...");
        }

        // select * from t_r_sevenday
        String sqlCode = "reportdata.ConentDayComparedDAO.getTempDate().select";
        Object paras[] = null;
        ResultSet rs = null;
        Map map = new HashMap();

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            while (rs.next())
            {
                map.put(rs.getString("contentid"),
                        String.valueOf(rs.getInt("orderbyid")));
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������ʱ�����쳣:",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������ʱ�����쳣:",
                                   e);
        }

        return map;
    }

    /**
     * �Ƿ���ڴ���������
     * 
     * @param contentId
     * @return
     * @throws DAOException
     */
    public boolean hasContentId(String contentId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasContentId() is starting ...");
        }

        // select 1 from t_r_sevendayscompared s where s.contentid = ?
        String sqlCode = "reportdata.ConentDayComparedDAO.hasContentId().select";
        Object paras[] = { contentId };
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            if (rs.next())
            {
                return true;
            }
        }
        catch (DAOException e)
        {
            throw new DAOException("���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������ʱ�����쳣:",
                                   e);
        }
        catch (SQLException e)
        {
            throw new DAOException("���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������ʱ�����쳣:",
                                   e);
        }

        return false;
    }

    /**
     * ���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������
     * 
     * @param map
     * @param osId 3:OPhone OS, 9:Android
     * @throws DAOException
     */
    public void updateNewDate(Map map, String osId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateNewCompared() is starting ...");
        }

        String[] mutiSQLCode = new String[map.size()];
        Object[][] mutiParas = new Object[map.size()][2];

        // insert into t_r_sevendayscompared s (contentid, aoldnumber,
        // anewnumber, ooldnumber, onewnumber) values (?, 0, ?, 0, 0)
        // insert into t_r_sevendayscompared s (contentid, aoldnumber,
        // anewnumber, ooldnumber, onewnumber) values (?, 0, 0, 0, ?)
        // update t_r_sevendayscompared d set d.anewnumber=? where d.contentid=?
        // update t_r_sevendayscompared d set d.onewnumber=? where d.contentid=?

        String sqlCode_update = "reportdata.ConentDayComparedDAO.updateNewDate().update_";
        String sqlCode_add = "reportdata.ConentDayComparedDAO.updateNewDate().add_";

        Set set = map.keySet();
        int temp = 0;

        for (Iterator iter = set.iterator(); iter.hasNext(); temp++)
        {
            String contentId = ( String ) iter.next();
            String orderbyId = ( String ) map.get(contentId);

            if (this.hasContentId(contentId))
            {
                mutiSQLCode[temp] = sqlCode_update + osId;
                mutiParas[temp] = new Object[] { orderbyId, contentId };
            }
            else
            {
                mutiSQLCode[temp] = sqlCode_add + osId;
                mutiParas[temp] = new Object[] { contentId, orderbyId };
            }
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQLCode, mutiParas);
        }
        catch (DAOException e)
        {
            throw new DAOException("���ͳ�Ʊ������ݸ�������仯����t_r_sevendayscompared���������ʱ�����쳣:",
                                   e);
        }
    }

    /**
     * �嶨���մ�������ֵ�Ľ����
     * 
     * @throws DAOException
     */
    public void delCompared() throws DAOException
    {
        // truncate table T_R_SEVENCOMPARED
        String sqlCode = "reportdata.ConentDayComparedDAO.delCompared().del";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("�嶨���մ�������ֵ�Ľ����ʱ�����쳣:", e);
        }
    }

    /**
     * �嶨���մ�������ֵ�Ľ����
     * 
     * @throws DAOException
     */
    public void updateCompared() throws DAOException
    {
        // insert into T_R_SEVENCOMPARED (contentid, anumber, onumber) select
        // s.contentid, decode(s.aoldnumber,0,s.anewnumber,s.aoldnumber -
        // s.anewnumber), decode(s.ooldnumber,0,s.onewnumber,s.ooldnumber -
        // s.onewnumber) from t_r_sevendayscompared s
        String sqlCode = "reportdata.ConentDayComparedDAO.updateCompared().update";
        Object paras[] = null;

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("�嶨���մ�������ֵ�Ľ����ʱ�����쳣:", e);
        }
    }
    
    /**
     * ���ô洢���̣��������������������ֵ
     * 
     * @param conn
     * @param spName �洢�������ƣ������Ʊ�����sql.properties�ļ��е�keyֵ���Ӧ�������޷���ȡ��Ӧ�����
     */
    public void exeProcedure(String spName) throws DAOException
    {
        Connection conn = null;
        CallableStatement cs = null;
        String errorMessage;
        try
        {
            // ִ�д洢���̡�
            logger.info("��ʼִ�д洢���̣�" + spName);

            conn = DB.getInstance().getConnection();

            cs = conn.prepareCall("{call " + spName + "()}");
            
            cs.execute();
            
            logger.info("�ɹ�ִ�д洢���̣�" + spName);
        }
        catch (Exception e)
        {
            errorMessage = "���ô洢����" + spName + "ʧ��,��������ϵ����Ա������";
            logger.error(errorMessage, e);
            
            throw new DAOException("���ô洢���̣��������������������ֵʱ�����쳣:", e);
        }
        finally
        {
            try
            {
                cs.close();
            }
            catch (Exception ex)
            {
                logger.error("�ر�CallableStatement=ʧ��", ex);
            }
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                }
            }
        }
    }
}
