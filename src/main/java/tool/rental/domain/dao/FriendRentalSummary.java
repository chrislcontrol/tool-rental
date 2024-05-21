package tool.rental.domain.dao;

public record FriendRentalSummary(
        String name,
        String socialSecurity,
        int totalRental,
        int currentBorrowed
) {
}
