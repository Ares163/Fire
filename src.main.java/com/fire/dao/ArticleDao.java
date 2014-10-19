package com.fire.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.fire.mapper.ArticleMapper;
import com.fire.model.Article;
import com.fire.model.PageBean;

@Repository("articleDao")
public class ArticleDao extends SqlSessionDaoSupport{
	@Autowired
	private ArticleMapper articleMapper;

	@SuppressWarnings("unchecked")
	public List<Article> queryArticle(Integer moduleId, PageBean<Article> pageBean) throws DataAccessException {
		if (pageBean != null) {
			Integer totalResults = articleMapper.getArticleCount(moduleId);
			pageBean.setTotalResults(totalResults);
			int skipResults = pageBean.getStartRownum() - 1;
			int maxResults = pageBean.getPageSize();
			return (List<Article>) this.getSqlSession().selectList("queryArticle", moduleId, new RowBounds(skipResults, maxResults));
		}
		return (List<Article>) this.getSqlSession().selectList("queryArticle", moduleId);
	}
}
