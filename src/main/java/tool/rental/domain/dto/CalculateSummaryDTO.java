package tool.rental.domain.dto;

/**
 * A data transfer object (DTO) that holds the summary of calculations.
 */
public record CalculateSummaryDTO(
        /**
         * The total count of tools.
         */
        int toolCount,

        /**
         * The total count of rentals.
         */
        int rentalCount,

        /**
         * The sum of the costs of all tools.
         */
        String toolCostSum,

        /**
         * The total count of friends.
         */
        int friendCount
) {
}
