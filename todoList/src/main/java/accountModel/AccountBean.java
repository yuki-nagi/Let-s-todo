package accountModel;

import java.io.Serializable;
import java.util.Date;
//アカウントのデータを引数にする時に、アカウントのデータをまとめておく為のクラス
public class AccountBean implements Serializable {
	private String userID;
	private String username;
	private String password;
	private String mail;
	private Date date;
	
	public AccountBean() {}
	

	public AccountBean(String userID, String username, String password, String mail, Date date) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.date = date;
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

	public void setDate(Date date) {
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
