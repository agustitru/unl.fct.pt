package com.agustitru;

public class AccesControlUnit implements AccesControler {

	@Override
	public Role newRole(String roleid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRole(Account user, Role role) {
		// TODO Auto-generated method stub

	}

	@Override
	public Role getRole(Account user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void grantPermission(Role role, Resource res, Operation op) {
		// TODO Auto-generated method stub

	}

	@Override
	public void revokePermission(Role role, Resource res, Operation op) {
		// TODO Auto-generated method stub

	}

	@Override
	public Capability makeKey(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkPermission(Capability cap, Resource res, Operation op) {
		// TODO Auto-generated method stub

	}

}
