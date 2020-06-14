public class Capability {
    private String capability_id;
    private Role role;

    public Capability(Role role) {
        this.capability_id = role.getRole_id() + "Capability";
        this.role = role;
    }

    public Role getRole() {
        return role;
    }


}
