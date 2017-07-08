package com.dts.ebuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.model.Category;

public class CategoryDAO extends AbstractDataAccessObject{

	public Connection con;
	public Category category;
	public CategoryDAO() 
	{
		
	}
	
	// Add new Category
    public boolean addCategory(Category category)
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
           int id = getSequenceID("category","CategoryID");
   		con=getConnection();
   		pst = con.prepareStatement("insert into category values(?,?,?)");
   		pst.setInt(1, id);
		pst.setString(2, category.getCategoryName());
		pst.setString(3, category.getCategoryDesc());
   		
       
           
			
           
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
    
 // Update Category
    public boolean updateCategory(Category category)
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
           int id = category.getCategoryID();
   		con=getConnection();
   		pst = con.prepareStatement("update category set CategoryName=?,CategoryDesc=? where CategoryID=?");
   		pst.setString(1, category.getCategoryName());
		pst.setString(2, category.getCategoryDesc());
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
    
 // Update Category
    public boolean deleteCategory(int categoryid)
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
   		pst = con.prepareStatement("delete from category  where CategoryID=?");
   		pst.setInt(1, categoryid);
           
			
           
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
    
    
    //list Categories
    
    public CoreHash listCategories()
	{
		CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		int categoryid;
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from Category order by categoryid");
			while(rs.next())
			{
				categoryid = rs.getInt(1);
				category = new Category();
				category.setCategoryID(categoryid);
				category.setCategoryName(rs.getString(2));
				category.setCategoryDesc(rs.getString(3));
				
				
				aCoreHash.put(new Integer(categoryid), category);
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
    
    
    
//list Category namess
    
    public CoreHash listCategoryNames()
	{
		CoreHash aCoreHash = new CoreHash();
		aCoreHash.clear();
		int categoryid;
		Statement st;
		try {
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from Category order by categoryid");
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
    
    

    
//edit Category
    
    public Category editCategory(int categoryid)
	{
		
		Statement st;
		try 
		{
			con = getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from CATEGORY where Categoryid="+categoryid);
			if(rs.next())
			{
				categoryid = rs.getInt(1);
				category = new Category();
				category.setCategoryID(categoryid);
				category.setCategoryName(rs.getString(2));
				category.setCategoryDesc(rs.getString(3));
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
		return category;
	}
    
public String getCategoryname(int cateid){
    	
    	Connection connection=null;
    	String categoryname="";
    	try{
    		connection=getConnection();
    		PreparedStatement pst=connection.prepareStatement("select CategoryName from category where categoryid=? ");
    		pst.setInt(1,cateid);
    	ResultSet rs=pst.executeQuery();
    	while(rs.next()){
    		categoryname=rs.getString(1);
    	}
    	connection.close();
    	}catch (Exception e) {
			// TODO: handle exception
		}
    	return categoryname;
    	
    }
public int getCategoryname(String catename){
	Connection connection=null;
	int brandid=0;
	try{
		connection=getConnection();
		PreparedStatement pst=connection.prepareStatement("select categoryid from category where CategoryName like '%"+catename+"%' or CategoryDesc like '%"+catename+"%'");
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
}
