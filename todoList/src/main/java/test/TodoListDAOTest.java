package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import main.TaskData;

public class TodoListDAOTest {
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/todo";
	private final String DB_USER = "root";
	private final String DB_PASS = "root";
	private final String SELECT_SQL = "SELECT id, taskname, taskdate, status, priority, memo FROM tasks WHERE userID = ?";
	private final String INSERT_SQL ="INSERT INTO user(username,password,mail,date) values(?,?,?,?)";
	
	

	public static void main(String[] args) {
		TodoListDAOTest dao = new TodoListDAOTest();
		try {
			List<JSONObject> jsonList = dao.showTasksDAO(16);
			System.out.println(jsonList.get(0));
			
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	
		

	}
	
	public List<JSONObject> showTasksDAO(int userID) {
		TodoListDAOTest dao = new TodoListDAOTest();
		TaskData taskdata = null;
		try {
			JSONObject json = null;
			List<JSONObject> jsonList = new ArrayList<>();
			List<TaskData> tasks = dao.serchTask(userID);
			for(int i=0; i < tasks.size(); i++) {
				taskdata = tasks.get(i);
				Map<String,Object> jmap = new HashMap<>();
				
				jmap.put("taskname", taskdata.getTaskname());
				jmap.put("taskdate", taskdata.getTaskdate());
				jmap.put("status", taskdata.getStatus());
				jmap.put("memo",taskdata.getMemo());
				jmap.put("userID",taskdata.getUserID());
				jmap.put("priority", taskdata.getPriority());
				json = new JSONObject(jmap);
				jsonList.add(json);
			}
			return jsonList;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
	
	public List<TaskData> serchTask(int userID) throws Exception{
		TaskData taskdata = null;
		List<TaskData> taskList = new ArrayList<>();
		try {
			System.out.println("showTasksDAO: 接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("showTasksDAO: :ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("showTasksDAO: DB接続できました");
			PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
			pstmt.setInt(1, userID);

			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("showTasksDAO: SQL文を送信しました");
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String taskname = rs.getString("taskname");
				String taskdate = rs.getDate("taskdate").toString();
				int isStatus = rs.getInt("status");
				boolean status = false;
				if (isStatus == 1) {
					status = true;
				}
				String memo = rs.getString("memo");
				int priority = rs.getInt("priority");
				System.out.println(taskname+taskdate+status+memo+priority);
				taskdata = new TaskData(taskname,taskdate,status,memo,userID,priority);
				taskList.add(taskdata);
			}
			System.out.println("showTasksDAO: taskListをreturnしました");
			return taskList;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	

}
