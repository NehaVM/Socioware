/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ajit Gupta 
 */
public class Comment
{
	protected String cmntid;
	protected String unid;
	protected String cmnton;
	protected String cmntdate;
	protected String comments;
	protected int likes;

	public String getCmntdate()
	{
		return cmntdate;
	}
	public void setCmntdate(String cmntdate)
	{
		this.cmntdate = cmntdate;
	}
	public String getCmntid()
	{
		return cmntid;
	}
	public void setCmntid(String cmntid)
	{
		this.cmntid = cmntid;
	}
	public String getCmnton()
	{
		return cmnton;
	}
	public void setCmnton(String cmnton)
	{
		this.cmnton = cmnton;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public Connection getCon()
	{
		return con;
	}
	public void setCon(Connection con)
	{
		this.con = con;
	}
	public int getLikes()
	{
		return likes;
	}
	public void setLikes(int likes)
	{
		this.likes = likes;
	}
	public PreparedStatement getPs()
	{
		return ps;
	}
	public void setPs(PreparedStatement ps)
	{
		this.ps = ps;
	}
	public String getUnid()
	{
		return unid;
	}
	public void setUnid(String unid)
	{
		this.unid = unid;
	}
    
	public Comment()
	{
		cmntid = new String();
		unid = new String();
		cmnton = new String();
		mntdate = new String();
		comments = new String();
		ikes = 0;
	}
	public Comment(String cmntid, String unid, String cmnton,String cmntdate,String comments, int likes)
	{
		this.cmntid=cmntid;
		this.unid=unid;
		this.cmnton=cmnton;
		this.cmntdate=cmntdate;
		this.comments=comments;
		this.likes=likes;
	}
        
	public boolean addComment()
	{
		boolean ret_val = false;
		DbContainor.loadDbDriver();
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl, DbContainor.dbuser, DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("Insert into comments values(?,?,?,?,?,?)");
			ps.setString(1,cmntid);
			ps.setString(2, unid);
			ps.setString(3, cmnton);
			try
			{
				ps.setDate(4,DbContainor.toSQLDate(cmntdate));
			}
			catch (ParseException ex)
			{
				System.out.println("can not convert date : "+ex.getMessage());
			}
			ps.setString(5, comments);
			ps.setInt(6, likes);
			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Succesfully inserted into comments table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not insert data into comments table.");
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in addComment() of Comment.java : " + sqle.getMessage());
		}
		return ret_val;
	}
        
	public boolean deleteComment()
	{
		boolean ret_val = false;
		DbContainor.loadDbDriver();
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl, DbContainor.dbuser, DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("delete from comments where cmntid=?");
			ps.setString(1,cmntid);

			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Successfully deleted into comments table  ");
				ret_val = true;
			}            
			else
			{
				System.out.println("ould not delete data into comments table.");
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in deleteComment() of Comment.java  : " + sqle.getMessage());
		}
	}
        
	public  ArrayList<Comment> findAllComments()
	{
		ArrayList<Comment> al = new ArrayList<Comment>();
		DbContainor.loadDbDriver();
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl, DbContainor.dbuser, DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("Select * from comments");
			ResultSet rs = ps.executeQuery();
            
			while(rs.next())
			{
				Comment cmnt = new Comment();
				cmnt .setCmntid(rs.getString("comment"));
				cmnt.setUnid(rs.getString("unid"));
				cmnt.setCmnton(rs.getString("cmnton"));
				cmnt.setCmntdate(rs.getString("cmntdate"));
				cmnt.setComments(rs.getString("comments"));
				cmnt.setLikes(rs.getInt("likes"));
				al.add(cmnt);
			}
			con.close();
        }
		catch(SQLException sqle)
		{
			System.out.println("sql error in findAllComment() of Comment.java : " + sqle.getMessage());
		}
		return al;
	}
    
}