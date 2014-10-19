package com.fire.model;

import java.util.List;

/**
 * 模块
 * 
 * @author hanhui
 * 
 */
public class Module {
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer level;
	private List<Module> subModuleList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Module> getSubModuleList() {
		return subModuleList;
	}

	public void setSubModuleList(List<Module> subModuleList) {
		this.subModuleList = subModuleList;
	}
}
