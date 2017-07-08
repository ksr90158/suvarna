package com.dts.ebuy.model;

import java.sql.Date;

public class Query {

	private int queryid;
	private String queryDate;
	private Date queryDate1;
    private String customerID;
    private String description;
    private String solution;
    private String status;
    private String solutionDate;
    private Date solutionDate1;
	public int getQueryid() {
		return queryid;
	}
	public void setQueryid(int queryid) {
		this.queryid = queryid;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public Date getQueryDate1() {
		return queryDate1;
	}
	public void setQueryDate1(Date queryDate1) {
		this.queryDate1 = queryDate1;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSolutionDate() {
		return solutionDate;
	}
	public void setSolutionDate(String solutionDate) {
		this.solutionDate = solutionDate;
	}
	public Date getSolutionDate1() {
		return solutionDate1;
	}
	public void setSolutionDate1(Date solutionDate1) {
		this.solutionDate1 = solutionDate1;
	}
}
