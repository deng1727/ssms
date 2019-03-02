package com.aspire.dotcard.hwcolorring.clrLoad;

import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;
import com.aspire.common.config.ServerInfo;

public class ColorringConfig
{
	/**
	 * ��־����
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(ColorringConfig.class);

	private static final String PATH_SEP = System.getProperty("file.separator");
	/**
	 * ͳһ��������ƽ̨���ʵ�ַ
	 */
	public static String UCAPUrl = "";
	/**
	 * ��������ת�����ݸ�ʽ������������
	 */
	public static String[] code_abps = null;
	/**
	 * ��Դ����������ת����Ĳ�����Ŀ¼�����ftp��¼���Ŀ¼��
	 */
	public static String clrConvertPath = "";
	/**
	 * ��Դ�������ķ��ʷ�ʽ������������apache����·��
	 */
	public static String sourceServerVisit = "";
	/**
	 * ����ת��������߳���
	 */
	public static int ColorringConvertTaskNum;
	/**
	 * ������µ�����߳���
	 */
	public static int ColorringUpdateTaskNum;
	/**
	 * �������������ڶ��е��������
	 */
	public static int ColorringMaxReceivedNum;
	public static String sourceServerIP;
	public static int sourceServerPort;
	public static String sourceServerUser;
	public static String sourceServerPassword;
	static
	{
		UCAPUrl = get("UCAPUrl");
		code_abps = ConfigFactory.getSystemConfig().getModuleConfig("colorring")
				.getItemValueList("code_abps");
		clrConvertPath = get("clrConvertPath");
		sourceServerVisit = get("sourceServerVisit");
		ColorringConvertTaskNum = Integer.parseInt(get("ColorringConvertTaskNum"));
		ColorringUpdateTaskNum = Integer.parseInt(ColorringConfig.get("ColorringUpdateTaskNum"));
		ColorringMaxReceivedNum=Integer.parseInt(ColorringConfig.get("ColorringMaxReceivedNum"));
		sourceServerIP=ColorringConfig.get("sourceServerIP");
		sourceServerPort=Integer.parseInt(ColorringConfig.get("sourceServerPort"));
		sourceServerUser=ColorringConfig.get("sourceServerUser");
		sourceServerPassword = ColorringConfig.get("sourceServerPassword");
	}

	/**
	 * ��ȡ������ص�������ֵ
	 * @param key String
	 * @return String
	 */
	public static String get(String key)
	{
		ModuleConfig module = ConfigFactory.getSystemConfig()
				.getModuleConfig("colorring");
		if (module == null)
		{
			return null;
		}
		String value = module.getItemValue(key);
		if (LOG.isDebugEnabled())
		{
			LOG.debug("config value for [" + key + "] is [" + value + "].");
		}
		return value;
	}

	/**
	 * ��ȡ���������ļ��ڱ��صı���·��
	 * @return String
	 */
	public static String getColorDataFilePath()
	{
		String path = ServerInfo.getAppRootPath();
		path = PublicUtil.replace(path, "\\", PATH_SEP);
		if (!path.endsWith(PATH_SEP))
		{
			path = path + PATH_SEP;
		}
		return path + "colorring_data";
	}

	/**
	 * ��ȡ���������ļ��ڱ��صı���·��
	 * @return String
	 */
	public static String getColorDataFileBakPath()
	{
		String path = ServerInfo.getAppRootPath();
		path = PublicUtil.replace(path, "\\", PATH_SEP);
		if (!path.endsWith(PATH_SEP))
		{
			path = path + PATH_SEP;
		}
		return path + "colorring_data_bak";
	}

}
