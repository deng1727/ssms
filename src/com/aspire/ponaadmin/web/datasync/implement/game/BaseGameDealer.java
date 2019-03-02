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
     * 日志引用
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

        // 组装数据对象信息
        baseGameFormat(game, record);

        // 用以存储系统中是否有此内容
        boolean isHas = true;

        try
        {
            // 根据PkgId查看数据是否存在
            isHas = BaseGameDao.getInstance().isExistedVO(game, false);
        }
        catch (DAOException e1)
        {
            logger.error("根据游戏包ID" + game.getPkgId()
                         + "查询是否存在于系统中时发生了数据库异常，对应的记录忽略处理！");
        }
        // 新增
        if ("1".equals(game.getChangeType()))
        {
            return insertBaseGame(game, isHas);
        }
        // 更新
        else if ("2".equals(game.getChangeType()))
        {
            return updateBaseGame(game, isHas);
        }
        // 删除
        else if ("3".equals(game.getChangeType()))
        {
            return deleteBaseGame(game, isHas);
        }
        else
        {
            // 异常情况记录错误信息，不处理
            logger.error("PkgId" + game.getPkgId()
                         + "，Changetype类型有误,Changetype=" + game.getChangeType());
            return DataSyncConstants.FAILURE_NOT_CHANGETYPE;
        }
    }

    /**
     * 把文件数据段组装成游戏包数据对象
     * @param game 游戏包数据对象
     * @param record 文件数据段信息
     */
    private void baseGameFormat(BaseGameVo game, DataRecord record)
    {
        game.setPkgId(( String ) record.get(1));
        game.setPkgName(( String ) record.get(2));
        game.setPkgDesc(( String ) record.get(3));
        game.setCpName(( String ) record.get(4));
        game.setServiceCode(( String ) record.get(5));
        
        int temp = Integer.valueOf((String)record.get(6)).intValue();
        
        // 由分变成厘
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
     * 用于转换基地游戏包中城市与终端城市匹配关系
     * @param provinceCtrol 基地游戏包中城市信息
     * @return
     */
    private String formatProvinceCtrol(String provinceCtrol)
    {
        Map proM = getProvinceCtrolMap();
        StringBuffer sb = new StringBuffer();
        
        // 如果是空。返回空
        if("".equals(provinceCtrol))
        {
            return "";
        }
        
        // 分解游戏方城市
        String[] gameP = provinceCtrol.split(",");
        
        // 转化为mo城市id
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
     * 用于获得城市对应关系
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
     * 新增游戏包数据
     * @param game 游戏包数据
     * @param isHas 库中是否存在此数据
     * @return
     */
    private int insertBaseGame(BaseGameVo game, boolean isHas)
    {

        if (isHas)
        {
            logger.error("新增游戏包内容失败，PkgId=" + game.getPkgId() + "，系统已存在该内容");
            return DataSyncConstants.FAILURE_ADD_EXIST;
        }
        try
        {
            // 是否存在此game且状态为删除的数据
            if(BaseGameDao.getInstance().isExistedVO(game, true))
            {
                // 更新已存数据状态
                BaseGameDao.getInstance().insertBaseGameByDelVo(game);
            }
            else
            {
                // 保存游戏包内容到数据库
                BaseGameDao.getInstance().insertBaseGameVo(game);
            }
        }
        catch (DAOException e)
        {
            logger.error("游戏包ID" + game.getPkgId() + "对应的记录入库时发生了数据库异常，保存失败", e);
            return DataSyncConstants.FAILURE_ADD;
        }
        return DataSyncConstants.SUCCESS_ADD;
    }

    /**
     * 更新数据库中游戏包数据
     * @param game 游戏包数据
     * @param isHas 库中是否存在此数据
     * @return
     */
    private int updateBaseGame(BaseGameVo game, boolean isHas)
    {
        if (!isHas)
        {
            logger.error("更新游戏包内容失败，PkgId=" + game.getPkgId() + "，系统不存在该内容");
            return DataSyncConstants.FAILURE_UPDATE_NOT_EXIST;
        }
        try
        {
            // 更新数据库中数据
            BaseGameDao.getInstance().updateBaseGameVo(game);
        }
        catch (DAOException e)
        {
            logger.error("更新游戏包内容出错,PkgId=" + game.getPkgId(), e);
            return DataSyncConstants.FAILURE_UPDATE;
        }
        return DataSyncConstants.SUCCESS_UPDATE;
    }
    
    /**
     * 删除游戏包数据
     * @param game 游戏包数据
     * @param isHas 库中是否存在此数据
     * @return
     */
    private int deleteBaseGame(BaseGameVo game, boolean isHas)
    {
        if (!isHas)
        {
            logger.error("删除游戏包内容失败，PkgId=" + game.getPkgId() + "，系统不存在该内容");
            return DataSyncConstants.FAILURE_DEL_NOT_EXIST;
        }
        // 根据old内容对应的id，查找对应的商品
        try
        {
            // 删除数据库中数据
            BaseGameDao.getInstance().deleteBaseGameVo(game);
        }
        catch (DAOException e)
        {
            logger.error("删除游戏包数据出错，game=" + game.getPkgId(), e);
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
