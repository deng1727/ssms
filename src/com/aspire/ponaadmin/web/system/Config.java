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
 * <p>������Ϣ��</p>
 * <p>������Ϣ��</p>
 * <p>Copyright (c) 2003-2005 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved</p>
 * @author shidr
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class Config implements ConfigDataListener{

    /**
     * ��־����
     */
    protected static JLogger logger = LoggerFactory.getLogger(Config.class);

    /**
     * singletonģʽ��ʵ��
     */
    private static Config instance = new Config();

    /**
     * ���췽������singletonģʽ����
     */
    private Config(){
    }

    /**
     * ��ȡʵ��
     * @return ʵ��
     */
    public static Config getInstance(){
        return instance;
    }

    /**
     * ϵͳ������Ϣʵ��
     */
    protected SystemConfig systemConfig = null;

    /**
     * ponaadmin_www�����������Ϣ
     */
    protected ModuleConfig ponaadminConfig = null;

    /**
     * ��ʼ������
     */
    public void init(){
        this.systemConfig = ConfigFactory.getSystemConfig();
        this.ponaadminConfig = systemConfig.getModuleConfig(ServerInfo.
                                                            getSystemName());
        this.systemConfig.addConfigDataListener(this);
    }

    /**
     *  ����ponaadmin_www�����������Ϣ
     * @return ModuleConfig  ponaadmin_www�����������Ϣ
     */
    public ModuleConfig getModuleConfig(){
        return this.ponaadminConfig;
    }

    /**
     * �޸��������ֵ
     * @param itemName String�������������
     * @param itemValue String���������ֵ
     */
    public void modifyItemConfig(String itemName, String itemValue){
        Item item = this.ponaadminConfig.getItem(itemName);
        item.setValue(itemValue);
        this.systemConfig.modifyItem(this.ponaadminConfig.getModuleName(), item);

    }

    /**
     * ���ǽӿ�ConfigDataListener�ķ������������ļ����޸ĺ��ˢ���������
     * @see com.aspire.common.config.ConfigDataListener#doConfigRefresh()
     */
    public void doConfigRefresh(){
        //ˢ��������
    }

    /**
     * ���ǽӿ�ConfigDataListener�ķ������ṩ���ü�����������
     * @return String ������������
     * @see com.aspire.common.config.ConfigDataListener#getListenerName()
     */
    public String getListenerName(){
        return "ponaadmin config";
    }

    /**
     * ��ȡssms����ģ���µ�ĳ���������Stringֵ
     * @param itemName String������������
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
     * ��ȡssms����ģ���µ�ĳ���������intֵ
     * @param itemName String������������
     * @param defaultValue int��ȱʡֵ
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
