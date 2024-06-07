package tool.rental.domain.entities;

import tool.rental.domain.infra.db.contracts.Model;

public class Friend extends Model {

    private final String id;
    private final String name;
    private final String phone;
    private final String socialSecurity;
    private final User user;

    public Friend(String id, String name, String phone, String socialSecurity, User user) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.socialSecurity = socialSecurity;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }



    public String getSocialSecurity() {
        return socialSecurity;
    }

    public User getUser() {
        return user;
    }


}
