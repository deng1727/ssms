package com.aspire.dotcard.basebook.config;

import com.aspire.common.config.ArrayValue;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

/**
 * 基地图书配置项
 * @author x_zhailiqing
 *
 */
public class BaseBookConfig {
	
	
	public static String get(String key){
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("basebook");	
		if(null!=module){
			return module.getItemValue(key);
		}
		return null;
	}
	
	/**
	 * 获取数组配置
	 * @param key
	 * @return
	 */
	public static ArrayValue[] getArrayValue(String key){
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig("basebook");
		if(null!=module){
			return module.getArrayItem(key).getArrayValues();
		}
		return null;		
		                                         
	}
}
