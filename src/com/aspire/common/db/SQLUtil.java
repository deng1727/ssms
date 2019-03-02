package com.aspire.common.db;

public class SQLUtil {
	
//	以下是一些匹配的举例，需要说明的是，只有like操作才有这些特殊字符，=操作是没有的。
//	a_b...        a[_]b%
//	a%b...       a[%]b%
//	a[b...       a[[]b%
//	a]b...       a]b%
//	a[]b...      a[[]]b%
//	a[^]b...     a[[][^]]b%
//	a[^^]b...    a[[][^][^]]b%
//	在实际进行处理的时候，对于=操作，我们一般只需要如此替换：
//	' -> ''
//	对于like操作，需要进行以下替换（注意顺序也很重要）
//	[ -> [[]     (这个必须是第一个替换的!!)
//	% -> [%]    (这里%是指希望匹配的字符本身包括的%而不是专门用于匹配的通配符)
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
