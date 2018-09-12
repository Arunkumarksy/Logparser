package com.parser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import java.util.ArrayList;


import com.parser.common.LogData;
import com.parser.common.RequestedLog;
import com.parser.common.ResultObject;
import com.parser.connection.JDBCUtil;
import com.parser.constants.ParserConstants;
import com.parser.exception.ParserException;

/**
 * 
 * @author arunkumar.k
 *
 */
public class LogParserDaoImpl implements LogParserDao{
	private static final Logger LOGGER = Logger.getLogger(LogParserDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.parser.dao.LogParserDao#saveLogData(java.util.List)
	 */
	public ResultObject saveLogData(List<LogData> logDataList) {
		LOGGER.info("LogParserDaoImpl Executing");
		ResultObject resultObject = new ResultObject();
		Connection connection = null;
		PreparedStatement prepStatement = null;
		try {
			connection = JDBCUtil.getConnection();
			if (connection != null) {
				String truncateQuery = "truncate table logDetails";
				prepStatement = connection.prepareStatement(truncateQuery);
				prepStatement.executeUpdate();
				prepStatement.close();
				/*long startTime = System.currentTimeMillis();
			    System.out.println(" start exec time :"+System.currentTimeMillis());*/
				String insertQuery = "Insert into logDetails(StartDate,Ip,Request,Status,UserAgent) VALUES(?,?,?,?,?)";
				prepStatement = connection.prepareStatement(insertQuery);
				connection.setAutoCommit(false);
				int i = 0;
				for (LogData logData : logDataList) {
					prepStatement.setString(1, logData.getStartDate());
					prepStatement.setString(2, logData.getIp());
					prepStatement.setString(3, logData.getRequestType());
					prepStatement.setString(4, logData.getStatus());
					prepStatement.setString(5, logData.getUserAgent());
					prepStatement.addBatch();
					i++;
					if (i % 1000 == 0) {
						prepStatement.executeBatch(); // Execute every 1000 items.
					}
				}
				prepStatement.executeBatch();
				connection.commit();
				//System.out.println(" Total exec time : "+(System.currentTimeMillis()-startTime)/1000);
				resultObject.setErrorCode(ParserConstants.SUCCESS_CODE);
			}
		} catch (SQLException ex) {
			resultObject.setErrorCode(ParserConstants.ERROR_CODE);
			resultObject.setErrorMessage(ex.getMessage());
			throw new ParserException(ParserConstants.SQL_EXCEPTION,ex.getCause());
		} catch (Exception ex) {
			resultObject.setErrorCode(ParserConstants.ERROR_CODE);
			resultObject.setErrorMessage(ex.getMessage());
			throw new ParserException(ParserConstants.EXCEPTION,ex.getCause());
		}
		finally{
			JDBCUtil.closeStatement(prepStatement);
			JDBCUtil.closeConnection(connection);
		}
		return resultObject;
	}

	/* (non-Javadoc)
	 * @see com.parser.dao.LogParserDao#findIpByRequest(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public ResultObject findIpByRequest(String startDate,String endDate,String duration,Integer threshHold) {
		LOGGER.info("findIpByRequest Executing");
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<RequestedLog> requestList = null; 
		ResultObject resultObjectData = new ResultObject();
		try{
			connection = JDBCUtil.getConnection();
			String comment = "BLOCKED FOR CROSSING MORE THAN "+threshHold;
			String selectQuery = "select StartDate,Ip,count(ip) as totalRequest,If(count(ip)> "+"'"+threshHold+"',"+"'"+comment+"','') as Comment from logDetails where StartDate between DATE_FORMAT('"+startDate+"','%Y-%m-%d.%H:%i:%s') and DATE_FORMAT('"+endDate+"','%Y-%m-%d.%H:%i:%s') GROUP BY ip having count(ip) > "+threshHold+"";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectQuery);
			requestList = new ArrayList<RequestedLog>();
			while (resultSet.next()){
		        requestList.add(new RequestedLog(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
		      }
			saveRequestData(requestList);
			resultObjectData.setObject(requestList);
			resultObjectData.setErrorCode(ParserConstants.SUCCESS_CODE);
		}
		catch(Exception ex){
			resultObjectData.setErrorCode(ParserConstants.ERROR_CODE);
			throw new ParserException(ParserConstants.EXCEPTION,ex.getCause());
		}
		finally{
			JDBCUtil.closeResultSet(resultSet);
			JDBCUtil.closeStatement(statement);
			JDBCUtil.closeConnection(connection);
		}
	    return resultObjectData;
	}
	
	/**
	 * @param requestedLogList
	 * @return
	 */
	public ResultObject saveRequestData(List<RequestedLog> requestedLogList) {
		LOGGER.info("findIpByRequest Executing");
		Connection connection = null;
		PreparedStatement statement = null;
		ResultObject resultObject = new ResultObject();
		try {
			connection = JDBCUtil.getConnection();
			String truncateQuery = "truncate table logrequests";
			statement = connection.prepareStatement(truncateQuery);
			statement.executeUpdate();
			statement.close();
			
			String insertQuery = "Insert into logrequests(StartDate,Ip,TotalRequest,Comment) VALUES(?,?,?,?);";
			statement = connection.prepareStatement(insertQuery);
			connection.setAutoCommit(false);
			int i = 0;
			for (RequestedLog logData : requestedLogList) {
				statement.setString(1, logData.getStartDate());
				statement.setString(2, logData.getIp());
				statement.setString(3, logData.getTotalRequest());
				statement.setString(4, logData.getComment());
				statement.addBatch();
				i++;
				if (i % 1000 == 0) {
					statement.executeBatch(); // Execute every 1000 items.
				}
				statement.executeBatch();
				connection.commit();
			}
			resultObject.setErrorCode(ParserConstants.SUCCESS_CODE);
		} catch (Exception ex) {
			resultObject.setErrorCode(ParserConstants.ERROR_CODE);
			resultObject.setErrorMessage(ex.getMessage());
			throw new ParserException(ParserConstants.EXCEPTION,ex.getCause());
		}
		finally{
			JDBCUtil.closeStatement(statement);
			JDBCUtil.closeConnection(connection);
		}
		return resultObject;
	}
}
