package com.dts.ebuy.model;

import java.sql.Date;

public class Orders {

	private int orderID;
	private String loginname;
	private String orderDate;
	private Date orderDate1;
	private double totalamount;
	private String status;
	private int brandid;
	private int categoryid;
	private int itemid;
	
	private String brandids[];
	private String categoryids[];
	private String itemids[];
	
	
	private int quantity;
	private double price;
	
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getOrderDate1() {
		return orderDate1;
	}
	public void setOrderDate1(Date orderDate1) {
		this.orderDate1 = orderDate1;
	}
	public String[] getBrandids() {
		return brandids;
	}
	public void setBrandids(String[] brandids) {
		this.brandids = brandids;
	}
	public String[] getCategoryids() {
		return categoryids;
	}
	public void setCategoryids(String[] categoryids) {
		this.categoryids = categoryids;
	}
	public String[] getItemids() {
		return itemids;
	}
	public void setItemids(String[] itemids) {
		this.itemids = itemids;
	}
}
