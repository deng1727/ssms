package com.aspire.dotcard.dcmpmgr;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
/**
 * ͬ��DCMP��������
 * @author zhangwei
 *
 */
public class FTPDCMPConfig
{
	JLogger logger=LoggerFactory.getLogger(FTPDCMPConfig.class);
	
	private String startTime;
	private String ftpIp;
	private String ftpPort;
	private String username;
	private String password;
	private String dateDir;
	private String emails [];
	private final static FTPDCMPConfig instance=new FTPDCMPConfig();
	private FTPDCMPConfig()
	{
		init();
	}
	public static FTPDCMPConfig getInstance()
	{
		return instance;
	}
	
	private void init()
	{
		String timeTemp=this.get("startTime");
		String defaultTime="20:00";
		if(timeTemp==null&&"".equals(timeTemp.trim()))
		{
			//Ĭ��Ϊ�賿4�㴥��
			startTime=defaultTime;
		}else
		{
		    startTime=timeTemp.trim();
		    if(!startTime.matches("[0-2]?[0-9]:[0-5][0-9]"))
		    {
		        startTime=defaultTime;
		        logger.error("dcmpͬ����ʼ���ڸ�ʽ����ϵͳִ��Ĭ��ͬ��ʱ�䣺"+startTime);
		    }
		}
		this.ftpIp=this.get("ftpIp");
		ftpPort=this.get("ftpPort");
		username=this.get("username");
		password=this.get("password");
		dateDir=this.get("dateDir");
		String emailStr=this.get("emails");
		this.emails=emailStr.split(";");
		
	}
	/**
     * ��ȡ���а���ص�������ֵ
     * 
     * @param key String
     * @return String
     */
    public  String get(String key)
    {

        ModuleConfig module = ConfigFactory.getSystemConfig()
                                           .getModuleConfig("FTPDCMPCONFIG");
        if (module == null)
        {
            return null;
        }
        String value = module.getItemValue(key);
        if(logger.isDebugEnabled())
        {
        	logger.debug("��ȡ���������:" + key + " == " + value);
        }
        return value;
    }

	public JLogger getLogger()
	{
		return logger;
	}

	public String getStartTime()
	{
		return startTime;
	}

	public String getFtpIp()
	{
		return ftpIp;
	}

	public String getFtpPort()
	{
		return ftpPort;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getDateDir()
	{
		return dateDir;
	}
	public String[] getEmails()
	{
		return emails;
	}

}
