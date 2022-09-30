package com.abhicodes.querydsldynamicquery.predicate;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import lombok.SneakyThrows;

/**
 * The Class CommonPredicate.
 *
 * @param <T> the generic type
 */
public class CommonPredicate<T> {

	private final Class<? extends T> type;
	private final String entityVariable;

	/**
	 * Instantiates a new common predicate.
	 *
	 * @param type           the type
	 * @param entityVariable the entity variable
	 */
	public CommonPredicate(Class<? extends T> type, String entityVariable) {
		this.type = type;
		this.entityVariable = entityVariable;
	}

	/**
	 * Gets the predicate.
	 *
	 * @param key      the key
	 * @param operator the operator
	 * @param value    the value
	 * @return the predicate
	 */
	public BooleanExpression getPredicate(String key, String operator, String value) {
		PathBuilder<T> entityPath = new PathBuilder<>(type, entityVariable);
		return getPredicate(key, operator, value, entityPath, type);
	}

	/**
	 * Gets the predicate.
	 *
	 * @param key        the key
	 * @param operator   the operator
	 * @param value      the value
	 * @param entityPath the entity path
	 * @param classType  the class type
	 * @return the predicate
	 */
	private BooleanExpression getPredicate(String key, String operator, String value, PathBuilder<?> entityPath,
			Class<?> classType) {
		boolean isMultiValue = value.contains(",");
		Class<?> propertyType = getPropertyType(classType, key);
		switch (propertyType.getSimpleName()) {
		case "Integer":
			if (isMultiValue) {
				NumberPath<Integer> path = entityPath.getNumber(key, Integer.class);
				Integer[] numValue = Stream.of(value.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
				return getNumberPredicate(path, operator, numValue);
			} else {
				NumberPath<Integer> path = entityPath.getNumber(key, Integer.class);
				Integer numValue = Integer.parseInt(value);
				return getNumberPredicate(path, operator, numValue);
			}
		case "Long":
			if (isMultiValue) {
				NumberPath<Long> path = entityPath.getNumber(key, Long.class);
				Long[] numValue = Stream.of(value.split(",")).map(Long::parseLong).toArray(Long[]::new);
				return getNumberPredicate(path, operator, numValue);
			} else {
				NumberPath<Long> path = entityPath.getNumber(key, Long.class);
				Long numValue = Long.parseLong(value);
				return getNumberPredicate(path, operator, numValue);
			}
		case "Double":
			if (isMultiValue) {
				NumberPath<Double> path = entityPath.getNumber(key, Double.class);
				Double[] numValue = Stream.of(value.split(",")).map(Double::parseDouble).toArray(Double[]::new);
				return getNumberPredicate(path, operator, numValue);
			} else {
				NumberPath<Double> path = entityPath.getNumber(key, Double.class);
				Double numValue = Double.parseDouble(value);
				return getNumberPredicate(path, operator, numValue);
			}
		case "Boolean":
			if (":".equals(operator)) {
				return entityPath.getBoolean(key).eq(Boolean.parseBoolean(value));
			} else {
				throw new RuntimeException("Unsupported Boolean operation");
			}
		case "String":
			return getStringPredicate(key, operator, value, entityPath);
		case "LocalDate":
			return getDatePredicate(key, operator, value, entityPath);
		case "LocalDateTime":
			return getDateTimePredicate(key, operator, value, entityPath);
		default:

			// do nothing
			return null;
		}
	}

	/**
	 * Gets the number predicate.
	 *
	 * @param path     the path
	 * @param operator the operator
	 * @param value    the value
	 * @return the number predicate
	 */
	private BooleanExpression getNumberPredicate(NumberPath<Integer> path, String operator, Integer value) {
		switch (operator) {
		case ":":
			return path.eq(value);
		case ">":
			return path.gt(value);
		case "<":
			return path.lt(value);
		case ">=":
			return path.goe(value);
		case "<=":
			return path.loe(value);
		case "!=":
			return path.notIn(value);
		default:
			return null;
		}
	}

	/**
	 * Gets the number predicate.
	 *
	 * @param path     the path
	 * @param operator the operator
	 * @param value    the value
	 * @return the number predicate
	 */
	private BooleanExpression getNumberPredicate(NumberPath<Double> path, String operator, Double value) {
		switch (operator) {
		case ":":
			return path.eq(value);
		case ">":
			return path.gt(value);
		case "<":
			return path.lt(value);
		case ">=":
			return path.goe(value);
		case "<=":
			return path.loe(value);
		case "!=":
			return path.notIn(value);
		default:
			return null;
		}
	}

	/**
	 * Gets the number predicate.
	 *
	 * @param path     the path
	 * @param operator the operator
	 * @param value    the value
	 * @return the number predicate
	 */
	private BooleanExpression getNumberPredicate(NumberPath<Long> path, String operator, Long value) {
		switch (operator) {
		case ":":
			return path.eq(value);
		case ">":
			return path.gt(value);
		case "<":
			return path.lt(value);
		case ">=":
			return path.goe(value);
		case "<=":
			return path.loe(value);
		default:
			return null;
		}
	}

	/**
	 * Gets the number predicate.
	 *
	 * @param path     the path
	 * @param operator the operator
	 * @param numValue the num value
	 * @return the number predicate
	 */
	private BooleanExpression getNumberPredicate(NumberPath<?> path, String operator, Object[] numValue) {
		Number[] valueArray = (Number[]) numValue;
		switch (operator) {
		case ":":
			return path.in(valueArray);
		case "()":
			return path.between(valueArray[0].doubleValue(), valueArray[1].doubleValue());
		case "!=":
			return path.notIn(valueArray);
		default:
			return null;
		}
	}

	/**
	 * Gets the date predicate.
	 *
	 * @param key        the key
	 * @param operator   the operator
	 * @param value      the value
	 * @param entityPath the entity path
	 * @return the date predicate
	 */
	private BooleanExpression getDatePredicate(String key, String operator, String value, PathBuilder<?> entityPath) {
		DatePath<LocalDate> path = entityPath.getDate(key, LocalDate.class);
		if (value.contains(",")) {
			List<LocalDate> dateValues = Stream.of(value.split(",")).map(LocalDate::parse).collect(Collectors.toList());
			switch (operator) {
			case ":":
				return path.in(dateValues);
			case "()":
				return path.between(dateValues.get(0), dateValues.get(1));
			default:
				return null;
			}
		} else {
			LocalDate dateValue = LocalDate.parse(value);
			switch (operator) {
			case ":":
				return path.eq(dateValue);
			case ">":
				return path.gt(dateValue);
			case "<":
				return path.lt(dateValue);
			case ">=":
				return path.goe(dateValue);
			case "<=":
				return path.loe(dateValue);
			default:
				return null;
			}
		}
	}

	/**
	 * Gets the date time predicate.
	 *
	 * @param key        the key
	 * @param operator   the operator
	 * @param value      the value
	 * @param entityPath the entity path
	 * @return the date time predicate
	 */
	private BooleanExpression getDateTimePredicate(String key, String operator, String value,
			PathBuilder<?> entityPath) {
		DatePath<LocalDateTime> path = entityPath.getDate(key, LocalDateTime.class);
		if (value.contains(",")) {
			List<LocalDate> dateValues = Stream.of(value.split(",")).map(LocalDate::parse).collect(Collectors.toList());
			switch (operator) {
			case ":":
				return path.in(dateValues.parallelStream().map(LocalDate::atStartOfDay).collect(Collectors.toList()));
			case "()":
				return path.between(dateValues.get(0).atStartOfDay(), dateValues.get(1).atTime(LocalTime.MAX));
			default:
				return null;
			}
		} else {
			LocalDate dateValue = LocalDate.parse(value);
			switch (operator) {
			case ":":
				return path.between(dateValue.atStartOfDay(), dateValue.atTime(LocalTime.MAX));
			case ">":
				return path.gt(dateValue.atTime(LocalTime.MAX));
			case "<":
				return path.lt(dateValue.atStartOfDay());
			case ">=":
				return path.goe(dateValue.atStartOfDay());
			case "<=":
				return path.loe(dateValue.atTime(LocalTime.MAX));
			default:
				return null;
			}
		}
	}

	/**
	 * Gets the string predicate.
	 *
	 * @param key        the key
	 * @param operator   the operator
	 * @param value      the value
	 * @param entityPath the entity path
	 * @return the string predicate
	 */
	private BooleanExpression getStringPredicate(String key, String operator, String value, PathBuilder<?> entityPath) {
		StringPath path = entityPath.getString(key);
		if (value.contains(",")) {
			return path.in(Stream.of(value.split(",")).collect(Collectors.toList()));
		}
		switch (operator) {
		case ":":
			return path.equalsIgnoreCase(value);
		case "%":
			return path.startsWithIgnoreCase(value);
		case "-":
			return path.containsIgnoreCase(value);
		default:
			return null;
		}
	}

	/**
	 * Gets the property type.
	 *
	 * @param parent   the parent
	 * @param property the property
	 * @return the property type
	 */
	private Class<?> getPropertyType(Class<?> parent, String property) {
		List<String> propertyList = new LinkedList<>(Arrays.asList(property.split("\\.")));
		return getRecursiveType(parent, propertyList);
	}

	/**
	 * Gets the recursive type.
	 *
	 * @param parent       the parent
	 * @param propertyList the property list
	 * @return the recursive type
	 */
	@SneakyThrows(NoSuchFieldException.class)
	private Class<?> getRecursiveType(Class<?> parent, List<String> propertyList) {
		if (propertyList.size() > 1) {
			Field field = parent.getDeclaredField(propertyList.get(0));
			Class<?> child = field.getType();
			propertyList.remove(propertyList.get(0));
			if ("List".equals(child.getSimpleName())) {
				return child;
//				ParameterizedType type = (ParameterizedType) field.getGenericType();
//				return getRecursiveType((Class<?>) type.getActualTypeArguments()[0], propertyList);
			}
			return getRecursiveType(child, propertyList);
		} else {
			return parent.getDeclaredField(propertyList.get(0)).getType();
		}
	}

}