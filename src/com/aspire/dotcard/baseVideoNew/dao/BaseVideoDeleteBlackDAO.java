package com.aspire.dotcard.baseVideoNew.dao;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

public class BaseVideoDeleteBlackDAO {
	/**
	 * ���ڲ�����Ƶ������
	 * 
	 * @param sql
	 * @param key
	 * @return
	 * @throws BOException
	 */
	protected static JLogger logger = LoggerFactory
	.getLogger(BaseVideoDeleteBlackDAO.class);
	private static BaseVideoDeleteBlackDAO dao = new BaseVideoDeleteBlackDAO();

	private BaseVideoDeleteBlackDAO()
	{}
	
	public static BaseVideoDeleteBlackDAO getInstance()
	{
		return dao;
	}
	public int delVideoBlack(String sql, String[] key) throws BOException
	{
		int ret = 0;
		
		try
		{
			ret = DB.getInstance().executeBySQLCode(sql, key);
		}
		catch (DAOException e)
		{
			logger.error("ɾ����Ƶ������ʧ��:", e);
			throw new BOException("ɾ����Ƶ��ʱ������:" + PublicUtil.GetCallStack(e)
					+ "<br>");
		}
		
		return ret;
	}
	
}
