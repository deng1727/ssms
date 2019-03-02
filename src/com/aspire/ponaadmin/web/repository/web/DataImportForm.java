package com.aspire.ponaadmin.web.repository.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DataImportForm extends ActionForm
{

	FormFile dataFile = null;
	FormFile phone = null;


  



	public FormFile getPhone() {
		return phone;
	}

	public void setPhone(FormFile phone) {
		this.phone = phone;
	}

	public FormFile getDataFile()
	{

		return dataFile;
	}

	public void setDataFile(FormFile dataFile)
	{

		this.dataFile = dataFile;
	}

	public boolean checkFileNameExtension(String[] type)
	{
		if (dataFile == null || "".equals(dataFile.getFileName()))
		{
			return true;
		}

		// 得到文件名
		String fileName = dataFile.getFileName();

		// 是否存在后缀名
		int pos = fileName.lastIndexOf(".");

		if (pos < 0)
		{
			return false;
		}

		// 当前文件后缀名
		String fileType = fileName.substring(pos + 1);

		// 循环判断后缀名情况
		for (int i = 0; i < type.length; i++)
		{
			String tempType = type[i];

			if (fileType.toUpperCase().equals(tempType.toUpperCase()))
			{
				return true;
			}
		}

		// 如果没有相同。错误。
		return false;
	}
	public boolean checkphoneFileNameExtension(String[] type)
	{
		if (dataFile == null || "".equals(dataFile.getFileName()))
		{
			return true;
		}
		
		// 得到文件名
		String fileName = dataFile.getFileName();
		
		// 是否存在后缀名
		int pos = fileName.lastIndexOf(".");
		
		if (pos < 0)
		{
			return false;
		}
		
		// 当前文件后缀名
		String fileType = fileName.substring(pos + 1);
		
		// 循环判断后缀名情况
		for (int i = 0; i < type.length; i++)
		{
			String tempType = type[i];
			
			if (fileType.toUpperCase().equals(tempType.toUpperCase()))
			{
				return true;
			}
		}
		
		// 如果没有相同。错误。
		return false;
	}

}
