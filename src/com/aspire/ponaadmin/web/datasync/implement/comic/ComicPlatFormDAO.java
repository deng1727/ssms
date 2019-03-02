/**
 * <p>
 * ����֧��ƽ̨���ݲ�����
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 7, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.datasync.implement.comic;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.DataSyncConstants;

/**
 * @author dongke
 *
 */
public class ComicPlatFormDAO {

	
	
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ComicPlatFormDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static ComicPlatFormDAO instance = new ComicPlatFormDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private ComicPlatFormDAO()
	{
	}

	/**
	 * ��ȡʵ��
	 *
	 * @return ʵ��
	 */
	public static ComicPlatFormDAO getInstance()
	{

		return instance;
	}
	
	/**
	 * ���ݶ���֧��ƽ̨ID��ѯ��ID�Ƿ��Ѵ���
	 * ����Ϊtrue,������Ϊfalse
	 * @param platFormID
	 * @return
	 * @throws DAOException 
	 */
	public boolean isComicPlatFormIDExist(String platFormID) throws DAOException{
		if(LOG.isDebugEnabled()){
			LOG.debug("��鶯��Ƶ��֧��ƽ̨�Ƿ����");	
		}
		ResultSet rs = null;
		boolean re = false ;
		Object[] paras = {platFormID };
		try {			
				rs = DB.getInstance().queryBySQLCode(
						"comic.platform.ComicPlatFormDAO.select().ISCOMICPLATFORMIDEXIST", paras);	
			// i = rs.getRow();
				if(rs != null && rs.next()){
					re = true;
					
				}
			} catch (DAOException e) {
				LOG.error(e);
				throw new DAOException("ִ�ж���Ƶ��֧��ƽ̨���ѯ�����쳣:", e);
			} catch (SQLException ex) {
			LOG.error(ex);
			throw new DAOException("ִ�ж���Ƶ��֧��ƽ̨���ѯ��ȡ���������쳣:", ex);
		}finally
		{
			DB.close(rs);
		}		
		return re;
	}
	
	/**
	 * �����������ƽ̨
	 * @param platFormID
	 * @return
	 */
	public  int addComicPlatForm(String platFormID){
		if(LOG.isDebugEnabled()){
			LOG.debug("��Ӷ���Ƶ��֧��ƽ̨");	
		}
		String platForm = this.getPlatFormByID(platFormID);
		Object[] paras = {platFormID,platForm };
		int num = 0 ;
		try {
			 num = DB.getInstance().executeBySQLCode(
					"comic.platform.ComicPlatFormDAO.insert().ADDCOMICPLATFORM",
					paras);
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error("��������֧��ƽ̨��platFormID="+platFormID,e);
			return DataSyncConstants.FAILURE_ADD;
		}
		if(num<1)
		{
			return DataSyncConstants.FAILURE_ADD;
		}else
		{
			return DataSyncConstants.SUCCESS_ADD;
		}
	}
	
	/**
	 * ɾ�����ж���Ƶ��֧��ƽ̨
	 * @return
	 */
	public int delAllComicPlatForm(){
		
		if (LOG.isDebugEnabled())
        {
			LOG.debug("delAllComicPlatForm");
        }
		String sqlCode="comic.platform.ComicPlatFormDAO.delete().ALL";
    	try
		{
			DB.getInstance().executeBySQLCode(sqlCode, null);
		} catch (DAOException e)
		{
			LOG.error(e);
			return DataSyncConstants.FAILURE_DEL;
		}		
	    return DataSyncConstants.SUCCESS_DEL;

	}
	
	
	/**
	 * ���ݶ���֧��ƽ̨ID��ȡ֧��ƽ̨����
	 * @param platFormID
	 * @return
	 */
	public String getPlatFormByID(String platFormID){
		if(platFormID != null && !platFormID.equals("")){
			if(platFormID.equals("100")){
				return "S602nd";				
			}else if(platFormID.equals("101")){
				return "S603rd";				
			}else if(platFormID.equals("102")){
				return "S605th";
			}else if(platFormID.equals("200")){
				return "WM";
			}else if(platFormID.equals("300")){
				return "Kjava";
			}else if(platFormID.equals("400")){	
				return "OMS";
			}else{
				return "";				
			}			
		}
		
		return null;
	}
	
	
}
