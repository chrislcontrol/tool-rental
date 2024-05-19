package tool.rental.domain.entities;

public class Tool {
    private final String id;
    private final String brand;
    private final String name;
    private final double cost;
    private final User user;
    private Rental currentRental;

    public Tool(String id, String brand, String name, double cost, User user, Rental latestRental) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.cost = cost;
        this.user = user;
        this.currentRental = latestRental;
    }


    public Tool(String id, String brand, String name, double cost, User user) {
        this(id, brand, name, cost, user, null);
    }

    public boolean isRented() {
        return this.getCurrentRental() != null;
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

    public Rental getCurrentRental() {
        return this.currentRental;
    }


    public void setCurrentRental(Rental currentRental) {
        this.currentRental = currentRental;
    }

    public String getName() {
        return this.name;
    }
}
