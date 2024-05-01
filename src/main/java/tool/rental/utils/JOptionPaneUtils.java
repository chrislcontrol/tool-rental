package tool.rental.utils;

import javax.swing.*;

public class JOptionPaneUtils {
    public static String[] YES_OR_NO = {"Sim", "Não"};
    public static String[] NOT_OR_YES = {"Não", "Sim"};

    public enum YES_OR_NO_INDEX {
        YES,
        NO
    }


    public static int showInputYesOrNoDialog(String message, String title) {
        return showInputYesOrNoDialog(message, title, YES_OR_NO_INDEX.YES);
    }


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
