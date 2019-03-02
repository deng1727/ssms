package com.aspire.dotcard.basebook.config;

import com.aspire.common.config.ArrayValue;
import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;

/**
 * ����ͼ��������
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
	 * ��ȡ��������
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
