package com.abhicodes.querydsldynamicquery.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

/**
 * The Class PageableResponse.
 */
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class PageableResponse {

	private int currentPage;
	private int pageSize;
	private int totalPages;
	private long totalElements;
	private Object results;
	private Double average;
}
