import java.awt.*;

public class Wall implements Obstacle {
    private final Vector start, end;
    private final double length;
    private final Color color;

    public Wall(Vector start, Vector end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
        length = start.getDistance(end);
    }

    @Override
    Layer project(Vector origin, Vector left, Vector right) {
	Vector p1 = a.copy().substract(origin).normalize();
	Vector p2 = b.copy().substract(origin).normalize();
	Vector int1 = a, int2 = b;
	if(!p1.isBetween(left, right)) {
		if(p1.getDistance(left) < p1.getDistance(right)) {
			p1 = left;
			int1 = Vector.rayIntLine(origin, left, a, b);
		} else {
			p1 = right;
			int1 = Vector.rayIntLine(origin, right, a, b);
		}
		if(int1 == null) {
			return null;
		}
	}
	if(!p2.isBetween(left, right)) {
		if(p2.getDistance(left) < p2.getDistance(right)) {
			p2 = left;
			int2 = Vector.rayIntLine(origin, left, a, b);
		} else {
			p2 = right;
			int2 = Vector.rayIntLine(origin, right, a, b);
		}
		if(int2 == null) {
			return null;
		}
	}
double dist1 = origin.getDistance(int1);
double dist2 = origin.getDistance(int2);
	return new Layer(left.getDistance(p1), left.getDistance(p2), dist1, dist2, (dist1 + dist2) / 2, this);
}

    @Override
    public boolean belongs(Vector position) {
        double u = position.getDistance(start);
        double t = position.getDistance(end);
        if(u > length || t > length) {
            return false;
        }
        double s = (length + u + t) / 2;
        return Math.sqrt(s * (s - length) * (s - u) * (s - t)) * 2 / length < 1;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
