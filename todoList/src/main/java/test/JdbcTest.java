package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

public class JdbcTest {
	
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/todo";
	private final String DB_USER = "root";
	private final String DB_PASS = "Kurochloe39881052";
	private final String SQL ="INSERT INTO user(username,password,mail,date) values(?,?,?,?)";
	private final String[] userfile = {"田中","2cf998400a1f067a2690a98d212550b9827c1aead8857c167d6e43e4fe0485d9","mail","2022-08-15"};

	@Test
	public void testName() throws Exception{

		
		
			try {
				System.out.println("接続を開始します");
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("ドライバを読み込みました");
				Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				System.out.println("DB接続できました");
				
				conn.setAutoCommit(false);
				
				try(PreparedStatement ps = conn.prepareStatement(SQL)) {
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

		        conn.close();
		       System.exit(0);
		   } catch (ClassNotFoundException e) {
		       System.out.println("ドライバを読み込めませんでした "+ e);
		   } catch (SQLException e) {
		       System.out.println("データベース接続エラー"+ e);
		   }
		}
	}


