package com.aspire.dotcard.basebook.vo;

/**
 * ����VO  ��Ӧר����Ϣͬ�������¡�����
 * @author x_zhailiqing
 *
 */
public class RCategoryVO {
	
	private int id;
	
	/**
	 * ����ID
	 * ���л���
	 * T2   ����ܰ�  M2 ����°�  W2 ����ܰ�
	 * T3   �����ܰ�  M3 �����°�  W3 �����ܰ�
	 * T5  �����ܰ�  M5 �����°�  W5 �����ܰ�
	 * T7   �ʻ��ܰ�  M7 �ʻ��°�  W7 �ʻ��ܰ�
	 */
	private String categoryId;
	
	/**
	 * ��������
	 */
	private String categoryName;
	
	private String parentId;
	
	/**
	 * ���
	 */
	private String description;
	
	/**
	 * ר��ͼƬ
	 */
	private String picUrl;
	
	/**
	 * �ʷ� ��λ�� ������
	 * ���ʱ����Ϊ��λ *10
	 */
	private int fee;
	
	/**
	 * ���������� 
	 */
	private String url;

	/**
	 * 1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ���
	 */
	private int changeType;
	
	private String catalogType;
	
	public String getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * ��ֵ
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data,String type){
		//����
		if("11".equals(type)){
			if(data.length!=5){
				return false;
			}
			if(null==data[0]||"".equals(data[0].trim())
					||null==data[1]||"".equals(data[1].trim())
					||null==data[3]||"".equals(data[3].trim())
					||null==data[4]||"".equals(data[4].trim())){
				return false;
			}
			this.categoryId = data[0].trim();
			this.categoryName = data[1].trim();
			this.catalogType =  "11";
			this.description = data[2];
			this.fee = Integer.parseInt(data[3].trim())*10;
			this.url = data[4];

			return true;
		}else {
			//ר��
			if(data.length!=6){
				return false;
			}
			if(null==data[0]||"".equals(data[0].trim())
					||null==data[1]||"".equals(data[1].trim())
					||null==data[2]||"".equals(data[2].trim())
					||null==data[5]||"".equals(data[5].trim())){
				return false;
			}	
			int changeType;
			int ctype;
			try {
				changeType = Integer.parseInt(data[5]);
				ctype = Integer.parseInt(data[2]);
			} catch (NumberFormatException e) {
				return false;
			}
			if(changeType!=1&&changeType!=2&&changeType!=3){
				return false;
			}		
			if(ctype!=1&&ctype!=2&&ctype!=3){
				return false;
			}
			this.categoryId = data[0].trim();
			this.categoryName = data[1].trim();
			this.catalogType = data[2].trim();
			this.description = data[3];
			this.picUrl = data[4];
			this.changeType = changeType;

			
			return true;
		}
		
	}		
}
