package com.aspire.common.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ASPire</p>
 * @author ZhangJi
 * @version 1.6
 */

// @CheckItem@ OPT-ZhangJi-20030827 ��ʹ����Կ�ļ���Ϊʹ���ַ���Ϊ��Կ
// @CheckItem@ OPT-ZhangJi-20040208 �Ż�������Կ�ķ���

import java.security.Key;

import javax.crypto.Cipher;

public class DES
{
    private static String strDefaultKey = "1111";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    /**
     * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
     * @throws Exception
     */
    public DES()
        throws Exception
    {
        this(strDefaultKey);
    }

    /**
     * ָ����Կ���췽��
     * @param strKey ָ������Կ
     * @throws Exception
     */
    public DES(String strKey)
        throws Exception
    {
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * �����ֽ�����
     * @param arrB ����ܵ��ֽ�����
     * @return ���ܺ���ֽ�����
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB)
        throws Exception
    {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     * @param strIn ����ܵ��ַ���
     * @return ���ܺ���ַ���
     * @throws Exception
     */
    public String encrypt(String strIn)
        throws Exception
    {
        return StringUtils.byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * �����ֽ�����
     * @param arrB ����ܵ��ֽ�����
     * @return ���ܺ���ֽ�����
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB)
        throws Exception
    {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     * @param strIn ����ܵ��ַ���
     * @return ���ܺ���ַ���
     * @throws Exception
     */
    public String decrypt(String strIn)
        throws Exception
    {
        return new String(decrypt(StringUtils.hexStr2ByteArr(strIn)));
    }

    /**
     * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ
     * ����8λʱ���油0������8λֻȡǰ8λ
     * @param arrBTmp ���ɸ��ַ������ֽ�����
     * @return ���ɵ���Կ
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp)
        throws Exception
    {
        //����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
        byte[] arrB = new byte[8];

        //��ԭʼ�ֽ�����ת��Ϊ8λ
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
        {
            arrB[i] = arrBTmp[i];
        }

        //������Կ
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * ��Ԫ���Է���
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            DES des = new DES(strDefaultKey);
            for (int i = 0; i < 10; i++)
            {
                des.test();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ��Ԫ���Է�������ӡ��ָ���ַ������ܺ�ͽ��ܺ���ַ���
     */
    private void test()
    {
        try
        {
            String strOriginal = "1111";
            System.out.println("Original data : " + strOriginal);
            System.out.println("Length: " + strOriginal.length());

            String strEncrypt = encrypt(strOriginal);
            System.out.println("Encrypted data: " + strEncrypt);
            System.out.println("Length: " + strEncrypt.length());

            String strDecrypt = decrypt("98af16da4eb258c0");
            System.out.println("Decrypted data: " + strDecrypt);
            System.out.println("Length: " + strDecrypt.length());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}