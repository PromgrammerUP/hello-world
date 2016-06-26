package org.javachina.connectionpool;

import java.sql.Connection;

public class Test {
	public static void main(String[] args) {
//		ConnectionPool pool = new ConnectionPool();
//		pool.init();
//		System.out.println("已经初始化20个连接");
//		for(int i=0;i<15000;i++){
//			Connection conn = pool.getConnection();
//			System.out.println(conn);
//			pool.close(conn);
//		}
		ConnectionPool.init();
		System.out.println(ConnectionPool.getConnection());
	}
}
