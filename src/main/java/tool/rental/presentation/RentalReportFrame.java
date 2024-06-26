package tool.rental.presentation;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import tool.rental.domain.controllers.RentalReportController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Frame for displaying the rental report in the tool rental system.
 * Extends PresentationFrame and initializes the UI components, table setup, and data loading.
 */
public class RentalReportFrame extends PresentationFrame {

    // Controller for handling rental report logic
    private final RentalReportController controller = new RentalReportController(this);

    // UI Components
    private JTable rentalsTable;
    private JScrollPane JScrollPanel;
    private JPanel MainPanel;
    private final TableConfigurator tableConfigurator;

    /**
     * Constructs the rental report frame and initializes components.
     *
     * @throws ToastError if there is an error during initialization
     */
    public RentalReportFrame() throws ToastError {
        this.tableConfigurator = new TableConfigurator(rentalsTable);
        setupPageLayout();
        setMainPanel();
        setupTable();
    }

    /**
     * Configures the frame layout and properties.
     */
    private void setupPageLayout() {
        setTitle("Relatório de empréstimos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(this.userScreen.widthFraction(40), this.userScreen.heightFraction(40));
        setLocationRelativeTo(null);
    }

    /**
     * Sets the main panel for the frame.
     */
    private void setMainPanel() {
        setContentPane(this.MainPanel);
    }

    /**
     * Sets up the rentals table with column headers and loads the initial data.
     *
     * @throws ToastError if there is an error during the setup or data loading
     */
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

    /**
     * Loads data into the rentals table.
     *
     * @throws ToastError if there is an error during data loading
     */
    private void loadData() throws ToastError {
        List<String[]> reports = controller.getRentalReport();
        tableConfigurator.insertRows(reports, true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(50, 25, 50, 25), -1, -1));
        MainPanel.setBackground(new Color(-14539224));
        MainPanel.setEnabled(true);
        JScrollPanel = new JScrollPane();
        JScrollPanel.setBackground(new Color(-14408668));
        MainPanel.add(JScrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rentalsTable = new JTable();
        rentalsTable.setForeground(new Color(-15527649));
        rentalsTable.setSelectionForeground(new Color(-4649));
        rentalsTable.setShowVerticalLines(true);
        JScrollPanel.setViewportView(rentalsTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}
