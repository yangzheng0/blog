package com.blog.service;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator.AggregateBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.entity.es.EsBlog;
import com.blog.repository.es.EsBlogRepository;
import com.blog.vo.Response;
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
	public void removeEsBlog(String id) {
		esBlogRepository.delete(id);
	}

	@Override
	public EsBlog updateEsBlog(EsBlog esBlog) {
		return esBlogRepository.save(esBlog);
	}

	@Override
	public EsBlog getEsBlogByBlogId(Long blogId) {
		return esBlogRepository.findByBlogId(blogId);
	}

	/*
	 * 模糊查询最新
	 *(non-Javadoc)  
	 * <p>Title: listNewestEsBlogs</p>  
	 * <p>Description: </p>  
	 * @param keyword
	 * @param pageable
	 * @return  
	 * @see com.blog.service.EsBlogService#listNewestEsBlogs(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) {
		Page<EsBlog> pages = null;
		Sort sort = new Sort(Direction.DESC,"createTime"); 
		if (pageable.getSort() == null) {
			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}
 
		pages = esBlogRepository.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,keyword,keyword,keyword, pageable);
 
		return pages;
	}

	/*
	 * 模糊查询最热
	 *(non-Javadoc)  
	 * <p>Title: listHotestEsBlogs</p>  
	 * <p>Description: </p>  
	 * @param keyword
	 * @param pageable
	 * @return  
	 * @see com.blog.service.EsBlogService#listHotestEsBlogs(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) {
		Sort sort = new Sort(Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");
		if (pageable.getSort() == null) {
			pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
		}

		return esBlogRepository
				.findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(keyword,
						keyword, keyword, keyword, pageable);
	}

	/*
	 * 直接查询列表
	 * <p>Title: listEsBlogs</p>  
	 * <p>Description: </p>  
	 * @param pageable
	 * @return  
	 * @see com.blog.service.EsBlogService#listEsBlogs(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EsBlog> listEsBlogs(Pageable pageable) {
		return esBlogRepository.findAll(pageable);
	}

	/*
	 * 查询最新前五
	 * <p>Title: listTop5NewestEsBlogs</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.blog.service.EsBlogService#listTop5NewestEsBlogs()
	 */
	@Override
	public List<EsBlog> listTop5NewestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	/*
	 * 查询最热前五
	 * <p>Title: listTop5HotestEsBlogs</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.blog.service.EsBlogService#listTop5HotestEsBlogs()
	 */
	@Override
	public List<EsBlog> listTop5HotestEsBlogs() {
		Page<EsBlog> page = this.listHotestEsBlogs(EMPTY_KEYWORD, TOP_5_PAGEABLE);
		return page.getContent();
	}

	/*
	 * 查询最热标签前30
	 * <p>Title: listTop30Tags</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see com.blog.service.EsBlogService#listTop30Tags()
	 */
	@Override
	public List<TagVO> listTop30Tags() {
		List<TagVO> list = new ArrayList<>();
		// given
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("tags").field("tags").order(Terms.Order.count(false)).size(30))
				.build();
		// when
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		
		StringTerms modelTerms =  (StringTerms)aggregations.asMap().get("tags"); 
	        
        Iterator<Bucket> modelBucketIt = modelTerms.getBuckets().iterator();
        while (modelBucketIt.hasNext()) {
            Bucket actiontypeBucket = modelBucketIt.next();
 
            list.add(new TagVO(actiontypeBucket.getKey().toString(),
                    actiontypeBucket.getDocCount()));
        }
		return list;
	}

	/*
	 * 列出用户前12
	 * <p>Title: listTop12Users</p>  
	 * <p>Description: </p>  
	 * @return
	 */
	@Override
	public List<User> listTop12Users() {
		List<String> usernamelist = new ArrayList<>();
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchAllQuery())
				.withSearchType(SearchType.QUERY_THEN_FETCH)
				.withIndices("blog").withTypes("blog")
				.addAggregation(terms("users").field("username").order(Terms.Order.count(false)).size(12))
				.build();
		
		//when
		Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {

			@Override
			public Aggregations extract(SearchResponse response) {
				return response.getAggregations();
			}
		});
		
		StringTerms modelTerms = (StringTerms) aggregations.asMap().get("users");
		
		Iterator<Bucket> modelBuchetIt = modelTerms.getBuckets().iterator();
		
		while (modelBuchetIt.hasNext()) {
			Bucket actiontypeBucket = modelBuchetIt.next();
			String username = actiontypeBucket.getKey().toString();
			usernamelist.add(username);
		}
		List<User> list = userService.listUsersByUsernames(usernamelist);
		return list;
	}

}
