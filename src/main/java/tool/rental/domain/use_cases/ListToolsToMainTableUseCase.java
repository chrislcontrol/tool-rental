package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Rental;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

import java.util.ArrayList;

public class ListToolsToMainTableUseCase {
    private final ToolRepository toolRepository = new ToolRepository();

    public String[][] execute() throws ToastError {
        ArrayList<Tool> tools = this.toolRepository.listAll();
        String[][] resultArray = new String[tools.size()][5];

        for (int i = 0; i < resultArray.length; i++) {
            Tool tool = tools.get(i);
            Rental latestRental = tool.getCurrentRental();
            String loanTo = "";
            String loanSince = "";

            if (latestRental != null) {
                Friend friend = latestRental.getFriend();
                loanTo = String.format("%s - %s", friend.getName(), friend.getSocialSecurity());
                loanSince = String.valueOf(latestRental.getFormattedRentalDate());
            }

            resultArray[i][0] = tool.getId();
            resultArray[i][1] = tool.getBrand();
            resultArray[i][2] = String.format("R$ %,.2f", tool.getCost());
            resultArray[i][3] = loanTo;
            resultArray[i][4] = loanSince;
        }

        return resultArray;
    }
}
