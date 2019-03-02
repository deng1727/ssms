/**
 * <p>
 * ������Ʒ���� ��ȡ������
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 16, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export;

import com.aspire.common.config.ConfigFactory;
import com.aspire.common.config.ModuleConfig;
import com.aspire.common.config.ServerInfo;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author dongke
 *
 */
public class CategoryExportConfig
{
	private static final JLogger logger = LoggerFactory.getLogger(CategoryExportConfig.class);

	
	
	   /**
	 * ������Ʒ���� ��ʱ���񴥷�ʱ�䡣
	 */
	public  static final String STARTTIME;
	public  static final String IP;
	public  static final int PORT;
	public  static final String USER;
	public  static final String PWD;
	public  static final String FTPDIR;				//ftp�������д洢excel��Ŀ¼
	public  static final String LOCALDIR;			//��������excel�洢��Ŀ¼
	public  static final String TEMPLATEDIR;		//��������excel��ģ���ļ�·��(���ļ���)
	public  static final String CategorySynFrequency;//���ڣ�ÿ��/ÿ�ܡ�	
	  static
	    {
	        ModuleConfig module = ConfigFactory.getSystemConfig()
	                                           .getModuleConfig("categoryExport");
	        
	        IP = module.getItemValue("FTPServerIP").trim();
	        PORT = Integer.parseInt(module.getItemValue("FTPServerPort").trim());
	        USER = module.getItemValue("FTPServerUser").trim();
	        PWD = module.getItemValue("FTPServerPassword").trim();	        
	        STARTTIME = module.getItemValue("startTime").trim();
	        FTPDIR =  module.getItemValue("FTPDir").trim();
	        LOCALDIR =  module.getItemValue("LocalDir").trim();
	        TEMPLATEDIR =  ServerInfo.getAppRootPath()+"/conf/categorytemplate.xls";//�����confĿ¼��
	        CategorySynFrequency = module.getItemValue("CategorySynFrequency").trim();
	        if (logger.isDebugEnabled())
	        {
	        	logger.debug("the FTPServerIP is " + IP);
	            logger.debug("the FTPServerPort is " + PORT);
	            logger.debug("the FTPServerUser is " + USER);
	            logger.debug("the FTPServerPassword is " + PWD);	            
	            logger.debug("the STARTTIME is " + STARTTIME);
	            logger.debug("the FTPDIR is " + FTPDIR);
	            logger.debug("the LOCALDIR is " + LOCALDIR);
	            logger.debug("the TEMPLATEDIR is " + TEMPLATEDIR);
	            logger.debug("the CategorySynFrequency is " + CategorySynFrequency);
	        }
	    }
	 
}
