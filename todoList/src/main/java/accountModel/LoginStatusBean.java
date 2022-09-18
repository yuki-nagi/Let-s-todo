package accountModel;

import java.io.Serializable;

//ログイン時に入力されたデータをまとめる為に使用するクラス
public class LoginStatusBean implements Serializable {
	private String userID;
	private String username;
	private String password;
	private String mail;
	
	public LoginStatusBean() {}
	
	public LoginStatusBean(String username,String mail, String password) {
		this.username = username;
		this.mail = mail;
		this.password = password;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
	
}
