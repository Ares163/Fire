package com.fire.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fire.constant.Constants;
import com.fire.model.AjaxResponseInfo;
import com.fire.service.ArticleService;
import com.fire.util.SpringMvcUtil;

@Controller
public class UploadController {
	private final static Logger log = Logger.getLogger(ArticleService.class);
	@RequestMapping(value = "upload")
	public void upload(String type, MultipartHttpServletRequest multipartRequest, HttpSession session, HttpServletResponse response) {
		AjaxResponseInfo ajaxResponseInfo = new AjaxResponseInfo();
		MultipartFile mFile = multipartRequest.getFile("file");
		if (mFile == null || mFile.isEmpty()) {
			ajaxResponseInfo.setCode(Constants.RESPONSE_CODE_400);
			ajaxResponseInfo.setMsg("上传出错");
			SpringMvcUtil.renderJson(response, ajaxResponseInfo);
			return;
		}
		String strArray[] = mFile.getOriginalFilename().split("\\.");
		String suffix = strArray[strArray.length - 1];
		String name = System.currentTimeMillis() + "";
		String path = multipartRequest.getSession().getServletContext().getRealPath("");
		String fileName = "/upload"+File.separator+name + "." + suffix;
		File file = null;
		try {
			file = new File(path+fileName);
			mFile.transferTo(file);
		} catch (Exception e) {
			log.error("资源文件上传失败", e);
			ajaxResponseInfo.setCode(Constants.RESPONSE_CODE_400);
			ajaxResponseInfo.setMsg("上传失败");
			SpringMvcUtil.renderJson(response, ajaxResponseInfo);
			return;
		}
		ajaxResponseInfo.setCode(Constants.RESPONSE_CODE_200);
		ajaxResponseInfo.setData(fileName);
		SpringMvcUtil.renderJson(response, ajaxResponseInfo);
		return;
	}
}
