package com.aspire.dotcard.basecomic.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Validateable {
	
	private List fieldError ;
	public abstract void validate ();
	public void addFieldError(String error){
		if(fieldError==null){
			fieldError = new ArrayList();
		}
		fieldError .add(error);
		
	}
	
	public String getFieldErrorMsg(){
		if(fieldError==null||fieldError.size()==0){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<fieldError.size();i++){
			sb.append(",").append(fieldError.get(i));
		}
		return sb.substring(1);
	}
	
	public boolean isEmpty(String field){
		return (null==field||"".equals(field));
		
	}

}
