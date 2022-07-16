package com.tweetapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	// properties
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	@Id
	@Column(name = "email_id", unique = true)
	private String email;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "password")
	private String password;
	@Column(name = "user_loggedin")
	private boolean loggedIn;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH })
	private List<Tweet> tweets = new ArrayList<Tweet>();

	public void addTweet(Tweet tweet) {

		if (tweet != null) {
			if (tweets == null) {
				tweets = new ArrayList<Tweet>();
			}
			tweets.add(tweet);
			tweet.setUser(this);
		}
	}

	// own constructor
	public User(String firstName, String lastName, String gender, Date dateOfBirth, String email, String password, String phoneNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.loggedIn = false;
	}
	
	//creating for maintaining login status for user
	public User(boolean loggedIn)
	{
		super();
		this.loggedIn = false;
	}

}
