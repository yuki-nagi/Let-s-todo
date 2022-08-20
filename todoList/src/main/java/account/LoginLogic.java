package account;

public class LoginLogic {
	
	public boolean execute(LoginStatus loginStatus) {
		try {
		AccountDAO dao = new AccountDAO();
		Account account = dao.findByLogin(loginStatus);
		return account != null;
		
		} catch(Exception e) {
			return false;
		}
		
	}
	
	
	
	public boolean executeGuest(GuestUser user) {
		if(user.getUserName().equals("田中") && user.getPass().equals("1234")) {
			return true;
		}
		
		return false;
	}
}
