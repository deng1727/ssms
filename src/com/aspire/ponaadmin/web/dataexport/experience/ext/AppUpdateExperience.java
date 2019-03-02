/*
 * 文件名：AppUpdateExperience.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述： 榜单应用信息更新
 */

package com.aspire.ponaadmin.web.dataexport.experience.ext;

import java.io.File;
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
 * <p>
 * Title: 体验营销应用榜单应用信息更新
 * </p>
 * <p>
 * Description: 体验营销应用榜单 应用信息更新
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class AppUpdateExperience extends CommonExperience
{
	private static final JLogger LOG = LoggerFactory.getLogger(AppUpdateExperience.class);
	  public static String AppUpdateColumnSep = "|";
    public AppUpdateExperience()
    {
    	 this.setName("应用信息更新同步数据导出");
    	this.fileName = DataExportTools.parseFileName(ExperienceConfig.APPUpdateName);
    	 if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
         	this.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  this.fileName;
         }else{
         	this.exportedFileName = CommonExperience.FileWriteDIR +  this.fileName;
         }
        this.setSize(5); 
        AppUpdateColumnSep = ExperienceConfig.APPUpdateColumnSep;
    }

    protected List getDBData() throws BOException
    {

        List finalList = new ArrayList();
        try
        {
        	
            // 得到数据
            List allLists = this.getAllAppUpdateList();
            finalList = allLists;
            LOG.info("得到数据="+ allLists.size());
//            for (int i = 0; i < allLists.size(); i++)
//            {
//
//                Category vo = ( Category ) allLists.get(i);
//                // 写入数据
//                finalList.addAll(getDataByDeviceList(vo));
//            }
        }
        catch (DAOException e)
        {
        	LOG.error("获取应用信息更新同步数据失败!"+ e);
            throw new BOException("获取应用信息更新同步数据失败。", e);
        }
        return finalList;
    }
    /**
     * 
     */
    protected void writeToFile(List list) throws BOException
    {
        	LOG.debug("list.size="+list.size());
        	char sep = AppUpdateColumnSep.charAt(0);
				//DataExportTools.writeToTXTFile(this.exportedFileName,list,lineSeparator,encoding);
        		DataExportTools.writeToCSVFile(this.exportedFileName,list,sep);
			
            LOG.info("export file=" + this.exportedFileName);
            
            copyFileToFTP();
            LOG.info("写入FTP" + this.fileName);
        
        
    }
    /**
     * 写入数据
     * 
     * @param vo
     * @return
     * @throws BOException
     */
    protected List getDataByDeviceList(Category vo) throws BOException
    {

        List list = new ArrayList();
        List record = new ArrayList(this.getSize());
        
        record.add(vo.getId());
        record.add(new Integer(vo.getCtype()));
        record.add(vo.getPath());
        record.add(new Integer(vo.getSortID()));
        record.add(vo.getDesc());

        list.add(record);

        return list;
    }

    /**
     * 从榜单应用信息表中获取全部列表
     * 
     * @return
     * @throws DAOException
     */
    private List getAllAppUpdateList() throws DAOException
    {

		ResultSet rs = null;
		List list = new ArrayList();

		//        select g.contentid,
		//        g.averagemark,
		//        g.appcateid,
		//        g.scantimes,
		//        v.mobileprice / 10 price
		//   from t_r_gcontent g, v_service v
		//  where g.contentid = v.contentid
		//    and g.provider = 'O'
		//    and g.SERVATTR = 'G'
		String sqlCode = "dataexport.experience.AppUpdateExperience.getAppUpdateList";
		//try
	//	{

			rs = DB.getInstance().queryBySQLCode(sqlCode, null);
			try
			{
				while (rs.next())
				{
					String temp[] = new String[5];
					temp[0] = rs.getString("contentid");
					temp[1] = rs.getString("averagemark");
					temp[2] = rs.getString("appcateid");
					//Integer scantimes = new Integer(rs.getInt("scantimes"));
					//temp[3] = scantimes.toString();//
					temp[3] = rs.getString("scantimes");
					String type = rs.getString("type");
				    String pric = rs.getString("price");
					//Integer pr = new Integer(rs.getInt("price"));
					float pr = Float.valueOf(pric).intValue();
					if (pr > 100 && type.equals("nt:gcontent:appTheme"))
					{
						// 主题大于1元的应用
						temp[4] = "0";
					}
					else
					{ 
						temp[4] = pric;//pr.toString();
					}
					list.add(temp);
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		//	}
		//} catch (Exception e)
		//{
			LOG.error("获取应用信息更新同步数据失败!" + e);
			throw new DAOException("应用信息更新同步数据出错", e);
		} finally
		{
			DB.close(rs);
		}
		return list;
	}
}
