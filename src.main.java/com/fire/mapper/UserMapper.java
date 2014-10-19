package com.fire.mapper;

import org.apache.ibatis.annotations.Param;

import com.fire.model.User;

public interface UserMapper {
	
	public int updatePassword(@Param("userName")String userName, @Param("password")String password);
	
	public User checkUser(@Param("userName")String userName, @Param("password")String password);
}
