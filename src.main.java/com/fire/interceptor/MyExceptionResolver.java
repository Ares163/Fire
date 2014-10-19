
package com.fire.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fire.constant.Constants;
import com.fire.model.AjaxResponseInfo;
import com.fire.util.IPUtil;
import com.fire.util.SpringMvcUtil;


public class MyExceptionResolver extends SimpleMappingExceptionResolver {

	private static final Logger logger = Logger.getLogger(MyExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		AjaxResponseInfo info = new AjaxResponseInfo(Constants.RESPONSE_CODE_400, null);
		String ip = IPUtil.getRemoteIp(request);
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		String endPath = null;
		if (path.endsWith(".do")) {
			int idx = contextPath.length();
			endPath = path.substring(idx);
			if (endPath.startsWith("//")) {
				endPath = endPath.substring(1);
			}
		}
		if (ex.getCause() instanceof NumberFormatException) {
			logger.error("Wrong param type! String can't be parsed to Integer! Ip [" + ip + "], endPath [" + endPath + "].");
			info.setMsg("参数类型错误!Code [91420].");
			SpringMvcUtil.renderJson(response, info);
			return super.doResolveException(request, response, handler, new NumberFormatException());
		} else if (ex instanceof BindException) {
			logger.error("Wrong param type! Bind model error! Ip [" + ip + "], endPath [" + endPath + "].");
			info.setMsg("参数类型错误!Code [41205].");
			SpringMvcUtil.renderJson(response, info);
			return super.doResolveException(request, response, handler, ex);
		} else {
			info.setMsg("系统繁忙，请稍候再试!");
			SpringMvcUtil.renderJson(response, info);
			return super.doResolveException(request, response, handler, ex);
		}
	}
}
