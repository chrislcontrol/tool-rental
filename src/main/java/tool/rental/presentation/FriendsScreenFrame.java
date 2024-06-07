package tool.rental.presentation;

import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.controllers.DeleteFriendController;
import tool.rental.domain.entities.Friend;
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
 * Represents a frame for displaying and managing the list of friends.
 */
public class FriendsScreenFrame extends PresentationFrame {

    // Controller instance to manage interactions for this frame
    private final AppMainController controller = new AppMainController(this);

    // Controller instance to manage friend deletion
    private final DeleteFriendController deleteFriendController = new DeleteFriendController(this);

    // Table to display the list of friends
    private JTable friendsTable;

    // Scroll pane for the friends table
    private JScrollPane jScrollPane;

    // Panel for components
    private JPanel panel1;

    // Button to update friend information
    private JButton updateButton;

    // Button to delete a friend
    private JButton deleteButton;

    // Button to register a new friend
    private JButton registerFriendButton;

    // Button to exit the screen
    private JButton exitButton;

    // Button to view friend ranking
    private JButton rankingButton;

    // Main panel of the frame
    private JPanel mainPanel;

    // Table configurator for configuring the friends table
    private final TableConfigurator tableConfigurator = new TableConfigurator(friendsTable);

    // Text field for filtering friend names
    private JTextField nameFilter;

    /**
     * Constructs a new FriendsScreenFrame.
     *
     * @throws ToastError if there is an error setting up the frame.
     */
    public FriendsScreenFrame() throws ToastError {
        this.createUIComponents();
        this.setupPageLayout();
        this.setMainPanel();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.registerFriendButton,
                this.updateButton,
                this.deleteButton,
                this.rankingButton,
                this.exitButton
        );
        this.setupTable();

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.openFriendsRankFrame();
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = friendsTable.getSelectedRow();
                    Friend friendSelected = controller.getFriendByIdAsTableRow(friendsTable.getValueAt(row, 0).toString());
                    controller.openFriendsUpdateFrame(friendSelected);
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });

        nameFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (nameFilter.getText().isEmpty()) {
                    DefaultTableModel model = (DefaultTableModel) friendsTable.getModel();
                    model.setRowCount(0);
                    try {
                        loadData();
                    } catch (ToastError ex) {
                        ex.display();
                    }
                }
            }
        });

        nameFilter.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = friendsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String friendId = friendsTable.getModel().getValueAt(row, 0).toString();
                try {
                    deleteFriendController.deleteFriend(friendId);
                    loadData();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        friendsTable.addKeyListener(new KeyAdapter() {
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

        registerFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openRegisterFriendModal(() -> {
                    try {
                        loadData();
                    } catch (ToastError exc) {
                        exc.display();
                    }
                });
            }
        });
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Amigos"); // Set the title of the frame
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.mainPanel); // Set the main panel of the frame
    }

    /**
     * Sets the pointer cursor for the specified components.
     *
     * @param cursor     The cursor to set
     * @param components The components to set the cursor for
     */
    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    /**
     * Sets up the table with appropriate column headers and loads data into it.
     *
     * @throws ToastError if there is an error setting up the table or loading data.
     */
    public void setupTable() throws ToastError {
        tableConfigurator.setup("Id", "Nome", "Telefone", "Identidade"); // Setup column headers
        this.loadData(); // Load data into the table
    }

    /**
     * Loads data into the table.
     *
     * @throws ToastError if there is an error loading data into the table.
     */
    private void loadData() throws ToastError {
        List<String[]> friendsRows = this.controller.listFriendAsTableRow(); // Get friend data
        tableConfigurator.insertRows(friendsRows, true); // Insert rows into the table
    }

    /**
     * Filters the table based on the text entered in the name filter.
     */
    private void filterTable() {
        String filterText = nameFilter.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) friendsTable.getModel();

        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            String name = (String) model.getValueAt(i, 1);
            if (!name.toLowerCase().contains(filterText)) {
                model.removeRow(i);
            }
        }
    }

    /**
     * Creates the UI components for the frame.
     */
    private void createUIComponents() {
        // Create friends table and scroll pane
        friendsTable = new JTable();
        jScrollPane = new JScrollPane(friendsTable);

        // Create panel
        panel1 = new JPanel();
    }
}


