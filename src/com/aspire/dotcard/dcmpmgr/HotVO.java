package com.aspire.dotcard.dcmpmgr;
/**
 * 消息头条的VO类
 * @author zhangwei
 *
 */
public class HotVO
{
	/**
	 * 内容id
	 */
	private String id;
	/**
	 * 内容的名字，或者标题
	 */
	private String title;
	/**
	 * 内容链接地址
	 */
	private String url;
	/**
	 * 头条id对应的媒体id
	 */
	private String mediaId;
	/**
	 * 头条图片url（由DCMP做终端适配）
	 */
	private String imageUrl;
	/**
	 * 内容正文
	 */
	private String content;
	/**
	 * 头条发布时间
	 */
	private String publishDate;
	
	public String getMediaId()
	{
		return mediaId;
	}
	public void setMediaId(String mediaId)
	{
		this.mediaId = mediaId;
	}
	public String getImageUrl()
	{
		return imageUrl;
	}
	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getPublishDate()
	{
		return publishDate;
	}
	public void setPublishDate(String publishDate)
	{
		this.publishDate = publishDate;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String toString()
	{
	    StringBuffer sb=new StringBuffer();
	    sb.append("内容id：");
	    sb.append(id);
	    sb.append("，内容标题：");
	    sb.append(title);
	    sb.append("，内容链接地址：");
	    sb.append(url);
	    sb.append("，头条id对应的媒体id：");
	    sb.append(mediaId);
	    sb.append("，头条图片url：");
	    sb.append(imageUrl);
	    sb.append("，内容正文：");
	    sb.append(content);
	    sb.append("，头条发布时间：");
	    sb.append(publishDate);
	    
	    return sb.toString();
	}
	
	

}
