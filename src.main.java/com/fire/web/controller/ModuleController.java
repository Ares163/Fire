package com.fire.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fire.constant.Constants;
import com.fire.model.AjaxResponseInfo;
import com.fire.model.Module;
import com.fire.service.ModuleService;
import com.fire.util.SpringMvcUtil;

@Controller
public class ModuleController {
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping("addModule")
	public void addModule(String name, Integer parentId, HttpSession session, HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		if(StringUtils.isBlank(name) || name.length() > 50){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("模块名字不合法!");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		if (parentId == null || moduleService.getModule(parentId, 1) == null){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("父级模块不存在!");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		Module module = new Module();
		module.setParentId(parentId);
		module.setName(name);
		module.setLevel(2);
		moduleService.addModule(module);
		info.setCode(Constants.RESPONSE_CODE_200);
		SpringMvcUtil.renderJson(response, info);
		return;
	}
	
	@RequestMapping("updateModule")
	public void updateModule(Module module,HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		if(StringUtils.isBlank(module.getName()) || module.getName().length() > 50){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("模块名字不合法!");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		
		if(moduleService.getModule(module.getId(), null) == null){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("模块不存在!");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		
		moduleService.updateModule(module);
		info.setCode(Constants.RESPONSE_CODE_200);
		SpringMvcUtil.renderJson(response, info);
		return;
		
	}
	
	@RequestMapping("deleteModule")
	public void deleteModule(Integer id,HttpServletRequest request,HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		if (moduleService.getModule(id, 2) == null){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("模块不存在!");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		String path = request.getSession().getServletContext().getRealPath(""); 
		moduleService.deleteModule(id, path);
		info.setCode(Constants.RESPONSE_CODE_200);
		SpringMvcUtil.renderJson(response, info);
		return; 
	}
	
	
}
