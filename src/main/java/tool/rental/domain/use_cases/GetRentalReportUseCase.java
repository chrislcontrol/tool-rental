package tool.rental.domain.use_cases;

import tool.rental.domain.dto.RentalReportDTO;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.utils.ToastError;

import java.util.List;

import java.util.List;

/**
 * This class represents a use case for generating a rental report.
 */
public class GetRentalReportUseCase {
    private final RentalRepository rentalRepository = new RentalRepository(); // Repository for rentals

    /**
     * Executes the use case to retrieve a list of rental reports.
     *
     * @return A list of RentalReportDTO objects representing rental reports.
     * @throws ToastError if an error occurs during the execution.
     */
    public List<RentalReportDTO> execute() throws ToastError {
        List<Rental> rentals = rentalRepository.listAll(); // Retrieve all rentals from the repository

        // Map each rental to a RentalReportDTO object representing a rental report
        return rentals.stream()
                .map(
                        rental -> new RentalReportDTO(
                                rental.getId(),
                                rental.getFormattedRentalDate("dd/MM/yyyy - HH:mm"), // Format rental date
                                rental.getFormattedDevolutionDate("dd/MM/yyyy - HH:mm"), // Format devolution date
                                rental.getFriend().getName() + " - " + rental.getFriend().getSocialSecurity(), // Friend info
                                rental.getTool().getBrand() + " - " + rental.getTool().getName() // Tool info
                        )
                ).toList();
    }
}

