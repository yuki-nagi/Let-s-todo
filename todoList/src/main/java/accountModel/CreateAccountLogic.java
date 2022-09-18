package accountModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class CreateAccountLogic {
	public String[] userfile(String name,String password,String mail) {
		PasswordHash ph = new PasswordHash();
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String passHash = ph.passwordHash(password, sdf.format(cl.getTime()).toString());
		
		String[] userfile = {name,passHash,mail,sdf.format(cl.getTime()).toString()};
		return userfile;
	}
	
	public void createAccount(String[] userfile){
		AccountDAO dao = new AccountDAO();
		try {
			dao.createAccountDAO(userfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
