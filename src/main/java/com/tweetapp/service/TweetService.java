package com.tweetapp.service;

import java.util.List;

import com.tweetapp.model.Tweet;

public interface TweetService {

	void saveTweet(String emailId, Tweet tweet);

	List<Tweet> fetchMyTweets(String emailId);

	List<Tweet> fetchAllTweets();

}
