package com.tweetapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tweetapp.model.User;

@Service
public interface UserService {

	public User registerNewUser(User user);

	public boolean validateUser(String emailId, String password);

	public User getUserByEmailID(String emailId);
	
	public boolean updateUser(User user);

	public User IsUserLoggedIn(User userLoggedInDetails);

	public List<User> getAllUser();
	
}
