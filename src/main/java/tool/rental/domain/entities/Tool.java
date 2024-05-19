package tool.rental.domain.entities;

public class Tool {
    private final String id;
    private final String name;
    private final String brand;
    private final double cost;
    private final User user;
    private Rental currentRental;

    public Tool(String id, String name, String brand, double cost, User user, Rental latestRental) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.cost = cost;
        this.user = user;
        this.currentRental = latestRental;
    }


    public Tool(String id, String name, String brand, double cost, User user) {
        this(id, name, brand, cost, user, null);
    }

    public boolean isRented() {
        return this.getCurrentRental() != null;
    }

    public String getId() {
        return id;
    }

    public String getName(){return name;}

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
}
