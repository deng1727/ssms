package com.aspire.common.util;  

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;



/** 
 * RSA �����ࡣ�ṩ���ܣ����ܣ�������Կ�Եȷ����� 
 * ��Ҫ��http://www.bouncycastle.org����bcprov-jdk15-123.jar�� 
 *  
 */  
public class RSAUtil {
    /** 
     * * ������Կ�� * 
     *  
     * @return KeyPair * 
     * @throws EncryptException 
     */  
    public static KeyPair generateKeyPair() throws Exception {
        try {  
        	BouncyCastleProvider b = new BouncyCastleProvider();
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",b);  
            final int KEY_SIZE = 1024;//���ֵ��ϵ������ܵĴ�С�����Ը��ģ����ǲ�Ҫ̫�󣬷���Ч�ʻ��  
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());  
            KeyPair keyPair = keyPairGen.generateKeyPair();  
            return keyPair;  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }  
    }  
 /*
    public static  void saveKeyPair(KeyPair kp,String path)throws Exception{  
          
         FileOutputStream fos = new FileOutputStream(path);  
         ObjectOutputStream oos = new ObjectOutputStream(fos);  
         //������Կ  
         oos.writeObject(kp);  
         oos.close();  
         fos.close();  
    }  
*/
    /** 
     * * ���ɹ�Կ * 
     *  
     * @param modulus * 
     * @param publicExponent * 
     * @return RSAPublicKey * 
     * @throws Exception 
     */  
    public static  RSAPublicKey generateRSAPublicKey(byte[] modulus,  
            byte[] publicExponent) throws Exception {  
        KeyFactory keyFac = null;  
        try {  
            keyFac = KeyFactory.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        } catch (NoSuchAlgorithmException ex) {  
            throw new Exception(ex.getMessage());  
        }  

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(  
                modulus), new BigInteger(publicExponent));  
        try {  
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);  
        } catch (InvalidKeySpecException ex) {  
            throw new Exception(ex.getMessage());  
        }  
    }  

    /** 
     * * ����˽Կ * 
     *  
     * @param modulus * 
     * @param privateExponent * 
     * @return RSAPrivateKey * 
     * @throws Exception 
     */  
    public static  RSAPrivateKey generateRSAPrivateKey(byte[] modulus,  
            byte[] privateExponent) throws Exception {  
        KeyFactory keyFac = null;  
        try {  
            keyFac = KeyFactory.getInstance("RSA",  
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        } catch (NoSuchAlgorithmException ex) {  
            throw new Exception(ex.getMessage());  
        }  

        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(  
                modulus), new BigInteger(privateExponent));  
        try {  
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);  
        } catch (InvalidKeySpecException ex) {  
            throw new Exception(ex.getMessage());  
        }  
    }  

    /** 
     * * ���� * 
     *  
     * @param key 
     *            ���ܵ���Կ * 
     * @param data 
     *            �����ܵ��������� * 
     * @return ���ܺ������ * 
     * @throws Exception 
     */  
    public static  byte[] encrypt(PublicKey pk, byte[] data) throws Exception {  
        try {  
            Cipher cipher = RSAConfiguration.getCipher();  
            cipher.init(Cipher.ENCRYPT_MODE, pk);  
            int blockSize = cipher.getBlockSize();// ��ü��ܿ��С���磺����ǰ����Ϊ128��byte����key_size=1024  
            // ���ܿ��СΪ127  
            // byte,���ܺ�Ϊ128��byte;��˹���2�����ܿ飬��һ��127  
            // byte�ڶ���Ϊ1��byte  
            int outputSize = cipher.getOutputSize(data.length);// ��ü��ܿ���ܺ���С  
            int leavedSize = data.length % blockSize;  
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1  
                    : data.length / blockSize;  
            byte[] raw = new byte[outputSize * blocksSize];  
            int i = 0;  
            while (data.length - i * blockSize > 0) {  
                if (data.length - i * blockSize > blockSize)  
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i  
                            * outputSize);  
                else  
                    cipher.doFinal(data, i * blockSize, data.length - i  
                            * blockSize, raw, i * outputSize);  
                // ������doUpdate���������ã��鿴Դ�������ÿ��doUpdate��û��ʲôʵ�ʶ������˰�byte[]�ŵ�  
                // ByteArrayOutputStream�У������doFinal��ʱ��Ž����е�byte[]���м��ܣ����ǵ��˴�ʱ���ܿ��С�ܿ����Ѿ�������  
                // OutputSize����ֻ����dofinal������  

                i++;  
            }  
            cipher =null;
            return raw;  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }  
    }  

    /** 
     * * ���� * 
     *  
     * @param key 
     *            ���ܵ���Կ * 
     * @param raw 
     *            �Ѿ����ܵ����� * 
     * @return ���ܺ������ * 
     * @throws Exception 
     */  
    public static  byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {  
    	ByteArrayOutputStream bout =null;
        try {  
            Cipher cipher = RSAConfiguration.getCipher(); 
            cipher.init(cipher.DECRYPT_MODE, pk);  
            int blockSize = cipher.getBlockSize();  
            bout = new ByteArrayOutputStream(64);  
            int j = 0;  
            while (raw.length - j * blockSize > 0) {  
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));  
                j++;  
            }  
            cipher=null;
            return bout.toByteArray();  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }finally{
        	if(bout!=null)
        		bout.close();
        }
    }  
}  
