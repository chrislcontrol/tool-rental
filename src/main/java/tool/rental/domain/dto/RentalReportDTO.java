package tool.rental.domain.dto;

/**
 * A data transfer object (DTO) that holds the details of a rental report.
 */
public record RentalReportDTO(
        /**
         * The unique identifier of the rental.
         */
        String id,

        /**
         * The date when the rental was made.
         */
        String rentalDate,

        /**
         * The date when the rental is expected to be devolved.
         */
        String devolutionDate,

        /**
         * The name of the friend who made the rental.
         */
        String friend,

        /**
         * The name of the tool that was rented.
         */
        String tool
) {
}
