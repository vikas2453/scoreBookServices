package com.fun.learning.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class TOTPDetails {
	@Id
	private String Id;
	
	@OneToOne
	@JoinColumn(name="username")	
	private User user;
	
	private String secret;

}
