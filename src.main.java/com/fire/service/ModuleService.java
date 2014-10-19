package com.fire.service;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fire.mapper.ArticleMapper;
import com.fire.mapper.ModuleMapper;
import com.fire.model.Article;
import com.fire.model.Module;
import com.fire.util.FileUtil;

@Service("moduleService")
public class ModuleService {
	private final static Logger log = Logger.getLogger(ModuleService.class);
	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private ArticleMapper articleMapper;
	
	public int addModule(Module module){
		return moduleMapper.addModule(module);
	}
	
	public int updateModule(Module module){
		return moduleMapper.updateModule(module);
	}
	
	public int deleteModule(Integer id, String path){
		moduleMapper.deleteModule(id);
		
		List<Article> list = articleMapper.queryArticle(id);
		for(Article a:list){
			articleMapper.deleteArticle(a.getId());
			if(StringUtils.isNotBlank(a.getThumbnail())){
				String filePath = path + File.separator +a.getThumbnail();
				try{
					FileUtil.delete(filePath);
				}catch(Exception e){
					log.error("删除文件失败"+filePath, e);
				}
			}
		}
		
		return 1;
	}
	
	public List<Module> queryModule(Module module){
		return moduleMapper.queryModule(module);
	}
	
	public List<Module> queryAllModule(){
		Module module = new Module();
		module.setLevel(1);
		List<Module> moduleList = queryModule(module);
		for(Module m:moduleList){
			Module query = new Module();
			query.setParentId(m.getId());
			List<Module> mList = queryModule(query);
			if(mList != null && mList.size() >0){
				m.setSubModuleList(mList);
			}
		}
		return moduleList;
	}
	
	public Module getModule(Integer id, Integer level){
		return moduleMapper.getModule(id, level);
	}
}
