package tool.rental.Presentation;

import tool.rental.App.Settings;
import tool.rental.Domain.Controllers.AppMainController;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AppMainFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController();
    private JPanel MainPanel;
    private JLabel registerFriendLabel;
    private JLabel registerToolLabel;
    private JLabel lendToolLabel;
    private JLabel returnToolLabel;
    private JLabel listBorrowedToolsLabel;
    private JTable toolsTable;

    public AppMainFrame() throws ToastError {
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.registerFriendLabel,
                this.registerToolLabel,
                this.lendToolLabel,
                this.returnToolLabel,
                this.listBorrowedToolsLabel
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

    private void setPointer(Cursor cursor, JLabel... labels) {
        for (JLabel label : labels) {
            label.setCursor(cursor);
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
    }
}
