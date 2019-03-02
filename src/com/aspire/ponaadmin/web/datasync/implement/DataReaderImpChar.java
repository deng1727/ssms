package com.aspire.ponaadmin.web.datasync.implement;

import java.util.ArrayList;
import java.util.List;

import com.aspire.ponaadmin.web.datasync.DataSyncConfig;

/**
 * ����ֻ������ʹ�õ����ַ����зָ��������֧��ʮ�����Ƶ���ʽ��ʾ�ַ���
 * ��Ҫ��Ϊ���ֲ���DataReaderImp���н������һ�������Ϊ�յķָ�����ȷ������������ַ� ��aa|�������ַ���|���ָ�󷵻س���Ϊ1
 * ��ΪString.split ������������һ���ַ�Ϊ�յ���
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
			// 0x��ͷ�ģ���ʾ��16���Ƶģ���Ҫת��
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
		//������ʼ��λ��
		int fieldStart=0;
		//����������ַ�����ÿһ���ַ�
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
		//��ȡ���һ����
		list.add(content.substring(fieldStart, content.length()));
		String fields[]=new String[list.size()];
		return (String[])list.toArray(fields);

	}
	public static void main(String arg[])
	{
		String str="1|����";
		DataReaderImpChar dd=new DataReaderImpChar();
		dd.sp='|';
		String aa[]=dd.splitToArray(str);
		System.out.println(aa.length+" "+aa[0]+"--"+aa[1]);
		
	}

}
