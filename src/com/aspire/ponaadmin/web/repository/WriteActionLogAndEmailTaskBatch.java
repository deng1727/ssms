package com.aspire.ponaadmin.web.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.aspire.common.db.DAOException;
import com.aspire.ponaadmin.web.daemon.DaemonTask;
import com.aspire.ponaadmin.web.mail.Mail;

public class WriteActionLogAndEmailTaskBatch extends DaemonTask {

	/**
     * Ҫд�Ĳ���������
     */
	private Map<String,Object> map;
	
	/**
	 * ���췽��������һ��д�����첽����
	 * @param log Ҫд�Ĳ�������
	 */
	public WriteActionLogAndEmailTaskBatch(Map<String,Object> map){
		this.map = map;
	}
	
	@Override
	public void execute() {
		try {
			
			try {
				CategoryLoggerDAO.getInstance().addLogger(map);
			} catch (Exception e) {
				
			}
		    Set<String> emailAddress = CategoryEmailDAO.getInstance().getMailAddress2(map);
		    String[] to = {};
		    String[] cc  = {};
		    String[] to2 = {};
		    if(emailAddress != null && emailAddress.size()>0){
		    	to = new String[emailAddress.size()];
		    	cc = new String[emailAddress.size()];
			    int i=0;
			    int j=0;
			    for(String s:emailAddress){
			    	String[] strs = s.split(",");
			    	to[i++]=strs[0];
			    	if(strs!=null&&strs.length>1){
			    		cc[j++]=strs[1];
			    	}
			    	
			    }

		    }
		     
		    if(Integer.parseInt(map.get("status").toString()) != 0){
		    	if(to.length>0||cc.length>0){
		    		 Mail.sendMail(map.get("mailTitle").toString(), map.get("mailContent").toString(),to,cc,null);
		    	}
		    }else{
		    	if(to2 != null&& to2.length>0){
		    		Mail.sendMail(map.get("mailTitle").toString(), map.get("mailContent").toString(), to);
		    	}
		    	 
		    }
		   
		} catch (DAOException e) {
			
		}
		
	}

}
