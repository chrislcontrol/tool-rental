package tool.rental.presentation;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import tool.rental.app.Settings;
import tool.rental.domain.controllers.AppMainController;
import tool.rental.domain.dto.CalculateSummaryDTO;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.TableConfigurator;
import tool.rental.utils.ToastError;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Locale;

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
    private JButton deleteToolButton;
    private JButton rentalReportButton;
    private JButton updateToolButton;
    private final TableConfigurator tableConfigurator;

    public AppMainFrame() throws ToastError {
        this.setMainPanel();
        this.tableConfigurator = new TableConfigurator(toolsTable);

        this.setupPageLayout();
        this.setUpListeners();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.registerFriendButton,
                this.registerToolButton,
                this.lendToolButton,
                this.returnToolButton,
                this.exitButton,
                this.deleteToolButton,
                this.rentalReportButton,
                this.updateToolButton
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
                } catch (ToastError exc) {
                    exc.display();
                }
            }

            ;
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

        deleteToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toolsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String toolId = toolsTable.getModel().getValueAt(row, 0).toString();
                try {
                    controller.deleteTool(toolId);
                    loadData();

                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        updateToolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = toolsTable.getSelectedRow();
                if (row == -1) {
                    return;
                }
                String toolId = toolsTable.getValueAt(row, 0).toString();
                try {
                    controller.openUpdateToolFrame(toolId, () -> {
                        try {
                            loadData();
                        } catch (ToastError exc) {
                            exc.display();
                        }
                    });
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

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

        rentalReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.openRentalReportFrame();
                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-14539224));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(50, 25, 50, 25), -1, -1));
        panel1.setBackground(new Color(-14539224));
        Font panel1Font = UIManager.getFont("Button.font");
        if (panel1Font != null) panel1.setFont(panel1Font);
        mainPanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-14539224));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        exitButton = new JButton();
        exitButton.setBackground(new Color(-4583424));
        exitButton.setForeground(new Color(-1441800));
        exitButton.setText("Sair");
        panel2.add(exitButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registerFriendButton = new JButton();
        registerFriendButton.setBackground(new Color(-16313046));
        registerFriendButton.setForeground(new Color(-1441800));
        registerFriendButton.setText("Amigos");
        panel2.add(registerFriendButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        returnToolButton = new JButton();
        returnToolButton.setBackground(new Color(-16313046));
        returnToolButton.setForeground(new Color(-1441800));
        returnToolButton.setText("Devolver ferramenta");
        panel2.add(returnToolButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lendToolButton = new JButton();
        lendToolButton.setBackground(new Color(-16313046));
        lendToolButton.setForeground(new Color(-1441800));
        lendToolButton.setText("Emprestar ferramenta");
        panel2.add(lendToolButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registerToolButton = new JButton();
        registerToolButton.setBackground(new Color(-16313046));
        registerToolButton.setForeground(new Color(-1441800));
        registerToolButton.setText("Cadastrar ferramenta");
        panel2.add(registerToolButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteToolButton = new JButton();
        deleteToolButton.setBackground(new Color(-16313046));
        deleteToolButton.setForeground(new Color(-1441800));
        deleteToolButton.setText("Deletar ferramenta");
        panel2.add(deleteToolButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rentalReportButton = new JButton();
        rentalReportButton.setBackground(new Color(-16313046));
        rentalReportButton.setForeground(new Color(-1441800));
        rentalReportButton.setText("Relatório de empréstimos");
        panel2.add(rentalReportButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateToolButton = new JButton();
        updateToolButton.setBackground(new Color(-16313046));
        updateToolButton.setForeground(new Color(-1441800));
        updateToolButton.setText("Atualizar ferramenta");
        panel2.add(updateToolButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel3.setBackground(new Color(-14539224));
        panel1.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-1)), "Totais", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel3.getFont()), new Color(-262149)));
        toolCountLabel = new JLabel();
        toolCountLabel.setBackground(new Color(-262151));
        toolCountLabel.setForeground(new Color(-458753));
        toolCountLabel.setText("Ferramentas: ");
        panel3.add(toolCountLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 18), null, 0, false));
        friendsCountLabel = new JLabel();
        friendsCountLabel.setBackground(new Color(-262151));
        friendsCountLabel.setForeground(new Color(-458753));
        friendsCountLabel.setText("Amigos: ");
        panel3.add(friendsCountLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 18), null, 0, false));
        toolTotalAmountLabel = new JLabel();
        toolTotalAmountLabel.setBackground(new Color(-262151));
        toolTotalAmountLabel.setForeground(new Color(-458753));
        toolTotalAmountLabel.setText("Valor total: ");
        panel3.add(toolTotalAmountLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 18), null, 0, false));
        loanToolCountLabel = new JLabel();
        loanToolCountLabel.setBackground(new Color(-262151));
        loanToolCountLabel.setForeground(new Color(-458753));
        loanToolCountLabel.setText("Ferramentas emprestadas: ");
        panel3.add(loanToolCountLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 18), null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBackground(new Color(-14408668));
        scrollPane1.setEnabled(true);
        Font scrollPane1Font = UIManager.getFont("Button.font");
        if (scrollPane1Font != null) scrollPane1.setFont(scrollPane1Font);
        scrollPane1.setForeground(new Color(-1441800));
        mainPanel.add(scrollPane1, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        toolsTable = new JTable();
        toolsTable.setEditingColumn(-1);
        toolsTable.setEnabled(true);
        toolsTable.setForeground(new Color(-15527649));
        toolsTable.setSelectionForeground(new Color(-4649));
        scrollPane1.setViewportView(toolsTable);
        rentalCB = new JCheckBox();
        rentalCB.setText("Somente emprestadas");
        mainPanel.add(rentalCB, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
