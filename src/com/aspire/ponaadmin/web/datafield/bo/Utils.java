package com.aspire.ponaadmin.web.datafield.bo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static boolean isMatcher(String str,String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static void main(String[] argv){
		System.out.println(isMatcher("300000021621","[\\d]{12}"));
		
		String x = "6";
		System.out.println(isMatcher(x,"[\\d]{1,2}")||isMatcher(x,"100"));
		
		x = "2010-09-12";
		System.out.println(isMatcher(x,"[\\d]{4}-[\\d]{2}-[\\d]{2}"));
		
		x = "23:59:59";
		System.out.println(isMatcher(x,"([0-1][0-9]|[2][0-3]):[0-5][0-9]:[0-5][0-9]"));
		
	}
	

}
