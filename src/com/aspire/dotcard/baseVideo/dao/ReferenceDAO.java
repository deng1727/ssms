package com.aspire.dotcard.baseVideo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ReferenceDAO
{
    /**
     * ��¼��־��ʵ������
     */
    protected static JLogger logger = LoggerFactory.getLogger(ReferenceDAO.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static ReferenceDAO instance = new ReferenceDAO();

    /**
     * ���췽������singletonģʽ����
     */
    private ReferenceDAO()
    {
    }
    
    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    public static ReferenceDAO getInstance()
    {
        return instance;
    }
    
    /**
     * �鿴��ǰ�������Ƿ񻹴�������Ʒ
     * 
     * @param page
     * @param vo
     * @throws DAOException
     */
    public int hasVideo(String categoryId)
                    throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("hasVideo( ) is starting ...");
        }

        // select count(*) as countNum from t_vo_reference t where t.categoryid = ?
        String sqlCode = "baseVideo.dao.ReferenceDAO.hasVideo.SELECT";
        ResultSet rs = null;
        int countNum = 0;
        
        rs = DB.getInstance().queryBySQLCode(sqlCode, new Object[]{categoryId});

        try
        {
            if (rs.next())
            {
                countNum = rs.getInt("countNum");
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("�鿴��ǰ�������Ƿ񻹴�������Ʒ��Ϣʱ�����쳣:", e);
        }
        
        finally
        {
            DB.close(rs);
        }

        return countNum;
    }
}
