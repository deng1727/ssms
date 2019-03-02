package com.aspire.dotcard.appinfosyn;

import java.util.TimerTask;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.appinfosyn.impl.AppXMLFile;

public class AppXmlTask extends TimerTask 
	{
		protected static JLogger logger = LoggerFactory
				.getLogger(AppXmlTask.class);
		
		public void run()
		{
			AppXMLFile xmlfile = new AppXMLFile();
			

				SynBaseAppXml appXml;
				try {
					appXml = new SynBaseAppXml(xmlfile);
					appXml.sysDataByXml();

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					logger.error("应用信息入库过程发生异常！", e);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					logger.error("应用信息入库过程发生异常！", e);
				}
			
		}
	}


