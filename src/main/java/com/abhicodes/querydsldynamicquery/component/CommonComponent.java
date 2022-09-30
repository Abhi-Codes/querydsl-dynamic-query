package com.abhicodes.querydsldynamicquery.component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonComponent {

	@Autowired
	ObjectMapper objectMapper;

	/**
	 * Customize Pagination
	 * 
	 * @param pageable
	 * @param replaceKeyMap
	 * @return
	 */
	public PageRequest customizePagable(Pageable pageable, Map<String, String> replaceKeyMap) {
		List<Order> newSort = Optional.ofNullable(pageable.getSort()).map(Sort::stream).orElseGet(Stream::empty)
				.map(o -> Order
						.by(Optional.ofNullable(replaceKeyMap).map(m -> m.get(o.getProperty())).orElse(o.getProperty()))
						.with(o.getDirection()).with(NullHandling.NULLS_LAST))
				.collect(Collectors.toList());
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(newSort));
	}

}