package com.abhicodes.querydsldynamicquery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "abhi_post_comments")
@Data
public class PostComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String comments;

	@ManyToOne(fetch = FetchType.LAZY)
	private Post post;

}
