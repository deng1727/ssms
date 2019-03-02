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
public class BMusicVO
{
	
	private String musicId ;
	private String songname;
	private String singer ;
	private String validity ;
	private String productMask;
	
	//发布时间
	private String pubtime;
	
	//在线听歌,1:表示支持;0:表示不支持
	private Integer onlinetype;
	//彩铃 1:表示支持;0:表示不支持
	private Integer colortype;
	//震铃  1:表示支持;0:表示不支持
	private Integer ringtype;
	//全曲 1:表示支持;0:表示不支持
	private Integer songtype;
	
	private String singersId;
	
	private String changetype;
	//杜比字段
	private String dolbytype;
	//无损音乐
	private String losslessmusic;
	//320kbps格式
	private String format320kbps;
	
	
	private int delFlag;
	
	/**
	 * 歌曲图片
	 */
	private String musicImage;
	public String getMusicImage()
	{
		return musicImage;
	}
	public void setMusicImage(String musicImage)
	{
		this.musicImage = musicImage;
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
	 * @return Returns the musicId.
	 */
	public String getMusicId()
	{
		return musicId;
	}
	/**
	 * @param musicId The musicId to set.
	 */
	public void setMusicId(String musicId)
	{
		this.musicId = musicId;
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
	 * @return Returns the songname.
	 */
	public String getSongname()
	{
		return songname;
	}
	/**
	 * @param songname The songname to set.
	 */
	public void setSongname(String songname)
	{
		this.songname = songname;
	}
	/**
	 * @return Returns the validity.
	 */
	public String getValidity()
	{
		return validity;
	}
	/**
	 * @param validity The validity to set.
	 */
	public void setValidity(String validity)
	{
		this.validity = validity;
	}
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
	 * @return Returns the productMask.
	 */
	public String getProductMask()
	{
		return productMask;
	}
	/**
	 * @param productMask The productMask to set.
	 */
	public void setProductMask(String productMask)
	{
		this.productMask = productMask;
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
	 * @return Returns the colortype.
	 */
	public Integer getColortype()
	{
		return colortype;
	}
	/**
	 * @param colortype The colortype to set.
	 */
	public void setColortype(Integer colortype)
	{
		this.colortype = colortype;
	}
	/**
	 * @return Returns the onlinetype.
	 */
	public Integer getOnlinetype()
	{
		return onlinetype;
	}
	/**
	 * @param onlinetype The onlinetype to set.
	 */
	public void setOnlinetype(Integer onlinetype)
	{
		this.onlinetype = onlinetype;
	}
	/**
	 * @return Returns the ringtype.
	 */
	public Integer getRingtype()
	{
		return ringtype;
	}
	/**
	 * @param ringtype The ringtype to set.
	 */
	public void setRingtype(Integer ringtype)
	{
		this.ringtype = ringtype;
	}
	/**
	 * @return Returns the songtype.
	 */
	public Integer getSongtype()
	{
		return songtype;
	}
	/**
	 * @param songtype The songtype to set.
	 */
	public void setSongtype(Integer songtype)
	{
		this.songtype = songtype;
	}
	/**
	 * @return Returns the singersId.
	 */
	public String getSingersId()
	{
		return singersId;
	}
	/**
	 * @param singersId The singersId to set.
	 */
	public void setSingersId(String singersId)
	{
		this.singersId = singersId;
	}
	public String getDolbytype() {
		return dolbytype;
	}
	public void setDolbytype(String dolbytype) {
		this.dolbytype = dolbytype;
	}
	public String getLosslessmusic() {
		return losslessmusic;
	}
	public void setLosslessmusic(String losslessmusic) {
		this.losslessmusic = losslessmusic;
	}
	public String getFormat320kbps() {
		return format320kbps;
	}
	public void setFormat320kbps(String format320kbps) {
		this.format320kbps = format320kbps;
	}

	
	
}
