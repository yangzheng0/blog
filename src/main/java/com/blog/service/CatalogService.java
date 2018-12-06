package com.blog.service;

import java.util.List;

import com.blog.entity.Catalog;
import com.blog.entity.User;

public interface CatalogService {
	
	/*
	 * 保存catalog
	 * <p>Title: saveCatalog</p>  
	 * <p>Description: </p>  
	 * @param catalog
	 * @return
	 */
	Catalog saveCatalog(Catalog catalog);
	
	/*
	 * 删除catalog
	 * <p>Title: removeCatalog</p>  
	 * <p>Description: </p>  
	 * @param id
	 */
	void removeCatalog(Long id);
	
	/*
	 * 根据id获取catalog
	 * <p>Title: getCatalogById</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	Catalog getCatalogById(Long id);
	
	/*
	 * 列表
	 * <p>Title: listCatalogs</p>  
	 * <p>Description: </p>  
	 * @param user
	 * @return
	 */
	List<Catalog> listCatalogs(User user);
}
