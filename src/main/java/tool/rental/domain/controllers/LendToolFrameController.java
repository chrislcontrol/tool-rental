package tool.rental.domain.controllers;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.IsRentalOpenUseCase;
import tool.rental.domain.use_cases.RentalToolUseCase;
import tool.rental.presentation.LendToolFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;


public class LendToolFrameController extends Controller {
    private final FriendRepository friendRepository = new FriendRepository();
    private final ToolRepository toolRepository = new ToolRepository();
    private final RentalToolUseCase rentalToolUseCase = new RentalToolUseCase();
    private final IsRentalOpenUseCase isRentalOpenUseCase = new IsRentalOpenUseCase();

    public LendToolFrameController(LendToolFrame Frame){
        super(Frame);

    }

    public void rentTool(String friendId, String toolId) throws ToastError {
        Tool tool = this.toolRepository.getById(toolId);
        Friend friend = this.friendRepository.getById(friendId);
        this.rentalToolUseCase.execute(friend, tool);
    }

    public boolean isToolRented(String toolId) throws ToastError {
       return isRentalOpenUseCase.isToolRented(toolId);
    }

    public void swapFrame(PresentationFrame nextFrame){
        this.frame.swapFrame(nextFrame);
    }

}