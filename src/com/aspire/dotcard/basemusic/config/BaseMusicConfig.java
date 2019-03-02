package com.aspire.dotcard.basemusic.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.config.ArrayItem;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseMusicConfig
{
	/**
	 * 日志对象。
	 */
	private static final JLogger LOG = LoggerFactory.getLogger(BaseMusicConfig.class);

	private static final String PATH_SEP = System.getProperty("file.separator");
	/**
	
	/**
	 * 基地音乐更新的最大线程数
	 */
	public static int BaseMusicUpdateTaskNum;
	/**
	 * 处理基地音乐的运行期队列的最大容量
	 */
	public static int BaseMusicMaxReceivedNum;

	public static int getBaseMusicUpdateTaskNum(){
		return Integer.parseInt(get("BaseMusicUpdateTaskNum"));
	}
public static int getBaseMusicMaxReceivedNum(){
	return Integer.parseInt(get("BaseMusicMaxReceivedNum"));
	}
	/**
	 * 获取基地音乐相关的配置项值
	 * @param key String
	 * @return String
	 */
	public static String get(String key)
	{
		ModuleConfig module = ConfigFactory.getSystemConfig()
				.getModuleConfig("basemusic");
	
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
public static List getArrayValues(String itemName){
	ArrayItem catefile = ConfigFactory.getSystemConfig()
	.getModuleConfig("basemusic").getArrayItem(itemName);
	ArrayList arr = new ArrayList();
	String val = null;
	String id = null;
	if (catefile != null) {
		com.aspire.common.config.ArrayValue[] avs = catefile.getArrayValues();
		if (avs != null) {
			for (int i = 0; i < avs.length; i++) {
				id = avs[i].getId();
				val = avs[i].getValue();
				if (val != null && id != null) {
					LOG.debug("get cate ="+id+" and file:" + val);
					String hm[] = {id,val};
					arr.add(hm);
				}
			}
		}
	}
	return arr;
}
	/**
	 * 获取基地音乐数据文件在本地的保存路径
	 * @return String
	 */
	public static String getMusicDataFilePath()
	{
		String path = ServerInfo.getAppRootPath();
		path = PublicUtil.replace(path, "\\", PATH_SEP);
		if (!path.endsWith(PATH_SEP))
		{
			path = path + PATH_SEP;
		}
		return path + "base_data";
	}

	/**
	 * 获取基地音乐数据文件在本地的保存路径
	 * @return String
	 */
	public static String getMusicDataFileBakPath()
	{
		String path = ServerInfo.getAppRootPath();
		path = PublicUtil.replace(path, "\\", PATH_SEP);
		if (!path.endsWith(PATH_SEP))
		{
			path = path + PATH_SEP;
		}
		return path + "base_data_bak";
	}

}
