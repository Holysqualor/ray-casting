public interface Obstacle {
    Intersection intersect(Vector origin, Vector destination);
    boolean belongs(Vector position);
    int getColor(Vector intersection);
}