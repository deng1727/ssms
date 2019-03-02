package com.aspire.ponaadmin.web.category;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class SynCategoryConfig
{
	/**
	 * ��¼��־��ʵ������
	 */
	private static JLogger LOG = LoggerFactory.getLogger(SynCategoryConfig.class);
    
	/**
	 * ����ͬ����Ӿ�Ʒ�����񴥷�ʱ�䡣
	 */
	public static String STARTTIME;
    
    /**
	 * ָ��WAP��Ʒ��Ļ���id
	 */
	public static String ACATEGORYID;
    
	/**
	 * ָ��WWW��Ʒ��Ļ���id
	 */
	public static String WCATEGORYID;
    
	/**
	 * ָ��MO��Ʒ��Ļ���id
	 */
	public static String OCATEGORYID;
	
	public static void loadConfig()throws Exception
	{
		LOG.info("����ͬ����Ӿ�Ʒ�⿪ʼ��ȡ������");
		
		// ��ʼ�������
		ModuleConfig module = ConfigFactory.getSystemConfig().getModuleConfig(
				"synCategory");
		if (module == null)
		{
			LOG.error("system-config.xml�ļ����Ҳ���synCategoryģ��");
			System.out.println("system-config.xml�ļ����Ҳ���synCategoryģ��,�Զ������޷���ȷ����");
			throw new BOException("system-config.xml�ļ����Ҳ���synCategoryģ��");
		}
		STARTTIME = module.getItemValue("startTime");
		LOG.info("startTime="+STARTTIME);
        ACATEGORYID = module.getItemValue("aCategoryId");
		LOG.info("ָ��WAP��Ʒ��Ļ���id="+ACATEGORYID);
        WCATEGORYID = module.getItemValue("wCategoryId");
		LOG.info("ָ��WWW��Ʒ��Ļ���id="+WCATEGORYID);
        OCATEGORYID = module.getItemValue("oCategoryId");
		LOG.info("ָ��MO��Ʒ��Ļ���id="+OCATEGORYID);	
	}

}
