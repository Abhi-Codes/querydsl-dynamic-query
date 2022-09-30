package com.abhicodes.querydsldynamicquery.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abhicodes.querydsldynamicquery.entity.Post;
import com.abhicodes.querydsldynamicquery.predicate.CommonPredicateBuilder;
import com.abhicodes.querydsldynamicquery.utils.SearchCriteria;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * The Class BaseService.
 */
@Service
public class BaseService {

	/**
	 * Format search criteria.
	 *
	 * @param filter the filter
	 * @return the list
	 */
	public List<SearchCriteria> formatSearchCriteria(String[] filter) {
		List<SearchCriteria> criterias = new ArrayList<>();
		if (null != filter) {
			Collection<SearchCriteria> collect = Arrays.asList(filter).parallelStream().map(this::validateFilterPattern)
					.collect(Collectors.toList());
			criterias.addAll(collect);
		}
		return criterias;
	}

	/**
	 * Gets the and boolean expression.
	 *
	 * @param criterias the criterias
	 * @param t         the t
	 * @return the and boolean expression
	 */
	public BooleanExpression getAndBooleanExpression(List<SearchCriteria> criterias, Class<Post> t) {
		return new CommonPredicateBuilder<>(t).and(criterias).build();
	}

	/**
	 * Validate filter pattern.
	 *
	 * @param filter the filter
	 * @return the search criteria
	 */
	private SearchCriteria validateFilterPattern(String filter) {
		final Pattern pattern = Pattern.compile("([\\w.]+?)(:|<|>|<=|>=|%|-|\\(\\))([\\w\\s\\(\\),.:-]+?)\\|");
		Matcher m = pattern.matcher(filter + "|");
		if (m.find()) {
			return SearchCriteria.builder().key(m.group(1)).operator(m.group(2)).value(m.group(3)).build();
		} else {
			throw new RuntimeException("Invalid Filter format");
		}
	}

	@SuppressWarnings("unused")
	protected String generateMessage(String pattern, Object... param) {
		return MessageFormat.format(pattern, param);
	}

}
