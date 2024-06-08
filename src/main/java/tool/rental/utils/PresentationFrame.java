package tool.rental.utils;

import tool.rental.app.Settings;

import javax.swing.*;

public abstract class PresentationFrame extends JFrame {
    final public Screen userScreen = new Screen();

    public PresentationFrame() {
        super();
        ImageIcon img = new ImageIcon("src/main/java/tool/rental/app/assets/imgs/icon.jpeg");
        setIconImage(img.getImage());
    }

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
