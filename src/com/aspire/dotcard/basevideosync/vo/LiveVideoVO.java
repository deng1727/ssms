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
     * Ŀ����Ӧ���� ��ʽΪ��YYYYMMDD
     */
    private String playDay;
	/**
     * CMS����ID,��Ŀid
     */
    private String programId;
    /**
     * CMS����ID
     */
    private String CMSID;
    /**
     * ֱ����Ŀ����
     */
    private String playName;
	
    /**
     * ֱ����Ŀ����
     */
    private String startTime;
    /**
     * ֱ����Ŀ����
     */
    private String endTime;
    /**
     * ���
     */
    private String ranking;
    /**
     * ��Ŀ��Ӧ�缯ID
     */
    private String playShellID;
    /**
     * ��Ŀ��Ӧ�㲥����ID
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
