
package com.aspire.ponaadmin.web.datasync.implement.game;

import java.sql.ResultSet;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class BaseGameStatDAO
{
    /**
     * 日志引用
     */
    protected static JLogger logger = LoggerFactory.getLogger(BaseGameStatDAO.class);

    private static BaseGameStatDAO dao = new BaseGameStatDAO();

    public static BaseGameStatDAO getInstance()
    {
        return dao;
    }

    /**
     * 检查游戏id是否存在
     * 
     * @param game 游戏类
     * @return 返回新增结果
     */
    public boolean isExistedVO(BaseGameStatVO game) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("isExistedVO" + game);
        }

        // select 1 from t_r_gcontent t where t.contentId = ?
        Object paras[] = { game.getGameId() };
        String sqlCode = "datasync.implement.game.BaseGameStatDAO.isExistedVO.SELECT";
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
            logger.error("查询游戏数据出错" + game, e);
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
    }

    /**
     * 用于查看gameId是否存在于附加表中
     * 
     * @param gameId
     * @return
     * @throws DAOException
     */
    public boolean isHasGame(String gameId) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("isHasGame" + gameId);
        }

        // select 1 from T_GAME_ATTR t where t.gameid = ?
        Object paras[] = { gameId };
        String sqlCode = "datasync.implement.game.BaseGameStatDAO.isHasGame.SELECT";
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
            logger.error("查询游戏数据出错gameid = " + gameId, e);
            throw new DAOException(e);
        }
        finally
        {
            DB.close(rs);
        }
    }

    /**
     * 用于变更游戏附加表信息。在就变更。不在就新增
     * 
     * @param game 游戏信息
     * @param isHas 是否存在
     */
    public void updateGameData(BaseGameStatVO game, boolean isHas)
                    throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updateGameData(),game=" + game.getGameId()
                         + ",isHas=" + isHas);
        }

        String sqlCode;

        // 存在就变更
        if (isHas)
        {
            // update T_GAME_ATTR g set g.testnumber = ?, g.uservalue = ?,
            // g.downloadchange = ?, g.dayactivityuser = ?, g.commendtime = ?
            // where g.gameid = ?;
            sqlCode = "datasync.implement.game.BaseGameStatDAO.updateGameData.update";
        }
        // 不存在就新增
        else
        {
            // insert into T_GAME_ATTR (testnumber, uservalue, downloadchange,
            // dayactivityuser, commendtime, gameid) values (?, ?, ?, ?, ?, ?)
            sqlCode = "datasync.implement.game.BaseGameStatDAO.updateGameData.INSERT";
        }

        Object paras[] = { game.getTestNumber(),
                        game.getUserValue(),
                        game.getDownloadChange(),
                        game.getDayActivityUser(),
                        game.getCommendTime(), game.getGameId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("变更游戏附加表信息时出错。gameid = " + game.getGameId(), e);
            throw new DAOException(e);
        }
    }

    /**
     * 用于变更游戏表信息
     * 
     * @param game 游戏信息
     */
    public void updteGameContent(BaseGameStatVO game) throws DAOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("updteGameContent(),game=" + game.getGameId());
        }

        // update t_r_gcontent g set g.dayordertimes=?, g.weekordertimes=?,
        // g.monthordertimes=?, g.downloadtimes=?, g.settimes=?, g.ordertimes=?
        // where g.contentid=?
        String sqlCode = "datasync.implement.game.BaseGameStatDAO.updteGameContent.update";

        Object paras[] = { game.getDayOrderTimes(),
                        game.getWeekOrderTimes(),
                        game.getMonthOrderTimes(),
                        game.getSevenDay(),
                        game.getMonthDay(),
                        game.getCount(), game.getGameId() };
        try
        {
            DB.getInstance().executeBySQLCode(sqlCode, paras);
        }
        catch (DAOException e)
        {
            logger.error("变更游戏附加表信息时出错。gameid = " + game.getGameId(), e);
            throw new DAOException(e);
        }
    }
}
