/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Ajit Gupta 
 */
public class NgoList 
{
	protected String ngoid;
	protected String unid;
	protected String name;
	protected String emailid;
	protected String ngologo;
   
	public NgoList()
	{
		this.ngoid = new String();
		this.unid  = new String();
		this.name  = new String();
		this.emailid = new String();
	}
	public NgoList (String ngoid,String unid ,String name, String emailid )
	{
		this.ngoid=ngoid;
		this.unid=unid;
		this.name=name;
		this.emailid=emailid;
	}
	public String getNgologo()
	{
		return ngologo;
	}
	public void setNgologo(String ngologo)
	{
		this.ngologo = ngologo;
	}
	public String getEmailid()
	{
		return emailid;
	}
	public void setEmailid(String emailid)
	{
		this.emailid = emailid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getNgoid()
	{
		return ngoid;
	}
	public void setNgoid(String ngoid)
	{
		this.ngoid = ngoid;
	}
	public String getUnid()
	{
		return unid;
	}
	public void setUnid(String unid)
	{
		this.unid = unid;
	}
	
	public ArrayList<NgoList> getNgoList()
	{
		ArrayList<NgoList> ngal = new ArrayList<NgoList>();
		DbContainor.loadDbDriver();
        
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl, DbContainor.dbuser,DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("Select ngoname , EMail,ngologo  from ngoinfo where EMail in (Select ngoid from joins where unid=?)");
			ps.setString(1,unid);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				NgoList ngl =n ew NgoList();
				ngl.setName(rs.getString("ngoname"));
				ngl.setEmailid(rs.getString("EMail"));
				ngl.setNgologo(rs.getString(3));
				ngal.add(ngl);
			}
			con.close();
		}
		catch(SQLException sqle)
		{
			System.out.println("sql error in getNgoLsit() of NgoList.java : " + sqle.getMessage());
		}
		return ngal;
	}
	public boolean updateNgoList()
	{
		boolean ret_val = false;
		DbContainor.loadDbDriver();
        
		try
		{
			Connection con = DriverManager.getConnection(DbContainor.dburl, DbContainor.dbuser,DbContainor.dbpwd);
			PreparedStatement ps = con.prepareStatement("insert into joins values(?,?)");
			ps.setString(1,ngoid);
			ps.setString(2, unid);

			if(ps.executeUpdate()>0)
			{
				System.out.println("Data Succesfully inserted into Joins table  ");
				ret_val = true;
			}
			else
			{
				System.out.println("Could not insert data into Joins table.");
			}
			con.close();
		}
		catch(SQLException sqle)
		{
            
			System.out.println("sql error in createNgoList() of NgoList.java : " + sqle.getMessage());
		}
		return ret_val;
	}
}