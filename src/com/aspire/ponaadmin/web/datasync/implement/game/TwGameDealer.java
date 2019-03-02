/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.game;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
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
public class TwGameDealer implements DataDealer
{

    /**
     * 日志引用
     */
    private static final JLogger logger = LoggerFactory.getLogger(TwGameDealer.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataDealer#clearDirtyData()
     */
    public void clearDirtyData()
    {
        /**
        if (logger.isDebugEnabled())
        {
            logger.debug("将T_GAME_TW改成 T_GAME_TW_TEMP1 ...");
        }

        // 将正式表改成 table_temp1
        try
        {
            renameByTable("T_GAME_TW", "T_GAME_TW_TEMP1");
        }
        catch (BOException e)
        {
            logger.error("将T_GAME_TW改成 T_GAME_TW_TEMP1时发生错误：", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("将T_GAME_TW_TEMP改成 T_GAME_TW ...");
        }

        // 将table_temp 改名为table
        try
        {
            renameByTable("T_GAME_TW_TEMP", "T_GAME_TW");
        }
        catch (BOException e)
        {
            logger.error("将T_GAME_TW_TEMP改成 T_GAME_TW时发生错误：", e);

            try
            {
                // 恢复状态
                renameByTable("T_GAME_TW_TEMP1", "T_GAME_TW");
            }
            catch (BOException e1)
            {
                e1.printStackTrace();
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("将T_GAME_TW_TEMP1改成 T_GAME_TW_TEMP ...");
        }

        // table_temp1 改名为table_temp
        try
        {
            renameByTable("T_GAME_TW_TEMP1", "T_GAME_TW_TEMP");
        }
        catch (BOException e)
        {
            logger.error("将T_GAME_TW_TEMP1改成 T_GAME_TW_TEMP时发生错误：", e);

            try
            {
                renameByTable("T_GAME_TW_TEMP1", "T_GAME_TW_TEMP");
            }
            catch (BOException e1)
            {
                e1.printStackTrace();
            }
        }
        **/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataDealer#dealDataRecrod(com.aspire.ponaadmin.web.datasync.DataRecord)
     */
    public int dealDataRecrod(DataRecord record) throws Exception
    {
        // 图文游戏对象
        TwGameKeyVO game = new TwGameKeyVO();

        // 组装数据对象信息
        twGameFormat(game, record);

        // 存入数据库中T_GAME_TW_TEMP表
        addData(game);

        return DataSyncConstants.SUCCESS_ADD;

    }

    /**
     * 把文件数据段组装成图文游戏数据对象
     * 
     * @param game 图文游戏数据对象
     * @param record 文件数据段信息
     */
    private void twGameFormat(TwGameKeyVO game, DataRecord record)
                    throws BOException
    {
        game.setCpId(( String ) record.get(1));
        game.setCpName(( String ) record.get(2));
        game.setCpServiceId(( String ) record.get(3));
        game.setServiceName(( String ) record.get(4));
        game.setServiceShortName(( String ) record.get(5));
        game.setServiceDesc(( String ) record.get(6));
        game.setOperationDesc(( String ) record.get(7));
        game.setServiceType(( String ) record.get(8));
        game.setServicePayType(( String ) record.get(9));

        game.setServiceStartDate(( String ) record.get(10));
        game.setServiceEndDate(( String ) record.get(11));

        game.setServiceStatus(( String ) record.get(12));
        game.setFee(String.valueOf(Integer.parseInt((String) record.get(13)) * 10));
        game.setServiceFeeDesc(( String ) record.get(14));
        game.setServiceUrl(( String ) record.get(15));
        game.setServiceFeeType(( String ) record.get(16));
        game.setGameType(( String ) record.get(17));
        game.setGameTypeDesc(( String ) record.get(18));
        game.setServiceFlag(( String ) record.get(19));
        game.setPtypeId(( String ) record.get(20));

        // 得到mm游戏类型。为gametype影射转换
        game.setMmGameType(GameSyncTools.getInstance()
                                        .getMMCateName(game.getGameTypeDesc()));
    }

    /**
     * 添加数据至T_GAME_TW_TEMP
     * 
     * @param game
     * @throws BOException
     */
    public void addData(TwGameKeyVO game) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("addData() is starting ...");
        }

        // insert into T_GAME_TW (cpid, Cpname, CPSERVICEID, SERVICENAME,
        // SERVICESHORTNAME, SERVICEDESC, OPERATIONDESC, SERVICETYPE,
        // SERVICEPAYTYPE, SERVICESTARTDATE, SERVICEENDDATE, SERVICESTATUS, FEE,
        // SERVICEFEEDESC, SERVICE_URL, SERVICEFEETYPE, GAMETYPE, GAMETYPE_DESC,
        // SERVICEFLAG, PTYPEID, MMGAMETYPE) values (?, ?, ?, ?, ?, ?, ?, ?, ?,
        // ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        String sqlCode = "game.TweGameDealer.addData().INSERT";

        try
        {
            DB.getInstance()
              .executeBySQLCode(sqlCode,
                                new Object[] { game.getCpId(),
                                                game.getCpName(),
                                                game.getCpServiceId(),
                                                game.getServiceName(),
                                                game.getServiceShortName(),
                                                game.getServiceDesc(),
                                                game.getOperationDesc(),
                                                game.getServiceType(),
                                                game.getServicePayType(),
                                                game.getServiceStartDate(),
                                                game.getServiceEndDate(),
                                                game.getServiceStatus(),
                                                game.getFee(),
                                                game.getServiceFeeDesc(),
                                                game.getServiceUrl(),
                                                game.getServiceFeeType(),
                                                game.getGameType(),
                                                game.getGameTypeDesc(),
                                                game.getServiceFlag(),
                                                game.getPtypeId(),
                                                game.getMmGameType() });
        }
        catch (DAOException e)
        {
            logger.error("添加数据至T_GAME_TW时发生错误：", e);
            throw new BOException("添加数据至T_GAME_TW时发生错误：", e);
        }
    }

