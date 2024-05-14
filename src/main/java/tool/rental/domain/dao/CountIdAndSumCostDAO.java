package tool.rental.domain.dao;

public class CountIdAndSumCostDAO {
    private final int totalCount;
    private final double totalCost;

    public CountIdAndSumCostDAO(int count, double sum) {
        this.totalCount = count;
        this.totalCost = sum;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
