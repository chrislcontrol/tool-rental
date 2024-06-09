package tool.rental.utils;

import javax.swing.*;

/**
 * Utility class for creating JOptionPane dialogs with predefined options.
 */
public class JOptionPaneUtils {

    // Predefined options for Yes/No dialog
    public static String[] YES_OR_NO = {"Sim", "Não"};

    // Predefined options for Not/Yes dialog
    public static String[] NOT_OR_YES = {"Não", "Sim"};

    /**
     * Enum representing the index of options in the YES_OR_NO array.
     */
    public enum YES_OR_NO_INDEX {
        YES,
        NO
    }

    /**
     * Displays a Yes/No dialog with the specified message and title.
     *
     * @param message the message to display in the dialog
     * @param title   the title of the dialog window
     * @return an integer representing the option chosen by the user (YES or NO)
     */
    public static int showInputYesOrNoDialog(String message, String title) {
        return showInputYesOrNoDialog(message, title, YES_OR_NO_INDEX.YES);
    }

    /**
     * Displays a Yes/No dialog with the specified message, title, and default option.
     *
     * @param message      the message to display in the dialog
     * @param title        the title of the dialog window
     * @param defaultIndex the default option to be selected (YES or NO)
     * @return an integer representing the option chosen by the user (YES or NO)
     */
    public static int showInputYesOrNoDialog(String message, String title, YES_OR_NO_INDEX defaultIndex) {
        return JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                YES_OR_NO,
                YES_OR_NO[defaultIndex.ordinal()]
        );
    }
}
