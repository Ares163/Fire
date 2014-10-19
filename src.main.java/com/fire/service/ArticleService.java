package com.fire.service;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fire.dao.ArticleDao;
import com.fire.mapper.ArticleMapper;
import com.fire.model.Article;
import com.fire.model.PageBean;
import com.fire.util.FileUtil;

@Service("articleService")
public class ArticleService {
	private final static Logger log = Logger.getLogger(ArticleService.class);
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleMapper articleMapper;
	
	public List<Article> queryArticle(Integer moduleId, PageBean<Article> pageBean){
		return articleDao.queryArticle(moduleId, pageBean);
	}
	
	public int addArticle(Article article){
		return articleMapper.addArticle(article);
	}
	
	public int updateArticle(Article article){
		return articleMapper.updateArticle(article);
	}
	
	public Article getArticle(Integer id){
		return articleMapper.getArticle(id);
	}
	
	public int deleteArticle(Article a, String path){
		
		articleMapper.deleteArticle(a.getId());
		if(StringUtils.isNotBlank(a.getThumbnail())){
			String filePath = path + File.separator +a.getThumbnail();
			try{
				FileUtil.delete(filePath);
			}catch(Exception e){
				log.error("删除文件失败"+filePath, e);
			}
		}
		return 1;
	}
	
}
