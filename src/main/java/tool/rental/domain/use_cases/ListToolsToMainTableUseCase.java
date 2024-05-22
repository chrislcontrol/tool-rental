package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

import java.util.ArrayList;
import java.util.List;

public class ListToolsToMainTableUseCase {
    private final ToolRepository toolRepository = new ToolRepository();

    public List<String[]> execute(boolean rentedOnly) throws ToastError {
        ArrayList<Tool> tools = this.toolRepository.listAll(rentedOnly);
        ArrayList<String[]> resultArray = new ArrayList<>(tools.size());

        for (Tool tool : tools) {
            Rental latestRental = tool.getCurrentRental();
            String loanTo = "";
            String loanSince = "";
            if (latestRental != null) {
                Friend friend = latestRental.getFriend();
                loanTo = String.format("%s - %s", friend.getName(), friend.getSocialSecurity());
                loanSince = String.valueOf(latestRental.getFormattedRentalDate());
            }

            String[] row = {
                    tool.getId(),
                    tool.getBrand(),
                    tool.getName(),
                    String.format("R$ %,.2f", tool.getCost()),
                    loanTo,
                    loanSince
            };
            resultArray.add(row);
        }

        resultArray.trimToSize();

        return resultArray;
    }
}
