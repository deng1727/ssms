package com.aspire.dotcard.basebook.vo;

/**
 * �Ƽ�ͼ��
 * @author x_zhailiqing
 *
 */
public class RecommendVO {

	/**
	 * �Ƽ�ID
	 */
	private String recommendId;
	/**
	 * ͼ��ID
	 */
	private String bookId;
	
	/**
	 * ͼ�����ID ���Է����Ƽ���Ҫ����ֶ�Ϊ��ʱ����ʾ�����Ƽ�
	 */
	private String typeId;

	/**
	 * 1��������2�����£�3�����ߡ�ȫ���ļ��ж���1�������ļ�������ֵ�����ܳ���
	 */
	private int changeType;
	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}	
	
	public String getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * ��ֵ
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data){
		if(data.length!=4){
			return false;
		}
		if(null==data[0]||"".equals(data[0].trim())
				||null==data[2]||"".equals(data[2].trim())
				||null==data[3]||"".equals(data[3].trim())){
			return false;
		}
		int changeType;
		try {
			changeType = Integer.parseInt(data[3]);
		} catch (NumberFormatException e) {
			return false;
		}
		if(changeType!=1&&changeType!=2&&changeType!=3){
			return false;
		}
		this.recommendId = data[0].trim();
		this.typeId = data[1];
		this.bookId = data[2].trim();
		this.changeType = changeType;
		return true;
		
	}	
}
