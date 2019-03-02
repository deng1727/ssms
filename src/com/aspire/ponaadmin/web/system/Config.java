package com.aspire.ponaadmin.web.system;

import com.aspire.common.config.SystemConfig;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ConfigDataListener;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.config.Item;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * <p>配置信息类</p>
 * <p>配置信息类</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class Config implements ConfigDataListener{

    /**
     * 日志对象。
     */
    protected static JLogger logger = LoggerFactory.getLogger(Config.class);

    /**
     * singleton模式的实例
     */
    private static Config instance = new Config();

    /**
     * 构造方法，由singleton模式调用
     */
    private Config(){
    }

    /**
     * 获取实例
     * @return 实例
     */
    public static Config getInstance(){
        return instance;
    }

    /**
     * 系统配置信息实例
     */
    protected SystemConfig systemConfig = null;

    /**
     * ponaadmin_www自身的配置信息
     */
    protected ModuleConfig ponaadminConfig = null;

    /**
     * 初始化方法
     */
    public void init(){
        this.systemConfig = ConfigFactory.getSystemConfig();
        this.ponaadminConfig = systemConfig.getModuleConfig(ServerInfo.
                                                            getSystemName());
        this.systemConfig.addConfigDataListener(this);
    }

    /**
     *  返回ponaadmin_www自身的配置信息
     * @return ModuleConfig  ponaadmin_www自身的配置信息
     */
    public ModuleConfig getModuleConfig(){
        return this.ponaadminConfig;
    }

    /**
     * 修改配置项的值
     * @param itemName String，配置项的名称
     * @param itemValue String，配置项的值
     */
    public void modifyItemConfig(String itemName, String itemValue){
        Item item = this.ponaadminConfig.getItem(itemName);
        item.setValue(itemValue);
        this.systemConfig.modifyItem(this.ponaadminConfig.getModuleName(), item);

    }

    /**
     * 覆盖接口ConfigDataListener的方法，当配置文件被修改后的刷新配置项方法
     * @see com.aspire.common.config.ConfigDataListener#doConfigRefresh()
     */
    public void doConfigRefresh(){
        //刷新配置项
    }

    /**
     * 覆盖接口ConfigDataListener的方法，提供配置监听器的名称
     * @return String 监听器的名称
     * @see com.aspire.common.config.ConfigDataListener#getListenerName()
     */
    public String getListenerName(){
        return "ponaadmin config";
    }

    /**
     * 读取ssms配置模块下的某个配置项的String值
     * @param itemName String，配置项名称
     * @return String
     */
    public String readConfigItem(String itemName)
    {
        try
        {
            String strItemValue = this.getModuleConfig().getItemValue(itemName);
            if(logger.isDebugEnabled()){
                logger.debug("Get " + itemName + " from CFG: " + strItemValue);
            }
            return strItemValue;
        } catch(Exception ex)
        {
            logger.error("ConfigItem[" + itemName + "] is not proper configed!",
                         ex);
            return null;
        }
    }

    /**
     * 读取ssms配置模块下的某个配置项的int值
     * @param itemName String，配置项名称
     * @param defaultValue int，缺省值
     * @return int
     */
    public int getConfigIntValue(String itemName, int defaultValue){
        int iItemValue = defaultValue;
        try{
            String strItemValue = this.getModuleConfig().getItemValue(itemName);
            iItemValue = Integer.parseInt(strItemValue);
            if(logger.isDebugEnabled()){
                logger.debug("Get " + itemName + " from CFG: " + iItemValue);
            }

        } catch(Exception ex){
            logger.error("ConfigItem[" + itemName + "] is not proper configed!",
                         ex);
        }
        return iItemValue;
    }

}
