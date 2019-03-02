package com.aspire.common.util;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

public class RSAConfiguration {

	/**
	 * ��Կ�Ը���
	 */
	private static int maxLength = 50;
	
	/**
	 * Cipher����
	 */
	private static int cipherCnt = 100;

	private static Logger logger = Logger.getLogger(RSAConfiguration.class);

	/**
	 * ��Կ�Լ���
	 */
	public static List<KeyPair> keyPairs = new CopyOnWriteArrayList<KeyPair>();
	
	/**
	 * Cipher����
	 */
	public static List<Cipher> ciphers = new CopyOnWriteArrayList<Cipher>();

	/**
	 * ��ʼ����Կ�Ժ�Cipher
	 */
	public static void init() {
//		new Thread(
//			new Runnable() {
//				public void run() {
//				   
//					logger.debug("��ʼ������Կ��");
					for (int i = 0; i < maxLength; i++) {
						try {
							KeyPair keyPair = RSAUtil.generateKeyPair();
							keyPairs.add(keyPair);
							logger.debug("��"+i+"����Կ�����ɳɹ�");
						} catch (Exception e) {
							logger.error("��"+i+"����Կ������ʧ��:"+e);
						}
					}
//				}
//		}).start();
//		new Thread(new Runnable() {
//			public void run() {
//			    Logger logger = Logger.getLogger(RSAConfiguration.class);
				logger.debug("��ʼ����Cipher");
				for (int i = 0; i < cipherCnt; i++) {
					try {
						Cipher cipher = Cipher
								.getInstance(
										"RSA",
										new org.bouncycastle.jce.provider.BouncyCastleProvider());
						ciphers.add(cipher);
						logger.debug("��"+i+"��Cipher�����ɳɹ�");
					} catch (NoSuchAlgorithmException e) {
						logger.error("��"+i+"��Cipher������ʧ��");
					} catch (NoSuchPaddingException e) {
						logger.error("��"+i+"��Cipher������ʧ��");
					}
				}
//			}
//		}).start();
	}
	
	/**
	 * ��ȡ��Կ��
	 * @return KeyPair
	 */
	public static KeyPair getKeyPair(){
	    Logger logger = Logger.getLogger(RSAConfiguration.class);
		KeyPair keyPair = null;
		int size = keyPairs.size();
		if(size <= 0){
			try {
				keyPair = RSAUtil.generateKeyPair();
				keyPairs.add(keyPair);
			} catch (Exception e) {
				logger.error("��Կ������ʧ��");
			}
		}else{
			//�����ȡ
			int i = (int)(Math.random()*size);
			keyPair = keyPairs.get(i);
		}
		return keyPair;
	}
	
	public static int index = 0;
	
	/**
	 * ��ͬ��ȡCipher
	 * @return Cipher
	 */
	public static synchronized Cipher getCipher(){
		int size = ciphers.size();
		if(index >= (size-1))
			index = 0;
		Cipher cipher =null;
		if(size <= 0){
			try {
				cipher = Cipher
				.getInstance(
						"RSA",
						new org.bouncycastle.jce.provider.BouncyCastleProvider());
				ciphers.add(cipher);
			} catch (Exception e) {
			    Logger logger = Logger.getLogger(RSAConfiguration.class);
				logger.error("��Կ������ʧ��");
			}
		}else{
			cipher = ciphers.get(index++);
		}
		return cipher;
	}
}
