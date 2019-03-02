package com.aspire.common.config;

/**
 * <p>Title: Poralt OAM</p>
 * <p>Description: Portal OAM Program file</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author He Chengshou
 * @version 1.0
 */
// @CheckItem@ OPT-hecs-20040202 ���Ӻ궨�幦��
// @CheckItem@ OPT-hecs-20040212 �����޸�:������޸ĺ�,���������ļ��е�λ�ú�webҳ���е�λ�ñ��ֲ���

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
     * Ĭ�Ϲ������������ļ���ע��MBean
     */
    public SystemConfig() {

    }

    /**
     * ���������ļ���·�����ƣ�JMX�ӿڡ�
     * @param file �����ļ���·������
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
     * �첽���������ļ�.
     */
    public void run ()
    {
    }

    /**
     * ���������ļ�
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
     * ���ڴ�������Ϣ����֮�����ļ�������Ա�ļ�
     */
    //@CheckLine-20031022-oam-hecs:save����������synchronized�����е��õģ����Լ������߳�ͬ����
    public void save() {

        try {
            XMLOutputter outp = new XMLOutputter();
            FileOutputStream outStream = new FileOutputStream(configFileName);
            outp.setTextTrim(true);
            outp.setLineSeparator(System.getProperties().getProperty(
                "line.separator"));
            // @CheckItem@ OPT-ZhangJi-200400428 �޸�����ļ�����ΪGB2312������GBK��������Linux���޷�ʶ��GBK������
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
     * ���첢����һ����ϵͳ�����࣬��������ϵͳ�����������
     * @param moduleName ��ϵͳ����
     * @return ��ϵͳ������
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
     * ȡ�����е���ϵͳ�������б�
     * @return ��ϵͳ�����ࣺModuleConfig����
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
     * ���ص�ǰ�ĺ궨����ʵ��
     * @return
     */
    public MarcoDef getMarcoConfig(){
        return marco;
    }

    /**
     * ����һ����ϵͳ��ϼ���
     * @param module ModuleConfig��
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
     * ɾ��һ����ϵͳ����
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
     * �޸���ϵͳ���õ�����
     * @param module ��ϵͳ����
     */
    public synchronized void modifyModuleConfig(ModuleConfig module) {
        ModuleConfig mcfg = this.getModuleConfig(module.getModuleName());
        mcfg.setDescription(module.getDescription());
    }

    /**
     * �޸��ض��������ֵ
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
     * �޸�ָ���Ķ�ֵ������
     * @param moduleName
     * @param aItem
     */
    public synchronized void modifyArrayItem(String moduleName, ArrayItem aItem) {
        ModuleConfig mcfg = this.getModuleConfig(moduleName);
        mcfg.getArrayItem(aItem.getName() ).modify(aItem) ;
        this.save();
    }

    /**
     *  ����һ��������
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
     * ɾ��һ��������
     * @param moduleName ��ϵͳ����
     * @param itemName ����������
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
     * ע��һ���������ݼ�����
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
     * ȡ��һ���������ݼ�����
     * @param listener
     */
    public synchronized void removeConfigDataListener(ConfigDataListener
        listener) {
        this.dataListeners.remove(listener);
        logger.info("New config listener removed: " + listener.getListenerName());

    }

    /**
     * ����ÿ����ע���ConfigDataListenerʵ����ˢ�º�����ˢ�����á�
     */
// @CheckLine@ REQOAM-hecs-20030905 ���ﲻ���߳�ͬ����
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
     * ��������ϵͳ����
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
     * ����һ���궨��
     * @param name  ������
     * @param value ��ֵ
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
     * �޸�һ���궨��
     * @param name ������
     * @param value ��ֵ
     */
    public void modifyMarco(String name,String value,String desc)
    {
        marco.modifyMarco(name,value,desc);
        this.save();
    }

    /**
     * ɾ��һ���궨��
     * @param name ������
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
     * ��������ļ��Ƿ����ָ�����ַ���
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
