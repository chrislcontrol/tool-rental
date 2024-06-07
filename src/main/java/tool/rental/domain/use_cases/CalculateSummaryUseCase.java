package tool.rental.domain.use_cases;

import tool.rental.domain.dao.CountIdAndSumCostDAO;
import tool.rental.domain.dto.CalculateSummaryDTO;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for calculating a summary of various data, including tool count, rental count,
 * total tool cost, and friend count.
 */
public class CalculateSummaryUseCase {
    private final ToolRepository toolRepository = new ToolRepository(); // Repository for tools
    private final FriendRepository friendRepository = new FriendRepository(); // Repository for friends
    private final RentalRepository rentalRepository = new RentalRepository(); // Repository for rentals

    /**
     * Executes the use case to calculate the summary.
     *
     * @return CalculateSummaryDTO object containing the calculated summary data.
     * @throws ToastError if an error occurs during the execution.
     */
    public CalculateSummaryDTO execute() throws ToastError {
        // Count and sum the cost of tools by user
        CountIdAndSumCostDAO toolResult = this.toolRepository.countAndSumCostByUser();
        double toolCostSum = toolResult.getTotalCost(); // Total cost of tools
        int toolCount = toolResult.getTotalCount(); // Total count of tools
        int rentalCount = this.rentalRepository.countBorrowedByUser(); // Total count of rentals borrowed by user
        int friendCount = this.friendRepository.countByUser(); // Total count of friends for the user

        // Create and return CalculateSummaryDTO object with the calculated summary data
        return new CalculateSummaryDTO(
                toolCount,
                rentalCount,
                String.format("R$ %,.2f", toolCostSum), // Format total tool cost as currency string
                friendCount
        );
    }
}

