package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for returning a tool that was borrowed.
 */
public class ReturnToolUseCase {
    private final RentalRepository rentalRepository = new RentalRepository(); // Repository for rentals

    /**
     * Executes the use case to return a tool that was borrowed.
     *
     * @param tool The tool to be returned.
     * @throws ToastError if an error occurs during the execution.
     */
    public void execute(Tool tool) throws ToastError {
        Rental rental = tool.getCurrentRental(); // Get the current rental record for the tool
        long currentTimestamp = System.currentTimeMillis(); // Get the current timestamp
        // Update the devolution timestamp of the rental record
        this.rentalRepository.updateDevolutionTimestamp(rental, currentTimestamp);
    }
}

