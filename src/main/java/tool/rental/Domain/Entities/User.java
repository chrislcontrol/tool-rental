package tool.rental.Domain.Entities;

import tool.rental.Domain.Infra.DB.Contracts.Model;

public class User extends Model {


    private final String id;
    private final String username;
    private boolean authenticated;
    public boolean hasMock;

    public User(String id, String username) {
        this(id, username, false, false);
    }


    public User(String id, String username, boolean authenticated, boolean hasMock) {
        this.id = id;
        this.username = username;
        this.authenticated = authenticated;
        this.hasMock = hasMock;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setHasMock(boolean hasMock) {
        this.hasMock = hasMock;
    }
}
