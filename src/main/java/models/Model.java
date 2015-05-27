package models;

import geometry.Point;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public abstract class Model {

    public abstract void addVertex(Point location);
    public abstract void moveVertex(int index, Point location);
    public abstract void removeVertex(int index);
}
