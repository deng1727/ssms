package com.aspire.dotcard.basebook.vo;

/**
 * 基地图书分类
 * @author x_zhailiqing
 *
 */
public class RTypeVO {

	private String typeId;
	
	private String typeName;
	
	private String parentId;

	/**
	 * 1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现
	 */
	private int changeType;
	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public boolean setValue(String[] data){
		if(data.length!=3){
			return false;
		}
		if(null==data[0]||"".equals(data[0].trim())
				||null==data[1]||"".equals(data[1].trim())
				||null==data[2]||"".equals(data[2].trim())){
			return false;
		}
		int changeType;
		try {
			changeType = Integer.parseInt(data[2].trim());
		} catch (NumberFormatException e) {
			return false;
		}
		if(changeType!=1&&changeType!=2&&changeType!=3){
			return false;
		}
		typeId = data[0].trim();
		typeName = data[1].trim();
		this.changeType = changeType;
		return true;
	}
}
