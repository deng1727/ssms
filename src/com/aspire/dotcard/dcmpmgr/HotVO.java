package com.aspire.dotcard.dcmpmgr;
/**
 * ��Ϣͷ����VO��
 * @author zhangwei
 *
 */
public class HotVO
{
	/**
	 * ����id
	 */
	private String id;
	/**
	 * ���ݵ����֣����߱���
	 */
	private String title;
	/**
	 * �������ӵ�ַ
	 */
	private String url;
	/**
	 * ͷ��id��Ӧ��ý��id
	 */
	private String mediaId;
	/**
	 * ͷ��ͼƬurl����DCMP���ն����䣩
	 */
	private String imageUrl;
	/**
	 * ��������
	 */
	private String content;
	/**
	 * ͷ������ʱ��
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
	    sb.append("����id��");
	    sb.append(id);
	    sb.append("�����ݱ��⣺");
	    sb.append(title);
	    sb.append("���������ӵ�ַ��");
	    sb.append(url);
	    sb.append("��ͷ��id��Ӧ��ý��id��");
	    sb.append(mediaId);
	    sb.append("��ͷ��ͼƬurl��");
	    sb.append(imageUrl);
	    sb.append("���������ģ�");
	    sb.append(content);
	    sb.append("��ͷ������ʱ�䣺");
	    sb.append(publishDate);
	    
	    return sb.toString();
	}
	
	

}
