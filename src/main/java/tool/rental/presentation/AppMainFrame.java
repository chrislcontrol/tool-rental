package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.dto.CalculateSummaryDTO;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class AppMainFrame extends PresentationFrame {

    private final AppMainController controller = new AppMainController(this);
    private JPanel mainPanel;
    private JButton registerFriendButton;
    private JButton registerToolButton;
    private JButton lendToolButton;
    private JButton returnToolButton;
    private JTable toolsTable;
    private JButton exitButton;
    private JLabel toolCountLabel;
    private JLabel loanToolCountLabel;
    private JLabel friendsCountLabel;
    private JLabel toolTotalAmountLabel;
    private JCheckBox rentalCB;
    private final TableConfigurator tableConfigurator = new TableConfigurator(toolsTable);

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
                this.exitButton
        );
        this.setupTable();
        registerToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openRegisterToolModal(() -> {
                    try {
                        loadData();
                    } catch (ToastError exc) {
                        exc.display();
                    }
                });
            }
        });
    }


    private void calculateSummary() throws ToastError {
        CalculateSummaryDTO summary = this.controller.calculateSummary();

        this.toolCountLabel.setText("Ferramentas: " + summary.toolCount());
        this.loanToolCountLabel.setText("Ferramentas emprestadas: " + summary.rentalCount());
        this.friendsCountLabel.setText("Amigos: " + summary.friendCount());
        this.toolTotalAmountLabel.setText("Valor total: " + summary.toolCostSum());
    }

    private void setupTable() throws ToastError {
        tableConfigurator.setup("ID", "Marca", "Nome", "Custo", "Emprestada para", "Data de empréstimo");
        this.loadData();
    }

    private void loadData() throws ToastError {
        boolean rentedOnly = this.rentalCB.isSelected();
        List<String[]> toolRows = this.controller.listToolsAsTableRow(rentedOnly);

        tableConfigurator.insertRows(toolRows, true);

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
        this.setSize(this.userScreen.widthFraction(60), this.userScreen.heightFraction(50));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.mainPanel);
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
                super.mouseClicked(e);
            }
        });

        this.lendToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String toolId = toolsTable.getValueAt(toolsTable.getSelectedRow(), 0).toString();
                String toolName = toolsTable.getValueAt(toolsTable.getSelectedRow(), 2).toString();
                try {
                    controller.openRegisterRentalModal(toolId, toolName, () -> {
                        try {
                            loadData();
                        } catch (ToastError exc) {
                            exc.display();
                        }
                    });
                } catch (ToastError exc){
                    exc.display();
                }
            };
        });
        registerFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.openFriendsScreenFrame();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

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
