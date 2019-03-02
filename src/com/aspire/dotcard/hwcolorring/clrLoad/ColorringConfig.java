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
	 * 日志对象。
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(ColorringConfig.class);

	private static final String PATH_SEP = System.getProperty("file.separator");
	/**
	 * 统一内容适配平台访问地址
	 */
	public static String UCAPUrl = "";
	/**
	 * 彩铃试听转换内容格式及编码率数组
	 */
	public static String[] code_abps = null;
	/**
	 * 资源服务器上面转换后的彩铃存放目录（相对ftp登录后的目录）
	 */
	public static String clrConvertPath = "";
	/**
	 * 资源服务器的访问方式，即彩铃试听apache访问路径
	 */
	public static String sourceServerVisit = "";
	/**
	 * 彩铃转化的最大线程数
	 */
	public static int ColorringConvertTaskNum;
	/**
	 * 彩铃更新的最大线程数
	 */
	public static int ColorringUpdateTaskNum;
	/**
	 * 处理彩铃的运行期队列的最大容量
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
	 * 获取彩铃相关的配置项值
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
	 * 获取彩铃数据文件在本地的保存路径
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
	 * 获取彩铃数据文件在本地的保存路径
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
