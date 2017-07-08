package com.dts.ebuy.model;

public class InventoryAudit {

	private int brandID;
	private int categoryID;
	private int itemID;
	private int quantity;
	private double price;
	private double vat;
	public int getBrandID() {
		return brandID;
	}
	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
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
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	
	
}
