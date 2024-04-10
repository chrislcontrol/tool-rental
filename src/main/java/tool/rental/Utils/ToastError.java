package tool.rental.Utils;

import javax.swing.*;

public class ToastError extends Toast {
    public static int DEFAULT_MESSAGE_TYPE = JOptionPane.ERROR_MESSAGE;

    public ToastError(String message, String title, boolean stopRunTime) {
        super(message, title, DEFAULT_MESSAGE_TYPE, stopRunTime);
    }

    public ToastError(String message, String title) {
        super(message, title, DEFAULT_MESSAGE_TYPE, false);
    }

}
