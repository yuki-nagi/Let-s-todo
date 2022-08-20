package test;

import account.Account;
import account.AccountDAO;
import account.LoginStatus;

public class AccountDAOTest {
	public static void main(String[] args) {
		testFindByLogin1();
		testFindByLogin2();
	}
	
	public static void testFindByLogin1() {
		try {
			LoginStatus LoginStatus = new LoginStatus
					("田中", 
					"mail1@yahoo.co.jp", 
					"3340f6bdcd67b484be101df5e63bb8c607bead02d509d9836241c045e66ce3fa");
			
			AccountDAO dao = new AccountDAO();
			Account result = dao.findByLogin(LoginStatus);
			
			if(result != null && 
					result.getUserID().equals("15") &&
					result.getUsername().equals("田中") && 
					result.getPassword().equals("3340f6bdcd67b484be101df5e63bb8c607bead02d509d9836241c045e66ce3fa") &&
					result.getMail().equals("mail1@yahoo.co.jp")){
				System.out.println("testFindByLogin1:成功しました");
			} else {
				System.out.println("testFindByLogin1:失敗しました");
			}
			
		} catch (Exception e){}
		
	}
	
	
	public static void testFindByLogin2() {
		try {
			LoginStatus LoginStatus = new LoginStatus("高橋","bbb@yahoo.co.jp","aaa");
			AccountDAO dao = new AccountDAO();
			Account result = dao.findByLogin(LoginStatus);
			if(result == null) {
				System.out.println("testFindByLogin2:成功しました");
			} else {
				System.out.println("testFindByLogin2:失敗しました");
			}
		} catch (Exception e) {}
	}
}
