package tool.rental.Domain.Entities;

import tool.rental.Domain.Infra.DB.Contracts.Model;

public class User extends Model {


    private final String id;
    private final String username;
    private boolean authenticated;

    public User(String id, String username) {
        this(id, username, false);
    }


    public User(String id, String username, boolean authenticated) {
        this.id = id;
        this.username = username;
        this.authenticated = authenticated;
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

}
