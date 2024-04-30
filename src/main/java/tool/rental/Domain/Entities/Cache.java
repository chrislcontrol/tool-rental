package tool.rental.Domain.Entities;

import tool.rental.Domain.Infra.DB.Contracts.Model;

public class Cache extends Model {
    private final String id;
    private final User user;


    public Cache(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
