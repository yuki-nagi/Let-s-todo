package accountModel;

public class LoginLogic {
	public boolean execute(LoginStatusBean loginStatus) {
		try {
		AccountDAO dao = new AccountDAO();
		AccountBean account = dao.findByLogin(loginStatus);
		return account != null;
		
		} catch(Exception e) {
			return false;
		}
		
	}
	
}