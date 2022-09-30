package com.abhicodes.querydsldynamicquery.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
	private Integer id;
	private String title;

	private UsersDTO author;

	private String postType;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
