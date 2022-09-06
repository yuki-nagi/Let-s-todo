package account;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class AccountDAO {
	
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/todo";
	private final String DB_USER = "root";
	private final String DB_PASS = "Kurochloe39881052";
	private final String SELECT_SQL = 
			"SELECT userID, username, password, mail, date FROM user WHERE username = ? AND mail = ? AND password = ?";
	private final String INSERT_SQL ="INSERT INTO user(username,password,mail,date) values(?,?,?,?)";
	private final String PASSHASH_SQL = "SELECT date FROM user WHERE username = ? AND mail = ?";
	
	
	public Account findByLogin(LoginStatus LoginStatus) throws Exception {
		Account account = null;
		try {
			System.out.println("接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("DB接続できました");
			PreparedStatement pstmt = conn.prepareStatement(SELECT_SQL);
			pstmt.setString(1, LoginStatus.getUsername());
			pstmt.setString(2, LoginStatus.getMail());
			pstmt.setString(3, LoginStatus.getPassword());
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("SQL文を送信しました");
			
			if(rs.next()) {
				String userID = rs.getString("userID");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String mail = rs.getString("mail");
				Date date = rs.getDate("date");
				System.out.println("結果："+userID+username+password+mail+date);
				account = new Account(userID,username,password,mail,date);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		System.out.println("accountをreturnしました");
		return account;
	}

	
	public void createAccountDAO(String[] userfile) throws Exception{
		
		try {
			System.out.println("接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("DB接続できました");
			
			conn.setAutoCommit(false);
			
			try(PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
				ps.setString(1, userfile[0]);
				ps.setString(2, userfile[1]);
				ps.setString(3, userfile[2]);
				ps.setString(4, userfile[3]);
				
				ps.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				System.out.println("rollback");
				throw e;
			}
			System.out.println("アカウントを作成しました");
	        conn.close();
	   } catch (ClassNotFoundException e) {
	       System.out.println("ドライバを読み込めませんでした "+ e);
	   } catch (SQLException e) {
	       System.out.println("データベース接続エラー"+ e);
	   }
	}
	
	public String findSolt(String username,String mail) {
		try {
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("接続を開始します");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("ドライバを読み込みました");
			Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("DB接続できました");
			PreparedStatement pstmt = conn.prepareStatement(PASSHASH_SQL);
			pstmt.setString(1, username);
			pstmt.setString(2, mail);
			
			ResultSet rs = pstmt.executeQuery();
			
			System.out.println("SQL文を送信しました");
			
			if(rs.next()) {
				date = rs.getDate("date");
			}
			else {
				date = null;
			}
			return sdf.format(date).toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
