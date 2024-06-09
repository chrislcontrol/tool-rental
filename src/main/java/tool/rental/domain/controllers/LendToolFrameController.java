package tool.rental.domain.controllers;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.IsToolRentedUseCase;
import tool.rental.domain.use_cases.RentalToolUseCase;
import tool.rental.presentation.LendToolFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.ToastError;

/**
 * The controller for the lend tool frame.
 */
public class LendToolFrameController extends Controller {

    /**
     * The friend repository.
     */
    private final FriendRepository friendRepository = new FriendRepository();

    /**
     * The tool repository.
     */
    private final ToolRepository toolRepository = new ToolRepository();

    /**
     * The use case to rent a tool.
     */
    private final RentalToolUseCase rentalToolUseCase = new RentalToolUseCase();

    /**
     * The use case to check if a tool is rented.
     */
    private final IsToolRentedUseCase isToolRentedUseCase = new IsToolRentedUseCase();


    /**
     * Creates a new instance of the LendToolFrameController.
     *
     * @param frame the lend tool frame
     */
    public LendToolFrameController(LendToolFrame frame) {

        super(frame);
    }

    /**
     * Rents a tool to a friend.
     *
     * @param friendId the ID of the friend
     * @param toolId the ID of the tool
     * @param callback a callback to run after the tool is rented
     * @throws ToastError if an error occurs
     */
    public void rentTool(String friendId, String toolId, Runnable callback) throws ToastError {
        Tool tool = this.toolRepository.getById(toolId);
        Friend friend = this.friendRepository.getById(friendId);
        this.rentalToolUseCase.execute(friend, tool);

        callback.run();

        closeFrame();
    }

    /**
     * Checks if a tool is rented.
     *
     * @param toolId the ID of the tool
     * @return true if the tool is rented, false otherwise
     * @throws ToastError if an error occurs
     */
    public boolean isToolRented(String toolId) throws ToastError {
        return isToolRentedUseCase.execute(toolId);
    }

    /**
     * Closes the frame.
     */
    public void closeFrame() {
        frame.setVisible(false);
    }
}