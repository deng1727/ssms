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
 *������ȡ����FTP�ļ����ݵĹ�����
 */
public class ParseFtpFileUtil
{

	
	private static JLogger logger = LoggerFactory.getLogger(ParseFtpFileUtil.class);

	
	/**
	 * 
	 *@desc ���������ļ�����
	 *@author dongke
	 *Apr 29, 2011
	 * @param absFilename  �����ļ�����·�������ļ���
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
		// �����ļ��ɹ����������
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
				lineNumeber++;// ��¼�ļ���������
				if (lineNumeber == 1)// ɾ����һ��bom�ַ�
				{
					lineText = PublicUtil.delStringWithBOM(lineText);
				}
				if ("".equals(lineText.trim()))// ���ڿ��еļ�¼������
				{
					logger.debug("�����ǿ��У�������lineNumeber=" + lineNumeber);
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
	 *@desc ������������
	 *@author dongke
	 *Apr 29, 2011
	 * @param lineText
	 * @param sep
	 * @return
	 */
		 private String[] readDataRecord(String lineText,String sep){
		    	if (sep.startsWith("0x")) 
				{
					// 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
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
