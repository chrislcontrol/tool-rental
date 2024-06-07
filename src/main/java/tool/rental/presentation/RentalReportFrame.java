package tool.rental.presentation;

import tool.rental.domain.controllers.RentalReportController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.util.List;

import javax.swing.*;
import java.util.List;

/**
 * Represents a frame for displaying rental reports.
 */
public class RentalReportFrame extends PresentationFrame {

    // Controller instance to manage rental reports
    private final RentalReportController controller = new RentalReportController(this);

    // Table to display rental reports
    private JTable rentalsTable;

    // Scroll pane for the table
    private JScrollPane JScrollPanel;

    // Main panel of the frame
    private JPanel MainPanel;

    // Configurator for the table
    private final TableConfigurator tableConfigurator = new TableConfigurator(rentalsTable);

    /**
     * Constructs a new RentalReportFrame.
     *
     * @throws ToastError if an error occurs while setting up the frame
     */
    public RentalReportFrame() throws ToastError {
        setupPageLayout();
        setMainPanel();
        setupTable();
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        setTitle("Relatório de empréstimos"); // Set the title of the frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        setSize(this.userScreen.widthFraction(40), this.userScreen.heightFraction(40)); // Set size of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        setContentPane(this.MainPanel); // Set the main panel of the frame
    }

    /**
     * Sets up the table for displaying rental reports.
     *
     * @throws ToastError if an error occurs while setting up the table
     */
    public void setupTable() throws ToastError {
        tableConfigurator.setup(
                "ID",
                "Data de empréstimo",
                "Data de devolução",
                "Amigo",
                "Ferramenta"
        ); // Setup columns for the table
        this.loadData(); // Load data into the table
    }

    /**
     * Loads rental reports data into the table.
     *
     * @throws ToastError if an error occurs while loading data
     */
    private void loadData() throws ToastError {
        List<String[]> reports = controller.getRentalReport(); // Retrieve rental reports data
        tableConfigurator.insertRows(reports, true); // Insert rows into the table
    }
}

