package com.dts.ebuy.model;

public class Item {
   
	private int itemID;
	private int categoryID;
	private String itemName;
	private String itemDesc;
	private byte[] img;
	
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
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public byte[] getImage(){
		return img;
	}
	public void setImage(byte[] img){
		this.img=img;
	}
	
	
	}
