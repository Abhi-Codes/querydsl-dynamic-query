package com.abhicodes.querydsldynamicquery.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "abhi_posts")
@EqualsAndHashCode(callSuper = false)
public class Post extends AuditableComplete {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	private String title;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostComment> comments = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_type_id")
	private PostType postType;
	
	@JoinColumn(name = "author_id")
	@OneToOne(fetch = FetchType.LAZY)
	private Users author;

	public void addComment(PostComment comment) {
		comments.add(comment);
		comment.setPost(this);
	}

	public void removeComment(PostComment comment) {
		comments.remove(comment);
		comment.setPost(null);
	}
}