package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.entity.Vote;
import com.blog.repository.VoteRepository;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRepository;

	@Override
	public Vote getVoteById(Long id) {
		return voteRepository.findOne(id);
	}

	@Override
	@Transactional
	public void removeVote(Long id) {
		voteRepository.delete(id);
	}

}
