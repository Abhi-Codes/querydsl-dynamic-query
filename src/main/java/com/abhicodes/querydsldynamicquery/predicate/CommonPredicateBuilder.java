package com.abhicodes.querydsldynamicquery.predicate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.abhicodes.querydsldynamicquery.utils.SearchCriteria;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

/**
 * The Class CommonPredicateBuilder.
 *
 * @param <T> the generic type
 */
public class CommonPredicateBuilder<T> {

	private final Class<T> t;

	private final String entityVariable;

	private List<SearchCriteria> criterias;

	private Map<String, String> replaceKeyMap;

	/**
	 * Instantiates a new common predicate builder.
	 *
	 * @param t the t
	 */
	public CommonPredicateBuilder(Class<T> t) {
		this.t = t;
		this.entityVariable = getEntityVariable(t.getSimpleName());
		this.criterias = new ArrayList<>();
		this.replaceKeyMap = new HashMap<>();
	}

	/**
	 * Replace key map.
	 *
	 * @param replaceKeyMap the replace key map
	 * @return the common predicate builder
	 */
	public CommonPredicateBuilder<T> replaceKeyMap(Map<String, String> replaceKeyMap) {
		this.replaceKeyMap = replaceKeyMap;
		return this;
	}

	/**
	 * And.
	 *
	 * @param criteria the criteria
	 * @return the common predicate builder
	 */
	public CommonPredicateBuilder<T> and(SearchCriteria criteria) {
		if (null != criteria) {
			this.criterias.add(criteria);
		}
		return this;
	}

	/**
	 * And.
	 *
	 * @param criterias the criterias
	 * @return the common predicate builder
	 */
	public CommonPredicateBuilder<T> and(List<SearchCriteria> criterias) {
		if (!CollectionUtils.isEmpty(criterias)) {
			this.criterias.addAll(criterias);
		}
		return this;
	}

	/**
	 * Builds the.
	 *
	 * @return the boolean expression
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BooleanExpression build() {
		BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();
		if (!CollectionUtils.isEmpty(criterias)) {
			List<BooleanExpression> predicates = criterias.stream()
					.map(c -> new CommonPredicate(t, entityVariable).getPredicate(
							replaceKeyMap.getOrDefault(c.getKey(), c.getKey()), c.getOperator(), c.getValue()))
					.filter(Objects::nonNull).collect(Collectors.toList());
			for (BooleanExpression predicate : predicates) {
				booleanExpression = booleanExpression.and(predicate);
			}
		}
		return booleanExpression;
	}

	/**
	 * Gets the entity variable.
	 *
	 * @param simpleClassName the simple class name
	 * @return the entity variable
	 */
	private static String getEntityVariable(String simpleClassName) {
		char[] c = simpleClassName.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		return new String(c);
	}
}