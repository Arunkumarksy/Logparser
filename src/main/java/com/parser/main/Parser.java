package com.parser.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.parser.common.RequestedLog;
/*import org.apache.log4j.Logger;*/
import com.parser.common.ResultObject;
import com.parser.constants.ParserConstants;
import com.parser.service.LogParserService;
import com.parser.service.LogParserServiceImpl;

/**
 * 
 * @author arunkumar.k
 * Main Class for LogParser
 */
public class Parser {
	private static final Logger LOGGER = Logger.getLogger(Parser.class);

	public static void main(String args[]) throws ParseException {
		LOGGER.info("main function executing");
		LogParserService logParserService = new LogParserServiceImpl();
		String accessLog = null;
		String startDate = null;
		String duration = null;
		String threshHold = null;
		int threshHolds = 0;
		boolean validInput = false;
		DateFormat customFormat = new SimpleDateFormat(ParserConstants.DATE_FORMAT2);
		if (args != null) {
			if (args[0] != null) {
				accessLog = args[0].substring(args[0].lastIndexOf("=") + 1);
				try {
					File f = new File(accessLog);
					if(f.exists() && !f.isDirectory()) { 
						validInput = true;
					}else{
						System.out.println(ParserConstants.INVALID_LOG_FILE_PATH);
						validInput = false;
					}
				} catch (Exception ex) {
					System.out.println(ParserConstants.INVALID_LOG_FILE_PATH);
					validInput = false;
				}
			}else{
				validInput = false;
				System.out.println(ParserConstants.INVALID_LOG_FILE_PATH);
			}
			
			if (args[1] != null) {
				startDate = args[1].substring(args[1].lastIndexOf("=") + 1);
				try {
					customFormat.parse(startDate);
					if (validInput != false) {
					validInput = true;
					}
				} catch (ParseException ex) {
					System.out.println(ParserConstants.INVALID_START_DATE);
					validInput = false;
				}
			}else{
				System.out.println(ParserConstants.INVALID_START_DATE);
				validInput = false;
			}
			
			
			if (args[2] != null) {
				duration = args[2].substring(args[2].lastIndexOf("=") + 1);
				// System.out.println(" duration :"+duration);
				if (duration.equals(ParserConstants.DURATION_DAILY)
						|| duration.equals(ParserConstants.DURATION_HOURLY)) {
					if (validInput != false) {
						validInput = true;
					}
				} else {
					validInput = false;
					System.out.println(ParserConstants.INVALID_DURATION);
				}
			}else{
				validInput = false;
				System.out.println(ParserConstants.INVALID_DURATION);
			}
			if (args[3] != null) {
				threshHold = args[3].substring(args[3].lastIndexOf("=") + 1);
				try {
					threshHolds = Integer.parseInt(threshHold);
					if (validInput != false) {
						validInput = true;
					}
				} catch (NumberFormatException nfe) {
					validInput = false;
					System.out.println(ParserConstants.INVALID_THRESHOLD);
				}
			}else{
				validInput = false;
				System.out.println(ParserConstants.INVALID_THRESHOLD);
			}
			
			if (validInput) {
				ResultObject resultObject = logParserService.loadLogFile(accessLog);
				if (resultObject == null || resultObject.equals(ParserConstants.ERROR_CODE)) {
					System.out.println(resultObject.getErrorMessage());
				}
				ResultObject resultDataObject = logParserService.findIpByRequest(startDate.trim(), duration.trim(),
						threshHolds);
				if (resultDataObject != null && resultDataObject.getErrorCode() == ParserConstants.SUCCESS_CODE) {
					List<RequestedLog> requestList = (List<RequestedLog>) resultDataObject.getObject();
					for (RequestedLog requestLog : requestList) {
						System.out.println(requestLog);
					}
				} else {
					System.out.println(resultDataObject.getErrorMessage());
				}
			}
		}
	}
	
}