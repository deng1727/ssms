package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class CPVO extends Validateable implements VO{
	private String cpid;
	private String cpName;
	
	public CPVO(String[] field){
		if(field!=null&&field.length>=2){
			this.cpid = field[0];
			this.cpName = field[1];
			
		}
		
	}
	
	public String getCpid() {
		return cpid;
	}
	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}


	public void validate() {
		// TODO Auto-generated method stub
		if(isEmpty(cpid)){
			this.addFieldError("cpid不能为空");
		}
		if(isEmpty(cpName)){
			this.addFieldError("cpName不能为空");
		}
		
		
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return cpid;
	}

	
	
	
	

}
