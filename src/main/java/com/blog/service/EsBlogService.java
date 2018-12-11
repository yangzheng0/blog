package com.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.entity.User;
import com.blog.entity.es.EsBlog;
import com.blog.vo.TagVO;

public interface EsBlogService {
	
	/*
	 * 删除
	 */
	void removeEsBlog(String id);
	
	/*
	 * 更新
	 */
	EsBlog updateEsBlog(EsBlog esBlog);
	
	/*
	 * 根据id获取Blog
	 */
	EsBlog getEsBlogByBlogId(Long blogId);
	
	/*
	 * 最新博客列表,分页
	 */
	Page<EsBlog> listNewestEsBlogs(String keyword,Pageable pageable);
	
	/*
	 * 最热博客列表,分页
	 */
	Page<EsBlog> listHotestEsBlogs(String keyword,Pageable pageable);
	
	/*
	 * 博客列表,分页
	 */
	Page<EsBlog> listEsBlogs(Pageable pageable);
	
	/*
	 * 最新前五
	 */
	List<EsBlog> listTop5NewestEsBlogs();
	
	/*
	 * 最热前五
	 */
	List<EsBlog> listTop5HotestEsBlogs();
	
	/*
	 * 最热标签前30
	 */
	List<TagVO> listTop30Tags();
	
	/*
	 * 最热前12用户
	 */
	List<User> listTop12Users();
}
