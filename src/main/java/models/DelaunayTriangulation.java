package models;

import geometry.*;
import graph.Edge;
import graph.Vertex;
import graphAdapters.DelaunayTriangulationGraphAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class DelaunayTriangulation extends Model {

    public static final double BOUNDS = 10000000.d;

    ArrayList<Vertex> delaunayVertexes = new ArrayList<Vertex>();
    ArrayList<DelaunayEdge> delaunayEdges = new ArrayList<DelaunayEdge>();

    ArrayList<Vertex> voronoiVertexes = new ArrayList<Vertex>();
    ArrayList<Edge> voronoiEdges = new ArrayList<Edge>();

    ArrayList<DelaunayTriangle> triangles = new ArrayList<DelaunayTriangle>();

    DelaunayTriangulationGraphAdapter graph = new DelaunayTriangulationGraphAdapter(this);

    boolean unique = true;

    Mode mode = Mode.FAST;

    /**
     * Returns the vertex at the given index.
     * @param index
     * @return
     */
    public Vertex getVertex(int index) {
        return delaunayVertexes.get(index);
    }

    @Override
    public void addVertex(Point location) {
        delaunayVertexes.add(new Vertex(location));
        update();
    }

    @Override
    public void addVertexes(ArrayList<Point> points) {
        for (Point point : points) {
            delaunayVertexes.add(new Vertex(point));
        }
        update();
    }

    @Override
    public void moveVertex(int index, Point location) {
        delaunayVertexes.get(index).setPoint(location);
        update();
    }

    @Override
    public void removeVertex(int index) {
        delaunayVertexes.remove(index);
        update();
    }

    @Override
    public void moveVertexes(ArrayList<Integer> indexes, double x, double y) {
        //assert(indexes.size() == locations.size());
        for (int i = 0; i < indexes.size(); i++) {
            Vertex vertex = delaunayVertexes.get(indexes.get(i));
            vertex.setPoint(new Point(vertex.getPoint().x + x, vertex.getPoint().y + y));
        }
        update();
    }

    @Override
    public void removeVertexes(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            delaunayVertexes.set(index, null);
        }
        delaunayVertexes.removeAll(Collections.singleton(null));
        update();
    }

    @Override
    public void clearVertexes() {
        delaunayVertexes.clear();
        update();
    }

    @Override
    public ArrayList<Vertex> getVertexes() {
        return delaunayVertexes;
    }

    @Override
    public ArrayList<Integer> getVertexes(double startX, double startY, double endX, double endY) {
        double minX = Math.min(startX, endX);
        double minY = Math.min(startY, endY);
        double maxX = Math.max(startX, endX);
        double maxY = Math.max(startY, endY);

        ArrayList<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < delaunayVertexes.size(); i++) {
            Point point = delaunayVertexes.get(i).getPoint();
            if(minX <= point.x && point.x <= maxX && minY <= point.y && point.y < maxY) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    @Override
    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>(delaunayEdges);
        return edges;
    }

    /**
     * Returns a list containing all the points at which all Delaunay vertexes are located in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<Point>(delaunayVertexes.size());
        for (Vertex vertex : delaunayVertexes) {
            points.add(vertex.getPoint());
        }
        return points;
    }

    /**
     * Returns a list containing all of the Delaunay vertexes in the Delaunay triangulation .
     * @return
     */
    public ArrayList<Vertex> getDelaunayVertexes() {
        return delaunayVertexes;
    }

    /**
     * Returns a list containing all of the Delaunay edges in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Edge> getDelaunayEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>(delaunayEdges);
        return edges;
    }

    /**
     * Returns a list containing all of the Voronoi vertexes in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Vertex> getVoronoiVertexes() {
        return voronoiVertexes;
    }

    /**
     * Returns a list containing all of the Voronoi edges in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Edge> getVoronoiEdges() {
        return voronoiEdges;
    }

    /**
     * Returns a list containing all of the segments in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Segment> getSegments() {
        ArrayList<Segment> segments = new ArrayList<Segment>(delaunayEdges.size());
        for (Edge edge : delaunayEdges) {
            segments.add(edge.getSegment());
        }
        return segments;
    }

    /**
     * Returns a list containing all of the circumcircles in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Circle> getCircumcircles() {
        ArrayList<Circle> circles = new ArrayList<Circle>(triangles.size());
        for (DelaunayTriangle triangle : triangles) {
            circles.add(triangle.getCircumcircle());
        }
        return circles;
    }

    /**
     * Returns a list containing all of the triangles in the Delaunay triangulation.
     * @return
     */
    public ArrayList<Triangle> getDelaunayTriangles() {
        ArrayList<Triangle> triangles = new ArrayList<Triangle>(this.triangles.size());
        for (DelaunayTriangle triangle : this.triangles) {
            triangles.add(triangle.getTriangle());
        }
        return triangles;
    }

    /**
     * Returns the Delaunay distance between the vertexes at the given indexes.
     * @param a
     * @param b
     * @return
     */
    public double getDelaunayDistance(int a, int b) {
        return graph.getDistance(delaunayVertexes.get(a), delaunayVertexes.get(b));
    }

    /**
     * Returns the indexes of the edges along the shortest path from the vertex at index a, to the vertex at index b.
     * @param a
     * @param b
     * @return
     */
    public ArrayList<Integer> getDelaunayPath(int a, int b) {
        List<Edge> path = graph.getPath(delaunayVertexes.get(a), delaunayVertexes.get(b));
        ArrayList<Integer> indexes = new ArrayList<Integer>();

        for (Edge edge : path) {
            for (int i = 0; i < delaunayEdges.size(); i++) {
                if (edge == delaunayEdges.get(i)) {
                    indexes.add(i);
                }
            }
        }

        return indexes;
    }

    /**
     * Returns the straightline distance between the vertexes at the given indexes.
     * @param a
     * @param b
     * @return
     */
    public double getStraightDistance(int a, int b) {
        return delaunayVertexes.get(a).getPoint().distance(delaunayVertexes.get(b).getPoint());
    }

    /**
     * Returns true if the Delauany triangulation is unique (i.e. no sets of 4 or more cocircular points), false
     * otherwise.
     * @return
     */
    public boolean isUnique() {
        return this.unique;
    }

    /**
     * Computes the Delaunay triangulation and voronoi diagram.
     */
    void update() {
        generate_delaunay_n4();
        generate_voronoi();
        graph.update();
    }

    /**
     * Computes the delaunay triangulation in O(n^4) time.
     */
    void generate_delaunay_n4() {
        delaunayEdges.clear();
        triangles.clear();
        unique = true;

        DelaunayEdge[][] delaunayEdges = new DelaunayEdge[delaunayVertexes.size()][delaunayVertexes.size()];

        if(delaunayVertexes.size() >= 3) {

            for (int i = 0; i < delaunayVertexes.size(); i++) {
                for (int j = i+1; j < delaunayVertexes.size(); j++) {
                    for (int k = j+1; k < delaunayVertexes.size(); k++) {
                        Circle circle = new Circle(delaunayVertexes.get(i).getPoint(), delaunayVertexes.get(j).getPoint(), delaunayVertexes.get(k).getPoint());
                        boolean intersected = false;
                        for (int l = 0; l < delaunayVertexes.size(); l++) {
                            if (circle.contains(delaunayVertexes.get(l).getPoint(), false)) {
                                intersected = true;
                                break;
                            } else if (circle.contains(delaunayVertexes.get(l).getPoint(), true)
                                    && l != i && l != j && l != k) {
                                unique = false;
                            }
                        }
                        if (!intersected) {
                            if(delaunayEdges[i][j] == null) {
                                delaunayEdges[i][j] = new DelaunayEdge(delaunayVertexes.get(i), delaunayVertexes.get(j));
                                this.delaunayEdges.add(delaunayEdges[i][j]);
                            }
                            if(delaunayEdges[j][k] == null) {
                                delaunayEdges[j][k] = new DelaunayEdge(delaunayVertexes.get(j), delaunayVertexes.get(k));
                                this.delaunayEdges.add(delaunayEdges[j][k]);
                            }
                            if(delaunayEdges[i][k] == null) {
                                delaunayEdges[i][k] = new DelaunayEdge(delaunayVertexes.get(i), delaunayVertexes.get(k));
                                this.delaunayEdges.add(delaunayEdges[i][k]);
                            }
                            DelaunayTriangle triangle = new DelaunayTriangle(
                                    delaunayEdges[i][j],
                                    delaunayEdges[j][k],
                                    delaunayEdges[i][k]);

                            triangles.add(triangle);
                        }
                    }
                }
            }

            if (mode == Mode.FAST) {
                if (!this.isUnique()) {
                    //checks for intersecting edges in the case of > 3 cocircular vertexes.
                    for (int i = 0; i < this.delaunayEdges.size(); i++) {
                        DelaunayEdge edge1 = this.delaunayEdges.get(i);
                        for (int j = i + 1; j < this.delaunayEdges.size(); j++) {
                            DelaunayEdge edge2 = this.delaunayEdges.get(j);
                            if (edge1.getSegment().intersects(edge2.getSegment())) {
                                this.delaunayEdges.remove(edge2);
                                ArrayList<DelaunayTriangle> triangles = new ArrayList<DelaunayTriangle>(edge2.getTriangles());
                                for (DelaunayTriangle triangle : triangles) {
                                    triangle.delete();
                                    this.triangles.remove(triangle);
                                }
                            }
                        }
                    }
                }
            }
//            } else if (mode == Mode.MAX_DISTANCE_RATIO) {
//                //generate all triangle set combinations (O(2^(n^4))
//                ArrayList<DelaunayTriangulation> dtList; //TODO
//
//                //discard triangle sets in which edges intersect (O(n^2) each)
//                for (DelaunayTriangulation dt : dtList) {
//                    for (DelaunayEdge edge1 : dt.delaunayEdges) {
//                        for (DelaunayEdge edge2 : dt.delaunayEdges) {
//                            if (edge1.getSegment().intersects(edge2.getSegment())) {
//                                //TODO delete triangulation
//                            }
//                        }
//                    }
//                }
//
//                //discard triangle sets in which edges are missing (O(n^2) each)
//                for (DelaunayTriangulation dt : dtList) {
//                    for (Vertex vertex1 : dt.delaunayVertexes) {
//                        for (Vertex vertex2 : dt.delaunayVertexes) {
//                            Circle circumcircle = new Circle(vertex1.getPoint(), vertex2.getPoint());
//                            boolean intersected = false;
//                            for (Vertex vertex3 : dt.delaunayVertexes) {
//                                if (circumcircle.contains(vertex3.getPoint(), false)) {
//                                    intersected = true;
//                                    break;
//                                }
//                            }
//                            if (!intersected) {
//                                if (!vertex1.isConnected(vertex2)) {
//                                    //TODO delete triangulation
//                                }
//                            }
//                        }
//                    }
//                }
//
//                //compute max ratio from remaining triangulations
//                double maxRatio = 0.d;
//                DelaunayTriangulation maxTriangulation = null;
//                for (DelaunayTriangulation dt : dtList) {
//                    DelaunayTriangulationGraphAdapter graph = new DelaunayTriangulationGraphAdapter(dt);
//                    for (int i = 0; i < dt.delaunayVertexes.size(); i++) {
//                        for (int j = i+1; j < dt.delaunayVertexes.size(); j++) {
//                            double delaunayDistance = graph.getDistance(dt.getVertex(i), dt.getVertex(j));
//                            double ratio = dt.getVertex(i).getPoint().distance(dt.getVertex(j).getPoint()) / delaunayDistance;
//                            if (ratio > maxRatio) {
//                                maxRatio = ratio;
//                                maxTriangulation = dt;
//                            }
//                        }
//                    }
//                }
//
//                this.delaunayVertexes = maxTriangulation.delaunayVertexes;
//                this.delaunayEdges = maxTriangulation.delaunayEdges;
//                this.triangles = maxTriangulation.triangles;
//            }

        }
    }

    /**
     * Generates the Voronoi diagram from the Delaunay triangulation, and computes alpha stability values for Delaunay
     * edges.
     */
    void generate_voronoi() {
        voronoiEdges.clear();
        voronoiVertexes.clear();

        for (DelaunayEdge edge : delaunayEdges) {

            //assert(triangles.size() > 0);
            if(edge.isHull()) {

                DelaunayTriangle dt = edge.getTriangles().get(0);
                Triangle triangle = dt.getTriangle();
                Point start = triangle.getCircumcircle().center;
                if(Math.abs(start.x) < BOUNDS && Math.abs(start.y) < BOUNDS) {

                    Point p1, p2;
                    if (dt.a == edge.from || dt.a == edge.to) {
                        if (dt.b == edge.from || dt.b == edge.to) {
                            p1 = dt.a.getPoint();
                            p2 = dt.b.getPoint();
                        } else {
                            assert (dt.c == edge.from || dt.c == edge.to);
                            p1 = dt.c.getPoint();
                            p2 = dt.a.getPoint();
                        }
                    } else {
                        p1 = dt.b.getPoint();
                        p2 = dt.c.getPoint();
                    }

                    Segment segment = new Segment(start, edge.getSegment().midpoint());
                    Line line = segment.getLine();

                    double x, y;

                    boolean clockwise = triangle.area() > 0;

                    //clockwise ordering
                    if (clockwise) {
                        if (p2.x > p1.x) { y = -BOUNDS; }
                        else { y = BOUNDS; }
                    }
                    //counter-clockwise ordering
                    else {
                        if (p2.x > p1.x) { y = BOUNDS; }
                        else { y = -BOUNDS; }
                    }

                    x = line.x(y);

                    if (Double.isNaN(x)) {
                        if(clockwise) {
                            if (p2.y > p1.y) { x = BOUNDS; }
                            else { x = -BOUNDS; }
                        } else {
                            if (p2.y > p1.y) { x = -BOUNDS; }
                            else { x = BOUNDS; }
                        }
                        y = line.y(x);
                    } else
                    if (Math.abs(x) > BOUNDS) {
                        x = BOUNDS * Math.abs(x) / x;
                        y = line.y(x);
                    }

                    Vertex a = new Vertex(start);
                    Vertex b = new Vertex(new Point(x, y));

                    voronoiVertexes.add(a);
                    voronoiVertexes.add(b);
                    voronoiEdges.add(new Edge(a, b));
                }
            } else {
                ArrayList<DelaunayTriangle> triangles = edge.getTriangles();

                Vertex a = new Vertex(triangles.get(0).getCircumcircle().center);
                Vertex b = new Vertex(triangles.get(1).getCircumcircle().center);

                edge.setAlphaStable(
                        Math.min(
                            Math.toRadians(Angle.getAngle(a.getPoint(), edge.getSegment().start, b.getPoint())),
                                Math.toRadians(Angle.getAngle(a.getPoint(), edge.getSegment().start, b.getPoint()))));

                voronoiVertexes.add(a);
                voronoiVertexes.add(b);
                voronoiEdges.add(new Edge(a, b));
            }
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        update();
    }

    public Mode getMode() {
        return this.mode;
    }

    /**
     * A specific formation used in the construction of the Delaunay triangulation
     */
    class DelaunayTriangle {

        final Vertex a, b, c;
        final DelaunayEdge ab, bc, ca;

        /**
         * Constructs a new DelaunayTriangle from the given delaunayEdges. Edge order or direction does not matter.
         * @param ab
         * @param bc
         * @param ca
         */
        DelaunayTriangle(DelaunayEdge ab, DelaunayEdge bc, DelaunayEdge ca) {
            this.ab = ab;
            this.bc = bc;
            this.ca = ca;

            this.ab.addTriangle(this);
            this.bc.addTriangle(this);
            this.ca.addTriangle(this);

            this.a = ab.from;
            this.b = ab.to;
            if(this.a != ca.from && this.b != ca.from) {
                this.c = ca.from;
            } else {
                this.c = ca.to;
            }
        }

        /**
         * Returns all adjacent {@link DelaunayTriangle LinkedTriangles}
         * @return
         */
        ArrayList<DelaunayTriangle> getAdjacentTriangles() {
            ArrayList<DelaunayTriangle> triangles = new ArrayList<DelaunayTriangle>(3);

            for (DelaunayTriangle triangle : ab.getTriangles()) {
                if(triangle != this) {
                    triangles.add(triangle);
                }
            }
            for (DelaunayTriangle triangle : bc.getTriangles()) {
                if(triangle != this) {
                    triangles.add(triangle);
                }
            }
            for (DelaunayTriangle triangle : ca.getTriangles()) {
                if(triangle != this) {
                    triangles.add(triangle);
                }
            }

            return triangles;
        }

        /**
         * Returns true if the triangle has a hull edge, false otherwise
         * @return
         */
        boolean isHull() {
            return ab.isHull() || bc.isHull() || ca.isHull();
        }

        /**
         * Returns true if the given vertex lies  within this {@link DelaunayTriangle}.
         * @param vertex
         * @param inclusive
         * @return
         */
        boolean contains(Vertex vertex, boolean inclusive) {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint()).contains(vertex.getPoint(), inclusive);
        }

        /**
         * Returns this {@link DelaunayTriangle}'s equivelant {@link Triangle}.
         * @return
         */
        Triangle getTriangle() {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint());
        }

        /**
         * Returns a circumcircle which passes through this triangles vertexes.
         * @return
         */
        Circle getCircumcircle() {
            return getTriangle().getCircumcircle();
        }

        /**
         * Deletes this triangle, removing its references from its {@link DelaunayEdge DelaunayEdges}.
         */
        void delete() {
            ab.removeTriangle(this);
            bc.removeTriangle(this);
            ca.removeTriangle(this);
        }

        @Override
        public String toString() {
            return a + ", " + b + ", " + c;
        }
    }

    /**
     * A Delaunay edge stores information about its associated triangles and alpha stability.
     */
    public class DelaunayEdge extends Edge {

        ArrayList<DelaunayTriangle> triangles = new ArrayList<DelaunayTriangle>(2);
        double alphaStability = Double.NaN;

        DelaunayEdge(Vertex a, Vertex b) {
            super(a, b);
        }

        ArrayList<DelaunayTriangle> getTriangles() {
            return triangles;
        }

        /**
         * Adds a triangle to this {@link DelaunayEdge}'s list. This edge should be an edge of the given triangle.
         * @param triangle
         */
        void addTriangle(DelaunayTriangle triangle) {
            //assert(triangles.size() < 2);
            triangles.add(triangle);
        }

        /**
         * Removes the given triangle from the edge's {@link DelaunayTriangle} list.
         * @param triangle
         */
        void removeTriangle(DelaunayTriangle triangle) {
            assert(triangles.contains(triangle));
            triangles.remove(triangle);
        }

        /**
         * Returns true if this is a hull edge, i.e. only has one corresponding triangle.
         */
        public boolean isHull() {
            return triangles.size() == 1;
        }

        /**
         * Returns the alpha stability of this edge.
         * @see <a href="http://arxiv.org/abs/1504.06851">Stable Delaunay Graphs</a>
         */
        public double getAlphaStability() {
            return alphaStability;
        }

        /**
         * Sets the alpha stability of this edge to the given value.
         * @param alphaStability
         * @see <a href="http://arxiv.org/abs/1504.06851">Stable Delaunay Graphs</a>
         */
        public void setAlphaStable(double alphaStability) {
            this.alphaStability = alphaStability;
        }

    }

    public enum Mode {
        FAST("Fast"),
        MAX_DISTANCE_RATIO("Maximize Distance Ratio");

        private final String text;

        Mode(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}