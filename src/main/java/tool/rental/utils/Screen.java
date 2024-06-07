package tool.rental.utils;

import java.awt.*;

/**
 * Utility class for working with screen dimensions.
 */
public class Screen {
    /**
     * Default screen size obtained from the system.
     */
    private final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Width of the screen.
     */
    public final int WIDTH = (int) SCREEN_SIZE.getWidth();

    /**
     * Height of the screen.
     */
    public final int HEIGHT = (int) SCREEN_SIZE.getHeight();

    /**
     * Calculates the width corresponding to the given fraction of the screen width.
     *
     * @param fraction the fraction of the screen width (should be between 0 and 100)
     * @return the calculated width
     */
    public int widthFraction(double fraction) {
        return calculateFraction(fraction, WIDTH);
    }

    /**
     * Calculates the height corresponding to the given fraction of the screen height.
     *
     * @param fraction the fraction of the screen height (should be between 0 and 100)
     * @return the calculated height
     */
    public int heightFraction(double fraction) {
        return calculateFraction(fraction, HEIGHT);
    }

    /**
     * Calculates the dimension corresponding to the given fraction of the total size.
     *
     * @param fraction the fraction of the total size (should be between 0 and 100)
     * @param size     the total size (either width or height) to calculate the fraction of
     * @return the calculated dimension
     */
    private int calculateFraction(double fraction, int size) {
        if (fraction < 0) {
            fraction = 0.01; // Ensuring minimum fraction
        }
        return (int) (size * (fraction / 100));
    }
}
