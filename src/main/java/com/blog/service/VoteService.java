package com.blog.service;

import com.blog.entity.Vote;

public interface VoteService {
	
	/*
	 * 根据id获取Vote
	 * <p>Title: getVoteById</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	Vote getVoteById(Long id);
	
	/*
	 * 删除Vote
	 * <p>Title: removeVote</p>  
	 * <p>Description: </p>  
	 * @param id
	 */
	void removeVote(Long id);
}
