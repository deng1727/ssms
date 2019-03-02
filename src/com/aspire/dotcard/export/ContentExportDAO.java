package com.aspire.dotcard.export;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class ContentExportDAO
{
	/**
	 * ��¼��־��ʵ������
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ContentExportDAO.class);
	/**
	 * singletonģʽ��ʵ��
	 */
	private static ContentExportDAO instance = new ContentExportDAO();

	/**
	 * ��ȡʵ��
	 *
	 * @return ʵ��
	 */
	public static ContentExportDAO getInstance()
	{

		return instance;
	}
	
	public List getExportDate(TypeExportConfig config)throws DAOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("��ʼ��ȡ����Ϊ��"+config.getType()+"����������");
		}
		StringBuffer sql=new StringBuffer("select ");
		int fieldSize=config.getExportColumnNames().size();
		if(fieldSize==0)
		{
			throw new DAOException("��Ҫ����һ������ֶ�");
		}
		boolean isFirst=true;
		for(int i=0;i<fieldSize;i++)
		{	
			if(isFirst)
			{
				isFirst=false;
			}else
			{
				sql.append(",");
			}
			ExportItem vo= (ExportItem)config.getExportColumnNames().get(i);
			if(vo.getColumnName().trim().equalsIgnoreCase("id"))
			{
				sql.append("g.");
				sql.append(vo.getColumnName().trim());
			}else
			{
				sql.append(vo.getColumnName());
			}
			
		}
		sql.append(" from t_r_gcontent g , t_r_base b  where b.id=g.id and type=?");
		Object paras[]= {config.getType()};
		List resultList=new ArrayList();
		ResultSet rs=null;
		try
		{
			rs=DB.getInstance().query(sql.toString(), paras);
			List record=null;
			while (rs.next())
			{
				record=new ArrayList();
				for(int i=0;i<fieldSize;i++)
				{
					record.add(getNoNullString(rs.getString(i+1)));
				}
				resultList.add(record);
			}

		} catch (SQLException e)
		{
			throw new DAOException("��ȡ�������VO�����з����쳣:", e);
		} finally
		{
			DB.close(rs);
		}
		return resultList;
	}
	
	/**
     * �õ���Ϊnull��String����
     * @param name
     * @return
     */
    private  String getNoNullString(String name)
    {
        return name == null ? "" : name;
    }
	
	/* *//**
     * ���������ָ���
     * @throws Throwable
     *//*
    public void task (GContent content,String methodName) throws Throwable
    {
    	 BeanUtils.setProperty(refNode, fieldName, value) ;
    	 BeanUtils.getProperty(bean, name);
    	//NodeCFG nodeCFG = DBPersistencyCFG.getInstance().getNodeCFG("");
    	//String name =nodeCFG.getRowByField(fieldName);
    }*/

}
