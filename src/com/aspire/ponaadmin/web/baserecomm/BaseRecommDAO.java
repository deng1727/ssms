/*
 * 
 */

package com.aspire.ponaadmin.web.baserecomm;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author x_wangml
 * 
 */
public class BaseRecommDAO
{

    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseRecommDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static BaseRecommDAO instance = new BaseRecommDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private BaseRecommDAO()
    {
    }

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static BaseRecommDAO getInstance()
    {
        return instance;
    }

    /**
     * �����ݴ�����ʱ����
     * 
     * @param baseIds ����id����
     * @param baseType ��������
     * @throws DAOException
     */
    public void addBaseData(String[] baseIds, String baseType)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addBaseData(" + baseType + ") is starting ...");
        }

        // insert into t_base_temp (baseid, basetype) values (?, ?)
        String sqlCode = "basegame.BaseGameDAO.addBaseData().INSERT";
        String[] mutiSQL = new String[baseIds.length];
        Object paras[][] = new String[baseIds.length][2];

        for (int i = 0; i < baseIds.length; i++)
        {
            mutiSQL[i] = sqlCode;
            paras[i][0] = baseIds[i];
            paras[i][1] = baseType;
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("�����ݴ�����ʱ��ʱ�����쳣:", e);
        }
    }
    
    /**
     * ���ڰ����ݴ���ʱ����ɾ��
     * 
     * @param baseIds ����id����
     * @param baseType ��������
     * @throws DAOException
     */
    public void delBaseData(String[] baseIds, String baseType)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("delBaseData(" + baseType + ") is starting ...");
        }

        // delete from t_base_temp t where baseType=? and baseid=?
        String sqlCode = "basegame.BaseGameDAO.delBaseData().DELETE";
        String[] mutiSQL = new String[baseIds.length];
        Object paras[][] = new String[baseIds.length][2];

        for (int i = 0; i < baseIds.length; i++)
        {
            mutiSQL[i] = sqlCode;
            paras[i][0] = baseType;
            paras[i][1] = baseIds[i];
        }

        try
        {
            DB.getInstance().executeMutiBySQLCode(mutiSQL, paras);
        }
        catch (DAOException e)
        {
            throw new DAOException("�����ݴ���ʱ����ɾ��ʱ�����쳣:", e);
        }
    }

    /**
     * �����ж������Ƿ�����б���
     * 
     * @param baseId
     * @param baseType
     * @return
     * @throws DAOException
     */
    public boolean isHasBaseDate(String baseId, String baseType)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("isHasBaseDate(" + baseId + ", " + baseType
                         + ") is starting ...");
        }

        // select 1 from t_base_temp t where t.baseid=? and t.basetype=?
        String sqlCode = "basegame.BaseGameDAO.isHasBaseDate().SELECT";

        ResultSet rs = DB.getInstance()
                         .queryBySQLCode(sqlCode, new Object[] { baseId, baseType });

        try
        {
            // �������next˵����������
            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ������ʱ��ʱ�������ݿ����");
        }
        finally
        {
            DB.close(rs);
        }
    }
}
