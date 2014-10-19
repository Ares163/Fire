package com.fire.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.fire.mapper.MessageMapper;
import com.fire.model.Message;
import com.fire.model.PageBean;

@Repository("messageDao")
public class MessageDao extends SqlSessionDaoSupport{
	@Autowired
	private MessageMapper messageMapper;

	@SuppressWarnings("unchecked")
	public List<Message> queryMessage(PageBean<Message> pageBean) throws DataAccessException {
		if (pageBean != null) {
			Integer totalResults = messageMapper.getMessageCount();
			pageBean.setTotalResults(totalResults);
			int skipResults = pageBean.getStartRownum() - 1;
			int maxResults = pageBean.getPageSize();
			return (List<Message>) this.getSqlSession().selectList("queryMessage", null, new RowBounds(skipResults, maxResults));
		}
		return (List<Message>) this.getSqlSession().selectList("queryMessage", null);
	}
}
