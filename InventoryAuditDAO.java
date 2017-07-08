package com.dts.ebuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.core.util.DateWrapper;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.model.Inventory;

public class InventoryAuditDAO extends AbstractDataAccessObject{

	public Connection con;
	public Inventory inventory;
	public String dbname;
	public String pattern;
	
	public InventoryAuditDAO() throws SQLException 
	{
		con=getConnection();
		dbname = getProperties().getProperty("dbname");
		if(dbname.equals("access"))
			pattern = "#"; 
	}
	
	// Add new Inventory
	
    public boolean inventoryAudit(Inventory inventory,String comments, int mode)
    {
    	boolean flag = false;
    	int i=0;
    	try 
        {
        	con=getConnection();
           con.setAutoCommit(false);
           PreparedStatement pst=null;
           //Statement st=con.createStatement();
           pst = con.prepareStatement("insert into inventoryaudit values(?,?,?,?,?,?)");
   		pst.setInt(1, inventory.getBrandID());
   		pst.setInt(2, inventory.getCategoryID());
   		pst.setInt(3, inventory.getItemID());
   		pst.setString(4, DateWrapper.parseDate(new Date()));
   		pst.setInt(5, mode);
        pst.setString(6, comments);
           
			
           
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
    

    // List Inventory Audit By Date
	
    public CoreHash InventoryAuditbyDate(String auditdate)
    {
    	CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int count = 0;
			ResultSet rs = st.executeQuery("select * from INVENTORYAUDIT where ModifiedDate="+pattern+auditdate+pattern);
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				inventory.setVat(rs.getDouble(6));
				
				count++;
				aCoreHash.put(new Integer(count), inventory);
			}	
    	}
    	catch(SQLException se)
    	{
    		LoggerManager.writeLogWarning(se);
    	}
    	catch(Exception e)
    	{
    		LoggerManager.writeLogWarning(e);
    	}
    	finally
    	{
    		try
    		{
    			con.close();
    		}
    		catch(SQLException se)
    		{
    			LoggerManager.writeLogWarning(se);
    		}
    	}
    	return aCoreHash;
    }
 
}
