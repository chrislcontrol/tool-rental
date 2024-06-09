package tool.rental.domain.controllers;

import tool.rental.app.Settings;
import tool.rental.domain.dto.CalculateSummaryDTO;
import tool.rental.domain.entities.Friend;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.*;
import tool.rental.presentation.*;
import tool.rental.utils.Controller;
import tool.rental.utils.JOptionPaneUtils;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.util.List;

/**
 * The main controller of the application.
 */
public class AppMainController extends Controller {

    /**
     * The use case to check if a tool is rented.
     */
    private final IsToolRentedUseCase isToolRentedUseCase = new IsToolRentedUseCase();

    /**
     * The use case to list tools to the main table.
     */
    private final ListToolsToMainTableUseCase listToolsToMainTableUseCase = new ListToolsToMainTableUseCase();

    /**
     * The use case to list friends to the main table.
     */
    private final ListFriendsToMainTableUseCase listFriendsToMainTableUseCase = new ListFriendsToMainTableUseCase();

    /**
     * The use case to logout.
     */
    private final LogoutUseCase logoutUseCase = new LogoutUseCase();

    /**
     * The use case to calculate the summary.
     */
    private final CalculateSummaryUseCase calculateSummaryUseCase = new CalculateSummaryUseCase();

    /**
     * The use case to return a tool.
     */
    private final ReturnToolUseCase returnToolUseCase = new ReturnToolUseCase();

    /**
     * The tool repository.
     */
    private final ToolRepository toolRepository = new ToolRepository();

    /**
     * The use case to delete a tool.
     */
    private final DeleteToolUseCase deleteToolUseCase = new DeleteToolUseCase();

    /**
     * The use case to delete a friend.
     */
    private final DeleteFriendUseCase deleteFriendUseCase = new DeleteFriendUseCase();

    /**
     * The friend repository.
     */
    private final FriendRepository friendsRepository = new FriendRepository();

    /**
     * Creates a new instance of the AppMainController.
     *
     * @param frame the presentation frame
     */
    public AppMainController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Lists the tools as table rows.
     *
     * @return the list of tools as table rows
     * @throws ToastError if an error occurs
     */
    public List<String[]> listToolsAsTableRow() throws ToastError {
        return this.listToolsAsTableRow(false);
    }

    /**
     * Lists the tools as table rows, optionally filtering by rented tools only.
     *
     * @param rentedOnly whether to filter by rented tools only
     * @return the list of tools as table rows
     * @throws ToastError if an error occurs
     */
    public List<String[]> listToolsAsTableRow(boolean rentedOnly) throws ToastError {
        return this.listToolsToMainTableUseCase.execute(rentedOnly);
    }

    /**
     * Lists the friends as table rows.
     *
     * @return the list of friends as table rows
     * @throws ToastError if an error occurs
     */
    public List<String[]> listFriendAsTableRow() throws ToastError {
        return this.listFriendsToMainTableUseCase.execute();
    }

    /**
     * Gets a friend by ID as a table row.
     *
     * @param friendId the ID of the friend
     * @return the friend as a table row
     * @throws ToastError if an error occurs
     */
    public Friend getFriendByIdAsTableRow(String friendId) throws ToastError {
        return this.listFriendsToMainTableUseCase.getFriendById(friendId);
    }

    /**
     * Calculates the summary.
     *
     * @return the summary
     * @throws ToastError if an error occurs
     */
    public CalculateSummaryDTO calculateSummary() throws ToastError {
        return this.calculateSummaryUseCase.execute();
    }

    /**
     * Logs out the user.
     *
     * @throws ToastError if an error occurs
     */
    public void logout() throws ToastError {
        int userOption = JOptionPaneUtils.showInputYesOrNoDialog(
                "Tem certeza?",
                "Sair"
        );

        if (userOption != JOptionPane.YES_OPTION) {
            return;
        }

        JOptionPane.showMessageDialog(
                null,
                "Volte sempre " + Settings.getUser().getUsername(),
                "Logout",
                JOptionPane.INFORMATION_MESSAGE
        );

        this.logoutUseCase.execute();
        this.frame.swapFrame(new LoginFrame());

    }

