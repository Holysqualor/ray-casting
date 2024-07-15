import java.util.Objects;

public class Vector {
    private double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector [x=" + x + ", y=" + y + "]";
    }

    public Vector add(Vector v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector subtract(Vector v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector multiply(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    public double getDistance(Vector v) {
        return Math.sqrt((x - v.x) * (x - v.x) + (y - v.y) * (y - v.y));
    }

    public Vector copy() {
        return new Vector(x, y);
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Vector normalize() {
        double length = Math.sqrt(x * x + y * y);
        x /= length;
        y /= length;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector v) {
            return Math.abs(x - v.x) < 0.03 && Math.abs(y - v.y) < 0.03;
        }
        return false;
    }

    public Vector rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;
        x = newX;
        y = newY;
        return this;
    }

    public static Vector lineIntersect(Vector a, Vector b, Vector c, Vector d) {
        double denominator = (a.x - b.x) * (c.y - d.y) - (a.y - b.y) * (c.x - d.x);
        if(Math.abs(denominator) < 1e-9) {
            return null;
        }
        double t = ((a.x - c.x) * (c.y - d.y) - (a.y - c.y) * (c.x - d.x)) / denominator;
        double u = -((a.x - b.x) * (a.y - c.y) - (a.y - b.y) * (a.x - c.x)) / denominator;
        if(t < 0 || t > 1 || u < 0 || u > 1) {
            return null;
        }
        return new Vector(a.x + t * (b.x - a.x), a.y + t * (b.y - a.y));
    }

    public static Vector rayIntLine(Vector origin, Vector direction, Vector start, Vector end) {
        Vector v1 = origin.copy().subtract(start);
        Vector v2 = end.copy().subtract(start);
        Vector v3 = new Vector(-direction.getY(), direction.getX());
        double dotProduct = v2.dot(v3);
        if(Math.abs(dotProduct) < 1e-9) {
            return null;
        }
        double t1 = v2.cross(v1) / dotProduct;
        double t2 = v1.dot(v3) / dotProduct;
        if(t1 < 0 || t2 < 0 || t2 > 1) {
            return null;
        }
        return direction.copy().multiply(t1).add(origin);
    }

    public double cross(Vector v) {
        return x * v.y - y * v.x;
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
