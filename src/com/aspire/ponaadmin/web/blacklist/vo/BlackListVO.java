package com.aspire.ponaadmin.web.blacklist.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.util.DateUtil;

public class BlackListVO implements PageVOInterface {

	/**
	 * ����ID
	 */
	private String contentId;
	/**
	 * ��������
	 */
	private String name;
	/**
	 * �ṩ��
	 */
	private String spName;
    
    /**
     * ��������
     */
    private String contentType;
	
	private String startDate;
	
	/**
	 * ��Ч��
	 */
	private String endDate;
	
	private int type;
	/**
	 * ���������ͣ� 1.����ˢ�� 2.�״�ˢ�� 3.���ˢ��  ��2�����ϣ�
	 */
	private int blackType;
	
	/**
	 * ����ʱ��
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
