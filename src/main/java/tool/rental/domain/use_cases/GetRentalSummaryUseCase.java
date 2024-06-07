package tool.rental.domain.use_cases;

import tool.rental.domain.dao.FriendRentalSummary;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

import java.util.List;

import java.util.List;

/**
 * This class represents a use case for retrieving a summary of rentals for friends.
 */
public class GetRentalSummaryUseCase {
    private final FriendRepository friendRepository = new FriendRepository(); // Repository for friends

    /**
     * Executes the use case to retrieve a summary of rentals for friends.
     *
     * @return A list of FriendRentalSummary objects representing the rental summary for each friend.
     * @throws ToastError if an error occurs during the execution.
     */
    public List<FriendRentalSummary> execute() throws ToastError {
        // Retrieve rental summary for friends from the repository
        return friendRepository.findRentalSummary();
    }
}

