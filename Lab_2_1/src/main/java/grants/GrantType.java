package grants;

public class GrantType extends Grant {

    private String canonicalName;

    public GrantType(Role role, String canonicalName) {
        super(role);
        this.canonicalName = canonicalName;
    }

    public GrantType(boolean read, boolean write, Role role) {
        super(read, write, role);
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }
}
