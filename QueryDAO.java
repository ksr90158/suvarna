package com.dts.ebuy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.dts.core.dao.AbstractDataAccessObject;
import com.dts.core.util.CoreHash;
import com.dts.core.util.LoggerManager;
import com.dts.ebuy.model.Query;

public class QueryDAO extends AbstractDataAccessObject {

	Connection con;
	
	public void sendQuery(Query cQuery)
	{
		try
		{
			int queryid = getSequenceID("QUERY", "queryid");
			con = getConnection();
		
			String querydate = cQuery.getQueryDate();
			String desc = cQuery.getDescription();
			String name = cQuery.getCustomerID();
			String status = "Process";
		
			PreparedStatement pst = con.prepareStatement("insert into query values(?,?,?,?,?)");
			pst.setInt(1, queryid);
			pst.setString(2, name);
			pst.setString(3, querydate);
			pst.setString(4, desc);
			pst.setString(5, status);
			
			pst.executeUpdate();
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
	}
	
	
	public CoreHash getSolution(String loginname)
	{
		CoreHash qCoreHash = new CoreHash();
		try
		{
			
			con = getConnection();
		
			int queryid = 0;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select q.queryid,q.loginname,q.querydate,q.description,q.status,s.solveddate,s.solution " +
					                       " from query q , solution s where q.queryid=s.queryid and q.loginname = '"+loginname+"'");
			
			Query query = null;
			while(rs.next())
			{
				query = new Query();
				queryid = rs.getInt(1);
				query.setQueryid(queryid);
				query.setCustomerID(rs.getString(2));
				query.setQueryDate1(rs.getDate(3));
				query.setDescription(rs.getString(4));
				query.setStatus(rs.getString(5));
				query.setSolutionDate1(rs.getDate(6));
				query.setSolution(rs.getString(7));
				
				qCoreHash.put(new Integer(queryid), query);
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
		return qCoreHash;
	}
	
	public Query getSolution(int queryid)
	{
		CoreHash qCoreHash = new CoreHash();
		Query query = null;
		try
		{
			
			con = getConnection();
		
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select q.queryid,q.loginname,q.querydate,q.description,q.status,s.solveddate,s.solution " +
					                       " from query q , solution s where q.queryid=s.queryid and q.queryid = "+queryid);
			
			
			if(rs.next())
			{
				query = new Query();
				queryid = rs.getInt(1);
				query.setQueryid(queryid);
				query.setCustomerID(rs.getString(2));
				query.setQueryDate1(rs.getDate(3));
				query.setDescription(rs.getString(4));
				query.setStatus(rs.getString(5));
				query.setSolutionDate1(rs.getDate(6));
				query.setSolution(rs.getString(7));
				
				
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
		return query;
	}
	
	
	public Query getQuery(int queryid)
	{
		CoreHash qCoreHash = new CoreHash();
		Query query = null;
		try
		{
			
			con = getConnection();
		
	
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select q.queryid,q.loginname,q.querydate,q.description,q.status" +
					                       " from query q where q.queryid="+queryid);
			
			
			if(rs.next())
			{
				query = new Query();
				queryid = rs.getInt(1);
				query.setQueryid(queryid);
				query.setCustomerID(rs.getString(2));
				query.setQueryDate1(rs.getDate(3));
				query.setDescription(rs.getString(4));
				query.setStatus(rs.getString(5));
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
		return query;
	}
	
	
	public CoreHash getQueryStatus(String loginname)
	{
		CoreHash qCoreHash = new CoreHash();  
		try
		{
		    int queryid=0;
			con = getConnection();
		
		    Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery("select * from  query where loginname='"+loginname+"'");
			Query query = null;
			while(rs.next())
			{
				query = new Query();
				queryid = rs.getInt(1);
				query.setQueryid(queryid);
				query.setCustomerID(rs.getString(2));
				query.setQueryDate1(rs.getDate(3));
				query.setDescription(rs.getString(4));
				query.setStatus(rs.getString(5));
				
				qCoreHash.put(new Integer(queryid), query);
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
		return qCoreHash;
	}
	
	public CoreHash getQueryStatus()
	{
		CoreHash qCoreHash = new CoreHash();  
		try
		{
		    int queryid=0;
			con = getConnection();
		
		    Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery("select * from  query");
			Query query = null;
			while(rs.next())
			{
				query = new Query();
				queryid = rs.getInt(1);
				query.setQueryid(queryid);
				query.setCustomerID(rs.getString(2));
				query.setQueryDate1(rs.getDate(3));
				query.setDescription(rs.getString(4));
				query.setStatus(rs.getString(5));
				
				qCoreHash.put(new Integer(queryid), query);
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
		return qCoreHash;
	}
	
	public void setSolution(Query aQuery)
	{
		Connection con = null;
		try
		{
			con = getConnection();
			con.setAutoCommit(false);
			int queryid = aQuery.getQueryid();
			String replydate = aQuery.getSolutionDate();
			String solution = aQuery.getSolution();
			
			PreparedStatement pst = con.prepareStatement("insert into solution values(?,?,?)");
			PreparedStatement pst1 = con.prepareStatement("update query set status=? where queryid=?");
			
			pst.setInt(1, queryid);
			pst.setString(2, replydate);
			pst.setString(3, solution);
			
			pst1.setString(1, "Solved");
			pst1.setInt(2, queryid);
			
			if(pst.executeUpdate()>0)
			{
				if(pst1.executeUpdate()>0)
					con.commit();
				else
					con.rollback();
			}
			else
				con.rollback();
		}
		catch(Exception e)
		{
			LoggerManager.writeLogWarning(e);
		}
	}
	
	
	public void deleteQuery(int queryid)
	{
		Connection con = null;
		try
		{
			con = getConnection();
			con.setAutoCommit(false);
			
			PreparedStatement pst = con.prepareStatement("delete from solution where queryid="+queryid);
			PreparedStatement pst1 = con.prepareStatement("delete from query where queryid="+queryid);
			
					
			if(pst.executeUpdate()>0)
			{
				if(pst1.executeUpdate()>0)
					con.commit();
				else
					con.rollback();
			}
			else
				con.rollback();
		}
		catch(Exception e)
		{
			LoggerManager.writeLogWarning(e);
		}
	}
}
