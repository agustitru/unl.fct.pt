package com.agustitru;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Authenticate implements Authenticator {
	public Authenticate() {}
	

	@Override
	public void create_account(String name, String pwd1, String pwd2, Sqlite_db db) throws AuthenticatorError {
		
		if(db.selectAnAccount(name) == null) {
			if(pwd1.equals(pwd2)) {
				db.insert(name, pwd1, 0, 0);
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
				if(acc.getLogged_in() == 0 && acc.getLocked() == 0) {
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
				db.update(name, pwd1, 0, 0);			
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
					db.update(name, pwd, 1, 0);
					return db.selectAnAccount(name);
				}else{
					throw new AuthenticatorError(1);
				}
			}else {
				throw new AuthenticatorError(1);
			
			}
		}else{
			throw new AuthenticatorError(1);
		}
			
	}
	
		
	

	@Override
	public void logout(Account acc, Sqlite_db db) throws AuthenticatorError {
		db.update(acc.getUsername(), acc.getPassword(), 0, 0);

	}

	@Override
	public Account login(HttpServletRequest req, HttpServletResponse resp, Sqlite_db db) {
		// TODO Auto-generated method stub
		return null;
	}

}
