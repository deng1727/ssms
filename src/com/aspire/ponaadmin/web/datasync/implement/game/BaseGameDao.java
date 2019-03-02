/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.game;

import java.sql.ResultSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author x_wangml
 * 
 */
public class BaseGameDao
{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseGameDao.class);

    private static BaseGameDao dao = new BaseGameDao();

    public static BaseGameDao getInstance()
    {

        return dao;
    }
    
    private BaseGameDao()
    {
        
    }

    /**
     * �����Ϸ���Ƿ����
     * 
     * @param game ��Ϸ��vo��
     * @return �����������
     */
    public boolean isExistedVO(BaseGameVo game, boolean isDel) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("isExistedVO" + game);
        }

        // select * from t_game_base where pkgid=? and (state='1' or state='2')
        Object paras[] = { game.getPkgId() };
        String sqlCode;
        
        if(isDel)
        {
            sqlCode = "datasync.implement.game.BaseGameDao.isExistedDelVO.SELECT";
        }
        else
        {
            sqlCode = "datasync.implement.game.BaseGameDao.isExistedVO.SELECT";
        }
        
        ResultSet rs = null;
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

            if (rs.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e)
        {
            logger.error("��ѯ��Ϸ�����ݳ���" + game, e);
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
    }

    /**
     * ������Ϸ������
     * 
     * @param game ��Ϸ��vo��
     * @throws DAOException
     */
    public void updateBaseGameVo(BaseGameVo game) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateBaseGameVo" + game);
        }

        // update t_game_base t set
        // t.pkgname=?,t.pkgdesc=?,t.cpname=?,t.servicecode=?,t.fee=?,t.pkgurl=?,
        // t.picurl1=?,t.picurl2=?,t.picurl3=?,t.picurl4=?,t.provincectrol=?,t.UPDATETIME=sysdate
        // t.state = '2'
        // where t.pkgid=?
        Object paras[] = { game.getPkgName(), game.getPkgDesc(),
                        game.getCpName(), game.getServiceCode(), game.getFee(),
                        game.getPkgURL(), game.getPicurl1(), game.getPicurl2(),
                        game.getPicurl3(), game.getPicurl4(),
                        game.getProvinceCtrol(), game.getPkgId() };

        String sqlCode = "datasync.implement.game.BaseGameDao.updateBaseGameVo.UPDATE";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("������Ϸ�����ݳ���" + game, e);
            throw new DAOException(e);
        }
    }
    
    /**
     * ������Ϸ������
     * 
     * @param game ��Ϸ��vo��
     * @throws DAOException
     */
    public void insertBaseGameByDelVo(BaseGameVo game) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("updateBaseGameVo" + game);
        }

        // update t_game_base t set
        // t.pkgname=?,t.pkgdesc=?,t.cpname=?,t.servicecode=?,t.fee=?,t.pkgurl=?,
        // t.picurl1=?,t.picurl2=?,t.picurl3=?,t.picurl4=?,t.provincectrol=?,t.UPDATETIME=sysdate
        // t.state = '1',provincectrol=?
        // where t.pkgid=?
        Object paras[] = { game.getPkgName(), game.getPkgDesc(),
                        game.getCpName(), game.getServiceCode(), game.getFee(),
                        game.getPkgURL(), game.getPicurl1(), game.getPicurl2(),
                        game.getPicurl3(), game.getPicurl4(),
                        game.getProvinceCtrol(), game.getPkgId() };

        String sqlCode = "datasync.implement.game.BaseGameDao.insertBaseGameByDelVo.INSERT";
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("������Ϸ�����ݳ���" + game, e);
            throw new DAOException(e);
        }
    }

    /**
     * ɾ����Ϸ������
     * 
     * @param game ��Ϸ��vo��
     * @throws DAOException
     */
    public void deleteBaseGameVo(BaseGameVo game) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("deleteBaseGameVo");
        }

        // update t_game_base t set
        // t.state = '3'
        // where t.pkgid=?
        String sqlCode = "datasync.implement.game.BaseGameDao.deleteBaseGameVo.DELETE";
        Object paras[] = { game.getPkgId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("ɾ����Ϸ�����ݳ���" + game, e);
            throw new DAOException(e);
        }
    }

    /**
     * ������Ϸ������
     * 
     * @param game ��Ϸ��vo��
     * @return �����������
     * @throws DAOException
     */
    public void insertBaseGameVo(BaseGameVo game) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("insertBaseGameVo");
        }

        // insert into t_game_base (pkgid, pkgName, pkgDesc, cpname,
        // serviceCode, fee, pkgURL, picurl1, picurl2, picurl3, picurl4,t.provincectrol=?, state)
        // values
        // (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '1')
        Object paras[] = { game.getPkgId(), game.getPkgName(),
                        game.getPkgDesc(), game.getCpName(),
                        game.getServiceCode(), game.getFee(), game.getPkgURL(),
                        game.getPicurl1(), game.getPicurl2(),
                        game.getPicurl3(), game.getPicurl4(),game.getProvinceCtrol()};
        String sqlCode = "datasync.implement.game.BaseGameDao.insertBaseGameVo.INSERT";

        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("������Ϸ������ʧ�ܣ�id=" + game.getPkgId() + ",bookId="
                         + game.getPkgName(), e);
            throw new DAOException(e);
        }
    }
}
