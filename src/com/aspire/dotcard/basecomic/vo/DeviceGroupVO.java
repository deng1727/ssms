package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class DeviceGroupVO extends Validateable implements VO {
	private String groupId;//�������ʶ
	private String logic;//�߼���ϵ
	private String logicField;//�߼��ֶ�
	private String logicValue;//��׼ֵ


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
			this.addFieldError("groupId����Ϊ��");
		}
		if (logic == null || "".equals(logic)) {
			this.addFieldError("logic����Ϊ��");
		}
		if (logicField == null || "".equals(logicField)) {
			this.addFieldError("logicField����Ϊ��");
		}
		if (logicValue == null || "".equals(logicValue)) {
			this.addFieldError("logicValue����Ϊ��");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
