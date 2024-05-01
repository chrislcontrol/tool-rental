package tool.rental.Domain.Controllers;

import tool.rental.App.Settings;
import tool.rental.Domain.DTO.CalculateSummaryDTO;
import tool.rental.Domain.UseCases.CalculateSummaryUseCase;
import tool.rental.Domain.UseCases.ListToolsToMainTableUseCase;
import tool.rental.Domain.UseCases.LogoutUseCase;
import tool.rental.Presentation.LoginFrame;
import tool.rental.Utils.Controller;
import tool.rental.Utils.JOptionPaneUtils;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

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
