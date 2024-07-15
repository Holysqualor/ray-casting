import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static final int SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT;
    public static final int SCREEN_SIZE;
    public static final int HALF_WIDTH;
    public static final int HALF_HEIGHT;
    public static final double ASPECT_RATIO;
    public static boolean DOUBLE_BUFFERING;
    public static int DRAW_DISTANCE;
    public static int MOUSE_SENSITIVITY;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = screenSize.width;
        SCREEN_HEIGHT = screenSize.height;
        HALF_WIDTH = SCREEN_WIDTH / 2;
        HALF_HEIGHT = SCREEN_HEIGHT / 2;
        ASPECT_RATIO = (double) SCREEN_WIDTH / screenSize.width;
        SCREEN_SIZE = SCREEN_WIDTH * SCREEN_HEIGHT;
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(fis);
            DOUBLE_BUFFERING = Boolean.parseBoolean(prop.getProperty("double_buffering"));
            DRAW_DISTANCE = Integer.parseInt(prop.getProperty("draw_distance"));
            MOUSE_SENSITIVITY = Integer.parseInt(prop.getProperty("mouse_sensitivity"));
            fis.close();
        } catch(IOException e) {
            System.exit(1);
        }
    }
}
