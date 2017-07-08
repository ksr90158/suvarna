package com.dts.ebuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.model.Brand;

public class BrandDAO extends AbstractDataAccessObject{

	public Connection con;
	public Brand brand;
	public BrandDAO() 
	{
		
	}
	
	// Add new Brand
    public boolean addBrand(Brand brand)
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
           int id = getSequenceID("brand","BrandID");
   		con=getConnection();
   		pst = con.prepareStatement("insert into brand values(?,?,?)");
   		pst.setInt(1, id);
   		pst.setString(2, brand.getBrandName());
   		pst.setString(3, brand.getBrandDesc());
   		
       
           
			
           
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
    
 // Update Brand
    public boolean updateBrand(Brand brand)
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
           int id = brand.getBrandID();
   		con=getConnection();
   		pst = con.prepareStatement("update brand set BrandName=?,BrandDesc=? where BrandID=?");
   		pst.setString(1, brand.getBrandName());
		pst.setString(2, brand.getBrandDesc());
		pst.setInt(3, id);
       
           
			
           
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
    
 // Delete Brand
    public boolean deleteBrand(int brandid)
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
          
   		con=getConnection();
   		pst = con.prepareStatement("delete from brand  where BrandID=?");
   		pst.setInt(1, brandid);
   		
       
           
			
           
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
    
    
    //list brands
    
    public CoreHash listBrands()
	{
		CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		int brandid;
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from brand order by BrandID");
			while(rs.next())
			{
				brandid = rs.getInt(1);
				brand = new Brand();
				brand.setBrandID(brandid);
				brand.setBrandName(rs.getString(2));
				brand.setBrandDesc(rs.getString(3));
				
				
				aCoreHash.put(new Integer(brandid), brand);
			}
			
			con.close();
		} catch (SQLException e) {
			LoggerManager.writeLogWarning(e);
		}
        
		return aCoreHash;
	}
    
    
//list brand names
    
    public CoreHash listBrandNames()
	{
		CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		int brandid;
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from brand order by BrandID");
			while(rs.next())
			{
				brandid = rs.getInt(1);
						
				aCoreHash.put(new Integer(brandid), rs.getString(2));
			}
			
			con.close();
		} catch (SQLException e) {
			LoggerManager.writeLogWarning(e);
		}
        
		return aCoreHash;
	}
   
    
//edit brand
    
    public Brand editBrand(int brandid)
	{
		
		Statement st;
		try 
		{
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from BRAND where BrandID="+brandid);
			if(rs.next())
			{
				brandid = rs.getInt(1);
				brand = new Brand();
				brand.setBrandID(brandid);
				brand.setBrandName(rs.getString(2));
				brand.setBrandDesc(rs.getString(3));
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
		return brand;
	}
    
    public int getBrandid(String brandname){
    	Connection connection=null;
    	int brandid=0;
    	try{
    		connection=getConnection();
    		PreparedStatement pst=connection.prepareStatement("select brandid from brand where brandname like '%"+brandname+"%' or BrandDesc like '%"+brandname+"%'");
    	ResultSet rs=pst.executeQuery();
    	while(rs.next()){
    		brandid=rs.getInt(1);
    	}
    	connection.close();
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	return brandid;
    	
    }
    public String getBrandname(int brandid){
    	
    	Connection connection=null;
    	String brandname="";
    	try{
    		connection=getConnection();
    		PreparedStatement pst=connection.prepareStatement("select BrandName from brand where BrandID=? ");
    		pst.setInt(1, brandid);
    	ResultSet rs=pst.executeQuery();
    	while(rs.next()){
    		brandname=rs.getString(1);
    	}
    	connection.close();
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	return brandname;
    	
    }
}
