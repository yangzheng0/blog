package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.entity.User;
import com.blog.entity.es.EsBlog;
import com.blog.service.EsBlogService;
import com.blog.vo.TagVO;


@Controller
@RequestMapping("/blogs")
public class BlogController {
	
	@Autowired
	private EsBlogService esBlogService;
	
	@GetMapping
	public String listBlogs(@RequestParam(value="order",required=false,defaultValue="new")String order,
							@RequestParam(value="keyword",required=false,defaultValue="")String keyword,
							@RequestParam(value="pageIndex",required=false,defaultValue="0")int pageIndex,
							@RequestParam(value="pageSize",required=false,defaultValue="10")int pageSize,
							@RequestParam(value="async",required=false) boolean async,
							Model model){
		Page<EsBlog> page = null;
		List<EsBlog> list = null;
		
		boolean isEmpty = true; // 系统初始化时，没有博客数据
		
		try {
			if (order.equals("hot")) {
				//最热查询
				Sort sort = new Sort(Direction.DESC,"readSize","commentSize","voteSize","createTime");
				Pageable pageable = new PageRequest(pageIndex, pageSize);
				page = esBlogService.listHotestEsBlogs(keyword, pageable);
			}else if(order.equals("new")){
				//最新查询
				new Sort(Direction.DESC,"createTime");
				Pageable pageable = new PageRequest(pageIndex, pageSize);
				page = esBlogService.listNewestEsBlogs(keyword, pageable);
			}
			isEmpty = false;
		} catch (Exception e) {
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = esBlogService.listEsBlogs(pageable);
		}
		
		//当前所在页面数据列表
		list = page.getContent();
		
		model.addAttribute("order", order);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		
		if (!async && !isEmpty) {
			List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
			model.addAttribute("newest", newest);
			List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
			model.addAttribute("tags", hotest);
			List<TagVO> tags = esBlogService.listTop30Tags();
			model.addAttribute("tags", tags);
			List<User> users = esBlogService.listTop12Users();
			model.addAttribute("users", users);
		}
		
		
		return (async==true?"/index :: #mainContainerRepleace":"/index");
	}
	
	@GetMapping("/newest")
	public String listNewestEsBlogs(Model model){
		List<EsBlog> newest = esBlogService.listTop5NewestEsBlogs();
		model.addAttribute("newest", newest);
		return "newest";
	}
	
	@GetMapping("/hotest")
	public String listHotestEsBlotgs(Model model){
		List<EsBlog> hotest = esBlogService.listTop5HotestEsBlogs();
		model.addAttribute("hotest", hotest);
		return "hotest";
	}
	
}
