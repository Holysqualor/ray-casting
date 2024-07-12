public record Intersection(Vector vector, double distance, Obstacle obstacle) implements Comparable<Intersection> {

    @Override
    public int compareTo(Intersection o) {
        return Double.compare(this.distance, o.distance);
    }
}
