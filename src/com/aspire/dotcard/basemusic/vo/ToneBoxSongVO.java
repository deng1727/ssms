package com.aspire.dotcard.basemusic.vo;

/**
 * 铃音盒歌曲
 * @author ouyangguangming
 *
 */
public class ToneBoxSongVO {

	private String id;//歌曲标识
	private String boxId;//铃音盒标识
	private String sortId;//排列序号
	private String operType;//操作类型(	1：新增；2：更新；3：下线。全量文件中都是1)
	private String updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public String getSortId() {
		return sortId;
	}
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
