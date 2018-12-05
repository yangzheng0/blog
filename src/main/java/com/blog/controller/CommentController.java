package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.vo.Response;

@Controller
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private BlogService blogService;

	@Autowired
	private CommentService commentService;

	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")// 指定角色权限才能操作方法
	public ResponseEntity<Response> createComment(Long blogId,String commentContent){
		
		
		
		return null;
		
	}
}
