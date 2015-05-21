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
    public abstract void moveVertex(int index, Point location);
    public abstract void removeVertex(int index);
}
