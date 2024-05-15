package tool.rental.utils;

import tool.rental.app.Settings;

import javax.swing.*;

public abstract class PresentationFrame extends JFrame {
    final public Screen userScreen = new Screen();

    final public Screen toolScreen = new Screen();

    public void swapFrame(PresentationFrame nextFrame) {
        this.swapFrame(nextFrame, false);
    }

    public void swapFrame(PresentationFrame nextFrame, boolean keepCurrentFrame) {
        Settings.setPreviousFrame(this);
        nextFrame.setVisible(true);
        if (!keepCurrentFrame) {
            this.setVisible(false);
        }
    }
}
