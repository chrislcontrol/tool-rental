package tool.rental.App;


import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.Toast;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            runApp();
        }
        catch (Toast exc) {
            exc.display();
            if (exc.getStopRunTime()) {
                throw new Exception("Stop has been called.");
            }
        }
    }

    public static void runApp() throws Toast {
        PresentationFrame firstFrame = Settings.FIRST_FRAME;
        firstFrame.setVisible(true);
    }
}