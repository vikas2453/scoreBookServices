package com.fun.learning.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Authority implements GrantedAuthority{
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String authority;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	public Authority(String authority, User user) {
		super();
		this.authority = authority;
		this.user = user;
	}


	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}

}
