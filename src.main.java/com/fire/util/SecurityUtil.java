package com.fire.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityUtil {
	
	private static Logger logger = Logger.getLogger(SecurityUtil.class);
	/**
	 * 对传入的字串进行sha1加密
	 * 
	 * @param content
	 */
	public static String getStringSHA1(String content) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.getBytes());
			BigInteger bi = new BigInteger(1, digest);
			return bi.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error("获取SHA1出错", e);
		}
		return null;
	}

	/**
	 * MD5 加密
	 */
	public static String getStringMD5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			logger.error("获取MD5出错", e);
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("获取MD5出错", e);
		}

		byte[] byteArray = messageDigest.digest();
		BigInteger bi = new BigInteger(1, byteArray);
		return bi.toString(16);
	}

	/**
	 * 对文件进行MD5
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取文件MD5出错", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("获取文件MD5出错", e);
				}
			}
		}
		return value;
	}
	/**
	 * 使用des进行加密
	 * @param encryptKey 至少为8位
	 * @param data 需要加密数据 ,若为空直接返回
	 * @return 加密后结果
	 * @throws RuntimeException
	 */
	public static String encryptDES(String encryptKey, String data) throws RuntimeException{
		if(encryptKey==null || encryptKey.length()<8){
			throw new IllegalArgumentException("密钥至少为8位");
		}
		if(StringUtils.isBlank(data)){
			return data;
		}
		try{			
			SecureRandom sr = new SecureRandom();  
			byte rawKeyData[] = encryptKey.getBytes();  
			DESKeySpec dks = new DESKeySpec(rawKeyData);  
			
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
			SecretKey key = keyFactory.generateSecret(dks);  
			
			Cipher c = Cipher.getInstance("DES");  
			c.init(Cipher.ENCRYPT_MODE, key, sr);  
			byte[] cipherByte = c.doFinal(data.getBytes());  
			String dec = new BASE64Encoder().encode(cipherByte);  
			return dec;  
		}catch(Exception e){
			logger.error("加密出错:encryptKey="+encryptKey+",data="+data, e);
			throw new RuntimeException("des加密出错");
		}
	}
	/**
	 * 使用des进行解密
	 * @param decryptKey 解密密钥，与加密密钥为同一个
	 * @param data 加密内容,若为空直接返回
	 * @return 解密后的内容
	 * @throws RuntimeException
	 */
	public static String decryptDES(String decryptKey, String data) throws RuntimeException{
		if(decryptKey==null || decryptKey.length()<8){
			throw new IllegalArgumentException("密钥至少为8位");
		}
		if(StringUtils.isBlank(data)){
			return data;
		}
		try{			
			byte[] dec = new BASE64Decoder().decodeBuffer(data);
			
			SecureRandom sr = new SecureRandom();
			byte rawKeyData[] = decryptKey.getBytes();
			
			DESKeySpec dks = new DESKeySpec(rawKeyData);
			
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			
			SecretKey key = keyFactory.generateSecret(dks);
			
			Cipher c = Cipher.getInstance("DES");
			c.init(Cipher.DECRYPT_MODE, key, sr);
			byte[] clearByte = c.doFinal(dec);
			
			return new String(clearByte);
		}catch(Exception e){
			logger.error("解密出错:decryptKey="+decryptKey+",data="+data, e);
			throw new RuntimeException("des解密出错");
		}
	}
	
	public static void main(String args[]) throws IOException {
		
		String key = "ada是的方法是  ";
		String data = "sdfakjdsert89fsdfsdf3333";
		String encryptStr = encryptDES(key, data);
		System.out.println(encryptStr.length());
		String str = encryptStr;
		System.out.println(new BASE64Decoder().decodeBuffer(str).length);
		System.out.println(decryptDES(key, str));

//		File file = new File("d:/vo.mp3");
//		System.out.println("MD5 file:" + getFileMD5(file));
	}

}
