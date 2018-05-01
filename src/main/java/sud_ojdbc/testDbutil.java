package sud_ojdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testDbutil {

	public static void main(String[] args) {
		System.out.println("k");
		String sql = "select * from languages";
		System.out.println(sql);
		try {
			Connection conn = DButil2.getConnection();
			System.out.println("k");
			Statement st = conn.createStatement();
			System.out.println("k");
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				System.out.println(rs.getString(2));
			}
			DButil2.closeConnection(conn);
		} catch (SQLException e) {
			new  Exception ("获取连接失败");
		
		}
		finally {
			
		}
		
	}

}

