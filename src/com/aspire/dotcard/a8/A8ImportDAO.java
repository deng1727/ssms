
package com.aspire.dotcard.a8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>
 * A8���ݹ���DAO��
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * 
 * @author bihui
 * @version 1.0.0.0
 */
public class A8ImportDAO
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(A8ImportDAO.class);

    /**
     * ���췽������singletonģʽ����
     */
    private A8ImportDAO()
    {

    }

    /**
     * singletonģʽ��ʵ��
     */
    private static A8ImportDAO a8ImportDAO = new A8ImportDAO();

    /**
     * ��ȡʵ��
     * 
     * @return ʵ��
     */
    static final A8ImportDAO getInstance()
    {

        return a8ImportDAO;
    }
     
    /**
     * ��ѯ���ݿ��д��ڵ�����a8ȫ����ID
     * @return
     * @throws DAOException
     */
    public List getAllAudioID() throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getAllAudioID()");
        }
        //select t0.id from t_r_base t0, t_r_gcontent t3 where t0.id = t3.id and t0.type = 'nt:gcontent:audio' and t0.parentid = 702
        String sqlCode = "com.aspire.dotcard.a8.A8ImportDAO.getAllAudioID().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        List res = new ArrayList();
        try
        {
            while (rs.next())
            {
                res.add(rs.getString("id"));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }

        return res;
    }
    /**
     * ��ȡ��ǰ���ݿ������и��ֵ�id
     * @return
     * @throws DAOException
     */
    public List getAllSingerIdS()throws DAOException
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("getAllAudioID()");
        }
        //select t0.id from t_r_base t0, t_r_gcontent t3 where t0.id = t3.id and t0.type = 'nt:gcontent:audio' and t0.parentid = 702
        String sqlCode = "com.aspire.dotcard.a8.A8ImportDAO.getAllSingerID().SELECT";
        ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, null);
        List res = new ArrayList();
        try
        {
            while (rs.next())
            {
                res.add(rs.getString("id"));
            }
        }
        catch (SQLException e)
        {
            throw new DAOException("��ȡ",e);
        }
        finally
        {
            DB.close(rs);
        }

        return res;
    	
    }
    /**
     * �������ּ�¼
     * @param vo ����vo��
     * @return �����������
     */
    public int insertSinger(SingerVO vo)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("insertSinger"+vo);
        }
    	Object paras[]= {vo.getId(),vo.getName(),vo.getRegion(),vo.getType(),vo.getFirstLetter(),vo.getSingerZone()};
    	String sqlCode="com.aspire.dotcard.a8.A8ImportDAO.insertSinger().INSERT";
    	int result;
    	try
		{
    		result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(vo,e);
			return A8ParameterConfig.failure;
		}
		if(result<1)
		{
			return A8ParameterConfig.failure;
		}else
		{
			return A8ParameterConfig.success_add;
		}
    	
    }
    /**
     * 
     * @param vo
     * @return
     */
    public int updateSinger(SingerVO vo)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("updateSinger"+vo);
        }
    	Object paras[]= {vo.getName(),vo.getRegion(),vo.getType(),vo.getFirstLetter(),vo.getSingerZone(),vo.getId()};
    	String sqlCode="com.aspire.dotcard.a8.A8ImportDAO.updateSinger().UPDATE";
    	int result;
    	try
		{
			result=DB.getInstance().executeBySQLCode(sqlCode, paras);
		} catch (DAOException e)
		{
			logger.error(vo,e);
			return A8ParameterConfig.failure;
		}
		if(result<1)
		{
			return A8ParameterConfig.failure;
		}else
		{
			return A8ParameterConfig.success_update;
		}   
    }
    /**
     * 
     * @param vo
     * @return
     */
    public int deleteSinger(List list )
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("deleteSinger");
        }
    	Object mutiParas[][]=new Object[list.size()][1];
    	for(int i=0;i<list.size();i++)
    	{
    		mutiParas[i][0]=list.get(i);
    	}
    	String sqlCode="com.aspire.dotcard.a8.A8ImportDAO.deleteSinger().DELETE";
    	try
		{
			DB.getInstance().executeBatchBySQLCode(sqlCode, mutiParas);
		} catch (DAOException e)
		{
			logger.error(e);
			return A8ParameterConfig.failure;
		}
	    return A8ParameterConfig.success_delete;
		
    }
}