package tool.rental.domain.use_cases;

import tool.rental.domain.dto.RentalReportDTO;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.utils.ToastError;

import java.util.List;

public class GetRentalReportUseCase {
    private final RentalRepository rentalRepository = new RentalRepository();

    public List<RentalReportDTO> execute() throws ToastError {
        List<Rental> rentals = rentalRepository.listAll();

        return rentals.stream()
                .map(
                        rental -> new RentalReportDTO(
                                rental.getId(),
                                rental.getFormattedRentalDate("dd/MM/yyyy - HH:mm"),
                                rental.getFormattedDevolutionDate("dd/MM/yyyy - HH:mm"),
                                rental.getFriend().getName() + " - " + rental.getFriend().getSocialSecurity(),
                                rental.getTool().getBrand() + " - " + rental.getTool().getName()
                        )
                ).toList();
    }
}
