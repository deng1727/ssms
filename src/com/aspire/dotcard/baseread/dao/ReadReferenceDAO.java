/*
 * �ļ�����ReadReferenceDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */
package com.aspire.dotcard.baseread.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.db.SQLUtil;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.common.persistence.DataAccessException;
import com.aspire.common.persistence.util.SQLCode;
import com.aspire.dotcard.baseVideo.vo.VideoRefVO;
import com.aspire.dotcard.baseread.vo.ReadCategoryVO;
import com.aspire.dotcard.baseread.vo.ReadReferenceVO;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.datafield.vo.ResourceVO;
import com.aspire.ponaadmin.web.db.TransactionDB;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class ReadReferenceDAO {
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger logger = LoggerFactory
			.getLogger(ReadReferenceDAO.class);

	/**
	 * singletonģʽ��ʵ��
	 */
	private static ReadReferenceDAO instance = new ReadReferenceDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private ReadReferenceDAO() {
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static ReadReferenceDAO getInstance() {
		return instance;
	}

	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class ReadReferencePageVO implements PageVOInterface {
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			ReadReferenceVO vo = (ReadReferenceVO) content;

			vo.setCId(String.valueOf(rs.getInt("cId")));
			vo.setBookId(rs.getString("bookId"));
			vo.setBookName(rs.getString("bookName"));
			vo.setAuthorName(rs.getString("authorname"));
			vo.setSortNumber(String.valueOf(rs.getInt("sortNumber")));
			vo.setRankValue(String.valueOf(rs.getInt("rankValue")));
			vo.setTypeName(rs.getString("typename"));
			vo.setLastUpTime(rs.getString("lastuptime"));
			vo.setChargeType(rs.getString("chargeType"));
			vo.setFee(rs.getString("fee"));
			vo.setVerify_status(rs.getString("VERIFY_STATUS"));
		}

		public Object createObject() {
			return new ReadReferenceVO();
		}
	}

	/**
	 * ���ڲ�ѯ��ǰ��������Ʒ�б�
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryReadRefList(PageResult page, ReadReferenceVO vo)
			throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryReadRefList(" + vo.getCId()
					+ ") is starting ...");
		}

		// select r.*, t.typename, a.authorname, b.bookname,
		// to_char(r.lupdate,'YYYYMMDD') lastuptime from T_RB_REFERENCE_NEW r,
		// T_RB_BOOK_NEW b, T_RB_TYPE_NEW t, t_rb_author_new a where r.bookid =
		// b.bookid and b.typeid = t.typeid(+) and b.authorid = a.authorid(+)
		String sqlCode = "read.ReadReferenceDAO.queryReadRefList().SELECT";
		String sql = null;

		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();

			// ����������sql�Ͳ���
			if (!"".equals(vo.getCId())) {
				sqlBuffer.append(" and r.cId =? ");
				paras.add(vo.getCId());
			}
			if (!"".equals(vo.getBookId())) {
				sqlBuffer.append(" and b.bookid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getBookId()) + "%");
			}
			if (!"".equals(vo.getBookName())) {
				sqlBuffer.append(" and b.bookname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getBookName()) + "%");
			}
			if (!"".equals(vo.getAuthorName())) {
				sqlBuffer.append(" and a.authorname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getAuthorName()) + "%");
			}
			if (!"".equals(vo.getTypeName())) {
				sqlBuffer.append(" and t.typeName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getTypeName()) + "%");
			}
			if (!"".equals(vo.getChargeType())) {
				sqlBuffer.append(" and b.chargeType = ?  ");
				paras.add(vo.getChargeType());
			}
			
			if (!"".equals(vo.getVerify_status())&&!"-1".equals(vo.getVerify_status()))
     		{
    			if(vo.getVerify_status().contains(",")){
    				String[] strs = vo.getVerify_status().split(",");
    				sqlBuffer.append(" and r.VERIFY_STATUS in (" + strs[0] + "," + strs[1] + ")");
    			}else{
    				sqlBuffer.append(" and r.VERIFY_STATUS = " + vo.getVerify_status());
    			}
     		}

			sqlBuffer.append(" order by r.sortnumber desc");

			page.excute(sqlBuffer.toString(), paras.toArray(),
					new ReadReferencePageVO());
		} catch (DataAccessException e) {
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}

	/**
	 * �����Ƴ�ָ��������ָ����ͼ��
	 * 
	 * @param categoryId
	 *            ����id
	 * @param bookId
	 *            ͼ��id��
	 * @throws DAOException
	 */
	public void removeReadRefs(String categoryId, String[] bookId)
			throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("removeBookRefs(" + categoryId + ") is starting ...");
		}

		// update T_RB_REFERENCE_NEW r set r.delflag = r.verify_status,r.verify_date= sysdate,r.verify_status = 0 where r.cid = ? and r.bookid = ?
		String sqlCode = "read.ReadReferenceDAO.removeReadRefs().remove";
		
		 TransactionDB tdb = null;
	        try
	        {
	        	 tdb = TransactionDB.getTransactionInstance();
	        	 for (int i = 0; i < bookId.length; i++)
	             {
	        		 String[] temp = bookId[i].split("#");
	        		 tdb.executeBySQLCode(sqlCode, new Object[]{categoryId,temp[0]});
	             }
	            this.setCategoryGoodsApproval(tdb, categoryId);
	            tdb.commit();
	        }
	        catch (DAOException e)
	        {
	        	logger.error("�Ƴ�ָ��������ָ����ͼ��ʱ�����쳣:", e);
	            // ִ�лع�
	            tdb.rollback();
	            throw new DAOException("�Ƴ�ָ��������ָ����ͼ��ʱ�����쳣:", e);
	        } finally {
	            if (tdb != null) {
	                tdb.close();
	              }
	        }
	}

	/**
	 * ���������Ķ�������ͼ����Ʒ����ֵ
	 * 
	 * @param categoryId
	 *            ����id
	 * @param setSortId
	 *            ͼ������id
	 * @throws DAOException
	 */
	public void setReadSort(String categoryId, String[] setSortId)
			throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("setBookSort(" + categoryId + ") is starting ...");
		}

		// update T_RB_REFERENCE_NEW r set r.sortnumber=?,r.verify_date= sysdate,r.verify_status = 0 where r.bookid=? and r.cid=?
		String sqlCode = "read.ReadReferenceDAO.setReadSort().set";
		 // �����������
        TransactionDB tdb = null;
        try {
       		tdb = TransactionDB.getTransactionInstance();
       		 for (int i = 0; i < setSortId.length; i++)
               {
                   String[] temp = setSortId[i].split("_");
                   tdb.executeBySQLCode(sqlCode, new Object[]{temp[1],temp[0],categoryId});
               }
       		   this.setCategoryGoodsApproval(tdb,categoryId);
              tdb.commit();
          }
          catch (DAOException e)
          {
          	logger.error("�����Ķ�������ͼ����Ʒ����ֵʱ�����쳣:", e);
  			// ִ�лع�
  			tdb.rollback();
              throw new DAOException("�����Ķ�������ͼ����Ʒ����ֵʱ�����쳣:", e);
          } finally {
  			if (tdb != null) {
  				tdb.close();
  			}
  		}
	}

	/**
	 * Ӧ�����ҳ��ȡVO��ʵ����
	 */
	private class ReadPageVO implements PageVOInterface {
		public void CopyValFromResultSet(Object content, ResultSet rs)
				throws SQLException {
			ReadReferenceVO vo = (ReadReferenceVO) content;

			vo.setBookId(rs.getString("bookId"));
			vo.setBookName(rs.getString("bookName"));
			vo.setAuthorName(rs.getString("authorName"));
			vo.setTypeName(rs.getString("typeName"));
			vo.setDesc(rs.getString("description"));
			vo.setIsFinish(rs.getString("isfinish"));
			vo.setChargeType(rs.getString("chargeType"));
			vo.setFee(rs.getString("fee"));
			vo.setShortRecommend(rs.getString("shortRecommend"));
		}

		public Object createObject() {
			return new ReadReferenceVO();
		}
	}

	/**
	 * ���ڲ�ѯͼ���б�
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public void queryReadList(PageResult page, ReadReferenceVO vo)
			throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("queryBookList( ) is starting ...");
		}

		// select * from T_RB_BOOK_NEW b, T_RB_TYPE_NEW t where b.delflag = 0
		// and b.typeid = t.typeid
		String sqlCode = "read.ReadReferenceDAO.queryReadList().SELECT";
		String sql = null;

		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();
			// ����������sql�Ͳ���
			if (!"".equals(vo.getBookId())) {
				sqlBuffer.append(" and b.bookid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getBookId()) + "%");
			}
			if (!"".equals(vo.getBookName())) {
				sqlBuffer.append(" and b.bookname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getBookName()) + "%");
			}
			if (!"".equals(vo.getAuthorName())) {
				sqlBuffer.append(" and a.authorname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getAuthorName()) + "%");
			}
			if (!"".equals(vo.getTypeName())) {
				sqlBuffer.append(" and t.typename like ? ");
				paras.add("%" + SQLUtil.escape(vo.getTypeName()) + "%");
			}
			if (!"".equals(vo.getChargeType())) {
				sqlBuffer.append(" and b.chargeType = ?  ");
				paras.add(vo.getChargeType());
			}

			sqlBuffer.append(" order by bookid");

			page
					.excute(sqlBuffer.toString(), paras.toArray(),
							new ReadPageVO());
		} catch (DataAccessException e) {
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		}
	}

	/**
	 * ���ڲ鿴ָ���������Ƿ����ָ��ͼ��
	 * 
	 * @param categoryId
	 *            ����id
	 * @param bookId
	 *            ͼ��id��
	 * @throws DAOException
	 */
	public String isHasReadRefs(String categoryId, String[] bookId)
			throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("isHasReadRefs(" + categoryId + ") is starting ...");
		}

		// select t.bookid from T_RB_REFERENCE_NEW t where 1=1
		String sqlCode = "read.ReadReferenceDAO.isHasReadRefs().SELECT";
		StringBuffer sql;
		ResultSet rs = null;
		String ret = "";
		StringBuffer temp = new StringBuffer("");

		try {
			sql = new StringBuffer(SQLCode.getInstance().getSQLStatement(
					sqlCode));

			sql.append(" and t.cId='").append(categoryId).append("' ");

			for (int i = 0; i < bookId.length; i++) {
				temp.append("'").append(bookId[i]).append("'").append(",");
			}

			if (temp.length() > 0) {
				temp.deleteCharAt(temp.length() - 1);
				sql.append(" and t.bookid in ( ").append(temp).append(" )");
			}

			rs = DB.getInstance().query(sql.toString(), null);

			while (rs.next()) {
				ret += rs.getString("bookid") + ". ";
			}

		} catch (DataAccessException e) {
			throw new DAOException(
					"��sql.properties�л�ȡsql���ʱ�������󣬶�ӦsqlCode�����ڡ�", e);
		} catch (SQLException e) {
			throw new DAOException("�鿴ָ���������Ƿ����ָ��ͼ��ʱ�����쳣:", e);
		} finally {
			DB.close(rs);
		}

		return ret;
	}

	/**
	 * �������ָ����ͼ����������
	 * 
	 * @param categoryId
	 *            ����id
	 * @param bookId
	 *            ͼ��id��
	 * @throws DAOException
	 */
	public void addReadRefs(ReadCategoryVO categoryVO, String[] bookId)
			throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("addReadRefs(" + categoryVO.getCategoryId() + ") is starting ...");
		}

		// insert into T_RB_REFERENCE_NEW  (cid, bookid, categoryid, sortNumber, rankValue,verify_status,verify_date) 
		//values (?, ?, ?, (select decode(max(sortNumber), null, 1, max(sortNumber) + 1) from T_RB_REFERENCE_NEW  n where n.cid = ?), 0,0,sysdate)
		String sqlCode = "read.ReadReferenceDAO.addReadRefs().add";
		
		 // �����������
        TransactionDB tdb = null;
        try {
       		tdb = TransactionDB.getTransactionInstance();
       		 for (int i = 0; i < bookId.length; i++)
               {
                   tdb.executeBySQLCode(sqlCode, new Object[]{categoryVO.getId(),bookId[i], categoryVO.getCategoryId(),categoryVO.getId()});
               }
       		   this.setCategoryGoodsApproval(tdb,categoryVO.getId());
              tdb.commit();
          }
          catch (DAOException e)
          {
          	logger.error("���ָ����ͼ����������ʱ�����쳣:", e);
  			// ִ�лع�
  			tdb.rollback();
              throw new DAOException("���ָ����ͼ����������ʱ�����쳣:", e);
          } finally {
  			if (tdb != null) {
  				tdb.close();
  			}
  		}
	}

	/**
	 * �鿴��ǰ�������Ƿ񻹴�������Ʒ
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public int hasRead(String categoryId) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("hasRead(" + categoryId + " ) is starting ...");
		}

		// select count(*) as countNum from T_RB_REFERENCE_NEW t where t.cid = ?
		String sqlCode = "read.ReadReferenceDAO.hasRead().SELECT";
		ResultSet rs = null;
		int countNum = 0;

		rs = DB.getInstance().queryBySQLCode(sqlCode,
				new Object[] { categoryId });

		try {
			if (rs.next()) {
				countNum = rs.getInt("countNum");
			}
		} catch (SQLException e) {
			throw new DAOException("���ص�ǰ�������Ƿ񻹴�������Ʒ��Ϣ�����쳣:", e);
		}

		finally {
			DB.close(rs);
		}

		return countNum;
	}

	/**
	 * �������ԭ������ͼ��
	 * 
	 * @param categoryId
	 *            ����id
	 * @throws DAOException
	 */
	public void delBookRef(String categoryId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("delBookRef(" + categoryId + ") is starting ...");
		}

		// delete from T_RB_REFERENCE_NEW r where r.cid = ?
		String sql = "read.ReadReferenceDAO.delBookRef().remove";

		try {
			DB.getInstance().executeBySQLCode(sql, new Object[] { categoryId });
		} catch (DAOException e) {
			throw new DAOException("�������ԭ������ͼ��ʱ�����쳣:", e);
		}
	}

	/**
	 * У��ļ��Д����Ƿ���ͼ����д���
	 * 
	 * @param list
	 * @throws DAOException
	 */
	public String verifyBook(List list) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("verifyBook() is starting ...");
		}

		// select 1 from t_rb_book_new c where c.bookid = ?
		String sql = "read.ReadReferenceDAO.verifyBook().select";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		// ������ԃ
		for (int i = 0; i < list.size(); i++) {
			String temp = (String) list.get(i);
			try {
				rs = DB.getInstance().queryBySQLCode(sql.toString(),
						new Object[] { temp });
				// �����������������
				if (!rs.next()) {
					list.remove(i);
					i--;
					sb.append(temp).append(". ");
				}
			} catch (SQLException e) {
				throw new DAOException("�鿴ָ���������Ƿ����ָ������ʱ�����쳣:", e);
			} finally {
				DB.close(rs);
			}
		}

		return sb.toString();
	}

	/**
	 * ���ڲ�ѯͼ������
	 * 
	 * @param page
	 * @param vo
	 * @throws DAOException
	 */
	public ReadReferenceVO queryReadInfo(String bookId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("queryBookInfo( ) is starting ...");
		}
		// select bookid,bookname,t.description,a.authorname from T_RB_BOOK_NEW
		// t, T_RB_AUTHOR_NEW a where t.bookid='' and t.authorid = a.authorname
		String sqlCode = "read.ReadReferenceDAO.queryReadInfo().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { bookId };
		ReadReferenceVO vo = null;
		try {
			rs = db.queryBySQLCode(sqlCode, paras);
			if (rs.next()) {
				vo = new ReadReferenceVO();
				vo.setBookId(rs.getString("bookid"));
				vo.setBookName(rs.getString("bookname"));
				vo.setDesc(rs.getString("DESCRIPTION"));
				vo.setAuthorName(rs.getString("AUTHORNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("ִ�в�ѯ��������ʧ��", e);
		} finally {
			DB.close(rs);
		}
		return vo;
	}

	/**
	 * 
	 * @desc ��ȡͼ�����չ�ֶ�
	 * @author dongke Aug 9, 2011
	 * @param musicId
	 * @return
	 * @throws DAOException
	 */
	public List queryReadKeyResource(String bookId) throws DAOException {
		List keyResourceList = null;
		if (logger.isDebugEnabled()) {
			logger.debug("queryReadKeyResource( ) is starting ...");
		}
		// select * from t_key_base b, (select * from t_key_resource r where
		// r.tid = ?) y where b.keytable = 't_rb_book_new' and b.keyid =
		// y.keyid(+)
		String sqlCode = "read.ReadReferenceDAO.queryReadKeyResource().SELECT";
		DB db = DB.getInstance();
		ResultSet rs = null;
		Object paras[] = { bookId };
		try {
			keyResourceList = new ArrayList();
			rs = db.queryBySQLCode(sqlCode, paras);
			while (rs.next()) {
				ResourceVO vo = new ResourceVO();
				vo.setKeyid(rs.getString("keyid"));
				vo.setKeyname(rs.getString("keyname"));
				vo.setKeytable(rs.getString("keytable"));
				vo.setKeydesc(rs.getString("keydesc"));
				vo.setKeyType(rs.getString("keytype"));
				vo.setTid(rs.getString("tid"));
				vo.setValue(rs.getString("value"));

				keyResourceList.add(vo);
			}
		} catch (SQLException e) {

			e.printStackTrace();
			throw new DAOException("ִ�в�ѯ������չ�ֶ�����ʧ��", e);
		} finally {
			DB.close(rs);
		}
		return keyResourceList;
	}
    //add by aiyan 2012-12-25 begin
    public void addReference(String id, String categoryId, String bookid,String sortId)
    throws Exception {
    	
    	if(!isExistContent(bookid)){//���Ķ����ݱ����û���ҵ�������ݾ����쳣��
    		throw new Exception("�Ķ���Ʒ�����ʱ������id��t_rb_book_new�����ڣ�bookid:"+bookid);
    	}
    	String sqlCode = "";
    	String[] paras = null;
    	if(isExistRef(id,bookid)){
    		sqlCode = "com.aspire.dotcard.baseread.dao.ReadReferenceDAO.addReference.UPDATE";
    		paras = new String[]{sortId,id,bookid};
    	}else{
//    		insert into t_rb_reference_new(cid, bookid, categoryid, sortNumber, rankValue,verify_status,verify_date) values (?, ?,?,?, 0,0,sysdate)
    		sqlCode = "com.aspire.dotcard.baseread.dao.ReadReferenceDAO.addReference.INSERT";
    		paras = new String[]{id,bookid,categoryId,sortId};
    	}
		try {
			DB.getInstance().executeBySQLCode(sqlCode, paras);
			this.approvalCategoryGoods(categoryId);
		} catch (DAOException e) {
			logger.error("���ָ����������������ʱ�����쳣:",e);
			throw new DAOException("���ָ����������������ʱ�����쳣:", e);
		}
	}
    
    private boolean isExistRef(String categoryId, String contentId) throws Exception{
		String sqlCode = "com.aspire.dotcard.baseread.dao.ReadReferenceDAO.isExistRef";
		String[] paras = new String[]{categoryId,contentId};
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
    }
    
    private boolean isExistContent(String contentId) throws Exception{
		String sqlCode = "com.aspire.dotcard.baseread.dao.ReadReferenceDAO.verifyContentId";
		String[] paras = new String[]{contentId};
		ResultSet rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
		return rs.next();
    }
    
   //add by aiyan 2012-12-25 end
    /**
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<ReadReferenceVO> queryNewBookReferenceListByExport(ReadReferenceVO vo)
			throws DAOException
	{

        if (logger.isDebugEnabled())
        {
            logger.debug("queryVideoReferenceListByExport( ) is starting ...");
        }
    	String sqlCode = "read.ReadReferenceDAO.queryReadRefList().SELECT";
		String sql = null;
		List<ReadReferenceVO> list = new ArrayList<ReadReferenceVO>();
		ResultSet rs = null;
		try {
			sql = SQLCode.getInstance().getSQLStatement(sqlCode);

			StringBuffer sqlBuffer = new StringBuffer(sql);
			List paras = new ArrayList();

			// ����������sql�Ͳ���
			if (!"".equals(vo.getCId())) {
				sqlBuffer.append(" and r.cId =? ");
				paras.add(vo.getCId());
			}
			if (!"".equals(vo.getBookId())) {
				sqlBuffer.append(" and b.bookid like ? ");
				paras.add("%" + SQLUtil.escape(vo.getBookId()) + "%");
			}
			if (!"".equals(vo.getBookName())) {
				sqlBuffer.append(" and b.bookname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getBookName()) + "%");
			}
			if (!"".equals(vo.getAuthorName())) {
				sqlBuffer.append(" and a.authorname like ? ");
				paras.add("%" + SQLUtil.escape(vo.getAuthorName()) + "%");
			}
			if (!"".equals(vo.getTypeName())) {
				sqlBuffer.append(" and t.typeName like ? ");
				paras.add("%" + SQLUtil.escape(vo.getTypeName()) + "%");
			}
			if (!"".equals(vo.getChargeType())) {
				sqlBuffer.append(" and b.chargeType = ?  ");
				paras.add(vo.getChargeType());
			}
			
			sqlBuffer.append("  and r.verify_status = 1 ");

			sqlBuffer.append(" order by r.sortnumber desc");


	     rs = DB.getInstance().query(sqlBuffer.toString(), paras.toArray());
			
			while (rs.next())
			{
				list.add(baseNewBookReferenceVoData(rs));
			}
		}
		catch (SQLException e)
		{
			logger.error("ִ�л�ȡ��ǰ���������в�ѯ��Ƶ���ݼ���Ϣʧ��", e);
			e.printStackTrace();
		}
		catch (DataAccessException e)
		{
			logger.error("ִ�л�ȡ��ǰ���������в�ѯ��Ƶ���ݼ���Ϣʧ��", e);
			e.printStackTrace();
		}
		finally
		{
			DB.close(rs);
		}
		
		return list;
    }
	private ReadReferenceVO baseNewBookReferenceVoData(ResultSet rs) throws SQLException
	{
		ReadReferenceVO vo = new ReadReferenceVO();

		vo.setCId(String.valueOf(rs.getInt("cId")));
		vo.setBookId(rs.getString("bookId"));
		vo.setBookName(rs.getString("bookName"));
		vo.setAuthorName(rs.getString("authorname"));
		vo.setSortNumber(String.valueOf(rs.getInt("sortNumber")));
		vo.setRankValue(String.valueOf(rs.getInt("rankValue")));
		vo.setTypeName(rs.getString("typename"));
		vo.setLastUpTime(rs.getString("lastuptime"));
		vo.setChargeType(rs.getString("chargeType"));
		vo.setFee(rs.getString("fee"));
		return vo;
    }
	
	/**
	 * ͼ����Ʒ�����ύ����
	 * 
	 * @param tdb
	 * @param categoryId ͼ����ܱ���
	 * @throws DAOException
	 */
	public void setCategoryGoodsApproval(TransactionDB tdb,String categoryId)
			throws DAOException {
		String sql = "com.aspire.dotcard.baseread.dao.ReadReferenceDAO.setCategoryGoodsApproval";
		try {
			tdb.executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("����ͼ����Ʒ���ܷ����쳣:", e);
            throw new DAOException("����ͼ����Ʒ���ܷ����쳣:", e);
		}
	}
	
	/**
	 * �༭ͼ����Ʒ����
	 * 
	 * @param categoryId ͼ����ܱ���
	 * @throws DAOException
	 */
	public void approvalCategoryGoods(String categoryId)
			throws BOException {
		String sql = "com.aspire.dotcard.baseread.dao.ReadReferenceDAO.approvalCategoryGoods";
		try {
			DB.getInstance().executeBySQLCode(sql, new Object[]{categoryId});
		} catch (DAOException e) {
			logger.error("�༭ͼ����Ʒ���ܷ����쳣:", e);
            throw new BOException("�༭ͼ����Ʒ���ܷ����쳣:", e);
		}
	}
	
	/**
	 * У��ļ��Д����Ƿ���ͼ����д���
	 * 
	 * @param list
	 * @throws DAOException
	 */
	public String verifyBookExists(List list) throws DAOException {

		if (logger.isDebugEnabled()) {
			logger.debug("verifyBook() is starting ...");
		}

		// select 1 from t_rb_book_new c where c.bookid = ?
		String sql = "read.ReadReferenceDAO.verifyBook().select";
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		// ������ԃ
		for (int i = 0; i < list.size(); i++) {
			Map m = (Map)list.get(i);
        	String temp = (String)m.get(1);
			try {
				rs = DB.getInstance().queryBySQLCode(sql.toString(),
						new Object[] { temp });
				// �����������������
				if (!rs.next()) {
					list.remove(i);
					i--;
					sb.append(temp).append(". ");
				}
			} catch (SQLException e) {
				throw new DAOException("�鿴ָ���������Ƿ����ָ������ʱ�����쳣:", e);
			} finally {
				DB.close(rs);
			}
		}

		return sb.toString();
	}
}
