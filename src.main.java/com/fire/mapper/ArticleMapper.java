package com.fire.mapper;

import java.util.List;

import com.fire.model.Article;

public interface ArticleMapper {
	public int addArticle(Article article);
	
	public int updateArticle(Article article);
	
	public Article getArticle(Integer id);
	
	public List<Article> queryArticle(Integer moduleId);
	
	public int getArticleCount(Integer moduleId);
	
	public int deleteArticle(Integer id);
}
