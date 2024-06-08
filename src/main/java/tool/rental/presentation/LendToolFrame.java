package tool.rental.presentation;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.controllers.LendToolFrameController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Locale;

/**
 * Frame for lending tools to friends in the tool rental system.
 * Extends PresentationFrame and initializes the UI components, table setup, and event listeners.
 */
public class LendToolFrame extends PresentationFrame {

    // Controllers for handling the main application logic and tool lending logic
    private final AppMainController controller = new AppMainController(this);
    private final LendToolFrameController lendToolFrameController = new LendToolFrameController(this);

    // UI Components
    private JPanel mainPanel;
    private JButton rentButton;
    private JButton cancelButton;
    private JLabel JLtoolName;
    private JTable friendsTable;
    private JTextField nameFilter;

    // Tool details and success callback
    private final String toolId;
    private final String toolName;
    private final Runnable successCallback;
    private final TableConfigurator tableConfigurator;

    /**
     * Constructs the lend tool frame and initializes components.
     *
     * @param toolId         the ID of the tool to be lent
     * @param toolName       the name of the tool to be lent
     * @param successCallback the callback to be executed upon successful lending
     * @throws ToastError if there is an error during initialization
     */
    public LendToolFrame(String toolId, String toolName, Runnable successCallback) throws ToastError {
        this.tableConfigurator = new TableConfigurator(friendsTable);
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
     * Configures the frame layout and properties.
     */
    private void setupPageLayout() {
        this.setTitle(String.format("Empréstimo de ferramenta (%s) ", Settings.getUser().getUsername()));
        this.setDefaultCloseOperation(LendToolFrame.DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60));
        this.setLocationRelativeTo(null);
    }

    /**
     * Sets the main panel for the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.mainPanel);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60));
    }

    /**
     * Sets up event listeners for the UI components.
     */
    protected void setUpListeners() {
        // Cancel button action listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lendToolFrameController.closeFrame();
            }
        });

        // Rent button action listener
        this.rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (lendToolFrameController.isToolRented(toolId)) {
                        throw new ToastError(
                                "A ferramenta não pode ser emprestada para mais de um amigo!",
                                "Ferramenta já emprestada");
                    }
                    String friendId = friendsTable.getValueAt(friendsTable.getSelectedRow(), 0).toString();
                    lendToolFrameController.rentTool(friendId, toolId, successCallback);
                } catch (ToastError exc) {
                    exc.display();
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
    }

    /**
     * Sets the tool name label with the provided tool name.
     */
    private void setTextToolName() {
        JLtoolName.setText(toolName);
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
     * Sets up the friends table with column headers and loads the initial data.
     *
     * @throws ToastError if there is an error during the setup or data loading
     */
    private void setupTable() throws ToastError {
        tableConfigurator.setup(new String[]{"Id", "Nome", "Telefone", "Identidade"}, new int[]{0, 2});
        this.loadData();
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(4, 5, new Insets(50, 25, 50, 25), -1, -1));
        mainPanel.setBackground(new Color(-14539224));
        mainPanel.setEnabled(true);
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(0, 4, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new GridConstraints(0, 2, 3, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        friendsTable = new JTable();
        scrollPane1.setViewportView(friendsTable);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-14539224));
        Font panel1Font = UIManager.getFont("Button.font");
        if (panel1Font != null) panel1.setFont(panel1Font);
        panel1.setForeground(new Color(-14539224));
        mainPanel.add(panel1, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nameFilter = new JTextField();
        nameFilter.setBackground(new Color(-2097160));
        Font nameFilterFont = UIManager.getFont("TextField.font");
        if (nameFilterFont != null) nameFilter.setFont(nameFilterFont);
        nameFilter.setForeground(new Color(-16777216));
        panel1.add(nameFilter, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-2097160));
        label1.setEnabled(true);
        Font label1Font = UIManager.getFont("Button.font");
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-2097160));
        label1.setText(" Pesquisar : ");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-14539224));
        panel2.setForeground(new Color(-2097160));
        mainPanel.add(panel2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rentButton = new JButton();
        rentButton.setBackground(new Color(-16313046));
        Font rentButtonFont = UIManager.getFont("Button.font");
        if (rentButtonFont != null) rentButton.setFont(rentButtonFont);
        rentButton.setForeground(new Color(-2097160));
        rentButton.setText("Emprestar");
        panel2.add(rentButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setBackground(new Color(-4583424));
        Font cancelButtonFont = UIManager.getFont("Button.font");
        if (cancelButtonFont != null) cancelButton.setFont(cancelButtonFont);
        cancelButton.setForeground(new Color(-2097160));
        cancelButton.setText("Sair");
        panel2.add(cancelButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-14539224));
        panel3.setForeground(new Color(-2097160));
        mainPanel.add(panel3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-2097160)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-14539224));
        Font label2Font = this.$$$getFont$$$(null, -1, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-2097160));
        label2.setText("Emprestar para:");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(136, 11), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-14539224));
        Font label3Font = this.$$$getFont$$$(null, -1, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-2097160));
        label3.setText("Ferramenta:");
        panel3.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JLtoolName = new JLabel();
        JLtoolName.setBackground(new Color(-14539224));
        Font JLtoolNameFont = this.$$$getFont$$$(null, -1, 18, JLtoolName.getFont());
        if (JLtoolNameFont != null) JLtoolName.setFont(JLtoolNameFont);
        JLtoolName.setForeground(new Color(-2097160));
        JLtoolName.setText("");
        panel3.add(JLtoolName, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
