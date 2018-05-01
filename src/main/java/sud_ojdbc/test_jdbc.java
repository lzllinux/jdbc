package sud_ojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test_jdbc {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		
		try {
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/class",
					"root","123456"
				);
			Statement stmt = conn.createStatement();
			ResultSet s = stmt.executeQuery("select languages.*"+"from languages"
					);
			while(s.next()) {
				System.out.println(s.getInt(1)+"\t"+s.getString(2));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
			

}}
