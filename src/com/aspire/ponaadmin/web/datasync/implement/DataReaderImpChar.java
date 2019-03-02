package com.aspire.ponaadmin.web.datasync.implement;

import java.util.ArrayList;
import java.util.List;

import com.aspire.ponaadmin.web.datasync.DataSyncConfig;

/**
 * 该类只适用于使用单个字符进行分割解析器。支持十六进制的形式表示字符。
 * 主要是为了弥补，DataReaderImp类中解析最后一个域可以为空的分隔不正确的情况。比如字符 ‘aa|’，以字符‘|’分割后返回长度为1
 * 因为String.split 方法会忽略最后一个字符为空的域。
 * @author zhangwei
 *
 */
public class DataReaderImpChar extends DataReaderImp
{
	private char sp;
	private List list=new ArrayList();
	public void init(DataSyncConfig config)
	{
		String tmp = config.get("data-reader.file-separator");
		if (tmp.startsWith("0x")) 
		{
			// 0x开头的，表示是16进制的，需要转换
			String s = tmp.substring(2,tmp.length());
			int i = Integer.parseInt(s,16);
			sp = (char)i;
		} 
		else 
		{
			sp=tmp.charAt(0);
		}
		
	}
	protected String [] splitToArray(String content)
	{
		list.clear();
		//解析域开始的位置
		int fieldStart=0;
		//保存待解析字符串的每一个字符
		char a=0;
		for(int i=0;i<content.length();i++)
		{
			 a=content.charAt(i);
			if(a==sp)
			{
				list.add(content.substring(fieldStart, i));
				fieldStart=i+1;
			}
		}
		//获取最后一个域。
		list.add(content.substring(fieldStart, content.length()));
		String fields[]=new String[list.size()];
		return (String[])list.toArray(fields);

	}
	public static void main(String arg[])
	{
		String str="1|竞速";
		DataReaderImpChar dd=new DataReaderImpChar();
		dd.sp='|';
		String aa[]=dd.splitToArray(str);
		System.out.println(aa.length+" "+aa[0]+"--"+aa[1]);
		
	}

}
