package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blog.entity.Catalog;
import com.blog.entity.User;
import com.blog.repository.CatalogRepository;

public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository;

	@Override
	public Catalog saveCatalog(Catalog catalog) {
		return null;
	}

	@Override
	public void removeCatalog(Long id) {
		
	}

	@Override
	public Catalog getCatalogById(Long id) {
		return null;
	}

	@Override
	public List<Catalog> listCatalogs(User user) {
		return null;
	}


}
