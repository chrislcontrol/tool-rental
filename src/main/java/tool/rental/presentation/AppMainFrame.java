package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.DTO.CalculateSummaryDTO;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
    private JLabel toolCountLabel;
    private JLabel loanToolCountLabel;
    private JLabel friendsCountLabel;
    private JLabel toolTotalAmountLabel;

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
        this.calculateSummary();

    }

    private void calculateSummary() throws ToastError {
        CalculateSummaryDTO summary = this.controller.calculateSummary();

        this.toolCountLabel.setText(this.toolCountLabel.getText() + summary.toolCount());
        this.loanToolCountLabel.setText(this.loanToolCountLabel.getText() + summary.rentalCount());
        this.friendsCountLabel.setText(this.friendsCountLabel.getText() + summary.friendCount());
        this.toolTotalAmountLabel.setText(this.toolTotalAmountLabel.getText() + summary.toolCostSum());
    }

    private void setupTable() throws ToastError {
        String[][] toolRows = this.controller.listToolsAsTableRow();

        DefaultTableModel model = (DefaultTableModel) this.toolsTable.getModel();


        String[] columns = {"ID", "Marca", "Custo", "Emprestada para", "Data de empréstimo"};

        for (String column : columns) {
            model.addColumn(column);
        }
        for (String[] toolRow : toolRows) {
            model.addRow(toolRow);
        }

        TableColumn column = this.toolsTable.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);

        this.toolsTable.setDefaultEditor(Object.class, null);
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
