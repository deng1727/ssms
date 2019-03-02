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
public class ReadLikeAuthorVO
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
	 * 作者id
	 */
	private String authorId;
	
	/**
	 * 1：专属名家 2：关联名家 3：热门作家
	 */
	private String recType;
	
	/**
	 * 推荐序号
	 */
	private String sortId;
	
	/**
	 * 由一条记录分解的多条信息
	 */
	private List<ReadLikeAuthorVO> list;
	
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
	
	public List<ReadLikeAuthorVO> getList()
	{
		return list;
	}
	
	/**
	 * 赋值
	 * 
	 * @param data
	 * @return
	 */
	public boolean setValue(String[] data, Map<String, String> allBook,
			Map<String, String> authorMap)
	{
		if (data.length != 32)
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
		list = new ArrayList<ReadLikeAuthorVO>();
		
		for (int i = 0; i < 10; i++)
		{
			ReadLikeAuthorVO vo = new ReadLikeAuthorVO();
			vo.setRecordDay(data[0]);
			vo.setMsisdn(data[1]);
			String authorId = data[(i * 3 + 1) + 1];
			String bookId = data[(i * 3 + 1) + 2];
			String recType = data[(i * 3 + 1) + 3];
			if (null == bookId || "".equals(bookId.trim()) || null == authorId
					|| "".equals(authorId.trim()) || null == recType
					|| "".equals(recType.trim()))
			{
				continue;
			}
			if (!allBook.containsKey(bookId))
			{
				continue;
			}
			if (!authorMap.containsKey(authorId))
			{
				continue;
			}
			vo.setAuthorId(authorId);
			vo.setBookId(bookId);
			vo.setRecType(recType);
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
	
	public String getAuthorId()
	{
		return authorId;
	}
	
	public void setAuthorId(String authorId)
	{
		this.authorId = authorId;
	}
	
	public String getSortId()
	{
		return sortId;
	}
	
	public void setSortId(String sortId)
	{
		this.sortId = sortId;
	}
	
	public String getRecType()
	{
		return recType;
	}
	
	public void setRecType(String recType)
	{
		this.recType = recType;
	}
	
}
