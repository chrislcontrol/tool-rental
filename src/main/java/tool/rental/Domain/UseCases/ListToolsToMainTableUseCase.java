package tool.rental.Domain.UseCases;

import tool.rental.Domain.Entities.Friend;
import tool.rental.Domain.Entities.Rental;
import tool.rental.Domain.Entities.Tool;
import tool.rental.Domain.Repositories.ToolRepository;
import tool.rental.Utils.ToastError;

import java.util.ArrayList;

public class ListToolsToMainTableUseCase {
    private final ToolRepository toolRepository = new ToolRepository();

    public String[][] execute() throws ToastError {
        ArrayList<Tool> tools = this.toolRepository.listAll();
        String[][] resultArray = new String[tools.size()][5];

        for (int i = 0; i < resultArray.length; i++) {
            Tool tool = tools.get(i);
            Rental latestRental = tool.getLatestRental();
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
