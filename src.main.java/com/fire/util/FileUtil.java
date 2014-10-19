package com.fire.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * 文件工具类.
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

	/**
	 * 写入输出流.
	 * 
	 * @param stream
	 *            the stream
	 * @param path
	 *            the path
	 * @param fileName
	 *            the file name
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void writerFromInputStream(InputStream stream, String path, String fileName) throws IOException {
		FileOutputStream fs = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			fs = new FileOutputStream(path + File.separator + fileName);
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
		} catch (Exception e) {
			logger.error("Write stream error!", e);
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (Exception e) {
					logger.warn("Resource FileOutputStream error!", e);
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {
					logger.warn("Resource InputStream error!", e);
				}
			}
		}
	}

	/**
	 * 获取配置文件中内部网络IP.
	 * 
	 * @return IP列表
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static List<String> getFilterStr() throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		String fileName = System.getProperty("REAL_PATH") + File.separator + "WEB-INF" + File.separator + "filter_str.properties";
		File f = new File(fileName);
		if (!f.exists()) {
			f = new File(fileName);
			f.createNewFile();
			logger.info("*********缺少配置文件：" + fileName);
			return result;
		}
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (StringUtils.isNotBlank(line)) {
			result.add(line.trim());
			line = br.readLine();
		}
		br.close();
		return result;
	}


	/**
	 * 删除指定文件或目录，若为目录，默认删除全部
	 * 
	 * @param fileName
	 *            - 文件名
	 * @return
	 */
	public static boolean delete(String fileName) {
		// 默认删除方式
		return delete(fileName, false, true);
	}

	/**
	 * 删除指定文件或目录
	 * 
	 * @param fileName
	 *            - 需要删除的文件名，可以是目录
	 * @param emptyDelete
	 *            - 若为目录，是否非空目录可删除，true当目录为空时才可删除，false目录非空可删除
	 * @param isRecursive
	 *            - 是否递归删除
	 * @return
	 */
	public static boolean delete(String fileName, boolean emptyDelete, boolean isRecursive) {
		File file = new File(fileName);
		// 不存在
		if (!file.exists()) {
			logger.info("删除文件失败：" + fileName + "文件不存在");
			return true;
		}
		// 文件
		if (file.isFile()) {
			return file.delete();
		}
		// 空目录才能删
		if (emptyDelete) {
			// 为空才可删除
			if (file.listFiles().length == 0) {
				// 目录为空，删除
				return file.delete();
			}
			// 目录不为空，返回删除失败
			logger.info("删除文件失败：" + fileName + " 目录非空");
			return false;
		}
		// 非空也可删除
		return deleteDirectory(file, isRecursive);

	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @param isRecursive
	 * @return 目录删除成功返回true,否则返回false
	 */
	private static boolean deleteDirectory(File dirFile, boolean isRecursive) {

		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = files[i].delete();
			}
			// 删除子目录
			else {
				if (isRecursive) {
					flag = deleteDirectory(files[i], isRecursive);
				}
			}
			if (!flag) {
				// 删除失败
				logger.info("删除文件失败：" + files[i].getName());
				return false;
			}
		}
		return dirFile.delete();
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file == null) {
			return;
		}
		try {
			file.delete();
		} catch (Exception e) {
			logger.error("删除文件失败：" + file.getPath(), e);
		}
	}
}
