package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.baseread.vo.BookBagVO;

public class BookBagDao
{
	protected static JLogger log = LoggerFactory.getLogger(BookBagDao.class);

	private static BookBagDao instance = new BookBagDao();

	public synchronized static BookBagDao getInstance()
	{

		return instance;
	}

	private BookBagDao()
	{}

	/**
	 * ����ͼ�����
	 * 
	 * @param type
	 * @throws Exception
	 */
	public void addBookBag(BookBagVO bookBag) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("add BookBagVO(" + bookBag + ")");
		}
		// insert into T_RB_BOOKBAG_NEW (BOOKBAGID, BOOKBAGNAME, BOOKBAGDESC,
		// BOOKBAGIMAGE, FEE, ONLINETIME, CATEID, PACKETTYPE) values
		// (?,?,?,?,?,?,?,?)
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagDao.addBookBag";

		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras =
		{ bookBag.getBookBagId(), bookBag.getBookBagName(),
				bookBag.getBookBagDesc(), bookBag.getBookBagPic(),
				new Integer(bookBag.getFee()), bookBag.getOnLineTime(),
				bookBag.getCateId(),
				new Integer(bookBag.getPacketType()) };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ���� �޸�
	 * 
	 * @param type
	 * @throws Exception
	 */
	public void updateBookBag(BookBagVO bookBag) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("update BookBagVO(" + bookBag + ")");
		}

		// update T_RB_BOOKBAG_NEW t set t.bookbagname=?, t.bookbagdesc=?,
		// t.bookbagimage=?, t.fee=?, t.onlinetime=?, t.CATEID=?, t.packettype=?
		// where t.bookbagid=?
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagDao.updateBookBag";

		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras =
		{ bookBag.getBookBagName(), bookBag.getBookBagDesc(),
				bookBag.getBookBagPic(), new Integer(bookBag.getFee()),
				bookBag.getOnLineTime(), bookBag.getCateId(),
				new Integer(bookBag.getPacketType()), bookBag.getBookBagId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ɾ��
	 * 
	 * @param type
	 * @throws Exception
	 */
	public void deleteBookBag(BookBagVO bookBag) throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("deleteBookBag BookBagVO(" + bookBag + ")");
		}
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagDao.deleteBookBag";

		// ������sql�����Ҫ�滻�Ĳ���,
		Object[] paras =
		{ bookBag.getBookBagId() };
		DB.getInstance().executeBySQLCode(sqlCode, paras);
	}

	/**
	 * ����Ƿ����
	 * 
	 * @param type
	 * @return false������ true����
	 * @throws Exception
	 */
	public boolean isExist(BookBagVO bookBag) throws Exception
	{
		int count = 0;
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagDao.isExist";

		ResultSet rs = null;
		try
		{
			Object[] paras =
			{ bookBag.getBookBagId() };
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);

			while (rs.next())
			{
				count = rs.getInt("count");
			}
		}
		catch (SQLException e)
		{
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}
		finally
		{
			DB.close(rs);
		}
		return count == 0 ? false : true;
	}

	/**
	 * ��ѯ����δɾ����ͼ����Ϣ
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map queryAllBookBags() throws Exception
	{
		String sqlCode = "com.aspire.dotcard.baseread.dao.BookBagDao.queryAllBookBags";
		Map m = null;
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			m = new HashMap();
			while (rs.next())
			{
				m.put(rs.getString("bookbagid"), rs.getString("bookbagname"));
			}
		}
		catch (SQLException e)
		{
			log.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}
		finally
		{
			DB.close(rs);
		}
		return m;
	}
}
