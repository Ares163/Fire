package com.fire.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

// TODO: Auto-generated Javadoc
/**
 * 验证码相关工具类.
 */
public class CaptchaUtil {

	/**
	 * 写入图片.
	 * 
	 * @param response
	 *            the response
	 * @param bi
	 *            the bi
	 */
	public static void writeImage(HttpServletResponse response, ByteArrayInputStream bi) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "private,no-cache,no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg; charset=utf-8");
		try {
			writeImage(response.getOutputStream(), bi);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write image.
	 * 
	 * @param os
	 *            the os
	 * @param bi
	 *            the bi
	 */
	public static void writeImage(OutputStream os, BufferedImage bi) {
		try {
			// 输出数据流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bi);
			os.flush();
			// 关闭
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write image.
	 * 
	 * @param os
	 *            the os
	 * @param bi
	 *            the bi
	 */
	public static void writeImage(OutputStream os, ByteArrayInputStream bi) {
		try {
			// 输出数据流
			byte[] b = new byte[512];
			while (bi.read(b) != -1) {
				os.write(b);
			}
			bi.close();
			os.flush();
			// 关闭
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
