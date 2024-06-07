package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateToolController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateToolFrame extends PresentationFrame {

    private final UpdateToolController updateToolController = new UpdateToolController(this);

    private JPanel MainPanel;
    private JTextField brandField;
    private JTextField nameField;
    private JTextField costField;
    private JButton confirmButton;
    private JButton cancelButton;
    private final String toolId;
    private final Runnable successCallback;

    public UpdateToolFrame(String toolId, Runnable successCallback) throws ToastError {
        this.toolId = toolId;
        this.successCallback = successCallback;
        this.setMainPanel();
        this.setupPageLayout();
        this.setupListeners();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.cancelButton,
                this.confirmButton
        );
    }

    protected void setupListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (costField.getText() == null || costField.getText().isEmpty()) {
                        throw new ToastError("Preço não pode ser nulo.", "Campo não pode ser nulo");
                    }
                    String costText = costField.getText().replace(",", ".");
                    updateToolController.updateTool(
                            toolId,
                            brandField.getText(),
                            nameField.getText(),
                            Double.parseDouble(costText),
                            successCallback
                    );

                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateToolController.closeFrame();
            }
        });
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    private void setupPageLayout() {
        this.setTitle("Atualizar ferramenta");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

}
