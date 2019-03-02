
package com.aspire.dotcard.reportdata.cystatistic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author tungke
 * 
 */
public class CyListDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger LOG = LoggerFactory.getLogger(CyListDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static CyListDAO instance = new CyListDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private CyListDAO()
    {

    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static CyListDAO getInstance() throws DAOException
    {

        return instance;
    }

    /**
     * ִ�����ݿ��ѯ
     * 
     * @param sql
     * @param parm
     * @return
     * @throws DAOException
     */
    public void updateCyListVO(String[] mutiSQL, Object paras[][])
                    throws DAOException, Exception
    {

        int count =0;
        ResultSet rs = DB.getInstance().queryBySQLCode(mutiSQL[0], paras[0]);
        try
        {
            if (rs.next())
            {
                count = 1;
            }
        }
        catch (SQLException e)
        {
            LOG.error("��ѯ��ҵ��������ʧ��", e);
            throw new DAOException("��ѯ��ҵ��������ʧ��", e);
        }
        finally
        {
            DB.close(rs);
        }

        if (count == 0)
        {
            // ��������
            DB.getInstance().executeBySQLCode(mutiSQL[1], paras[1]);
        }
        else if (count == 1)
        {
            // ��������
            DB.getInstance().executeBySQLCode(mutiSQL[2], paras[2]);
        }

    }

}
