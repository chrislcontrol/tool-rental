package tool.rental.Presentation;

import tool.rental.App.Settings;
import tool.rental.Domain.Controllers.AppMainController;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppMainFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private JPanel MainPanel;
    private JButton registerFriendButton;
    private JButton registerToolButton;
    private JButton lendToolButton;
    private JButton returnToolButton;
    private JButton listBorrowedToolsButton;
    private JTable toolsTable;
    private JButton exitButton;

    public AppMainFrame() throws ToastError {
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.registerFriendButton,
                this.registerToolButton,
                this.lendToolButton,
                this.returnToolButton,
                this.listBorrowedToolsButton,
                this.exitButton
        );
        this.setupTable();

    }

    private void setupTable() throws ToastError {
        String[][] toolRows = this.controller.listToolsAsTableRow();

        DefaultTableModel model = (DefaultTableModel) this.toolsTable.getModel();
        String[] columns = {"ID", "Marca", "Custo"};

        for (String column : columns) {
            model.addColumn(column);
        }
        for (String[] toolRow : toolRows) {
            model.addRow(toolRow);
        }
    }

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    private void setupPageLayout() {
        this.setTitle(String.format("Tool Rental (%s)", Settings.getUser().getUsername()));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(100), this.userScreen.heightFraction(100));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    protected void setUpListeners() {
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
    }
}
