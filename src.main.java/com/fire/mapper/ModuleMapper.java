package com.fire.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fire.model.Module;

public interface ModuleMapper {
	
	public int addModule(Module module);
	
	public int updateModule(Module module);
	
	public int deleteModule(Integer id);
	
	public List<Module> queryModule(Module module);
	
	public Module getModule(@Param("id")Integer id, @Param("level")Integer level);
}
