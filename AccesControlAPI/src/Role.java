import java.util.ArrayList;
import java.util.List;

public class Role {
    private String Role_id;
    private List<Operation> permissions = new ArrayList<Operation>();

    public Role(String role_id) {
        Role_id = role_id;
    }

    public void setOperation(Operation op){
        permissions.add(op);
    }

    public void deleteOperation(Operation op){
        permissions.remove(op);
    }

    public List<Operation> getOperations() {
        return permissions;
    }

    public String getRole_id() {
        return Role_id;
    }


}
