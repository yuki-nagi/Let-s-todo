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
	
}