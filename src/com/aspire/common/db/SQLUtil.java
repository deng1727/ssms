package com.aspire.common.db;

public class SQLUtil {
	
//	������һЩƥ��ľ�������Ҫ˵�����ǣ�ֻ��like����������Щ�����ַ���=������û�еġ�
//	a_b...        a[_]b%
//	a%b...       a[%]b%
//	a[b...       a[[]b%
//	a]b...       a]b%
//	a[]b...      a[[]]b%
//	a[^]b...     a[[][^]]b%
//	a[^^]b...    a[[][^][^]]b%
//	��ʵ�ʽ��д����ʱ�򣬶���=����������һ��ֻ��Ҫ����滻��
//	' -> ''
//	����like��������Ҫ���������滻��ע��˳��Ҳ����Ҫ��
//	[ -> [[]     (��������ǵ�һ���滻��!!)
//	% -> [%]    (����%��ָϣ��ƥ����ַ����������%������ר������ƥ���ͨ���)
//	_ -> [_]
//	^ -> [^]
	public static String escape(String param){
		param = param.replaceAll("\\[", "[[]");
		param = param.replaceAll("%", "[%]");
		param = param.replaceAll("_", "[_]");
		param = param.replaceAll("\\^", "[^]");
		return param;
		
	}
	
	public static void main(String[] argv){
		String aa="5%_[^_4";
		System.out.println(escape(aa));
		
		
	}

}
