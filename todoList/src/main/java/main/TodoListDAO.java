package main;

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

public class TodoListDAO {	
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/todo";
	private final String DB_USER = "root";
	private final String DB_PASS = "Kurochloe39881052";
	private final String SELECT_SQL = "SELECT id, taskname, taskdate, status, priority, memo FROM tasks WHERE userID = ?";
	private final String INSERT_SQL ="INSERT INTO user(username,password,mail,date) values(?,?,?,?)";
	
	
	
	public List<JSONObject> showTasksDAO(int userID) {
		TodoListDAO dao = new TodoListDAO();
		TaskData taskdata = null;
		try {
			JSONObject json = null;
			List<JSONObject> jsonList = new ArrayList<>();
			List<TaskData> tasks = dao.serchTaskDAO(userID);
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
	
	public List<TaskData> serchTaskDAO(int userID) throws Exception{
		TaskData taskdata = null;
		List<TaskData> taskList = new ArrayList<>();
		try {
			System.out.println("serchTaskDAO: 接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("serchTaskDAO: :ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("serchTaskDAO: DB接続できました");
			PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
			pstmt.setInt(1, userID);

			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("serchTaskDAO: SQL文を送信しました");
			
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
			System.out.println("serchTaskDAO: taskListをreturnしました");
			return taskList;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
}