    /**
     * Returns a tool.
     *
     * @param toolId the ID of the tool
     * @throws ToastError if an error occurs
     */
    public void returnTool(String toolId) throws ToastError {
        Tool tool = this.toolRepository.getById(toolId);
        if (!tool.isRented()) {
            JOptionPane.showMessageDialog(
                    null,
                    "A ferramenta selecionada não está emprestada."
            );
            return;
        }

        int userOption = JOptionPaneUtils.showInputYesOrNoDialog(
                "Tem certeza que deseja devolver esta ferramenta?",
                "Devolver ferramenta"
        );

        if (userOption != JOptionPane.YES_OPTION) {
            return;
        }

        this.returnToolUseCase.execute(tool);

        JOptionPane.showMessageDialog(
                null,
                "Ferramenta devolvida com sucesso."
        );
    }

    /**
     * Opens the register rental modal.
     *
     * @param toolId the ID of the tool
     * @param toolName the name of the tool
     * @param callback the callback to execute after the modal is closed
     * @throws ToastError if an error occurs
     */
    public void openRegisterRentalModal(String toolId, String toolName, Runnable callback) throws ToastError {
        if (isToolRentedUseCase.execute(toolId)) {
            throw new ToastError(
                    "Ferramenta selecionada já está emprestada!",
                    "Ferramenta já emprestada"
            );
        }

        int userOption = JOptionPaneUtils.showInputYesOrNoDialog(
                "Tem certeza que deseja emprestar esta ferramenta?",
                "Emprestar ferramenta"
        );

        if (userOption != JOptionPane.YES_OPTION) {
            return;
        }

        this.frame.swapFrame(new LendToolFrame(toolId, toolName, callback), true);
    }

    /**
     * Opens the register tool modal.
     *
     * @param callback the callback to execute after the modal is closed
     */
    public void openRegisterToolModal(Runnable callback) {
        this.frame.swapFrame(new RegisterToolFrame(callback), true);

    }

    /**
     * Opens the register friend modal.
     *
     * @param callback the callback to execute after the modal is closed
     */
    public void openRegisterFriendModal(Runnable callback) {
        this.frame.swapFrame(new RegisterFriendFrame(callback), true);
    }

    /**
     * Opens the friends screen frame.
     *
     * @throws ToastError if an error occurs
     */
    public void openFriendsScreenFrame() throws ToastError {
        frame.swapFrame(new FriendsScreenFrame(), true);
    }

    /**
     * Opens the friends rank frame.
     *
     * @throws ToastError if an error occurs
     */
    public void openFriendsRankFrame() throws ToastError {
        frame.swapFrame(new FriendsRankFrame(), true);
    }

    /**
     * Opens the friends update frame.
     *
     * @param row the friend to update
     * @throws ToastError if an error occurs
     */
    public void openFriendsUpdateFrame(Friend row) throws ToastError {
        frame.swapFrame(new FriendUpdateFrame(row), true);
    }

    /**
     * Deletes a tool.
     *
     * @param toolId the ID of the tool
     * @throws ToastError if an error occurs
     */
    public void deleteTool(String toolId) throws ToastError {
        Tool tool = this.toolRepository.getById(toolId);
        if (tool.isRented()) {
            throw new ToastError(
                    "Não é possível deletar uma ferramenta emprestada.",
                    "Ferramenta emprestada"
            );
        }

        int userOption = JOptionPaneUtils.showInputYesOrNoDialog(
                "Tem certeza que deseja deletar esta ferramenta?",
                "Deletar ferramenta"
        );

        if (userOption != JOptionPane.YES_OPTION) {
            return;
        }

        this.deleteToolUseCase.execute(tool);

        JOptionPane.showMessageDialog(
                null,
                "Ferramenta deletada com sucesso!"
        );
    }

    /**
     * Opens the rental report frame.
     *
     * @throws ToastError if an error occurs
     */
    public void openRentalReportFrame() throws ToastError {
        frame.swapFrame(new RentalReportFrame(), true);
    }

    /**
     * Opens the update tool frame.
     *
     * @param toolId the ID of the tool
     * @param callback the callback to execute after the frame is closed
     * @throws ToastError if an error occurs
     */
    public void openUpdateToolFrame(String toolId, Runnable callback) throws ToastError {
        frame.swapFrame(new UpdateToolFrame(toolId, callback), true);
    }
}
