package com.dts.ebuy.dao;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.DateWrapper;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.model.FeedBackModel;

import java.sql.*;
import java.util.Date;

public class FeedbackDAO extends AbstractDataAccessObject {
	public FeedbackDAO() {

	}
	 public Connection con;
	 private boolean flag;
int n=0;
	public boolean FeedbackItems(FeedBackModel feedBackModel) {
		
		try 
        {
        	con=getConnection();
           con.setAutoCommit(false);
           PreparedStatement pst=null;
           Statement st=con.createStatement();
           int i=0;
        
           int feedbackid = getSequenceID("feedbackdesc", "Feedbackid");
           pst=con.prepareStatement("insert into feedbackdesc values(?,?,?,?,?,?)");
           pst.setInt(1, feedbackid);
			pst.setString(2, feedBackModel.getLoginname());
			pst.setInt(3, feedBackModel.getBrandid());
			pst.setInt(4, feedBackModel.getCategoryid());
			pst.setInt(5, feedBackModel.getItemid());
			pst.setString(6, feedBackModel.getFeedbackdesc());
			
           
           i=pst.executeUpdate();
           
          
            if(i==1)
            {
                flag=true;
                con.commit();
            }
            else
            {
                flag=false;
                con.rollback();
            } 
            con.close();
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
            flag=false;
            try 
            {
                con.rollback();
            } 
            catch (SQLException sex) 
            {
                sex.printStackTrace();
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            flag=false;
            try 
            {
                con.rollback();
            } 
            catch (SQLException se) 
            {
                se.printStackTrace();
            }
        }
		
		
		
		return flag;
		
		
	}

	
	public String fbDesc(String loginname,int bid,int cid,int iid)
	{
		String fb3=null;
		 Statement st=null;
		 System.out.println("yfjc"+loginname+"dfdvcvdcv"+bid+"fyvcvciya"+cid+"cvyvcy"+iid);
		 
		 try 
			{
				con = getConnection();
				st = con.createStatement();
				ResultSet rs = st.executeQuery("select fbdesc from feedbackdesc where Loginname='"+loginname+"' and brandid='"+bid+"' and categoryid='"+cid+"' and itemid='"+iid+"'");
				while(rs.next())
				{
					fb3 = rs.getString(1);
				}
			} 
			catch (SQLException e)
			{
				LoggerManager.writeLogWarning(e);
			}
			finally
			{
				try
				{
				  con.close();
				}
				catch(Exception e)
				{
					LoggerManager.writeLogWarning(e);
				}
			}
		 
		
		System.out.println("In DAO"+fb3);
		return fb3;
		
	}
	
	public boolean registerSuggestedItems(FeedBackModel feedBackModel)
{
	Connection connection=null;
	boolean flag=false;
	try{
		connection=getConnection();
		String status=feedBackModel.getFeedbackdesc();
		int suggestid=updateStatus(feedBackModel);
		String updatesql="";
		if(suggestid!=0){
			if(status.equalsIgnoreCase("Good")){
			
				updatesql=	"update SuggestedItems set goodranking=goodranking+? where suggestedid=?";
			}else if(status.equalsIgnoreCase("Bad"))
			{	updatesql=	"update SuggestedItems set negativeranking=negativeranking+? where suggestedid=?";
				
			}else if(status.equalsIgnoreCase("Average")){
				updatesql=	"update SuggestedItems set averageranking=averageranking+? where suggestedid=?";
			}
			
			PreparedStatement pst1=connection.prepareStatement(updatesql);
			pst1.setInt(1,1);
			pst1.setInt(2,suggestid);
			int x=pst1.executeUpdate();
			}else{
		
		int suggestinonid=getSequenceID("SuggestedItems", "suggestedid");
	
		
		
		String insertsql="";
		
		if(status.equalsIgnoreCase("Good")){
			insertsql="insert into SuggestedItems(suggestedid,brandid,categoryid,itemid,goodranking) values(?,?,?,?,?)";
			
		}else if(status.equalsIgnoreCase("Bad"))
		{
			insertsql="insert into SuggestedItems(suggestedid,brandid,categoryid,itemid,negativeranking) values(?,?,?,?,?)";
			
		}else if(status.equalsIgnoreCase("Average")){
			insertsql="insert into SuggestedItems(suggestedid,brandid,categoryid,itemid,averageranking) values(?,?,?,?,?)";
					}
		PreparedStatement pst=connection.prepareStatement(insertsql);
		pst.setInt(1,suggestinonid);
		pst.setInt(2, feedBackModel.getBrandid());
		pst.setInt(3,feedBackModel.getCategoryid());
		pst.setInt(4,feedBackModel.getItemid());
		pst.setInt(5, 1);
		int n=pst.executeUpdate();
		if(n>0)
		{
			flag=true;
		}
		connection.close();
		}}catch (Exception e) {
		e.printStackTrace();
		// TODO: handle exception
	}
	return flag;
}
	public int updateStatus(FeedBackModel feedBackModel){
		int suggestid=0;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pst=connection.prepareStatement("select suggestedid from suggesteditems where brandid=? and categoryid=? and itemid=?");
	pst.setInt(1, feedBackModel.getBrandid());
	pst.setInt(2,feedBackModel.getCategoryid());
	pst.setInt(3,feedBackModel.getItemid());
	ResultSet rs=pst.executeQuery();
	while(rs.next()){
		suggestid=rs.getInt(1);
	}
	connection.close();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return suggestid;
	}
	
}
