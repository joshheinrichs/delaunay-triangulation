package models;

import geometry.Point;
import graph.Vertex;
import javafx.scene.Group;
import tools.Tool;

import java.util.ArrayList;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public abstract class Model {

    public abstract void addVertex(Point location);
    public abstract void moveVertex(Vertex vertex, Point location);
    public abstract void moveVertex(Point point, Point location);
    public abstract void removeVertex(Vertex vertex);
    public abstract void removeVertex(Point point);
}