    /**
     * 根据图文游戏游戏类型得到转换后的mm游戏对应游戏类型
     * 
     * @param gameType
     * @return
     */
    public String getMmGameType(String gameType) throws BOException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("getMmGameType(" + gameType + ") is starting ...");
        }

        // select * from t_game_cate_mapping t where t.basecatename = ?
        String sqlCode = "game.TweGameDealer.getMmGameType().SELECT";
        ResultSet rs = null;

        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode,
                                                 new Object[] { gameType });

            if (rs.next())
            {
                return rs.getString("alilid");
            }
        }
        catch (DAOException e)
        {
            logger.error("根据图文游戏游戏类型得到转换后的mm游戏对应游戏类型时发生错误：", e);
            throw new BOException("根据图文游戏游戏类型得到转换后的mm游戏对应游戏类型时发生错误：", e);
        }
        catch (SQLException e)
        {
            logger.error("根据图文游戏游戏类型得到转换后的mm游戏对应游戏类型时发生错误：", e);
            throw new BOException("根据图文游戏游戏类型得到转换后的mm游戏对应游戏类型时发生错误：", e);
        }

        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataDealer#prepareData()
     */
    public void prepareData() throws Exception
    {
        logger.debug("用于执行清空原表的备份表T_GAME_TW");

        // 清空原表的备份表
        String backupTableSql = "delete from T_GAME_TW";

        logger.info("开始清空原表的备份表的表名 :T_GAME_TW");
        try
        {
            DB.getInstance().execute(backupTableSql, null);
        }
        catch (DAOException e)
        {

            throw new BOException("清空原表的备份表T_GAME_TW出错：", e);
        }
        
        GameSyncTools.getInstance().initGameCateMapping();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.aspire.ponaadmin.web.datasync.DataSyncBuilder#init(com.aspire.ponaadmin.web.datasync.DataSyncConfig)
     */
    public void init(DataSyncConfig config) throws Exception
    {
    }

    /**
     * 用于修改数据库中的表名
     * 
     * @param tableName
     * @param toTableName
     */
    public void renameByTable(String tableName, String toTableName)
                    throws BOException
    {
        // 修改数据库中的表名
        String backupTableSql = "alter table " + tableName + " rename to "
                                + toTableName;

        logger.info("开始修改数据库中的表名 :" + toTableName);
        try
        {
            DB.getInstance().execute(backupTableSql, null);
        }
        catch (DAOException e)
        {

            throw new BOException("修改数据库中的表名:" + tableName + "出错："
                                  + toTableName, e);
        }
    }
}
