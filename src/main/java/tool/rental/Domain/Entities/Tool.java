package tool.rental.Domain.Entities;

public class Tool {
    private final String id;
    private final String brand;
    private final double cost;
    private final String user_id;

    public Tool(String id, String brand, double cost, String user_id) {
        this.id = id;
        this.brand = brand;
        this.cost = cost;
        this.user_id = user_id;
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

    public String getUser_id() {
        return user_id;
    }

}
