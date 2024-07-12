import java.awt.*;

public class Wall implements Obstacle {
    private final Vector a, b;
    private final double length;
    private final int texture;

    public Wall(Vector a, Vector b, int texture) {
        this.a = a;
        this.b = b;
        this.texture = texture;
        length = a.getDistance(b);
    }

    @Override
    public Intersection intersect(Vector origin, Vector destination) {
        double denominator = (a.getX() - b.getX()) * (origin.getY() - destination.getY()) - (a.getY() - b.getY()) * (origin.getX() - destination.getX());
        if(Math.abs(denominator) < 1e-9) {
            return null;
        }
        double t = ((a.getX() - origin.getX()) * (origin.getY() - destination.getY()) - (a.getY() - origin.getY()) * (origin.getX() - destination.getX())) / denominator;
        double u = -((a.getX() - b.getX()) * (a.getY() - origin.getY()) - (a.getY() - b.getY()) * (a.getX() - origin.getX())) / denominator;
        if(t < 0 || t > 1 || u < 0 || u > 1) {
            return null;
        }
        Vector intersection = new Vector(a.getX() + t * (b.getX() - a.getX()), a.getY() + t * (b.getY() - a.getY()));
        return new Intersection(intersection, origin.getDistance(intersection), this);
    }

    @Override
    public boolean belongs(Vector position) {
        double u = position.getDistance(a);
        double t = position.getDistance(b);
        if(u > length || t > length) {
            return false;
        }
        double s = (length + u + t) / 2;
        return Math.sqrt(s * (s - length) * (s - u) * (s - t)) * 2 / length < 0.5;
    }

    @Override
    public int getColor(Vector intersection) {
        return (a.equals(intersection) || b.equals(intersection)) ? Color.BLACK.getRGB() : texture;
    }
}