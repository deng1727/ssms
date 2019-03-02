package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class AdapterVO extends Validateable implements VO {

	private String id;//���ֱ�ʶ
	private String chapterId;//�½ڱ�ʶ
	private String groups;//�������ʶ
	private String fileSize;//�ļ���С
	private String useType;//ʹ�÷�ʽ
	private String clear;//	��Ƶ������
	private String url;//ʹ��URL
	private String type;//��������


	public AdapterVO(String[] field) {
		if (field != null && field.length >= 8) {
			this.id = field[0];
			this.chapterId = field[1];
			this.groups = removeSomeString(field[2]);
			this.fileSize = field[3];
			this.useType = field[4];
			this.clear = field[5];
			this.url = field[6];
			this.type = field[7];
			

		}

	}

	//<values><Tvalue>[11012711191800]</Tvalue></values>
	//<values><Tvalue>[11012711220700]</Tvalue><Tvalue>[11012711191800]</Tvalue><Tvalue>[11012710103800]</Tvalue></values>
	public String removeSomeString(String str){
		if(str==null||str.trim().equals("")){
			return "";
		}
		str = str.replaceAll("\\[", "");
		str = str.replaceAll("]", "");
		str = str.replaceAll("<values><Tvalue>", "");
		str = str.replaceAll("</Tvalue><Tvalue>", "|");
		str = str.replaceAll("</Tvalue></values>", "");
		
		return str;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}



	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}
	
	

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public void validate() {
		// TODO Auto-generated method stub
		if (isEmpty(id)) {
			this.addFieldError("id����Ϊ��");
			//return;
		}
		if (isEmpty(chapterId)) {
			this.addFieldError("chapterId����Ϊ��");
			//return;
		}
		if (isEmpty(fileSize)) {
			this.addFieldError("fileSize����Ϊ��");
			//return;
		}
		if (isEmpty(type)) {
			this.addFieldError("type����Ϊ��");
			//return;
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id;
	}

}
