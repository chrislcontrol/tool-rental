package tool.rental.domain.use_cases;

import tool.rental.domain.dao.FriendRentalSummary;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

import java.util.List;

public class GetRentalSummaryUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public List<FriendRentalSummary> execute() throws ToastError {
        return friendRepository.findRentalSummary();
    }
}
