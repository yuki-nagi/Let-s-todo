package account;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("userName");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String passCheck = request.getParameter("passCheck");
		
		
		// アカウント作成フォームに空文字がない & パスワードが再入力と同じならDBへのアカウント登録を実行、見つからなければ同じページにフォワード
		if(name !="" && password != "" && mail != "" && passCheck != null && password == passCheck) {
			CreateAccountLogic cal = new CreateAccountLogic();
			cal.createAccount(cal.userfile(name, password, mail));
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createAccountResult.jsp");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/createAccount.jsp");
			dispatcher.forward(request, response);
		}
	}

}
