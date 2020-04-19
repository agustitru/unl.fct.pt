package com.agustitru;

public class Account {
	private String username, password;
    private int logged_in = 0, locked = 0, is_root;

    public Account(String username, String password,int is_root) {
        this.username = username;
        this.password = password;
        this.is_root = is_root;
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLogged_in() {
        return logged_in;
    }

    public void setLogged_in(int logged_in) {
        this.logged_in = logged_in;
    }

    public int getLocked() {
        return locked;
    }

    public void setLocked(int locked) {
        this.locked = locked;
    }
      

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", logged_in=" + logged_in +
                ", locked=" + locked +
                '}';
    }

	public int getIs_root() {
		return is_root;
	}

	public void setIs_root(int is_root) {
		this.is_root = is_root;
	}

}
