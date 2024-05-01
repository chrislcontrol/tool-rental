package tool.rental.Domain.UseCases;

import tool.rental.Domain.DAO.CountIdAndSumCostDAO;
import tool.rental.Domain.DTO.CalculateSummaryDTO;
import tool.rental.Domain.Repositories.FriendRepository;
import tool.rental.Domain.Repositories.RentalRepository;
import tool.rental.Domain.Repositories.ToolRepository;
import tool.rental.Utils.ToastError;

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
