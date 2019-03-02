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
public class ReadLikePercentageVO
{
	/**
	 * 记录时间
	 */
	private String recordDay;
	
	/**
	 * 源图书编号
	 */
	private String sourceBookId;
	
	/**
	 * 图书编号
	 */
	private String bookId;
	
	/**
	 * 图书人数百分比
	 */
	private String rate;
	
	/**
	 * 推荐序号
	 */
	private String sortId;
	
	/**
	 * 由一条记录分解的多条信息
	 */
	private List<ReadLikePercentageVO> list;
	
	public String getRecordDay()
	{
		return recordDay;
	}
	
	public void setRecordDay(String recordDay)
	{
		this.recordDay = recordDay;
	}
	
	public String getSourceBookId()
	{
		return sourceBookId;
	}

	public void setSourceBookId(String sourceBookId)
	{
		this.sourceBookId = sourceBookId;
	}

	public String getBookId()
	{
		return bookId;
	}
	
	public void setBookId(String bookId)
	{
		this.bookId = bookId;
	}
	
	public List<ReadLikePercentageVO> getList()
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
		if (data.length != 22)
		{
			return false;
		}
		if (null == data[0] || "".equals(data[0].trim()) || null == data[1]
				|| "".equals(data[1].trim()))
		{
			return false;
		}
		
		if(!allBook.containsKey(data[1]))
		{
			return false;
		}
		
		this.recordDay = data[0];
		this.sourceBookId = data[1];
		list = new ArrayList<ReadLikePercentageVO>();
		
		for (int i = 0; i < 10; i++)
		{
			ReadLikePercentageVO vo = new ReadLikePercentageVO();
			vo.setRecordDay(data[0]);
			vo.setSourceBookId(data[1]);
			String bookId = data[(i + 1) * 2];
			String rate = data[(i + 1) * 2 + 1];
			if (null == bookId || "".equals(bookId.trim()) || null == rate
					|| "".equals(rate.trim()))
			{	
				continue;
			}
			if (!allBook.containsKey(bookId))
			{
				continue;
			}
			vo.setBookId(bookId);
			vo.setRate(rate);
			vo.setSortId(String.valueOf(i + 1));
			list.add(vo);
		}
		
		if(list.size()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public String getSortId()
	{
		return sortId;
	}

	public void setSortId(String sortId)
	{
		this.sortId = sortId;
	}
	
}
