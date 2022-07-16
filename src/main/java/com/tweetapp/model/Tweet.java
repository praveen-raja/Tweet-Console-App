package com.tweetapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tweet_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

	// properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "text")
	@Size(min = 3, max = 144, message = "Tweet text should be between 3 and 144")
	private String text;
	@Column(name = "tags")
	// @Size(min = 3, max = 50, message = "Tweet tag should be between 3 and 50")
	private String tags;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email_id")
	private User user;

	// own constructor
	public Tweet(String text, String tags) {
		this.text = text;
		this.tags = tags;
	}
}
