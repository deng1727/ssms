package com.aspire.dotcard.cysyncdata.tactic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * CMS����ͬ������DAO
 * @author x_liyouli
 *
 */
public class CYTacticDAO
{
    /**
     * ��־����
     */
    JLogger logger = LoggerFactory.getLogger(CYTacticDAO.class);
    
	/**
	 * ���һ��ͬ������
	 * @param vo
	 */
	public void addTactic(CYTacticVO vo) throws DAOException
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("TacticDAO.addTactic()");
        }
		
		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.addTactic.INSERT";
    	Object[] paras = new Object[6];
    	paras[0] = vo.getCategoryID();
    	paras[1] = vo.getContentType();
    	paras[2] = vo.getUmFlag();
    	paras[3] = vo.getContentTag();
    	paras[4] = new Integer(vo.getTagRelation());
        paras[5] = vo.getAppCateName();
    	
        try
        {
        	DB.getInstance().executeBySQLCode(sqlCode,paras);            
        }
        catch(Exception e)
        {
            throw new DAOException("CYTacticDAO.addTactic() error.", e);
        }
	}
	
	/**
	 * ��ѯ����ͬ������
	 * @param id
	 * @return
	 */
	public CYTacticVO queryByID(int id) throws DAOException
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("TacticDAO.queryByID()");
        }
		CYTacticVO vo = null; 
        ResultSet rs = null;
        String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryByID.SELECT";
        Object[] paras = {new Integer(id)};
        
        try
        {        	
        	rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            if(rs.next())
            {
            	vo = new CYTacticVO();
            	setVOFromRS(vo, rs);
            }
        }
        catch(Exception e)
        {
            throw new DAOException("TacticDAO.queryByID() error.", e);
        }
        finally
        {
            DB.close(rs);
        }
        return vo;
	}
	
	/**
	 * ��ѯһ�����������е�ͬ�����ԣ�����Ҫ��ҳ��������޸�ʱ�併������
	 * @return
	 */
	public List queryByCategoryID(String categoryID) throws DAOException
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("TacticDAO.queryByCategoryID()");
        }
        List tacticList = new ArrayList(); 
        ResultSet rs = null;
        String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.queryByCategoryID.SELECT";
        Object[] paras = {categoryID};
        try
        {        	
        	rs = DB.getInstance().queryBySQLCode(sqlCode,paras);
            while(rs.next())
            {
            	CYTacticVO vo = new CYTacticVO();
            	setVOFromRS(vo, rs);
            	tacticList.add(vo);
            }
        }
        catch(Exception e)
        {
            throw new DAOException("TacticDAO.queryByCategoryID() error.", e);
        }
        finally
        {
            DB.close(rs);
        }
        return tacticList;
	}
	
	/**
	 * ��ѯ���е�ͬ������
	 * @return
	 */
	public List queryAll() throws DAOException
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("CYTacticDAO.queryAll()");
        }
        List tacticList = new ArrayList(); 
        ResultSet rs = null;
        String sqlCode = "com.aspire.dotcard.cysyncData.tactic.CYTacticDAO.queryAll.SELECT";
        
        try
        {        	
        	rs = DB.getInstance().queryBySQLCode(sqlCode,null);
            while(rs.next())
            {
            	CYTacticVO vo = new CYTacticVO();
            	setVOFromRS(vo, rs);
            	tacticList.add(vo);
            }
        }
        catch(Exception e)
        {
            throw new DAOException("CYTacticDAO.queryAll() error.", e);
        }
        finally
        {
            DB.close(rs);
        }
        return tacticList;
	}
	
	/**
	 * �޸�һ��ͬ������
	 * @param vo
	 */
	public void modifyTactic(CYTacticVO vo) throws DAOException
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("TacticDAO.modifyTactic()");
        }

		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.modifyTactic.UPDATE";
		Object[] paras = new Object[6];
    	paras[0] = vo.getContentType();
    	paras[1] = vo.getUmFlag();
    	paras[2] = vo.getContentTag();
    	paras[3] = new Integer(vo.getTagRelation());
        paras[4] = vo.getAppCateName();
    	paras[5] = new Integer(vo.getId());
    	
        try
        {        	
        	DB.getInstance().executeBySQLCode(sqlCode,paras);            
        }
        catch(Exception e)
        {
            throw new DAOException("TacticDAO.modifyTactic() error.", e);
        }
	}
	
	/**
	 * ɾ��һ��ͬ������
	 * @param id
	 */
	public void delTactic(int id) throws DAOException
	{
		if(logger.isDebugEnabled())
        {
            logger.debug("TacticDAO.delTactic()");
        }

		String sqlCode = "com.aspire.dotcard.syncData.tactic.TacticDAO.delTactic.DELETE";
    	Object[] paras = {new Integer(id)};
        try
        {        	
        	DB.getInstance().executeBySQLCode(sqlCode,paras);            
        }
        catch(Exception e)
        {
            throw new DAOException("TacticDAO.delTactic() error.", e);
        }
	}
	
	/**
	 * ��ResultSet���ݸ�ֵ��VO������
	 * @param vo
	 * @param rs
	 * @throws SQLException
	 */
	private void setVOFromRS(CYTacticVO vo, ResultSet rs) throws SQLException
	{
		vo.setId(rs.getInt("id"));
		vo.setCategoryID(rs.getString("categoryID"));
		vo.setContentType(rs.getString("contentType"));
		vo.setUmFlag(rs.getString("umFlag"));
		vo.setContentTag(rs.getString("contentTag"));
		vo.setTagRelation(rs.getInt("tagRelation"));
        vo.setAppCateName(rs.getString("appCateName"));
		vo.setCreatTime(rs.getString("crateTime"));
		vo.setLastUpdateTime(rs.getString("lastUpdateTime"));		
	}
}
