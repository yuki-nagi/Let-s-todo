package mainController;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import accountModel.AccountBean;
import mainModel.TaskDataBean;
import mainModel.TodoListDAO;

/**
 * Servlet implementation class TodoList
 */
@WebServlet("/TodoList")
public class TodoList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TodoList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		System.out.println("TodoList: GETを受信しました。");
		TodoListDAO dao = new TodoListDAO();
		HttpSession session = request.getSession();
		
		AccountBean account = (AccountBean) session.getAttribute("account");
		int userID = Integer.parseInt(account.getUserID());
		String Mode = request.getParameter("Mode");
		JSONObject json = null;
		if(Mode.equals("select1")) {
			 json = dao.showTasksDAO(userID, 1);
		}
		else if(Mode.equals("select2")) {
			 json = dao.showTasksDAO(userID, 2);
		}
		else if(Mode.equals("select3")) {
			 json = dao.showTasksDAO(userID, 3);
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        out.print(json);
        
        System.out.println("TodoList: responseにjsonをoutしました。");
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		System.out.println("TodoList: POSTを受信しました。");
		
		//todoList.jspのリクエストjsonの中のhiddenを参照して処理を振り分ける
		String hidden = request.getParameter("hidden");
		HttpSession session = request.getSession();
		
		//loginResultからPOSTされた時のフォワード処理
		if(hidden == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/todoList.jsp");
			dispatcher.forward(request, response);
		}
			
		//タスク追加ボタンの処理
		else if(hidden.equals("AddTask")) {
		    try{		    	
		    	String taskname = request.getParameter("taskname");
		    	String taskdate = request.getParameter("taskdate");
		    	int priority = Integer.parseInt(request.getParameter("priority"));
		    	AccountBean account = (AccountBean) session.getAttribute("account");
		    	
		    	int userID = Integer.parseInt(account.getUserID());
		    	String updatetime = request.getParameter("updatetime");
	
		    	TaskDataBean taskData = new TaskDataBean(taskname, taskdate, false, userID, priority,updatetime);
		    	
		    	TodoListDAO dao = new TodoListDAO();
		    	
		    	dao.saveTasksDAO(taskData);
		    	
		    	
	
		      }catch( Exception e ){
		        e.printStackTrace();
		      }
		    
		//タスク完了ボタンの処理
		}
		else if(hidden.equals("Complete")) {
			String updatetime = request.getParameter("updatetime");
			AccountBean account = (AccountBean) session.getAttribute("account");
			int userID = Integer.parseInt(account.getUserID());
			//セッションスコープのユーザーIDと、クライアント側のタイムスタンプを使ってTodoListから完了対象のタスクにクエリ送信
			TodoListDAO dao = new TodoListDAO();
			dao.completeTaskDAO(updatetime, userID);
		}
	}
	

}
