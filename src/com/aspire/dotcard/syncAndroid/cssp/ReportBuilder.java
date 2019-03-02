package com.aspire.dotcard.syncAndroid.cssp;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;


public class ReportBuilder implements IBuilder{
	private static JLogger LOG = LoggerFactory
	.getLogger(ReportBuilder.class);
	
	public String getPrefix(){
		return "report"; 
	}

	public Bean getBean(String line,String fileName) {
		// TODO Auto-generated method stub
    	ReportBean bean = null;
    	try{
	    	bean = new ReportBean();
	    	String[] arr = line.split((char)31+"");
	    	bean.setContentid(arr[3]);
	    	bean.setSubsplace(arr[6]);
	    	bean.setUa(arr[7]);
	    	bean.setFilename(fileName);
    	}catch(Exception e){
    		LOG.error(e);
    	}
		return bean;
	}
    

	public DataDealer getDataDealer() {
		// TODO Auto-generated method stub
		return new ReportDealer();
	}

}
