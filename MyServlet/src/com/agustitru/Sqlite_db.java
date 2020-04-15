package com.agustitru;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sqlite_db {
	public static String url = "jdbc:sqlite:C:/Users/Usuario/eclipse-workspace/MyServlet/db/accounts.db";
	private Crypto crypto = new Crypto();
	public Sqlite_db() {}
	
	public void createNewTable() throws AuthenticatorError{
		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS accounts (\n"+ 
		"    username text NOT NULL UNIQUE,\n" +
				"    password text NOT NULL,\n" +
		"    logged_in integer,\n"
				+ "    locked integer\n" + ");";

		try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			System.out.println("Table accounts has been created!\n");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insert(String username, String password, int logged_in, int locked) throws AuthenticatorError{
		
		String sql = "INSERT INTO accounts(username,password,logged_in,locked) VALUES(?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			try {
				pstmt.setString(2,crypto.encrypt( password));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			pstmt.setInt(3, logged_in);
			pstmt.setInt(4, locked);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			 throw new AuthenticatorError(2);
			
		}
	}
	
	public void selectAll() throws AuthenticatorError{
        String sql = "SELECT username, password, logged_in, locked FROM accounts";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
        	System.out.println("--------------PRINT DATABASE-------------\n");
            while (rs.next()) {
                System.out.println( 
                                   rs.getString("username") + "\t" +
                                   rs.getString("password") + "\t" +
                                   rs.getInt("logged_in") + "\t" +
                                   rs.getInt("locked")+ "\t" 
                                  );
                		
            }
            System.out.println("-----------------------------------------\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AuthenticatorError(2);
        }
    }
	
	public Account selectAnAccount(String username) throws AuthenticatorError{
        String sql = "SELECT username, password, logged_in, locked FROM accounts WHERE username = "+"'"+username+"'";
        try (Connection conn = this.connect();
        		Statement stmt  = conn.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
        	
        		System.out.println( "Account name:"+
                    rs.getString("username") + "\t" +
                    rs.getString("password") + "\t" +
                    rs.getInt("logged_in") + "\t" +
                    rs.getInt("locked")+ "\t" );
        		
               try {
            	   return new Account(rs.getString("username"),crypto.decrypt(rs.getString("password")));
               } catch (Exception e) {
            	   System.out.println(e.getMessage());
            	   return null;
               }
        		
     
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;     
        }

    }
	
    public void update( String username,  String password, int logged_in, int locked) throws AuthenticatorError {
        String sql = "UPDATE accounts SET username = ? , "
                + "password = ? ,"
        		+ "logged_in = ? ,"
        		+ "locked = ?"
                + "WHERE username = ?";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding parameter
            pstmt.setString(1, username);
            
            try {
				pstmt.setString(2, crypto.encrypt(password));
			} catch (Exception e) {
				 System.out.println(e.getMessage());
			}
            pstmt.setInt(3, logged_in);
            pstmt.setInt(4, locked);
            pstmt.setString(5, username);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AuthenticatorError(2);
        }
    }
	
	private Connection connect() throws AuthenticatorError{
		// SQLite connection string
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new AuthenticatorError(3);
		}
		return conn;
	}
	
    public void delete(String name) throws AuthenticatorError {
        String sql = "DELETE FROM accounts WHERE username = ?";
 
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding parameter
            pstmt.setString(1, name);
            // execute the delete statement
            pstmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AuthenticatorError(2);
        }
    }

}
