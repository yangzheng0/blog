package com.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
