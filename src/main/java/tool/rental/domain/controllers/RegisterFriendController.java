package tool.rental.domain.controllers;

import tool.rental.domain.use_cases.RegisterFriendUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.JOptionPane;

/**
 * The controller for registering a friend.
 */
public class RegisterFriendController extends Controller {

    /**
     * The use case to register a friend.
     */
    private final RegisterFriendUseCase registerFriendUseCase = new RegisterFriendUseCase();

    /**
     * Creates a new instance of the RegisterFriendController.
     *
     * @param frame the presentation frame
     */
    public RegisterFriendController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Registers a friend.
     *
     * @param name the name of the friend
     * @param phone the phone number of the friend
     * @param social_security the social security number of the friend
     * @param callback a callback to run after the friend is registered
     * @throws ToastError if an error occurs
     */
    public void registerFriend(String name, String phone, String social_security, Runnable callback) throws ToastError {
        this.registerFriendUseCase.execute(name, phone, social_security);
        JOptionPane.showMessageDialog(null, "Amigo(a) cadastrado(a) com sucesso!");

        callback.run();

        closeFrame();
    }

    /**
     * Closes the frame.
     */
    public void closeFrame() {
        frame.setVisible(false);
    }
}

