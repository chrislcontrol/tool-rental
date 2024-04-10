package tool.rental.Utils;

import javax.swing.*;

public abstract class Toast extends Exception {
    public String message;
    public String title;
    public int messageType;
    public boolean stopRuntime;

    public Toast(String message, String title, int messageType, boolean stopRuntime) {
        super();
        this.title = title;
        this.message = message;
        this.messageType = messageType;
        this.stopRuntime = stopRuntime;
    }


    public void display() {
        JOptionPane.showMessageDialog(null, this.message, this.title, this.messageType);
    }

    public boolean getStopRunTime() {
        return this.stopRuntime;
    }
}
