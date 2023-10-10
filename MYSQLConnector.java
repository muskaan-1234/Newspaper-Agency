package Papermaster;

import java.sql.Connection;
import java.sql.DriverManager;

public class MYSQLConnector 
{
	static Connection con;

	public static Connection doConnect()
	{
	
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/newspaperag","root","");
			System.out.println("Your sql connector connected");
		}
		catch(Exception exp)
		{
			System.out.println(exp);
		}
		return con;
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		doConnect();

	}

}
