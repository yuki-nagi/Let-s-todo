package account;

import java.util.Date;

public class Account {
	private String userID;
	private String username;
	private String password;
	private String mail;
	private Date date;
	
	public Account(String userID, String username, String password, String mail, Date date) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.date = date;
	}

	public String getUserID() {
		return userID;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}


	public String getMail() {
		return mail;
	}

	public Date getDate() {
		return date;
	}

}
