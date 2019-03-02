package com.aspire.dotcard.basebook.vo;

public class BookAuthorVO {

	/**
	 * 作者ID
	 */
	private String authorId;
	
	/**
	 * 作者姓名
	 */
	private String authorName;
	
	/**
	 * 简介
	 */
	private String description;
	
	/**
	 * 变更类型
	 */
	private int changeType;

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	
	/**
	 * 赋值
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data){
		if(data.length!=4){
			return false;
		}
		if(null==data[0]||"".equals(data[0].trim())
				||null==data[1]||"".equals(data[1].trim())
				||null==data[3]||"".equals(data[3].trim())){
			return false;
		}
		int changeType;
		try {
			changeType = Integer.parseInt(data[3].trim());
		} catch (NumberFormatException e) {
			return false;
		}
		if(changeType!=1&&changeType!=2&&changeType!=3){
			return false;
		}
		this.authorId = data[0].trim();
		this.authorName = data[1].trim();
		this.description = data[2];
		this.changeType = changeType;
		return true;
		
	}
}
