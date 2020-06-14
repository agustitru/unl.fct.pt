import java.util.List;

public class AccesControler implements AccesControlerInterface {
    public AccesControler() {
    }

    @Override
    public Role newRole(String roleid) {
        Role newrole = new Role(roleid);
        return newrole;
    }

    @Override
    public void setRole(Account user, Role role) {
        user.setRole(role);
    }

    @Override
    public List<Role> getRole(Account user) {
        return user.getRole();
    }

    @Override
    public void grantPermission(Role role, Resource res, Operation op) {
        RolesDB roles = res.getRolesDB();
        Role roleOnDb = roles.searchRole(role);
        roleOnDb.setOperation(op);
        roles.add_role(roleOnDb);
    }

    @Override
    public void revokePermission(Role role, Resource res, Operation op) {
        RolesDB roles = res.getRolesDB();
        Role roleOnDb = roles.searchRole(role);
        roleOnDb.deleteOperation(op);
        roles.add_role(roleOnDb);
    }

    @Override
    public Capability makeKey(Role role) {
        Capability cap = new Capability(role);
        return cap;
    }

    @Override
    public void checkPermission(Capability cap, Resource res, Operation op) {

        if(res.getRolesDB().searchRole(cap.getRole()).getOperations().contains(op)){

            System.out.print("---YES!, the role: "+ res.getRolesDB().searchRole(cap.getRole()).getRole_id()+
                    " have permissions on resource: "+ res.getResource() + " of operation:"+ op.getOperationType()+"---\n");

        }else{
            System.out.print("---SORRY "+ res.getRolesDB().searchRole(cap.getRole()).getRole_id() +" DON'T HAVE "+ op.getOperationType() +" PERMISSIONS ON "+ res.getResource() +"!---\n");
        }

    }
}

