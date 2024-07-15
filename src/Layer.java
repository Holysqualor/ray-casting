public record Layer(double offset1, double offset2, double distance1, double distance2, double distance, Obstacle obstacle) implements Comparable<Layer> {
    @Override
    public int compareTo(Layer o) {
        return Double.compare(distance, o.distance) * -1;
    }
}
