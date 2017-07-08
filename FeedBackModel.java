package com.dts.ebuy.model;

public class FeedBackModel implements java.io.Serializable {

	private int feedbackid;
	private String loginname;
	private int brandid;
	private int categoryid;
	private int itemid;
	private String itemname;
	private String feedbackdesc;
	private String fb;
	
	public int getFeedbackid() {
		return feedbackid;
	}
	public void setFeedbackid(int feedbackid) {
		this.feedbackid = feedbackid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public int getBrandid() {
		return brandid;
	}
	public void setBrandid(int brandid) {
		this.brandid = brandid;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getFeedbackdesc() {
		return feedbackdesc;
	}
	public void setFeedbackdesc(String feedbackdesc) {
		this.feedbackdesc = feedbackdesc;
	}
	public String getFb() {
		return fb;
	}
	public void setFb(String fb) {
		this.fb = fb;
	}
}
