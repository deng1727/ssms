package com.aspire.dotcard.basebook.vo;

/**
 * 推荐图书
 * @author x_zhailiqing
 *
 */
public class RecommendVO {

	/**
	 * 推荐ID
	 */
	private String recommendId;
	/**
	 * 图书ID
	 */
	private String bookId;
	
	/**
	 * 图书分类ID 仅对分类推荐需要填，此字段为空时，表示总体推荐
	 */
	private String typeId;

	/**
	 * 1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现
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
	 * 赋值
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
