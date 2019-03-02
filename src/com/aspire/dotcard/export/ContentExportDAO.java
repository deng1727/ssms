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
	 * 记录日志的实例对象
	 */
	protected static JLogger LOG = LoggerFactory.getLogger(ContentExportDAO.class);
	/**
	 * singleton模式的实例
	 */
	private static ContentExportDAO instance = new ContentExportDAO();

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static ContentExportDAO getInstance()
	{

		return instance;
	}
	
	public List getExportDate(TypeExportConfig config)throws DAOException
	{
		if(LOG.isDebugEnabled())
		{
			LOG.debug("开始获取类型为："+config.getType()+"的所有内容");
		}
		StringBuffer sql=new StringBuffer("select ");
		int fieldSize=config.getExportColumnNames().size();
		if(fieldSize==0)
		{
			throw new DAOException("需要至少一个输出字段");
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
			throw new DAOException("提取结果集到VO对象中发生异常:", e);
		} finally
		{
			DB.close(rs);
		}
		return resultList;
	}
	
	/**
     * 得到不为null的String对象
     * @param name
     * @return
     */
    private  String getNoNullString(String name)
    {
        return name == null ? "" : name;
    }
	
	/* *//**
     * 任务的任务指令方法
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
