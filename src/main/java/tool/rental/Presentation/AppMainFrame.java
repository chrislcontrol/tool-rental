package tool.rental.Presentation;

import tool.rental.App.Settings;
import tool.rental.Utils.PresentationFrame;

import javax.swing.*;

public class AppMainFrame extends PresentationFrame {
    private JPanel MainPanel;

    public AppMainFrame() {
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
    }

    private void setupPageLayout() {
        this.setTitle(String.format("Tool Rental (%s)", Settings.USER.getUsername()));
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
