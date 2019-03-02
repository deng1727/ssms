package com.aspire.ponaadmin.web.category;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class CategoryRuleConfig
{
	/**
	 * ��¼��־��ʵ������
	 */
	private static JLogger LOG = LoggerFactory.getLogger(CategoryRuleConfig.class);
	/**
	 * ���ܶ�ʱ�������񴥷�ʱ�䡣
	 */
	public static String STARTTIME;
    /**
	 * �����Ӧ������Ĭ�ϻ�ȡ���ֵ��
	 */
	public static int CONDITIONMAXVALUE;
	/**
	 * ����sp��Ϣ������Ʒ�����������Ʒ��
	 */
	public static int SPNAMESORTCOUNT;
	/**
	 * ����ͬһsp�������Ʒ��
	 */
	public static int SPNAMEMAXCOUNT;
	/**
	 * ִ�й��������߳���
	 */
	public static int RuleRunningTaskNum;
	
	public static void loadConfig()throws Exception
	{

		
		LOG.info("�����Զ����¿�ʼ��ȡ������");
		
		// ��ʼ�������
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"categoryAutoUpdate");
		if (module == null)
		{
			LOG.error("system-config.xml�ļ����Ҳ���categoryAutoUpdateģ��");
			System.out.println("system-config.xml�ļ����Ҳ���categoryAutoUpdateģ��,�Զ������޷���ȷ����");
			throw new BOException("system-config.xml�ļ����Ҳ���categoryAutoUpdateģ��");

		}
		STARTTIME = module.getItemValue("startTime");
		LOG.info("startTime="+STARTTIME);
		CONDITIONMAXVALUE = Integer.parseInt(module.getItemValue("ConditionMaxValue"));
		LOG.info("ConditionMaxValue="+CONDITIONMAXVALUE);
		SPNAMESORTCOUNT = Integer.parseInt(module.getItemValue("spnameSortCount"));
		LOG.info("spnameSortCount="+SPNAMESORTCOUNT);
		SPNAMEMAXCOUNT = Integer.parseInt(module.getItemValue("spnameMaxCount"));
		LOG.info("spnameMaxCount="+SPNAMEMAXCOUNT);	
		RuleRunningTaskNum=Integer.parseInt(module.getItemValue("RuleRunningTaskNum"));
		LOG.info("RuleRunningTaskNum="+RuleRunningTaskNum);
	}

}
