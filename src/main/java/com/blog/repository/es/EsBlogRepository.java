package com.blog.repository.es;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.blog.entity.es.EsBlog;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
	
	/*
	 *模糊查询
	 */
	Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,String Summary,String content,String tags,Pageable pageable);

	EsBlog findByBlogId(Long blogId);
}
