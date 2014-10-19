package com.fire.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * The Class Tools.
 */
public class Tools {

	/** The DEBUG. */
	static boolean DEBUG = true;

	// 0. Some parameters
	/** The NUMBE r_ key. */
	static byte NUMBER_KEY = 48;

	/** The UPPE r_ key. */
	static byte UPPER_KEY = 55;

	/** The LOWE r_ key. */
	static byte LOWER_KEY = 87;

	/** The HE x_ key. */
	static byte HEX_KEY = 16;

	/** The NUMBE r_ max. */
	static byte NUMBER_MAX = 57;

	/** The NUMBE r_ min. */
	static byte NUMBER_MIN = 48;

	/** The UPPE r_ max. */
	static byte UPPER_MAX = 70;

	/** The UPPE r_ min. */
	static byte UPPER_MIN = 65;

	/** The LOWE r_ max. */
	static byte LOWER_MAX = 102;

	/** The LOWE r_ min. */
	static byte LOWER_MIN = 97;

	/** The check byte. */
	static byte checkByte = 0;

	/** The MS g_ coding. */
	static String MSG_CODING = "ISO8859_1";

	// Convert a String to SMPPBytes
	/**
	 * Str to smpp bytes.
	 * 
	 * @param pStr
	 *            the str
	 * @return the byte[]
	 */
	public static byte[] StrToSMPPBytes(String pStr) {
		// try
		// {
		byte[] tmBytes = StrToHex(pStr);
		if (tmBytes == null)
			return null;
		byte[] destNum = new byte[tmBytes.length + 1];
		for (int i = 0; i < tmBytes.length; i++)
			destNum[i] = tmBytes[i];
		destNum[destNum.length - 1] = 0x00;
		return destNum;
		// }
		/*
		 * catch (UnsupportedEncodingException ex) { System.err.println("UnsupportedEncoding on "+pStr); ex.printStackTrace(); return null; }
		 */}

	// read
	// Note: This convertation is an unsigned byte between string
	// Convert the String to Binary coded by Hex
	/**
	 * Str to hex.
	 * 
	 * @param pStr
	 *            the str
	 * @return the byte[]
	 */
	public static byte[] StrToHex(String pStr) {

		byte[] bytes = pStr.getBytes();
		byte[] tmpBytes = new byte[2];
		int i = -1;
		Vector<Integer> v = new Vector<Integer>();
		while (i < bytes.length - 1) {
			tmpBytes[0] = 0;
			tmpBytes[1] = 0;
			try {
				// convert the ascii to hex here
				// 1. convert the high position
				i++;
				tmpBytes[0] = bytes[i];
				checkByte = tmpBytes[0];
				if (tmpBytes[0] >= NUMBER_MIN && tmpBytes[0] <= NUMBER_MAX)
					tmpBytes[0] = new Integer(tmpBytes[0] - NUMBER_KEY).byteValue();
				if (tmpBytes[0] >= UPPER_MIN && tmpBytes[0] <= UPPER_MAX)
					tmpBytes[0] = new Integer(tmpBytes[0] - UPPER_KEY).byteValue();
				if (tmpBytes[0] >= LOWER_MIN && tmpBytes[0] <= LOWER_MAX)
					tmpBytes[0] = new Integer(tmpBytes[0] - LOWER_KEY).byteValue();
				if (checkByte == tmpBytes[0]) {
					continue;
				}
				tmpBytes[0] = new Integer(tmpBytes[0] * HEX_KEY).byteValue();

				// 2. conver the low position
				i++;
				tmpBytes[1] = bytes[i];
				checkByte = tmpBytes[1];
				if (tmpBytes[1] >= NUMBER_MIN && tmpBytes[1] <= NUMBER_MAX)
					tmpBytes[1] = new Integer(tmpBytes[1] - NUMBER_KEY).byteValue();
				if (tmpBytes[1] >= UPPER_MIN && tmpBytes[1] <= UPPER_MAX)
					tmpBytes[1] = new Integer(tmpBytes[1] - UPPER_KEY).byteValue();
				if (tmpBytes[1] >= LOWER_MIN && tmpBytes[1] <= LOWER_MAX)
					tmpBytes[1] = new Integer(tmpBytes[1] - LOWER_KEY).byteValue();
				if (checkByte == tmpBytes[1]) {
					continue;
				}

			} catch (java.lang.ArrayIndexOutOfBoundsException ex) {

			}
			v.add(new Integer(tmpBytes[0] + tmpBytes[1]));
		}
		Object[] intList = v.toArray();
		byte[] result = new byte[intList.length];
		for (int li = 0; li < intList.length; li++) {
			result[li] = ((Integer) intList[li]).byteValue();
		}
		return result;
	}

	// Note: This convertation is an unsigned byte between string
	// Convert the Binary to String coded by Hex
	/**
	 * Hex to str.
	 * 
	 * @param pBytes
	 *            the bytes
	 * @return the string
	 */
	public static String HexToStr(byte[] pBytes) {
		String result = "";
		for (int i = 0; i < pBytes.length; i++) {
			int tmpInt = new Byte(pBytes[i]).intValue();
			if (tmpInt < 0)
				tmpInt = tmpInt + 256;
			byte[] strList = new byte[2];
			strList[1] = new Integer(tmpInt % 16).byteValue();
			strList[0] = new Integer((tmpInt / 16) % 16).byteValue();

			if (strList[1] > 9 && strList[1] < 16)
				strList[1] += UPPER_KEY;
			if (strList[1] >= 0 && strList[1] < 10)
				strList[1] += NUMBER_KEY;

			if (strList[0] > 9 && strList[0] < 16)
				strList[0] += UPPER_KEY;
			if (strList[0] >= 0 && strList[0] < 10)
				strList[0] += NUMBER_KEY;

			result = result + new String(strList);

		}
		return result;
	}

	/**
	 * Encrypt.
	 * 
	 * @param plainText
	 *            the plain text
	 * @param deskey
	 *            the deskey
	 * @return the string
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	@SuppressWarnings("deprecation")
	public static String encrypt(String plainText, SecretKey deskey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		String result = null;
		if (null == plainText) {
			return null;
		}
		// 用密钥加密明文,生成密文(cipherByte)
		Cipher c1 = Cipher.getInstance("DES");
		c1.init(Cipher.ENCRYPT_MODE, deskey);

		byte[] cipherByte = c1.doFinal(plainText.getBytes());

		result = URLEncoder.encode(Base64.encode(cipherByte));

		return result;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		String str = "08 9";
		System.out.println(Tools.HexToStr(Tools.StrToHex(str)));
	}
}
