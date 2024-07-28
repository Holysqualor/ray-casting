import java.awt.*;

public interface Obstacle {
    Layer project(Vector origin, Vector left, Vector right);
    boolean belongs(Vector position);
    Color getColor();
}
