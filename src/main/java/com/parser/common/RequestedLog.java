package com.parser.common;

public class RequestedLog {

	private String ip;
	private String totalRequest;
	private String comment;
	private String startDate;
	
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
	public String getTotalRequest() {
		return totalRequest;
	}
	public void setTotalRequest(String totalRequest) {
		this.totalRequest = totalRequest;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public RequestedLog(){
		super();
	}
	
	public RequestedLog(String ip, String totalRequest, String comment) {
		super();
		this.ip = ip;
		this.totalRequest = totalRequest;
		this.comment = comment;
	}
	
	public RequestedLog(String startDate,String ip, String totalRequest, String comment) {
		super();
		this.ip = ip;
		this.totalRequest = totalRequest;
		this.comment = comment;
		this.startDate = startDate;
	}
	
	@Override
	public String toString() {
		return "RequestedLog [ip=" + ip + ", totalRequest=" + totalRequest + ", comment=" + comment + "]";
	}
	
}
