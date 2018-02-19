package Pwd.src.Models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private long id;
	private String userId;
	private String password;
	private int securityQuestion;
	private String securityAnswer;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(long id, String userId, String accessToken) {
		this.id = id;
		this.userId = userId;
		this.password = accessToken;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(int securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

}
