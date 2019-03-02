package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataExportVO
{
	/**
	 * 编码
	 */
	private String id;
	
	/**
	 * 导出任务名称
	 */
	private String exportName;

	/**
	 * 导出任务查询sql语句
	 */
	private String exportSql;
	
	/**
	 * 导出任务生成文件类型
	 */
	private String exportType;
	
	/**
	 * 导出任务生成文件附加信息
	 */
	private String exportTypeOther;
	
	/**
	 * 导出查询时的分页缓存数
	 */
	private String exportPageNum;
	
	/**
	 * 导出生成文件的每行列数
	 */
	private String exportLine;
	
	/**
	 * 最后执行时间
	 */
	private String lastTime;
	
	/**
	 * 导出生成文件名默认。后加时间
	 */
	private String fileName;
	
	/**
	 * 导出文件所在路径
	 */
	private String filePath;
	
	/**
	 * 文件全路径名
	 */
	private String fileAllName;
	
	/**
	 * 文件后缀名
	 */
	private String fileSuffix;
	
	/**
	 * 生成文件字符集
	 */
	private String encoder;
	
	/**
	 * 文件导出角色
	 */
	private String exportByUser;
	
	/**
	 * 文件导出方式
	 */
	private String exportByAuto;
	
	/**
	 * 最后一次执行耗时
	 */
	private String execTime;
	
	/**
	 * 当前任务所属分组
	 */
	private String groupId;
	
	/**
	 * 是否需要校验文件,默认0,不需要校验
	 */
	private String isVerf;
	

	public String getIsVerf() {
		return isVerf;
	}

	public void setIsVerf(String isVerf) {
		this.isVerf = isVerf;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getExportName()
	{
		return exportName;
	}

	public void setExportName(String exportName)
	{
		this.exportName = exportName;
	}

	public String getExportSql()
	{
		return exportSql;
	}

	public void setExportSql(String exportSql)
	{
		this.exportSql = exportSql;
	}

	public String getExportType()
	{
		return exportType;
	}

	public void setExportType(String exportType)
	{
		this.exportType = exportType;
	}

	public String getExportTypeOther()
	{
		return exportTypeOther;
	}

	public void setExportTypeOther(String exportTypeOther)
	{
		this.exportTypeOther = exportTypeOther;
	}

	public String getExportPageNum()
	{
		return exportPageNum;
	}

	public void setExportPageNum(String exportPageNum)
	{
		this.exportPageNum = exportPageNum;
	}

	public String getExportLine()
	{
		return exportLine;
	}

	public void setExportLine(String exportLine)
	{
		this.exportLine = exportLine;
	}

	public String getLastTime()
	{
		return lastTime;
	}

	public void setLastTime(String lastTime)
	{
		this.lastTime = lastTime;
	}
	
	public void setLastTime(Date lastTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
		this.lastTime = sdf.format(lastTime);
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getFileAllName()
	{
		if(fileAllName == null)
		{
			if(!filePath.endsWith(File.separator))
			{
				fileAllName = filePath + File.separator + FileRegexKey.getNameByFileName(fileName) + fileSuffix;
			}
			else
			{
				fileAllName = filePath + FileRegexKey.getNameByFileName(fileName) + fileSuffix;
			}
		}
		return fileAllName;
	}

	public String getToFTPFileName()
	{
		return FileRegexKey.getNameByFileName(fileName) + fileSuffix;
	}
	
	public String getFileSuffix()
	{
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix)
	{
		this.fileSuffix = fileSuffix;
	}

	public String getEncoder()
	{
		return encoder;
	}

	public void setEncoder(String encoder)
	{
		this.encoder = encoder;
	}

	public String getExportByUser()
	{
		return exportByUser;
	}

	public void setExportByUser(String exportByUser)
	{
		this.exportByUser = exportByUser;
	}

	public String getExportByAuto()
	{
		return exportByAuto;
	}

	public void setExportByAuto(String exportByAuto)
	{
		this.exportByAuto = exportByAuto;
	}

	public String getExecTime()
	{
		return execTime;
	}

	public void setExecTime(String execTime)
	{
		this.execTime = execTime;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
}
