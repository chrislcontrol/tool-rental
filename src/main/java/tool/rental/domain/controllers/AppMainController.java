package tool.rental.domain.controllers;

import tool.rental.app.Settings;
import tool.rental.domain.DTO.CalculateSummaryDTO;
import tool.rental.domain.useCases.CalculateSummaryUseCase;
import tool.rental.domain.useCases.ListToolsToMainTableUseCase;
import tool.rental.domain.useCases.LogoutUseCase;
import tool.rental.presentation.LoginFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.JOptionPaneUtils;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;

public class AppMainController extends Controller {
    private final ListToolsToMainTableUseCase listToolsToMainTableUseCase = new ListToolsToMainTableUseCase();
    private final LogoutUseCase logoutUseCase = new LogoutUseCase();
    private final CalculateSummaryUseCase calculateSummaryUseCase = new CalculateSummaryUseCase();

    public AppMainController(PresentationFrame frame) {
        super(frame);
    }

    public String[][] listToolsAsTableRow() throws ToastError {
        return this.listToolsToMainTableUseCase.execute();
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
}
