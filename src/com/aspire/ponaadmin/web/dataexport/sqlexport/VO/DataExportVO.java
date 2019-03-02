package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataExportVO
{
	/**
	 * ����
	 */
	private String id;
	
	/**
	 * ������������
	 */
	private String exportName;

	/**
	 * ���������ѯsql���
	 */
	private String exportSql;
	
	/**
	 * �������������ļ�����
	 */
	private String exportType;
	
	/**
	 * �������������ļ�������Ϣ
	 */
	private String exportTypeOther;
	
	/**
	 * ������ѯʱ�ķ�ҳ������
	 */
	private String exportPageNum;
	
	/**
	 * ���������ļ���ÿ������
	 */
	private String exportLine;
	
	/**
	 * ���ִ��ʱ��
	 */
	private String lastTime;
	
	/**
	 * ���������ļ���Ĭ�ϡ����ʱ��
	 */
	private String fileName;
	
	/**
	 * �����ļ�����·��
	 */
	private String filePath;
	
	/**
	 * �ļ�ȫ·����
	 */
	private String fileAllName;
	
	/**
	 * �ļ���׺��
	 */
	private String fileSuffix;
	
	/**
	 * �����ļ��ַ���
	 */
	private String encoder;
	
	/**
	 * �ļ�������ɫ
	 */
	private String exportByUser;
	
	/**
	 * �ļ�������ʽ
	 */
	private String exportByAuto;
	
	/**
	 * ���һ��ִ�к�ʱ
	 */
	private String execTime;
	
	/**
	 * ��ǰ������������
	 */
	private String groupId;
	
	/**
	 * �Ƿ���ҪУ���ļ�,Ĭ��0,����ҪУ��
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
