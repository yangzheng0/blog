package com.blog.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.util.ConstraintViolationExceptionHandler;
import com.blog.vo.Response;

@Controller
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private CommentService commentService;

	/*
	 * 评论列表
	 * <p>Title: listComments</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param model
	 * @return
	 */
	@GetMapping
	public String listComments(@RequestParam(value = "blogId", required = true) Long blogId, Model model) {
		Blog blog = blogService.getBlogById(blogId);
		List<Comment> comments = blog.getComments();

		// 判断操作用户是否是评论的所有者
		String commentOwner = "";
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null) {
				commentOwner = principal.getUsername();
			}
		}
		model.addAttribute("commentOwner", commentOwner);
		model.addAttribute("comments", comments);

		return "/userspace/blog :: #mainContainerRepleace";
	}

	/*
	 * 发表评论
	 * <p>Title: createComment</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param commentContent
	 * @return
	 */
	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')") // 指定角色权限才能操作方法
	public ResponseEntity<Response> createComment(Long blogId, String commentContent) {

		try {
			blogService.createComment(blogId, commentContent);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteBlog(@PathVariable("id") Long id, Long blogId) {

		boolean isOwner = false;
		User user = commentService.getCommentById(id).getUser();

		// 判断操作用户是否是博客的所有者
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && !SecurityContextHolder
						.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
			User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal != null && user.getUsername().equals(principal.getUsername())) {
				isOwner = true;
			}
		}

		if (!isOwner) {
			return ResponseEntity.ok().body(new Response(false, "没有操作权限"));
		}

		try {
			blogService.removeComment(blogId, id);
			commentService.removeComment(id);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}
}
