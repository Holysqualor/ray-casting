import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Display extends JPanel {
    private final Movable camera;
    private final BufferedImage buffer;
    private final int[] pixels;
    private final double[] xValues;

    public Display(Movable camera) {
        this.camera = camera;
        buffer = new BufferedImage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        xValues = new double[Config.SCREEN_WIDTH];
        for(int i = 0; i < Config.SCREEN_WIDTH; i++) {
            xValues[i] = ((2.0 * i / Config.SCREEN_WIDTH) - 1.0) * Config.ASPECT_RATIO;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long startTime = System.nanoTime();
        Arrays.fill(pixels, 0, Config.SCREEN_SIZE / 2, Color.GRAY.getRGB());
        Arrays.fill(pixels, Config.SCREEN_SIZE / 2, Config.SCREEN_SIZE - 1, Color.DARK_GRAY.getRGB());
        Vector position = camera.getPosition(), forward = camera.getForward();
        IntStream.range(0, Config.SCREEN_WIDTH).forEach(i -> {
            Intersection intersection = camera.getScene().getIntersection(position, camera.getRight().multiply(xValues[i]).add(forward).normalize().multiply(Config.DRAW_DISTANCE).add(position));
            if(intersection != null) {
                int k = (int) (Config.HALF_HEIGHT / intersection.distance());
                int start = Math.max(Config.HALF_HEIGHT - k, 0);
                int end = Math.min(Config.HALF_HEIGHT + k, getHeight());
                int frame = (end - start) * 3 / 100;
                for(int j = start; j < start + frame; j++) {
                    pixels[j * getWidth() + i] = 0;
                }
                for(int j = start + frame; j < end - frame; j++) {
                    pixels[j * getWidth() + i] = intersection.obstacle().getColor(intersection.vector());
                }
                for(int j = end - frame; j < end; j++) {
                    pixels[j * getWidth() + i] = 0;
                }
            }
        });
        g.drawImage(buffer, 0, 0, this);
        g.setColor(Color.WHITE);
        g.drawString((System.nanoTime() - startTime) / 1000000 + " ms", 10, 20);
    }
}
