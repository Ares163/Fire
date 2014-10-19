package com.fire.util;

import java.util.Map;

/**
 * 
 * 
 * java类获取web应用的根目录
 * 
 */
public class PathUtil {

	private static PathUtil pathUtil = new PathUtil();

	private PathUtil() {

	}

	public static String getWebClassesPath() {
		String path = pathUtil.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;

	}

	public static String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 7);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public static String getWebRoot() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("/WEB-INF/classes"));
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	/**
	 * 获取系统变量
	 * 
	 * @param string
	 * @return
	 */
	public static String systemVar(String string) {
		Map<String, String> m = System.getenv();
		return m.get(string);
	}
}