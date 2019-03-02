package com.aspire.dotcard.basecomic.vo;

import com.aspire.dotcard.basecomic.common.Validateable;

public class DeviceGroupItemVO extends Validateable implements VO {
	private String groupId;//�������ʶ
	private String deviceId;//���ͱ�ʶ

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
			this.addFieldError("groupId����Ϊ��");
		}
		if (deviceId == null || "".equals(deviceId)) {
			this.addFieldError("deviceId����Ϊ��");
		}

	}

	public String getKey() {
		// TODO Auto-generated method stub
		return groupId+"|"+deviceId;
	}

}
