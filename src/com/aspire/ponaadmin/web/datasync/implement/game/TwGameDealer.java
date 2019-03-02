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
     * ��־����
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
            logger.debug("��T_GAME_TW�ĳ� T_GAME_TW_TEMP1 ...");
        }

        // ����ʽ��ĳ� table_temp1
        try
        {
            renameByTable("T_GAME_TW", "T_GAME_TW_TEMP1");
        }
        catch (BOException e)
        {
            logger.error("��T_GAME_TW�ĳ� T_GAME_TW_TEMP1ʱ��������", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("��T_GAME_TW_TEMP�ĳ� T_GAME_TW ...");
        }

        // ��table_temp ����Ϊtable
        try
        {
            renameByTable("T_GAME_TW_TEMP", "T_GAME_TW");
        }
        catch (BOException e)
        {
            logger.error("��T_GAME_TW_TEMP�ĳ� T_GAME_TWʱ��������", e);

            try
            {
                // �ָ�״̬
                renameByTable("T_GAME_TW_TEMP1", "T_GAME_TW");
            }
            catch (BOException e1)
            {
                e1.printStackTrace();
            }
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("��T_GAME_TW_TEMP1�ĳ� T_GAME_TW_TEMP ...");
        }

        // table_temp1 ����Ϊtable_temp
        try
        {
            renameByTable("T_GAME_TW_TEMP1", "T_GAME_TW_TEMP");
        }
        catch (BOException e)
        {
            logger.error("��T_GAME_TW_TEMP1�ĳ� T_GAME_TW_TEMPʱ��������", e);

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
        // ͼ����Ϸ����
        TwGameKeyVO game = new TwGameKeyVO();

        // ��װ���ݶ�����Ϣ
        twGameFormat(game, record);

        // �������ݿ���T_GAME_TW_TEMP��
        addData(game);

        return DataSyncConstants.SUCCESS_ADD;

    }

    /**
     * ���ļ����ݶ���װ��ͼ����Ϸ���ݶ���
     * 
     * @param game ͼ����Ϸ���ݶ���
     * @param record �ļ����ݶ���Ϣ
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

        // �õ�mm��Ϸ���͡�ΪgametypeӰ��ת��
        game.setMmGameType(GameSyncTools.getInstance()
                                        .getMMCateName(game.getGameTypeDesc()));
    }

    /**
     * ���������T_GAME_TW_TEMP
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
            logger.error("���������T_GAME_TWʱ��������", e);
            throw new BOException("���������T_GAME_TWʱ��������", e);
        }
    }

    /**
     * ����ͼ����Ϸ��Ϸ���͵õ�ת�����mm��Ϸ��Ӧ��Ϸ����
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
            logger.error("����ͼ����Ϸ��Ϸ���͵õ�ת�����mm��Ϸ��Ӧ��Ϸ����ʱ��������", e);
            throw new BOException("����ͼ����Ϸ��Ϸ���͵õ�ת�����mm��Ϸ��Ӧ��Ϸ����ʱ��������", e);
        }
        catch (SQLException e)
        {
            logger.error("����ͼ����Ϸ��Ϸ���͵õ�ת�����mm��Ϸ��Ӧ��Ϸ����ʱ��������", e);
            throw new BOException("����ͼ����Ϸ��Ϸ���͵õ�ת�����mm��Ϸ��Ӧ��Ϸ����ʱ��������", e);
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
        logger.debug("����ִ�����ԭ��ı��ݱ�T_GAME_TW");

        // ���ԭ��ı��ݱ�
        String backupTableSql = "delete from T_GAME_TW";

        logger.info("��ʼ���ԭ��ı��ݱ�ı��� :T_GAME_TW");
        try
        {
            DB.getInstance().execute(backupTableSql, null);
        }
        catch (DAOException e)
        {

            throw new BOException("���ԭ��ı��ݱ�T_GAME_TW����", e);
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
     * �����޸����ݿ��еı���
     * 
     * @param tableName
     * @param toTableName
     */
    public void renameByTable(String tableName, String toTableName)
                    throws BOException
    {
        // �޸����ݿ��еı���
        String backupTableSql = "alter table " + tableName + " rename to "
                                + toTableName;

        logger.info("��ʼ�޸����ݿ��еı��� :" + toTableName);
        try
        {
            DB.getInstance().execute(backupTableSql, null);
        }
        catch (DAOException e)
        {

            throw new BOException("�޸����ݿ��еı���:" + tableName + "����"
                                  + toTableName, e);
        }
    }
}
