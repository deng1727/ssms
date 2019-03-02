package com.aspire.common.util;

/**
 * <p>Title: Base64</p>
 * <p>Description: Base64����ı���ͽ�����</p>
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
     * ���뷽��, ��һ���ֽ��������ΪBASE64������ַ���
     * @param arrB ��Ҫ������ֽ�����
     * @return BASE64������ַ���
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
     * ���뷽��, ��һ��BASE64������ַ�������Ϊ����ǰ���ֽ�����
     * @param in ��Ҫ�����BASE64������ַ���
     * @return �������ֽ�����, ������ʧ��(���ַ�������BASE64����)�򷵻�null
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
     * ��Ԫ���Է���, ��ӡһ���ַ������ڴ��еĶ�����ֵ
     * @param in ��Ҫ��ӡ���ַ���
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
     * ��Ԫ���Է���, �ֱ��ӡһ���ַ�������ǰ, �����, ������ֵ
     * @param args ��������, ��ʹ��
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