package tool.rental.domain.dto;

public record CalculateSummaryDTO(int toolCount, int rentalCount, String toolCostSum, int friendCount) {
}
