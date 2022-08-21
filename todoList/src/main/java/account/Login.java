package account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String mail = request.getParameter("mail");
		String passwordBeforHash = request.getParameter("pass");
		
		
		//フォームに空文字が存在しなければ、パスワードをハッシュ化してログイン処理を実行する
		if(username != "" && mail != "" && passwordBeforHash != "") {
			
			AccountDAO dao = new AccountDAO();		
			PasswordHash ph = new PasswordHash();
			String solt = dao.findSolt(username,mail);
			
			// パスワードのハッシュ化の為のソルトが見つかれば引き続き実行する。見つからなければリダイレクト。
			if(solt != "") {
				String password = ph.passwordHash(passwordBeforHash,solt);
		
				LoginStatus loginStatus = new LoginStatus(username,mail,password);
				LoginLogic bo = new LoginLogic();
				boolean result = bo.execute(loginStatus);
				
				
				//LoginLogicによってアカウントがDBから見つかった場合、AccountにDBのアカウント情報を引き渡し、userIDとusernameをセッションスコープに入れる。。
				if(result) {
					Account account;
					try {
						account = dao.findByLogin(loginStatus);

					
					HttpSession session = request.getSession();
					session.setAttribute("username", account.getUsername());
					session.setAttribute("userID", account.getUserID());
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/loginResult.jsp");
					dispatcher.forward(request, response);
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				}
				
				else {
					System.out.println("Login: アカウントが見つかりませんでした。リダイレクトします");
					response.sendRedirect("/todoList/top.jsp");
				}
			}
			else {
				System.out.println("Login: Solt もしくはアカウントが見つかりませんでした。リダイレクトします");
				response.sendRedirect("/todoList/top.jsp");
			}
		}
		else {
			System.out.println("Login: フォームに空文字が存在するため、リダイレクトします");
			response.sendRedirect("/todoList/top.jsp");
		}
	}

}
//String userName = request.getParameter("userName");
//String pass = request.getParameter("pass");
//
//GuestUser user = new GuestUser(userName,pass);
//
//LoginLogic loginLogic = new LoginLogic();
//boolean isLogin = loginLogic.executeGuest(user);
//
//if(isLogin) {
//	HttpSession session = request.getSession();
//	session.setAttribute("loginUser",user);
//}
