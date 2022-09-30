package com.abhicodes.querydsldynamicquery.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhicodes.querydsldynamicquery.domain.PostDTO;
import com.abhicodes.querydsldynamicquery.repository.PostRepository;
import com.abhicodes.querydsldynamicquery.service.BaseService;
import com.abhicodes.querydsldynamicquery.utils.PostUtils;
import com.abhicodes.querydsldynamicquery.utils.SearchCriteria;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class PostService extends BaseService {

	@Autowired
	PostUtils pu;

	@Autowired
	PostRepository pr;

	public Page<PostDTO> getPostListing(String[] filter, Pageable pageable) {
		List<SearchCriteria> criteria = formatSearchCriteria(filter);
		BooleanExpression exp = pu.getPCQFilterExp(criteria);
		PageRequest pageRequest = pu.getCustomizablePage(pageable);
		return pr.findAll(exp, pageRequest).map(pcq -> pu.mapToDTO(pcq));
	}

}
