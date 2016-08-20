package com.pm.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "User")
public class User extends AbstractModel {

	private String lastName;
	private String firstName;
	private String login;
	private String email;
	private String password;
	private String lastLog;
	private List<String> hashPreferences= new ArrayList<String>();
	private String profilePhoto;

	public User(){}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getHashPreferences() {
		return hashPreferences;
	}
	public void setHashPreference(String hashPreference) {
		this.hashPreferences.add(hashPreference);
	}
	public void setHashPreferences(List<String> hashPreferences) {
		this.hashPreferences = hashPreferences;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLastLog() {
		return lastLog;
	}
	public void setLastLog(String lastLog) {
		this.lastLog = lastLog;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
}
