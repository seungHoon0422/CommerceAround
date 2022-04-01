package com.commerce.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	
	private static DBUtil instance = new DBUtil();
	
	private DBUtil() {}
	
	public static DBUtil getInstance() {
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		
		try {
			Context context = new InitialContext();
			Context rootContxt = (Context) context.lookup("java:comp/env");
			DataSource dataSource = (DataSource) rootContxt.lookup("jdbc/ssafy");
			return dataSource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void close(AutoCloseable... closeables) {
		for (AutoCloseable c : closeables) {
			if (c != null) {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
