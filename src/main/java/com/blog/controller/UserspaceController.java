package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.service.BlogService;

@Controller
@RequestMapping("/u")
public class UserspaceController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username, Model model) {
		User  user = (User)userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return "redirect:/u/" + username + "/blogs";
	}
	
	@GetMapping("/{username}/profile")
	@PreAuthorize("authentication.name.equals(#username)") 
	public ModelAndView profile(@PathVariable("username") String username, Model model) {
		User  user = (User)userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/profile", "userModel", model);
	}
	
	@GetMapping("/{username}/blogs")
	public String listBlogsByOrder(
			@PathVariable("username")String username,
			@RequestParam(value="order",required=false,defaultValue="new")String order,
			@RequestParam(value="category",required=false)Long category,
			@RequestParam(value="keyword",required=false,defaultValue="")String keyword,
			@RequestParam(value="async",required=false)boolean async,
			@RequestParam(value="pageIndex",required=false,defaultValue="0")int pageIndex,
			@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize,
			Model model){
		System.out.println("username="+username);
		
		User user = (User)userDetailsService.loadUserByUsername(username);
		model.addAttribute("user", user);
		
		if(category !=null){
			System.out.println("category:"+category);
			System.out.print("selflink:" + "redirect:/u/"+ username +"/blogs?category="+category);
			return "/u";
			
		}
		Page<Blog> page=null;
		if(order.equals("hot")){
			//最热查询
			Sort sort = new Sort(Direction.DESC,"reading","comments","likes");
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByTitleLikeAndSort(user, keyword, pageable);
		}
		
		if (order.equals("new")) { // 最新查询
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.listBlogsByTitleLike(user, keyword, pageable);
		}
 
		
		List<Blog> list = page.getContent();	// 当前所在页面数据列表
		
		model.addAttribute("order", order);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		return (async==true?"/userspace/u :: #mainContainerRepleace":"/userspace/u");
	}
	
}
