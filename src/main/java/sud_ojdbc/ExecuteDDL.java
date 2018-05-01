package sud_ojdbc;

import java.util.Properties;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ExecuteDDL {
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
//创建表
public void createTable(String sql)throws Exception{
	//加载jdbc驱动
	Class.forName(driver);
	
			//获取数据库连接
	Connection conn = DriverManager.getConnection(url, user, pass);
	//创建Statement对象
	Statement stmt = conn.createStatement();	
	stmt.executeUpdate(sql);	
	}
public int insertData(String sql)throws Exception{
	//加载jdbc驱动
	Class.forName(driver);
	
			//获取数据库连接
	Connection conn = DriverManager.getConnection(url, user, pass);
	//创建Statement对象
	Statement stmt = conn.createStatement();	
	return stmt.executeUpdate(sql);	
	}
public static void main(String[] args) throws Exception {
	ExecuteDDL ed = new ExecuteDDL();
	ed.initParam("C:\\Users\\Administrator\\Desktop\\mysql.ini");
	try {
		ed.createTable("create table jdbc_test"
				+"(jdbc_id int auto_increment primary key,"
				+"jdbc_name varchar(255),"
				+"jdbc_desc text);"
					);
		System.out.println("-----创建成功------");
		}
	catch(Exception e){
		System.out.println("数据表已存在");	
		}
	try {
		//注意sql语句的空格
		int result = ed.insertData("insert into jdbc_test(jdbc_name,jdbc_desc) "
				+"select name,heigth "
				+"from languages;"
				);
		System.out.println("共有"+result+"记录受影响");
	} catch (Exception e) {
		System.out.println("插入数据失败");
	}
}

}
