package com.abhicodes.querydsldynamicquery.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.abhicodes.querydsldynamicquery.component.CommonComponent;
import com.abhicodes.querydsldynamicquery.domain.PostDTO;
import com.abhicodes.querydsldynamicquery.domain.UsersDTO;
import com.abhicodes.querydsldynamicquery.entity.Post;
import com.abhicodes.querydsldynamicquery.entity.QPost;
import com.abhicodes.querydsldynamicquery.predicate.CommonPredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@Component
public class PostUtils {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CommonComponent commonComponent;

	public BooleanExpression getPCQFilterExp(List<SearchCriteria> criterias) {

		BooleanExpression exp = new CommonPredicateBuilder<>(Post.class).build();
		QPost qpostPath = QPost.post;
		for (SearchCriteria criteria : criterias) {

			switch (criteria.getKey()) {
			case "author_term":

				BooleanExpression authorUserNameExp = qpostPath.author.userName.containsIgnoreCase(criteria.getValue());
				BooleanExpression authorEmailExp = qpostPath.author.email.containsIgnoreCase(criteria.getValue());
				BooleanExpression authorFullNameExp = qpostPath.author.fullName.containsIgnoreCase(criteria.getValue());
				exp = exp.and(authorUserNameExp.or(authorEmailExp).or(authorFullNameExp));
				break;
			default:
				exp = exp.and(new CommonPredicateBuilder<>(Post.class).and(criteria)
						.replaceKeyMap(getPostFilterReplaceMap()).build());
			}
		}
		return exp;
	}

	public PageRequest getCustomizablePage(Pageable pageable) {
		return commonComponent.customizePagable(pageable, getPostFilterReplaceMap());
	}

	private Map<String, String> getPostFilterReplaceMap() {
		Map<String, String> replaceKeyMap = new HashMap<>();
		replaceKeyMap.put("postType", "postType.id");
		replaceKeyMap.put("authorName", "author.fullName");
		return replaceKeyMap;
	}

	public PostDTO mapToDTO(Post post) {
		UsersDTO author = modelMapper.map(post.getAuthor(), UsersDTO.class);
		return PostDTO.builder().author(author).createdAt(post.getCreatedAt()).id(post.getId()).title(post.getTitle())
				.postType(post.getPostType().getType()).build();

	}
}
