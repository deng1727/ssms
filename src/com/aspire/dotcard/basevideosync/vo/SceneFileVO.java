package com.aspire.dotcard.basevideosync.vo;

public class SceneFileVO {

	public SceneFileVO() {
	}
	/**
	 * ���������ļ�ID �ļ�ID���ϴ�����ܲ��������֣�˳���ţ�2���ڲ��ظ���
	 */
	private String scFileID;
	/**
	 * ���������ļ�������
	 */
	private String scFileName;
	/**
	 * ����������ѭ�Ĺ淶��0-SMIL 1-SVG 2-HTML 3-SAF ע��SAFΪ��XML��������ʽ
	 */
	private String scFileSpec;
	/**
	 * �������ͣ�A��B��C��D��E��F��G��H��I�� ö��ֵΪNUMBER��1��ʼ 1- A 2- B 3- C 4- D 5- E 6- F 7- G
	 * 8- H 9- I ��ĸ�����ն���������
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
