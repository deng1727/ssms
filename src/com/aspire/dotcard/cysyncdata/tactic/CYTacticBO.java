package com.aspire.dotcard.cysyncdata.tactic;

import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * CMS����ͬ�����Թ���
 * @author x_liyouli
 *
 */
public class CYTacticBO
{
    /**
     * ��־����
     */
    JLogger logger = LoggerFactory.getLogger(CYTacticBO.class);
    
	/**
	 * ���һ��ͬ������
	 * @param vo
	 */
	public void addTactic(CYTacticVO vo) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.addTactic(). CYTacticVO=" + vo);
	    }
		try
        {
            new CYTacticDAO().addTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("���һ��ͬ������ʧ�ܣ�",e);
        }
	}
	
	/**
	 * ��ѯ����ͬ������
	 * @param id
	 * @return
	 */
	public CYTacticVO queryByID(int id) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.queryByID(). ID=" + id);
	    }
		try
        {
            return new CYTacticDAO().queryByID(id);
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ����ͬ������ʧ�ܣ�",e);
        }
	}
	
	/**
	 * ��ѯ���е�ͬ�����ԣ�����Ҫ��ҳ��������޸�ʱ�併������
	 * @return
	 */
	public List queryByCategoryID(String categoryID) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.queryByCategoryID(). categoryID=" + categoryID);
	    }
		try
        {
            return new CYTacticDAO().queryByCategoryID(categoryID);
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ���е�ͬ������ʧ�ܣ�",e);
        }
	}
	
	/**
	 * ��ѯ���е�ͬ������
	 * @return
	 */
	public List queryAll() throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.queryAll()." );
	    }
		try
        {
            return new CYTacticDAO().queryAll();
        }
        catch (DAOException e)
        {
            throw new BOException("��ѯ���е�ͬ������ʧ�ܣ�",e);
        }
	}
	
	/**
	 * �޸�һ��ͬ������
	 * @param vo
	 */
	public void modifyTactic(CYTacticVO vo) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.modifyTactic(). CYTacticVO=" + vo);
	    }
		try
        {
            new CYTacticDAO().modifyTactic(vo);
        }
        catch (DAOException e)
        {
            throw new BOException("�޸�һ��ͬ������ʧ�ܣ�",e);
        }
	}
	
	/**
	 * ɾ��һ��ͬ������
	 * @param id
	 */
	public void delTactic(int id) throws BOException
	{
		if (logger.isDebugEnabled())
	    {
			logger.debug("CYTacticBO.delTactic(). id=" + id);
	    }
		try
        {
            new CYTacticDAO().delTactic(id);
        }
        catch (DAOException e)
        {
            throw new BOException("ɾ��һ��ͬ������ʧ�ܣ�",e);
        }
	}
}
