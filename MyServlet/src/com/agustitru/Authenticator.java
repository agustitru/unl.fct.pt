package com.agustitru;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public interface Authenticator {

	void create_account(String name, String pwd1, String pwd2, Sqlite_db db) throws AuthenticatorError;

	void delete_account(String name, Sqlite_db db) throws AuthenticatorError ;

	Account get_account(String name, Sqlite_db db) throws AuthenticatorError ;

	void change_pwd(String name, String pwd1, String pwd2, Sqlite_db db) throws AuthenticatorError;

	Account login(String name, String pwd, Sqlite_db db) throws AuthenticatorError;

	void logout(Account acc, Sqlite_db db) throws AuthenticatorError;

	Account login(HttpServletRequest req, HttpServletResponse resp, Sqlite_db db);

}
