package com.fun.learning.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fun.learning.validator.ConfirmPassword;
import com.fun.learning.validator.Password;
import com.fun.learning.validator.UniqueEmail;
import com.fun.learning.validator.UniqueUsername;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@Component
@NoArgsConstructor
@RequiredArgsConstructor
// @Slf4j
@ConfirmPassword
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	@NotEmpty(message = "Please enter username")
	@UniqueUsername
	private String username;

	@NonNull
	@Size(min = 2, max = 15, message = "name must between 1 and 4 characters")
	@NotEmpty(message = "Please enter FirstName")
	private String firstName;

	@NotEmpty(message = "Please enter FirstName")
	@NonNull
	private String lastName;

	@NotEmpty
	@Email(message = "Email is not validssss")
	@UniqueEmail
	@NonNull
	private String email;

	// public static final short maxLoginFailedAttempt = 3;
	private int loginFailedAttempt = 0;
	@NotEmpty
	@NonNull
	@Password
	private String password;
	
	private String pin;

	@Transient
	private String confirmPassword;

	@NonNull
	private Gender gender;

	private String securityQuestion;
	private String securityAnswer;
	private Date lastLoggedIn;
	private int loginTimes;
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean MFAEnabled;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<Authority> authorityList = new ArrayList<>();
	
	@OneToOne (mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private TOTPDetails TOTPDetails;

	private String userType;

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorityList;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		/*
		 * log.debug("maxLoginFailedAttempt" + maxLoginFailedAttempt); if
		 * (this.loginFailedAttempt >= maxLoginFailedAttempt) { return false; }
		 */
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public static class UserBuilder {
		private String username;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private Gender gender;

		public UserBuilder username(String username) {
			this.username = username;
			return this;
		}

		public UserBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public UserBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public UserBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder gender(Gender gender) {
			this.gender = gender;
			return this;
		}

		public User Build() {
			User user = new User(username,firstName, lastName, email,password, gender);
			return user;
		}

	}
}
