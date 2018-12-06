package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {

}
