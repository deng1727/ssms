
package com.aspire.ponaadmin.web.pivot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.pivot.vo.PivotDeviceVO;

public class PivotDeviceDAO
{
    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(PivotDeviceDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static PivotDeviceDAO instance = new PivotDeviceDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private PivotDeviceDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static PivotDeviceDAO getInstance()
    {
        return instance;
    }

    /**
     * Ӧ�����ҳ��ȡVO��ʵ����
     */
    private class PivotDevicePageVO implements PageVOInterface
    {
        public void CopyValFromResultSet(Object content, ResultSet rs)
                        throws SQLException
        {
            PivotDeviceVO vo = ( PivotDeviceVO ) content;

            vo.setDeviceId(String.valueOf(rs.getInt("device_id")));
            vo.setDeviceName(rs.getString("device_name"));
            vo.setBrandName(rs.getString("brand_name"));
            vo.setOsName(rs.getString("os_name"));
            vo.setCreDate(String.valueOf(rs.getDate("CreDate")));
        }

        public Object createObject()
        {
            return new PivotDeviceVO();
        }
    }

    /**
     * �������ڲ�ѯ�ص�����б�
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public void queryPivotDeviceList(PageResult page, PivotDeviceVO vo)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("queryPivotDeviceList() is starting ...");
        }

        // select * from t_pivot_device t where 1=1
        String sqlCode = "pivot.PivotDeviceDAO.queryPivotDeviceList().SELECT";
        String sql = null;

        try
        {
            sql = SQLCode.getInstance().getSQLStatement(sqlCode);

            
            StringBuffer sqlBuffer = new StringBuffer(sql) ;
            List paras = new ArrayList();
            //����������sql�Ͳ���
            
            if (!"".equals(vo.getDeviceId()))
            {
                //sql += " and t.device_id ='" + vo.getDeviceId() + "'";
            	sqlBuffer.append(" and t.device_id =? ");
            	paras.add(vo.getDeviceId());
            }
            if (!"".equals(vo.getDeviceName()))
            {
//                sql += " and upper(t.device_name) like('%" + vo.getDeviceName().toUpperCase()
//                       + "%')";
            	sqlBuffer.append(" and upper(t.device_name) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getDeviceName().toUpperCase())+"%");
            }
            if (!"".equals(vo.getBrandName()))
            {
//                sql += " and upper(t.brand_name) like('%"
//                       + vo.getBrandName().toUpperCase() + "%')";
            	sqlBuffer.append(" and upper(t.brand_name) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getBrandName().toUpperCase())+"%");
            }
            if (!"".equals(vo.getOsName()))
            {
//                sql += " and upper(t.os_name) like('%"
//                       + vo.getOsName().toUpperCase() + "%')";
            	sqlBuffer.append(" and upper(t.os_name) like ? ");
            	paras.add("%"+SQLUtil.escape(vo.getOsName().toUpperCase())+"%");
            }

            //sql += " order by t.device_id asc";
            sqlBuffer.append(" order by t.device_id asc");

            //page.excute(sql, null, new PivotDevicePageVO());
            page.excute(sqlBuffer.toString(), paras.toArray(), new PivotDevicePageVO());
        }
        catch (DataAccessException e)
        {
            throw new DAOException("��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�",
                                   e);
        }
    }

    /**
     * �����Ƴ�ָ���ص����
     * 
     * @param deviceId �ص����id��
     * @throws DAOException
     */
    public void removeDeviceID(String[] deviceId) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("removeDeviceID() is starting ...");
        }

        // delete from t_pivot_device r where r.device_id = ?
        String sql = "pivot.PivotDeviceDAO.removeDeviceID().remove";
        String sqlCode[] = new String[deviceId.length];
        Object[][] object = new Object[deviceId.length][1];

        for (int i = 0; i < deviceId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = deviceId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("�Ƴ�ָ���ص����ʱ�����쳣:", e);
        }
    }

    /**
     * У��ļ��Д����Ƿ��ڻ��ͱ��д���
     * 
     * @param list
     * @throws DAOException
     */
    public String verifyDeviceId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("verifyDeviceId() is starting ...");
        }

        // select 1 from t_device c where c.device_id = ?
        String sql = "pivot.PivotDeviceDAO.verifyDeviceId().select";
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        // ������ԃ
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // �����������������
                if (!rs.next())
                {
                    list.remove(i);
                    i--;
                    sb.append(temp).append(". ");
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("У��ļ��Д����Ƿ��ڻ��ͱ��д���ʱ�����쳣:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }

        return sb.toString();
    }
    
    /**
     * �����б����Ƿ����ԭ������
     * 
     * @param list
     * @throws DAOException
     */
    public void hasDeviceId(List list) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasDeviceId() is starting ...");
        }

        // select 1 from t_pivot_device c where c.device_id = ?
        String sql = "pivot.PivotDeviceDAO.hasDeviceId().select";
        ResultSet rs = null;

        // ������ԃ
        for (int i = 0; i < list.size(); i++)
        {
            String temp = ( String ) list.get(i);
            try
            {
                rs = DB.getInstance().queryBySQLCode(sql.toString(),
                                                     new Object[] { temp });
                // ���������������
                if (rs.next())
                {
                    list.remove(i);
                    i--;
                }
            }
            catch (SQLException e)
            {
                throw new DAOException("�����б����Ƿ����ԭ������ʱ�����쳣:", e);
            }
            finally
            {
                DB.close(rs);
            }
        }
    }
    

    /**
     * ��ӻ���id�б�
     * 
     * @param String
     */
    public void addDeviceId(String[] deviceId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("addDeviceId() is starting ...");
        }

        // insert into t_pivot_device (device_id, device_name, brand_name,
        // os_name) select d.device_id, d.device_name, b.brand_name, s.osname
        // from t_device d, t_device_brand b, t_device_os s where b.brand_id =
        // d.brand_id and s.os_id = d.os_id and d.device_id = ?
        String sql = "pivot.PivotDeviceDAO.addDeviceId().add";

        String sqlCode[] = new String[deviceId.length];
        Object[][] object = new Object[deviceId.length][1];

        for (int i = 0; i < deviceId.length; i++)
        {
            sqlCode[i] = sql;
            object[i][0] = deviceId[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(sqlCode, object);
        }
        catch (DAOException e)
        {
            throw new DAOException("����ص����id�б�ʱ�����쳣:", e);
        }
    }
}
