package com.fire.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fire.constant.Constants;
import com.fire.model.AjaxResponseInfo;
import com.fire.model.Article;
import com.fire.model.PageBean;
import com.fire.service.ArticleService;
import com.fire.util.SpringMvcUtil;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("addArticle")
	public void addArticle(Article article, HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		articleService.addArticle(article);
		info.setCode(Constants.RESPONSE_CODE_200);
		SpringMvcUtil.renderJson(response, info);
		return;
	}
	
	@RequestMapping("updateArticle")
	public void updateArticle(Article article, HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		articleService.updateArticle(article);
		info.setCode(Constants.RESPONSE_CODE_200);
		SpringMvcUtil.renderJson(response, info);
		return;
	}
	
	@RequestMapping("deleteArticle")
	public void deleteArticle(Integer id,HttpServletRequest request, HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		String path = request.getSession().getServletContext().getRealPath("");
		Article article = articleService.getArticle(id);
		if(article == null){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("文章不存在");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		articleService.deleteArticle(article, path);
		info.setCode(Constants.RESPONSE_CODE_200);
		SpringMvcUtil.renderJson(response, info);
		return;
		
	}
	
	@RequestMapping("getArticle")
	public void getArticle(Integer id, HttpServletRequest request,HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		String path = request.getSession().getServletContext().getRealPath("");
		System.out.print(path);
		Article article = articleService.getArticle(id);
		if(article == null){
			info.setCode(Constants.RESPONSE_CODE_400);
			info.setMsg("文章不存在");
			SpringMvcUtil.renderJson(response, info);
			return;
		}
		info.setCode(Constants.RESPONSE_CODE_200);
		info.setData(article);
		SpringMvcUtil.renderJson(response, info);
		return;
	}
	
	@RequestMapping("queryArticle")
	public void queryArticle(Integer moduleId, PageBean<Article> pageBean,HttpServletResponse response){
		AjaxResponseInfo info = new AjaxResponseInfo();
		List<Article> list = articleService.queryArticle(moduleId, pageBean);
		pageBean.setData(list);
		info.setCode(Constants.RESPONSE_CODE_200);
		info.setData(pageBean);
		SpringMvcUtil.renderJson(response, info);
		return;
	}
}
