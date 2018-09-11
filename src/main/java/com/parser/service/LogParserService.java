package com.parser.service;

import java.text.ParseException;
import com.parser.common.ResultObject;

public interface LogParserService  {
	public ResultObject loadLogFile(String logfilePath) throws ParseException;
    public ResultObject findIpByRequest(String startDate,String duration,Integer threshHold) throws ParseException;
}
