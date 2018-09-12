package com.parser.dao;

import com.parser.common.ResultObject;
import com.parser.common.LogData;
import java.util.List;

public interface LogParserDao {
	public ResultObject saveLogData(List<LogData> logDataList);
	public ResultObject findIpByRequest(String startDate,String endDate,String duration,Integer threshHold);
}
