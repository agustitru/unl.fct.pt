package com.agustitru;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Sqlite_db db = new Sqlite_db();
	private static Authenticate auth = new Authenticate();
	private static HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		//init http session
		session = request.getSession(true);	
		//Init database and create account admin.
		
		try {	
			db.createNewTable();
			
		} catch (AuthenticatorError e) {	
			out.println ("<H3>"+e.getMessage()+"</H3>");
		}
		
		try {
			auth.create_account("admine","admine", "admine", db);
		} catch (AuthenticatorError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		LoginPage(request,response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		//On press button create user
		if(request.getParameter("CreateUser") != null) {
			CreateUserPage(request,response);
		}
		if(request.getParameter("Register") != null) {		
				String aname = request.getParameter("username");
				String apwd = request.getParameter("password");
				String apwd2 = request.getParameter("password2");
				try {
					auth.create_account(aname, apwd, apwd2, db);
					CreateUserPage(request,response);
					out.println ("<H3>"+"Account Created Succesfully"+"</H3>");
				} catch (AuthenticatorError e) {
					CreateUserPage(request,response);
					out.println ("<H3>"+e.getMessage()+"</H3>");
				}		
		}
		
		//On press button delete user
		if(request.getParameter("DeleteUser") != null) {	
			DeletePage(request,response);
		}
		
		if(request.getParameter("Delete") != null) {	
			try {
				String aname = request.getParameter("username");
				auth.delete_account(aname, db);
				DeletePage(request,response);
				out.println ("<H3>"+"Account Deleted Succesfully"+"</H3>");
			} catch (AuthenticatorError e) {
				DeletePage(request,response);
				out.println ("<H3>"+e.getMessage()+"</H3>");
			}
		}
		
		//LOGIN ACCOUNT
		if(request.getParameter("Login") != null) {
			try {
				
				String aname = request.getParameter("username");
				String apwd = request.getParameter("password");
				Account authUser = auth.login(aname, apwd,db);
				session.setAttribute("user", authUser.getUsername());
				session.setAttribute("password", authUser.getPassword());
				managePage(request, response);
				
			} catch (AuthenticatorError e) {
				LoginPage(request,response);
				out.println("<br>");
				out.println ("<H3>"+e.getMessage()+"</H3>");
			}
			
		}
		//Back button
		if(request.getParameter("Back") != null) {
			managePage(request, response);
			
		}
		//Change password
		if(request.getParameter("Changepassword") != null) {
			ChangePasswordPage(request,response);
		}
		
		if(request.getParameter("Change") != null) {		
			String aname = request.getParameter("username");
			String apwd = request.getParameter("password");
			String apwd2 = request.getParameter("password2");
			try {
				auth.change_pwd(aname, apwd, apwd2, db);
				ChangePasswordPage(request,response);
				out.println ("<H3>"+"Password Changed Succesfully"+"</H3>");
			} catch (AuthenticatorError e) {
				ChangePasswordPage(request,response);
				out.println ("<H3>"+e.getMessage()+"</H3>");
			}
		}
			
		//LOGOUT
		if(request.getParameter("Logout") != null) {
			
			String username = (String) session.getAttribute("user");
			Account lacc;
			try {
				lacc = auth.get_account(username, db);
				auth.logout(lacc, db);
				LoginPage(request,response);
			} catch (AuthenticatorError e) {
				out.println ("<H3>"+e.getMessage()+"</H3>");
			}
			
		}
}
	
	protected void CreateUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println ("<HTML>");
		out.println ("<HEAD><TITLE>CreateUser</TITLE></HEAD>");
		out.println ("<BODY>");
		out.println ("<H3>CreateUser</H3>");
		out.println("<br>");
		out.println ("<form ACTION = 'HelloServlet' method='Post'>" );
		out.println ("<table border='0'>");
		
		out.println ("  <fieldset>\r\n" );
		out.println ("    <legend>Sing in</legend>\r\n"); 
		out.println ("    <label for=\"username\">Username:</label><br>\r\n"); 
		out.println ("    <input type=\"text\" id=\"username\" name=\"username\" value=\"\"><br>\r\n" );
		out.println ("    <label for=\"password\">Password</label><br>\r\n" ); 
		out.println ("    <input type=\"text\" id=\"password\" name=\"password\" value=\"\"><br>\r\n"); 
		out.println ("    <label for=\"password\">Repeat password</label><br>\r\n" );
		out.println ("    <input type=\"text\" id=\"password2\" name=\"password2\" value=\"\"><br><br>\r\n" );
		out.println ("    <input type=\"submit\" name=\"Register\" value=\"Register\">" );
		out.println ("    <input type=\"submit\" name=\"Back\" value=\"Back\">" );
		out.println ("  </fieldset>");
		
	}
	
	protected void LoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println ("<HTML>");
		out.println ("<HEAD><TITLE>Authenticator</TITLE></HEAD>");
		out.println ("<BODY>");
		out.println ("<H3>Authenticator</H3>");
		out.println("<br>");
		out.println ("<form ACTION = 'HelloServlet' method='Post'>" );
		out.println ("<table border='0'>");
		
		out.println ("  <fieldset>\r\n"); 
		out.println ("    <legend>Sing in</legend>\r\n" );
		out.println ("    <label for=\"username\">Username:</label><br>\r\n");
		out.println ("    <input type=\"text\" id=\"username\" name=\"username\" value=\"\"><br>\r\n"); 
		out.println ("    <label for=\"password\">Password</label><br>\r\n" );
		out.println ("    <input type=\"text\" id=\"password\" name=\"password\" value=\"\"><br><br>\r\n" );
		out.println ("    <input type=\"submit\" name=\"Login\" value=\"Login\">" );
		out.println ("  </fieldset>");
	

	}
	
	protected void managePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		out.println ("<HTML>");
		out.println ("<HEAD><TITLE>Authenticator</TITLE></HEAD>");
		out.println ("<BODY>");
		out.println ("<H3>Manage Users</H3>");
		out.println("<br>");
		out.println ("<form ACTION = 'HelloServlet' method='Post'>" );
		out.println ("<table border='0'>");

		out.println ("<tr>");
		out.println (" <td> </td> ");
		out.println (" <td><input type='submit' name='CreateUser' value='Create User'></td> ");
		out.println ("</tr> ");
		
		out.println ("<tr>");
		out.println (" <td> </td> ");
		out.println (" <td><input type='submit' name='DeleteUser' value='Delete User'></td> ");
		out.println ("</tr> ");
		
		out.println ("<tr>");
		out.println (" <td> </td> ");
		out.println (" <td><input type='submit' name='Changepassword' value='Change password'></td> ");
		out.println ("</tr> ");
		
		out.println ("<tr>");
		out.println (" <td> </td> ");
		out.println (" <td><input type='submit' name='Logout' value='Logout'></td> ");
		out.println ("</tr> ");

		out.println ("</table> ");
		out.println ("<br>");
		out.println ("</form> ");
		out.println ("</BODY>");
		out.println ("</HTML>");

		out.flush();
		out.close();
	}

	protected void DeletePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println ("<HTML>");
		out.println ("<HEAD><TITLE>Authenticator</TITLE></HEAD>");
		out.println ("<BODY>");
		out.println ("<H3>Authenticator</H3>");
		out.println("<br>");
		out.println ("<form ACTION = 'HelloServlet' method='Post'>" );
		out.println ("<table border='0'>");
		
		out.println ("  <fieldset>\r\n" );
		out.println ("    <legend>Sing in</legend>\r\n" );
		out.println ("    <label for=\"username\">Username:</label><br>\r\n" );
		out.println ("    <input type=\"text\" id=\"username\" name=\"username\" value=\"\"><br>\r\n");
		out.println ("    <input type=\"submit\" name=\"Delete\" value=\"Login\">");
		out.println ("    <input type=\"submit\" name=\"Back\" value=\"Back\">" );
		out.println ("  </fieldset>");
		

	}
	
	protected void ChangePasswordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println ("<HTML>");
		out.println ("<HEAD><TITLE>CreateUser</TITLE></HEAD>");
		out.println ("<BODY>");
		out.println ("<H3>CreateUser</H3>");
		out.println("<br>");
		out.println ("<form ACTION = 'HelloServlet' method='Post'>" );
		out.println ("<table border='0'>");
		
		out.println ("  <fieldset>\r\n" );
		out.println ("    <legend>Sing in</legend>\r\n" ); 
		out.println ("    <label for=\"username\">Username:</label><br>\r\n"); 
		out.println ("    <input type=\"text\" id=\"username\" name=\"username\" value=\"\"><br>\r\n");
		out.println ("    <label for=\"password\">Password</label><br>\r\n" ); 
		out.println ("    <input type=\"text\" id=\"password\" name=\"password\" value=\"\"><br>\r\n" );
		out.println ("    <label for=\"password2\">Repeat password</label><br>\r\n" );
		out.println ("    <input type=\"text\" id=\"password2\" name=\"password2\" value=\"\"><br><br>\r\n" );
		out.println ("    <input type=\"submit\" name=\"Change\" value=\"Change it\">" ); 
		out.println ("    <input type=\"submit\" name=\"Back\" value=\"Back\">" );
		out.println ("  </fieldset>");
		
	}
}
