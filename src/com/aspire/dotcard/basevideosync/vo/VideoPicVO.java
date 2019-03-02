package com.aspire.dotcard.basevideosync.vo;

public class VideoPicVO {

	/**
	 * ����id
	 */
	private String id;
	/**
	 * CMS����ID,��Ŀid
	 */
	private String programId;
	/**
	 * CMS����ID,���ݱ��,10λ����Ψһ�̱��룬ý��IDCMS��2��ͷ��MMS��3��ͷ,�൱����ƵID
	 */
	private String CMSID;
	/**
	 * ���ݴ�����ʱ��ϵͳ���ɵ�ID
	 */
	private String dpFileID;
	/**
	 * ����ͼƬ����
	 */
	private String dpFileName;

	/**
	 * ���ͱ��,����ͬ�ı����������
	 */
	private String dpUsageCode;
	/**
	 * ͼƬ��ص�����˵��
	 */
	private String dpFileDetail;
	/**
	 * ����ͼ������ 01- GIF 02- BMP 03- JPG
	 */
	private String dpFileType;
	/**
	 * 00-ͼ�� 1- ͼ��
	 */
	private String dpUseType;
	/**
	 * 1-6��Ŀǰ������6���ն��������ͣ���0�����ɱ��κ��ն���������ʹ�� 1- ANY 1- A 02-B 1- C 1- D 1- E 06-F
	 * 
	 */
	private String dpAdapType;
	/**
	 * 00-ANY 01-176*208 02-240*320 03-320*240 04-320*480 05-352*416 06-480*640
	 * 07-320*480 08-360*640 09-640*360
	 */
	private String Pixel;
	
	

	public String getDpFileID() {
		return dpFileID;
	}

	public void setDpFileID(String dpFileID) {
		this.dpFileID = dpFileID;
	}

	public String getDpFileDetail() {
		return dpFileDetail;
	}

	public void setDpFileDetail(String dpFileDetail) {
		this.dpFileDetail = dpFileDetail;
	}

	public String getDpFileType() {
		return dpFileType;
	}

	public void setDpFileType(String dpFileType) {
		this.dpFileType = dpFileType;
	}

	public String getDpUseType() {
		return dpUseType;
	}

	public void setDpUseType(String dpUseType) {
		this.dpUseType = dpUseType;
	}

	public String getDpAdapType() {
		return dpAdapType;
	}

	public void setDpAdapType(String dpAdapType) {
		this.dpAdapType = dpAdapType;
	}

	public String getPixel() {
		return Pixel;
	}

	public void setPixel(String pixel) {
		Pixel = pixel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getCMSID() {
		return CMSID;
	}

	public void setCMSID(String cMSID) {
		CMSID = cMSID;
	}

	public String getDpFileName() {
		return dpFileName;
	}

	public void setDpFileName(String dpFileName) {
		this.dpFileName = dpFileName;
	}

	public String getDpUsageCode() {
		return dpUsageCode;
	}

	public void setDpUsageCode(String dpUsageCode) {
		this.dpUsageCode = dpUsageCode;
	}
}
