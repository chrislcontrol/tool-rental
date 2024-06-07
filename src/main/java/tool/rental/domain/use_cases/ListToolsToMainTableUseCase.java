package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a use case for listing tools for display in the main table.
 */
public class ListToolsToMainTableUseCase {
    private final ToolRepository toolRepository = new ToolRepository(); // Repository for tools

    /**
     * Executes the use case to retrieve a list of tools formatted for display in the main table.
     *
     * @param rentedOnly Flag indicating whether to include only rented tools.
     * @return A list of String arrays representing tool data for each row in the main table.
     * @throws ToastError if an error occurs during the execution.
     */
    public List<String[]> execute(boolean rentedOnly) throws ToastError {
        // Retrieve all tools from the repository based on the rentedOnly flag
        ArrayList<Tool> tools = this.toolRepository.listAll(rentedOnly);
        ArrayList<String[]> resultArray = new ArrayList<>(tools.size()); // Initialize list to store formatted tool data

        // Iterate through each tool to format its data for the main table
        for (Tool tool : tools) {
            Rental latestRental = tool.getCurrentRental(); // Get the latest rental of the tool
            String loanTo = "";
            String loanSince = "";
            // If the tool is currently rented, get information about the rental
            if (latestRental != null) {
                Friend friend = latestRental.getFriend(); // Get the friend to whom the tool is loaned
                loanTo = String.format("%s - %s", friend.getName(), friend.getSocialSecurity()); // Format loaned to information
                loanSince = String.valueOf(latestRental.getFormattedRentalDate()); // Get formatted loaned since date
            }

            // Create a String array representing the data of the tool
            String[] row = {
                    tool.getId(), // Tool ID
                    tool.getBrand(), // Tool brand
                    tool.getName(), // Tool name
                    String.format("R$ %,.2f", tool.getCost()), // Tool cost formatted as currency string
                    loanTo, // Tool loaned to information
                    loanSince // Tool loaned since information
            };
            // Add the formatted row to the result array
            resultArray.add(row);
        }

        resultArray.trimToSize(); // Trim the result array to conserve memory

        return resultArray; // Return the list of formatted tool data
    }
}

