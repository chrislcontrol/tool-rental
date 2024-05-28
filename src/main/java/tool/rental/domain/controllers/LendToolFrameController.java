package tool.rental.domain.controllers;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.IsToolRentedUseCase;
import tool.rental.domain.use_cases.RentalToolUseCase;
import tool.rental.presentation.AppMainFrame;
import tool.rental.presentation.LendToolFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LendToolFrameController extends Controller {
    private final FriendRepository friendRepository = new FriendRepository();
    private final ToolRepository toolRepository = new ToolRepository();
    private final RentalToolUseCase rentalToolUseCase = new RentalToolUseCase();
    private final IsToolRentedUseCase isToolRentedUseCase = new IsToolRentedUseCase();

    public LendToolFrameController(LendToolFrame frame) {
        super(frame);

    }

    public void rentTool(String friendId, List<String> toolIds, Runnable callback) throws ToastError {
        Friend friend = this.friendRepository.getById(friendId);
        List<Tool> tools = this.toolRepository.filterByIds(toolIds);
        ArrayList<String> failures = new ArrayList<>();

        for (Tool tool : tools) {
            try {
                this.rentalToolUseCase.execute(friend, tool);
            } catch (ToastError exc) {
                failures.add(tool.getId());
            }
        }

        failures.trimToSize();

        JOptionPane.showMessageDialog(
                null,
                String.format("Não foi possível emprestar %s ferramentas.", failures.size())
        );

        callback.run();

        closeFrame();
    }

    public boolean isToolRented(String toolId) throws ToastError {
        return isToolRentedUseCase.execute(toolId);
    }

    private void returnToMainFrame() throws ToastError {
        this.frame.swapFrame(new AppMainFrame());
    }

    public void closeFrame() {
        frame.setVisible(false);

    }

}