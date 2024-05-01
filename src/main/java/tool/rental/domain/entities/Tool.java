package tool.rental.domain.entities;

public class Tool {
    private final String id;
    private final String brand;
    private final double cost;
    private final User user;
    private Rental latestRental;

    public Tool(String id, String brand, double cost, User user, Rental latestRental) {
        this.id = id;
        this.brand = brand;
        this.cost = cost;
        this.user = user;
        this.latestRental = latestRental;
    }

    public Tool(String id, String brand, double cost, User user) {
        this(id, brand, cost, user, null);
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public double getCost() {
        return cost;
    }

    public User getUser() {
        return this.user;
    }

    public Rental getLatestRental() {
        return this.latestRental;
    }


    public void setLatestRental(Rental latestRental) {
        this.latestRental = latestRental;
    }
}
