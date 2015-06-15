package models;

import geometry.Point;
import graph.Edge;
import graph.Vertex;
import java.util.List;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public abstract class Model {

    /**
     * Returns the vertex at the given index.
     * @param index Index of the vertex to be returned.
     */
    public abstract Vertex getVertex(int index);

    /**
     * Returns a list containing all vertexes in the model.
     */
    public abstract List<Vertex> getVertexes();

    /**
     * Returns a list of vertex indexes which are contained within the given bounds.
     * @param startX starting X coordinate of the bounds.
     * @param startY starting Y coordinate of the bounds.
     * @param endX ending X coordinate of the bounds.
     * @param endY ending Y coordinate of the bounds.
     */
    public abstract List<Integer> getVertexes(double startX, double startY, double endX, double endY);

    /**
     * Adds a vertex to the model at the given location.
     * @param location Location at which the vertex will be placed.
     */
    public abstract void addVertex(Point location);

    /**
     * Adds the vertexes to the model at the given locations. This should always be used when multiple vertexes are
     * being added, since the model may be recomputed after each vertex if they are added one at a time.
     * @param points The locations at which the vertexes will be placed.
     */
    public abstract void addVertexes(List<Point> points);

    /**
     * Sets the location of the vertex at the given index to the given location
     * @param index Index of the vertex to be moved
     * @param location Location to which the vertex will be moved.
     */
    public abstract void moveVertex(int index, Point location);

    /**
     * Moves the vertexes at the given indexes by the given amounts.
     * @param selectedVertexes Indexes of the vertexes to be moved.
     * @param x Value added to the X coordinate of the location of each vertex being moved.
     * @param y Value added to the Y coordinate of the location of each vertex being moved.
     */
    public abstract void moveVertexes(List<Integer> selectedVertexes, double x, double y);

    /**
     * Removes the vertex at the given index. This may effect the indexes of other vertexes.
     * @param index Index of the vertex to be removed.
     */
    public abstract void removeVertex(int index);

    /**
     * Removes the vertexes at the given indexes. This should always be used when removing multiple vertexes, since
     * the model may be recomputed after each vertex removal if they are removed one at a time.
     * @param vertexes Indexes of the vertexes to be removed.
     */
    public abstract void removeVertexes(List<Integer> vertexes);

    /**
     * Returns the edge at the given index.
     * @param index Index of the edge to be returned.
     */
    public abstract Edge getEdge(int index);

    /**
     * Returns the model's edges.
     */
    public abstract List<Edge> getEdges();

    /**
     * Removes all vertexes from the model.
     */
    public abstract void clearVertexes();
}
