package tool.rental.domain.entities;

import tool.rental.domain.infra.db.contracts.Model;

/**
 * A user model that represents a user in the system.
 */
public class User extends Model {

    /**
     * The unique identifier of the user.
     */
    private final String id;

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * Whether the user is authenticated or not.
     */
    private boolean authenticated;

    /**
     * Whether the user has a mock or not.
     */
    public boolean hasMock;

    /**
     * Creates a new user instance with the given id and username.
     *
     * @param id       the unique identifier of the user
     * @param username the username of the user
     */
    public User(String id, String username) {
        this(id, username, false, false);
    }

    /**
     * Creates a new user instance with the given id, username, authenticated status, and mock status.
     *
     * @param id           the unique identifier of the user
     * @param username     the username of the user
     * @param authenticated whether the user is authenticated or not
     * @param hasMock      whether the user has a mock or not
     */
    public User(String id, String username, boolean authenticated, boolean hasMock) {
        this.id = id;
        this.username = username;
        this.authenticated = authenticated;
        this.hasMock = hasMock;
    }

    /**
     * Returns whether the user is authenticated or not.
     *
     * @return whether the user is authenticated or not
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets whether the user is authenticated or not.
     *
     * @param authenticated whether the user is authenticated or not
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return the id of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets whether the user has a mock or not.
     *
     * @param hasMock whether the user has a mock or not
     */
    public void setHasMock(boolean hasMock) {
        this.hasMock = hasMock;
    }
}
