package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.infra.db.DataBase;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LendToolFrame extends PresentationFrame {

    private JPanel mainPanel;
    private JComboBox friendList;
    private JButton rentButton;
    private JButton cancelButton;
    private JLabel toolName;
    private String toolId;
    public LendToolFrame(String toolId) throws ToastError {
        this.toolId = toolId;
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        this.setPointer(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.rentButton,
                this.cancelButton,
                this.friendList
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

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    public ResultSet listFriends() throws ToastError{
        try(DataBase dataBase = new DataBase()){
            String query = """ 
                        SELECT name FROM FRIEND ORDER BY name
                        """;
            PreparedStatement stm = dataBase.connection.prepareStatement(query);
            return stm.executeQuery();

        } catch(SQLException e){
            throw new ToastError(e.getMessage(), "Erro de banco de dados.");
        }
    }
}
