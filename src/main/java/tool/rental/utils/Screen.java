package tool.rental.utils;

import java.awt.*;

public class Screen {
    Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public int WIDTH = (int) SCREEN_SIZE.getWidth();
    public int HEIGHT = (int) SCREEN_SIZE.getHeight();

    public int widthFraction(double fraction) {
        return this.calculateFraction(fraction, this.WIDTH);
    }

    public int heightFraction(double fraction) {
        return this.calculateFraction(fraction, this.HEIGHT);
    }

    private int calculateFraction(double fraction, int size) {
        if (fraction < 0) {
            fraction = 0.01;
        }
        return (int) (size * (fraction / 100));
    }
}
