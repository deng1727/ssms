package com.aspire.common.log.config;

/**
 * <p>Title: LogFileTransConfig</p>
 * <p>Description: the config for all parameter for
 * 1:local path
 * 2:remote IP,username,password
 * 3:remote path
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Aspire Technologies</p>
 * @author YanFeng
 * @version 1.0
 * history:
 * created at 26/4/2003
 * @CheckItem@ BUG-yanfeng-20031025 add new JMSRecvTopicName config for cluster enviroment
 * @CheckItem@ OPT-yanfeng-20031025 clarity the log config totally
 * @CheckItem@ OPT-yanfeng-20040921 add the config for alert switch
 */
import com.aspire.common.config.ConfigDataListener;
import com.aspire.common.config.SystemConfig;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.constants.LogErrorCode;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.config.ArrayItem;
import java.util.ArrayList;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.Item;
import com.aspire.common.config.ArrayValue;

public class LogConfig
    implements ConfigDataListener,LogErrorCode
{
    private static SystemConfig systemConfig=null;
    private static ModuleConfig logConfig=null;
    static JLogger log=LoggerFactory.getLogger(LogConfig.class);
    private static LogConfig config;

    private String oldLogChkTime="";
    private String oldLogExpireTime="";
    private ArrayItem items;

    private boolean alertAllowed;
    private LogConfig()
    {
        init();
    }
    public static LogConfig getInstance()
    {
        if(config==null)
        {
            config=new LogConfig();
        }
        return config;
    }
    private void init()
    {
        systemConfig=ConfigFactory.getSystemConfig();
        logConfig=systemConfig.getModuleConfig("LOG");
        this.doConfigRefresh();
        systemConfig.addConfigDataListener(this);
    }
    public String getListenerName()
    {
        return "log configuration";
    }
    public boolean validateSystemId(int systemId)
    {
        return true;
    }
    public void doConfigRefresh()
    {
        Item item=logConfig.getItem("OldLogChkTime");
        if (item!=null)
        {
            oldLogChkTime = item.getValue();
        }
        item=logConfig.getItem("OldLogExpireTime");
        if (item!=null)
        {
            oldLogExpireTime = item.getValue();
        }
        item=logConfig.getItem("AlertAllowed");
        String tmp="";
        if (item!=null)
        {
            tmp = item.getValue();
        }
        if ("true".equals(tmp))
        {
            alertAllowed=true;
        }
        else
        {
            alertAllowed=false;
        }
        items=logConfig.getArrayItem("OldLogReserved");

    }


    /**
     * �������õ�ÿ���೤ʱ������ʷ��־�Ƿ������
     * @return long,����Ϊ��λ
     */
    public long getOldLogChkTime()
    {
        long chkTime = 24*3600;//ȱʡ24Сʱ���һ��
        try
        {
            chkTime = Long.parseLong(oldLogChkTime);
            chkTime=chkTime*3600;
            if(chkTime <= 0)
            {
                chkTime = 24*3600;
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }

        return chkTime;
    }
    /**
     * ���ر�����ʷ��־���������
     * @return int ����Ϊ��λ
     */
    public long getOldLogExpireTime()
    {
        long expireTime = 7*24*3600;//ȱʡ������ʷ��־7��
        try
        {
            expireTime = Integer.parseInt(oldLogExpireTime);
            expireTime=expireTime*24*3600;
            if(expireTime <= 0)
            {
                expireTime = 7*24*3600;
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }

        return expireTime;
    }
    /**
     * �������û�ȡ��������־Ŀ¼
     * @return String[]
     */
    public ArrayList getOldLogReserved()
    {

        //log.debug("getOldLogReserved:"+items);
        ArrayList arr=new ArrayList();
        int i=0;
        if (items!=null)
        {
            ArrayValue[] vals = items.getArrayValues();
            if (vals!=null)
            {
                for(i = 0; i < vals.length; i++)
                {
                    String folderName = vals[i].getValue();
                    if((folderName != null) && !folderName.trim().equals(""))
                    { //�ǿ�
                        log.debug("reserved:"+folderName);
                        arr.add(folderName);
                    }
                }
            }
        }
        if(arr.size() == 0)
        {//������,ȱʡbizĿ¼
            arr.add("biz");
        }
        String[] folders=new String[arr.size()];
        for(i=0;i<arr.size();i++)
        {
            folders[i]=(String)arr.get(i);
        }
        return arr;
    }
    public boolean isAlertAllowed()
    {
        return alertAllowed;
    }


}
