package com.aspire.dotcard.basebook.vo;

/**
 * 货架VO  对应专区信息同步、包月、排行
 * @author x_zhailiqing
 *
 */
public class RCategoryVO {
	
	private int id;
	
	/**
	 * 货架ID
	 * 排行货架
	 * T2   点击总榜  M2 点击月榜  W2 点击周榜
	 * T3   畅销总榜  M3 畅销月榜  W3 畅销周榜
	 * T5  搜索总榜  M5 搜索月榜  W5 搜索周榜
	 * T7   鲜花总榜  M7 鲜花月榜  W7 鲜花周榜
	 */
	private String categoryId;
	
	/**
	 * 货架名称
	 */
	private String categoryName;
	
	private String parentId;
	
	/**
	 * 简介
	 */
	private String description;
	
	/**
	 * 专区图片
	 */
	private String picUrl;
	
	/**
	 * 资费 单位分 包月用
	 * 入库时用厘为单位 *10
	 */
	private int fee;
	
	/**
	 * 包月类型用 
	 */
	private String url;

	/**
	 * 1：新增；2：更新；3：下线。全量文件中都是1，增量文件中三种值都可能出现
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
	 * 赋值
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data,String type){
		//包月
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
			//专区
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
