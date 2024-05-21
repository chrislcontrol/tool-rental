package tool.rental.presentation;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FriendsScreenFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private JTable friendsTable;
    private JScrollPane JScrollPanel;
    private JPanel Panel1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton registerFriendButton;
    private JButton exitButton;
    private JButton rankingButton;
    private JPanel MainPanel;
    private final TableConfigurator tableConfigurator = new TableConfigurator(friendsTable);
    private JTextField nameFilter;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        JTable friendsTable = new JTable();
        JScrollPane JScrollPanel = new JScrollPane(friendsTable);
        JPanel Panel1 = new JPanel();
    }

    public FriendsScreenFrame() throws ToastError {
        this.createUIComponents();
        this.setupPageLayout();
        this.setMainPanel();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.registerFriendButton,
                this.updateButton,
                this.deleteButton,
                this.updateButton,
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

    private void setupPageLayout() {
        this.setTitle("Amigos");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(60));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    public void setupTable() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        String[] columns = {"ID", "Nome", "Telefone", "Identidade"};
        for (String column : columns) {
            model.addColumn(column);
        }

        TableColumn column = this.friendsTable.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);

        this.friendsTable.setDefaultEditor(Object.class, null);
        this.loadData();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        friendsTable.setRowSorter(sorter);

    }

    private void loadData() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        model.setNumRows(0);
        String[][] friendsRows = this.controller.listFriendAsTableRow();

        for (String[] friendRow : friendsRows) {
            model.addRow(friendRow);
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
