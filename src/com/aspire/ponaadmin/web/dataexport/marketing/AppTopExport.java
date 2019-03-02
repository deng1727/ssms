package com.aspire.ponaadmin.web.dataexport.marketing;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.util.PublicUtil;
/**
 * 榜单应用导出
 * @author zhangwei
 *
 */
public class AppTopExport extends CommonAppExport 
{
	//private String 
	public AppTopExport()throws DAOException
	{
		this.setName("榜单应用信息数据导出");
		this.setFromSqlCode("dataexport.marketing.AppTopExport.getDBData");
		this.exportedFileName=CommonAppExport.FileWriteDIR+"APP_"+PublicUtil.getCurDateTime("yyyyMMdd")+".XLS";
        this.fileName="APP_"+PublicUtil.getCurDateTime("yyyyMMdd")+".XLS";
		this.setSize(16);
	}
	protected List getDBData()throws BOException
	{
		List finalList=new ArrayList();
		try
		{
			List allTopLists=this.getAllTopList();
			for(int i=0;i<allTopLists.size();i++)
			{
				
				ExportTopListVO vo=(ExportTopListVO)allTopLists.get(i);
				finalList.addAll(getDataByTopList(vo));
			}
		} catch (DAOException e)
		{
			throw new BOException("获取榜单数据失败。",e);
		}
		return finalList;
	}
	/**
	 * 根据类型获取榜单top count的数据
	 * @param para1
	 * @param count
	 * @return
	 * @throws BOException
	 */
	protected List getDataByTopList(ExportTopListVO vo)throws BOException
	{
		ResultSet rs=null;
		List list=new ArrayList();
		try
		{
			this.importParams= new Object[2];
			this.importParams[0]=vo.getCategoryid();
			this.importParams[1]=new Integer(vo.getCount());
			String sql=DB.getInstance().getSQLByCode(getFromSqlCode());
			if(vo.getCondition()!=null)
			{
				sql=DataExportTools.assembleSQL(sql, vo.getCondition());		
			}
			rs= DB.getInstance().query(sql, importParams);
			int index=0;
			while(rs.next())
			{
				List record=new ArrayList(this.getSize());
				record.add(String.valueOf(vo.getId()));
				record.add(vo.getName());
				record.add(String.valueOf(new Integer(++index)));
				int columnSize=this.getSize()-3;
				for(int i=1;i<=columnSize;i++)
				{
					if(i==9)
					{
						record.add(DataExportTools.getClobString(rs.getClob(i)));
					}else
					{
						record.add(rs.getString(i));
					}
					
				}
				list.add(record);	
			}
		} catch (Exception e)
		{
			throw new BOException("获取应用内容数据失败,榜单专区id="+vo.getId(),e);
		}finally
		{
			DB.close(rs);
		}
		
		return list;
	}
	/**
	 * 获取所有输出榜单
	 * @return
	 * @throws DAOException
	 */
	private List getAllTopList()throws DAOException
	{
		ResultSet rs=null;
		List list=new ArrayList();
		String sqlCode="dataexport.marketing.DeviceTopAppExport.getAllTopList";		
		try
		{
			rs= DB.getInstance().queryBySQLCode(sqlCode, null);
			
			while(rs.next())
			{
				ExportTopListVO vo=new ExportTopListVO();
				vo.setId(rs.getInt("id"));
				vo.setName(rs.getString("name"));
				vo.setCategoryid(rs.getString("categoryid"));
				vo.setCount(rs.getInt("count"));
				vo.setCondition(rs.getString("condition"));
				list.add(vo);
				
			}
		} catch (Exception e)
		{
            throw new DAOException("全量读取t_export_toplist出错",e);
		}finally
		{
			DB.close(rs);
		}
		return list;
	}

}
