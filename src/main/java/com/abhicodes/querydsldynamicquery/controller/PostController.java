package com.abhicodes.querydsldynamicquery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abhicodes.querydsldynamicquery.domain.PostDTO;
import com.abhicodes.querydsldynamicquery.serviceimpl.PostService;
import com.abhicodes.querydsldynamicquery.vo.PageableResponse;

@RestController
public class PostController extends BaseController {

	@Autowired
	PostService ps;

	@GetMapping("/all-posts")
	public PageableResponse getAllPosts(@SortDefault(sort = "updatedAt", direction = Direction.DESC) Pageable pageable,
			@RequestParam(required = false) String[] filter) {

		Page<PostDTO> pcq = ps.getPostListing(filter, pageable);
		return formatPageResponse(pcq);

	}
}
