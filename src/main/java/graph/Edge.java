package graph;

import geometry.Segment;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Edge {

    public final Vertex from, to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;

        this.from.addEdge(this);
        this.to.addEdge(this);
    }

    /**
     * Deletes the edge, removing itself from its vertexes
     */
    public void delete() {
        this.from.removeEdge(this);
        this.to.removeEdge(this);
    }

    /**
     * Returns the corresponding segment
     * @return
     */
    public Segment getSegment() {
        return new Segment(from.getPoint(), to.getPoint());
    }

    @Override
    public String toString() {
        return "(" + from + ", " + to + ")";
    }
}
