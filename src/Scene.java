import java.awt.*;
import java.util.*;
import java.util.List;

public class Scene {
    private final List<Obstacle> obstacles = new ArrayList<>();

    public Scene() {
        obstacles.add(new Wall(new Vector(-5, 5), new Vector(-1, 5), Color.RED.getRGB()));
        obstacles.add(new Wall(new Vector(1, 5), new Vector(5, 5), Color.RED.getRGB()));
        obstacles.add(new Wall(new Vector(5, 5), new Vector(5, -5), Color.GREEN.getRGB()));
        obstacles.add(new Wall(new Vector(5, -5), new Vector(-5, -5), Color.BLUE.getRGB()));
        obstacles.add(new Wall(new Vector(-5, -5), new Vector(-5, 5), Color.MAGENTA.getRGB()));
    }

    public boolean isFree(Vector position) {
        return obstacles.stream().noneMatch(obstacle -> obstacle.belongs(position));
    }

    public Intersection getIntersection(Vector origin, Vector destination) {
        List<Intersection> intersections = obstacles.stream().map(obstacle -> obstacle.intersect(origin, destination)).filter(Objects::nonNull).sorted().toList();
        return intersections.isEmpty() ? null : intersections.get(0);
    }
}
