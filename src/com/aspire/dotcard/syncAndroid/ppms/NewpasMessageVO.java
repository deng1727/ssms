package com.aspire.dotcard.syncAndroid.ppms;
import java.sql.Timestamp;

public class NewpasMessageVO  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String transactionid;//事物ID
	private String categoryid;//货架ID
	private String message;//消息体
	private String state;//状态，-1：未发送，0，已发送到数据中心准现网，1：已发送到数据中心现网
	private String contentId;//内容ID
	
	private Timestamp lupdate;
	
	private Timestamp createdate;
	
    public String logString()
    {
        return "事物ID："+transactionid+",货架ID："+categoryid+",内容ID："+contentId+",消息体："+message;
    }
	
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public Timestamp getLupdate() {
		return lupdate;
	}

	public void setLupdate(Timestamp lupdate) {
		this.lupdate = lupdate;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

}
