/**
 * <p>
 * ��Ҫ�������ݵĻ������ݲ�����
 * </p>
 * <p>
 * Copyright (c) 2009 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights Reserved
 * </p>
 * @Sep 16, 2009
 * @author dongke
 * @version 1.0.0.0
 */
package com.aspire.ponaadmin.web.category.export;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

/**
 * @author dongke
 *
 */
public class CategoryExportDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(CategoryExportDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static CategoryExportDAO instance = new CategoryExportDAO();

	/**
	 * ���췽������singletonģʽ����
	 */
	private CategoryExportDAO()
	{
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @return ʵ��
	 */
	public static CategoryExportDAO getInstance() throws DAOException
	{
		return instance;
	}

	/**
	 * ��ȡ���������б�
	 * @return
	 * @throws DAOException
	 */
	
	public List getCategoryExportID() throws DAOException
	{
		
		if(LOG.isDebugEnabled()){
			LOG.debug("�������ܵ�����ID");	
		}
		List catoryExports = new ArrayList();
		ResultSet rs = null;
		ResultSet rsSub = null;
		try
		{
			rs = DB.getInstance().queryBySQLCode("category.export.CategoryExportDAO.select().GETCATEGORYEXPORTID", null);
			while(rs.next())
			{
				String catoryExporyID = rs.getString("CID");
				//catoryExports.add(catoryExporyID);				
				Object[] parm1 = {catoryExporyID};
				try{
					rsSub =  DB.getInstance().queryBySQLCode("category.export.CategoryExportDAO.select().GETCATEGORYEXPORTIDPAR", parm1);
					while(rsSub.next()){
					
						String catoryExporyIDSub = rsSub.getString("id");
						catoryExports.add(catoryExporyIDSub);
					}
				} finally
				{
					DB.close(rsSub);
				}
			}
		} catch (SQLException e)
		{
			throw new DAOException("��ȡ������з����쳣:", e);
		} finally
		{
			DB.close(rs);
		}	
		return catoryExports;
	}
	

	public List getRefrenceByCategoryId(String categoryId) throws DAOException 
	{
		if(LOG.isDebugEnabled()){
			LOG.debug("����ָ�������µ�Ӧ����Ʒ");			
		}
		ResultSet rs = null;
		Object [] parm = { categoryId };
		List exportRefernceList = new ArrayList();
		try
		{
			rs = DB.getInstance().queryBySQLCode("category.export.CategoryExportDAO.select().GETREFRENCEBYCATEGORYID",parm);
		int i = 1;//ת������
			while(rs.next()){
			ExportReferenceVO vo = new ExportReferenceVO();
			vo.setContentId(rs.getString("id"));
			vo.setAppCode(rs.getString("contenttag"));
			vo.setAppName(rs.getString("name"));
			vo.setContentType(rs.getString("catename"));
			vo.setSpName(rs.getString("spname"));
			//vo.setSort(new Integer(rs.getInt("sortid")));
			vo.setSort(new Integer(i));
			vo.setDayDownloadTime(new Integer(rs.getInt("dayordertimes")));
			vo.setWeekDownloadTime(new Integer(rs.getInt("weekordertimes")));
			vo.setMonthDownloadTime(new Integer(rs.getInt("monthordertimes")));
			vo.setTotalDownloadTime(new Integer(rs.getInt("ordertimes")));
			vo.setCreateTime(rs.getString("CREATEDATE"));
			vo.setGrade(new Integer(rs.getInt("MARKTIMES")));
			String devName = rs.getString("DEVICENAME");
			if(devName == null ){//Ϊ�յĻ������ַ���
				devName = "";
			}
			StringBuffer deviceName = new StringBuffer (devName);	
			for(int j=2; j < 21;j ++){
				String ord = new Integer(j).toString();
				if(j<10){
					ord = "0"+ new Integer(j).toString();
				}
				if(rs.getString("DEVICENAME"+ord)!= null && !rs.getString("DEVICENAME"+ord).equals("")){
					deviceName.append(";");
					deviceName.append(rs.getString("DEVICENAME"+ord));				
				}	
			}
			vo.setDeviceName(deviceName.toString());
			exportRefernceList.add(vo);		
			i++;
		} 
		} catch (SQLException e)
		{
			throw new DAOException("��ȡָ�������µ�Ӧ����Ʒ�����쳣:", e);
		} finally
		{
			DB.close(rs);
		}			
		return exportRefernceList;
	}	
	
}
