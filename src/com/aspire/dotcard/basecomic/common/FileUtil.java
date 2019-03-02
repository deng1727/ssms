package com.aspire.dotcard.basecomic.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspire.common.exception.BOException;
import com.aspire.common.log.proxy.JLogger;
import com.aspire.common.log.proxy.LoggerFactory;

public class FileUtil {

	private static JLogger logger = LoggerFactory.getLogger(FileUtil.class);

	public static List getLine(String file) throws BOException {
		List list = new ArrayList();
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw new BOException("文件没有找到错误!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw new BOException("读校验文件时发生IO异常!");
		}
		return list;

	}

	//查找以.verf结尾的文件。
	public static String getVerfFile(String[] fileName) {
		for(int i=0;i<fileName.length;i++){
			if(fileName[i].endsWith(".verf")){
				return fileName[i];
			}
		}
		return null;
	}
	
    public static String getVerfMsg(String fileName){
    	StringBuffer sb = new StringBuffer();
    	List list;
		try {
			list = FileUtil.getLine(fileName);
		} catch (BOException e) {
			// TODO Auto-generated catch block
			logger.error("getVerfMsg出错！",e);
			return e.getMessage();
		}
    	for(int i=0;i<list.size();i++){
    		String line = (String)list.get(i);
    		String[] s = line.split(handleSep("\\|"));
    		if(s.length>=3){
    			sb.append("<br>文件名：").append(s[0]).append("，行数：").append(s[2]);
    		}else if(!("999999").equals(line)){
    			return "校验文件字段不够！";
    			
    		}
    	}
    	return sb.toString();
    
    }
    
    public static String handleSep(String sep){
    	
		if(sep!=null&&sep.startsWith("0x")){
			// 0x开头的，表示是16进制的，需要转换
			String s = sep.substring(2, sep.length());
			int i = Integer.parseInt(s, 16);
			char c = (char) i;
			sep = String.valueOf(c);
		}
		return sep;
    }

}
