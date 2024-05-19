package tool.rental.domain.controllers;

import tool.rental.app.Settings;
import tool.rental.domain.dto.CalculateSummaryDTO;
import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.*;
import tool.rental.presentation.FriendsScreenFrame;
import tool.rental.presentation.LoginFrame;
import tool.rental.presentation.RegisterToolFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.JOptionPaneUtils;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;

public class AppMainController extends Controller {
    private final ListToolsToMainTableUseCase listToolsToMainTableUseCase = new ListToolsToMainTableUseCase();
    private final ListFriendsToMainTableUseCase listFriendsToMainTableUseCase = new ListFriendsToMainTableUseCase();
    private final LogoutUseCase logoutUseCase = new LogoutUseCase();
    private final CalculateSummaryUseCase calculateSummaryUseCase = new CalculateSummaryUseCase();
    private final ReturnToolUseCase returnToolUseCase = new ReturnToolUseCase();
    private final ToolRepository toolRepository = new ToolRepository();

    public AppMainController(PresentationFrame frame) {
        super(frame);
    }

    public String[][] listToolsAsTableRow() throws ToastError {
        return this.listToolsAsTableRow(false);
    }

    public String[][] listToolsAsTableRow(boolean rentedOnly) throws ToastError {
        return this.listToolsToMainTableUseCase.execute(rentedOnly);
    }

    public String[][] listFriendAsTableRow() throws ToastError {
        return this.listFriendsToMainTableUseCase.execute();
    }

    public CalculateSummaryDTO calculateSummary() throws ToastError {
        return this.calculateSummaryUseCase.execute();
    }

    public void logout() throws ToastError {
        int userOption = JOptionPaneUtils.showInputYesOrNoDialog(
                "Tem certeza?",
                "Sair"
        );

        if (userOption == JOptionPane.NO_OPTION) {
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

        if (userOption == JOptionPane.NO_OPTION) {
            return;
        }

        this.returnToolUseCase.execute(tool);

        JOptionPane.showMessageDialog(
                null,
                "Ferramenta devolvida com sucesso."
        );
    }
    public void openRegisterToolModal(Runnable callback) {
        this.frame.swapFrame(new RegisterToolFrame(callback), true);
    }
    public void openFriendsScreenFrame() throws ToastError {
        frame.swapFrame(new FriendsScreenFrame(),true);
    }
}
