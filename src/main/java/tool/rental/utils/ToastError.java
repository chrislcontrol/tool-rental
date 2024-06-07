package tool.rental.utils;

import javax.swing.*;

/**
 * Custom exception class representing an error toast message that can be displayed in a GUI.
 */
public class ToastError extends Toast {
    /** Default message type for error toasts. */
    public static int DEFAULT_MESSAGE_TYPE = JOptionPane.ERROR_MESSAGE;

    /**
     * Constructs a ToastError with the specified message, title, and stop runtime flag.
     *
     * @param message     the message content of the toast error
     * @param title       the title of the toast error
     * @param stopRunTime a flag indicating whether the toast error should stop runtime execution
     */
    public ToastError(String message, String title, boolean stopRunTime) {
        super(message, title, DEFAULT_MESSAGE_TYPE, stopRunTime);
    }

    /**
     * Constructs a ToastError with the specified message and title.
     * By default, this constructor sets the stop runtime flag to false.
     *
     * @param message the message content of the toast error
     * @param title   the title of the toast error
     */
    public ToastError(String message, String title) {
        super(message, title, DEFAULT_MESSAGE_TYPE, false);
    }
}