import java.util.ArrayList;
import java.util.List;

public class Resource {
    private String resource;
    private RolesDB rolesDB;

    public Resource(String resource, RolesDB rolesDB) {
        this.resource = resource;
        this.rolesDB = rolesDB;
    }

    public String getResource() {
        return resource;
    }

    public RolesDB getRolesDB() {
        return rolesDB;
    }

    public void setRolesDB(RolesDB rolesDB) {
        this.rolesDB = rolesDB;
    }
}
