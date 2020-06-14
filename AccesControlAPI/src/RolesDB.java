import java.util.ArrayList;
import java.util.List;

public class RolesDB {
    private List<Role> list = new ArrayList<Role>();

    public RolesDB(List<Role> list) {
        this.list = list;
    }

    public void safe_roles(){

    }

    public Role searchRole(Role role){
        if(list.contains(role)){
            int index = list.indexOf(role);
            return list.get(index);
        }else{
            return null;
        }
    }

    public void delete_roles(){

    }
    public void add_role(Role role){

        if(!list.contains(role)) {

            list.add(role);

        }else{

            int index = list.indexOf(role);
            list.set(index,role);

        }

    }
}
