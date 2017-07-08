package com.dts.ebuy.dao;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.ebuy.model.SuggestedItemModel;

import java.sql.*;
public class SuggestedItemDAO extends AbstractDataAccessObject {

	public CoreHash getSuggestedItems(int brandid){
		CoreHash acorehash=new CoreHash();
		Connection connection=null;
		SuggestedItemModel suggestedItemModel=null;
		try{
			connection=getConnection();
		PreparedStatement pst=connection.prepareStatement("select suggestedid,brandid,categoryid,itemid from SuggestedItems  where brandid=? ORDER by goodranking desc,negativeranking,averageranking desc");
		pst.setInt(1,brandid);
		ResultSet rs=pst.executeQuery();
		int i=0;
		while(rs.next()){
			suggestedItemModel=new SuggestedItemModel();
			suggestedItemModel.setSuggestedod(rs.getInt(1));
			suggestedItemModel.setBrandid(rs.getInt(2));
			suggestedItemModel.setCategoryid(rs.getInt(3));
			suggestedItemModel.setItemid(rs.getInt(4));
			acorehash.put(new Integer(i), suggestedItemModel);
			i++;
			if(i==2){
				break;
			}
		}
		connection.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return acorehash;
	}
	public CoreHash getSuggestedItemsByCategory(int catid){
		CoreHash acorehash=new CoreHash();
		Connection connection=null;
		SuggestedItemModel suggestedItemModel=null;
		try{
			connection=getConnection();
		PreparedStatement pst=connection.prepareStatement("select suggestedid,brandid,categoryid,itemid from SuggestedItems  where categoryid=? ORDER by goodranking desc,negativeranking,averageranking desc");
		pst.setInt(1,catid);
		ResultSet rs=pst.executeQuery();
		int i=0;
		while(rs.next()){
			suggestedItemModel=new SuggestedItemModel();
			suggestedItemModel.setSuggestedod(rs.getInt(1));
			suggestedItemModel.setBrandid(rs.getInt(2));
			suggestedItemModel.setCategoryid(rs.getInt(3));
			suggestedItemModel.setItemid(rs.getInt(4));
			acorehash.put(new Integer(i), suggestedItemModel);
			i++;
		}
		connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return acorehash;
	}
	public CoreHash getSuggestedItemsByItem(int itemid){
		CoreHash acorehash=new CoreHash();
		Connection connection=null;
		SuggestedItemModel suggestedItemModel=null;
		try{
			connection=getConnection();
		PreparedStatement pst=connection.prepareStatement("select suggestedid,brandid,categoryid,itemid from SuggestedItems where itemid=? and ORDER by goodranking desc,negativeranking,averageranking desc");
		pst.setInt(1,itemid);
		ResultSet rs=pst.executeQuery();
		int i=0;
		while(rs.next()){
			suggestedItemModel=new SuggestedItemModel();
			suggestedItemModel.setSuggestedod(rs.getInt(1));
			suggestedItemModel.setBrandid(rs.getInt(2));
			suggestedItemModel.setCategoryid(rs.getInt(3));
			suggestedItemModel.setItemid(rs.getInt(4));
			acorehash.put(new Integer(i), suggestedItemModel);
			i++;
		}
		connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return acorehash;
	}
	
	public int getQtyDetails(int brandid,int catid,int itemid){
		int qty=0;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pst=connection.prepareStatement("select Quantity from itemorder where BrandID=? and CategoryID=? and ItemID=?");
			pst.setInt(1, brandid);
			pst.setInt(2, catid);
			pst.setInt(3, itemid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				qty=rs.getInt(1);	
			}connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return qty;
	}
	public int getpriceDetails(int brandid,int catid,int itemid){
		int price=0;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pst=connection.prepareStatement("select Quantity from itemorder where BrandID=? and CategoryID=? and ItemID=?");
			pst.setInt(1, brandid);
			pst.setInt(2, catid);
			pst.setInt(3, itemid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				price=rs.getInt(1);	
			}connection.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return price;
	}
}
