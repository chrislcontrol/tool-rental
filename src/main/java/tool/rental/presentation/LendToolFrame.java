package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.controllers.LendToolFrameController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Represents a frame for lending a tool to a friend.
 */
public class LendToolFrame extends PresentationFrame {

    // Controller instance to manage the lending of the tool
    private final AppMainController controller = new AppMainController(this);

    // Controller instance to manage the actions within the lend tool frame
    private final LendToolFrameController lendToolFrameController = new LendToolFrameController(this);

    // Main panel of the frame
    private JPanel mainPanel;

    // Button to confirm the tool lending
    private JButton rentButton;

    // Button to cancel the tool lending
    private JButton cancelButton;

    // Label to display the name of the tool
    private JLabel JLtoolName;

    // Table displaying the list of friends
    private JTable friendsTable;

    // Text field for filtering friends by name
    private JTextField nameFilter;

    // ID of the tool being lent
    private String toolId;

    // Name of the tool being lent
    private String toolName;

    // Callback to execute upon successful lending of the tool
    private Runnable successCallback;

    // Table configurator instance for setting up the friends table
    private final TableConfigurator tableConfigurator = new TableConfigurator(friendsTable);

    /**
     * Constructs a new LendToolFrame.
     *
     * @param toolId         The ID of the tool being lent
     * @param toolName       The name of the tool being lent
     * @param successCallback The callback to execute upon successful lending of the tool
     * @throws ToastError If an error occurs while setting up the frame
     */
    public LendToolFrame(String toolId, String toolName, Runnable successCallback) throws ToastError {
        this.successCallback = successCallback;
        this.toolId = toolId;
        this.toolName = toolName;
        this.setMainPanel();
        this.setupPageLayout();
        this.setTextToolName();
        this.setUpListeners();
        this.setupTable();
        this.setPointer(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.rentButton,
                this.cancelButton
        );
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle(String.format("Empréstimo de ferramenta (%s) ", Settings.getUser().getUsername())); // Set the title of the frame
        this.setDefaultCloseOperation(LendToolFrame.DISPOSE_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.mainPanel); // Set the main panel of the frame
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60)); // Set size of the frame
    }

    /**
     * Sets up listeners for UI components.
     */
    protected void setUpListeners() {
        // Action listener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lendToolFrameController.closeFrame(); // Close the frame
            }
        });

        // Action listener for the rent button
        this.rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Check if the tool is already rented
                    if (lendToolFrameController.isToolRented(toolId)) {
                        throw new ToastError(
                                "A ferramenta não pode ser emprestada para mais de um amigo!",
                                "Ferramenta já emprestada"
                        );
                    }
                    // Get the ID of the friend selected
                    String friendId = friendsTable.getValueAt(friendsTable.getSelectedRow(), 0).toString();
                    // Rent the tool to the selected friend
                    lendToolFrameController.rentTool(friendId, toolId, successCallback);
                } catch (ToastError exc) {
                    exc.display(); // Display the error message
                }
            }
        });

        // Key listener for the name filter text field
        nameFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (nameFilter.getText().isEmpty()) {
                    // Clear the table if the filter text is empty
                    DefaultTableModel model = (DefaultTableModel) friendsTable.getModel();
                    model.setRowCount(0);
                    try {
                        loadData(); // Load the friends data
                    } catch (ToastError ex) {
                        ex.display(); // Display the error message
                    }
                }
            }
        });

        // Document listener for the name filter text field
        nameFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(); // Filter the table based on the text entered
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(); // Filter the table based on the text entered
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(); // Filter the table based on the text entered
            }
        });
    }

    /**
     * Sets the text of the tool name label.
     */
    private void setTextToolName() {
        JLtoolName.setText(toolName); // Set the text of the tool name label
    }

    /**
     * Loads data into the friends table.
     *
     * @throws ToastError If an error occurs while loading the data
     */
    private void loadData() throws ToastError {
        List<String[]> friendsRows = this.controller.listFriendAsTableRow(); // Get the list of friends as table rows
        tableConfigurator.insertRows(friendsRows, true); // Insert the rows into the table
    }

    /**
     * Sets up the friends table.
     *
     * @throws ToastError If an error occurs while setting up the table
     */
    private void setupTable() throws ToastError {
        tableConfigurator.setup(new String[]{"Id", "Nome", "Telefone", "Identidade"}, new int[]{0, 2}); // Set up the table columns
        this.loadData(); // Load data into the table
    }

    /**
     * Sets the cursor for the specified components.
     *
     * @param cursor     The cursor to set
     * @param components The components to set the cursor for
     */
    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor); // Set the cursor for each component
        }
    }

    /**
     * Filters the friends table based on the text entered in the name filter text field.
     */
    private void filterTable() {
        String filterText = nameFilter.getText().toLowerCase(); // Get the filter text
        DefaultTableModel model = (DefaultTableModel) friendsTable.getModel(); // Get the table model

        // Iterate through each row of the table
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String name = (String) model.getValueAt(i, 1); // Get the name from the current row
            if (!name.toLowerCase().contains(filterText)) {
                model.removeRow(i); // Remove the row if the name does not contain the filter text
            }
        }
    }
}
