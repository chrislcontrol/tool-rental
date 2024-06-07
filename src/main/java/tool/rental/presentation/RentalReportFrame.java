package tool.rental.presentation;

import tool.rental.domain.controllers.RentalReportController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.util.List;

public class RentalReportFrame extends PresentationFrame {
    private final RentalReportController controller = new RentalReportController(this);
    private JTable rentalsTable;
    private JScrollPane JScrollPanel;
    private JPanel MainPanel;
    private final TableConfigurator tableConfigurator = new TableConfigurator(rentalsTable);


    public RentalReportFrame() throws ToastError {
        setupPageLayout();
        setMainPanel();
        setupTable();
    }

    private void setupPageLayout() {
        setTitle("Relatório de empréstimos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(this.userScreen.widthFraction(40), this.userScreen.heightFraction(40));
        setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        setContentPane(this.MainPanel);
    }

    public void setupTable() throws ToastError {
        tableConfigurator.setup(
                "ID",
                "Data de empréstimo",
                "Data de devolução",
                "Amigo",
                "Ferramenta"
        );
        this.loadData();
    }

    private void loadData() throws ToastError {
        List<String[]> reports = controller.getRentalReport();
        tableConfigurator.insertRows(reports, true);
    }
}
