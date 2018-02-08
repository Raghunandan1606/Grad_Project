package Pwd.src.Models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private long id;
	private String userId;
	private String accessToken;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(long id, String userId, String accessToken) {
		this.id = id;
		this.userId = userId;
		this.accessToken = accessToken;
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
