package com.aspire.ponaadmin.web.dissertation;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

public class UploadFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5872085753936447949L;

	private FormFile file;
	
	private String fileName;

	private String picUrl;
	public String getPicUrl() {
		return picUrl;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
		this.fileName=file.getFileName();
	}

	public String getFileName() {
		return fileName;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
}
