package grants;

public abstract class Grant {
    private boolean read;
    private boolean write;
    private Role role;

    public Grant(Role role){
        this.read = false;
        this.write = false;
        this.role = role;
    }

    public Grant(boolean read, boolean write, Role role){
        this.read = read;
        this.write = write;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }
}
