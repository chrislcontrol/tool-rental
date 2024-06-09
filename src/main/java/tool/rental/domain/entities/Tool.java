package tool.rental.domain.entities;

/**
 * Represents a tool in the tool rental system.
 */
public class Tool {

    /**
     * The unique identifier of the tool.
     */
    private final String id;

    /**
     * The brand of the tool.
     */
    private final String brand;

    /**
     * The name of the tool.
     */
    private final String name;

    /**
     * The cost of the tool.
     */
    private final double cost;

    /**
     * The user who owns the tool.
     */
    private final User user;

    /**
     * The current rental of the tool, or null if the tool is not currently rented.
     */
    private Rental currentRental;

    /**
     * Constructs a new Tool object with the provided id, brand, name, cost, user, and latestRental.
     *
     * @param id the unique identifier of the tool
     * @param brand the brand of the tool
     * @param name the name of the tool
     * @param cost the cost of the tool
     * @param user the user who owns the tool
     * @param latestRental the latest rental of the tool, or null if the tool has not been rented
     */
    public Tool(String id, String brand, String name, double cost, User user, Rental latestRental) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.cost = cost;
        this.user = user;
        this.currentRental = latestRental;
    }

    /**
     * Constructs a new Tool object with the provided id, brand, name, cost, and user, and sets the latestRental to null.
     *
     * @param id the unique identifier of the tool
     * @param brand the brand of the tool
     * @param name the name of the tool
     * @param cost the cost of the tool
     * @param user the user who owns the tool
     */
    public Tool(String id, String brand, String name, double cost, User user) {
        this(id, brand, name, cost, user, null);
    }

    /**
     * Returns true if the tool is currently rented, and false otherwise.
     *
     * @return true if the tool is currently rented, and false otherwise
     */
    public boolean isRented() {
        return this.getCurrentRental()!= null;
    }

    /**
     * Returns the unique identifier of the tool.
     *
     * @return the unique identifier of the tool
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the brand of the tool.
     *
     * @return the brand of the tool
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Returns the cost of the tool.
     *
     * @return the cost of the tool
     */
    public double getCost() {
        return cost;
    }

    /**
     * Returns the user who owns the tool.
     *
     * @return the user who owns the tool
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Returns the current rental of the tool, or null if the tool is not currently rented.
     *
     * @return the current rental of the tool, or null if the tool is not currently rented
     */
    public Rental getCurrentRental() {
        return this.currentRental;
    }

    /**
     * Sets the current rental of the tool.
     *
     * @param currentRental the current rental of the tool
     */
    public void setCurrentRental(Rental currentRental) {
        this.currentRental = currentRental;
    }

    /**
     * Returns the name of the tool.
     *
     * @return the name of the tool
     */
    public String getName() {
        return this.name;
    }
}
