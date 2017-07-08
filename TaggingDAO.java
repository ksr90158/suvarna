package com.dts.ebuy.dao;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.ebuy.model.TaggingModel;

import java.sql.*;
public class TaggingDAO extends AbstractDataAccessObject {

	public TaggingDAO(){
		
	}
	
	public boolean regstertag(TaggingModel taggingModel){
		Connection connection=null;
		try{
			connection=getConnection();
			String sql="";
			boolean status=checkStatus(taggingModel);
			if(status){
				sql="update TaggingSystem set rank=rank+1 where taggingname='"+taggingModel.getTaggingname()+"'";
				
			}else{
				sql="insert into taggingsystem values('"+taggingModel.getTaggingname()+"',1)";
			}
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
		int n=preparedStatement.executeUpdate();
			if(n>0){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
}
	
	public boolean checkStatus(TaggingModel taggingModel){
		Connection connection=null;
		boolean flag=false;
		try{
			connection=getConnection();
			PreparedStatement pst=connection.prepareStatement("select * from TaggingSystem where taggingname=?");
		pst.setString(1,taggingModel.getTaggingname());
ResultSet rs=pst.executeQuery();
while(rs.next()){
	flag=true;
}connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
}