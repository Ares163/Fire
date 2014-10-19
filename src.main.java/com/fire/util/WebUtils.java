package com.fire.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Web Utils函数集合.
 * 
 */
public class WebUtils {
	protected static Logger logger = Logger.getLogger(WebUtils.class);

	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 * @throws UnsupportedEncodingException
	 */
	public static void setDownloadableHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 设置过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
	}

	/**
	 * 设置无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 检查浏览器客户端是否支持gzip编码.
	 */
	public static boolean checkAccetptGzip(HttpServletRequest request) {
		// Http1.1 header
		String acceptEncoding = request.getHeader("Accept-Encoding");

		if (StringUtils.contains(acceptEncoding, "gzip")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置Gzip Header并返回GZIPOutputStream.
	 */
	public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Encoding", "gzip");
		return new GZIPOutputStream(response.getOutputStream());
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header,计算Etag是否无效.
	 * 
	 * 如果Etag有效,checkIfNoneMatch返回false, 设置304 not modify status.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!headerValue.equals("*")) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	public static String getRemoteIpAddress(HttpServletRequest request) {
		String rip = request.getRemoteAddr();
		String xff = request.getHeader("X-Forwarded-For");
		String ip;
		if (xff != null && xff.length() != 0) {
			int px = xff.indexOf(',');
			if (px != -1) {
				ip = xff.substring(0, px);
			} else {
				ip = xff;
			}
		} else {
			ip = rip;
		}
		return StringUtils.trimToEmpty(ip);
	}

	/**
	 * 获取用户的url请求
	 * 
	 * @param request
	 * @param invocation
	 * @return
	 */
	public static String getGoingToURL(HttpServletRequest request) {

		final StringBuffer buffer = request.getRequestURL();
		if (request.getQueryString() != null) {
			buffer.append("?");
			buffer.append(request.getQueryString());
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @param inStream
	 *            - 文件流
	 * @param response
	 *            - http响应
	 * @param type
	 *            - http响应类型，默认为"bin"
	 * @param filename
	 *            - 文件名
	 * @throws IOException
	 */
	public static void downLoadFileByStream(InputStream inStream, HttpServletResponse response, String type, String filename) {
		try {
			if (StringUtils.isBlank(type)) {
				type = "bin";
			}
			// 设置输出的格式
			response.reset();
			response.setContentType(type);

			WebUtils.setDownloadableHeader(response, filename);

			// 读取文件流到response中
			byte[] buffer = new byte[1204];
			int byteread = 0;
			while ((byteread = inStream.read(buffer)) != -1) {
				response.getOutputStream().write(buffer, 0, byteread);
			}
			inStream.close();
			response.getOutputStream().close();
		} catch (IOException e) {
			logger.error("Download exception:", e);
		} finally {
			if (inStream != null) {
				try {
					response.getOutputStream().close();
				} catch (IOException e) {
					logger.error("Download exception:", e);
				}
			}
		}
	}
}
