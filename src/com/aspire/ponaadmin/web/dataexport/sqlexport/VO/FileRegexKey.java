package com.aspire.ponaadmin.web.dataexport.sqlexport.VO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FileRegexKey
{
	YYYYMMDD, YYYYMM, YYYYMMDDHH24, YYYYMMDDHH24mm, N;
	
	public static String getNameByFileName(String fileName)
	{
		Matcher m = Pattern.compile("%\\w+%").matcher(fileName);
		
		while (m.find())
		{
			String temp = m.group();
			String tempTo = temp.replaceAll("%", "");
			fileName = fileName.replaceAll(temp, getNameByFileRegex(tempTo));
		}
		
		return fileName;
	}
	
	public static String getNameByFileRegex(String reger)
	{
		if (FileRegexKey.YYYYMMDD.toString().equals(reger))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			return simpleDateFormat.format(new Date());
		}
		else if (FileRegexKey.YYYYMM.toString().equals(reger))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			return simpleDateFormat.format(new Date());
		}
		else if (FileRegexKey.YYYYMMDDHH24.toString().equals(reger))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
			return simpleDateFormat.format(new Date());
		}
		else if (FileRegexKey.YYYYMMDDHH24mm.toString().equals(reger))
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			return simpleDateFormat.format(new Date());
		}
		else if (reger.startsWith(FileRegexKey.N.toString()) & reger.endsWith(FileRegexKey.N.toString()))
		{
			StringBuffer temp = new StringBuffer();
			for(int i = 1; i < reger.length(); i++)
			{
				temp.append("0");
			}
			temp.append("1");
			return temp.toString();
		}
		else
		{
			return "";
		}
	}
}
