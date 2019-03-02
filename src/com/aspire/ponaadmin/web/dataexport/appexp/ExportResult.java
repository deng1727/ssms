package com.aspire.ponaadmin.web.dataexport.appexp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportResult {
	private String msg;

	private boolean success;

	private long size;

	private long sTime;

	private long eTime;

	private String operation;

	public ExportResult(String o) {
		this.operation = o;
	}

	public ExportResult(String msg, boolean success, long size) {
		this.msg = msg;
		this.size = size;
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMdd HH:mm:ss SSS");

	public String getStartTime() {
		return sdf.format(new Date(sTime));
	}

	public String getEndTime() {
		return sdf.format(new Date(eTime));
	}

	public long getElapsedTime() {
		return eTime - sTime;
	}

	public long getETime() {
		return eTime;
	}

	public void setETime(long time) {
		eTime = time;
	}

	public long getSTime() {
		return sTime;
	}

	public void setSTime(long time) {
		sTime = time;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getOperation() {
		return operation;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getEmailContent() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.operation).append(". <br/>");
		sb.append("�������:").append(this.success ? "�ɹ�" : "ʧ��").append(". <br/>");
		sb.append("��ʾ��Ϣ:").append(this.msg).append(". <br/>");
		sb.append("������ʼʱ��:").append(this.getStartTime()).append(". <br/>");
		sb.append("��������ʱ��:").append(this.getEndTime()).append(". <br/>");
		sb.append("����ʱ��").append(this.getElapsedTime()).append(" ����");
		return sb.toString();
	}

}
