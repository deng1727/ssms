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
     * ��־����
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

        // ��װ���ݶ�����Ϣ
        baseGameStatFormat(game, record);

        // ���Դ洢ϵͳ���Ƿ��д�����
        boolean isHas = true;

        try
        {
            // ������Ϸid�鿴�����Ƿ����
            isHas = BaseGameStatDAO.getInstance().isExistedVO(game);
        }
        catch (DAOException e1)
        {
            logger.error("������ϷID" + game.getGameId()
                         + "��ѯ�Ƿ������ϵͳ��ʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        // ����
        if(isHas)
        {
            return insertBaseGame(game);
        }
        else
        {
            // �쳣�����¼������Ϣ��������
            logger.error("������Ϸ����ʧ�ܣ�gameId=" + game.getGameId() + "��ϵͳ�����ڸ�����");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
    }

    /**
     * ���ļ����ݶ���װ����Ϸ�����ݶ���
     * @param game ��Ϸ�����ݶ���
     * @param record �ļ����ݶ���Ϣ
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
     * ������Ϸ������
     * @param game ��Ϸ����
     * @param isHas �����Ƿ���ڴ�����
     * @return
     */
    private int insertBaseGame(BaseGameStatVO game)
    {
        boolean isHas = false;
        
        // ��t_r_gcontent���б������
        try
        {
            BaseGameStatDAO.getInstance().updteGameContent(game);
        }
        catch (DAOException e)
        {
            logger.error("������ϷID" + game.getGameId()
                         + "����������Ϣʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        // ���±��п����Ƿ��д�gameid��Ϣ
        try
        {
            isHas = BaseGameStatDAO.getInstance().isHasGame(game.getGameId());
        }
        catch (DAOException e)
        {
            logger.error("������ϷID" + game.getGameId()
                         + "��ѯ������Ϣʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        
        // �����,���û��
        try
        {
            BaseGameStatDAO.getInstance().updateGameData(game, isHas);
        }
        catch (DAOException e)
        {
            logger.error("������ϷID" + game.getGameId()
                         + "�����Ϸ���ӱ�������Ϣʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
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
