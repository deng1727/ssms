package com.aspire.ponaadmin.web.blacklist.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.util.DateUtil;

public class BlackListVO implements PageVOInterface {

	/**
	 * 内容ID
	 */
	private String contentId;
	/**
	 * 内容名称
	 */
	private String name;
	/**
	 * 提供商
	 */
	private String spName;
    
    /**
     * 内容类型
     */
    private String contentType;
	
	private String startDate;
	
	/**
	 * 有效期
	 */
	private String endDate;
	
	private int type;
	/**
	 * 黑名单类型： 1.嫌疑刷榜 2.首次刷榜 3.多次刷榜  （2次以上）
	 */
	private int blackType;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	private String modifyTime;
	
	private String blacklistStatus;
	
	public String getBlacklistStatus() {
		return blacklistStatus;
	}

	public void setBlacklistStatus(String blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}



	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBlackType() {
		return blackType;
	}

	public void setBlackType(int blackType) {
		this.blackType = blackType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void CopyValFromResultSet(Object vo, ResultSet rs)
			throws SQLException {
		BlackListVO black = (BlackListVO)vo;
		black.setContentId(rs.getString("contentid"));
		black.setCreateTime(rs.getString("createTime"));
		black.setModifyTime(rs.getString("modifyTime"));
		black.setBlackType(rs.getInt("blackType"));
		//black.setInDate(DateUtil.formatDate(DateUtil.stringToDate(rs.getString("indate"), "yyyyMMdd"), "yyyy-MM-dd"));
		black.setStartDate(DateUtil.formatDate(DateUtil.stringToDate(rs.getString("startDate"), "yyyyMMdd"), "yyyy-MM-dd"));
		black.setEndDate(DateUtil.formatDate(DateUtil.stringToDate(rs.getString("endDate"), "yyyyMMdd"), "yyyy-MM-dd"));
		black.setType(rs.getInt("type"));
		black.setName(rs.getString("name"));
		black.setSpName(rs.getString("spname"));
        black.setContentType(rs.getString("contentType"));
        black.setBlacklistStatus(rs.getString("blacklistStatus"));
	}

	public Object createObject() {
		return new BlackListVO();
	}

    
    public String getContentType()
    {
        return contentType;
    }

    
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

}
