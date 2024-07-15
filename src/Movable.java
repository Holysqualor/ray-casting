public class Movable {
    private Vector position;
    private final Vector forward = new Vector(0, 1);
    private final Vector right = new Vector(1, 0);
    private boolean movingForward = false, movingBackward = false, movingRight = false, movingLeft = false, running = false;

    public Movable(double x, double y) {
        position = new Vector(x, y);
    }

    public Vector getPosition() {
        return position.copy();
    }

    public Vector getForward() {
        return forward.copy();
    }

    public Vector getRight() {
        return right.copy();
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public void setMovingBackward(boolean movingBackward) {
        this.movingBackward = movingBackward;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void rotate(double angle) {
        forward.rotate(angle).normalize();
        right.rotate(angle).normalize();
    }

    public void update() {
        Vector direction = new Vector();
        if(movingForward) {
            direction.add(forward);
        } else if(movingBackward) {
            direction.subtract(forward);
        }
        if(movingRight) {
            direction.add(right);
        } else if(movingLeft) {
            direction.subtract(right);
        }
        if(!direction.isZero()) {
            Vector newPosition = direction.normalize().multiply(running ? 0.3 : 0.1).add(position);
            if(Scene.isFree(newPosition)) {
                position = newPosition;
            }
        }
    }
}
