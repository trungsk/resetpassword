package com.thanhtrung.loginsecurity.service;

import com.thanhtrung.loginsecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

	void createUser(User user);

	User findUserByEmail(String email);

	void activeUser(String token);

	List<User> getAllUsers();

}
