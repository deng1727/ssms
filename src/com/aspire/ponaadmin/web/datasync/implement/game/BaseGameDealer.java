/*
 * 
 */

package com.aspire.ponaadmin.web.datasync.implement.game;

import java.util.HashMap;
import java.util.Map;

import com.aspire.common.config.ArrayValue;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
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
public class BaseGameDealer implements DataDealer
{

    /**
     * ��־����
     */
    private static final JLogger logger = LoggerFactory.getLogger(BaseGameDealer.class);

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

        BaseGameVo game = new BaseGameVo();

        // ��װ���ݶ�����Ϣ
        baseGameFormat(game, record);

        // ���Դ洢ϵͳ���Ƿ��д�����
        boolean isHas = true;

        try
        {
            // ����PkgId�鿴�����Ƿ����
            isHas = BaseGameDao.getInstance().isExistedVO(game, false);
        }
        catch (DAOException e1)
        {
            logger.error("������Ϸ��ID" + game.getPkgId()
                         + "��ѯ�Ƿ������ϵͳ��ʱ���������ݿ��쳣����Ӧ�ļ�¼���Դ���");
        }
        // ����
        if ("1".equals(game.getChangeType()))
        {
            return insertBaseGame(game, isHas);
        }
        // ����
        else if ("2".equals(game.getChangeType()))
        {
            return updateBaseGame(game, isHas);
        }
        // ɾ��
        else if ("3".equals(game.getChangeType()))
        {
            return deleteBaseGame(game, isHas);
        }
        else
        {
            // �쳣�����¼������Ϣ��������
            logger.error("PkgId" + game.getPkgId()
                         + "��Changetype��������,Changetype=" + game.getChangeType());
            return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
        }
    }

    /**
     * ���ļ����ݶ���װ����Ϸ�����ݶ���
     * @param game ��Ϸ�����ݶ���
     * @param record �ļ����ݶ���Ϣ
     */
    private void baseGameFormat(BaseGameVo game, DataRecord record)
    {
        game.setPkgId(( String ) record.get(1));
        game.setPkgName(( String ) record.get(2));
        game.setPkgDesc(( String ) record.get(3));
        game.setCpName(( String ) record.get(4));
        game.setServiceCode(( String ) record.get(5));
        
        int temp = Integer.valueOf((String)record.get(6)).intValue();
        
        // �ɷֱ����
        temp = temp * 10;
        game.setFee(String.valueOf(temp));
        
        game.setPkgURL(( String ) record.get(7));
        game.setPicurl1(( String ) record.get(8));
        game.setPicurl2(( String ) record.get(9));
        game.setPicurl3(( String ) record.get(10));
        game.setPicurl4(( String ) record.get(11));
        game.setChangeType(( String ) record.get(12));
        
        if(record.size() == 13)
        {
            game.setProvinceCtrol(formatProvinceCtrol((String)record.get(13)));
        }
        else
        {
            game.setProvinceCtrol("");
        }
    }
    
    /**
     * ����ת��������Ϸ���г������ն˳���ƥ���ϵ
     * @param provinceCtrol ������Ϸ���г�����Ϣ
     * @return
     */
    private String formatProvinceCtrol(String provinceCtrol)
    {
        Map proM = getProvinceCtrolMap();
        StringBuffer sb = new StringBuffer();
        
        // ����ǿա����ؿ�
        if("".equals(provinceCtrol))
        {
            return "";
        }
        
        // �ֽ���Ϸ������
        String[] gameP = provinceCtrol.split(",");
        
        // ת��Ϊmo����id
        for (int i = 0; i < gameP.length; i++)
        {
            String temp = gameP[i];
            String  mcity = (String)proM.get(temp);
            if(mcity != null &&!mcity.equals("")){
            	sb.append("{").append(proM.get(temp)).append("}").append(",");
            	}
        }
        
        sb = sb.deleteCharAt(sb.length()-1);
        
        return sb.toString();
    }
    
    /**
     * ���ڻ�ó��ж�Ӧ��ϵ
     * @return
     */
    private Map getProvinceCtrolMap()
    {
        Map m = new HashMap();

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("ssms");
        ArrayValue[] typeArray = module.getArrayItem("BaseGameProvinceCtrol")
                                       .getArrayValues();

        if (null != typeArray)
        {
            for (int i = 0; i < typeArray.length; i++)
            {
                String tmp = typeArray[i].getValue();
                String gamePro = tmp.split("[|]")[0];
                String moPro = tmp.split("[|]")[1];
                m.put(gamePro, moPro);
            }
        }

        return m;
    }
    
    /**
     * ������Ϸ������
     * @param game ��Ϸ������
     * @param isHas �����Ƿ���ڴ�����
     * @return
     */
    private int insertBaseGame(BaseGameVo game, boolean isHas)
    {

        if (isHas)
        {
            logger.error("������Ϸ������ʧ�ܣ�PkgId=" + game.getPkgId() + "��ϵͳ�Ѵ��ڸ�����");
            return DataSyncConstants.FAILURE_ADD_EXIST;
        }
        try
        {
            // �Ƿ���ڴ�game��״̬Ϊɾ��������
            if(BaseGameDao.getInstance().isExistedVO(game, true))
            {
                // �����Ѵ�����״̬
                BaseGameDao.getInstance().insertBaseGameByDelVo(game);
            }
            else
            {
                // ������Ϸ�����ݵ����ݿ�
                BaseGameDao.getInstance().insertBaseGameVo(game);
            }
        }
        catch (DAOException e)
        {
            logger.error("��Ϸ��ID" + game.getPkgId() + "��Ӧ�ļ�¼���ʱ���������ݿ��쳣������ʧ��", e);
            return DataSyncConstants.FAILURE_ADD;
        }
        return DataSyncConstants.SUCCESS_ADD;
    }

    /**
     * �������ݿ�����Ϸ������
     * @param game ��Ϸ������
     * @param isHas �����Ƿ���ڴ�����
     * @return
     */
    private int updateBaseGame(BaseGameVo game, boolean isHas)
    {
        if (!isHas)
        {
            logger.error("������Ϸ������ʧ�ܣ�PkgId=" + game.getPkgId() + "��ϵͳ�����ڸ�����");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        try
        {
            // �������ݿ�������
            BaseGameDao.getInstance().updateBaseGameVo(game);
        }
        catch (DAOException e)
        {
            logger.error("������Ϸ�����ݳ���,PkgId=" + game.getPkgId(), e);
            return DataSyncConstants.FAILURE_UPDATE;
        }
        return DataSyncConstants.SUCCESS_UPDATE;
    }
    
    /**
     * ɾ����Ϸ������
     * @param game ��Ϸ������
     * @param isHas �����Ƿ���ڴ�����
     * @return
     */
    private int deleteBaseGame(BaseGameVo game, boolean isHas)
    {
        if (!isHas)
        {
            logger.error("ɾ����Ϸ������ʧ�ܣ�PkgId=" + game.getPkgId() + "��ϵͳ�����ڸ�����");
            return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
        }
        // ����old���ݶ�Ӧ��id�����Ҷ�Ӧ����Ʒ
        try
        {
            // ɾ�����ݿ�������
            BaseGameDao.getInstance().deleteBaseGameVo(game);
        }
        catch (DAOException e)
        {
            logger.error("ɾ����Ϸ�����ݳ���game=" + game.getPkgId(), e);
            return DataSyncConstants.FAILURE_DEL;
        }
        return DataSyncConstants.SUCCESS_DEL;
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
