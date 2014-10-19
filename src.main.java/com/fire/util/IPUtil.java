package com.fire.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class IPUtil {

	private static Logger logger = Logger.getLogger(IPUtil.class);

	/**
	 * ip地址正则表达式
	 */
	static final Pattern ipPattern = Pattern
			.compile("^(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.)(([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))\\.){2}([1-9]|([1-9]\\d)|(1\\d\\d)|(2([0-4]\\d|5[0-5])))$");

	/**
	 * 获取真实ip
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * ip白名单判断
	 * 
	 * @param ip
	 * @param whiteList
	 * @return boolean
	 */
	public static boolean canInvokeIP(String ip, String[] whiteList) {
		// 默认允许任意IP调用
		if (whiteList == null || whiteList.length == 0) {
			logger.warn("WhiteList is empty[allow all IP invoke]");
			return true;
		}
		boolean can = false;
		for (String wip : whiteList) {
			// 正则匹配
			Pattern pattern = Pattern.compile(wip.trim());
			Matcher matcher = pattern.matcher(ip);
			if (matcher.find()) {
				can = true;
				break;
			}
		}
		return can;
	}

	/**
	 * 判断ip是否合法
	 * 
	 * @param ip
	 * @return boolean
	 */
	public static boolean isIP(String ip) {
		if (StringUtils.isBlank(ip)) {
			return false;
		}
		Matcher m = ipPattern.matcher(ip);
		return m.find();
	}

	public static void main(String[] args) {
		String[] whiteList = new String[] { "192.168.144.*" };
		String ip = "192.168.144.181";
		String ip_invalidate = "192.168.145.181";
		System.out.println(IPUtil.canInvokeIP(ip, whiteList));
		System.out.println(IPUtil.canInvokeIP(ip_invalidate, whiteList));
		// test ip
		System.out.println(isIP("s"));
		System.out.println(isIP("s.s.s.s"));
		System.out.println(isIP("1.1.1.1"));
		System.out.println(isIP("192.168.1.1"));
		System.out.println(isIP("183.129.244.4"));
		System.out.println(isIP("123.53.2.87"));
		System.out.println(isIP("192.1268.1.1"));
		System.out.println(isIP("1912.168.1.1"));
		System.out.println(isIP("255.255.255.255"));
		System.out.println(isIP("192.sss.s.s"));
		System.out.println(isIP("311.1.2.1"));
	}
}
