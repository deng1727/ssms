package com.aspire.dotcard.dcmpmgr;
/**
 * DCMP时，媒体的VO类
 * @author zhangwei
 *
 */
public class MediaVO
{
	/**
	 * 内容id
	 */
	private String id;
	/**
	 * 内容的名字，或者标题
	 */
	private String name;
	/**
	 * 内容链接地址
	 */
	private String url;

	/**
	 * 图标url（由DCMP做终端适配）
	 */
	private String iconUrl;

	public String getIconUrl()
	{
		return iconUrl;
	}

	public void setIconUrl(String iconUrl)
	{
		this.iconUrl = iconUrl;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
	public boolean equals(Object media)
	{
		if(this==media)
		{
			return true;
		}
		if(media instanceof MediaVO)
		{
			MediaVO temp=(MediaVO)media;
			if(this.id.equals(temp.id))
			{
				return true;
			}
		}
		
		return false;
	}
	public String toString()
	{
		
		StringBuffer content = new StringBuffer();
		content.append("媒体文件的信息为：id ");
		content.append(this.id);
		content.append(",name ");
		content.append(this.name);
		content.append(",iconUrl  ");
		content.append(this.iconUrl);
		content.append(",url ");
		content.append(this.url);
		return content.toString();
	}

}
