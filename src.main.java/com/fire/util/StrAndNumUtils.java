package com.fire.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * The Class StrAndNumUtils.
 */
public class StrAndNumUtils {

	/**
	 * 计算包含中英文的字符串占的字节长度.
	 * 
	 * @param str
	 *            the str
	 * @return the int
	 */
	public static int countStrLength(String str) {
		int len = 0;
		str = StringUtils.trim(str);
		if (str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				int num = (int) str.charAt(i);
				if (num <= 0 || num >= 126) {
					len = len + 3; // 汉字在字符集UTF-8数据库中占3个字节
				} else {
					len = len + 1; // 英文、数字在字符集UTF-8数据库中占一个字节
				}
			}
		}
		return len;
	}

	/**
	 * 按字节长度截取字符串.
	 * 
	 * @param str
	 *            将要截取的字符串参数
	 * @param toCount
	 *            截取的字节长度
	 * @param more
	 *            字符串末尾补上的字符串
	 * @return 返回截取后的字符串
	 */
	public static String substring(String str, int toCount, String more) {
		int reInt = 0;
		String reStr = "";
		if (str == null)
			return "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++) {
			String s1 = String.valueOf(tempChar[kk]);
			byte[] b = s1.getBytes();
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1))
			reStr += more;
		return reStr;
	}

	/**
	 * 检查email地址是否合法.
	 * 
	 * @param email
	 *            邮件地址
	 * @return true, if successful
	 */
	public static boolean checkEmail(String email) {
		String ps = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		Pattern p = Pattern.compile(ps);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查手机号码是否合法.
	 * 
	 * @param mobilePhone
	 *            手机号码
	 * @return true, if successful
	 */
	public static boolean checkMobilePhone(String mobilePhone) {
		String ps = "^(13|14|15|17|18)\\d{9}$";
		Pattern p = Pattern.compile(ps);
		Matcher m = p.matcher(mobilePhone);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查录入数字格式是否正确（整数intlen位或者带小数点后pointlen位数字的数字）.
	 * 
	 * @param value
	 *            the value
	 * @param intlen
	 *            the intlen
	 * @param pointlen
	 *            the pointlen
	 * @return true, if successful
	 */
	public static boolean checkNumberPattern(String value, int intlen, int pointlen) {
		String ps = "^([0-9]{1," + intlen + "})$|^(-[0-9]{1," + intlen + "})$|^([0-9]{1," + intlen + "}.[0-9]{1," + pointlen + "})$|^(-[0-9]{1,"
				+ intlen + "}.[0-9]{1," + pointlen + "})$";

		Pattern p = Pattern.compile(ps);
		Matcher m = p.matcher(value);
		if (!m.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字串是否为Double类型.
	 * 
	 * @param str
	 *            the str
	 * @return true, if is int
	 */
	public static boolean isDouble(String str) {
		Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
		Matcher m = p.matcher(str);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字串是否为整数int.
	 * 
	 * @param str
	 *            the str
	 * @return true, if is int
	 */
	public static boolean isInt(String str) {
		Pattern p = Pattern.compile("^[0-9]+$");
		Matcher m = p.matcher(str);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * Check number.
	 * 
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public static boolean checkNumber(String value) {
		try {
			if (value == null || value.length() == 0) {
				return false;
			}
			Float.parseFloat(value);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Check number positive.
	 * 
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public static boolean checkNumberPositive(String value) {
		try {
			if (value == null || value.length() == 0)
				return true;
			float num = Float.parseFloat(value);
			if (num < 0) {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * 提供精确的小数位四舍五入处理。.
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * Str format.
	 * 
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String strFormat(String v) {
		if (v == null) {
			return "0.00";
		}
		double dv = Double.parseDouble(v);
		DecimalFormat nf = new DecimalFormat("#.##");
		nf.applyPattern("0.00");
		return nf.format(dv);
	}

	/**
	 * Number format.
	 * 
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String numberFormat(double v) {
		if (v == 0.0) {
			return "0.00";
		}
		DecimalFormat nf = new DecimalFormat("#.##");
		nf.applyPattern("0.00");
		return nf.format(v);
	}

	/**
	 * Number format to int.
	 * 
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String numberFormatToInt(double v) {
		if (v == 0.0) {
			return "0";
		}
		DecimalFormat nf = new DecimalFormat("#");
		nf.applyPattern("0");
		return nf.format(v);
	}

	/**
	 * Convert rate from double.
	 * 
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String convertRateFromDouble(double value) {
		return String.valueOf(round(value * 100, 2));
	}

	/**
	 * Format discount.
	 * 
	 * @param rate
	 *            the rate
	 * @return the string
	 */
	public static String formatDiscount(double rate) {
		String discount = StrAndNumUtils.convertRateFromDouble(rate);
		return discount + "%";
	}

	/**
	 * Str format2.
	 * 
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String strFormat2(String v) {
		if (v == null) {
			return "0";
		}
		if (v.indexOf(",") > -1) {
			return v;
		}
		double dv = Double.parseDouble(v);
		double sv = round(dv, 0);

		NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
		return nf.format(sv);
	}

	/**
	 * Number format2.
	 * 
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String numberFormat2(double v) {
		double sv = round(v, 0);
		NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
		return nf.format(sv);
	}

	/**
	 * My parse int.
	 * 
	 * @param intValue
	 *            the int value
	 * @param defaultValue
	 *            the default value
	 * @return the int
	 */
	public static int myParseInt(String intValue, int defaultValue) {
		int value = 0;
		try {
			value = Integer.parseInt(intValue);
		} catch (Throwable e) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * 将数字字符串转换成千分位数字字符串.
	 * 
	 * @param str_num
	 *            the str_num
	 * @return the string
	 * @return
	 */
	public static String transformNum(String str_num) {
		String str_result = str_num;
		int len = str_num.length();

		if (len > 3 && len <= 6) {
			str_result = str_num.substring(0, len - 3) + "," + str_num.substring(len - 3);
		}

		if (len > 6 && len <= 9) {
			str_result = str_num.substring(0, len - 6) + "," + str_num.substring(len - 6, len - 3) + "," + str_num.substring(len - 3);
		}

		if (len > 9 && len <= 12) {
			str_result = str_num.substring(0, len - 9) + "," + str_num.substring(len - 9, len - 6) + "," + str_num.substring(len - 6, len - 3) + ","
					+ str_num.substring(len - 3);
		}
		return str_result;
	}

	/**
	 * Str format.
	 * 
	 * @param v
	 *            the v
	 * @return the string
	 */
	public static String strFormat(double v) {
		double sv = round(v, 2);
		NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
		return nf.format(sv);
	}

	/**
	 * Parses the double.
	 * 
	 * @param str
	 *            the str
	 * @return the double
	 */
	public static double parseDouble(String str) {
		if (str == null) {
			str = "0";
		}
		return Double.parseDouble(str);
	}

	/**
	 * command格式例：12@sdfs.dc_sf@dfd.com__ 本函数取出command中的字符串，放到队列listString中.
	 * 
	 * @param strList
	 *            the str list
	 * @return the string list
	 */
	public static List<String> getStringList(String strList) {
		return getStringList('_', strList);
	}

	/**
	 * Gets the string list.
	 * 
	 * @param sep
	 *            the sep
	 * @param StrList
	 *            the str list
	 * @return the string list
	 */
	public static List<String> getStringList(char sep, String StrList) {
		List<String> listString = new ArrayList<String>();
		if (null == StrList) {
			return listString;
		}

		Integer posStart = 0; // 扫描command的游标1:指向一个数字的开始
		Integer posEnd = 0; // 扫描command的游标1:指向一个数字的结束
		String strItem; // 取出的一个字符串

		while (posStart < StrList.length()) {
			/* 1.定位一个数字:夹在"-"中间 */
			posEnd = StrList.indexOf(sep, posStart);
			if (posEnd < 0) {
				/* 后面没有数字 */
				break;
			}
			strItem = StrList.substring(posStart, posEnd);

			/* 2.放到队列中 */
			listString.add(strItem);

			/* 3.移动游标 */
			posStart = posEnd + 1;
		}
		return listString;
	}

	/**
	 * command格式例：12_3_43_34_44_ 本函数取出command中的数字，放到队列listTemplateID中.
	 * 
	 * @param stringIDList
	 *            the string id list
	 * @return the iD list
	 * @throws AppException
	 *             the app exception
	 */
	public static List<Integer> getIDList(String stringIDList) throws Exception {

		List<Integer> listInteger = new ArrayList<Integer>();
		// 拆成若干小字符串
		List<String> listString = getStringList(stringIDList);

		// 转换为Integer队列
		for (int i = 0; i < listString.size(); i++) {
			String strItem = listString.get(i);
			if (!StrAndNumUtils.isInt(strItem)) {
				throw new Exception("错误的ID." + strItem);
			}
			Integer number = Integer.parseInt(strItem);
			if (0 >= number) {
				throw new Exception("ID不能为负" + strItem);
			}
			listInteger.add(number);
		}
		return listInteger;
	}

	/**
	 * To html.
	 * 
	 * @param src
	 *            the src
	 * @return the string
	 */
	public static String ToHtml(String src) {
		src = src.replace("\r\n", "<br>");
		src = src.replace("\n", "<br>");
		src = src.replace("\r", "<br>");
		src = src.replace(" ", "&nbsp;");
		ToJavascriptText(src);
		return src;
	}

	/**
	 * To javascript text.
	 * 
	 * @param src
	 *            the src
	 * @return the string
	 */
	public static String ToJavascriptText(String src) {
		src = src.replace("\\", "\\\\");
		src = src.replace("\n", "\\n");
		src = src.replace("\r", "\\r");
		src = src.replace("'", "\\'");
		src = src.replace("\"", "\\\"");
		src = src.replace("\b", "\\b");
		src = src.replace("\t", "\\t");
		src = src.replace("\f", "\\f");
		return src;
	}

	/**
	 * Checklen.
	 * 
	 * @param tmp_str
	 *            the tmp_str
	 * @return true, if successful
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static boolean checklen(String tmp_str) throws UnsupportedEncodingException {
		String str = new String(tmp_str.getBytes(), "ISO-8859-1");
		char[] b = new char[str.length()];
		str.getChars(0, str.length(), b, 0);
		int cs = 0;
		int es = 0;
		for (int i = 0; i < b.length; i++) {
			char c = b[i];
			if (c > 128) {
				cs++;
				i++;
			} else {
				es++;
			}
		}
		if (cs > 0) {
			if ((es + cs) > (140 / 2)) {
				return true;
			}
		} else {
			if (es > 160) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成随即密码.
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		int maxNum = 36;
		// 生成的随机数
		int i;
		// 生成的密码的长度
		int count = 0;

		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

}
