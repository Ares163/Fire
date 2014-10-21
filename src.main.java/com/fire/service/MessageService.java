package com.fire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fire.dao.MessageDao;
import com.fire.mapper.MessageMapper;
import com.fire.model.Message;
import com.fire.model.PageBean;

@Service("messageServie")
public class MessageService {
	@Autowired
	private MessageMapper messageMapper;
	@Autowired
	private MessageDao messageDao;
	public int addMessage(Message message){
		return messageMapper.addMessage(message);
	}
	public int updateMessage(Message message){
		return messageMapper.updateMessage(message);
	}
	public int deleteMessage(Integer id){
		return messageMapper.deleteMessage(id);
	}
	public List<Message> queryMessage(PageBean<Message> pageBean){
		return messageDao.queryMessage(pageBean);
	}
}
