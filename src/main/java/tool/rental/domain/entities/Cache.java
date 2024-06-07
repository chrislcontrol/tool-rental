package tool.rental.domain.entities;

import tool.rental.domain.infra.db.contracts.Model;

/**
 * A cache model that stores data for faster access.
 */
public class Cache extends Model {
    /**
     * The unique identifier of the cache.
     */
    private final String id;

    /**
     * The user associated with the cache.
     */
    private final User user;

    /**
     * Creates a new cache instance with the given id and user.
     *
     * @param id   the unique identifier of the cache
     * @param user the user associated with the cache
     */
    public Cache(String id, User user) {
        this.id = id;
        this.user = user;
    }

    /**
     * Returns the unique identifier of the cache.
     *
     * @return the id of the cache
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the user associated with the cache.
     *
     * @return the user of the cache
     */
    public User getUser() {
        return user;
    }
}
