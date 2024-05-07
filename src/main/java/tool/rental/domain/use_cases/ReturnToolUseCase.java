package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.utils.ToastError;

public class ReturnToolUseCase {
    private final RentalRepository rentalRepository = new RentalRepository();

    public void execute(Tool tool) throws ToastError {
        Rental rental = tool.getCurrentRental();
        this.rentalRepository.updateDevolutionTimestamp(rental, System.currentTimeMillis());
    }
}
