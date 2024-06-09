package tool.rental.utils;

import javax.swing.*;

/**
 * Custom exception class representing a toast message that can be displayed in a GUI.
 */
public abstract class Toast extends Exception {
    /** The message content of the toast. */
    public String message;
    /** The title of the toast. */
    public String title;
    /** The message type of the toast. */
    public int messageType;
    /** A flag indicating whether the toast should stop runtime execution. */
    public boolean stopRuntime;

    /**
     * Constructs a Toast with the specified parameters.
     *
     * @param message      the message content of the toast
     * @param title        the title of the toast
     * @param messageType the message type of the toast
     * @param stopRuntime  a flag indicating whether the toast should stop runtime execution
     */
    public Toast(String message, String title, int messageType, boolean stopRuntime) {
        super();
        this.title = title;
        this.message = message;
        this.messageType = messageType;
        this.stopRuntime = stopRuntime;
    }

    /**
     * Displays the toast message using a JOptionPane.
     */
    public void display() {
        JOptionPane.showMessageDialog(null, this.message, this.title, this.messageType);
    }

    /**
     * Retrieves the value of the stopRuntime flag.
     *
     * @return true if the toast should stop runtime execution, false otherwise
     */
    public boolean getStopRunTime() {
        return this.stopRuntime;
    }
}
