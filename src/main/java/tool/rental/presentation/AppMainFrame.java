package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.dto.CalculateSummaryDTO;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Represents the main frame of the application, providing user interface and interaction functionalities.
 */
public class AppMainFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this); // Controller for the main frame
    private JTable toolsTable; // Table displaying tool information
    private final TableConfigurator tableConfigurator = new TableConfigurator(toolsTable); // Configurator for the tools table
    private JPanel mainPanel; // Main panel containing UI components
    private JButton registerFriendButton; // Button to register a new friend
    private JButton registerToolButton; // Button to register a new tool
    private JButton lendToolButton; // Button to lend a tool to a friend
    private JButton returnToolButton; // Button to return a borrowed tool
    private JButton deleteToolButton; // Button to delete a tool
    private JButton rentalReportButton; // Button to view rental reports
    private JButton updateToolButton; // Button to update tool information
    private JButton exitButton; // Button to exit the application
    private JLabel toolCountLabel; // Label displaying the total number of tools
    private JLabel loanToolCountLabel; // Label displaying the number of tools on loan
    private JLabel friendsCountLabel; // Label displaying the total number of friends
    private JLabel toolTotalAmountLabel; // Label displaying the total cost of tools
    private JCheckBox rentalCB; // Checkbox to filter rented tools

    /**
     * Constructs a new instance of the AppMainFrame class.
     *
     * @throws ToastError if an error occurs during initialization.
     */
    public AppMainFrame() throws ToastError {
        this.setMainPanel(); // Set the main panel
        this.setupPageLayout(); // Setup page layout
        this.setUpListeners(); // Setup event listeners
        this.setPointer( // Set cursor pointer for interactive components
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.registerFriendButton,
                this.registerToolButton,
                this.lendToolButton,
                this.returnToolButton,
                this.exitButton,
                this.deleteToolButton,
                this.rentalReportButton,
                this.updateToolButton
        );
        this.setupTable(); // Setup the tools table
    }

    /**
     * Calculates and updates the summary information displayed on the UI.
     *
     * @throws ToastError if an error occurs during the calculation.
     */
    private void calculateSummary() throws ToastError {
        CalculateSummaryDTO summary = this.controller.calculateSummary();

        this.toolCountLabel.setText("Ferramentas: " + summary.toolCount());
        this.loanToolCountLabel.setText("Ferramentas emprestadas: " + summary.rentalCount());
        this.friendsCountLabel.setText("Amigos: " + summary.friendCount());
        this.toolTotalAmountLabel.setText("Valor total: " + summary.toolCostSum());
    }

    /**
     * Sets up the tools table with necessary configurations.
     *
     * @throws ToastError if an error occurs during setup.
     */
    private void setupTable() throws ToastError {
        tableConfigurator.setup("ID", "Marca", "Nome", "Custo", "Emprestada para", "Data de empréstimo");
        this.loadData();
    }

    /**
     * Loads data into the tools table based on the selected filter option.
     *
     * @throws ToastError if an error occurs during data loading.
     */
    private void loadData() throws ToastError {
        boolean rentedOnly = this.rentalCB.isSelected();
        List<String[]> toolRows = this.controller.listToolsAsTableRow(rentedOnly);

        tableConfigurator.insertRows(toolRows, true);

        this.calculateSummary();
    }

    /**
     * Sets the cursor pointer for the given components.
     *
     * @param cursor     the cursor type.
     * @param components the components to set the cursor for.
     */
    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    /**
     * Sets up the layout of the main frame.
     */
    private void setupPageLayout() {
        this.setTitle(String.format("Tool Rental (%s)", Settings.getUser().getUsername()));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(50));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.mainPanel);
    }

    /**
     * Sets up event listeners for UI components.
     */
    protected void setUpListeners() {
        // ActionListener for exitButton
        this.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.logout();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // ActionListener for lendToolButton
        this.lendToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toolId = toolsTable.getValueAt(toolsTable.getSelectedRow(), 0).toString();
                String toolName = toolsTable.getValueAt(toolsTable.getSelectedRow(), 2).toString();
                try {
                    controller.openRegisterRentalModal(toolId, toolName, () -> {
                        try {
                            loadData();
                        } catch (ToastError exc) {
                            exc.display();
                        }
                    });
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // ActionListener for registerFriendButton
        this.registerFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.openFriendsScreenFrame();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // KeyListener for toolsTable
        this.toolsTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_F5) {
                    try {
                        loadData();
                    } catch (ToastError ex) {
                        ex.display();
                    }
                }
            }
        });

        // ActionListener for returnToolButton
        this.returnToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toolsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String toolId = toolsTable.getModel().getValueAt(row, 0).toString();
                try {
                    controller.returnTool(toolId);
                    loadData();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // ActionListener for rentalCB (checkbox)
        rentalCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadData();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // ActionListener for deleteToolButton
        this.deleteToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toolsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String toolId = toolsTable.getModel().getValueAt(row, 0).toString();
                try {
                    controller.deleteTool(toolId);
                    loadData();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // ActionListener for updateToolButton
        this.updateToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toolsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String toolId = toolsTable.getValueAt(row, 0).toString();
                try {
                    controller.openUpdateToolFrame(toolId, () -> {
                        try {
                            loadData();
                        } catch (ToastError exc) {
                            exc.display();
                        }
                    });
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        // ActionListener for registerToolButton
        this.registerToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openRegisterToolModal(() -> {
                    try {
                        loadData();
                    } catch (ToastError exc) {
                        exc.display();
                    }
                });
            }
        });

        // ActionListener for rentalReportButton
        this.rentalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.openRentalReportFrame();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });
    }
}
