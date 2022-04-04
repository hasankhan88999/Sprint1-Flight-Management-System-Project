package com.capg.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {
	
	private String userType;
	
	@Id
	private Integer userId=Integer.MIN_VALUE;
	
	private String userName;
	private String userPassword;
	private String userPhone;
	private String userEmail;
}
