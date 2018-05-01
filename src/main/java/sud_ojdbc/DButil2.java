package sud_ojdbc;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 使用连接池技术管理数据库连接
 * 
 * @author Administrator
 *
 */
public class DButil2 {
	// 数据库连接池
	private static BasicDataSource ds;
	// 为不同线程管理连接
	private static ThreadLocal<Connection> tl;

	static {
		try {
			Properties prop = new Properties();

			// InputStream is =
			// DButil2.class.getClassLoader().getResourceAsStream("day01/config.properties");
			FileInputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\mysql.ini");
			prop.load(is);
			is.close();
			System.out.println("k");
			// 初始化连接池

			ds = new BasicDataSource();
			// 设置驱动 (Class.forName())
			ds.setDriverClassName(prop.getProperty("driver"));
			// 设置url
			ds.setUrl(prop.getProperty("url"));
			// 设置数据库用户名
			ds.setUsername(prop.getProperty("user"));
			// 设置数据库密码
			ds.setPassword(prop.getProperty("pwd"));
			// 初始连接数量
			ds.setInitialSize(Integer.parseInt(prop.getProperty("initsize")));
			// 连接池允许的最大连接数
			ds.setMaxActive(Integer.parseInt(prop.getProperty("maxactive")));
			// 设置最大等待时间
			// ds.setMaxWait(Integer.parseInt(prop.getProperty("maxwait")));
			// // 设置最小空闲数
			// ds.setMinIdle(Integer.parseInt(prop.getProperty("minidle")));
			// // 设置最大空闲数
			// ds.setMaxIdle(Integer.parseInt(prop.getProperty("maxidle")));
			// // 初始化线程本地
			tl = new ThreadLocal<Connection>();
			System.out.println("k");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @throws SQLException
	 */
	// String sql = "select * from languages";

	public static Connection getConnection() {
		/*
		 * 通过连接池获取一个空闲连接
		 */
		try {
			Connection conn = ds.getConnection();
			return conn;
		} 
		catch (Exception e) {
			//System.out.println("j");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// tl.set(conn);
	
	
	}

	/**
	 * 关闭数据库连接
	 */
	public static void closeConnection(Connection conn) {
		try {

			if (conn != null) {
				/*
				 * 通过连接池获取的Connection 的close()方法实际上并没有将 连接关闭，而是将该链接归还。
				 */
				conn.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
