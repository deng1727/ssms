package com.aspire.dotcard.basemusic.vo;

/**
 * 铃音盒
 * @author ouyangguangming
 *
 */
public class ToneBoxVO {

	private String id;//铃音盒标识
	private String name;//铃音盒名称
	private String description;//铃音盒介绍
	private String charge;//资费,单位分
	private String valid;//有效期
	private String operType;//操作类型(	1：新增；2：更新；3：下线。全量文件中都是1)
	private String updateTime;
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	

}
