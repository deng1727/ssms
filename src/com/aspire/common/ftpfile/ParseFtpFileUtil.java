/**
 * com.aspire.common.util parseFtpFile.java
 * Apr 29, 2011
 *<p>
 * Copyright (c) 2003-2011 ASPire TECHNOLOGIES (SHENZHEN) LTD All Rights
 * Reserved
 * </p>
 * @author dongke
 * @version 1.0
 *
 */
package com.aspire.common.ftpfile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;
import com.aspire.ponaadmin.web.util.PublicUtil;

/**
 * @author tungke
 *
 *解析获取到的FTP文件内容的工具类
 */
public class ParseFtpFileUtil
{

	
	private static JLogger logger = LoggerFactory.getLogger(ParseFtpFileUtil.class);

	
	/**
	 * 
	 *@desc 解析单个文件内容
	 *@author dongke
	 *Apr 29, 2011
	 * @param absFilename  本地文件绝对路径包含文件名
	 * @param split
	 * @param split
	 * @return  list[String[]]
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	public List parseFileContent(String absFilename, String split,String fileEncoding) 
	{
		String lineText = null;
		BufferedReader reader = null;
		// 用于文件成功处理的行数
		int lineNumeber = 0;
		List dr = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(absFilename), fileEncoding));
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			dr = new ArrayList();
			while ((lineText = reader.readLine()) != null)
			{
				lineNumeber++;// 记录文件的行数。
				if (lineNumeber == 1)// 删除第一行bom字符
				{
					lineText = PublicUtil.delStringWithBOM(lineText);
				}
				if ("".equals(lineText.trim()))// 对于空行的记录不处理。
				{
					logger.debug("该行是空行，不处理。lineNumeber=" + lineNumeber);
					continue;
				}

				dr.add(this.readDataRecord(lineText, split));
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dr;
	}
	
	
	/**
	 * 
	 *@desc 解析单行数据
	 *@author dongke
	 *Apr 29, 2011
	 * @param lineText
	 * @param sep
	 * @return
	 */
		 private String[] readDataRecord(String lineText,String sep){
		    	if (sep.startsWith("0x")) 
				{
					// 0x开头的，表示是16进制的，需要转换
					String s = sep.substring(2,sep.length());
					int i = Integer.parseInt(s,16);
					char c = (char)i;
					sep = String.valueOf(c);
				}
		    	String[] dataArray = lineText.split("["+sep+"]");
		    	//List record = new ArrayList();
		    	//record.add(dataArray);
//		    	for(int i =0 ;i < dataArray.length; i ++){
//		    		if(dataArray[i] != null && dataArray[i].length() > 0){
//		    			record.add(dataArray[i]);	
//		    		}
//		    	}
		    	return dataArray;
		    }
}
