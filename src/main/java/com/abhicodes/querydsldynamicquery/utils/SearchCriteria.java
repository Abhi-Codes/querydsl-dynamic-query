package com.abhicodes.querydsldynamicquery.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SearchCriteria {
    private String key;
	private String operator;
	private String value;
}