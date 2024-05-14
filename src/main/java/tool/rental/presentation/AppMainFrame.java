package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.DTO.CalculateSummaryDTO;
import tool.rental.domain.repositories.RentalRepository;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AppMainFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private final RentalRepository rentalRepository = new RentalRepository();
    private JPanel mainPanel;
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
    private JCheckBox rentalCB;

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

    private void calculateSummary() throws ToastError {
        CalculateSummaryDTO summary = this.controller.calculateSummary();

        this.toolCountLabel.setText("Ferramentas: " + summary.toolCount());
        this.loanToolCountLabel.setText("Ferramentas emprestadas: " + summary.rentalCount());
        this.friendsCountLabel.setText("Amigos: " + summary.friendCount());
        this.toolTotalAmountLabel.setText("Valor total: " + summary.toolCostSum());
    }

    private void setupTable() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.toolsTable.getModel();

        String[] columns = {"ID", "Marca", "Custo", "Emprestada para", "Data de empréstimo"};

        for (String column : columns) {
            model.addColumn(column);
        }

        this.loadData();

        TableColumn column = this.toolsTable.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);

        this.toolsTable.setDefaultEditor(Object.class, null);
    }

    private void loadData() throws ToastError {
        DefaultTableModel model = (DefaultTableModel) this.toolsTable.getModel();
        model.setNumRows(0);

        boolean rentedOnly = this.rentalCB.isSelected();
        String[][] toolRows = this.controller.listToolsAsTableRow(rentedOnly);

        for (String[] toolRow : toolRows) {
            model.addRow(toolRow);
        }

        this.calculateSummary();
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
        this.setContentPane(this.mainPanel);
    }

//EM TESTE

    private String idSelecionado;

    public AppMainFrame(String idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public String getIdSelecionado() {
        return idSelecionado;
    }

    public void setIdSelecionado(String idSelecionado) {
        this.idSelecionado = idSelecionado;
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

        this.toolsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setIdSelecionado(toolsTable.getValueAt(toolsTable.getSelectedRow(), 0).toString());
                System.out.println("Esse é o id = "+ getIdSelecionado());
                lendToolButton.setEnabled(true);
                super.mouseClicked(e);
            }
        });

        this.lendToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(rentalRepository.isRentalOpen(getIdSelecionado())){
                        JOptionPane.showMessageDialog(null, "A ferramenta selecionada já está emprestada!");

                    } else if(!rentalRepository.isRentalOpen(getIdSelecionado())){
                        controller.openRegisterModal();
                    }
                } catch (ToastError exc) {
                    exc.display();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //ATÉ AQUI :D

        this.toolsTable.addKeyListener(new KeyAdapter() {
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

        this.returnToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toolsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String toolId = toolsTable.getModel().getValueAt(row, 0).toString();
                try {
                    controller.returnTool(toolId);
                    loadData();

                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        rentalCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadData();
                } catch (ToastError exc) {
                    exc.display();

                }
            }
        });
    }
}
