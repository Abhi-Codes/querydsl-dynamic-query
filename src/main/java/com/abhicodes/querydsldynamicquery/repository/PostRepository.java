package com.abhicodes.querydsldynamicquery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.abhicodes.querydsldynamicquery.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, QuerydslPredicateExecutor<Post> {

}
