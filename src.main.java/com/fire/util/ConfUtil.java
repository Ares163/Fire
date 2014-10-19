package com.fire.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfUtil {

	private static Logger logger = Logger.getLogger(ConfUtil.class);
	
	/**
	 * 获取文件中的配置项
	 * 
	 * @param properyName
	 * @param filename
	 *            相对web根目录的路径，/开头
	 * @return
	 */
	public static String getConfigFromFilePath(String filename, String properyName) {
		Properties prop = loadConfigFileFromFilePath(filename);
		return prop.getProperty(properyName) == null ? "" : prop.getProperty(properyName).trim();
	}

	/**
	 * 获取文件中的配置项
	 * 
	 * @param filename
	 *            相对web根目录的路径，要以/开头
	 * @return
	 */
	public static Properties loadConfigFileFromFilePath(String filename) {
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			String fullPath = PathUtil.getWebRoot() + filename;
			File f = new File(fullPath);
			fis = new FileInputStream(f);
			prop.load(fis);
		} catch (Exception e) {
			logger.error("Load config file from filepath error!", e);
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				logger.error("Resource FileInputStream error!", e);
			}
		}
		return prop;
	}

	/**
	 * 根据key获取配置项的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfigFromClassPath(String filename, String conf) {
		Properties props = loadConfigFileFromClassPath(filename);
		if (props == null) {
			return null;
		}
		return props.getProperty(conf);
	}

	/**
	 * 从类路径中获取配置文件
	 * 
	 * @param filename
	 * @return
	 */
	public static Properties loadConfigFileFromClassPath(String filename) {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			Properties props = new Properties();
			props.load(input);
			return props;
		} catch (IOException e) {
			// load failed:
			logger.error("Load config file from classpath error!", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Resource FileInputStream error!", e);
				}
			}
		}
		return null;
	}
}
