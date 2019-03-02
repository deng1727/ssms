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
	 * 密钥对个数
	 */
	private static int maxLength = 50;
	
	/**
	 * Cipher个数
	 */
	private static int cipherCnt = 100;

	private static Logger logger = Logger.getLogger(RSAConfiguration.class);

	/**
	 * 密钥对集合
	 */
	public static List<KeyPair> keyPairs = new CopyOnWriteArrayList<KeyPair>();
	
	/**
	 * Cipher集合
	 */
	public static List<Cipher> ciphers = new CopyOnWriteArrayList<Cipher>();

	/**
	 * 初始化密钥对和Cipher
	 */
	public static void init() {
//		new Thread(
//			new Runnable() {
//				public void run() {
//				   
//					logger.debug("开始生成密钥对");
					for (int i = 0; i < maxLength; i++) {
						try {
							KeyPair keyPair = RSAUtil.generateKeyPair();
							keyPairs.add(keyPair);
							logger.debug("第"+i+"个密钥对生成成功");
						} catch (Exception e) {
							logger.error("第"+i+"个密钥对生成失败:"+e);
						}
					}
//				}
//		}).start();
//		new Thread(new Runnable() {
//			public void run() {
//			    Logger logger = Logger.getLogger(RSAConfiguration.class);
				logger.debug("开始生成Cipher");
				for (int i = 0; i < cipherCnt; i++) {
					try {
						Cipher cipher = Cipher
								.getInstance(
										"RSA",
										new org.bouncycastle.jce.provider.BouncyCastleProvider());
						ciphers.add(cipher);
						logger.debug("第"+i+"个Cipher对生成成功");
					} catch (NoSuchAlgorithmException e) {
						logger.error("第"+i+"个Cipher对生成失败");
					} catch (NoSuchPaddingException e) {
						logger.error("第"+i+"个Cipher对生成失败");
					}
				}
//			}
//		}).start();
	}
	
	/**
	 * 获取密钥对
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
				logger.error("密钥对生成失败");
			}
		}else{
			//随机获取
			int i = (int)(Math.random()*size);
			keyPair = keyPairs.get(i);
		}
		return keyPair;
	}
	
	public static int index = 0;
	
	/**
	 * 对同获取Cipher
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
				logger.error("密钥对生成失败");
			}
		}else{
			cipher = ciphers.get(index++);
		}
		return cipher;
	}
}
