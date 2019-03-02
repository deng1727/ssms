/**
 * 
 */
package com.aspire.dotcard.basevideosync.vo;

/**
 * @author dongke
 *
 */
public class LiveVideoVO {

	/**
     * 目单对应日期 格式为：YYYYMMDD
     */
    private String playDay;
	/**
     * CMS内容ID,节目id
     */
    private String programId;
    /**
     * CMS内容ID
     */
    private String CMSID;
    /**
     * 直播节目名称
     */
    private String playName;
	
    /**
     * 直播节目名称
     */
    private String startTime;
    /**
     * 直播节目名称
     */
    private String endTime;
    /**
     * 序号
     */
    private String ranking;
    /**
     * 节目对应剧集ID
     */
    private String playShellID;
    /**
     * 节目对应点播内容ID
     */
    private String playVodID;
    
	public String getPlayShellID() {
		return playShellID;
	}
	public void setPlayShellID(String playShellID) {
		this.playShellID = playShellID;
	}
	public String getPlayVodID() {
		return playVodID;
	}
	public void setPlayVodID(String playVodID) {
		this.playVodID = playVodID;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getPlayDay() {
		return playDay;
	}
	public void setPlayDay(String playDay) {
		this.playDay = playDay;
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
	public void setCMSID(String cmsid) {
		CMSID = cmsid;
	}
	public String getPlayName() {
		return playName;
	}
	public void setPlayName(String playName) {
		this.playName = playName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
    
    
}
