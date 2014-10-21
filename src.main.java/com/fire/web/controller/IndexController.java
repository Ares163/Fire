package com.fire.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fire.model.Article;
import com.fire.model.Message;
import com.fire.model.Module;
import com.fire.model.PageBean;
import com.fire.service.IndexService;
import com.fire.service.MessageService;
import com.fire.service.ModuleService;

@Controller
public class IndexController {
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="index")
	public String index(HttpSession session){
		List<Module> mList = moduleService.queryAllModule();
		Map<Integer, List<Article>> articles = indexService.queryIndexArticle(mList);
		
		session.setAttribute("modules", mList);
		session.setAttribute("articles", articles);
		
		PageBean<Message> pageBean = new PageBean<Message>();
		pageBean.setPageSize(10);
		List<Message> messages = messageService.queryMessage(pageBean);
		
		session.setAttribute("messages", messages);
		
		return "index";
	}
	
	@RequestMapping(value="login")
	public String login(){
		return "login";
	}
	
	public String checkUser(String userName, String password, HttpSession session, HttpServletResponse response){
		return null;
	}
}
