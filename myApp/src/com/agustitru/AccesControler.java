package com.agustitru;

public interface AccesControler {
	
	Role newRole(String roleid);
	
	void setRole(Account user, Role role);
	
	Role getRole(Account user);
	
	void grantPermission(Role role, Resource res, Operation op);
	
	void revokePermission(Role role, Resource res, Operation op);
	
	Capability makeKey(Role role);
	
	void checkPermission(Capability cap, Resource res, Operation op);

}
