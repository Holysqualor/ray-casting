import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {
    private final Movable camera = new Movable(0, 0, new Scene());
    private Robot robot;

    public Game() {
        super("Doom");
        setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
        Display display = new Display(camera);
        add(display);
        Timer timer = new Timer( 0, e -> {
            display.repaint();
            camera.update();
        });
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.createImage("");
        Cursor cursor = toolkit.createCustomCursor(image, new Point(0, 0), "blankCursor");
        setCursor(cursor);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.exit(1);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                timer.start();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keyPressedHandle(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                keyReleasedHandle(e);
            }
        });
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseClicked(e);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMoved(e);
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void keyPressedHandle(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                camera.setMovingForward(true);
                break;
            case KeyEvent.VK_S:
                camera.setMovingBackward(true);
                break;
            case KeyEvent.VK_A:
                camera.setMovingLeft(true);
                break;
            case KeyEvent.VK_D:
                camera.setMovingRight(true);
                break;
            case KeyEvent.VK_SHIFT:
                camera.setRunning(true);
                break;
            case KeyEvent.VK_ESCAPE:
                SwingUtilities.invokeLater(() -> {
                    dispose();
                    System.exit(0);
                });
                break;
        }
    }

    public void keyReleasedHandle(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                camera.setMovingForward(false);
                break;
            case KeyEvent.VK_S:
                camera.setMovingBackward(false);
                break;
            case KeyEvent.VK_A:
                camera.setMovingLeft(false);
                break;
            case KeyEvent.VK_D:
                camera.setMovingRight(false);
                break;
            case KeyEvent.VK_SHIFT:
                camera.setRunning(false);
                break;
        }
    }

    private void handleMouseClicked(MouseEvent e) {
    }


    private void handleMouseMoved(MouseEvent e) {
        int deltaX = e.getX() - Config.HALF_HEIGHT;
        if(deltaX != 0) {
            camera.rotate(-Math.toRadians((double) Config.MOUSE_SENSITIVITY * deltaX / Config.HALF_HEIGHT * Config.ASPECT_RATIO));
            robot.mouseMove(getX() + Config.HALF_HEIGHT, getY());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
