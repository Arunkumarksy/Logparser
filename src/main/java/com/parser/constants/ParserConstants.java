package com.parser.constants;

public interface ParserConstants {
	public int SUCCESS_CODE = 100;
	public int ERROR_CODE = -1;
	public String DURATION_DAILY = "daily";
	public String DURATION_HOURLY = "hourly";
	public String LOG_FILE_PATH = "C:\\Arun\\DO_Test\\workspace\\LogParser\\log\\access.log";
	public String DATE_FORMAT1 = "YYYY-MM-dd HH:mm:ss";
	public String DATE_FORMAT2 = "YYYY-MM-dd.HH:mm:ss";
	public String PROPERTY_FILE_NAME = "parser.properties";
	public String CLASS_NOT_FOUND = "Class Not Found on specified path";
	public String SQL_EXCEPTION = "An SQL Exception Occurred at Database operation";
	public String FILE_NOT_FOUND_EXCEPTION = "File Not Found on the specified path";
	public String INDEX_OUT_OF_BOUND_EXCEPTION = "Array Index Out Of Bounds Exception occured while parsing log file";
	public String IO_EXCEPTION = "Io Exception occuered while performing io operation on file";
	public String EXCEPTION = "An Exception Occured";
	
	public String INVALID_START_DATE = "Invalid Input for startDate";
	public String INVALID_DURATION = "Invalid Input for Duration";
	public String INVALID_THRESHOLD = "Invalid Input for threshHold";
	
	
	
	
	
	
	
	
}
