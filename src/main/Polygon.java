package main;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<int[]> pointsList;

    public List<int[]> getPointsList() {
        return pointsList;
    }

    public Polygon() {
        this.pointsList = new ArrayList<>();
    }
}
