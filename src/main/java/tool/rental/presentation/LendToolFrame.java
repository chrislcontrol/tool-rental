package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.controllers.LendToolFrameController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;

public class LendToolFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private final LendToolFrameController lendToolFrameController = new LendToolFrameController(this);
    private JPanel mainPanel;
    private JButton rentButton;
    private JButton cancelButton;
    private JLabel JLtoolName;
    private JTable friendsTable;
    private JTextField textField1;
    private String toolId;
    private String toolName;
    public LendToolFrame(String toolId, String toolName) throws ToastError {
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

    protected void setUpListeners() throws ToastError{
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LendToolFrame.this.dispose();
            }
        });

        this.rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (lendToolFrameController.isToolRented(toolId)){
                        throw new ToastError(
                                "A ferramenta não pode ser emprestada para mais de um amigo!",
                                "Ferramenta já emprestada");
                    }
                    String friendId = friendsTable.getValueAt(friendsTable.getSelectedRow(), 0).toString();
                    lendToolFrameController.rentTool(friendId, toolId);
                    JOptionPane.showMessageDialog(
                            null,
                            "Empréstimo realizado com sucesso!"
                            );
                    lendToolFrameController.swapFrame(new AppMainFrame());
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });
    }

    private void setTextToolName(){
        JLtoolName.setText(toolName);
    }

    private void loadData() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        model.setNumRows(0);
        String[][] friendsRows = this.controller.listFriendAsTableRow();

        for (String[] friendRow : friendsRows) {
            model.addRow(friendRow);
        }

    }

    private void setupTable() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.friendsTable.getModel();
        String[] columns = {"Id", "Nome", "Telefone", "Identidade"};
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
    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

}
