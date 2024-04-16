package tool.rental.Domain.Entities;

import tool.rental.Domain.Infra.DB.Contracts.Model;

public class User extends Model {


    private final String id;
    private final String username;
    private final String password;
    private boolean authenticated;

    public User(String id, String username, String password) {
        this(id, username, password, false);
    }


    public User(String id, String username, String password, boolean authenticated) {
        this.id = id;
        this.username = username;
        this.password = password;
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
