package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tweetapp.controller.MainMenuController;

@SpringBootApplication
public class TweetAppApplication{
	
	
	private static MainMenuController mainMenuController;

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "db");
		ApplicationContext applicationContext =  SpringApplication.run(TweetAppApplication.class, args);
		mainMenuController = applicationContext.getBean(MainMenuController.class);
		mainMenuController.startConsoleApplication();
	}

}
