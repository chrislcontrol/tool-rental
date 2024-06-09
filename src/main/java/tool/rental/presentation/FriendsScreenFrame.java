package tool.rental.presentation;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
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
 * Frame for displaying and managing friends in the tool rental system.
 * Extends PresentationFrame and initializes the UI components, table setup, and event listeners.
 */
public class FriendsScreenFrame extends PresentationFrame {

    // Controllers for handling the main application logic and deletion of friends
    private final AppMainController controller = new AppMainController(this);
    private final DeleteFriendController deleteFriendController = new DeleteFriendController(this);

    // UI Components
    private JTable friendsTable;
    private JScrollPane JScrollPanel;
    private JPanel Panel1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton registerFriendButton;
    private JButton exitButton;
    private JButton rankingButton;
    private JPanel MainPanel;
    private final TableConfigurator tableConfigurator;
    private JTextField nameFilter;

    /**
     * Constructs the friends screen frame and initializes components.
     *
     * @throws ToastError if there is an error during initialization
     */
    public FriendsScreenFrame() throws ToastError {
        this.tableConfigurator = new TableConfigurator(friendsTable);
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

        // Exit button action listener
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Ranking button action listener
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

        // Update button action listener
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

        // Name filter key listener for filtering table based on input
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

        // Document listener for filtering table based on input
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

        // Delete button action listener
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

        // Key listener for friends table
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

        // Register friend button action listener
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
     * Configures the frame layout and properties.
     */
    private void setupPageLayout() {
        this.setTitle("Amigos");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60));
        this.setLocationRelativeTo(null);
    }

    /**
     * Sets the main panel for the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    /**
     * Sets the cursor pointer for the specified components.
     *
     * @param cursor the cursor to be set
     * @param components the components to apply the cursor to
     */
    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    /**
     * Sets up the friends table with column headers and loads the initial data.
     *
     * @throws ToastError if there is an error during the setup or data loading
     */
    public void setupTable() throws ToastError {
        tableConfigurator.setup("Id", "Nome", "Telefone", "Identidade");
        this.loadData();
    }

    /**
     * Loads data into the friends table.
     *
     * @throws ToastError if there is an error during data loading
     */
    private void loadData() throws ToastError {
        List<String[]> friendsRows = this.controller.listFriendAsTableRow();
        tableConfigurator.insertRows(friendsRows, true);
    }

    /**
     * Filters the friends table based on the input text in the name filter.
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
        MainPanel.setLayout(new GridLayoutManager(2, 2, new Insets(50, 25, 50, 25), -1, -1));
        MainPanel.setBackground(new Color(-14539224));
        MainPanel.setEnabled(true);
        Panel1 = new JPanel();
        Panel1.setLayout(new GridLayoutManager(5, 1, new Insets(50, 25, 50, 25), -1, -1));
        Panel1.setBackground(new Color(-14539224));
        Panel1.setForeground(new Color(-2097160));
        MainPanel.add(Panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        updateButton = new JButton();
        updateButton.setBackground(new Color(-16313046));
        updateButton.setForeground(new Color(-2097160));
        updateButton.setText("Atualizar");
        Panel1.add(updateButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setBackground(new Color(-16313046));
        deleteButton.setForeground(new Color(-2097160));
        deleteButton.setText("Deletar");
        Panel1.add(deleteButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registerFriendButton = new JButton();
        registerFriendButton.setBackground(new Color(-16313046));
        registerFriendButton.setForeground(new Color(-2097160));
        registerFriendButton.setHorizontalTextPosition(11);
        registerFriendButton.setText("Cadastrar");
        Panel1.add(registerFriendButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setBackground(new Color(-4583424));
        Font exitButtonFont = UIManager.getFont("Button.font");
        if (exitButtonFont != null) exitButton.setFont(exitButtonFont);
        exitButton.setForeground(new Color(-2097160));
        exitButton.setHideActionText(false);
        exitButton.setText("Sair");
        Panel1.add(exitButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rankingButton = new JButton();
        rankingButton.setBackground(new Color(-16313046));
        rankingButton.setForeground(new Color(-2097160));
        rankingButton.setText("Ranking");
        Panel1.add(rankingButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JScrollPanel = new JScrollPane();
        JScrollPanel.setBackground(new Color(-14408668));
        MainPanel.add(JScrollPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        friendsTable = new JTable();
        friendsTable.setForeground(new Color(-15527649));
        friendsTable.setSelectionForeground(new Color(-4649));
        friendsTable.setShowVerticalLines(true);
        JScrollPanel.setViewportView(friendsTable);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-14539224));
        Font panel1Font = UIManager.getFont("Button.font");
        if (panel1Font != null) panel1.setFont(panel1Font);
        panel1.setForeground(new Color(-14539224));
        MainPanel.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nameFilter = new JTextField();
        nameFilter.setBackground(new Color(-2097160));
        Font nameFilterFont = UIManager.getFont("Button.font");
        if (nameFilterFont != null) nameFilter.setFont(nameFilterFont);
        nameFilter.setForeground(new Color(-14539224));
        panel1.add(nameFilter, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-2097160));
        label1.setEnabled(true);
        Font label1Font = UIManager.getFont("Button.font");
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-2097160));
        label1.setText(" Pesquisar : ");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}


