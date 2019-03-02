package com.aspire.dotcard.basevideosync.vo;

public class SceneFileVO {

	public SceneFileVO() {
	}
	/**
	 * 场景描述文件ID 文件ID在上传后才能产生，数字，顺序编号，2年内不重复。
	 */
	private String scFileID;
	/**
	 * 场景描述文件的名称
	 */
	private String scFileName;
	/**
	 * 场景描述遵循的规范，0-SMIL 1-SVG 2-HTML 3-SAF 注：SAF为其XML的描述形式
	 */
	private String scFileSpec;
	/**
	 * 适配类型，A、B、C、D、E、F、G、H、I。 枚举值为NUMBER从1开始 1- A 2- B 3- C 4- D 5- E 6- F 7- G
	 * 8- H 9- I 字母代表终端适配类型
	 */
	private String scAdapType;
	
	public String getScFileID() {
		return scFileID;
	}
	public void setScFileID(String scFileID) {
		this.scFileID = scFileID;
	}
	public String getScFileName() {
		return scFileName;
	}
	public void setScFileName(String scFileName) {
		this.scFileName = scFileName;
	}
	public String getScFileSpec() {
		return scFileSpec;
	}
	public void setScFileSpec(String scFileSpec) {
		this.scFileSpec = scFileSpec;
	}
	public String getScAdapType() {
		return scAdapType;
	}
	public void setScAdapType(String scAdapType) {
		this.scAdapType = scAdapType;
	}

}
