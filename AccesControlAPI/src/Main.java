import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.print("----------INITIAL ELEMENTS OF TEST------------\n");
        AccesControler accesControler = new AccesControler();
        List<Role>  Rolelist = new ArrayList<Role>();
        RolesDB rolesDB = new RolesDB(Rolelist);

        System.out.print("----------SETTING OPERATIONS------------\n");
        Operation write = new Operation("W");
        Operation read = new Operation("R");
        Operation diffop = new Operation("D");

        System.out.print("----------NEW ROLE ADMIN------------\n");
        Role admin = accesControler.newRole("admin");
        admin.setOperation(write);
        admin.setOperation(read);

        System.out.print("----------NEW ROLE USER------------\n");
        Role user = accesControler.newRole("user");
        user.setOperation(read);
        user.setOperation(diffop);

        System.out.print("----------CREATING RESOURCES------------\n");
        rolesDB.add_role(user);
        rolesDB.add_role(admin);
        Resource home = new Resource("/home",rolesDB);


        System.out.print("----------NEW ACCOUNT ADMIN AND USER------------\n");
        Account adminAccount = new Account("admin","1234",0);
        Account userAccount = new Account("user","1234",1);

        System.out.print("----------ASSOCIATING ROLES IN ACCOUNTS------------\n");
        accesControler.setRole(adminAccount,admin);
        accesControler.setRole(userAccount,user);

        List<Role> result_adAcc = accesControler.getRole(adminAccount);
        for (int x = 0 ; x < result_adAcc.size(); x++){
            System.out.print("adminAccount have a role of "+ result_adAcc.get(x).getRole_id()+"\n");
        }

        List<Role> result_userAcc = accesControler.getRole(userAccount);
        for (int x = 0 ; x < result_adAcc.size(); x++){
            System.out.print("userAccount have a role of "+ result_userAcc.get(x).getRole_id()+"\n");
        }

        userAccount.printPermissions();
        adminAccount.printPermissions();

        System.out.print("----GRANT D PERMISSION ON ADMIN ROLE IN HOME RESOURCE----\n");
        accesControler.grantPermission(admin,home,diffop);

        System.out.print("----REVOKE D PERMISSION ON USER ROLE IN HOME RESOURCE----\n");
        accesControler.revokePermission(user,home,diffop);

        System.out.print("----MAKING KEYS OF CAPABILITY----\n");
        Capability userCap = accesControler.makeKey(user);
        Capability adminCap = accesControler.makeKey(admin);

        System.out.print("----CHEKING PERMISSIONS----\n");
        //checking user capability permissions
        accesControler.checkPermission(userCap,home,read);
        accesControler.checkPermission(userCap,home,write);
        accesControler.checkPermission(userCap,home,diffop);
        //checking user capability permissions
        accesControler.checkPermission(adminCap,home,read);
        accesControler.checkPermission(adminCap,home,write);
        accesControler.checkPermission(adminCap,home,diffop);



    }
}
