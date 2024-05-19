package tool.rental.presentation;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.entities.Friend;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
    }

    private void setupPageLayout() {
        this.setTitle("Amigos");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(20), this.userScreen.heightFraction(40));
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

    private Friend friend;
    public void setupTable() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        String[] columns = {"ID", "Name", "Phone", "Social_Security", "User"};
        for (String column : columns) {
            model.addColumn(column);
        }

        TableColumn column = this.friendsTable.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);

        this.friendsTable.setDefaultEditor(Object.class, null);
        //this.loadData();

    }

    private void loadData() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        model.setNumRows(0);
        String[][] friendsRows = this.controller.listFriendAsTableRow();

        for (String[] friendRow : friendsRows) {
            model.addRow(friendRow);
    }

    }
}
