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

    /*@Override
    public Layer intersects(Vector origin, Vector left, Vector right) {
        Vector a = Vector.lineIntersect(start, end, origin, left);
        Vector b = Vector.lineIntersect(start, end, origin, right);
        Vector c = Vector.lineIntersect(start, end, left, right);
        boolean startBelongsToTriangle = start.belongsToTriangle(origin, left, right);
        boolean endBelongsToTriangle = end.belongsToTriangle(origin, left, right);
        if(c == null) {
            if(a != null && b == null) {
                b = endBelongsToTriangle ? end.copy() : start.copy();
            } else if(a == null && b != null) {
                a = startBelongsToTriangle ? start.copy() : end.copy();
            } else if(startBelongsToTriangle && endBelongsToTriangle) {
                a = start.copy();
                b = end.copy();
            }
        } else {
            if(a == null && b == null) {
                a = startBelongsToTriangle ? start.copy() : end.copy();
                b = c;
            } else if(a == null) {
                a = c;
            } else if(b == null) {
                b = c;
            }
        }
        if(a == null || b == null) {
            return null;
        }
        return new Layer(a, b, origin.getDistance(a.copy().add(b).multiply(0.5)), this);
    }*/

    @Override
    public Layer project(Vector origin, Vector leftLimit, Vector rightLimit, Vector firstPixel, Vector lastPixel) {
        Vector offset1 = Vector.lineIntersect(start, origin, firstPixel, lastPixel);
        Vector offset2 = Vector.lineIntersect(end, origin, firstPixel, lastPixel);
        Vector a = start, b = end;
        if(offset1 == null || offset2 == null) {
            Vector c = Vector.rayIntLine(origin, leftLimit, a, b);
            Vector d = Vector.rayIntLine(origin, rightLimit, a, b);
            if(offset1 == null) {
                if(c != null) {
                    offset1 = firstPixel;
                    a = c;
                } else if(d != null) {
                    offset1 = lastPixel;
                    a = d;
                } else {
                    return null;
                }
            }
            if(offset2 == null) {
                if(d != null) {
                    offset2 = lastPixel;
                    b = d;
                } else if(c != null) {
                    offset2 = firstPixel;
                    b = c;
                } else {
                    return null;
                }
            }
        }
        double distance1 = origin.getDistance(a), distance2 = origin.getDistance(b);
        double overview = firstPixel.getDistance(lastPixel);
        return new Layer(firstPixel.getDistance(offset1) / overview, firstPixel.getDistance(offset2) / overview, distance1, distance2, (distance1 + distance2) / 2, this);
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
