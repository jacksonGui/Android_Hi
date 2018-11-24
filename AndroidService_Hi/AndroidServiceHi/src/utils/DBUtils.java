package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class DBUtils {
	private static String driver;
	private static String name;
	private static String password;
	private static String url;
	
	static{
		Properties prop=new Properties();
		InputStream in=DBUtils.class.getClassLoader().getResourceAsStream("dbConfig.properties");
		try {
			prop.load(in);
			driver=(String) prop.get("driver");
			url=(String) prop.get("url");
			name=(String) prop.get("name");
			password=(String) prop.get("password");
			Class.forName(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url,name,password);
	}
	
	public static void release(ResultSet rs,Statement stmt,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	
	public static List<Map<String,Object>> query(String sql,Object ...params){
		List<Map<String,Object>> list=null;
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null; 
		try {
			conn=getConnection();
			pre=conn.prepareStatement(sql);
			setParams(pre, params);
			rs=pre.executeQuery();
			list=RsToList(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			
			release(rs,pre,conn);
		}
		return list;
	}
	
	public static int update(String sql,Object ...params){
		int count=0;
		Connection conn=null;
		PreparedStatement pre=null;
		ResultSet rs=null; 
		try {
			conn=getConnection();
			pre=conn.prepareStatement(sql);
			setParams(pre, params);
			count=pre.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			
			release(rs,pre,conn);
		}
		return count;
	}
	
	

	private static void setParams(PreparedStatement pre, Object... params)
			throws SQLException {
		if(params!=null){
			for(int i=0;i<params.length;i++){
				pre.setObject(i+1, params[i]);
			}
		}
	}
	
	//rs转换成list
		private static List<Map<String, Object>> RsToList(ResultSet rs) throws SQLException {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			//获取表结构
			ResultSetMetaData rsmd=rs.getMetaData();
			
			//通过循环每次读出一条记录
			while(rs.next()){
				//map用来存储当前读出的一条记录
				Map<String,Object> map=new HashMap<String,Object>();
				
				//通过循环将当前记录中的字段和字段值读出
				for(int i=1;i<=rsmd.getColumnCount();i++){
					//读取字段名rsmd.getCatalogName(i)
					//读取字段值rs.getObject(i)，对应的value
					map.put(rsmd.getColumnName(i).toLowerCase(), rs.getObject(i));
				}
				list.add(map);
			}
			return list;
		}


}
