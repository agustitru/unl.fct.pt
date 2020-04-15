package com.agustitru;

public class AuthenticatorError extends Exception {
	
	
	private int codigoError;
    
    public AuthenticatorError(int codigoError){
        super();
        this.codigoError=codigoError;
    }
     
    @Override
    public String getMessage(){
         
        String mensaje="";
         
        switch(codigoError){
            case 1:
                mensaje="Wrong password";
                break;
            case 2:
                mensaje="Database Error";
                break;
            case 3:
                mensaje="Impossible to connect with database, setup variable URL on SQLite_db.java";
                break;
            case 4:
                mensaje="Passwords not equals";
                break;
            case 5:
                mensaje="Account already exist";
                break;
            case 6:
                mensaje="Account Logged in or locked";
                break;
            case 7:
                mensaje="Account not exist";
                break;
        }
         
        return mensaje;
         
    }

}
