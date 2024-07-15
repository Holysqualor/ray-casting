import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Display extends JPanel {
    private final Movable camera;

    public Display() {
        camera = Scene.getCamera();
        setDoubleBuffered(Config.DOUBLE_BUFFERING);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long startTime = System.nanoTime();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Config.SCREEN_WIDTH, Config.HALF_HEIGHT);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, Config.HALF_HEIGHT, Config.SCREEN_WIDTH, Config.HALF_HEIGHT);
        Vector position = camera.getPosition(), forward = camera.getForward();
        Vector leftLimit = camera.getRight().multiply(-Config.ASPECT_RATIO).add(forward).normalize();
        Vector rightLimit = camera.getRight().multiply(Config.ASPECT_RATIO).add(forward).normalize();
        List<Layer> canvas = Scene.getCanvas(position, leftLimit, rightLimit, leftLimit.copy().add(position), rightLimit.copy().add(position));
        for(Layer layer : canvas) {
            int x1 = (int) (Config.SCREEN_WIDTH * layer.offset1());
            int x2 = (int) (Config.SCREEN_WIDTH * layer.offset2());
            int k1 = (int) (Config.HALF_HEIGHT / layer.distance1());
            int k2 = (int) (Config.HALF_HEIGHT / layer.distance2());
            int start1 = Math.max(Config.HALF_HEIGHT - k1, 0);
            int end1 = Math.min(Config.HALF_HEIGHT + k1, getHeight());
            int start2 = Math.max(Config.HALF_HEIGHT - k2, 0);
            int end2 = Math.min(Config.HALF_HEIGHT + k2, getHeight());
            g.setColor(layer.obstacle().getColor());
            g.fillPolygon(new Polygon(new int[]{x1, x1, x2, x2}, new int[]{end1, start1, start2, end2}, 4));
        }
        g.setColor(Color.WHITE);
        g.drawString((System.nanoTime() - startTime) / 1000000 + " ms", 10, 20);
    }
}
