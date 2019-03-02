package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class DeviceGroupVO extends Validateable implements VO {
	private String groupId;//机型组标识
	private String logic;//逻辑关系
	private String logicField;//逻辑字段
	private String logicValue;//基准值


	public DeviceGroupVO(String[] field) {
		if (field != null && field.length >= 4) {
			this.groupId = field[0];
			this.logic = field[1];
			this.logicField = field[2];
			this.logicValue = field[3];
		}

	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public String getLogicField() {
		return logicField;
	}

	public void setLogicField(String logicField) {
		this.logicField = logicField;
	}

	public String getLogicValue() {
		return logicValue;
	}

	public void setLogicValue(String logicValue) {
		this.logicValue = logicValue;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (groupId == null || "".equals(groupId)) {
			this.addFieldError("groupId不能为空");
		}
		if (logic == null || "".equals(logic)) {
			this.addFieldError("logic不能为空");
		}
		if (logicField == null || "".equals(logicField)) {
			this.addFieldError("logicField不能为空");
		}
		if (logicValue == null || "".equals(logicValue)) {
			this.addFieldError("logicValue不能为空");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
