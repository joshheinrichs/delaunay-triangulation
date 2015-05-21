package models;

import geometry.Circle;
import geometry.Point;
import geometry.Segment;
import geometry.Triangle;
import graph.Edge;
import graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class DelaunayTriangulation extends Model {

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


    void generate_delaunay() {
        generate_delaunay_n4();
    }

    /**
     * Computes the Delaunay triangulation in O(n^2) time. This could be further optimized to O(nlogn) time.
     */
    void generate_delaunay_n2() {

        delaunayEdges.clear();
        triangles.clear();

        if(delaunayVertexes.size() >= 0) {

            //find the lexicographically highest point, the rightmost among the points with the largest y-coordinate
//            ArrayList<Vertex> highest = new ArrayList<Vertex>();
//            highest.add(delaunayVertexes.get(0));
//            for (int i = 1; i < delaunayVertexes.size(); i++) {
//                Vertex vertex = delaunayVertexes.get(i);
//                if (vertex.getPoint().y > highest.get(0).getPoint().y) {
//                    highest.clear();
//                    highest.add(vertex);
//                } else if (vertex.getPoint().y == highest.get(0).getPoint().y) {
//                    highest.add(vertex);
//                }
//            }
//            Vertex bound0 = highest.get(0);
//            for (int i = 1; i < highest.size(); i++) {
//                Vertex vertex = highest.get(i);
//                if (vertex.getPoint().x > bound0.getPoint().x) {
//                    bound0 = vertex;
//                }
//            }

            //let p−1 and p−2 be two points in R2 sufficiently far away and such that P is contained in the triangle p0 p−1 p−2.
            Vertex bound1 = new Vertex(new Point(-20000, -20000)); //TODO: construct proper bound points
            Vertex bound2 = new Vertex(new Point(20000, -20000));
            Vertex bound3 = new Vertex(new Point(0, 20000));

            //initialize T as the triangulation consisting of the single triangle p0 p−1 p−2. Compute a random permutation p1,p2,...,pn of P\{p0}.
            DelaunayTriangle bound = new DelaunayTriangle(
                    new DelaunayEdge(bound3, bound1),
                    new DelaunayEdge(bound1, bound2),
                    new DelaunayEdge(bound2, bound3)
            );
            triangles.add(bound);

            //for r←1 to n do (∗ Insert pr into T: ∗)
            for (int i = 0; i < delaunayVertexes.size(); i++) {
                Vertex vertex = delaunayVertexes.get(i);
               // if (vertex != bound0) {
                    //find a triangle pi p j pk ∈ T containing pr .
                    for (int j = 0; j < triangles.size(); j++) {
                        DelaunayTriangle triangle = triangles.get(j);
                        if (triangle.contains(vertex)) {
                            split(vertex, triangle);
                            break;
                        } else if (j == triangles.size() - 1) {
                            assert(false);
                        }
                    }
                //}
            }

//            for (DelaunayTriangle triangle : triangles) {
//                System.out.println(triangle);
//            }

            //discard p−1 and p−2 with all their incident delaunayEdges from T. return T
            for (Edge edge : bound1.getEdges()) {
                delaunayEdges.remove(edge);
                //TODO: remove adjacent triangles?
            }
            for (Edge edge : bound2.getEdges()) {
                delaunayEdges.remove(edge);
            }
            for (Edge edge : bound3.getEdges()) {
                delaunayEdges.remove(edge);
            }
        }
    }

    void split(Vertex vertex, DelaunayTriangle triangle) {
        //if pr lies in the interior of the triangle pi p j pk
        if(triangle.interior(vertex)) {

//            System.out.println(triangle);

            //then add delaunayEdges from pr to the three vertices of pi p j pk , thereby splitting pi p j pk into three triangles.
            DelaunayEdge va = new DelaunayEdge(vertex, triangle.a);
            DelaunayEdge vb = new DelaunayEdge(vertex, triangle.b);
            DelaunayEdge vc = new DelaunayEdge(vertex, triangle.c);

            delaunayEdges.add(va);
            delaunayEdges.add(vb);
            delaunayEdges.add(vc);

            //delete triangle
            int size = triangles.size();
            triangles.remove(triangle);
            assert(triangles.size() == size - 1);
            triangle.delete();

            //add new triangles
            triangles.add(new DelaunayTriangle(va, triangle.ab, vb));
            triangles.add(new DelaunayTriangle(vb, triangle.bc, vc));
            triangles.add(new DelaunayTriangle(vc, triangle.ca, va));

            assert(triangles.size() == size + 2);

            //LEGALIZEEDGE(pr, pi pj, T)
            flip(vertex, triangle.ab);
            //LEGALIZEEDGE(pr, pj pk, T)
            flip(vertex, triangle.bc);
            //LEGALIZEEDGE(pr, pk pi, T)
            flip(vertex, triangle.ca);
        }
        //else (∗ pr lies on an edge of pipjpk, say the edge pipj ∗)
        else {

            assert(false);

            System.out.println("on line");

            //add delaunayEdges from pr to pk and to the third vertex pl of the other triangle that is incident to pi p j , thereby splitting the two triangles incident to pi p j into four triangles.

            //find intersected edge
            DelaunayEdge edge;
            if(triangle.ab.contains(vertex)) {
                edge = triangle.ab;
            } else if(triangle.bc.contains(vertex)) {
                edge = triangle.bc;
            } else {
                assert triangle.ca.contains(vertex);
                edge = triangle.ca;
            }

            //find adjacent triangle
            DelaunayTriangle adjacentTriangle;
            ArrayList<DelaunayTriangle> edgeTriangles = edge.getTriangles();
            if(edgeTriangles.get(0) != triangle) {
                adjacentTriangle = edgeTriangles.get(0);
            } else {
                assert(edgeTriangles.get(1) != triangle);
                adjacentTriangle = edgeTriangles.get(1);
            }

            //delete triangle, adjacent triangle, and intersected edge
            triangles.remove(triangle);
            triangle.delete();
            triangles.remove(adjacentTriangle);
            adjacentTriangle.delete();
            delaunayEdges.remove(edge);
            edge.delete();

            //find the points and unshared delaunayEdges
            Vertex a, b, c, d;

            a = edge.from;
            c = edge.to;

            if(triangle.a != a && triangle.a != c) {
                b = triangle.a;
            } else if(triangle.b != a && triangle.b != c) {
                b = triangle.b;
            } else {
                assert(triangle.c != a && triangle.c != c);
                b = triangle.c;
            }

            if(adjacentTriangle.a != a && adjacentTriangle.a != c) {
                d = adjacentTriangle.a;
            } else if(adjacentTriangle.b != a && adjacentTriangle.b != c) {
                d = adjacentTriangle.b;
            } else {
                assert(adjacentTriangle.c != a && adjacentTriangle.c != c);
                d = adjacentTriangle.c;
            }

            DelaunayEdge ab, bc, cd, da;

            //ab is nonshared edge from t1 with point a
            if(triangle.ab != edge && (triangle.a == a || triangle.b == a)) {
                ab = triangle.ab;
            } else if(triangle.bc != edge && (triangle.b == a || triangle.c == a)) {
                ab = triangle.bc;
            } else {
                assert(triangle.ca != edge && (triangle.c == a || triangle.a == a));
                ab = triangle.ca;
            }

            //bc is nonshared edge from t1 with point c
            if(triangle.ab != edge && (triangle.a == c || triangle.b == c)) {
                bc = triangle.ab;
            } else if(triangle.bc != edge && (triangle.b == c || triangle.c == c)) {
                bc = triangle.bc;
            } else {
                assert(triangle.ca != edge && (triangle.c == c || triangle.a == c));
                bc = triangle.ca;
            }

            //cd is nonshared edge from t2 with point c
            if(adjacentTriangle.ab != edge && (adjacentTriangle.a == c || adjacentTriangle.b == c)) {
                cd = adjacentTriangle.ab;
            } else if(adjacentTriangle.bc != edge && (adjacentTriangle.b == c || adjacentTriangle.c == c)) {
                cd = adjacentTriangle.bc;
            } else {
                assert(adjacentTriangle.ca != edge && (adjacentTriangle.c == c || adjacentTriangle.a == c));
                cd = adjacentTriangle.ca;
            }

            //da is nonshared edge from t2 with point a
            if(adjacentTriangle.ab != edge && (adjacentTriangle.a == a || adjacentTriangle.b == a)) {
                da = adjacentTriangle.ab;
            } else if(adjacentTriangle.bc != edge && (adjacentTriangle.b == a || adjacentTriangle.c == a)) {
                da = adjacentTriangle.bc;
            } else {
                assert(adjacentTriangle.ca != edge && (adjacentTriangle.c == a || adjacentTriangle.a == a));
                da = adjacentTriangle.ca;
            }

            //create new delaunayEdges
            DelaunayEdge va = new DelaunayEdge(vertex, a);
            DelaunayEdge vb = new DelaunayEdge(vertex, b);
            DelaunayEdge vc = new DelaunayEdge(vertex, c);;
            DelaunayEdge vd = new DelaunayEdge(vertex, d);;

            //create new triangles
            triangles.add(new DelaunayTriangle(va, vb, ab));
            triangles.add(new DelaunayTriangle(vb, vc, bc));
            triangles.add(new DelaunayTriangle(vc, vd, cd));
            triangles.add(new DelaunayTriangle(vd, va, da));

            //LEGALIZEEDGE(pr, pi pl, T)
            flip(vertex, ab);
            //LEGALIZEEDGE(pr, pl pj, T)
            flip(vertex, bc);
            //LEGALIZEEDGE(pr, pj pk, T)
            flip(vertex, cd);
            //LEGALIZEEDGE(pr, pk pi, T)
            flip(vertex, da);
        }
    }

    //LEGALIZEEDGE(pr, pi pj,T)
    void flip(Vertex vertex, DelaunayEdge edge) {
        //(∗ The point being inserted is pr, and pi pj is the edge of T that may need to be flipped. ∗)
        //if pi pj is illegal
            //then let pi pj pk be the triangle adjacent to pr pi pj along pi pj
            //(∗ Flip pi pj: ∗) Replace pi pj with pr pk.
            //LEGALIZEEDGE(pr, pi pk,T)
            //LEGALIZEEDGE(pr, pk pj,T)
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

        Circle getCircumcircle() {
            return new Triangle(a.getPoint(), b.getPoint(), c.getPoint()).getCircumcircle();
        }

        void delete() {
            ab.removeTriangle(this);
            bc.removeTriangle(this);
            ca.removeTriangle(this);
        }

//        ArrayList<DelaunayEdge> getEdges() {
//            ArrayList<DelaunayEdge> delaunayEdges = new ArrayList<DelaunayEdge>(3);
//            delaunayEdges.add(ab);
//            delaunayEdges.add(bc);
//            delaunayEdges.add(ca);
//            return delaunayEdges;
//        }
//
//        ArrayList<Vertex> getVertexes() {
//            ArrayList<Vertex> delaunayVertexes = new ArrayList<Vertex>(3);
//            delaunayVertexes.add(a);
//            delaunayVertexes.add(b);
//            delaunayVertexes.add(c);
//            return delaunayVertexes;
//        }

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

    /**
     * Computes the delaunay triangulation in O(n^4) time. This should only be used for testing purposes.
     */
    void generate_delaunay_n4() {
        delaunayEdges.clear();

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
            generate_voronoi();
        }
    }

    void generate_voronoi() {
        generate_voronoi(triangles.get(0));
    }


    void generate_voronoi(DelaunayTriangle triangle) {
//        triangle.visited = true;
//        System.out.println("test");
//        for (DelaunayTriangle adjacentTriangle : triangle.getAdjacentTriangles()) {
//            System.out.println("222222");
//            if(!adjacentTriangle.visited) {
//                voronoiEdges.add(new Segment(triangle.getCircumcircle().center, adjacentTriangle.getCircumcircle().center));
//                generate_voronoi(triangle);
//            }
//        }

        voronoiEdges.clear();

        for (DelaunayEdge edge : delaunayEdges) {

            assert(triangles.size() > 0);
            if(edge.isHull()) {
                ArrayList<DelaunayTriangle> triangles = edge.getTriangles();

                Vertex a = new Vertex(triangles.get(0).getCircumcircle().center);
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


}