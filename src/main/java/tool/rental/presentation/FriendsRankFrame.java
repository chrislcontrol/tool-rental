package tool.rental.presentation;

import tool.rental.domain.controllers.FriendsRankController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.List;

/**
 * Represents a frame to display the ranking of friends based on their rental activity.
 */
public class FriendsRankFrame extends PresentationFrame {

    // Controller instance to manage interactions for this frame
    private final FriendsRankController controller = new FriendsRankController(this);

    // Table to display the ranking information
    private JTable friendsTable;

    // Scroll pane for the table
    private JScrollPane jScrollPane;

    // Main panel of the frame
    private JPanel mainPanel;

    // Table configurator for configuring the table
    private final TableConfigurator tableConfigurator = new TableConfigurator(friendsTable);

    /**
     * Constructs a new FriendsRankFrame.
     *
     * @throws ToastError if there is an error setting up the frame.
     */
    public FriendsRankFrame() throws ToastError {
        setupPageLayout();
        setMainPanel();
        setupTable();
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        setTitle("Rank de amigos"); // Set the title of the frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        setSize(this.userScreen.widthFraction(40), this.userScreen.heightFraction(40)); // Set size of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        setContentPane(this.mainPanel); // Set the main panel of the frame
    }

    /**
     * Sets up the table with appropriate column headers and loads data into it.
     *
     * @throws ToastError if there is an error setting up the table or loading data.
     */
    public void setupTable() throws ToastError {
        String[] columns = {
                "Posição",
                "Nome",
                "Identidade",
                "Ferramentas atualmente emprestadas",
                "Total de ferramentas emprestadas"
        }; // Column headers for the table
        tableConfigurator.setup(columns, null); // Setup the table with columns
        this.loadData(); // Load data into the table
    }

    /**
     * Loads data into the table.
     *
     * @throws ToastError if there is an error loading data into the table.
     */
    private void loadData() throws ToastError {
        List<String[]> summary = this.controller.getRentalSummary(); // Get rental summary from the controller
        tableConfigurator.insertRows(summary, true); // Insert rows into the table
    }
}

