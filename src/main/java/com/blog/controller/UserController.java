package com.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.blog.entity.Authority;
import com.blog.entity.User;
import com.blog.service.AuthorityService;
import com.blog.service.UserService;
import com.blog.util.ConstraintViolationExceptionHandler;
import com.blog.vo.Response;

@RestController
@RequestMapping("/users")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")// 指定角色权限才能操作方法
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping
	public ModelAndView list(
			@RequestParam(value="async",required=false) boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0")int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize,
			@RequestParam(value="name",required=false,defaultValue="")String name,
			Model model){
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<User> page = userService.listUsersByNameLike(name, pageable);
		List<User> list = page.getContent();
		model.addAttribute("page",page);
		model.addAttribute("userList",list);
		return new ModelAndView("/users/list","userModel",model);
	}
	
	@GetMapping("/add")
	public ModelAndView createForm(Model model){
		model.addAttribute("user", new User(null, null, null, null));
		return new ModelAndView("users/add", "userModel", model);
	}

	
	/*
	 * 删除用户
	 * <p>Title: delete</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @param model
	 * @return
	 */
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model){
		try {
			userService.removeUser(id);
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}
		return ResponseEntity.ok().body(new Response(true, "处理成功"));
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView modifyForm(@PathVariable("id") Long id, Model model){
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return new ModelAndView("users/edit", "userModel", model);
	}
	
	
	@GetMapping
	public String test(){
		
		return "成功";
	}
	
}
