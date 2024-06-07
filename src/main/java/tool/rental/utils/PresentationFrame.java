package tool.rental.utils;

import tool.rental.app.Settings;

import javax.swing.JFrame;

/**
 * An abstract class that extends JFrame and provides a basic implementation for a presentation frame in a tool rental application.
 */
public abstract class PresentationFrame extends JFrame {
    /**
     * A public final field that holds an instance of the Screen class, which represents the user's screen.
     */
    final public Screen userScreen = new Screen();

    /**
     * Swaps the current frame with a new one.
     *
     * @param nextFrame the new frame to swap with
     */
    public void swapFrame(PresentationFrame nextFrame) {
        this.swapFrame(nextFrame, false);
    }

    /**
     * Swaps the current frame with a new one.
     *
     * @param nextFrame the new frame to swap with
     * @param keepCurrentFrame whether to keep the current frame visible
     */
    public void swapFrame(PresentationFrame nextFrame, boolean keepCurrentFrame) {
        Settings.setPreviousFrame(this);
        nextFrame.setVisible(true);
        if (!keepCurrentFrame) {
            this.setVisible(false);
        }
    }
}