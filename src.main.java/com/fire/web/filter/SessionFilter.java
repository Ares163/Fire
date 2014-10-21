package com.fire.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.fire.constant.Constants;
import com.fire.model.AjaxResponseInfo;
import com.fire.model.User;
import com.fire.util.SpringMvcUtil;

public class SessionFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute(Constants.USER);
		String path = req.getRequestURI();
		String contextPath = req.getContextPath();

		int idx = contextPath.length();
		String endPath = path.substring(idx);
		if (endPath.startsWith("//")) {
			endPath = endPath.substring(1).toLowerCase();
		}
		if(endPath.startsWith("update") || endPath.startsWith("delete") || 
				endPath.startsWith("add")){
			if(user != null){
				chain.doFilter(request, response);
			}else if(endPath.startsWith("addMessage")){
				chain.doFilter(request, response);
			}else{
				AjaxResponseInfo info = new AjaxResponseInfo();
				info.setCode(Constants.RESPONSE_CODE_400);
				info.setMsg("登录超时，请重新登录");
				SpringMvcUtil.renderJson(rep, info);
				return;
				
			}
		}
			
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
