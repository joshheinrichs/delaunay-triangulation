package graph;

import geometry.Segment;
import javafx.scene.shape.Line;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Edge extends Line {

    Vertex from;
    Vertex to;

    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;

        this.from.addOutgoingEdge(this);
        this.to.addIncomingEdge(this);
    }

    public Segment getSegment() {
        return new Segment(from.getPoint(), to.getPoint());
    }
}
