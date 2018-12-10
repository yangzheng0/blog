package com.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/blogs")
public class BlogController {
	
	@GetMapping
	public String listBlogs(@RequestParam(value="order",required=false,defaultValue="new")String order,
							@RequestParam(value="tag",required=false)Long tag,
							@RequestParam(value="async",required=false) boolean async){
		System.out.println("order:"+order+";tag"+tag);
		return (async==true?"/index :: #mainContainerRepleace":"/index");
	}
}
