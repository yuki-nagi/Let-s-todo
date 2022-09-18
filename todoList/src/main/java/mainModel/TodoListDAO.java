package mainModel;

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
	private final String DB_PASS = "root";
	
	//クライアント側(Javascript)にデータを渡す為、json化するメソッド
	//取り出す時、キーの形が「j1、ｊ2…」のように連番になっている為、forループで「"j"+i」をキーにすること
	public JSONObject showTasksDAO(int userID,int sortMode) {
		TodoListDAO dao = new TodoListDAO();
		TaskDataBean taskdata = null;
		Map<String, Map<String, Object>> jmap = new HashMap<String, Map<String, Object>>();

		try {
			JSONObject json = null;
			
			List<TaskDataBean> tasks = dao.serchTaskDAO(userID,sortMode);
			for(int i=0; i < tasks.size(); i++) {
				taskdata = tasks.get(i);
				
				Map<String,Object>cmap = new HashMap<String, Object>();
				cmap.put("taskname", taskdata.getTaskname());
				cmap.put("taskdate", taskdata.getTaskdate());
				cmap.put("status", taskdata.getStatus());
				cmap.put("userID",taskdata.getUserID());
				cmap.put("priority", taskdata.getPriority());
				cmap.put("updatetime", taskdata.getUpdatetime());
				
				jmap.put("j"+i, cmap);
			}
			json = new JSONObject(jmap);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//statusが0のタスクを取り出すメソッド
	//ajaxから受け取ったデータによって、ORDER BYクエリを使い分けてソート表示する
	public List<TaskDataBean> serchTaskDAO(int userID,int sortMode) throws Exception{
		final String SELECT_SQL_MODE1 = "SELECT id, taskname, taskdate, status, priority, updatetime FROM tasks WHERE userID = ?";
		final String SELECT_SQL_MODE2 = "SELECT id, taskname, taskdate, status, priority, updatetime FROM tasks WHERE userID = ? ORDER BY taskdate ASC";
		final String SELECT_SQL_MODE3 = "SELECT id, taskname, taskdate, status, priority, updatetime FROM tasks WHERE userID = ? ORDER BY priority ASC";
		TaskDataBean taskdata = null;
		List<TaskDataBean> taskList = new ArrayList<>();
		try {
			System.out.println("serchTaskDAO: 接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("serchTaskDAO: :ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("serchTaskDAO: DB接続できました");
			PreparedStatement pstmt = null;
			if(sortMode == 1) {
				pstmt = conn.prepareStatement(SELECT_SQL_MODE1);
				System.out.println("serchTaskDAO: モード1でSQL文を送信します");
			}
			else if(sortMode == 2) {
				pstmt = conn.prepareStatement(SELECT_SQL_MODE2);
				System.out.println("serchTaskDAO: モード2でSQL文を送信します");
			}
			else if(sortMode == 3) {
				pstmt = conn.prepareStatement(SELECT_SQL_MODE3);
				System.out.println("serchTaskDAO: モード3でSQL文を送信します");
			}
			pstmt.setInt(1, userID);
			ResultSet rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
//				int id = rs.getInt("id");
				String taskname = rs.getString("taskname");
				String taskdate = rs.getDate("taskdate").toString();
				int isStatus = rs.getInt("status");
				boolean status = false;
				if (isStatus == 1) {
					status = true;
				}
				int priority = rs.getInt("priority");
				
				String updatetime = rs.getString("updatetime");

				
//				System.out.println(taskdate+" "+priority+" "+taskname+" "+status+memo+" "+updatetime);
				taskdata = new TaskDataBean(taskname,taskdate,status,userID,priority,updatetime);
				taskList.add(taskdata);
			}
			System.out.println("serchTaskDAO: taskListをreturnしました");
			return taskList;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//DBへのタスク追加メソッド
	//サーブレットがTaskDataにまとめたデータを取り出し、クエリにする
	public void saveTasksDAO(TaskDataBean taskdata) {
		final String INSERT_SQL ="INSERT INTO tasks(taskname,taskdate,userID,priority,updatetime) values(?,?,?,?,?)";
		
		try {
			System.out.println("saveTasksDAO: 接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("saveTasksDAO: ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("saveTasksDAO: DB接続できました");
			
			conn.setAutoCommit(false);
			
			try(PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
				ps.setString(1, taskdata.getTaskname());
				ps.setString(2, taskdata.getTaskdate());
				ps.setInt(3, taskdata.getUserID());
				ps.setInt(4, taskdata.getPriority());
				ps.setString(5, taskdata.getUpdatetime().toString());
				
				ps.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				System.out.println("rollback");
				throw e;
			}
			System.out.println("saveTasksDAO: タスクを追加しました");
	        conn.close();
	   } catch (ClassNotFoundException e) {
	       System.out.println("saveTasksDAO: ドライバを読み込めませんでした "+ e);
	   } catch (SQLException e) {
	       System.out.println("saveTasksDAO: データベース接続エラー"+ e);
	   }
		
	}
	
	//セッションスコープのユーザーIDと、クライアント側のタイムスタンプを使ってTodoListから完了対象のタスクにクエリ送信
	//statusを変更し、クライアント側のリストに表示されなくする（削除する訳ではないので、DBには残ったまま）
	public void completeTaskDAO(String updatetime,int userID) {
		final String UPDATE_SQL ="UPDATE tasks SET status = 1 where userID = ? AND updatetime = ?";
		try {
			System.out.println("completeTaskDAO: 接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("completeTaskDAO: ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("completeTaskDAO: DB接続できました");
			
			conn.setAutoCommit(false);
			
			try(PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
				ps.setInt(1, userID);
				ps.setString(2, updatetime);
				
				ps.executeUpdate();
				conn.commit();
				System.out.println("completeTaskDAO: statusを変更しました");
			} catch (Exception e) {
				conn.rollback();
				System.out.println("rollback");
				throw e;
			}
	        conn.close();
	   } catch (ClassNotFoundException e) {
	       System.out.println("completeTaskDAO: ドライバを読み込めませんでした "+ e);
	   } catch (SQLException e) {
	       System.out.println("completeTaskDAO: データベース接続エラー"+ e);
	   }
	}
	
}

