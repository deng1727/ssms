package com.aspire.dotcard.baseVideoNew.bo;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoDeleteBlackDAO;
import com.aspire.dotcard.baseVideoNew.dao.BaseVideoNewFileDAO;

public class BaseVideoDeleteBlackBO {
	/**
	 * 用于插入预删除数据至指定中间表
	 * 
	 * @param sql
	 * @param key
	 * @return
	 */
	protected static JLogger logger = LoggerFactory
	.getLogger(BaseVideoDeleteBlackBO.class);
	
	private static BaseVideoDeleteBlackBO bo = new BaseVideoDeleteBlackBO();
	
	public BaseVideoDeleteBlackBO()
	{}
	
	public static BaseVideoDeleteBlackBO getInstance()
	{
		return bo;
	}	

	
	public boolean delVideoBlack()
	{
		String  rsql = "videoblack.BaseVideoDeleteBlackDAO.delVideoBlack.DELETE";
		String  psql = "videoblack.BaseVideoDeleteBlackDAO.delVideoBlack2.DELETE";

		boolean isTrue = true;
		
		try
		{
			BaseVideoDeleteBlackDAO.getInstance().delVideoBlack(rsql, null);
			BaseVideoDeleteBlackDAO.getInstance().delVideoBlack(psql, null);

		}
		catch (BOException e)
		{
			isTrue = false;
			logger.debug("执行删除视频表时发生错误！！！", e);
		}
		return isTrue;
	}
}
