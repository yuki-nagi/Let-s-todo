package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

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
		System.out.println("GETを受信しました。");
		TodoListDAO dao = new TodoListDAO();
		HttpSession session = request.getSession();
		
		String userIDsouse = session.getAttribute("userID").toString();
		int userID = Integer.parseInt(userIDsouse);
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
        
        System.out.println("responseにjsonをoutしました。");

        
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding( "UTF-8" );
		System.out.println("POSTを受信しました。");
		
		//todoList.jspのリクエストjsonの中のhiddenを参照して処理を振り分ける
		String hidden = request.getParameter("hidden");
		System.out.println("hiddenステータスを取得しました。");
		HttpSession session = null;
		//タスク追加ボタンの処理
		if(hidden.equals("AddTask")) {
		    try{
		    	session = request.getSession();
		    	
		    	String taskname = request.getParameter("taskname");
		    	String taskdate = request.getParameter("taskdate");
		    	String memo = request.getParameter("memo");
		    	int priority = Integer.parseInt(request.getParameter("priority"));
		    	String userIDsouse = session.getAttribute("userID").toString();
		    	
		    	int userID = Integer.parseInt(userIDsouse);
		    	String updatetime = request.getParameter("updatetime");
	
		    	TaskData taskData = new TaskData(taskname, taskdate, false, memo, userID, priority,updatetime);
		    	
		    	TodoListDAO dao = new TodoListDAO();
		    	
		    	dao.saveTasksDAO(taskData);
		    	
		    	
	
		      }catch( Exception e ){
		        e.printStackTrace();
		      }
		    
		//タスク完了ボタンの処理
		}
		else if(hidden.equals("Complete")) {
			String updatetime = request.getParameter("updatetime");
			session = request.getSession();
	    	String userIDsouse = session.getAttribute("userID").toString();
	    	int userID = Integer.parseInt(userIDsouse);
			TodoListDAO dao = new TodoListDAO();
			dao.completeTaskDAO(updatetime, userID);
		}
	}
	

}
