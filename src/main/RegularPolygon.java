package main;

public class RegularPolygon {
    Point centerPoint;
    Point rangePoint;

    public RegularPolygon(int x, int y) {
        this.centerPoint = new Point(x, y);
    }
    public RegularPolygon(int x, int y, int x1, int y1){
        this.centerPoint = new Point(x, y);
        this.rangePoint = new Point(x1, y1);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }

    public Point getRangePoint() {
        return rangePoint;
    }

    public void setRangePoint(Point rangePoint) {
        this.rangePoint = rangePoint;
    }

}
