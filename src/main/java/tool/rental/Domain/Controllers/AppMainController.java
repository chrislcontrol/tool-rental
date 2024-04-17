package tool.rental.Domain.Controllers;

import tool.rental.Domain.Entities.Tool;
import tool.rental.Domain.Repositories.ToolRepository;
import tool.rental.Utils.ToastError;

import java.util.ArrayList;

public class AppMainController {
    private final ToolRepository toolRepository = new ToolRepository();

    public String[][] listToolsAsTableRow() throws ToastError {
        ArrayList<Tool> tools = this.toolRepository.listAll();
        String[][] resultArray = new String[tools.size()][3];

        for (int i = 0; i < resultArray.length; i++) {
            Tool tool = tools.get(i);
            resultArray[i][0] = tool.getId();
            resultArray[i][1] = tool.getBrand();
            resultArray[i][2] = String.format("R$ %.2f", tool.getCost());
        }

        return resultArray;
    }
}
