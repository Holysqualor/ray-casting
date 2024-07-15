import java.awt.*;

public interface Obstacle {
    Layer project(Vector origin, Vector leftLimit, Vector rightLimit, Vector firstPixel, Vector lastPixel);
    boolean belongs(Vector position);
    Color getColor();
}
