package com.aspire.ponaadmin.web.dissertation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.ponaadmin.common.page.PageResult;
import com.aspire.ponaadmin.common.page.PageVOInterface;
import com.aspire.ponaadmin.web.db.ResultSetConvertor;
import com.aspire.ponaadmin.web.db.TransactionDB;

public class DisserDao implements ResultSetConvertor,PageVOInterface {
	public int add(DisserVo vo) throws DAOException{
		String sqlCode="com.aspire.ponaadmin.web.dissertation.DisserDao.add";
		return DB.getInstance().executeBySQLCode(sqlCode, getAddParamFromVo(vo));
	}
	/**
	 * 
	 * @return �����������ƴ����ݿ��ȡ�Զ���������ID
	 * @throws DAOException
	 */
	public int getPK() throws DAOException{
		String seqName="SEQ_T_DISSERTATION_ID";
		return DB.getInstance().getSeqValue(seqName);
	}
	/**
	 * ���ݵ�ǰʱ��ı�ר��״̬
	 * @throws DAOException 
	 *
	 */
	public int updateStatus() throws DAOException{
		String[] sqlCodes=new String[]{
		"com.aspire.ponaadmin.web.dissertation.DisserDao.updateStatusGQ",
		"com.aspire.ponaadmin.web.dissertation.DisserDao.updateStatusYX",
		"com.aspire.ponaadmin.web.dissertation.DisserDao.updateStatusWSX"
		};
		DB.getInstance().executeMutiBySQLCode(sqlCodes, null);
		return 1;
		
	}
	public int del(String dissId) throws DAOException{
		String sqlCode="com.aspire.ponaadmin.web.dissertation.DisserDao.del";
		return DB.getInstance().executeBySQLCode(sqlCode, new Object[]{dissId});
	}
	/**
	 * ִ�и������⣬�������е���Ϣ
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public int update(DisserVo vo) throws DAOException{
		String sqlCode="com.aspire.ponaadmin.web.dissertation.DisserDao.update";
		return DB.getInstance().executeBySQLCode(sqlCode, getUpdateParams(vo));
	}
	/**
	 * ִ�и���ר�⣬û�й����Ż�����Ϣ
	 * @param vo
	 * @return
	 * @throws DAOException
	 */
	public int updateNoLogoUrl(DisserVo vo) throws DAOException{
		String sqlCode="com.aspire.ponaadmin.web.dissertation.DisserDao.updateNoLogoUrl";
		return DB.getInstance().executeBySQLCode(sqlCode, getUpdateNoLogoParams(vo));
	}
	public void queryAll(PageResult page) throws DAOException  {
			String sqlCode="com.aspire.ponaadmin.web.dissertation.DisserDao.queryAll";
			String sql=DB.getInstance().getSQLByCode(sqlCode);
			this.queryBySql(sql,page);
	}
	public void queryBySql(String sql,PageResult page) throws DAOException{
		page.excute(sql, null, this);
	}
	
	
	/**
	 * ִ�в�ѯר����Ϣ��������������Ķ���Ż���Ϣ
	 * @param dissId
	 * @return ר����Ϣ�б�
	 * @throws DAOException
	 */
	public List getDiss(String dissId) throws DAOException{
		String sql="com.aspire.ponaadmin.web.dissertation.DisserDao.getDiss";
		List list=TransactionDB.getInstance().queryBySQLCode(sql, new Object[]{dissId}, this);
		return list;
	}
	public Object[] getAddParamFromVo(DisserVo vo){
		Object[] obs=new Object[]{
				vo.getDissId(),
			vo.getLogoURL(),vo.getDescr(),
			vo.getKeywords(),vo.getStartDate(),
			vo.getEndDate(),vo.getCategoryId(),
			vo.getCategoryName(),vo.getDissName(),
			vo.getDissType(),vo.getRelation(),
			vo.getDissURL(),
		};
		return obs;
	}
	private Object[]  getUpdateNoLogoParams(DisserVo vo){
		Object[] obs=new Object[]{
				//vo.getLogoURL(),
				vo.getDescr(),
				vo.getKeywords(),
				vo.getStartDate(),
				vo.getEndDate(),
				vo.getCategoryId(),
				vo.getCategoryName(),
				vo.getDissName(),
				vo.getDissType(),
				vo.getDissURL(),
				vo.getDissId()
		};
		return obs;
	}
	private Object[]  getUpdateParams(DisserVo vo){
		Object[] obs=new Object[]{
				vo.getLogoURL(),
				vo.getDescr(),
				vo.getKeywords(),
				vo.getStartDate(),
				vo.getEndDate(),
				vo.getCategoryId(),
				vo.getCategoryName(),
				vo.getDissName(),
				vo.getDissType(),
				vo.getDissURL(),
				vo.getDissId()
		};
		return obs;
	}
	public Object convert(ResultSet rs) throws SQLException {
		DisserVo vo=new DisserVo();
		vo.setDissId(rs.getString("dissid"));
		vo.setLogoURL(rs.getString("logourl"));
		vo.setDissURL(rs.getString("dissurl"));
		vo.setDescr(rs.getString("descr"));
		vo.setKeywords(rs.getString("keywords"));
		vo.setStartDate(rs.getString("startdate"));
		vo.setEndDate(rs.getString("enddate"));
		vo.setCategoryId(rs.getString("categoryid"));
		vo.setCategoryName(rs.getString("categoryname"));
		vo.setStatus(new Integer(rs.getInt("status")));
		vo.setDissName(rs.getString("dissname"));
		vo.setDissType(rs.getString("disstype"));
		vo.setRelation(rs.getString("relation"));
		return vo;
	}
	public void CopyValFromResultSet(Object voo, ResultSet rs) throws SQLException {
		// TODO �Զ����ɷ������
		DisserVo vo=(DisserVo)voo;
		vo.setDissId(rs.getString("dissid"));
		vo.setLogoURL(rs.getString("logourl"));
		vo.setDissURL(rs.getString("dissurl"));
		vo.setDescr(rs.getString("descr"));
		vo.setKeywords(rs.getString("keywords"));
		vo.setStartDate(rs.getString("startdate"));
		vo.setEndDate(rs.getString("enddate"));
		vo.setCategoryId(rs.getString("categoryid"));
		vo.setCategoryName(rs.getString("categoryname"));
		vo.setStatus(new Integer(rs.getInt("status")));
		vo.setDissName(rs.getString("dissname"));
		vo.setDissType(rs.getString("disstype"));
		vo.setRelation(rs.getString("relation"));
	}
	public Object createObject() {
		// TODO �Զ����ɷ������
		return new DisserVo();
	}
}
