package boardifier.model;

public class Coord2D {
    private double x;
    private double y;

    public Coord2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coord2D() {
        this(0.0, 0.0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Coord2D add (double x, double y) {
        return new Coord2D(this.x+x, this.y+y);
    }

    public Coord2D add (Coord2D other) {
        return add(other.getX(), other.getY());
    }

    public Coord2D multiply (double scale) {
        return new Coord2D(this.x * scale, this.y * scale);
    }

    public Coord2D add (Coord2D other, Coord2D scale) {
        return add(other.getX() * scale.getX(), other.getY() * scale.getY());
    }

    public Coord2D subtract (double x, double y) {
        return new Coord2D(this.x-x, this.y-y);
    }

    @Override
    public String toString() {
        return "Coord2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
