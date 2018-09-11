package com.parser.common;

public class LogData {
	
	private int recNo;
	private String startDate;
	private String ip;
	private String requestType;
	private String status;
	private String userAgent;
	
	public int getRecNo() {
		return recNo;
	}
	public void setRecNo(int recNo) {
		this.recNo = recNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public LogData(){
		super();
	}
	
	public LogData(String startDate, String ip, String requestType, String status, String userAgent) {
		super();
		this.startDate = startDate;
		this.ip = ip;
		this.requestType = requestType;
		this.status = status;
		this.userAgent = userAgent;
	}
	
	@Override
	public String toString() {
		return "LogData [recNo=" + recNo + ", startDate=" + startDate + ", ip=" + ip + ", requestType=" + requestType
				+ ", status=" + status + ", userAgent=" + userAgent + "]";
	}
	
}
