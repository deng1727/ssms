package com.aspire.common.util;

/**
 * <p>Title: Base64</p>
 * <p>Description: Base64编码的编码和解码类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ASPire</p>
 * @author ZhangJi
 * @version 1.0
 */
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class Base64
{

    /**
     * 编码方法, 把一个字节数组编码为BASE64编码的字符串
     * @param arrB 需要编码的字节数组
     * @return BASE64编码的字符串
     */
    public static String encode(byte[] arrB)
    {
        if (arrB == null)
        {
            return null;
        }
        return new BASE64Encoder().encode(arrB);
    }

    /**
     * 解码方法, 把一个BASE64编码的字符串解码为编码前的字节数组
     * @param in 需要解码的BASE64编码的字符串
     * @return 解码后的字节数组, 若解码失败(该字符串不是BASE64编码)则返回null
     */
    public static byte[] decode(String in)
    {
        byte[] arrB = null;
        try
        {
            arrB = new BASE64Decoder().decodeBuffer(in);
        }
        catch (Exception ex)
        {
            arrB = null;
        }
        return arrB;
    }

    /**
     * 单元测试方法, 打印一个字符串在内存中的二进制值
     * @param in 需要打印的字符串
     */
    public static void printByte(String in)
    {
        byte[] arrB = in.getBytes();
        for (int i = 0; i < arrB.length; i++)
        {
            System.out.print(Integer.toHexString(arrB[i]) + " ");
        }
        System.out.println();
    }

    /**
     * 单元测试方法, 分别打印一个字符串编码前, 编码后, 解码后的值
     * @param args 参数数组, 不使用
     */
    public static void main(String[] args)
    {
        String strTmp = "13823194533";
        System.out.println(strTmp);
        printByte(strTmp);

        strTmp = encode(strTmp.getBytes());
        //strTmp = encode("".getBytes());
        System.out.println(strTmp);
        printByte(strTmp);

        strTmp = new String(decode(strTmp));
        System.out.println(strTmp);
        printByte(strTmp);

        strTmp = "MTMxLDEwMDAxMA==";
        strTmp = new String(decode(strTmp));
        System.out.println(strTmp);
        printByte(strTmp);

    }
}