package com.parser.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.parser.common.LoadProperties;
import com.parser.constants.ParserConstants;
import com.parser.exception.ParserException;

public class JDBCUtil {
	
	static Connection connection;
	static Properties properties;
	
	 static {
	  properties = LoadProperties.loadPropertyFile();
	  try {
	   Class.forName(properties.getProperty("jdbc.driver"));
	  } catch (ClassNotFoundException clnfe) {
	   throw new ParserException(ParserConstants.CLASS_NOT_FOUND,clnfe.getCause());
	  }
	 }

	 public static Connection getConnection() throws SQLException {
	  return connection = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"),properties.getProperty("jdbc.password"));
	 }

	 public static void closeConnection(Connection connection) {
	  try {
	   if (null != connection) {
	    connection.close();
	   }
	  } catch (SQLException e) {
	   throw new ParserException(ParserConstants.SQL_EXCEPTION,e.getCause());
	  }
	 }

	 public static void closeStatement(Statement stmt) {
	  try {
	   if (null != stmt) {
	    stmt.close();
	   }
	  } catch (SQLException e) {
	   throw new ParserException(ParserConstants.SQL_EXCEPTION,e.getCause());
	  }
	 }

	 public static void closeResultSet(ResultSet rs) {
	  try {
	   if (null != rs) {
	    rs.close();
	   }
	  } catch (SQLException e) {
	   throw new ParserException(ParserConstants.SQL_EXCEPTION,e.getCause());
	  }
	 }

}
