package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
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

public class LendToolFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private final LendToolFrameController lendToolFrameController = new LendToolFrameController(this);
    private JPanel mainPanel;
    private JButton rentButton;
    private JButton cancelButton;
    private JLabel JLtoolName;
    private JTable friendsTable;
    private JTextField nameFilter;
    private String toolId;
    private String toolName;
    private Runnable sucessCallBack;
    private final TableConfigurator tableConfigurator = new TableConfigurator(friendsTable);

    public LendToolFrame(String toolId, String toolName, Runnable sucessCallback) throws ToastError {
        this.sucessCallBack = sucessCallback;
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

    private void setupPageLayout() {
        this.setTitle(String.format("Empréstimo de ferramenta (%s) ", Settings.getUser().getUsername()));
        this.setDefaultCloseOperation(LendToolFrame.DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.mainPanel);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60));
    }

    protected void setUpListeners() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lendToolFrameController.closeFrame();
            }
        });

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
                    lendToolFrameController.rentTool(friendId, toolId, sucessCallBack);
                } catch (ToastError exc) {
                    exc.display();
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

        nameFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
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
    }

    private void setTextToolName() {
        JLtoolName.setText(toolName);
    }

    private void loadData() throws ToastError {
        List<String[]> friendsRows = this.controller.listFriendAsTableRow();

        tableConfigurator.insertRows(friendsRows, true);

    }

    private void setupTable() throws ToastError {
        tableConfigurator.setup(new String[]{"Id", "Nome", "Telefone", "Identidade"}, new int[]{0, 2});
        this.loadData();
    }

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

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
}
