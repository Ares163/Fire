package com.fire.mapper;

import java.util.List;

import com.fire.model.Message;

public interface MessageMapper {
	public int addMessage(Message message);
	public int updateMessage(Message message);
	public List<Message> queryMessage();
	public int getMessageCount();
	public int deleteMessage(Integer id);
}
