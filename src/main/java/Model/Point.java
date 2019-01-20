package Model;

public class Point {

    private int x;
    private int y;
    private int cubes;
    private Point sourcePoint;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int cubes) {
        this.x = x;
        this.y = y;
        this.cubes = cubes;
    }

    public Point(int x, int y, Point sourcePoint) {
        this.x = x;
        this.y = y;
        this.sourcePoint = sourcePoint;
    }

    public Point(int x, int y, int cubes, Point point) {
        this.x = x;
        this.y = y;
        this.cubes = cubes * (-1);
        this.sourcePoint = point;
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCubes() {
        return cubes;
    }

    public Point getSourcePoint() {
        return sourcePoint;
    }
}
