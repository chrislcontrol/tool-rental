package tool.rental.domain.dto;

public record RentalReportDTO(
        String id,
        String rentalDate,
        String devolutionDate,
        String friend,
        String tool
) {
}
