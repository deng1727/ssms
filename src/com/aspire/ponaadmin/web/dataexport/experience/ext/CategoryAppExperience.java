/*
 * 文件名：CategoryAppExperience.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述： 榜单应用信息
 */

package com.aspire.ponaadmin.web.dataexport.experience.ext;

import java.io.File;
import java.sql.ResultSet;
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
import com.aspire.ponaadmin.web.repository.RepositoryConstants;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * <p>
 * Title: 体验营销 应用分类信息数据导出
 * </p>
 * <p>
 * Description: 体验营销 应用分类信息数据导出
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class CategoryAppExperience extends CommonExperience
{
	  private static final JLogger LOG = LoggerFactory.getLogger(CategoryAppExperience.class);
	  public static  String lineSeparator ="\n\r";
	  public static String categoryColumnSep = "|";
	  public static  String encoding ="UTF-8";
    public CategoryAppExperience()
    {

        this.setName("应用分类信息数据导出");
      
        this.fileName = DataExportTools.parseFileName(ExperienceConfig.categoryName);//"MMFenLei_" + PublicUtil.getCurDateTime("yyyyMMdd") + ".txt";
        if(!CommonExperience.FileWriteDIR.endsWith(File.separator)){
        	this.exportedFileName = CommonExperience.FileWriteDIR + File.separator +  this.fileName;
        }else{
        	this.exportedFileName = CommonExperience.FileWriteDIR +  this.fileName;
        }
        this.setSize(5);
        lineSeparator = ExperienceConfig.categoryLineSep;
        categoryColumnSep = ExperienceConfig.categoryColumnSep;
        encoding = ExperienceConfig.ExperEncoding;
        
    }

    protected List getDBData() throws BOException
    {

        List finalList = new ArrayList();
        try
        {
            // 得到数据
            List allLists = this.getAllCategoryAppList();
            finalList = allLists;
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
            throw new BOException("应用分类信息数据导出失败。", e);
        }
        return finalList;
    }
    protected void writeToFile(List list) throws BOException
    {

        try
        {
            //DataExportTools.ExportDate(list, this.exportedFileName);
        	DataExportTools.writeToTXTFile(this.exportedFileName,list,lineSeparator,encoding);
            LOG.info("export file=" + this.exportedFileName);
            
            copyFileToFTP();
            LOG.info("写入FTP" + this.fileName);
        }
        catch (Exception e)
        {
            throw new BOException("写入文件出错，filename=" + this.exportedFileName, e);
        }
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
        
        record.add(vo.getDesc());
        record.add(new Integer(vo.getSortID()));
        record.add(vo.getId());
        record.add(vo.getName());
        
        String type = vo.getType();
        if (type.equals(RepositoryConstants.TYPE_APPSOFTWARE))
        {
            record.add(new Integer(0));
        }
        else if (type.equals(RepositoryConstants.TYPE_APPGAME))
        {
            record.add(new Integer(1));
        }
        else if (type.equals(RepositoryConstants.TYPE_APPTHEME))
        {
            record.add(new Integer(2));
        }
        list.add(record);

        return list;
    }

    /**
     * 应用分类信息数据导出
     * 
     * @return
     * @throws DAOException
     */
    private List getAllCategoryAppList() throws DAOException
    {

        ResultSet rs = null;
        List list = new ArrayList();

//        select decode(t.type, 1001, 0, 1002, 1, 1003, 2) type,
//        t.id,
//        t.name,
//        '1' num
//   from om_dictionary t
 
        String sqlCode = "dataexport.experience.CategoryAppExperience.getAllCategoryAppList";
        try
        {
            rs = DB.getInstance().queryBySQLCode(sqlCode, null);

            while (rs.next())
            {
            	//List ca = new ArrayList();
            	StringBuffer ca = new StringBuffer("");
            	ca.append(rs.getString("type"));
            	ca.append(categoryColumnSep);
            	ca.append(rs.getString("id"));
            	ca.append(categoryColumnSep);
            	ca.append(rs.getString("name"));
            	ca.append(categoryColumnSep);
            	ca.append(rs.getString("num"));
                list.add(ca.toString());
            }
        }
        catch (Exception e)
        {
        	LOG.error("应用分类信息数据导出出错"+e);
            throw new DAOException("应用分类信息数据导出出错", e);
        }
        finally
        {
            DB.close(rs);
        }
        return list;
    }
}
