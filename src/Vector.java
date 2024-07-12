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

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}