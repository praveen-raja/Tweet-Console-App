package com.tweetapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.model.User;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerNewUser(User user) {

		Optional<User> existingUser = userRepository.findById(user.getEmail());

		if (existingUser.isPresent()) {
			return null;
		} else {
			User savedUser = userRepository.save(user);
			return savedUser;
		}

	}

	@Override
	public boolean validateUser(String emailId, String password) {

		Optional<User> existingUser = userRepository.findById(emailId);

		if (existingUser.isPresent()) {
			if ((existingUser.get().getPassword()).equals(password))
				return true;
		}
		return false;
	}

	@Override
	public User getUserByEmailID(String emailId) {
		Optional<User> existingUser = userRepository.findById(emailId);

		if (existingUser.isPresent()) {
			return existingUser.get();
		}
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		Optional<User> existingUser = userRepository.findById(user.getEmail());
		if (existingUser.isEmpty()) {
			return false;
		} else {
			userRepository.save(user);
			return true;
		}
	}

	@Override
	public List<User> getAllUser() {

		return this.userRepository.findAll();
	}

}
