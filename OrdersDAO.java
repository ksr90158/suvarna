package com.dts.ebuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.dao.InventoryAuditDAO;
import com.dts.ebuy.dao.InventoryDAO;
import com.dts.ebuy.model.Inventory;
import com.dts.ebuy.model.Item;
import com.dts.ebuy.model.Orders;
import com.dts.ebuy.model.SampleMail;

public class OrdersDAO extends AbstractDataAccessObject{

	public Connection con;
	private Orders order;
	private InventoryAuditDAO iadao;
	private InventoryDAO inventorydao;
	
	public OrdersDAO() throws SQLException
	{
		con = getConnection();
		iadao = new InventoryAuditDAO();
	}
   
	// place total order
	public boolean placeTotalOrder(Orders order, CoreHash aCoreHash)
	{
		boolean flag = false;
		try
		{
			con.setAutoCommit(false);
			int orderid = getSequenceID("totalorders", "orderid");
			String loginname = order.getLoginname();
			double totalamt = order.getTotalamount();
			String orderdate = order.getOrderDate();
			String status = order.getStatus();
			
			PreparedStatement pst = con.prepareStatement("insert into totalorders values(?,?,?,?,?)");
			
			pst.setString(2, loginname);
			pst.setInt(1, orderid);
			pst.setDouble(4, totalamt);
			pst.setString(3, orderdate);
			pst.setString(5, status);
			
			if(pst.executeUpdate()>0)
			{
				flag = placeItemOrder(orderid,aCoreHash);
			}
		}
		catch(Exception e)
		{
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return flag;
	}

	//place Item Order
	
	private boolean placeItemOrder(int orderid, CoreHash aCoreHash)
	{
		boolean flag = false;
		Inventory inventory = new Inventory();
		try
		{
		   PreparedStatement pst = con.prepareStatement("insert into itemorder values(?,?,?,?,?,?)");
		   PreparedStatement pst1 = con.prepareStatement("update inventory set quantity=(quantity-?) where brandid=? and categoryid=? and itemid=?");
		   //PreparedStatement pst2=con.prepareStatement("select quantity from inventory where brandid=? and categoryid=? and itemid=?");
		   //System.out.print("Hello");
		  //pst2.executeUpdate();
		 /* con = getConnection();
		  int q = 0;
		  Statement st;
			st = con.createStatement();
			ResultSet rs = st.executeQuery("select quantity from inventory where brandid=? and categoryid=? and itemid=?");
			while(rs.next())
			{
				q = rs.getInt(1);
			}
			if(q>10)
			{
		   try {
				String mess= "Item order placed ";
				String adminEmail= "surinuthalapati@gmail.com";
				System.out.print("Hello mail");
				
				SampleMail.Send("donotreplyatebuy", "ebuygadgets", adminEmail, "", "Order Placed", mess );
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   //System.out.print("Hello out mail");
		   
			}
		   */
		   Enumeration enu = aCoreHash.elements();
		   while(enu.hasMoreElements())
		   {
			   order = (Orders)enu.nextElement();
			   pst.setInt(1, orderid);
			   pst.setInt(2, order.getBrandid());
			   pst.setInt(3, order.getCategoryid());
			   pst.setInt(4, order.getItemid());
			   pst.setInt(5, order.getQuantity());
			   pst.setDouble(6, order.getPrice());
			   
			   pst1.setInt(1, order.getQuantity()); 
			   pst1.setInt(2, order.getBrandid());
			   pst1.setInt(3, order.getCategoryid());
			   pst1.setInt(4, order.getItemid());
			   
			   inventory.setBrandID(order.getBrandid());
			   inventory.setCategoryID(order.getCategoryid());
			   inventory.setItemID(order.getItemid());
			   
			  
			   if(pst.executeUpdate()>0 && pst1.executeUpdate()>0 && iadao.inventoryAudit(inventory, order.getQuantity()+"Quantity deducted",2))
			   {
				   flag = true;
				   System.gc();
			   }
			   else
			   {
				   flag = false;
				   break;
			   }
		   }
		}
		catch(Exception e)
		{
			flag = false;
			LoggerManager.writeLogWarning(e);
		}	
		
		int bid=order.getBrandid();
		   int cid=order.getCategoryid();
		 int iid= order.getItemid();
		emailInventory(bid,cid,iid);
		return flag;
	}
	
	//email
	
	public void emailInventory(int bid, int cid, int iid)
	{
		
		//System.out.println("in email ids"+bid+"---"+cid+"-----"+iid);
		  int q = 0;
		  String bn=null;
		  String cn=null;
		  String in=null;
		  Statement st = null;
			try {
				st = con.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ResultSet rs = null;
			try {
				rs = st.executeQuery("select quantity from inventory where brandid='"+bid+"' and categoryid='"+cid+"' and itemid='"+iid+"'");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				while(rs.next())
				{
					q = rs.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//System.out.println("in email quantity"+q);
			if(q<35)
			{
				try {
					rs = st.executeQuery("select BrandName, CategoryName, ItemName from brand b, category c, item i where b.BrandID='"+bid+"' and c.CategoryID='"+cid+"' and i.ItemID='"+iid+"';");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					while(rs.next())
					{
						bn = rs.getString(1);
						cn = rs.getString(2);
						in = rs.getString(3);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		   try {
				String mess= "Hello Admin@eBuy,"
						+ "                   This is to notify you that The following Items in eBuy inventory is went low to "
						+q+" Items.		"
						+ "               Brnd Name= "+bn+"  with Id= "+bid
								+ ".                   Category Name= "+cn+"  with Id= "+cid
										+ ".             Item Name= "+in+" with Id= "+iid+""
												+ ""
												+ ".          Please Upadete/ add the items for this Item"
												+ "			Thank you,"
												+ "			eBuy";
				String adminEmail= "mangalampalli.siva@gmail.com";
				//System.out.print("Hello mail");
				
				SampleMail.Send("donotreplyatebuy", "ebuygadgets", adminEmail, "", "Inventory low Warning- eBuy electronics", mess );
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   //System.out.print("Hello out mail");
		   
			}
	}
	
	//List Orders
	public CoreHash listOrders(String fromdate, String todate)
	{
		CoreHash aCoreHash = new CoreHash();
		String dbname = getProperties().getProperty("dbname");
		String pattern ="";
		if(dbname.equals("access"))
			pattern = "#"; 
		 
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from TOTALORDERS where orderdate>="+pattern+fromdate+pattern+" and orderdate<="+pattern+todate+pattern);
		   int orderid = 0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setOrderID(orderid);
			  order.setLoginname(rs.getString(2));
			  order.setOrderDate1(rs.getDate(3));
			  order.setTotalamount(rs.getDouble(4));
			  order.setStatus(rs.getString(5));
			  
		      aCoreHash.put(new Integer(orderid), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	
	//List Orders
	public CoreHash listOrder(String orderdate,String loginname)
	{
		CoreHash aCoreHash = new CoreHash();
		//String dbname = getProperties().getProperty("dbname");
		//String pattern ="";
		//if(dbname.equals("access"))
		//	pattern = "#"; 
		 
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from totalorders where OrderDate='"+orderdate+"' and LoginName='"+loginname+"' Order by Orderdate desc");
		   int orderid = 0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setOrderID(orderid);
			  order.setLoginname(rs.getString(2));
			  order.setOrderDate1(rs.getDate(3));
			  order.setTotalamount(rs.getDouble(4));
			  order.setStatus(rs.getString(5));
			  
		      aCoreHash.put(new Integer(orderid), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	
	//bydate admin
	public CoreHash listOrderAdmin(String orderdate)
	{
		CoreHash aCoreHash = new CoreHash();
		//String dbname = getProperties().getProperty("dbname");
		//String pattern ="";
		//if(dbname.equals("access"))
		//	pattern = "#"; 
		 
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from TOTALORDERS where orderdate='"+orderdate+"' Order by Orderdate desc");
		   int orderid = 0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setOrderID(orderid);
			  order.setLoginname(rs.getString(2));
			  order.setOrderDate1(rs.getDate(3));
			  order.setTotalamount(rs.getDouble(4));
			  order.setStatus(rs.getString(5));
			  
		      aCoreHash.put(new Integer(orderid), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	//List Orders
	public CoreHash listOrders(String loginname)
	{
		CoreHash aCoreHash = new CoreHash();
		
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from totalorders where loginname='"+loginname+"' Order by Orderdate desc");
		   int orderid = 0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setOrderID(orderid);
			  order.setLoginname(rs.getString(2));
			  order.setOrderDate1(rs.getDate(3));
			  order.setTotalamount(rs.getDouble(4));
			  order.setStatus(rs.getString(5));
			  
		      aCoreHash.put(new Integer(orderid), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	//List Orders
	public CoreHash listOrdersOfAllUser(String logintype)
	{
		CoreHash aCoreHash = new CoreHash();
		
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from ebuy.totalorders ttl, ebuy.logindetails ld where ld.loginname=ttl.LoginName and ld.logintype='"+logintype+"' Order by Orderdate desc");
		   int orderid = 0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setOrderID(orderid);
			  order.setLoginname(rs.getString(2));
			  order.setOrderDate1(rs.getDate(3));
			  order.setTotalamount(rs.getDouble(4));
			  order.setStatus(rs.getString(5));
			  
		      aCoreHash.put(new Integer(orderid), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	//List Orders Items
	public CoreHash listOrderDetails(int orderid)
	{
		CoreHash aCoreHash = new CoreHash();
		
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from itemorder where Orderid="+orderid);
		   int i=0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setBrandid(rs.getInt(2));
			  order.setCategoryid(rs.getInt(3));
			  order.setItemid(rs.getInt(4));
			  order.setQuantity(rs.getInt(5));
			  order.setPrice(rs.getDouble(6));
			  
		      aCoreHash.put(new Integer(i++), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	
	public CoreHash listOrderDetailsAdmin(int orderid)
	{
		CoreHash aCoreHash = new CoreHash();
		
		try
		{
		   Statement st = con.createStatement();
		   ResultSet rs = st.executeQuery("select * from ITEMORDER where Orderid="+orderid);
		   int i=0;
		   while(rs.next())
		   {
			  order = new Orders();
			  orderid = rs.getInt(1);
			  order.setBrandid(rs.getInt(2));
			  order.setCategoryid(rs.getInt(3));
			  order.setItemid(rs.getInt(4));
			  order.setQuantity(rs.getInt(5));
			  order.setPrice(rs.getDouble(6));
			  
		      aCoreHash.put(new Integer(i++), order);	  
		   }
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
			catch(Exception e)
			{
				LoggerManager.writeLogWarning(e);
			}
		}
		return aCoreHash;
	}
	
	
	//update order status
	
	public boolean updateOrderStatus(int orderid, int statusid)
	{
		boolean flag = false;
		try
		{
			String status = "Rejected";
			con.setAutoCommit(false);
			PreparedStatement pst = con.prepareStatement("update totalorders set status=? where orderid=?");
			
			if( statusid == 2)
			{
				if(new InventoryDAO().updateInventory(orderid)) 
				{
					status = "Rejected";
					pst.setString(1, status);
					pst.setInt(2, orderid);
		       	
					pst.executeUpdate();
					con.commit();
					flag = true;
				}
			}
			else if(statusid == 1)
			{
	        	status = "Accepted";
	        	pst.setString(1, status);
		       	pst.setInt(2, orderid);
		       	
	        	pst.executeUpdate();
	        	con.commit();
	        	flag = true;
			}
	        else
	        {
	        	con.rollback();
	        	flag = false;
	        }
		}
		catch(Exception e)
		{
			try
			{
				con.rollback();
				flag = false;
			}
			catch(Exception ex)
			{
				LoggerManager.writeLogWarning(ex);
			}
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
		return flag;
	}
}
