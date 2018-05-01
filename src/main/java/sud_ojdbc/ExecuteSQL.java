package sud_ojdbc;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ExecuteSQL {
	/**
	 * 使用mysql.ini配置jdbc
	 */
	private String driver;
	private String url;
	private String user;
	private String pass;
	//param参数
	//Properties 键值对
public void initParam(String paramFile) throws Exception{
	Properties props = new Properties();
	props.load(new FileInputStream(paramFile)); 
	driver = props.getProperty("driver");
	url = props.getProperty("url");
	user = props.getProperty("user");
	pass = props.getProperty("pass");
	System.out.println("导入文件成功");
}	
public void query(String sql) throws ClassNotFoundException {
	Class.forName(driver);
	try {
		Connection conn = DriverManager.getConnection(url,user,pass);
		PreparedStatement pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = pstmt.executeQuery();
		rs.last();
		int rowCount = rs.getRow();
		System.out.println(rowCount);
		for(int i = rowCount;i>0;i--) {
			rs.absolute(i);
			System.out.println(rs.getString(1)+"\t"+rs.getString(2));
			rs.updateString(2, "学生"+i);
			rs.updateRow();
		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
	
	
}
public static void main(String[] args) throws Exception {
	ExecuteSQL rt = new ExecuteSQL();
	rt.initParam("C:\\Users\\Administrator\\Desktop\\mysql.ini");
	rt.query("select * from jdbc_test");

	}

}
