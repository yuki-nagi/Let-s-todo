package todoList;

public class LoginLogic {
	public boolean execute(GuestUser user) {
		if(user.getUserName().equals("田中") && user.getPass().equals("1234")) {
			return true;
		}
		if(user.getUserName().equals("山田") && user.getPass().equals("0000")) {
			return true;
		}
		return false;
	}
}
