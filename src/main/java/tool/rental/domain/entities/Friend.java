package tool.rental.domain.entities;

import tool.rental.domain.infra.db.contracts.Model;

/**
 * A friend model that represents a friend of a user.
 */
public class Friend extends Model {

    /**
     * The unique identifier of the friend.
     */
    private final String id;

    /**
     * The name of the friend.
     */
    private final String name;

    /**
     * The phone number of the friend.
     */
    private final String phone;

    /**
     * The social security number of the friend.
     */
    private final String socialSecurity;

    /**
     * The user who is friends with this friend.
     */
    private final User user;

    /**
     * Creates a new friend instance with the given id, name, phone, social security number, and user.
     *
     * @param id              the unique identifier of the friend
     * @param name            the name of the friend
     * @param phone           the phone number of the friend
     * @param socialSecurity  the social security number of the friend
     * @param user            the user who is friends with this friend
     */
    public Friend(String id, String name, String phone, String socialSecurity, User user) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.socialSecurity = socialSecurity;
        this.user = user;
    }

    /**
     * Returns the unique identifier of the friend.
     *
     * @return the id of the friend
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the friend.
     *
     * @return the name of the friend
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number of the friend.
     *
     * @return the phone number of the friend
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the social security number of the friend.
     *
     * @return the social security number of the friend
     */
    public String getSocialSecurity() {
        return socialSecurity;
    }

    /**
     * Returns the user who is friends with this friend.
     *
     * @return the user who is friends with this friend
     */
    public User getUser() {
        return user;
    }
}
