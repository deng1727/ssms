package com.aspire.common.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: ASPire</p>
 * @author ZhangJi
 * @version 1.6
 */

// @CheckItem@ OPT-ZhangJi-20030827 把使用密钥文件改为使用字符串为密钥
// @CheckItem@ OPT-ZhangJi-20040208 优化构造密钥的方法

import java.security.Key;

import javax.crypto.Cipher;

public class DES
{
    private static String strDefaultKey = "1111";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    /**
     * 默认构造方法，使用默认密钥
     * @throws Exception
     */
    public DES()
        throws Exception
    {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     * @param strKey 指定的密钥
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
     * 加密字节数组
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encrypt(byte[] arrB)
        throws Exception
    {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn)
        throws Exception
    {
        return StringUtils.byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * 解密字节数组
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB)
        throws Exception
    {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn)
        throws Exception
    {
        return new String(decrypt(StringUtils.hexStr2ByteArr(strIn)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位
     * 不足8位时后面补0，超出8位只取前8位
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp)
        throws Exception
    {
        //创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        //将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
        {
            arrB[i] = arrBTmp[i];
        }

        //生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * 单元测试方法
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
     * 单元测试方法，打印对指定字符串加密后和解密后的字符串
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