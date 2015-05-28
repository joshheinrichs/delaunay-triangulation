package models;

import geometry.*;
import graph.Edge;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;

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

    public Vertex getVertex(int index) {
        return delaunayVertexes.get(index);
    }

    public void addVertex(Point location) {
        delaunayVertexes.add(new Vertex(location));
        generate_delaunay();
    }

    public void moveVertex(int index, Point location) {
        delaunayVertexes.get(index).setPoint(location);
        generate_delaunay();
    }

    public void removeVertex(int index) {
        delaunayVertexes.remove(index);
        generate_delaunay();
    }

    public void moveVertexes(ArrayList<Integer> indexes, double x, double y) {
        //assert(indexes.size() == locations.size());
        for (int i = 0; i < indexes.size(); i++) {
            Vertex vertex = delaunayVertexes.get(indexes.get(i));
            vertex.setPoint(new Point(vertex.getPoint().x + x, vertex.getPoint().y + y));
        }
        generate_delaunay();
    }

    public void removeVertexes(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            delaunayVertexes.set(index, null);
        }
        delaunayVertexes.removeAll(Collections.singleton(null));
        generate_delaunay();
    }

    public void clearVertexes() {
        delaunayVertexes.clear();
        generate_delaunay();
    }

    @Override
    public ArrayList<Vertex> getVertexes() {
        return delaunayVertexes;
    }

    /**
     * Returns a list of vertex indexes in ascending order which reside within the given box
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return
     */
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

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<Point>(delaunayVertexes.size());
        for (Vertex vertex : delaunayVertexes) {
            points.add(vertex.getPoint());
        }
        return points;
    }

    public ArrayList<Vertex> getDelaunayVertexes() {
        return delaunayVertexes;
    }

    public ArrayList<Edge> getDelaunayEdges() {
        ArrayList<Edge> edges = new ArrayList<Edge>(delaunayEdges);
        return edges;
    }

    public ArrayList<Vertex> getVoronoiVertexes() {
        return voronoiVertexes;
    }

    public ArrayList<Edge> getVoronoiEdges() {
        return voronoiEdges;
    }

    public ArrayList<Segment> getSegments() {
        ArrayList<Segment> segments = new ArrayList<Segment>(delaunayEdges.size());
        for (Edge edge : delaunayEdges) {
            segments.add(edge.getSegment());
        }
        return segments;
    }

    public ArrayList<Circle> getCircumcircles() {
        ArrayList<Circle> circles = new ArrayList<Circle>(triangles.size());
        for (DelaunayTriangle triangle : triangles) {
            circles.add(triangle.getCircumcircle());
        }
        return circles;
    }

    public ArrayList<Triangle> getDelaunayTriangles() {
        ArrayList<Triangle> triangles = new ArrayList<Triangle>(this.triangles.size());
        for (DelaunayTriangle triangle : this.triangles) {
            triangles.add(triangle.getTriangle());
        }
        return triangles;
    }


    void generate_delaunay() {
        generate_delaunay_n4();
    }

    /**
     * Computes the delaunay triangulation in O(n^4) time. This should only be used for testing purposes.
     */
    void generate_delaunay_n4() {
        delaunayEdges.clear();
        triangles.clear();

        DelaunayEdge[][] delaunayEdges = new DelaunayEdge[delaunayVertexes.size()][delaunayVertexes.size()];

        if(delaunayVertexes.size() >= 3) {

            for (int i = 0; i < delaunayVertexes.size(); i++) {
                for (int j = i+1; j < delaunayVertexes.size(); j++) {
                    for (int k = j+1; k < delaunayVertexes.size(); k++) {
                        Circle circle = new Circle(delaunayVertexes.get(i).getPoint(), delaunayVertexes.get(j).getPoint(), delaunayVertexes.get(k).getPoint());
                        boolean intersected = false;
                        for (int l = 0; l < delaunayVertexes.size(); l++) {
                            if (circle.contains(delaunayVertexes.get(l).getPoint())) {
                                intersected = true;
                                break;
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
        }

        generate_voronoi();
    }

    void generate_voronoi() {
        voronoiEdges.clear();
        voronoiVertexes.clear();

        for (DelaunayEdge edge : delaunayEdges) {

            assert(triangles.size() > 0);
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
                    System.out.println(line + ", x: " + line.xIntercept);

                    double x, y;

                    boolean clockwise = triangle.area() > 0;

                    //clockwise ordering
                    if (clockwise) {
                        if (p2.x > p1.x) {
                            y = -BOUNDS;
                        } else {
                            y = BOUNDS;
                        }
                    }
                    //counter-clockwise ordering
                    else {
                        if (p2.x > p1.x) {
                            y = BOUNDS;
                        } else {
                            y = -BOUNDS;
                        }
                    }

                    x = line.x(y);

                    if (Double.isNaN(x)) {
                        if(clockwise) {
                            if (p2.y > p1.y) {
                                x = BOUNDS;
                            } else {
                                x = -BOUNDS;
                            }
                        } else  {
                            if (p2.y > p1.y) {
                                x = -BOUNDS;
                            } else {
                                x = BOUNDS;
                            }
                        }
                        y = line.y(x);
                    } else
                    if (Math.abs(x) > BOUNDS) {
                        x = BOUNDS * Math.abs(x) / x;
                        y = line.y(x);
                    }

                    System.out.println(new Point(x, y));

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

                voronoiVertexes.add(a);
                voronoiVertexes.add(b);
                voronoiEdges.add(new Edge(a, b));
            }
        }
    }

    class DelaunayTriangle {

        final Vertex a, b, c;
        final DelaunayEdge ab, bc, ca;
        boolean visited = false;

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

            System.out.println("adjacent triangles: " + (ab.getTriangles().size() + bc.getTriangles().size() + ca.getTriangles().size()));

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

        boolean contains(Vertex vertex) {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint()).contains(vertex.getPoint());
        }

        boolean interior(Vertex vertex) {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint()).interior(vertex.getPoint());
        }

        Triangle getTriangle() {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint());
        }

        Circle getCircumcircle() {
            return getTriangle().getCircumcircle();
        }

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
     * A triangular edge stores additional information about
     */
    class DelaunayEdge extends Edge {

        ArrayList<DelaunayTriangle> triangles = new ArrayList<DelaunayTriangle>(2);
        public boolean inspected = false;

        DelaunayEdge(Vertex a, Vertex b) {
            super(a, b);
        }

        ArrayList<DelaunayTriangle> getTriangles() {
            return triangles;
        }

        void addTriangle(DelaunayTriangle triangle) {
            assert(triangles.size() < 2);
            triangles.add(triangle);
        }

        void removeTriangle(DelaunayTriangle triangle) {
            assert(triangles.size() > 0);
            for (int i = 0; i < triangles.size(); i++) {
                if(triangles.get(i) == triangle) {
                    triangles.remove(i);
                }
            }
        }

        boolean isHull() {
            return triangles.size() == 1;
        }

        boolean contains(Vertex vertex) {
            return this.getSegment().contains(vertex.getPoint());
        }

    }
}