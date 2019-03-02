package com.aspire.dotcard.basevideosync.vo;

public class VideoMediaVO {

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
     * 媒体ID
     */
    private String mediaFileID;
    /**
     * 单个媒体文件名称
     */
    private String mediaFileName;
    /**
     * 转码源文件名
     */
    private String sourceFileName;
    /**
     * 访问路径访问路径
     */
    private String visitPath;
    /**
     * 媒体文件存放路径
     */
    private String mediaFilePath;
    /**
     * 媒体文件预览路径
     */
    private String mediaFilePreviewPath;
    /**
     * 文件操作
     */
    private String mediaFileAction;
    /**
     * 单个媒体文件大小
     */
    private String mediaSize;
    /**
     * 该文件的时长
     */
    private String duration;
    /**
     * 单个媒体文件类型
     */
    private String mediaType;
    /**
     * 类型编号等级
     */
    private String mediaUsageCode;
    /**
     * 单个媒体文件编码格式
     */
    private String mediaCodeFormat;
    /**
     * 容器或封装格式
     */
    private String mediaContainFormat;
    /**
     * 码率类型等级包括 5,10,15,20,25,30,90
     */
    private String mediaCodeRate;
    /**
     * 支持网络类型
     */
    private String mediaNetType;
    /**
     * mime类型
     */
    private String mediaMimeType;
    /**
     * 分辩率类型
     */
    private String mediaResolution;
    /**
     * Profile类型
     */
    private String mediaProfile;
    /**
     * Level类型
     */
    private String mediaLevel;
    
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
	public String getMediaFileID() {
		return mediaFileID;
	}
	public void setMediaFileID(String mediaFileID) {
		this.mediaFileID = mediaFileID;
	}
	public String getMediaFileName() {
		return mediaFileName;
	}
	public void setMediaFileName(String mediaFileName) {
		this.mediaFileName = mediaFileName;
	}
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public String getVisitPath() {
		return visitPath;
	}
	public void setVisitPath(String visitPath) {
		this.visitPath = visitPath;
	}
	public String getMediaFilePath() {
		return mediaFilePath;
	}
	public void setMediaFilePath(String mediaFilePath) {
		this.mediaFilePath = mediaFilePath;
	}
	public String getMediaFilePreviewPath() {
		return mediaFilePreviewPath;
	}
	public void setMediaFilePreviewPath(String mediaFilePreviewPath) {
		this.mediaFilePreviewPath = mediaFilePreviewPath;
	}
	public String getMediaFileAction() {
		return mediaFileAction;
	}
	public void setMediaFileAction(String mediaFileAction) {
		this.mediaFileAction = mediaFileAction;
	}
	public String getMediaSize() {
		return mediaSize;
	}
	public void setMediaSize(String mediaSize) {
		this.mediaSize = mediaSize;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaUsageCode() {
		return mediaUsageCode;
	}
	public void setMediaUsageCode(String mediaUsageCode) {
		this.mediaUsageCode = mediaUsageCode;
	}
	public String getMediaCodeFormat() {
		return mediaCodeFormat;
	}
	public void setMediaCodeFormat(String mediaCodeFormat) {
		this.mediaCodeFormat = mediaCodeFormat;
	}
	public String getMediaContainFormat() {
		return mediaContainFormat;
	}
	public void setMediaContainFormat(String mediaContainFormat) {
		this.mediaContainFormat = mediaContainFormat;
	}
	public String getMediaCodeRate() {
		return mediaCodeRate;
	}
	public void setMediaCodeRate(String mediaCodeRate) {
		this.mediaCodeRate = mediaCodeRate;
	}
	public String getMediaNetType() {
		return mediaNetType;
	}
	public void setMediaNetType(String mediaNetType) {
		this.mediaNetType = mediaNetType;
	}
	public String getMediaMimeType() {
		return mediaMimeType;
	}
	public void setMediaMimeType(String mediaMimeType) {
		this.mediaMimeType = mediaMimeType;
	}
	public String getMediaResolution() {
		return mediaResolution;
	}
	public void setMediaResolution(String mediaResolution) {
		this.mediaResolution = mediaResolution;
	}
	public String getMediaProfile() {
		return mediaProfile;
	}
	public void setMediaProfile(String mediaProfile) {
		this.mediaProfile = mediaProfile;
	}
	public String getMediaLevel() {
		return mediaLevel;
	}
	public void setMediaLevel(String mediaLevel) {
		this.mediaLevel = mediaLevel;
	}
 
}
