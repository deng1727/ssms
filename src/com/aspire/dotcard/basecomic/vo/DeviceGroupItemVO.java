package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class DeviceGroupItemVO extends Validateable implements VO {
	private String groupId;//机型组标识
	private String deviceId;//机型标识

	public DeviceGroupItemVO(String[] field) {
		if (field != null && field.length >= 2) {
			this.groupId = field[0];
			this.deviceId = field[1];

		}

	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (groupId == null || "".equals(groupId)) {
			this.addFieldError("groupId不能为空");
		}
		if (deviceId == null || "".equals(deviceId)) {
			this.addFieldError("deviceId不能为空");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return groupId+"|"+deviceId;
	}

}
