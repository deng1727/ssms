/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.game;

import com.aspire.common.db.DAOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataDealer;
import com.aspire.ponaadmin.web.datasync.DataRecord;
import com.aspire.ponaadmin.web.datasync.DataSyncConfig;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * @author x_wangml
 * 
 */
public class BaseGameStatDealer implements DataDealer
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(BaseGameStatDealer.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataDealer#clearDirtyData()
     */
    public void clearDirtyData()
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataDealer#dealDataRecrod(com.aspire.ponaadmin.web.datasync.DataRecord)
     */
    public int dealDataRecrod(DataRecord record) throws Exception
    {
        BaseGameStatVO game = new BaseGameStatVO();

        // 组装数据对象信息
        baseGameStatFormat(game, record);

        // 用以存储系统中是否有此内容
        boolean isHas = true;

        try
        {
            // 根据游戏id查看数据是否存在
            isHas = BaseGameStatDAO.getInstance().isExistedVO(game);
        }
        catch (DAOException e1)
        {
            logger.error("根据游戏ID" + game.getGameId()
                         + "查询是否存在于系统中时发生了数据库异常，对应的记录忽略处理！");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        // 新增
        if(isHas)
        {
            return insertBaseGame(game);
        }
        else
        {
            // 异常情况记录错误信息，不处理
            logger.error("更新游戏内容失败，gameId=" + game.getGameId() + "，系统不存在该内容");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
    }

    /**
     * 把文件数据段组装成游戏包数据对象
     * @param game 游戏包数据对象
     * @param record 文件数据段信息
     */
    private void baseGameStatFormat(BaseGameStatVO game, DataRecord record)
    {
        game.setGameId((String)record.get(1));
        game.setTestNumber((String)record.get(2));
        game.setUserValue((String)record.get(3));
        game.setDayOrderTimes((String)record.get(4));
        game.setWeekOrderTimes((String)record.get(5));
        game.setMonthOrderTimes((String)record.get(6));
        game.setSevenDay((String)record.get(7));
        game.setMonthDay((String)record.get(8));
        game.setCount((String)record.get(9));
        game.setDownloadChange((String)record.get(10));
        game.setDayActivityUser((String)record.get(11));
        game.setCommendTime((String)record.get(12));
    }
    
    /**
     * 新增游戏包数据
     * @param game 游戏数据
     * @param isHas 库中是否存在此数据
     * @return
     */
    private int insertBaseGame(BaseGameStatVO game)
    {
        boolean isHas = false;
        
        // 在t_r_gcontent表中变更数据
        try
        {
            BaseGameStatDAO.getInstance().updteGameContent(game);
        }
        catch (DAOException e)
        {
            logger.error("根据游戏ID" + game.getGameId()
                         + "变列数据信息时发生了数据库异常，对应的记录忽略处理！");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        // 于新表中看。是否有此gameid信息
        try
        {
            isHas = BaseGameStatDAO.getInstance().isHasGame(game.getGameId());
        }
        catch (DAOException e)
        {
            logger.error("根据游戏ID" + game.getGameId()
                         + "查询数据信息时发生了数据库异常，对应的记录忽略处理！");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        // 如果有,如果没有
        try
        {
            BaseGameStatDAO.getInstance().updateGameData(game, isHas);
        }
        catch (DAOException e)
        {
            logger.error("根据游戏ID" + game.getGameId()
                         + "变更游戏附加表数据信息时发生了数据库异常，对应的记录忽略处理！");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        
        return DataSyncConstants.SUCCESS_ADD;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataDealer#prepareData()
     */
    public void prepareData() throws Exception
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataSyncBuilder#init(com.aspire.ponaadmin.web.datasync.DataSyncConfig)
     */
    public void init(DataSyncConfig config) throws Exception
    {

    }
}
