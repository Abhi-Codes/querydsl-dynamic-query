package com.abhicodes.querydsldynamicquery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "abhi_users")
@Data
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "email")
	private String email;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "full_name")
	private String fullName;

}
