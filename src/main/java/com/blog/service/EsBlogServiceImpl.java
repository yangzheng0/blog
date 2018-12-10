package com.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.entity.es.EsBlog;
import com.blog.repository.es.EsBlogRepository;
import com.blog.vo.TagVO;

@Service
public class EsBlogServiceImpl implements EsBlogService {
	
	@Autowired
	private EsBlogRepository esBlogRepository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Autowired
	private UserService userService;
	
	private static final Pageable TOP_5_PAGEABLE = new PageRequest(0, 5);
	private static final String EMPTY_KEYWORD = "";

	@Override
	public void removeEsBlog() {

	}

	@Override
	public EsBlog updateEsBlog(EsBlog esBlog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getEsBlogByBlogId(Long blogId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<EsBlog> listEsBlogs(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EsBlog> listTop5NewestEsBlogs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EsBlog> listTop5HotestEsBlogs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TagVO> listTop30Tags() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> listTop12Users() {
		// TODO Auto-generated method stub
		return null;
	}

}
