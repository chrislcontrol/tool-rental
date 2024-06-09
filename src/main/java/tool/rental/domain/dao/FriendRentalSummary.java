package tool.rental.domain.dao;

/**
 * A record that summarizes a friend's rental information.
 */
public record FriendRentalSummary(
        /**
         * The name of the friend.
         */
        String name,

        /**
         * The social security number of the friend.
         */
        String socialSecurity,

        /**
         * The total number of rentals made by the friend.
         */
        int totalRental,

        /**
         * The number of items currently borrowed by the friend.
         */
        int currentBorrowed
) {
}
