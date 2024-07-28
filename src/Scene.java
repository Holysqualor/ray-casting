import java.awt.*;
import java.util.*;
import java.util.List;

public class Scene {
    private static final List<Obstacle> obstacles = new ArrayList<>();
    private static final Movable camera = new Movable(0, 0);

    static  {
        obstacles.add(new Wall(new Vector(-5, 5), new Vector(-1, 5), Color.RED));
        obstacles.add(new Wall(new Vector(1, 5), new Vector(5, 5), Color.RED));
        obstacles.add(new Wall(new Vector(5, 5), new Vector(5, -5), Color.GREEN));
        obstacles.add(new Wall(new Vector(5, -5), new Vector(-5, -5), Color.BLUE));
        obstacles.add(new Wall(new Vector(-5, -5), new Vector(-5, 5), Color.MAGENTA));
    }

    public static boolean isFree(Vector position) {
        return obstacles.stream().noneMatch(obstacle -> obstacle.belongs(position));
    }

    public static List<Layer> getCanvas(Vector origin, Vector left, Vector right) {
        return obstacles.stream().map(obstacle -> obstacle.project(origin, left, right)).filter(Objects::nonNull).sorted().toList();
    }

    public static Movable getCamera() {
        return camera;
    }

    public static void update() {
        camera.update();
    }
}
