package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 增加宏定义功能
// @CheckItem@ OPT-hecs-20040212 功能修改:配置项被修改后,它在配置文件中的位置和web页面中的位置保持不变

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class SystemConfig
    implements java.lang.Runnable
{
    private static JLogger logger = LoggerFactory.getLogger(SystemConfig.class);
    private static String configFileName = null;
    private static Document sysDocument = null;
    private static Element sysElement = null;
    private static Element marcoElement = null;
    private static MarcoDef marco = null;
    private Vector dataListeners = new Vector();

    /**
     * 默认构造器：加载文件，注册MBean
     */
    public SystemConfig() {

    }

    /**
     * 设置配置文件的路径名称，JMX接口。
     * @param file 配置文件的路径名称
     */
    public void setConfigFile(String file) {
        configFileName = file;
        ServerInfo.configFileName = file;
    }

    public String getConfigFileName() {
        if (configFileName == null || configFileName.length() == 0) {
            configFileName = ServerInfo.configFileName;
        }

        return configFileName;
    }

    public void init() {
        new Thread(this).start();
    }

    /**
     * 异步加载配置文件.
     */
    public void run ()
    {
    }

    /**
     * 加载配置文件
     */
    public synchronized void load() {
        try {
            SAXBuilder builder = new SAXBuilder();
            sysDocument = builder.build(getConfigFileName());
            sysElement = sysDocument.getRootElement();
            marcoElement = sysElement.getChild("MarcoDefine");
            marco = new MarcoDef(marcoElement);
            doRefresh();
            logger.info("SystemConfig load config file: " + configFileName);
        }
        catch (Exception ex) {
            logger.info("Error: SystemConfig load config file failed. " +
                        ex.getMessage());
            logger.error(ex);
        }
    }

    /**
     * 将内存配置信息保存之配置文件，覆盖员文件
     */
    //@CheckLine-20031022-oam-hecs:save方法都是在synchronized方法中调用的，它自己不需线程同步。
    public void save() {

        try {
            XMLOutputter outp = new XMLOutputter();
            FileOutputStream outStream = new FileOutputStream(configFileName);
            outp.setTextTrim(true);
            outp.setLineSeparator(System.getProperties().getProperty(
                "line.separator"));
            // @CheckItem@ OPT-ZhangJi-200400428 修改输出文件编码为GB2312，不用GBK，修正在Linux下无法识别GBK的问题
            outp.setEncoding("GB2312");
            outp.setIndent("    ");
            outp.setNewlines(true);
            outp.output(sysDocument, outStream);
            outStream.close();
            this.doRefresh();
            logger.info("SystemConfig new config file saved");
        }
        catch (IOException ex) {
            logger.info("Error: SystemConfig save file failed. " +
                        ex.getMessage());
            logger.error(ex);
        }
    }

    /**
     * 构造并返回一个子系统配置类，包含该子系统的所有配置项。
     * @param moduleName 子系统名称
     * @return 子系统配置类
     */
    public ModuleConfig getModuleConfig(String moduleName) {
        checkConfig(moduleName);
        ModuleConfig mConfig = null;
        String mName = null;
        List list = sysElement.getChildren("ModuleConfig");
        Iterator itrts = list.iterator();
        while (itrts.hasNext()) {
            Element element = (Element) itrts.next();
            mName = element.getAttribute("Name").getValue();
            if (mName.equalsIgnoreCase(moduleName)) {
                mConfig = new ModuleConfig(element);
                break;
            }
        }
        return mConfig;
    }

    private void checkConfig(String moduleName) {
        if (sysDocument == null || sysElement == null) {
            load();
            return;
        }
        List list = sysElement.getChildren("ModuleConfig");
        if (list == null) {
            load();
        }
    }

    /**
     * 取得所有的子系统配置类列表
     * @return 子系统配置类：ModuleConfig数组
     */
    public ModuleConfig[] getModuleConfigs() {
        ModuleConfig[] mConfigs = null;
        List list = sysElement.getChildren("ModuleConfig");
        mConfigs = new ModuleConfig[list.size()];
        Iterator itrts = list.iterator();
        int i = 0;
        while (itrts.hasNext()) {
            Element element = (Element) itrts.next();
            mConfigs[i] = new ModuleConfig(element);
            i++;
        }
        return mConfigs;
    }

    /**
     * 返回当前的宏定义类实例
     * @return
     */
    public MarcoDef getMarcoConfig(){
        return marco;
    }

    /**
     * 新增一个子系统配合集合
     * @param module ModuleConfig类
     */
    public synchronized void addModuleConfig(ModuleConfig module) throws
        ConfigException {
        if (this.getModuleConfig(module.getModuleName()) != null) {
            throw new ConfigException("Duplicated name tag:" +
                                      module.getModuleName());
        }
        else {
            sysElement.addContent(module.getElement());
            this.save();
        }

    }

    /**
     * 删除一个子系统配置
     * @param moduleName
     */
    public synchronized void removeModuleConfig(String moduleName) {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        if (mcfg == null) {
            return;
        }
        sysElement.removeContent(mcfg.getElement());
        this.save();
    }

    /**
     * 修改子系统配置的属性
     * @param module 子系统名称
     */
    public synchronized void modifyModuleConfig(ModuleConfig module) {
        ModuleConfig mcfg = this.getModuleConfig(module.getModuleName());
        mcfg.setDescription(module.getDescription());
    }

    /**
     * 修改特定配置项的值
     * @param moduleName
     * @param item
     */
    public synchronized void modifyItem(String moduleName, Item item) {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        mcfg.getItem(item.getName() ).modify(item) ;
//        try {
//            mcfg.addItem(item);
//        }
//        catch (ConfigException ex) {
//            logger.info("Error: AddItem failed." + ex.getMessage());
//            logger.error(OAMErrorCode._EC_OAM_ADD_ITEM_ERR, ex);
//        }
        this.save();
    }

    /**
     * 修改指定的多值配置项
     * @param moduleName
     * @param aItem
     */
    public synchronized void modifyArrayItem(String moduleName, ArrayItem aItem) {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        mcfg.getArrayItem(aItem.getName() ).modify(aItem) ;
        this.save();
    }

    /**
     *  增加一个配置项
     * @param moduleName
     * @param item
     * @throws ConfigException
     */
    public synchronized void addItem(String moduleName, Item item) throws
        ConfigException {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        if (mcfg.getItem(item.getName()) != null) {
            throw new ConfigException("Duplicated name tag:" + item.getName());
        }
        else {
            mcfg.getElement().addContent(item.getElement());
            this.save();
        }

    }

    public synchronized void addArrayItem(String moduleName, ArrayItem aitem) throws
        ConfigException {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        if (mcfg.getItem(aitem.getName()) != null) {
            throw new ConfigException("Duplicated name tag:" + aitem.getName());
        }
        else {
            mcfg.getElement().addContent(aitem.getElement());
            this.save();
        }
    }

    /**
     * 删除一个配置项
     * @param moduleName 子系统名称
     * @param itemName 配置项名称
     */
    public synchronized void removeItem(String moduleName, String itemName) {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        Item item = mcfg.getItem(itemName);
        if (item != null) {
            mcfg.removeItem(itemName);
        }
        else {
            mcfg.removeArrayItem(itemName);
        }
        this.save();
    }

    /**
     * 注册一个配置数据监听器
     * @param listener
     */
    public synchronized void addConfigDataListener(ConfigDataListener listener) {
        Object obj = getConfigObj(listener.getListenerName());
        if (obj != null) {
            this.removeListener(listener.getListenerName());
        }
        dataListeners.add(listener);
        logger.debug("New config listener added: " + listener.getListenerName() + " Class: " + listener.getClass().getName());
    }

    /**
     * get a registered config ConfigListener in the dataListeners
     * @param listenerName
     * @return Object: if found .null: not found
     */
    public synchronized Object getConfigListener(String listenerName) {
        return getConfigObj(listenerName);
    }

    /**
     * 取消一个配置数据监听器
     * @param listener
     */
    public synchronized void removeConfigDataListener(ConfigDataListener
        listener) {
        this.dataListeners.remove(listener);
        logger.info("New config listener removed: " + listener.getListenerName());

    }

    /**
     * 调用每个已注册的ConfigDataListener实例的刷新函数，刷新配置。
     */
// @CheckLine@ REQOAM-hecs-20030905 这里不需线程同步。
    private void doRefresh() {
        ConfigDataListener listener = null;
        int size = dataListeners.size();
        for (int i = 0; i < size; i++) {
            try {
                listener = (ConfigDataListener) dataListeners.get(i);
                logger.debug("i=" + i + " System Config: " +
                             listener.getListenerName() + " Class: " + listener.getClass().getName() +
                             " doConfigRefresh call begin!");
                listener.doConfigRefresh();
            }
            catch (Throwable ex) {
                logger.error(ex);
            }
            logger.debug("i=" + i + " System Config: " +
                         listener.getListenerName() + " Class: " + listener.getClass().getName() +
                         " doConfigRefresh called finished!");

        }
    }

    /**
     * 返回整个系统配置
     * @return Jdom root Element
     */
    protected org.jdom.Document getDocument() {
        return sysDocument;
    }

    public String getLocalAppRootPath() {
        return ServerInfo.getAppRootPath();
    }

    /**
     * Remove the specified listener
     * @param listenerName
     */
    private void removeListener(String listenerName) {
        ConfigDataListener listener = null;
        Vector tmpListeners = (Vector) dataListeners.clone();
        int size = tmpListeners.size();
        for (int i = 0; i < size; i++) {
            listener = (ConfigDataListener) tmpListeners.get(i);
            if (listener.getListenerName().equals(listenerName)) {
                dataListeners.remove(listener);
                logger.debug("Listener removed: " + listenerName + " Class: " + listener.getClass().getName());
                break;
            }
        }

    }

    /**
     * get a registered config object in the dataListeners
     * @param listenerName
     * @return Object: if found .null: not found
     */
    private Object getConfigObj(String listenerName) {
        Object obj = null;
        ConfigDataListener listener = null;
        int size = dataListeners.size();
        for (int i = 0; i < size; i++) {
            listener = (ConfigDataListener) dataListeners.get(i);
            if (listener.getListenerName().equals(listenerName)) {
                obj = dataListeners.get(i);
                logger.debug("getConfigObj. Found Listener: " + listenerName);
                break;
            }
        }
        return obj;
    }

    /**
     * get all the config listener name array
     * @return String array.
     */
    public synchronized ConfigDataListener[] getListenerNames() {
        int size = dataListeners.size();
        ConfigDataListener[] names = new ConfigDataListener[size];
        ConfigDataListener listener = null;
        for (int i = 0; i < size; i++) {
            listener = (ConfigDataListener) dataListeners.get(i);
            names[i] = listener;
        }
        return names;
    }

    /**
     * Clear all config listeners from memory. This method only be call when this application
     * been undeployed.
     */
    public void clearListeners() {
        dataListeners.clear();
    }

    /**
     * Return total count of ConfigDataListeners registered in the OAM
     * @return count of listeners
     */
    public int getListenerCount() {
        return dataListeners.size();
    }

    /**
     * For debug use
     */
    protected void finalize() {
        logger.debug("OAM SystemConfig stoped !");
    }

    /**
     * 增加一个宏定义
     * @param name  宏名称
     * @param value 宏值
     * @throws ConfigException
     */
    public void addMarco(String name, String value,String desc) throws ConfigException
    {
        if( marcoElement == null){
            marcoElement = new Element("MarcoDefine");
            sysElement.addContent(marcoElement);
            marco.setElement(marcoElement) ;
        }
        marco.addMarco(name,value,desc) ;
        this.save();
   }

    /**
     * 修改一个宏定义
     * @param name 宏名称
     * @param value 宏值
     */
    public void modifyMarco(String name,String value,String desc)
    {
        marco.modifyMarco(name,value,desc);
        this.save();
    }

    /**
     * 删除一个宏定义
     * @param name 宏名称
     */
    public void removeMarco(String name) throws ConfigException
    {
        if(this.isFileContains("${"+name+"}") ){
            throw new ConfigException("Marco:"+name+" currently in use, can't be removed!");
        }else{
            marco.removeMarco(name);
            this.save();
        }
    }

    /**
     * 检查配置文件是否包含指定的字符串
     * @param ss
     * @return
     */
    private boolean isFileContains(String ss){
        boolean res = false;
        java.io.FileReader fr = null;
        try {
            fr = new FileReader(this.getConfigFileName());
            char[] cs = new char[1024];
            String tmp = null;
            while((fr.read(cs)) != -1){
                tmp = new String(cs);
                if(tmp.indexOf(ss) != -1){
                    res = true;
                    break;
                }
            }
        }
        catch (Exception ex) { }
        try {
            fr.close();
        }
        catch (IOException ex1) {
        }
        return res;
    }
}
