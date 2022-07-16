package com.tweetapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;
import com.tweetapp.service.TweetService;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public void saveTweet(String emailId, Tweet tweet) {
		Optional<User> fetchedUser = userRepository.findById(emailId);

		if (fetchedUser.isPresent()) {
			User fetchedUserObj = fetchedUser.get();

			this.tweetRepository.save(tweet);

			fetchedUserObj.addTweet(tweet);
			userRepository.save(fetchedUserObj);

		} else {
			System.out.println("User not found with the given email ID");
		}

	}

	@Override
	public List<Tweet> fetchMyTweets(String emailId) {

		Optional<User> fetchedUser = userRepository.findById(emailId);

		if (fetchedUser.isPresent()) {
			User fetchedUserObj = fetchedUser.get();

			return fetchedUserObj.getTweets();
		} else {
			System.out.println("User not found with the given email ID");
		}
		return null;
	}

	@Override
	public List<Tweet> fetchAllTweets() {
		return this.tweetRepository.findAll();
	}

}
