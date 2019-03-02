/*
 * 文件名：AppUpdateExperience.java
 * 版权：  卓望数码技术(深圳）有限公司所有
 * 描述： 榜单应用信息更新
 */

package com.aspire.ponaadmin.web.dataexport.experience.ext;

import java.io.File;
import java.sql.Clob;
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
import com.aspire.ponaadmin.web.repository.Category;

/**
 * <p>
 * Title: 体验营销增量/全量应用信息同步
 * </p>
 * <p>
 * Description: 体验营销增量/全量应用信息同步
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author x_wangml
 * @version
 */
public class AllAppExperience extends CommonExperience
{
	private static final JLogger LOG = LoggerFactory
			.getLogger(AllAppExperience.class);

	public static String encoding = "UTF-8";
	public static String APPNewColumnSep = "|";
	public static boolean isChangeEnter = true;
	
	/**
     * 回车换行符如何定义
     */
	private String enterString;
	
	private String enterString_0a;
	
	/**
     * 如果要变更，变更为何字符
     */
	private String changeEnterToStr = " ";

	public AllAppExperience()
	{
		this.setName("应用信息更新数据导出");
		this.constraint = "0";// 默认为0，增量
		this.fileName = DataExportTools
				.parseFileName(ExperienceConfig.APPNewName);
		if (!CommonExperience.FileWriteDIR.endsWith(File.separator))
		{
			this.exportedFileName = CommonExperience.FileWriteDIR
					+ File.separator + this.fileName;
		}
		else
		{
			this.exportedFileName = CommonExperience.FileWriteDIR
					+ this.fileName;
		}
		APPNewColumnSep = ExperienceConfig.APPNewColumnSep;
		this.setSize(5);
		encoding = ExperienceConfig.ExperEncoding;
		enterString = this.getEnterString();
		enterString_0a = this.getEnterString_0a();
	}

	protected List getDBData() throws BOException
	{

		List finalList = new ArrayList();
		try
		{
			// 得到数据
			// 1 全量；0，增量
			List allLists = this.getAllAppUpdateList(constraint);
			finalList = allLists;

		}
		catch (DAOException e)
		{
			LOG.error("增量/全量应用信息同步数据出错" + e);
			throw new BOException("获取增量/全量应用信息同步数据失败。", e);
		}
		return finalList;
	}

