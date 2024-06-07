package tool.rental.presentation;

import tool.rental.domain.controllers.RegisterToolController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterToolFrame extends PresentationFrame {

    private final RegisterToolController registerToolController = new RegisterToolController(this);

    private JTextField costField;
    private JTextField brandField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel MainPanel;
    private JTextField nameField;


    public RegisterToolFrame(Runnable successCallback) {
        this.setMainPanel();
        this.setupPageLayout();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String costText = costField.getText().replace(',', '.');
                    registerToolController.registerTool(
                            brandField.getText(),
                            nameField.getText(),
                            Double.parseDouble(costText),
                            successCallback

                    );
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerToolController.closeFrame();
            }
        });
    }

    private void setupPageLayout() {
        this.setTitle("Registrar ferramenta");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

