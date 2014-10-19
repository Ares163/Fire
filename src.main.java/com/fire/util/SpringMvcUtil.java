package com.fire.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;

public class SpringMvcUtil {

	private static Logger logger = Logger.getLogger(SpringMvcUtil.class);

	// -- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain", "hello", "no-cache:false"); render("text/plain", "hello",
	 * "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final HttpServletResponse response, final String contentType, final String content, final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else {
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
				}
			}

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			// response.setHeader("Access-Control-Allow-Origin", "*");
			if (noCache) {
				WebUtils.setNoCacheHeader(response);
			}
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final HttpServletResponse response, final String text, final String... headers) {
		render(response, TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final HttpServletResponse response, final String html, final String... headers) {
		render(response, HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final HttpServletResponse response, final String xml, final String... headers) {
		render(response, XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, final String jsonString, final String... headers) {
		render(response, JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, final Map<?, ?> map, final String... headers) {
		String jsonString = JSONObject.fromObject(map).toString();
		render(response, JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, final Object object, final String... headers) {
		String jsonString = JSONObject.fromObject(object).toString();
		render(response, JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, final Object object, JsonConfig json, final String... headers) {
		String jsonString = JSONObject.fromObject(object,json).toString();
		render(response, JSON_TYPE, jsonString, headers);
	}
	
	/**
	 * 直接输出JSON.
	 * 
	 * @param collection
	 *            Java对象集合, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, final Collection<?> collection, final String... headers) {
		String jsonString = JSONArray.fromObject(collection).toString();
		render(response, JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param array
	 *            Java对象数组, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final HttpServletResponse response, final Object[] array, final String... headers) {
		String jsonString = JSONArray.fromObject(array).toString();
		render(response, JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param contentMap
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJsonp(final HttpServletResponse response, final String callbackName, final Map<?, ?> contentMap, final String... headers) {
		String jsonParam = JSONObject.fromObject(contentMap).toString();

		StringBuilder result = new StringBuilder().append(callbackName).append("(").append(jsonParam).append(");");

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{content:'Hello World!!!'}");
		render(response, JS_TYPE, result.toString(), headers);
	}
}
