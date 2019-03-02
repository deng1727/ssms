/**
 * SSMS
 * com.aspire.ponaadmin.web.datasync.implement.music BMusicDAO.java
 * May 7, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.dotcard.basemusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.db.TransactionDB;

/**
 * @author tungke
 *
 */
public class BMusicDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(BMusicDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static BMusicDAO instance = new BMusicDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private BMusicDAO()
	{
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static BMusicDAO getInstance() throws DAOException
	{
		return instance;
	}

	/**
	 * ֧����������ݿ�����������Ϊ�ձ�ʾ�Ƿ��������͵Ĳ���
	 */
	private TransactionDB transactionDB;

	/**
	 * ��ȡ��������TransactionDB��ʵ�� ����Ѿ�ָ���ˣ����Ѿ�ָ���ġ����û��ָ�����Լ�����һ����ע���Լ�������ֱ���ò�֧���������͵ļ���
	 * 
	 * @return TransactionDB
	 */
	private TransactionDB getTransactionDB()
	{

		if (this.transactionDB != null)
		{
			return this.transactionDB;
		}
		return TransactionDB.getInstance();
	}

	/**
	 * �¼ܻ����ϵĻ���������Ʒ
	 * @param categoryID
	 */

	public void delBMusicRefByCateID(String categoryID)
	{
		String delSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.delBMusicRefByCateID().DELETE";
		Object paras[] = { categoryID };
		try
		{
			int result = this.execueSqlCode(delSqlCode, paras);
			LOG.debug("�¼ܻ��ܣ�" + categoryID + " �ϵ�ȫ��������Ʒ�ɹ���");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("�¼ܻ��ػ��ܣ�" + categoryID + " �ϵ�ȫ����Ʒʧ�ܣ�", e);
		}finally
		{
			
		
			
		}
	}

	/**
	 * �¼ܹ�����Ʒ
	 * @return
	 */
	public int delInvalBMusicRef(){
		
		String delSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.delInvalBMusicRef().DELETE";
		int result = 0;
		try
		{
			 result = this.execueSqlCode(delSqlCode, null);
			LOG.debug("�¼ܹ��ڵĻ���������Ʒ�ɹ���");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("�¼ܹ��ڵĻ���������Ʒ �ϵ�ȫ����Ʒʧ�ܣ�", e);
		}
			return result;
		
	}
	/**
	 * �¼ܹ�����Ʒ
	 * @return
	 */
	public int delInvalNewBMusicRef(){
		
		String delSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.delInvalNewBMusicRef().DELETE";
		int result = 0;
		try
		{
			 result = this.execueSqlCode(delSqlCode, null);
			LOG.debug("�¼ܹ��ڵĻ���������Ʒ�ɹ���");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("�¼ܹ��ڵĻ���������Ʒ �ϵ�ȫ����Ʒʧ�ܣ�", e);
		}
			return result;
		
	}
	/**
	 *  ���ݻ������ƺͻ��ܸ�id��ȡ����ID,û�������
	 * @param pcategoryID
	 * @param Cname
	 * @return
	 * @throws DAOException 
	 */
	public String getCategoryIDByNameAndParCid(String pcategoryID, String Cname,String listId,int sum) throws DAOException
	{

		String selectSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getCategoryIDByNameAndParCid().SELECT";
		Object paras[] = { Cname, pcategoryID };
		
		if(listId.equals("null")){
			listId = "";
		}
		
		String categoryid = null;
		ResultSet rs = null;
		ResultSet rstemp = null;
		try
		{
			 rs = DB.getInstance().queryBySQLCode(selectSqlCode, paras);
			while (rs.next())
			{
				categoryid = rs.getString("id");
			}
			if (categoryid == null)
			{//û�ж�Ӧ������������
				// insert into t_mb_category_new
				// (CATEGORYID,PARENTCATEGORYID,CATEGORYNAME,TYPE,DELFLAG,SORTID,SUM,CREATETIME,ALBUM_ID)
				// values(SEQ_BM_CATEGORY_ID.nextval,?,?,?,?,?,?,to_char(sysdate,'yyyy-mm-dd
				// hh24:mi:ss'))
				String  insertCateSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getCategoryIDByNameAndParCid().INSERT";
				Object para[] = {pcategoryID,Cname,new String("1"),new String("0"),new Integer(0),new Integer(sum),listId};
				int result = BMusicDAO.getInstance().execueSqlCode(insertCateSqlCode, para);
				//DB.getInstance().queryBySQLCode(sqlCode, null);
				 rstemp = DB.getInstance().queryBySQLCode(selectSqlCode, paras);
				while (rstemp.next())
				{
					categoryid = rstemp.getString("id");
				}
			}else{
				//���»����ϼ���Ʒ����
				this.updateCategorySum(sum,categoryid);
				
			}
			LOG.debug("��ȡ����ID��" + categoryid + " �ɹ���");
		} catch (DAOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("���ݻ��ܸ�ID��" + pcategoryID + "������"+Cname+" ��ȡ����IDʧ�ܣ�", e);
			throw new  DAOException("���ݿ����ʧ�ܣ�" + e);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("���ݻ��ܸ�ID��" + pcategoryID + "������"+Cname+" ��ȡ����IDʧ�ܣ����ݿ����ʧ��", e);
			throw new  DAOException("���ݿ����ʧ�ܣ�" + e);
		}finally
		{
			 DB.close(rs);
			 DB.close(rstemp);
			
		}
		return categoryid;

	}
	
	public void updateCategorySum(int sum,String  categoryID) throws DAOException{
//		���»����ϼ���Ʒ����
		String updateSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getCategoryIDByNameAndParCid().UPDATE";
		Object paras1[] = { new Integer(sum), categoryID };
		int result = BMusicDAO.getInstance().execueSqlCode(updateSqlCode, paras1);
		
	}
	
	public int updateAllCategoryRefSum() throws DAOException{
//		���»����ϼ���Ʒ����
		String updateAllSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.updateAllCategoryRefSum().UPDATE";
		//Object paras1[] = { new Integer(sum), categoryID };
		int result = BMusicDAO.getInstance().execueSqlCode(updateAllSqlCode, null);
		return result;
		
	}
	
	public int updateAllNewCategoryRefSum() throws DAOException{
//		���»����ϼ���Ʒ����
		String updateAllSqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.updateAllNewCategoryRefSum().UPDATE";
		//Object paras1[] = { new Integer(sum), categoryID };
		int result = BMusicDAO.getInstance().execueSqlCode(updateAllSqlCode, null);
		return result;
		
	}
	/**
	 * 
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistMusicID() throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistMusicID().SELECT";
		HashMap allExistMusicMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistMusicMap = new HashMap();
			while (rs.next())
			{
				allExistMusicMap.put(rs.getString("id"), new Integer(rs.getInt("delflag")));

			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistMusicMap;
	}

	/**
	 * �����ֻ���ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistNewMusicID() throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistNewMusicID().SELECT";
		HashMap allExistMusicMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistMusicMap = new HashMap();
			while (rs.next())
			{
				allExistMusicMap.put(rs.getString("id"), new Integer(rs.getInt("delflag")));

			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistMusicMap;
	}
	
	/**
	 * �����ֻ���ר��ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistAlbumMusicCateID(String parentId) throws DAOException
	{
		
		//select t.album_id,t.delflag from T_MB_CATEGORY_NEW  t where t.parentcategoryid=?
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistAlbumMusicCateID.SELECT";
		HashMap allExistAlbumMusicCateMap = null;
		ResultSet rs = null;
		try
		{
			Object paras[] = {parentId};
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistAlbumMusicCateMap = new HashMap();
			while (rs.next())
			{
				allExistAlbumMusicCateMap.put(rs.getString("album_id"), new Integer(rs.getInt("delflag")));

			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistAlbumMusicCateMap;
	}
	/**
	 * �����ֻ��ػ���ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistAlbumMusicCateID() throws DAOException
	{
		
		//select t.album_id,t.CATEGORYID from T_MB_CATEGORY_NEW  t where t.delflag=0 and t.album_id is not null
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistAlbumMusicCateIDAll.SELECT";
		HashMap allExistAlbumMusicCateMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistAlbumMusicCateMap = new HashMap();
			while (rs.next())
			{
				allExistAlbumMusicCateMap.put(rs.getString("album_id"), rs.getString("CATEGORYID"));

			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistAlbumMusicCateMap;
	}

	/**
	 * �����ֻ������ָ���ID
	 * @return
	 * @throws DAOException 
	 * @throws SQLException 
	 */
	public HashMap getAllexistNewMusicSingerID() throws DAOException
	{
		
		//select t.sid,t.singername from T_MB_SINGER_NEW t 
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.getAllexistNewMusicSingerID().SELECT";
		HashMap allExistMusicSingerMap = null;
		ResultSet rs = null;
		try
		{
			
			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			//ResultSet rs = this.querySqlCode(sqlCode, );
			allExistMusicSingerMap = new HashMap();
			while (rs.next())
			{
				allExistMusicSingerMap.put(rs.getString("SID"), rs.getString("singername"));

			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}finally
		{
			 DB.close(rs);
			
		}
		return allExistMusicSingerMap;
	}

	
	public boolean checkCategoryById(String categoryId) throws DAOException
	{
		String sqlCode = "com.aspire.dotcard.basemusic.BMusicDAO.checkCategoryById().SELECT";
		String paras[] = { categoryId };
		ResultSet rs = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
			//ResultSet rs = this.querySqlCode(sqlCode, paras);
			while (rs.next())
			{
				//�иû���
				return true;
			}
		} catch (SQLException e)
		{
			LOG.error("���ݿ����ʧ��");
			throw new DAOException("���ݿ����ʧ�ܣ�" + e);

		}finally
		{
			 DB.close(rs);
			
		}

		return false;
	}

	/**
	 * ��ȡ�������͵�DAOʵ��
	 * 
	 * @return AwardDAO
	 */

	public static BMusicDAO getTransactionInstance(TransactionDB transactionDB)
	{

		BMusicDAO dao = new BMusicDAO();
		dao.transactionDB = transactionDB;
		return dao;
	}

	/**
	 * ִ�����ݿ��ѯ
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
//	private ResultSet querySqlCode(String sql, Object[] parm) throws DAOException
//	{
//		ResultSet rs = null;
//		rs = DB.getInstance().queryBySQLCode(sql, parm);
//		return rs;
//	}

	/**
	 * ִ�����ݿ��ѯ
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public int insertIntoMBSqlCode(String sqlCode, Object[] parm) throws DAOException
	{
		int rs = 0;
		TransactionDB tdb = this.getTransactionDB();
		rs = tdb.executeBySQLCode(sqlCode, parm);

		//rs = DB.getInstance().executeBySQLCode(sql,parm);
		return rs;
	}

	/**
	 * ִ�����ݿ��ѯ
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	private ResultSet querySql(String sql, Object[] parm) throws DAOException
	{
		ResultSet rs = null;
		rs = DB.getInstance().query(sql, parm);
		return rs;
	}

	/**
	 * ִ�����ݿ��ѯ
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	private int execueSql(String sql, Object[] parm) throws DAOException
	{
		int rs = 0;
		rs = DB.getInstance().execute(sql, parm);
		return rs;
	}

	/**
	 * ִ�����ݿ��ѯ
	 * @param sql
	 * @param parm
	 * @return
	 * @throws DAOException
	 */
	public int execueSqlCode(String sql, Object[] parm) throws DAOException
	{
		int rs = 0;
		rs = DB.getInstance().executeBySQLCode(sql, parm);

		return rs;
	}
}
