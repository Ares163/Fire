package com.fire.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fire.model.Article;
import com.fire.model.Module;
import com.fire.model.PageBean;

@Service("indexService")
public class IndexService {
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ArticleService articleService;
	
	public Map<Integer,List<Article>> queryIndexArticle(List<Module> moduleList){
		Map<Integer,List<Article>> map = new HashMap<Integer,List<Article>>();
		PageBean<Article> pageBean = new PageBean<Article>();
		pageBean.setPageSize(10);
		pageBean.setCurrentPage(1);
		for(Module m:moduleList){
			if(m.getSubModuleList() != null && m.getSubModuleList().size() > 0){
				for(Module m1:moduleList){
					List<Article> list = articleService.queryArticle(m1.getId(), pageBean);
					map.put(m1.getId(), list);
				}
			}else{
				List<Article> list = articleService.queryArticle(m.getId(), pageBean);
				map.put(m.getId(), list);
			}
		}
		
		return map;
	}
}
