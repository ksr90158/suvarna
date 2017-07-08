package com.dts.ebuy.dao;

import com.dts.core.dao.AbstractDataAccessObject; 
import com.dts.core.util.DateWrapper;
import com.dts.core.util.LoggerManager;
import com.dts.dae.model.Profile;
import com.dts.ebuy.model.Cards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
public class CardsDAO extends AbstractDataAccessObject{
	public Connection con;

    private boolean flag;
    /** Creates a new instance of ProfileDAO */
    public CardsDAO() 
    {
           //getting Database Connection
           
    }
    //User Registration
    public boolean creditcards(Cards ccbean)
    {
    	//ResultSet rs = null;
    	Statement st=null;
    	String ln=ccbean.getLName();
    	System.out.println("Loginname"+ln);
        String cname=ccbean.getCName();
        String type=ccbean.getType();
        String cno=ccbean.getCNo();
        String cvv=ccbean.getCVV();
        String exp=ccbean.getExp();
        String addr=ccbean.getStreet();
        String city=ccbean.getCity();
       
        String state=ccbean.getState();
        String country=ccbean.getCountry();
        String zip=ccbean.getZipcode();
      
        try 
        {
        	con=getConnection();
           con.setAutoCommit(false);
           PreparedStatement pst=null;
           st=con.createStatement();
           int i=0;
           int orderid = getSequenceID("totalorders", "OrderID");
           orderid=orderid-1;
         
           pst=con.prepareStatement("insert into creditcards values(?,?,?,?,?,?,?,?,?,?,?,?)");
           pst.setInt(1,orderid);
           pst.setString(2,ln);
           System.out.println(type);
           pst.setString(3, type);
           pst.setString(4,cno);
           pst.setString(5,cname);
                      pst.setString(6,cvv);
           pst.setString(7,exp);
           pst.setString(8,addr);
           pst.setString(9,city);
           pst.setString(10,state);
           pst.setString(11,country);
           pst.setString(12,zip);
           
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
    
}
