/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.sql.*;
/**
 *
 * @author ANOOP
 */
public class AdminLogin
{

	private String adminid;
	private String pwd;

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	public void setAdminid(String adminid)
	{
		this.adminid = adminid;
	}
	public String getPwd()
	{
		return pwd;
	}
	public String getAdminid()
	{
		return adminid;
	}

	public boolean isValidAdmin()
	{
		boolean ret_val = false;
		System.out.println("in isValidAdmin methos of Adminlogin class.");
		DbContainor.loadDbDriver();
       
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl,DbContainor.dbuser,DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("select EMail,Password from userinfo where EMail=? and Password=?");
			ps.setString(1,adminid);
			ps.setString(2, pwd);
			ps.execute();
			System.out.println("command is successfully executed");
			
			if(ps.executeQuery().next())
			{
				ret_val = true;
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("SQl Error Occured : "+sqle.getMessage());
		}
		return ret_val;
	}
}