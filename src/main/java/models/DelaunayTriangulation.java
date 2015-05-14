package models;

import geometry.Circle;
import geometry.Point;
import geometry.Segment;
import geometry.Triangle;
import graph.Edge;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class DelaunayTriangulation extends Model {

    HashMap<String, Vertex> vertexes = new HashMap<String, Vertex>();
    HashMap<String, TriangleEdge> edges = new HashMap<String, TriangleEdge>();

    //delaunay construction
    ArrayList<GraphTriangle> triangles = new ArrayList<GraphTriangle>();
    ArrayList<TriangleEdge> edgeInspections = new ArrayList<TriangleEdge>();

    @Override
    public void addVertex(Point location) {
        Vertex vertex = new Vertex(location);
        vertexes.put(vertex.toString(), vertex);
        triangulate();
    }

    @Override
    public void moveVertex(Vertex vertex, Point location) {
        vertexes.remove(vertex.toString());
        Vertex newVertex = new Vertex(location);
        vertexes.put(newVertex.toString(), newVertex);
        triangulate();
    }

    @Override
    public void moveVertex(Point point, Point location) {
        moveVertex(vertexes.get(point.toString()), location);
    }

    @Override
    public void removeVertex(Vertex vertex) {
        //remove vertex, remove edges
        vertexes.remove(vertex.toString());
        triangulate();
    }

    @Override
    public void removeVertex(Point point) {
        removeVertex(vertexes.get(point.toString()));
    }

    void triangulate() { //temporarily public
        triangulate_n2();
    }

    /**
     * Computes the Delaunay triangulation in O(n^2) time. This could be further optimized to O(nlogn) time.
     */
    void triangulate_n2() {
        edges.clear();
        triangles.clear();
        if(vertexes.size() >= 3) {
            ArrayList<Point> points = getVertexPoints();

            Triangle boundingTriangle = new Triangle(points);

            Vertex boundA = new Vertex(boundingTriangle.a);
            Vertex boundB = new Vertex(boundingTriangle.b);
            Vertex boundC = new Vertex(boundingTriangle.c);

//            vertexes.put(boundA.toString(), boundA);
//            vertexes.put(boundB.toString(), boundB);
//            vertexes.put(boundC.toString(), boundC);

            TriangleEdge boundAB = new TriangleEdge(boundA, boundB);
            TriangleEdge boundBC = new TriangleEdge(boundB, boundC);
            TriangleEdge boundCA = new TriangleEdge(boundC, boundA);

            edges.put(boundAB.toString(), boundAB);
            edges.put(boundBC.toString(), boundBC);
            edges.put(boundCA.toString(), boundCA);

            GraphTriangle boundingGraphTriangle = new GraphTriangle(boundAB, boundBC, boundCA);

            triangles.add(boundingGraphTriangle);

            for (String s : vertexes.keySet()) {
                for (int j = 0; j < triangles.size(); j++) {
                    if (triangles.get(j).contains(vertexes.get(s))) {
                        split(vertexes.get(s), triangles.get(j));
                        //System.out.println(edgeInspections.size());
//                        ArrayList<TriangleEdge> edgesCopy = new ArrayList<TriangleEdge>(edges.size());
//                        for (String k : edges.keySet()) {
//                            edgesCopy.add(edges.get(k));
//                        }
//                        for (TriangleEdge edge : edgesCopy) {
//                            flip(edge);
//                        }
                        while (!edgeInspections.isEmpty()) {
                           // System.out.println("test");
                            TriangleEdge edge = edgeInspections.remove(edgeInspections.size()-1); //more efficient
                            flip(edge);
                        }
                        break;
                    }
                }
            }

//            //delete outer triangle
//            vertexes.remove(boundA.toString());
//            for (Edge edge : boundA.getEdges()) {
//                edges.remove(edge.toString());
//            }
//            vertexes.remove(boundB.toString());
//            for (Edge edge : boundB.getEdges()) {
//                edges.remove(edge.toString());
//            }
//            vertexes.remove(boundC.toString());
//            for (Edge edge : boundC.getEdges()) {
//                edges.remove(edge.toString());
//            }



//            for (int i = 0; i < vertexes.size(); i++) {
//                for (int j = 0; j < triangles.size(); j++) {
//                    if (triangles.get(j).contains(points.get(i))) {
//                        //TODO
//                        //split(new Vertex(points.get(i)), triangles.get(j));
//                        //while edgeInspections isn't empty, flip on edge
////                        ArrayList<Segment> segments = triangle.getSegments();
////                        for (int k = 0; k < segments.size(); k++) {
////                            assert (k < 3);
////                            Segment segment = segments.get(k);
////                            triangles.add(new Triangle(segment.start, segment.end, points.get(i)));
////                        }
////                        break;
//                    }
//                }
//            }
        }
        System.out.println("num edges: " + edges.size());
    }

    public ArrayList<Point> getVertexPoints() {
        ArrayList<Point> points = new ArrayList<Point>(vertexes.size());
        for (String s : vertexes.keySet()) {
            points.add(vertexes.get(s).getPoint());
        }
        return points;
    }

    public ArrayList<Segment> getEdgeSegments() {
        ArrayList<Segment> segments = new ArrayList<Segment>(edges.size());
        for (String s : edges.keySet()) {
            segments.add(edges.get(s).getSegment());
        }
        return segments;
    }

    /**
     * Inserts the given point into the triangulation, splitting the given triangle into three new triangles
     * @param vertex
     * @param triangle
     */
    void split(Vertex vertex, GraphTriangle triangle) {
        this.triangles.remove(triangle);
        triangle.delete();

        assert(triangle.contains(vertex));

        TriangleEdge va = new TriangleEdge(vertex, triangle.a);
        TriangleEdge vb = new TriangleEdge(vertex, triangle.b);
        TriangleEdge vc = new TriangleEdge(vertex, triangle.c);

        this.edges.put(va.toString(), va);
        this.edges.put(vb.toString(), vb);
        this.edges.put(vc.toString(), vc);

        this.triangles.add(new GraphTriangle(triangle.ab, vb, va));
        this.triangles.add(new GraphTriangle(triangle.bc, vc, vb));
        this.triangles.add(new GraphTriangle(triangle.ca, va, vc));

        this.edgeInspections.add(triangle.ab);
        this.edgeInspections.add(triangle.bc);
        this.edgeInspections.add(triangle.ca);

        assert(va.getTriangles().size() == 2);
        assert(vb.getTriangles().size() == 2);
        assert(vc.getTriangles().size() == 2);

        assert(triangle.ab.getTriangles().size() > 0);
        assert(triangle.bc.getTriangles().size() > 0);
        assert(triangle.ca.getTriangles().size() > 0);
    }

//    /**
//     * Potentially flips the given edge depending upon
//     * @param edge
//     */
    void flip(TriangleEdge edge) {

        if(!edge.isHull()) {

            ArrayList<GraphTriangle> adjacentTriangles = edge.getTriangles();
            assert(adjacentTriangles.size() == 2) : "size should be 2, actually: " + adjacentTriangles.size();

            GraphTriangle triangleA = adjacentTriangles.get(0);
            GraphTriangle triangleB = adjacentTriangles.get(1);

            Vertex a = edge.from;
            Vertex b = edge.to;

            //find the two unshared vertexes
            Vertex c;
            if (triangleA.a != a && triangleA.a != b) { c = triangleA.a; }
            else if (triangleA.b != a && triangleA.b != b) { c = triangleA.b; }
            else { c = triangleA.c; }

            Vertex d;
            if (triangleB.a != a && triangleB.a != b) { d = triangleB.a; }
            else if (triangleB.b != a && triangleB.b != b) { d = triangleB.b; }
            else { d = triangleB.c; }

            if (triangleA.circumcircleContains(d) || triangleB.circumcircleContains(c)) {
                //delete triangles
                assert(triangles.remove(triangleA));
                triangleA.delete();

                assert(triangles.remove(triangleB));
                triangleB.delete();

                //delete edge to be flipped
                edges.remove(edge.toString());
                edgeInspections.remove(edge);
                edge.delete();

                //find the four unshared edges
                ArrayList<TriangleEdge> taEdges = new ArrayList<TriangleEdge>(2);
                if (triangleA.ab != edge) {
                    taEdges.add(triangleA.ab);
                }
                if (triangleA.bc != edge) {
                    taEdges.add(triangleA.bc);
                }
                if (triangleA.ca != edge) {
                    taEdges.add(triangleA.ca);
                }
                assert (taEdges.size() == 2);
                assert (!taEdges.contains(edge));

                ArrayList<TriangleEdge> tbEdges = new ArrayList<TriangleEdge>(2);
                if (triangleB.ab != edge) {
                    tbEdges.add(triangleB.ab);
                }
                if (triangleB.bc != edge) {
                    tbEdges.add(triangleB.bc);
                }
                if (triangleB.ca != edge) {
                    tbEdges.add(triangleB.ca);
                }
                assert (tbEdges.size() == 2);
                assert (!tbEdges.contains(edge));

                //find the pairs which share a vertex from the edge to be swapped
                ArrayList<TriangleEdge> tcEdges = new ArrayList<TriangleEdge>(2);
                ArrayList<TriangleEdge> tdEdges = new ArrayList<TriangleEdge>(2);
                if (taEdges.get(0).from == a || taEdges.get(0).to == a) {
                    tcEdges.add(taEdges.get(0));
                    tdEdges.add(taEdges.get(1));
                } else {
                    tdEdges.add(taEdges.get(0));
                    tcEdges.add(taEdges.get(1));
                }
                if (tbEdges.get(0).from == a || tbEdges.get(0).to == a) {
                    tcEdges.add(tbEdges.get(0));
                    tdEdges.add(tbEdges.get(1));
                } else {
                    tdEdges.add(tbEdges.get(0));
                    tcEdges.add(tbEdges.get(1));
                }

                //construct new edge
                TriangleEdge flippedEdge = new TriangleEdge(c, d);
                edges.put(flippedEdge.toString(), flippedEdge);

                //construct triangles
                GraphTriangle tc = new GraphTriangle(tcEdges.get(0), tcEdges.get(1), flippedEdge);
                triangles.add(tc);
                GraphTriangle td = new GraphTriangle(tdEdges.get(0), tdEdges.get(1), flippedEdge);
                triangles.add(td);

//                //mark unshared edges to be inspected
//                edgeInspections.add(tcEdges.get(0));
//                edgeInspections.add(tcEdges.get(1));
//                edgeInspections.add(tdEdges.get(0));
//                edgeInspections.add(tdEdges.get(1));

                System.out.println("swapped");

                assert(taEdges.get(0).getTriangles().size() > 0);
                assert(taEdges.get(1).getTriangles().size() > 0);
                assert(tbEdges.get(0).getTriangles().size() > 0);
                assert(tbEdges.get(1).getTriangles().size() > 0);

                assert(tcEdges.get(0).getTriangles().size() > 0);
                assert(tcEdges.get(1).getTriangles().size() > 0);
                assert(tdEdges.get(0).getTriangles().size() > 0);
                assert(tdEdges.get(1).getTriangles().size() > 0);

                assert(triangleA.ab.getTriangles().size() > 0 || !edges.containsKey(triangleA.ab.toString()));
                assert(triangleA.bc.getTriangles().size() > 0 || !edges.containsKey(triangleA.bc.toString()));
                assert(triangleA.ca.getTriangles().size() > 0 || !edges.containsKey(triangleA.ca.toString()));

                assert(triangleB.ab.getTriangles().size() > 0 || !edges.containsKey(triangleB.ab.toString()));
                assert(triangleB.bc.getTriangles().size() > 0 || !edges.containsKey(triangleB.bc.toString()));
                assert(triangleB.ca.getTriangles().size() > 0 || !edges.containsKey(triangleB.ca.toString()));

                assert(flippedEdge.getTriangles().size() > 0);

            }
        }

    }



    class GraphTriangle {

        public final Vertex a, b, c;
        public final TriangleEdge ab, bc, ca;

        /**
         * Constructs a new GraphTriangle from the given edges. Edge order or direction does not matter.
         * @param ab
         * @param bc
         * @param ca
         */
        GraphTriangle(TriangleEdge ab, TriangleEdge bc, TriangleEdge ca) {
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
         * Returns all adjacent {@link GraphTriangle LinkedTriangles}
         * @return
         */
        ArrayList<GraphTriangle> getAdjacentTriangles() {
            ArrayList<GraphTriangle> triangles = new ArrayList<GraphTriangle>();

            for (GraphTriangle triangle : ab.getTriangles()) {
                if(triangle != this) {
                    triangles.add(triangle);
                }
            }
            for (GraphTriangle triangle : bc.getTriangles()) {
                if(triangle != this) {
                    triangles.add(triangle);
                }
            }
            for (GraphTriangle triangle : ca.getTriangles()) {
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

        boolean circumcircleContains(Vertex vertex) {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint()).getCircumcircle().contains(vertex.getPoint());
        }

        void delete() {
            ab.removeTriangle(this);
            bc.removeTriangle(this);
            ca.removeTriangle(this);
        }

//        ArrayList<TriangleEdge> getEdges() {
//            ArrayList<TriangleEdge> edges = new ArrayList<TriangleEdge>(3);
//            edges.add(ab);
//            edges.add(bc);
//            edges.add(ca);
//            return edges;
//        }
//
//        ArrayList<Vertex> getVertexes() {
//            ArrayList<Vertex> vertexes = new ArrayList<Vertex>(3);
//            vertexes.add(a);
//            vertexes.add(b);
//            vertexes.add(c);
//            return vertexes;
//        }
    }

    /**
     * A triangular edge stores additional information about
     */
    class TriangleEdge extends Edge {

        ArrayList<GraphTriangle> triangles = new ArrayList<GraphTriangle>(2);
        public boolean inspected = false;

        TriangleEdge(Vertex a, Vertex b) {
            super(a, b);
        }

        ArrayList<GraphTriangle> getTriangles() {
            return triangles;
        }

        void addTriangle(GraphTriangle triangle) {
            assert(triangles.size() < 2);
            triangles.add(triangle);
        }

        void removeTriangle(GraphTriangle triangle) {
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

    }

//    /**
//     * Computes the delaunay triangulation in O(n^4) time. This should only be used for testing purposes.
//     */
//    void triangulate_n4() {
//        edges.clear();
//        if(vertexes.size() >= 3) {
//
//            for (String i : vertexes.keySet()) {
//                for (String j : vertexes.keySet()) {
//                    for (String k : vertexes.keySet()) {
//                            Circle circle = new Circle(vertexes.get(i).getPoint(), vertexes.get(j).getPoint(), vertexes.get(k).getPoint());
//                            boolean intersected = false;
//                            for (String l : vertexes.keySet()) {
//                                if (circle.contains(vertexes.get(l).getPoint())) {
//                                    System.out.println("intersected");
//                                    intersected = true;
//                                    break;
//                                }
//                            }
//                            if (!intersected) {
//                                Edge edge;
//                                edge = new Edge(vertexes.get(i), vertexes.get(j));
//                                edges.put(edge.toString(), edge);
//                                edge = new Edge(vertexes.get(j), vertexes.get(k));
//                                edges.put(edge.toString(), edge);
//                                edge = new Edge(vertexes.get(k), vertexes.get(i));
//                                edges.put(edge.toString(), edge);
//                            }
//                    }
//                }
//            }
//        }
//    }

}