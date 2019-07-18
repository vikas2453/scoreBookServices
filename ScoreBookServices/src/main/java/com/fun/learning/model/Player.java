package com.fun.learning.model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
@Entity
public class Player {
	
	@Id
	private int id;
	
	private String name;
	
	private LocalTime DOB;
	
	private User user;
	

}
