package org.javachina.connectionpool;
/*
 * 连接池组件
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConnectionPool {
	//连接池底层对象
	private static ArrayList<Connection> pool = new ArrayList<Connection>();
	
	private static ResourceBundle bundle = ResourceBundle.getBundle(Constant.CONNECTIONPOOL_FILEPATH);
	//最大连接数
	private static final int max=Integer.parseInt(bundle.getString(Constant.CONNECTIONPOOL_MAX));
	
	//最小连接数
	private static final int min = Integer.parseInt(bundle
			.getString(Constant.CONNECTIONPOOL_MIN));
	private static final String driverName = bundle
			.getString(Constant.CONNECTIONPOOL_DRIVERNAME);
	private static final String url = bundle
			.getString(Constant.CONNECTIONPOOL_URL);
	private static final String userName = bundle
			.getString(Constant.CONNECTIONPOOL_USERNAME);
	private static final String password = bundle
			.getString(Constant.CONNECTIONPOOL_PASSWORD);
	
	static {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//todo:单例模式
	private ConnectionPool(){}
	//对外提供连接
	public static Connection getConnection(){
		//调用keepcount位置连接数量
		//取出一个连接提供出去
		keepCount();
		pool.get(pool.size()-1);
		return pool.remove(pool.size()-1);
	}
	/*
	 * 提供回收方法
	 */
	public static void close(Connection conn){
		//把传过来的连接返回pool
		if(pool.size()<20){
			pool.add(conn);
		}else{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//私有的能够实现创建连接对象
	private static void createConnection(){
		//创建连接放入pool中
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			pool.add(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//维持连接数量
	
	private static void keepCount(){
		//判断池子的size（）
		//低于最小值时创建连接（createconnection）
		//创建到最大值为止
		int size = pool.size();
		if(size<=min){
			while(pool.size()<max){
				createConnection();
			}
		}
	}
	//初始化连接：最初的连接数量达到最大值
	public static void init(){
		//调用createConnection创建20个连接
		for(int i=0;i<max;i++){
			createConnection();
		}
	}
}
