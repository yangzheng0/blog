package com.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blog.entity.Blog;
import com.blog.entity.User;

public interface BlogService {
	/**
	 * 保存Blog
	 * @param Blog
	 * @return
	 */
	Blog saveBlog(Blog blog);
	
	/**
	 * 删除Blog
	 * @param id
	 * @return
	 */
	void removeBlog(Long id);
	
	/**
	 * 更新Blog
	 * @param Blog
	 * @return
	 */
	Blog updateBlog(Blog blog);
	
	/**
	 * 根据id获取Blog
	 * @param id
	 * @return
	 */
	Blog getBlogById(Long id);
	
	/**
	 * 根据用户名进行分页模糊查询（最新）
	 * @param user
	 * @return
	 */
	Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);
	
	/**
	 * 根据用户名进行分页模糊查询（最热）
	 * @param user
	 * @return
	 */
	Page<Blog> listBlogsByTitleLikeAndSort(User suser, String title, Pageable pageable);
	
	/**
	 * 阅读量递增
	 * @param id
	 */
	void readingIncrease(Long id);
	
	/*
	 * 发表评论
	 * <p>Title: createComment</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	Blog createComment(Long blogId,String commentContent);

	/*
	 * 删除评论
	 * <p>Title: removeComment</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param commentId
	 */
	void removeComment(Long blogId,Long commentId);
	
	/*
	 * 点赞
	 * <p>Title: createVote</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @return
	 */
	Blog createVote(Long blogId);
	
	/*
	 * 取消点赞`
	 * <p>Title: removeVote</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param voteId
	 */
	void removeVote(Long blogId,Long voteId);
}
