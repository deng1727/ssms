package com.aspire.ponaadmin.web.util;

import java.util.regex.Pattern;

/**
 * <p>Title: 字符串的公用工具类 </p>
 * <p>Description: 字符串的公用工具类，提供静态工具方法 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author shidr
 * @version 1.0
 */

public class StringTool
{

    /**
     * 构造方法
     */
	private StringTool()
    {
    }

    /**
     * 计算一个字符串的长度，汉字当成两个字符计算，ascii英文字符当成一个。
     * @param aStr 要计算长度的目标字符串
     * @return 计算出的长度
     */
    public static int lengthOfHZ(String aStr)
    {
        char c;
        int length = 0;
        for (int i = 0; i < aStr.length(); i++)
        {
            c = aStr.charAt(i);
            if (c >= 127)
            {
                length += 2;
            }
            else
            {
                length += 1;
            }
        }
        return length;
    }
    
    /**
     * 返回一个新的字符串，它是此字符串的一个子字符串。该子字符串始于指定索引处的字符，一直到此字符串末尾。 
     * 汉字当成两个字符计算，ascii英文字符当成一个。
     * @param aStr
     * @param len
     * @return
     */
    public static String substring(String aStr, int len)
    {
        StringBuffer resultStr = new StringBuffer();
        char c;
        int length = 0;
        for (int i = 0; i < aStr.length(); i++)
        {
            c = aStr.charAt(i);
            if(length==len){
                resultStr.append(c);
            }
            else if (c >= 127)
            {
                length += 2;
            }
            else
            {
                length += 1;
            }
            
        }
        return resultStr.toString();
    }
   

    /**
     * 把一个字符串的根据长度len来截取，汉字当成两个字符计算，ascii英文字符当成一个。
     * @param aStr 目标字符串
     * @param len 长度
     * @param endStr 如果超出长度，在截取后的字符串尾巴上添加的字符
     * @return 截取后的长度
     */
    public static String formatByLen(String aStr, int len , String endStr)
    {
        char c;
        int length = len;
        int aStrLen = aStr.length();
        StringBuffer resultStr = new StringBuffer();
        int i;
        for (i = 0; i < aStrLen; i++)
        {
            c = aStr.charAt(i);
            if (c >= 127)
            {
                length -= 2;
            }
            else
            {
                length -= 1;
            }
            if (length >= 0)
            {
                resultStr.append(c);
            }
            else
            {
                break;
            }
        }
        if(i < aStrLen)
        {
            resultStr.append(endStr);
        }
        return resultStr.toString();
    }

    /**
     * 字符串分解工具
     * @param inputString String 传入的待分解的字符串.
     * @param split String  传入的分解的标志.
     * @return String[] 返回分解完毕的string数组.
     * added by gaobb. 20060107.
     */
    public static String[] StringSplitTool(String inputString, String split)
    {
        Pattern SyncPattern = Pattern.compile(split) ;
        CharSequence ch = inputString ;
        String[] arrContent = SyncPattern.split(ch);
        return arrContent;
    }


    //测试方法。

    public static void main(String[] args)
    {
        String str = "a1‘“《》0";
        System.out.println(lengthOfHZ(str));
        System.out.println(formatByLen(str, 11, "…"));
    }
    /**
	 * 将{aa};{bb}分隔为一个字符串数组返回
	 * @param original
	 * @return
	 */
	public static String[] convertKeywords(String original){
		String later[]=null;
		if(original!=null && !original.equals("")){
			original=original.substring(1, original.length()-1);
			later=original.split("\\};\\{");
		}
		return later;
	}
	/**
	 * 用指定字符拼接新的字符串
	 * @param original 字符串数组
	 * @return 
	 */
	public static String packageStr(String[] original,char ch){
		StringBuffer sb=new StringBuffer();
		if(original!=null && original.length>0){
		sb=new StringBuffer();
		int length=original.length;
		for(int i=0;i<length;i++){
			sb.append(original[i]);
			if(i!=length-1){
				sb.append(ch);
			}
		}
		}
		return sb.toString();
	}
	/**
	 * @param original aaa;bbb
	 * @return {aaa};{bbb}
	 */
	public static String packageTags(String original){
		if(!original.equals("") && original!=null){
			String[] array=original.split(";");
			if(array.length>0){
				StringBuffer sb=new StringBuffer("{");
				for(int i=0;i<array.length;i++){
					sb.append(array[i]);
					if(i!=array.length-1){
						sb.append("};{");
					}
				}
				sb.append("}");
				return sb.toString();
			}else{
				return original;
			}
		}
		return original;
	}
	/**
	 * 
	 * @param {aaa};{bbb}
	 * @return aaa;bbb
	 */
	public static String unPack(String original){
		String[] ss=convertKeywords(original);
		return packageStr(ss,';');
	}
	/**
	 * 将abcded按指定长度截取，如abcd...
	 * @param length 需要留的字符数量
	 * @param original  初始字符串
	 * @param ch 用什么进行拼接
	 * @return
	 */
	public static String showKeywords(int length,String original,char ch){
		String[] strArray=convertKeywords(original);
		return subStr(length,packageStr(strArray,ch));
	}
	
	/**
	 * 将aaaaa按指定长度变成aaa...
	 * @param length
	 * @param original
	 * @return
	 */
	public static String subStr(int length,String original){
		if(original!=null && !original.equals("") && original.length()>length){
			return original.substring(0, length)+"...";
		}
		return original;
	}
}
