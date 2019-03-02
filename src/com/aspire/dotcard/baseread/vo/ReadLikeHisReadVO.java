package com.aspire.dotcard.baseread.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 推荐图书
 * 
 * @author x_zhailiqing
 * 
 */
public class ReadLikeHisReadVO
{
	/**
	 * 记录时间
	 */
	private String recordDay;
	/**
	 * 用户号码
	 */
	private String msisdn;
	
	/**
	 * 图书编号
	 */
	private String bookId;
	
	/**
	 * 推荐理由
	 */
	private String reson;
	
	/**
	 * 推荐序号
	 */
	private String sortId;
	
	/**
	 * 由一条记录分解的多条信息
	 */
	private List<ReadLikeHisReadVO> list;
	
	public String getRecordDay()
	{
		return recordDay;
	}
	
	public void setRecordDay(String recordDay)
	{
		this.recordDay = recordDay;
	}
	
	public String getMsisdn()
	{
		return msisdn;
	}
	
	public void setMsisdn(String msisdn)
	{
		this.msisdn = msisdn;
	}
	
	public String getBookId()
	{
		return bookId;
	}
	
	public void setBookId(String bookId)
	{
		this.bookId = bookId;
	}
	
	public List<ReadLikeHisReadVO> getList()
	{
		return list;
	}
	
	/**
	 * 赋值
	 * 
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data, Map<String, String> allBook)
	{
		if (data.length != 102)
		{
			return false;
		}
		if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
				|| "".equals(data[1].trim()))
		{
			return false;
		}
		
		this.recordDay = data[0];
		this.msisdn = data[1];
		list = new ArrayList<ReadLikeHisReadVO>();
		
		for (int i = 0; i < 50; i++)
		{
			ReadLikeHisReadVO vo = new ReadLikeHisReadVO();
			vo.setRecordDay(data[0]);
			vo.setMsisdn(data[1]);
			String bookId = data[(i + 1) * 2];
			String reson = data[(i + 1) * 2 + 1];
			if (null == bookId || "".equals(bookId.trim()) || null == reson
					|| "".equals(reson.trim()))
			{	
				continue;
			}
			if (!allBook.containsKey(data[(i + 1) * 2]))
			{
				continue;
			}
			vo.setBookId(bookId);
			vo.setReson(reson);
			vo.setSortId(String.valueOf(i + 1));
			list.add(vo);
		}
		
		if (list.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getSortId()
	{
		return sortId;
	}
	
	public void setSortId(String sortId)
	{
		this.sortId = sortId;
	}
	
	public String getReson()
	{
		return reson;
	}
	
	public void setReson(String reson)
	{
		this.reson = reson;
	}
	
}
