package sud_ojdbc;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
public class DemoPoll {
	private static String driver;
	private static String url;
	private static String user;
	private static String pass;
	public void initParam(String paramFile) throws Exception{
		Properties props = new Properties();
		props.load(new FileInputStream(paramFile));
		driver = props.getProperty("driver");
		url = props.getProperty("url");
		user = props.getProperty("user");
		pass = props.getProperty("pass");
		System.out.println("导入文件成功");
	}	
	public static void main(String[] args) throws Exception {
		new DemoPoll().initParam("C:\\Users\\Administrator\\Desktop\\mysql.ini");
		System.out.println(driver);
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pass);
		ds.setInitialSize(2);
		ds.setMaxActive(100);
		//使用连接池中的数据库连接
		Connection conn = ds.getConnection();
		String sql = "select * from languages";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		//得到RsesultSetMetaData对象
		ResultSetMetaData rd = rs.getMetaData();
		//获取列的数量
		int col_number = rd.getColumnCount();
		System.out.println("列的数量为"+col_number);
		//获取指定索引的列名与列的类型
		for(int i = 1;i < col_number;i++) {
			System.out.println("索引"+i+"的列名:"+rd.getColumnName(i)+"类型:"+rd.getColumnTypeName(i));
		}
		//使用PreparedStatement执行sql语句可防止sql注入
		PreparedStatement pstm = conn.prepareStatement("insert into jdbc_test values(null,?,1);");
		pstm.setString(1, "ruby");
		pstm.executeUpdate();
		while(rs.next()) {
			System.out.println(rs.getString("name"));
		}
		//归还连接到数据库连接池
		conn.close();
	}

}
