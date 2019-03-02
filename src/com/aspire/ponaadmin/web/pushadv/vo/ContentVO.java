package com.aspire.ponaadmin.web.pushadv.vo;

public class ContentVO 
{
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
     * 创建时间
     */
    private String createTime;
    /**
     * 上线时间
     */
    private String marketDate;
    /**
     * 应用类型
     */
    private String catename;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}
	public String getCatename() {
		return catename;
	}
	public void setCatename(String catename) {
		this.catename = catename;
	}

}
