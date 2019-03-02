package com.aspire.dotcard.basebook.vo;

/**
 * ����ͼ����Ʒ��
 * @author x_zhailiqing
 *
 */
public class ReferenceVO {
	
	/**
	 * ������ID
	 */
	private int cId;
	
	/**
	 * ר�������¡����ж�Ӧ��id
	 */
	private String categoryId;
	
	/**
	 * ͼ��ID
	 */
	private String bookId;
	
	/**
	 * ������ ����
	 */
	private int sortNumber;
	
	/**
	 * ������Ʒ��
	 */
	private int rankValue;

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
	
	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public int getRankValue() {
		return rankValue;
	}

	public void setRankValue(int rankValue) {
		this.rankValue = rankValue;
	}
	
	/**
	 * ��ֵ ר����
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data){
		if(data.length!=3){
			return false;
		}
		if(null==data[0]||"".equals(data[0].trim())
				||null==data[1]||"".equals(data[1].trim())
				||null==data[2]||"".equals(data[2].trim())){
			return false;
		}
		int changeType;
		try {
			changeType = Integer.parseInt(data[2]);
		} catch (NumberFormatException e) {
			return false;
		}
		if(changeType!=1&&changeType!=2&&changeType!=3){
			return false;
		}
		this.categoryId = data[0].trim();
		this.bookId = data[1].trim();
		this.changeType = changeType;
		return true;
		
	}	
}
