package com.aspire.ponaadmin.web.util;

import java.util.regex.Pattern;

/**
 * <p>Title: �ַ����Ĺ��ù����� </p>
 * <p>Description: �ַ����Ĺ��ù����࣬�ṩ��̬���߷��� </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author shidr
 * @version 1.0
 */

public class StringTool
{

    /**
     * ���췽��
     */
	private StringTool()
    {
    }

    /**
     * ����һ���ַ����ĳ��ȣ����ֵ��������ַ����㣬asciiӢ���ַ�����һ����
     * @param aStr Ҫ���㳤�ȵ�Ŀ���ַ���
     * @return ������ĳ���
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
     * ����һ���µ��ַ��������Ǵ��ַ�����һ�����ַ����������ַ���ʼ��ָ�����������ַ���һֱ�����ַ���ĩβ�� 
     * ���ֵ��������ַ����㣬asciiӢ���ַ�����һ����
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
     * ��һ���ַ����ĸ��ݳ���len����ȡ�����ֵ��������ַ����㣬asciiӢ���ַ�����һ����
     * @param aStr Ŀ���ַ���
     * @param len ����
     * @param endStr ����������ȣ��ڽ�ȡ����ַ���β������ӵ��ַ�
     * @return ��ȡ��ĳ���
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
     * �ַ����ֽ⹤��
     * @param inputString String ����Ĵ��ֽ���ַ���.
     * @param split String  ����ķֽ�ı�־.
     * @return String[] ���طֽ���ϵ�string����.
     * added by gaobb. 20060107.
     */
    public static String[] StringSplitTool(String inputString, String split)
    {
        Pattern SyncPattern = Pattern.compile(split) ;
        CharSequence ch = inputString ;
        String[] arrContent = SyncPattern.split(ch);
        return arrContent;
    }


    //���Է�����

    public static void main(String[] args)
    {
        String str = "a1��������0";
        System.out.println(lengthOfHZ(str));
        System.out.println(formatByLen(str, 11, "��"));
    }
    /**
	 * ��{aa};{bb}�ָ�Ϊһ���ַ������鷵��
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
	 * ��ָ���ַ�ƴ���µ��ַ���
	 * @param original �ַ�������
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
	 * ��abcded��ָ�����Ƚ�ȡ����abcd...
	 * @param length ��Ҫ�����ַ�����
	 * @param original  ��ʼ�ַ���
	 * @param ch ��ʲô����ƴ��
	 * @return
	 */
	public static String showKeywords(int length,String original,char ch){
		String[] strArray=convertKeywords(original);
		return subStr(length,packageStr(strArray,ch));
	}
	
	/**
	 * ��aaaaa��ָ�����ȱ��aaa...
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
