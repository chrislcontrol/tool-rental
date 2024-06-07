package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a use case for listing friends for display in the main table.
 */
public class ListFriendsToMainTableUseCase {
    private final FriendRepository friendRepository = new FriendRepository(); // Repository for friends

    /**
     * Executes the use case to retrieve a list of friends formatted for display in the main table.
     *
     * @return A list of String arrays representing friend data for each row in the main table.
     * @throws ToastError if an error occurs during the execution.
     */
    public List<String[]> execute() throws ToastError {
        ArrayList<Friend> friends = this.friendRepository.listAll(); // Retrieve all friends from the repository
        ArrayList<String[]> resultArray = new ArrayList<>(friends.size()); // Initialize list to store formatted friend data

        // Iterate through each friend to format their data for the main table
        for (Friend friend : friends) {
            // Create a String array representing the data of the friend
            String[] row = {
                    friend.getId(),
                    friend.getName(),
                    friend.getPhone(),
                    friend.getSocialSecurity()
            };
            // Add the formatted row to the result array
            resultArray.add(row);
        }

        resultArray.trimToSize(); // Trim the result array to conserve memory

        return resultArray; // Return the list of formatted friend data
    }

    /**
     * Retrieves a friend by their ID.
     *
     * @param friendId The ID of the friend to retrieve.
     * @return The Friend object corresponding to the specified ID.
     * @throws ToastError if an error occurs during the retrieval.
     */
    public Friend getFriendById(String friendId) throws ToastError {
        return this.friendRepository.getById(friendId); // Retrieve the friend by their ID from the repository
    }
}

