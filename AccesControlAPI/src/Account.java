import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username, password;
    private int logged_in = 0, locked = 0, id;
    private List<Role> role = new ArrayList<Role>();

    public Account(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Role> getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role.add(role);
    }

    public void printPermissions(){
        System.out.print("----------PRINTING PERMISSIONS OF ACCOUNT:"+ this.username +"------------\n");
        for (int x = 0 ; x < this.getRole().size(); x++){
            for (int y = 0 ; y < this.getRole().get(x).getOperations().size(); y++) {
                System.out.print(this.username + " have a permissions of: " + this.getRole().get(x).getOperations().get(y).getOperationType() + " on role "
                        + this.getRole().get(x).getRole_id() + "\n");
            }
        }

    }
}
