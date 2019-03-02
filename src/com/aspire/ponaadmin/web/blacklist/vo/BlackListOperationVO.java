package com.aspire.ponaadmin.web.blacklist.vo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.util.DateUtil;

public class BlackListOperationVO implements PageVOInterface{

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
	/**
	 * ����״̬
	 */
	private String blacklistStatus;
	
	/**
	 * ������
	 */
	private String operator;
	/**
	 * ����ʱ��
	 */
	private Date operatorTime;
	/**
	 * ������
	 */
	private String approval;
	/**
	 * ����ʱ��
	 */
	private Date approvalTime;
	
	/**
	 * ʱ���ʽ��
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorTime() {
		if(operatorTime!=null){
			return format.format(operatorTime);
		}else{
			return "";
		}
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getApprovalTime() {
		if(approvalTime!=null){
			return format.format(approvalTime);
		}else{
			return "";
		}
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

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
		BlackListOperationVO black = (BlackListOperationVO)vo;
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
        String operator = rs.getString("operator");
        black.setOperator(operator==null?"":operator);
        black.setOperatorTime(rs.getDate("operator_Time"));
		 String approval = rs.getString("approval");
		black.setApproval(approval==null?"":approval);
		black.setApprovalTime(rs.getTimestamp("approval_Time"));
	}

	public Object createObject() {
		return new BlackListOperationVO();
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
