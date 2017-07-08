package com.dts.ebuy.model;

public class SuggestedItemModel {
private int suggestedod;
private int brandid;
private int categoryid;
private int itemid;
private int goodranking;
private int negativeranking;
private int averageranking;
private byte[] img;
public int getSuggestedod() {
	return suggestedod;
}
public void setSuggestedod(int suggestedod) {
	this.suggestedod = suggestedod;
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

public byte[] getImage(){
	return img;
}
public void setImage(byte[] img){
	this.img=img;
}
public int getGoodranking() {
	return goodranking;
}
public void setGoodranking(int goodranking) {
	this.goodranking = goodranking;
}
public int getNegativeranking() {
	return negativeranking;
}
public void setNegativeranking(int negativeranking) {
	this.negativeranking = negativeranking;
}
public int getAverageranking() {
	return averageranking;
}
public void setAverageranking(int averageranking) {
	this.averageranking = averageranking;
}
}
