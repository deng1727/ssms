package com.aspire.dotcard.basevideosync.vo;

public class VideoPicVO {

	/**
	 * 主键id
	 */
	private String id;
	/**
	 * CMS内容ID,节目id
	 */
	private String programId;
	/**
	 * CMS内容ID,内容编号,10位内容唯一短编码，媒资IDCMS以2开头，MMS以3开头,相当于视频ID
	 */
	private String CMSID;
	/**
	 * 内容创建的时候，系统生成的ID
	 */
	private String dpFileID;
	/**
	 * 单个图片名称
	 */
	private String dpFileName;

	/**
	 * 类型编号,代表不同的编码分类类型
	 */
	private String dpUsageCode;
	/**
	 * 图片相关的文字说明
	 */
	private String dpFileDetail;
	/**
	 * 单个图标类型 01- GIF 02- BMP 03- JPG
	 */
	private String dpFileType;
	/**
	 * 00-图标 1- 图文
	 */
	private String dpUseType;
	/**
	 * 1-6（目前仅存在6中终端适配类型）；0表明可被任何终端适配类型使用 1- ANY 1- A 02-B 1- C 1- D 1- E 06-F
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
