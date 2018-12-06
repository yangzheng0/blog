package com.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.entity.Vote;
import com.blog.repository.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.BlogService#saveBlog(com.waylau.spring.boot.blog.domain.Blog)
	 */
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.BlogService#removeBlog(java.lang.Long)
	 */
	@Transactional
	@Override
	public void removeBlog(Long id) {
		blogRepository.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.BlogService#updateBlog(com.waylau.spring.boot.blog.domain.Blog)
	 */
	@Transactional
	@Override
	public Blog updateBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.blog.service.BlogService#getBlogById(java.lang.Long)
	 */
	@Override
	public Blog getBlogById(Long id) {
		return blogRepository.findOne(id);
	}

	@Override
	public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
		// 模糊查询
		title = "%" + title + "%";
		Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
		return blogs;
	}

	@Override
	public void readingIncrease(Long id) {
		Blog blog = blogRepository.findOne(id);
		blog.setReadSize(blog.getReadSize()+1);
		blogRepository.save(blog);
	}

	/*
	 * 发表评论
	 *(non-Javadoc)  
	 * <p>Title: createComment</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param commentContent
	 * @return  
	 * @see com.blog.service.BlogService#createComment(java.lang.Long, java.lang.String)
	 */
	@Override
	public Blog createComment(Long blogId, String commentContent) {
		Blog originalBlog = blogRepository.findOne(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Comment comment = new Comment(commentContent,user);
		originalBlog.addComment(comment);
		return blogRepository.save(originalBlog);
	}

	/*
	 * 删除评论
	 *(non-Javadoc)  
	 * <p>Title: removeComment</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param commentId  
	 * @see com.blog.service.BlogService#removeComment(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void removeComment(Long blogId, Long commentId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeComment(commentId);
		blogRepository.save(originalBlog);
	}

	/*
	 * 点赞
	 *(non-Javadoc)  
	 * <p>Title: createVote</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @return  
	 * @see com.blog.service.BlogService#createVote(java.lang.Long)
	 */
	@Override
	public Blog createVote(Long blogId) {
		Blog origionalBlog = blogRepository.findOne(blogId);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Vote vote = new Vote(user);
		boolean isExist = origionalBlog.addVote(vote);
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		return blogRepository.save(origionalBlog);
	}

	/*
	 * 取消点赞
	 *(non-Javadoc)  
	 * <p>Title: removeVote</p>  
	 * <p>Description: </p>  
	 * @param blogId
	 * @param voteId  
	 * @see com.blog.service.BlogService#removeVote(java.lang.Long, java.lang.Long)
	 */
	@Override
	public void removeVote(Long blogId, Long voteId) {
		Blog originalBlog = blogRepository.findOne(blogId);
		originalBlog.removeVote(voteId);
		blogRepository.save(originalBlog);
	}

}
