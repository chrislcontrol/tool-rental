package tool.rental.domain.use_cases;

import tool.rental.domain.DAO.CountIdAndSumCostDAO;
import tool.rental.domain.DTO.CalculateSummaryDTO;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class CalculateSummaryUseCase {
    private final ToolRepository toolRepository = new ToolRepository();
    private final FriendRepository friendRepository = new FriendRepository();
    private final RentalRepository rentalRepository = new RentalRepository();

    public CalculateSummaryDTO execute() throws ToastError {
        CountIdAndSumCostDAO toolResult = this.toolRepository.countAndSumCostByUser();
        double toolCostSum = toolResult.getTotalCost();
        int toolCount = toolResult.getTotalCount();
        int rentalCount = this.rentalRepository.countBorrowedByUser();
        int friendCount = this.friendRepository.countByUser();

        return new CalculateSummaryDTO(
                toolCount,
                rentalCount,
                String.format("R$ %,.2f", toolCostSum),
                friendCount
        );
    }
}
