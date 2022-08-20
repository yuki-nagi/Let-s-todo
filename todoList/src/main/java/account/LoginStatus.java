package account;

public class LoginStatus {
	private String userID;
	private String username;
	private String password;
	private String mail;
	
	public LoginStatus(String username,String mail, String password) {
		this.username = username;
		this.mail = mail;
		this.password = password;
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
