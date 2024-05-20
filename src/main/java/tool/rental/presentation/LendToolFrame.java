package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.controllers.LendToolFrameController;
import tool.rental.domain.entities.Friend;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LendToolFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private final LendToolFrameController lendToolFrameController = new LendToolFrameController(this);
    private JPanel mainPanel;
    private JComboBox friendList;
    private JButton rentButton;
    private JButton cancelButton;
    private JLabel toolName;
    private JTable friendsTable;
    private String toolId;
    public LendToolFrame(String toolId) throws ToastError {
        this.toolId = toolId;
        this.setMainPanel();
        this.setupPageLayout();
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
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.mainPanel);
    }

    public void setUpListeners(){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LendToolFrame.this.dispose();
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
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

        TableColumn column2 = this.friendsTable.getColumnModel().getColumn(2);
        column2.setMinWidth(0);
        column2.setMaxWidth(0);

        this.friendsTable.setDefaultEditor(Object.class, null);
        this.loadData();

    }

    private void loadData() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        model.setNumRows(0);
        String[][] friendsRows = this.controller.listFriendAsTableRow();

        for (String[] friendRow : friendsRows) {
            model.addRow(friendRow);
        }
    }

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

}
