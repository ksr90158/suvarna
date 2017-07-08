package com.dts.ebuy.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.core.util.CoreList;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.dao.InventoryAuditDAO;
import com.dts.ebuy.model.Inventory;

public class InventoryDAO extends AbstractDataAccessObject{

	public Connection con;
	private Inventory inventory;
	private InventoryAuditDAO iadao;
	
	public InventoryDAO() throws SQLException 
	{
		
		iadao = new InventoryAuditDAO();
	}
	
	// Add new Inventory
	
    public boolean addInventory(Inventory inventory)
    {
    	
    	boolean flag = false;
    	boolean flag1 = false;
    	int i=0;
    	try 
        {
        	con=getConnection();
           con.setAutoCommit(false);
           PreparedStatement pst=null;
           //Statement st=con.createStatement();
        pst = con.prepareStatement("insert into inventory values(?,?,?,?,?)");
        pst.setInt(1, inventory.getBrandID());
		pst.setInt(2, inventory.getCategoryID());
		pst.setInt(3, inventory.getItemID());
		pst.setInt(4, inventory.getQuantity());
		pst.setDouble(5, inventory.getPrice());
           
			
           
           i=pst.executeUpdate();
           
          
            if(i==1)
            {
                flag=true;
                con.commit();
               flag1=iadao.inventoryAudit(inventory, "New Inventory Added",1);
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
    
// Update Inventory
	
    public boolean updateInventory(Inventory inventory)
    {
    	boolean flag = false;
    	boolean flag1 = false;
    	int i=0;
    	try 
        {
        	con=getConnection();
           con.setAutoCommit(false);
           PreparedStatement pst=null;
           //Statement st=con.createStatement();
        pst = con.prepareStatement("update inventory set Quantity=?,Price=? where BrandID=? and CategoryID=? and ItemID=?");
   		System.out.println("Quantity"+inventory.getQuantity());
   		pst.setInt(1, inventory.getQuantity());
   		pst.setDouble(2, inventory.getPrice());
   		pst.setInt(3, inventory.getBrandID()); 		
   		pst.setInt(4, inventory.getCategoryID());	
   		pst.setInt(5, inventory.getItemID());
           
			
           
           i=pst.executeUpdate();
           
          
            if(i==1)
            {
                flag=true;
                con.commit();
               flag1=iadao.inventoryAudit(inventory, "Inventory Modified",1);
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
    
// Delete Inventory
	
    public boolean deleteInventory(Inventory inventory)
    {
    	
    	boolean flag = false;
    	boolean flag1 = false;
    	int i=0;
    	try 
        {
        	con=getConnection();
           con.setAutoCommit(false);
           PreparedStatement pst=null;
           //Statement st=con.createStatement();
        pst = con.prepareStatement("delete from inventory  where BrandID=? and CategoryID=? and ItemID=?");
        pst.setInt(1, inventory.getBrandID()); 		
		pst.setInt(2, inventory.getCategoryID());	
		pst.setInt(3, inventory.getItemID());
			
           
           i=pst.executeUpdate();
           
          
            if(i==1)
            {
                flag=true;
                con.commit();
               flag1=iadao.inventoryAudit(inventory, "Inventory Deleted",2);
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
// Update Inventory by orderid
	
    public boolean updateInventory(int orderid)
    {
    	boolean flag = false;
    	boolean flag1 = false;
    	try
    	{
    		con=getConnection();
    		con.setAutoCommit(false);
    		PreparedStatement pst1 = con.prepareStatement("select brandid, categoryid, itemid, quantity from itemorder where orderid=?");
    		PreparedStatement pst = con.prepareStatement("update INVENTORY set quantity=(quantity+?) where brandid=? and categoryid=? and itemid=?");
    		pst1.setInt(1, orderid);
    		
    		ResultSet rs = pst1.executeQuery();
    		while(rs.next())
    		{   
    			pst.setInt(2, rs.getInt(1)); 		
    			pst.setInt(3, rs.getInt(2));	
    			pst.setInt(4, rs.getInt(3));
    			pst.setInt(1, rs.getInt(4)); 
    		     
    			if(pst.executeUpdate()>0)
    			{
    				flag = true;
    				flag1=iadao.inventoryAudit(inventory, "Quantity Updated",1);
    			}
    			else
    			{
    				flag = false;
    				break;
    			}
    		}
    	}
       	catch(SQLException se)
    	{
       		flag = false;
    		LoggerManager.writeLogWarning(se);
    	}
    	catch(Exception e)
    	{
    		flag = false;
    		LoggerManager.writeLogWarning(e);
    	}
    	finally
    	{
    		try
    		{
    			if(flag)
    				con.commit();
    			else
    				con.rollback();	
    			con.close();
    		}
    		catch(SQLException se)
    		{
    			LoggerManager.writeLogWarning(se);
    		}
    	}
    	return flag;
    }
    
    
//list Category by brand
    
    public CoreHash listCategorybyBrand(int brandid)
	{
		CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		int categoryid;
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from inventory where brandid="+brandid);
			while(rs.next())
			{				
				aCoreHash.put(new Integer(rs.getInt(1)), rs.getString(2));
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
    		catch(SQLException se)
    		{
    			LoggerManager.writeLogWarning(se);
    		}
    	}
		return aCoreHash;
	}
    
    
//list items by brand and category
    
    public CoreHash listItemsbyBrandCategory(String brandid, String categoryid)
	{
		CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select i.itemid, i.itemname from item i where i.itemid not in (select itemid from " +
										   "inventory where brandid="+brandid+" and categoryid="+categoryid+") and i.categoryid="+categoryid);
			while(rs.next())
			{				
				aCoreHash.put(new Integer(rs.getInt(1)), rs.getString(2));
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
    		catch(SQLException se)
    		{
    			LoggerManager.writeLogWarning(se);
    		}
    	}
		return aCoreHash;
	}
    
    
    
// List Inventory By Brand
	
    public CoreList InventoryByBrand(int brandid)
    {
    	CoreList aCoreList = new CoreList();
		aCoreList.clear();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int count = 0;
			ResultSet rs = st.executeQuery("select * from INVENTORY where brandid="+brandid+" order by brandid");
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				
				count++;
				aCoreList.add(inventory);
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
    	return aCoreList;
    }
    
    
// List Inventory By Category
	
    public CoreList InventoryByCategory(int categoryid)
    {
    	CoreList aCoreList = new CoreList();
		aCoreList.clear();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int count = 0;
			ResultSet rs = st.executeQuery("select * from INVENTORY where categoryid="+categoryid+" order by Brandid");
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				
				count++;
				aCoreList.add(inventory);
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
    	return aCoreList;
    } 
    
    
// List Inventory By Item
	
    public CoreList InventoryByItem(int itemid)
    {
    	CoreList aCoreList = new CoreList();
		aCoreList.clear();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int count = 0;
			ResultSet rs = st.executeQuery("select * from INVENTORY where itemid="+itemid+" order by brandid");
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				
				count++;
				aCoreList.add(inventory);
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
    	return aCoreList;
    } 
    
    
    //list Search Inventory
    public CoreHash searchInventory(int brandid, int categoryid, int itemid)
    { int itemid1=0;
    	CoreHash aCoreHash = new CoreHash();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int count = 0;
			String query="select * from INVENTORY where ";
			if(brandid != 0)
			{
				query+=" brandid="+brandid;
				count++;
			}
			if(count==1 && categoryid!=0)
			{
				query+=" and ";
				count=0;
			}
			if(categoryid!=0)
			{
				query+=" categoryid="+categoryid;
				count=1;
			}
			if(count==1 && itemid1!=0)
			{
				query+=" and ";
				count=0;
			}
			if(itemid1!=0)
			{
				query+=" itemid="+itemid1;
			}
			query+=" order by brandid";
			ResultSet rs = st.executeQuery(query);
			count=0;
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				
				count++;
				aCoreHash.put(new Integer(count),inventory);
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
    
    
    //get price by inventory
    public CoreHash getInventoryDetails(int brandid)
    {
    	CoreHash aCoreHash = new CoreHash();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int i = 0;
			String query="select * from INVENTORY where brandid="+brandid+"";
			ResultSet rs = st.executeQuery(query);
		
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				aCoreHash.put(new Integer(i),inventory);
				i++;
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
    
    public Inventory getInventory(int brandid, int categoryid, int itemid)
    {
    	CoreHash aCoreHash = new CoreHash();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int count = 0;
			String query="select * from INVENTORY where brandid="+brandid+" and  categoryid="+categoryid+" and itemid="+itemid+"  order by brandid";
			ResultSet rs = st.executeQuery(query);
			count=0;
			if(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
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
    	return inventory;
    }
    public CoreHash getCategoryInventoryDetails(int cateid)
    {
    	CoreHash aCoreHash = new CoreHash();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int i = 0;
			String query="select * from INVENTORY where CategoryID="+cateid+"";
			ResultSet rs = st.executeQuery(query);
		
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				aCoreHash.put(new Integer(i),inventory);
				i++;
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
    public CoreHash getItemInventoryDetails(int itemid)
    {
    	CoreHash aCoreHash = new CoreHash();
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			int i = 0;
			String query="select * from INVENTORY where ItemID="+itemid+"";
			ResultSet rs = st.executeQuery(query);
		
			while(rs.next())
			{
				inventory = new Inventory();
				inventory.setBrandID(rs.getInt(1));
				inventory.setCategoryID(rs.getInt(2));
				inventory.setItemID(rs.getInt(3));
				inventory.setQuantity(rs.getInt(4));
				inventory.setPrice(rs.getDouble(5));
				aCoreHash.put(new Integer(i),inventory);
				i++;
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