	/**
	 * 
	 */
	protected void writeToFile(List list) throws BOException
	{
		LOG.debug("list.size=" + list.size());
		char sep = APPNewColumnSep.charAt(0);
		// DataExportTools.writeToTXTFile(this.exportedFileName,list,lineSeparator,encoding);
		DataExportTools.writeToCSVFile(this.exportedFileName, list, sep);

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
	private List getAllAppUpdateList(String fullType) throws DAOException
	{

		ResultSet rs = null;
		List list = new ArrayList();

		// select g.contentid, g.name, g.icpcode, g.spname, g.lupddate,
		// decode(g.averagemark, null, 3, g.averagemark) averagemark,
		// decode(b.type, 'nt:gcontent:appGame', '1', 'nt:gcontent:appSoftWare',
		// '0', 'nt:gcontent:appTheme', '2') type, g.appcateid,
		// decode(g.icpcode, '100246', '0', g.icpcode, '2') icpcode, g.keywords,
		// g.introduction, g.scantimes, g.fulldeviceid, v.mobileprice / 10
		// price, g.picture1, g.picture2, g.picture3, g.picture4, g.picture5,
		// g.picture6, g.PLATFORM, '1' syntype from t_r_base b, t_r_gcontent g,
		// v_service v where g.contentid = v.contentid and g.provider = 'O' and
		// g.SERVATTR = 'G' and g.subtype <> '6' and g.subtype <> '11' and
		// g.subtype <> '12' and (v.paytype = '0' or v.paytype is null) and g.id
		// = b.id
		String fullSqlCode = "dataexport.experience.AllAppExperience.getAllAppList";
		
		// select g.contentid, g.name, g.icpcode, g.spname, g.lupddate,
		// decode(g.averagemark, null, 3, g.averagemark) averagemark,
		// decode(b.type, 'nt:gcontent:appGame', '1', 'nt:gcontent:appSoftWare',
		// '0', 'nt:gcontent:appTheme', '2') type, g.appcateid,
		// decode(g.icpcode, '100246', '0', g.icpcode, '2') icpcode, g.keywords,
		// g.introduction, g.scantimes, g.fulldeviceid, v.mobileprice / 10
		// price, g.picture1, g.picture2, g.picture3, g.picture4, g.picture5,
		// g.picture6, g.PLATFORM, decode(y.syntype, '1', '1', '2', '1', '3',
		// '0') syntype from t_r_base b, t_r_gcontent g, v_service v, (select *
		// from (select t.*, row_number() over(partition by t.contentid order by
		// t.syntime desc) rn from t_syn_result t) where rn = 1) y where
		// y.contentid = g.contentid and g.provider = 'O' and g.subtype <> '6'
		// and g.subtype <> '11' and g.subtype <> '12' and g.SERVATTR = 'G' and
		// y.syntime > to_char(sysdate - 1, 'yyyy-mm-dd') || ' 03:20:00' and
		// g.contentid = v.contentid and (v.paytype = '0' or v.paytype is null)
		// and g.id = b.id union all select y.contentid, y.contentname, ' '
		// icpcode, ' ' spname, ' ' lupddate, 0 averagemark, '0' type, ' '
		// appcateid, '0' icpcode, ' ' keywords, ' ' introduction, 0 scantimes,
		// empty_clob() fulldeviceid, 0 price, ' ' picture1, ' ' picture2, ' '
		// picture3, ' ' picture4, ' ' picture5, ' ' picture6, ' ' PLATFORM, '0'
		// syntype from t_syn_result y where y.syntime > to_char(sysdate - 1,
		// 'yyyy-mm-dd') || ' 03:20:00' and y.syntype = '3'
		String addSqlCode = "dataexport.experience.AllAppExperience.getAllAppUpdateList";
		try
		{
			LOG.debug("增量/全量应用信息同步数据开始。");
			if (fullType != null && fullType.equals("0"))
			{
				// 增量
				rs = DB.getInstance().queryBySQLCode(addSqlCode, null);

			}
			else if (fullType != null && fullType.equals("1"))
			{
				// 全量
				rs = DB.getInstance().queryBySQLCode(fullSqlCode, null);
			}

			while (rs.next())
			{
				String tempstr[] = new String[22];
				tempstr[0] = rs.getString("contentid");
				tempstr[1] = rs.getString("name");
				tempstr[2] = rs.getString("icp");
				tempstr[3] = rs.getString("spname");
				tempstr[4] = rs.getString("lupddate");
				tempstr[5] = rs.getString("averagemark");
				tempstr[6] = rs.getString("type");
				tempstr[7] = rs.getString("appcateid");
				tempstr[8] = rs.getString("icpcode");
				String keyworld = this.checkFileldEnter(rs.getString("keywords"));
				tempstr[9] = replace(keyworld, ",", ";");
				tempstr[10] = this.checkFileldEnter(rs.getString("introduction"));
				tempstr[11] = rs.getString("scantimes");
				
				//String devicename = getClobString(rs.getClob("fulldeviceid"));
				String devicename = "";
				
				//DB.getClobValue(rs, "fulldeviceid");
				
				try
				{
					devicename = getClobString(rs.getClob("fulldeviceid"));
				}
				catch (Exception e)
				{
					LOG.error("这个clod转换又错了。换成空了。。。" + e);
				}
				
				tempstr[12] = replace(devicename, ",", ";");

				String pric = rs.getString("price");
				if (pric.indexOf(".") > 0)
				{
					pric = pric.substring(0, pric.indexOf("."));
				}

				// Integer pr = new Integer(rs.getInt("price"));
				float pr = Float.valueOf(pric).intValue();
				
				// Integer pr = new Integer(rs.getInt("price"));
				if (pr > 100 && tempstr[3].equals("2"))
				{
					// 主题大于1元的应用
					tempstr[13] = "0";
				}
				else
				{
					tempstr[13] = pric;// pr.toString();
				}

				tempstr[14] = rs.getString("wwwpropapicture2");
				tempstr[15] = rs.getString("wwwpropapicture3");
				tempstr[16] = rs.getString("picture1");
				tempstr[17] = rs.getString("picture2");
				tempstr[18] = rs.getString("picture3");
				tempstr[19] = rs.getString("picture4");
				tempstr[20] = rs.getString("PLATFORM");// 新增支持平台
				tempstr[21] = rs.getString("syntype");

				list.add(tempstr);
			}
		}
		catch (Exception e)
		{
			LOG.debug(e);
			LOG.error("增量/全量应用信息同步数据出错" , e);
			throw new DAOException("增量/全量读取应用信息更新数据出错", e);
		}
		finally
		{
			DB.close(rs);
		}
		LOG.debug("增量/全量应用信息同步数据开始。");
		return list;
	}

	private String replace(String str1, String old, String newstr)
	{
		if (null == str1)
		{
			return null;
		}
		else
		{
			return str1.replaceAll(old, newstr);
		}

	}
    /**
     * 判断该字符是回车换行
     * @param temp 传进来的值
     * @return
     */
	private String checkFileldEnter(String temp)
    {
        if(temp == null)
        {
            return "";
        }
        
        if (isChangeEnter)
        {
            temp = String.valueOf(temp).replaceAll(enterString,
                                                   changeEnterToStr);
        }
        
        if(isChangeEnter)
        {
        	temp = String.valueOf(temp).replaceAll(enterString_0a,
                    changeEnterToStr);
        }
        return temp;
    }
	
    /**
     * 用于返回0d0a换行回车符
     * @return
     */
    private String getEnterString()
    {
        int a = 13;
        int b = 10;
        char x = (char)a;
        char y = (char)b;
        
        return String.valueOf(x)+String.valueOf(y);
    }
    
    /**
     * 用于返回0a换行回车符
     * @return
     */
    private String getEnterString_0a()
    {
        int b = 10;
        char y = (char)b;
        
        return String.valueOf(y);
    }
	
	private String getClobString(Clob clob) throws SQLException
	{

		if (clob == null)
		{
			return "";
		}
		long len = clob.length();
		return clob.getSubString(0, (int) len);
	}
}
