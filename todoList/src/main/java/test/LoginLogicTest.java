package test;

import account.LoginLogic;
import account.LoginStatus;

public class LoginLogicTest {

	public static void main(String[] args) {
		testExecute1();
		testExecute2();
	
	}

	public static void testExecute1() {

		LoginStatus loginStatus = new LoginStatus("tanaka","2cf998400a1f067a2690a98d212550b9827c1aead8857c167d6e43e4fe0485d9","mail");
		LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(loginStatus);
		if(result) {
			System.out.println("testExuecute1: 成功しました");
		}
		else {
			System.out.println("testExecute2: 失敗しました");
		}

	}
	
	
	public static void testExecute2() {
		LoginStatus loginStatus = new LoginStatus("tanaka","2cf998400a1f067a2690a98d212550b9827c1aead8857c167d6e43e4fe0485d910","mail");
		LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(loginStatus);
		if(!result) {
			System.out.println("testExuecute1: 成功しました");
		}
		else {
			System.out.println("testExecute2: 失敗しました");
		}
		
	}
}
