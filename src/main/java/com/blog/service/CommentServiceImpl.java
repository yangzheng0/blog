package com.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.repository.CommentRepository;
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment getCommentById(Long id) {
		return commentRepository.findOne(id);
	}

	@Override
	@Transactional
	public void removeComment(Long id) {
		commentRepository.delete(id);
	}

}
