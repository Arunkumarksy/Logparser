package com.parser.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.parser.common.LogData;
import com.parser.common.ResultObject;
import com.parser.constants.ParserConstants;
import com.parser.dao.LogParserDao;
import com.parser.dao.LogParserDaoImpl;
import com.parser.exception.ParserException;


/**
 * 
 * @author arunkumar.k
 *
 */
public class LogParserServiceImpl implements LogParserService{
	
	private static final Logger LOGGER = Logger.getLogger(LogParserServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.parser.service.LogParserService#loadLogFile(java.lang.String)
	 */
	public ResultObject loadLogFile(String logfilePath) throws ParseException {
		LOGGER.info("loadLogFile executing");
		BufferedReader br = null;
		FileReader fr = null;
		ResultObject resultObject = null;
		List<LogData> logDataList = new ArrayList<LogData>();
		try {
			fr = new FileReader(logfilePath);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] split = sCurrentLine.split(Pattern.quote("|"));
				try {
					if (split != null) {
						logDataList.add(new LogData(split[0], split[1], split[2], split[3], split[4]));
					}
				} catch (IndexOutOfBoundsException ex) {
					LOGGER.error("IndexOutOfBoundsException  at loadLogFile :" + ex.getCause(), ex);
				}
			}
		} catch (FileNotFoundException ex) {
		    throw new ParserException(ParserConstants.FILE_NOT_FOUND_EXCEPTION, ex.getCause());
		} 
		catch (IndexOutOfBoundsException ex) {
		    throw new ParserException(ParserConstants.FILE_NOT_FOUND_EXCEPTION, ex.getCause());
		} 
		catch (IOException e) {
			throw new ParserException(ParserConstants.IO_EXCEPTION,e.getCause());
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (logDataList.size() > 0) {
			LogParserDao logParserDao = new LogParserDaoImpl();
			resultObject = logParserDao.saveLogData(logDataList);
		}
		return resultObject;
	}
	
	/* (non-Javadoc)
	 * @see com.parser.service.LogParserService#findIpByRequest(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public ResultObject findIpByRequest(String startDate, String duration, Integer threshHold) throws ParseException {
		LOGGER.info("findIpByRequest executing");
		DateFormat customFormat = new SimpleDateFormat(ParserConstants.DATE_FORMAT2);
		Date date = customFormat.parse(startDate);
		Calendar cal = Calendar.getInstance();
		String formattedEndDate = null;
		LogParserDao logParserDao = new LogParserDaoImpl();
		ResultObject resultObject = null;
		if (duration.equals(ParserConstants.DURATION_HOURLY)) {
			cal.setTime(date); // sets calendar time/date
			cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
			formattedEndDate = customFormat.format(cal.getTime());
			resultObject = logParserDao.findIpByRequest(startDate, formattedEndDate, duration, threshHold);
		} else if (duration.equals(ParserConstants.DURATION_DAILY)) {
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, 1); // adds one day
			formattedEndDate = customFormat.format(cal.getTime());
			resultObject = logParserDao.findIpByRequest(startDate, formattedEndDate, duration, threshHold);
		} else {
			System.out.println("Invalid Duration");
		}
		return resultObject;
	}
}
