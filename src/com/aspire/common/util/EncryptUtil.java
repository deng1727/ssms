package com.aspire.common.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.aspire.dotcard.syncAndroid.ppms.PackageVO;
import com.aspire.mm.common.client.httpsend.HttpSender;
import com.aspire.mm.common.client.mportal.req.SafeCenterNotifyReq;

public class EncryptUtil {

private static final byte[] KEY = "5x4dqure81c9n8p3".getBytes();
	
	private static byte[] encrypt(byte[] iv, byte[] data, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec dps = new IvParameterSpec(iv);
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, dps);
		byte[] buf = cipher.doFinal(data);
		return buf;
	}

	private static byte[] decrypt(byte[] iv, byte[] data, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec dps = new IvParameterSpec(iv);
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, dps);
		byte[] buf = cipher.doFinal(data);
		return buf;
	}

	/**
	 * @Description: 获取随机字节
	 * @param
	 * @return byte[]
	 */
	private static byte[] getRamdomBytes(int length) {
		byte[] result = new byte[length];
		for (int i = 0; i< length; i++) {
			result[i] = (byte) (Math.round(Math.round(Math.random() * 255)) - 128);
		}
		return result;
	}
	
	 /**
	  * 加密
	  * @param data
	  * @return
	  */
	 public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
		 try {
				byte[] iv = getRamdomBytes(16);
				byte[] buf = encrypt(iv, data,key);
				byte[] result = new byte[iv.length + buf.length];			
				System.arraycopy(iv, 0, result, 0, iv.length);
				System.arraycopy(buf, 0, result, iv.length, buf.length);
				return result;
			} catch (Exception ex) {
//				return null;
				throw new Exception(ex.getMessage());
			}
	 }
	
	public static byte[] encrypt(byte[] data) {
		 try {
				byte[] iv = getRamdomBytes(16);
				byte[] buf = encrypt(iv, data,KEY);
				byte[] result = new byte[iv.length + buf.length];			
				System.arraycopy(iv, 0, result, 0, iv.length);
				System.arraycopy(buf, 0, result, iv.length, buf.length);
				return result;
			} catch (Exception ex) {
				return null;
			}
		}
	 /**
	  * 解密
	  * @paramsrcData
	  * @return
	  */
	public static byte[] decrypt(final byte[] data, final byte[] key) throws Exception {
		try {
			final byte[] iv = new byte[16];
			System.arraycopy(data, 0, iv, 0, iv.length);
			final byte[] src = new byte[data.length - iv.length];
			System.arraycopy(data, iv.length, src, 0, data.length - iv.length);
			final byte[] tmp = decrypt(iv, src, key);			
			return tmp;
		} catch (final Exception ex) {
			return null;
		}
	}
	
	public static byte[] decrypt(final byte[] data) {
		try {
			final byte[] iv = new byte[16];
			System.arraycopy(data, 0, iv, 0, iv.length);
			final byte[] src = new byte[data.length - iv.length];
			System.arraycopy(data, iv.length, src, 0, data.length - iv.length);
			final byte[] tmp = decrypt(iv, src, KEY);
			return tmp;
		} catch (final Exception ex) {
			return null;
		}
	}
	public static void main(String[] args) throws Exception {
		final String POST_URL = "http://ordercenter.kvpioneer.net/mmsafesyn/service";
		String KEY = "mmybr858ri0cl0ld";
		String startString = "adb";
		SafeCenterNotifyReq req = new SafeCenterNotifyReq();
		PackageVO vo = new PackageVO();
		vo.setPackageName("cn.wps.moffice_eng");
		vo.setVersionCode("65");
		vo.setCermd5("96bcc9bdd7eff9d9feb58b29f0ad5d73");
		vo.setOnline(1);
		vo.setDeduction("1");
		List<PackageVO> packageVOList = new ArrayList<PackageVO>();
		packageVOList.add(vo);
		req.setPackageVOList(packageVOList);
		startString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><genuineinfodatapushreq><msgheader version=\"1.0.0\"><pushtype>2</pushtype></msgheader><msgbody><check><package>cn.wps.moffice_eng</package><version>65</version><cermd5>96bcc9bdd7eff9d9feb58b29f0ad5d73</cermd5><online>1</online><deduction>1</deduction></check><check><package>com.ophone.reader.ui</package><version>22</version><cermd5>1df2e857dfd6d0eb0cde692ca11afd54</cermd5><online>1</online><deduction>1</deduction></check></msgbody></genuineinfodatapushreq>";
		System.out.println(startString);
		byte[] start = startString.getBytes("UTF-8");
		byte[] end = encrypt(start);
		HttpSender.sendRequestEncrypt(POST_URL, req) ;
		String msg = new String (decrypt(end),"UTF-8");
		System.out.println(end);
		SAXReader  sax = new SAXReader();         
        Document doc = sax.read(new ByteArrayInputStream(msg.getBytes("UTF-8")));
        System.out.println("response xml : \n" + doc.asXML());

	}
}
