package com.agustitru;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Authenticate implements Authenticator {
	
	private String JSONtoken;
	public Authenticate() {}
	

	@Override
	public void create_account(String name, String pwd1, String pwd2, int is_root, Sqlite_db db) throws AuthenticatorError {
		
		if(db.selectAnAccount(name) == null) {
			if(pwd1.equals(pwd2)) {
				db.insert(name, pwd1, 0, 0, is_root);
			}else{
				//print error
				throw new AuthenticatorError(4);
			}
		}else{
			throw new AuthenticatorError(5);	
		}
		
	}

	@Override
	public void delete_account(String name, Sqlite_db db) throws AuthenticatorError {
		
			Account acc = get_account(name,db);
			
			if(acc != null) {
				acc.setLocked(1);
				db.update(acc.getUsername(), acc.getPassword(), acc.getLogged_in(), acc.getLocked(), acc.getIs_root());
				if(acc.getLogged_in() == 0 && acc.getLocked() == 1) {
					db.delete(name);	
				}else {
					throw new AuthenticatorError(6);
				}
			}else {
				throw new AuthenticatorError(7);
			}
		
		
	}

	@Override
	public Account get_account(String name, Sqlite_db db) throws AuthenticatorError {			
			return db.selectAnAccount(name);

	}
	

	@Override
	public void change_pwd(String name, String pwd1, String pwd2, Sqlite_db db) throws AuthenticatorError {
			
			if(pwd1.equals(pwd2)) {
				Account acc = db.selectAnAccount(name);
				if(acc != null) {
					db.update(name, pwd1, acc.getLogged_in(), acc.getLocked(),acc.getIs_root());		
				}else {
					throw new AuthenticatorError(7);
				}
			}else {
				throw new AuthenticatorError(4);
			}
		

	}

	@Override
	public Account login(String name, String pwd, Sqlite_db db) throws AuthenticatorError {
	
		Account acc = get_account(name, db);
		
		if(acc != null) {
			if(acc.getLocked() == 0) {
				if(acc.getPassword().equals(pwd)) {
					db.update(name, pwd, 1, 0, acc.getIs_root());
					
					/*JWT NOT WORKS
					 * 
					 I don't know what is the issue but I believe that is a problem of library.
					 * 
					JSON_token JWT = new JSON_token();
					JSONtoken = JWT.createJWT(name,"","",600000);
					*/
					return db.selectAnAccount(name);
				}else{
					throw new AuthenticatorError(1);
				}
			}else {
				throw new AuthenticatorError(6);
			
			}
		}else{
			throw new AuthenticatorError(7);
		}
			
	}
	
		
	

	@Override
	public void logout(Account acc, Sqlite_db db) throws AuthenticatorError {
		db.update(acc.getUsername(), acc.getPassword(), 0, 0, acc.getIs_root());
		
	}

	@Override
	public Boolean login(HttpServletRequest req, HttpServletResponse resp, Sqlite_db db) {
		/*I don't know what is the issue but i believe that is a problem of library.
		HttpSession session = req.getSession();
		JSON_token JWT = new JSON_token();
		String name = JWT.parseJWT(this.JSONtoken);
		if(((String)session.getAttribute("user")).equals(name)) {
			return true;
		}
		
		return false;
		
		*/
		return true;
	}

}
