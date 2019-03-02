/**
 * SSMS
 * com.aspire.ponaadmin.web.datasync.implement.music BMusicVO.java
 * May 7, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basemusic;

/**
 * @author tungke
 *
 */
public class BNewMusicAlbumVO
{
	
	private String albumId ;
	private String singersid;
	private String albumupcase ;
	private String albumName ;
	private String albumDesc;
	private String albumpicurl;
	private String singer;
	
	private String parentId;
	private Integer sortId;
	
	private String categoryId;
	
	private String pubtime;
	
	private  String changetype;
	private int delFlag;
	/**
	 * @return Returns the delFlag.
	 */
	public int getDelFlag()
	{
		return delFlag;
	}
	/**
	 * @param delFlag The delFlag to set.
	 */
	public void setDelFlag(int delFlag)
	{
		this.delFlag = delFlag;
	}
	/**
	 * @return Returns the changetype.
	 */
	public String getChangetype()
	{
		return changetype;
	}
	/**
	 * @param changetype The changetype to set.
	 */
	public void setChangetype(String changetype)
	{
		this.changetype = changetype;
	}
	/**
	 * @return Returns the albumDesc.
	 */
	public String getAlbumDesc()
	{
		return albumDesc;
	}
	/**
	 * @param albumDesc The albumDesc to set.
	 */
	public void setAlbumDesc(String albumDesc)
	{
		this.albumDesc = albumDesc;
	}
	/**
	 * @return Returns the albumId.
	 */
	public String getAlbumId()
	{
		return albumId;
	}
	/**
	 * @param albumId The albumId to set.
	 */
	public void setAlbumId(String albumId)
	{
		this.albumId = albumId;
	}
	/**
	 * @return Returns the albumName.
	 */
	public String getAlbumName()
	{
		return albumName;
	}
	/**
	 * @param albumName The albumName to set.
	 */
	public void setAlbumName(String albumName)
	{
		this.albumName = albumName;
	}
	/**
	 * @return Returns the albumpicurl.
	 */
	public String getAlbumpicurl()
	{
		return albumpicurl;
	}
	/**
	 * @param albumpicurl The albumpicurl to set.
	 */
	public void setAlbumpicurl(String albumpicurl)
	{
		this.albumpicurl = albumpicurl;
	}
	/**
	 * @return Returns the albumupcase.
	 */
	public String getAlbumupcase()
	{
		return albumupcase;
	}
	/**
	 * @param albumupcase The albumupcase to set.
	 */
	public void setAlbumupcase(String albumupcase)
	{
		this.albumupcase = albumupcase;
	}
	/**
	 * @return Returns the pubtime.
	 */
	public String getPubtime()
	{
		return pubtime;
	}
	/**
	 * @param pubtime The pubtime to set.
	 */
	public void setPubtime(String pubtime)
	{
		this.pubtime = pubtime;
	}
	/**
	 * @return Returns the singersid.
	 */
	public String getSingersid()
	{
		return singersid;
	}
	/**
	 * @param singersid The singersid to set.
	 */
	public void setSingersid(String singersid)
	{
		this.singersid = singersid;
	}
	/**
	 * @return Returns the singer.
	 */
	public String getSinger()
	{
		return singer;
	}
	/**
	 * @param singer The singer to set.
	 */
	public void setSinger(String singer)
	{
		this.singer = singer;
	}
	/**
	 * @return Returns the parentId.
	 */
	public String getParentId()
	{
		return parentId;
	}
	/**
	 * @param parentId The parentId to set.
	 */
	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}
	
	/**
	 * @return Returns the sortId.
	 */
	public Integer getSortId()
	{
		return sortId;
	}
	/**
	 * @param sortId The sortId to set.
	 */
	public void setSortId(Integer sortId)
	{
		this.sortId = sortId;
	}
	/**
	 * @return Returns the categoryId.
	 */
	public String getCategoryId()
	{
		return categoryId;
	}
	/**
	 * @param categoryId The categoryId to set.
	 */
	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}
	
	
	
	

	
	
}
