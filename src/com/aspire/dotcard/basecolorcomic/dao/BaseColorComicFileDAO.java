/*
 * �ļ�����BaseVideoFileDAO.java
 * ��Ȩ��  ׿�����뼼��(���ڣ����޹�˾����
 * ������  
 */

package com.aspire.dotcard.basecolorcomic.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.dotcard.basecolorcomic.conf.BaseColorComicConfig;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * @author wangminlong
 * @version
 */
public class BaseColorComicFileDAO
{
	protected static JLogger logger = LoggerFactory
			.getLogger(BaseColorComicFileDAO.class);
	
	private static BaseColorComicFileDAO dao = new BaseColorComicFileDAO();
	
	private BaseColorComicFileDAO()
	{}
	
	public static BaseColorComicFileDAO getInstance()
	{
		return dao;
	}
	
	public Map<String, String> getKeyIDMap(String type)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ�ָ������ID�б�,��ʼ����ǰ����Ϊ:" + type);
		}
		Map<String, String> idMap = new HashMap<String, String>();
		ResultSet rs = null;
		String sql = null;
		
        // ���͵������
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CATEGORY))
        {
        	// select t.categoryid from t_cm_category t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getTypeMap";
        }
        // ���ݵ������
        if (type.equals(BaseColorComicConfig.FILE_TYPE_CONTENT))
        {
        	// select t.id from t_cm_content t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getContentMap";
        }
        // ��Ʒ�������
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_REFERENCE))
        {
        	// select t.id from t_cm_reference t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getRefMap";
        }
        // �Ƽ��������
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND))
        {
        	// select t.id from t_cm_recommend t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getRecommendMap";
        }
        // �Ƽ�������ϵ�������
        else if (type.equals(BaseColorComicConfig.FILE_TYPE_RECOMMEND_LINK))
        {
        	// select t.id from t_cm_recommend_link t
        	sql = "baseColorComic.exportfile.BaseColorComicFileDAO.getLinkMap";
        }
		
		try
		{
			rs = DB.getInstance().queryBySQLCode(sql, null);
			
			while (rs.next())
			{
				idMap.put(rs.getString(1), "");
			}
		}
		catch (SQLException e)
		{
			logger.error("���ݿ�SQLִ���쳣����ѯʧ��", e);
		}
		catch (DAOException ex)
		{
			logger.error("���ݿ�����쳣����ѯʧ��", ex);
		}
		finally
		{
			DB.close(rs);
		}
		
		if (logger.isDebugEnabled())
		{
			logger.debug("�õ�����ID�б�,����");
		}
		return idMap;
	}
	
    public int callUpdateCategoryNum()
	{
		CallableStatement cs = null;
		
		try
		{
			Connection conn = DB.getInstance().getConnection();
			
			cs = conn.prepareCall("{?=call f_update_cm_cate_rnum}");
			cs.registerOutParameter(1, Types.INTEGER);
			cs.execute();
			int intValue = cs.getInt(1); //��ȡ�������ؽ��
			return intValue;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
