package com.fire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fire.mapper.UserMapper;
import com.fire.model.User;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public int updatePassword(String userName, String password){
		return userMapper.updatePassword(userName, password);
	}
	
	public User checkUser(String userName, String password){
		return userMapper.checkUser(userName, password);
	}
}
