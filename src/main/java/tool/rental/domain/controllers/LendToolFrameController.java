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

public class LendToolFrameController extends Controller {
    private final FriendRepository friendRepository = new FriendRepository();
    private final ToolRepository toolRepository = new ToolRepository();
    private final RentalToolUseCase rentalToolUseCase = new RentalToolUseCase();
    private final IsToolRentedUseCase isToolRentedUseCase = new IsToolRentedUseCase();

    public LendToolFrameController(LendToolFrame frame){
        super(frame);

    }

    public void rentTool(String friendId, String toolId, Runnable callback) throws ToastError {
        Tool tool = this.toolRepository.getById(toolId);
        Friend friend = this.friendRepository.getById(friendId);
        this.rentalToolUseCase.execute(friend, tool);

        callback.run();

        closeFrame();
    }

    public boolean isToolRented(String toolId) throws ToastError {
       return isToolRentedUseCase.execute(toolId);
    }

    public void closeFrame() {
        frame.setVisible(false);

    }

}