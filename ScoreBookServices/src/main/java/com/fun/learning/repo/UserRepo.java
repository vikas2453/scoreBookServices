package com.fun.learning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fun.learning.model.User;


@Repository
public interface UserRepo extends JpaRepository<User, String>  {

	User findByUsername(String username);

	User findByEmail(String email);
}
