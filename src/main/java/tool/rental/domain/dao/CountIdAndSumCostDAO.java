package tool.rental.domain.dao;

/**
 * A data access object (DAO) that holds the total count and total cost of tools.
 */
public class CountIdAndSumCostDAO {

    /**
     * The total count of tools.
     */
    private final int totalCount;

    /**
     * The total cost of tools.
     */
    private final double totalCost;

    /**
     * Creates a new instance of the CountIdAndSumCostDAO.
     *
     * @param count the total count of tools
     * @param sum the total cost of tools
     */
    public CountIdAndSumCostDAO(int count, double sum) {
        this.totalCount = count;
        this.totalCost = sum;
    }

    /**
     * Returns the total count of tools.
     *
     * @return the total count of tools
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Returns the total cost of tools.
     *
     * @return the total cost of tools
     */
    public double getTotalCost() {
        return totalCost;
    }
}
