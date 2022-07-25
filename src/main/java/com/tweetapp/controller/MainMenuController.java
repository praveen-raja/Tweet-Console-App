package com.tweetapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;

@Component
public class MainMenuController {

	// User Service
	@Autowired
	private UserService userService;

	// Tweet Service
	@Autowired
	private TweetService tweetService;

	// Email ID of logged in User
	private static String loggedInUserEmailID = "";

	// start the console application
	public void startConsoleApplication() {
		beforeLogin();
	}

	// menu for user: not logged
	private void beforeLogin() {

		int choice = -1;
		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println("===========================");
			System.out.println("***Tweet App***\n");
			System.out.println("Hello User!\nWelcome to TweetApp..!\n");

			System.out.println("1.Register\n2.Login\n3.Forgot Password\n4.Exit");
			System.out.println("===========================");

			// validate the userInput
			System.out.print("Enter your choice(1-4): ");
			String userInput = scanner.nextLine();

			try {
				choice = Integer.parseInt(userInput);

				switch (choice) {
				case 1: {
					performUserRegistration();
					break;

				}
				case 2: {
					performLogin();
					break;
				}
				case 3: {
					performForgotPassword();
					break;
				}
				case 4: {
					System.out.println("Thank you visit again!");
					System.exit(0);
				}
				default:
					System.out.println("Error: Invalid Choice: Kindly enter the number from the given list range..");
					beforeLogin();
				}
				break;
			} catch (Exception e) {
				System.out.println("Error: Invalid Choice: Kindly enter the number from the given list range..");
			}

		}
		scanner.close();
	}

	// Actions after the Login
	private void afterLogin() {

		int choice = -1;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("===========================");
			System.out.println("***Tweet App***\n\nHello " + loggedInUserEmailID
					+ "!\n\n1.Post Tweet\n2.See All Tweets\n3.View My Tweets\n4.View Profile\n5.View All User\n6.Reset Password\n7.Logout");
			System.out.println("===========================");

			// validate the userInput
			System.out.print("Enter your choice: ");
			String userInput = scanner.nextLine();

			try {
				choice = Integer.parseInt(userInput);

				switch (choice) {
				case 1: {
					this.postNewTweet();
					break;

				}
				case 2: {
					this.viewAllTweets();
					break;
				}
				case 3: {
					this.viewMyTweets();
					break;
				}
				case 4: {
					this.viewMyProfile();
					break;
				}
				case 5: {
					this.viewAllProfile();
				}
				case 6: {
					this.resetPassword();
					break;
				}
				case 7: {

					final User userdetails = userService.getUserByEmailID(loggedInUserEmailID);
					//Setting false to user logged in field
					userdetails.setLoggedIn(false);

					//Setting UserLogged in field
					userService.updateUser(userdetails);
					System.out.println("User logged out status updated successfully..!");
					System.out.println("Logged out Successfully..!");
					loggedInUserEmailID = "";
					System.out.print("Do you want to exit App?(Y/N): ");
					String choice_logout = scanner.next();

					if (choice_logout.equals("N") || choice_logout.equals("n")) {
						//System.out.println("in N");

						this.beforeLogin();
					} else {
						System.out.println("Thank you visit again!");
						System.exit(0);
					}
					this.beforeLogin();
					break;
				}
				default:
					System.out.println("Error: Invalid Choice: Kindly enter the number from the given option list");
					afterLogin();
				}
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace().toString());
				System.out.println("Error: Invalid Choice: Kindly enter the number from the given option list");
			}

		}
		scanner.close();
	}
	
	// Actions for user registration
	private void performUserRegistration() {
		System.out.println("===========================");
		System.out.println("Mode => User Registration");
		System.out.println("===========================");
		Scanner scanner = new Scanner(System.in);

		// User Input First Name
		System.out.print("Enter your First Name (*required): ");
		String firstName = scanner.nextLine();

		while (firstName.length() == 0) {
			System.out.print("Kindly enter First Name (*required): ");
			firstName = scanner.nextLine();
		}

		// User Input Last Name
		System.out.print("Enter your Last Name (optional): ");
		String lastName = scanner.nextLine();

		// User Input Gender
		System.out.print("Enter your Gender(M/F) (*required): ");
		String gender = scanner.nextLine();
		while (gender.length() == 0) {
			System.out.print("Enter your Gender(M/F) (*required): ");
			gender = scanner.nextLine();
		}
		while (gender.charAt(0) != 'M' && gender.charAt(0) != 'F' && gender.charAt(0) != 'm'
				&& gender.charAt(0) != 'f') {
			System.out.print("Enter your Gender(M/F) (*required): ");
			gender = scanner.nextLine();
		}
		// User Input phone number
		System.out.print("Enter your Phone number(*required): ");
		String phoneNumber = scanner.nextLine();
		while (phoneNumber.length() == 0) {
			System.out.print("Enter your Phone number(*required): ");
			phoneNumber = scanner.nextLine();
		}

		// User Input Email
		System.out.print("Enter your Email ID (*required): ");
		String email = scanner.nextLine();

		final String emailRegex = "^(.+)@(.+)$";
		final Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher matcher = emailPattern.matcher(email);

		while (!matcher.matches()) {
			System.out.print("Kindly enter valid Email ID (*required): ");
			email = scanner.nextLine();
			matcher = emailPattern.matcher(email);
		}

		// User Input Password
		System.out.println(
				"INFO:: Password Format: Minimum 8 characters, at least 1 uppercase letter, 1 lowercase letter, 1 special character and 1 number");
		System.out.print("Enter your password (*required): ");
		String password = scanner.nextLine();

		final String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[!@#$%^&+=])"
				+ "(?=\\S+$).{8,20}$";
		final Pattern passwordPattern = Pattern.compile(passwordRegex);
		Matcher matcher1 = passwordPattern.matcher(password);

		while (!matcher1.matches()) {
			System.out.println(
					"Kindly enter the valid Password: Minimum eight characters, at least one uppercase letter, one lowercase letter and one number");
			System.out.print("Enter your valid password (*required): ");
			password = scanner.nextLine();
			matcher1 = emailPattern.matcher(password);
		}

		// User Input Date Of Birth
		System.out.print("Enter your date of birth (dd/MM/yyyy) (*required): ");
		Date dateOfBirth;

		while (true) {
			try {
				final String date = scanner.nextLine();
				dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(date);
				break;
			} catch (ParseException e) {
				System.out.print("Enter your date of birth in valid format (dd/MM/yyyy) (*required): ");
			}
		}

		// create a new user object
		final User userDetails = new User(firstName, lastName, gender, dateOfBirth, email, password, phoneNumber);
		// save the user object into the database
		if (userService == null) {
			System.out.println("User Service is null");
		}
		final User savedUser = userService.registerNewUser(userDetails);
		if (savedUser != null) {
			System.out.println("===========================");
			System.out.println("User : " + firstName + " - Registered successfully!");
			System.out.println("Kindly use your email Id as username: " + savedUser.getEmail());
			System.out.println("Thank you..!");
		} else {
			System.out.println("User already exist with the given Email ID! Kindly try again");
		}

		this.beforeLogin();
		scanner.close();
	}
	//Action for login
	private void performLogin() {

		System.out.println("===========================");
		System.out.println("Mode => User Login");
		System.out.println("===========================");
		Scanner scanner = new Scanner(System.in);

		// User Input Email
		System.out.print("Enter your Email ID (*required): ");
		String email = scanner.nextLine();

		final String emailRegex = "^(.+)@(.+)$";
		final Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher matcher = emailPattern.matcher(email);

		while (!matcher.matches()) {
			System.out.print("Kindly enter valid Email ID (*required): ");
			email = scanner.nextLine();
			matcher = emailPattern.matcher(email);
		}

		System.out.print("Enter your password (*required): ");
		String password = scanner.nextLine();

		final String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
				+ "(?=\\S+$).{8,20}$";
		final Pattern passwordPattern = Pattern.compile(passwordRegex);
		Matcher matcher1 = passwordPattern.matcher(password);

		while (!matcher1.matches()) {
			System.out.println("### Username or Password is Invalid ###");
			System.out.print("Enter your valid password (*required): ");
			password = scanner.nextLine();
			matcher1 = emailPattern.matcher(password);
		}

		final boolean isValidUser = userService.validateUser(email, password);
		final User userdetails = userService.getUserByEmailID(email);

		if (isValidUser) {
			//Set user logged in field to true
			userdetails.setLoggedIn(true);

			final boolean savedUser = userService.updateUser(userdetails);

			System.out.println("User login status updated : " + savedUser);
			System.out.println(email + " Logged in Successfully :)");
			loggedInUserEmailID = email;

			this.afterLogin();
		} else {
			System.out.println("### Username or Password is Invalid ###");

			performLogin();
		}
		scanner.close();
	}

	// Perform Forgot Password
	public void performForgotPassword() {

		System.out.println("===========================");
		System.out.println("Mode => Forgot Password");
		System.out.println("===========================");
		Scanner scanner = new Scanner(System.in);

		// User Input Email
		System.out.print("Enter your Email ID (*required): ");
		String email = scanner.nextLine();

		final String emailRegex = "^(.+)@(.+)$";
		final Pattern emailPattern = Pattern.compile(emailRegex);
		Matcher matcher = emailPattern.matcher(email);

		while (!matcher.matches()) {
			System.out.print("Kindly enter valid Email ID (*required): ");
			email = scanner.nextLine();
			matcher = emailPattern.matcher(email);
		}
		// =============================
		// User Input Phone number
		System.out.print("Enter your Phone number (*required): ");
		String phone = scanner.nextLine();
		while (phone.length() == 0) {
			System.out.print("Enter your Phone number(*required): ");
			phone = scanner.nextLine();
		}

		final User existingUser = userService.getUserByEmailID(email);
		
		if (existingUser == null) {
			System.out.println("No user exist with the given email ID..\nTry again!");
			beforeLogin();
		} else {
			if (existingUser.getPhoneNumber().equals(phone)) {
				System.out.println(
						"INFO:: Password Format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one special character and one number");
				System.out.print("Enter your new password (*required): ");
				String password = scanner.nextLine();

				final String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
						+ "(?=\\S+$).{8,20}$";
				final Pattern passwordPattern = Pattern.compile(passwordRegex);
				Matcher matcher1 = passwordPattern.matcher(password);

				while (!matcher1.matches()) {
					System.out.println(
							"Kindly enter the valid Password: Minimum eight characters, at least one uppercase letter, one lowercase letter and one number");
					System.out.print("Enter your new password (*required): ");
					password = scanner.nextLine();
					matcher1 = passwordPattern.matcher(password);
				}

				existingUser.setPassword(password);
				final boolean isUserPasswordUpdated = userService.updateUser(existingUser);
				if (isUserPasswordUpdated) {
					System.out.println("Password successfully changed");
				} else {
					System.out.println("Opps!! Something went wrong");
				}
			} else {
				System.out.println("Email and Phone number not matching! Try again..");
			}

			this.beforeLogin();
		}
		scanner.close();
	}

	// Create a new tweet
	public void postNewTweet() {
		System.out.println("===========================");
		System.out.println("Mode => Post New Tweet");
		System.out.println("===========================");

		Scanner scanner = new Scanner(System.in);

		// User Input : Tweet Text
		System.out.print("Enter Tweet Text (*required): ");
		String tweetText = scanner.nextLine();

		while (tweetText.length() < 3 || tweetText.length() > 144) {
			System.out.print("Kindly enter Tweet Text (*required) (length should be [3,144]):  ");
			tweetText = scanner.nextLine();
		}

		// User Input : Tweet Tags
		System.out.print("Enter Tweet Tags (optional): ");
		String tweetTags = scanner.nextLine();

		while (tweetTags.length() > 50) {
			System.out.print("Kindly enter Tweet Tag (length should be [0,50]): ");
			tweetTags = scanner.nextLine();
		}

		// saving the details to the database
		final Tweet tweet = new Tweet(tweetText, tweetTags);
		tweetService.saveTweet(loggedInUserEmailID, tweet);

		this.afterLogin();
		scanner.close();
	}

	// view tweets of all user
	public void viewAllTweets() {
		System.out.println("===========================");
		System.out.println("Mode => View All Tweets");
		System.out.println("===========================");
		Scanner scanner = new Scanner(System.in);

		final List<Tweet> tweetsList = this.tweetService.fetchAllTweets();
		if (tweetsList.isEmpty()) {
			System.out.println("No tweets found yet.");
		} else {
			for (int i = 0; i < tweetsList.size(); i++) {
				System.out.println("Tweet " + (i + 1) + " : |" + tweetsList.get(i).getText() + "| >>> Tags : |"
						+ tweetsList.get(i).getTags() + "| >>> User : |" + tweetsList.get(i).getUser().getEmail()
						+ "|");
			}
			System.out.print("Do you want to continue?(Y/N): ");
			String choice = scanner.next();

			if (choice.equals("N") || choice.equals("n")) {
				//System.out.println("in N");
				final User userdetails = userService.getUserByEmailID(loggedInUserEmailID);
				userdetails.setLoggedIn(false);
				
				//Setting UserLogged in field
				userService.updateUser(userdetails);
				System.out.println("User logged out status updated successfully..!");
				this.beforeLogin();
			} else {
				//System.out.println("in Y");
				this.afterLogin();
			}

		}
		scanner.close();
	}

	// View My Tweets
	public void viewMyTweets() {
		System.out.println("===========================");
		System.out.println("Mode => View My Tweets");
		System.out.println("===========================");
		Scanner scanner = new Scanner(System.in);

		final List<Tweet> tweetsList = this.tweetService.fetchMyTweets(loggedInUserEmailID);
		if (tweetsList.isEmpty()) {
			System.out.println("No tweets found under your account");
			afterLogin();
		} else {
			for (int i = 0; i < tweetsList.size(); i++) {
				System.out.println("Tweet " + (i + 1) + " : |" + tweetsList.get(i).getText() + "| >>> Tags : |"
						+ tweetsList.get(i).getTags() + "| >>> User : |" + tweetsList.get(i).getUser().getEmail()
						+ "|");
			}
			System.out.print("Do you want to continue?(Y/N): ");
			String choice = scanner.next();

			if (choice.equals("N") || choice.equals("n")) {
				//System.out.println("in N");
				final User userdetails = userService.getUserByEmailID(loggedInUserEmailID);
				userdetails.setLoggedIn(false);
				
				//Setting UserLogged in field
				userService.updateUser(userdetails);
				System.out.println("User logged out status updated successfully..!");
				this.beforeLogin();
			} else {
				//System.out.println("in Y");
				this.afterLogin();
			}
		}
		scanner.close();
	}

	// View Profile
	public void viewMyProfile() {
		System.out.println("===========================");
		System.out.println("Mode => View Profile");
		System.out.println("===========================");

		final User savedUser = this.userService.getUserByEmailID(loggedInUserEmailID);

		if (savedUser == null) {
			System.out.println("Something went wrong, Kindly try again");

			this.beforeLogin();
		} else {
			System.out.println("First Name: " + savedUser.getFirstName());
			final String lastName = savedUser.getLastName();
			if (lastName.length() != 0) {
				System.out.println("Last Name: " + lastName);
			}
			final String gender = savedUser.getGender().equals("M") ? "Male" : "Female";
			System.out.println("Gender: " + gender);

			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			final String strDate = formatter.format(savedUser.getDateOfBirth());
			System.out.println("Date Of Birth: " + strDate);
			System.out.println("Email ID: " + loggedInUserEmailID);
			System.out.println("Phone Number: " + savedUser.getPhoneNumber());
			this.afterLogin();
		}
	}

	// View All Profile
	private void viewAllProfile() {
		System.out.println("===========================");
		System.out.println("Mode => View All Profile");
		System.out.println("===========================");

		Scanner scanner = new Scanner(System.in);

		final List<User> allUser = this.userService.getAllUser();
		if (allUser.isEmpty()) {
			System.out.println("No users found in the App");
			afterLogin();
		} else {
			for (int i = 0; i < allUser.size(); i++) {
				System.out.println(
						"User " + (i + 1) + " : | FirstName: " + allUser.get(i).getFirstName() + " | LastName: "
								+ allUser.get(i).getLastName() + " | EmailID: " + allUser.get(i).getEmail() + " |");
			}
			System.out.print("Do you want to continue?(Y/N): ");
			String choice = scanner.next();
			if (choice.equals("N") || choice.equals("n")) {
				//System.out.println("in N");
				this.beforeLogin();
			} else {
				//System.out.println("in Y");
				this.afterLogin();
			}
		}
		scanner.close();
	}

	// reset the password for logged in user
	public void resetPassword() {
		System.out.println("===========================");
		System.out.println("Mode => Reset Password");
		System.out.println("===========================");

		Scanner scanner = new Scanner(System.in);

		// old password input
		System.out.println(
				"INFO:: Password Format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one special character and one number");
		System.out.print("Enter your current password (*required): ");
		String password = scanner.nextLine();

		final String passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])"
				+ "(?=\\S+$).{8,20}$";
		final Pattern passwordPattern = Pattern.compile(passwordRegex);
		Matcher matcher1 = passwordPattern.matcher(password);

		while (!matcher1.matches()) {
			System.out.println(
					"Kindly enter the valid current Password: Minimum eight characters, at least one uppercase letter, one lowercase letter and one number");
			System.out.print("Enter your valid password (*required): ");
			password = scanner.nextLine();
			matcher1 = passwordPattern.matcher(password);
		}

		final User savedUser = userService.getUserByEmailID(loggedInUserEmailID);

		if (savedUser.getPassword().equals(password)) {
			// new password input
			System.out.println(
					"INFO:: Password Format: Minimum eight characters, at least one uppercase letter, one lowercase letter, one special character and one number");
			System.out.print("Enter your new password (*required): ");
			String password1 = scanner.nextLine();

			matcher1 = passwordPattern.matcher(password1);

			while (!matcher1.matches()) {
				System.out.println(
						"Kindly enter the valid current Password: Minimum eight characters, at least one uppercase letter, one lowercase letter and one number");
				System.out.print("Enter new valid password (*required): ");
				password1 = scanner.nextLine();
				matcher1 = passwordPattern.matcher(password1);
			}
			savedUser.setPassword(password1);

			final boolean isUserUpdated = userService.updateUser(savedUser);
			if (isUserUpdated) {
				System.out.println("Password reset successfully.");
				final User userdetails = userService.getUserByEmailID(loggedInUserEmailID);
				//set user logged in field to false
				userdetails.setLoggedIn(false);

				userService.updateUser(userdetails);
				System.out.println("User logged out status updated successfully");

				this.beforeLogin();
			} else {
				System.out.println("Something went wrong, Try again");

				this.afterLogin();
			}

		} else {
			System.out.println("Invalid Current Password! Kindly try again");

			this.resetPassword();
		}
		scanner.close();
	}
}
