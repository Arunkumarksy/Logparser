package com.parser.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class LogParserConnection {
	private Connection connection = null;
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public LogParserConnection(){
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/logparser","root","root");
			this.setConnection(con);
		}catch(Exception e){ 
	      e.printStackTrace();  
	    }   
	}
}
