package com.aspire.dotcard.gcontent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.datasync.implement.book.jidi.AuthorVO;
import com.aspire.ponaadmin.web.repository.Node;
import com.aspire.ponaadmin.web.repository.Repository;

/**
 * ���������ݲ������࣬�Ե�ǰ��ܵ�һ�����䣻<br>
 * �Ժ���������Ӹ���ķ���������������������¶����ܵ�Ҫ��
 * @author zhangwei
 *
 */
public class GContentDAO
{
	private static GContentDAO dao=new GContentDAO();
	private static JLogger logger=LoggerFactory.getLogger(GContentDAO.class);
	
	/**
	 * ����ģʽ
	 * @return
	 */
	public static GContentDAO getInstance()
	{
		return dao;
	}
	/**
	 * ��ȡ����idΪ cateId�ķ������������ݵ�id�б�
	 * @param cateId 
	 * @return List
	 */
	public List getContentIdByCateId(String cateId)throws DAOException
	{
		  if (logger.isDebugEnabled())
	        {
	            logger.debug("getContentIdByCate, cate:"+cateId);
	        }
	        String sqlCode = "com.aspire.dotcard.gcontent.GContentDAO.getContentIdByCate.SELECT";
	        List deviceNameList = new ArrayList();
	        ResultSet rs = null;
	        try
	        {

	        	Node node;
				try
				{
					node = Repository.getInstance().getNode(cateId);
				} catch (BOException e)
				{
					logger.error("��ȡ����ڵ����,cateId="+cateId,e);
					throw new DAOException("��ȡ����ڵ����",e);
				}
	        	
	            // �����б�Ϊ�գ�ִ��ȫ������
	            Object[] paras = {node.getPath()+"%"};
	            
	            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
	            while (rs.next())
	            {
	                deviceNameList.add(rs.getString(1));
	            }
	            return deviceNameList;
	        }
	        catch (SQLException ex)
	        {
	            throw new DAOException("getContentIdByCate error.", ex);
	        }finally{
	        	//add by tungke for close db con or rs
	        	DB.close(rs);
	        	
	        }
		
	}
	/**
	 * ���»����Ķ��У�������Ϣ
	 * @return
	 */
	public int updateContentAuhor(AuthorVO vo)throws DAOException
	{

        if (logger.isDebugEnabled())
        {
            logger.debug("updateContentAuhor");
        }
        String sqlCode = "com.aspire.dotcard.gcontent.GContentDAO.updateContentAuhor.update";
            // �����б�Ϊ�գ�ִ��ȫ������
        Object[] paras = {vo.getName(),vo.getDesc(),vo.getId()};
        return DB.getInstance().executeBySQLCode(sqlCode, paras);
     
        
	}

	 /**
         * ����contentid��ѯ����id
         * 
         * @return
         */
    public String getContentByID(String contentid) throws DAOException
    {

        if (logger.isDebugEnabled())
        {
            logger.debug("getContentByID, contentid:" + contentid);
        }
        String sqlCode = "com.aspire.dotcard.gcontent.GContentDAO.getContentByID.SELECT";
        ResultSet rs = null;
        String id = "";
        try
        {

            // �����б�Ϊ�գ�ִ��ȫ������
            Object[] paras = { contentid };

            rs = DB.getInstance().queryBySQLCode(sqlCode, paras);
            while (rs.next())
            {
                id = rs.getString("id");
            }

        }
        catch (SQLException ex)
        {
            throw new DAOException("getContentByID error.", ex);
        }
        finally
        {
            // add by tungke for close db con or rs
            DB.close(rs);

        }
        return id;
    }

}
