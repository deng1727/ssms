/**
 * SSMS
 * com.aspire.ponaadmin.web.dataexport.experience.ext AppExperience.java
 * Jul 8, 2010
 * @author tungke
 * @version 1.0
 *
 */
package com.aspire.ponaadmin.web.dataexport.experience.ext;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.db.DAOException;
import com.aspire.common.db.DB;
import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.dataexport.DataExportTools;
import com.aspire.ponaadmin.web.dataexport.experience.CommonExperience;
import com.aspire.ponaadmin.web.dataexport.experience.ExperienceConfig;
import com.aspire.ponaadmin.web.dataexport.marketing.CommonAppExport;
import com.aspire.ponaadmin.web.repository.Category;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *榜单应用信息
 */
public class AppExperience extends CommonExperience
{

	
	private static final JLogger LOG = LoggerFactory.getLogger(AppExperience.class);
	  public static  String lineSeparator ="\n\r";
	  public static String APPColumnSep = "|";
	  public static  String encoding ="UTF-8";
	  
	 public AppExperience()
	    {

	        this.setName("榜单应用信息数据导出");
	        
	        this.fileName = DataExportTools.parseFileName(ExperienceConfig.APPName);
	        if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
	        	this.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  this.fileName;
	        }else{
	        	this.exportedFileName = CommonExperience.FileWriteDIR +  this.fileName;
	        }
	        this.setSize(5);
	        lineSeparator = ExperienceConfig.APPLineSep;
	        APPColumnSep = ExperienceConfig.APPColumnSep;
	        encoding = ExperienceConfig.ExperEncoding;
	    }

	    protected List getDBData() throws BOException
	    {

	        List finalList = new ArrayList();
	        try
	        {
	            // 得到数据
	            List allLists = this.getAppList();
	            finalList = allLists;
	        }
	        catch (DAOException e)
	        {
	        	LOG.error("获取榜单数据失败"+e);
	            throw new BOException("获取榜单数据失败。", e);
	        }
	        return finalList;
	    }
	    protected void writeToFile(List list) throws BOException
	    {

	       
	        	LOG.debug("list.size="+list.size());
	            //DataExportTools.ExportDate(list, this.exportedFileName);
	        	try
				{

					DataExportTools.writeToTXTFile(this.exportedFileName,list,lineSeparator,encoding);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					 LOG.error("写入TXT出错：" + this.fileName+"，"+e);
					e.printStackTrace();
				}
	            LOG.info("export file=" + this.exportedFileName);
	            
	            copyFileToFTP();
	            LOG.info("写入FTP" + this.fileName);
	        
	        
	    }
	  
	    /**
	     * 从榜单应用信息表中获取全部列表
	     * 
	     * @return
	     * @throws DAOException
	     */
	    private List getAppList() throws DAOException
	    {

		ResultSet rs = null;
		List list = new ArrayList();
		ResultSet rs1 = null;

		String sqlCodeCate = "dataexport.experience.AppExperience.getAppCateList";
		rs1 = DB.getInstance().queryBySQLCode(sqlCodeCate, null);
		try
		{
			while (rs1.next())
			{
				String cname = rs1.getString("cname");
				
				String cid = rs1.getString("cid");
				Integer count = new Integer(rs1.getInt("count"));

				//	        select c.cname ,
				//	        t.sortid,
				//	        g.contentid,
				//	        g.name,
				//	       decode(b.type,'nt:gcontent:appGame','1','nt:gcontent:appSoftWare','0','nt:gcontent:appTheme','2') type
				//	       from t_r_reference t, t_r_base b, t_r_gcontent g,t_category_train c
				//	      where 
				//	      t.categoryid = c.cid
				//	       and  t.refnodeid = g.id
				//	        and g.id = b.id
				String sqlCode = "dataexport.experience.AppExperience.getAppList";
				try
				{
					Object parms [] = {cid,count};
					rs = DB.getInstance().queryBySQLCode(sqlCode, parms);
					
					
					while (rs.next())
					{
						StringBuffer ca = new StringBuffer("");
						ca.append(cname);
						ca.append(APPColumnSep);
						Integer sidtemp = new Integer(rs.getInt("sortid"));
						ca.append(sidtemp.toString());
						ca.append(APPColumnSep);
						ca.append(rs.getString("contentid"));
						ca.append(APPColumnSep);
						ca.append(rs.getString("name"));
						ca.append(APPColumnSep);
						ca.append(rs.getString("type"));
						list.add(ca.toString());
						//	                Category vo = new Category();
						//	                vo.setId(rs.getString("id"));
						//	                vo.setCtype(rs.getInt("averagemark"));
						//	                vo.setPath(rs.getString("appcateid"));
						//	                vo.setSortID(rs.getInt("scantimes"));
						//	                vo.setDesc(rs.getString("mobileprice"));
						//	                list.add(vo);
					}
				} catch (Exception e)
				{
					LOG.error("全量读取榜单应用信息数据出错" + e);
					throw new DAOException("全量读取榜单应用信息数据出错", e);
				} finally
				{
					DB.close(rs);
				}

			}
		} catch (SQLException e1)
		{
			e1.printStackTrace();
			LOG.error("读取榜单数据出错" + e1);
		}finally
		{
			DB.close(rs1);
		}
		return list;
	}
}
