package tool.rental.presentation;

import tool.rental.domain.controllers.FriendsRankController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.util.List;

public class FriendsRankFrame extends PresentationFrame {
    private final FriendsRankController controller = new FriendsRankController(this);
    private JTable friendsTable;
    private JScrollPane JScrollPanel;
    private JPanel MainPanel;
    private final TableConfigurator tableConfigurator = new TableConfigurator(friendsTable);


    public FriendsRankFrame() throws ToastError {
        setupPageLayout();
        setMainPanel();
        setupTable();
    }

    private void setupPageLayout() {
        setTitle("Rank de amigos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(this.userScreen.widthFraction(40), this.userScreen.heightFraction(40));
        setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        setContentPane(this.MainPanel);
    }

    public void setupTable() throws ToastError {
        String[] columns = {
                "Posição",
                "Nome",
                "Identidade",
                "Ferramentas atualmente emprestadas",
                "Total de ferramentas emprestadas"
        };
        tableConfigurator.setup(columns, null);
        this.loadData();
    }

    private void loadData() throws ToastError {
        List<String[]> summary = this.controller.getRentalSummary();
        tableConfigurator.insertRows(summary, true);
    }
}
