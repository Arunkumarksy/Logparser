package com.parser.main;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/*import org.apache.log4j.Logger;*/
import com.parser.common.ResultObject;
import com.parser.constants.ParserConstants;
import com.parser.service.LogParserService;
import com.parser.service.LogParserServiceImpl;

/**
 * 
 * @author arunkumar.k
 *
 */
public class LogParser {
	private static final Logger LOGGER = Logger.getLogger(LogParser.class);
	public static void main(String args[]) throws ParseException{
		LOGGER.info("main function executing");
		LogParserService logParserService = new LogParserServiceImpl();
		String startDate = null;
		String duration  = null;
		String threshHold = null;
		int threshHolds = 0;
		boolean validInput = false;
		Date date = null;
		DateFormat customFormat = new SimpleDateFormat(ParserConstants.DATE_FORMAT2);
		if(args!=null){
			if(args[0]!=null){
				startDate = args[0].substring(args[0].lastIndexOf("=")+1);
				try{
				 date = customFormat.parse(startDate);
				 validInput = true;
				}
				catch(ParseException ex){
					System.out.println("Invalid Input for startDate");
					validInput = false;
				}
			}
			if(args[1]!=null){
				duration = args[1].substring(args[1].lastIndexOf("=")+1);
				//System.out.println(" duration :"+duration);
				if(duration.equals(ParserConstants.DURATION_DAILY) || duration.equals(ParserConstants.DURATION_HOURLY)){
					if(validInput!=false){
					validInput = true;
					}
				} else{
					validInput = false;	
					System.out.println("Invalid Input for Duration");	
				}
			}
			if(args[2]!=null){
				threshHold = args[2].substring(args[2].lastIndexOf("=")+1);
				//System.out.println(" threshHold :"+threshHold);
				 try{
					 threshHolds =  Integer.parseInt(threshHold);
					 if(validInput!=false){
					 validInput = true;
					 }
				 }
				 catch(NumberFormatException nfe){
					validInput = false;
					System.out.println("Invalid Input for threshHold");	 
				 }
			}
			if(validInput){
		    ResultObject resultObject = logParserService.loadLogFile(ParserConstants.LOG_FILE_PATH);	
		    if(resultObject==null || resultObject.equals(ParserConstants.ERROR_CODE)){
		    	System.out.println(resultObject.getErrorMessage());
		    }
		    ResultObject resultDataObject = logParserService.findIpByRequest(startDate.trim(), duration.trim(), threshHolds);
 			if(resultDataObject==null || resultDataObject.equals(ParserConstants.ERROR_CODE)){
 				System.out.println(resultDataObject.getErrorMessage());
 			}
			} 			
		}
	}
}
