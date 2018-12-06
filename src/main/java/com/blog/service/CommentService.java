package com.blog.service;

import com.blog.entity.Comment;

public interface CommentService {
	
	/*
	 * 根据id获取评论
	 * <p>Title: getCommentById</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	Comment getCommentById(Long id);
	
	/*
	 * 删除评论
	 * <p>Title: removeComment</p>  
	 * <p>Description: </p>  
	 * @param id
	 */
	void removeComment(Long id);
}
