package com.aspire.dotcard.syncAndroid.ppms;
import java.sql.Timestamp;

public class NewpasMessageVO  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String transactionid;//����ID
	private String categoryid;//����ID
	private String message;//��Ϣ��
	private String state;//״̬��-1��δ���ͣ�0���ѷ��͵���������׼������1���ѷ��͵�������������
	private String contentId;//����ID
	
	private Timestamp lupdate;
	
	private Timestamp createdate;
	
    public String logString()
    {
        return "����ID��"+transactionid+",����ID��"+categoryid+",����ID��"+contentId+",��Ϣ�壺"+message;
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
