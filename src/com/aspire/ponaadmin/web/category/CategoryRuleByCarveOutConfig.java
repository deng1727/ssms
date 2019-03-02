package com.aspire.ponaadmin.web.category;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryRuleByCarveOutConfig
{
	/**
	 * ��¼��־��ʵ������
	 */
	private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleByCarveOutConfig.class);
    
	/**
	 * ��ҵ�������ܶ�ʱ�������񴥷�ʱ�䡣
	 */
	public static String STARTTIME;
    
    /**
	 * �����Ӧ������Ĭ�ϻ�ȡ���ֵ��
	 */
	public static int CONDITIONMAXVALUE;

	/**
	 * ִ�й��������߳���
	 */
	public static int RuleRunningTaskNum;
	
	public static void loadConfig()throws Exception
	{
		LOG.info("��ҵ���������Զ����¿�ʼ��ȡ������");
		
		// ��ʼ�������
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"categoryAutoUpdateByCarveOut");
        
		if (module == null)
		{
			LOG.error("system-config.xml�ļ����Ҳ���categoryAutoUpdateByCarveOutģ��");
			System.out.println("system-config.xml�ļ����Ҳ���categoryAutoUpdateByCarveOutģ��,�Զ������޷���ȷ����");
			throw new BOException("system-config.xml�ļ����Ҳ���categoryAutoUpdateByCarveOutģ��");

		}
		STARTTIME = module.getItemValue("startTime");
		LOG.info("startTime="+STARTTIME);
		CONDITIONMAXVALUE = Integer.parseInt(module.getItemValue("ConditionMaxValue"));
		LOG.info("ConditionMaxValue="+CONDITIONMAXVALUE);
		RuleRunningTaskNum=Integer.parseInt(module.getItemValue("RuleRunningTaskNum"));
		LOG.info("RuleRunningTaskNum="+RuleRunningTaskNum);
	}
}
