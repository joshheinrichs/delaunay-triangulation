package graphAdapters;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Vertex;
import models.DelaunayTriangulation;
import org.apache.commons.collections15.Transformer;

import java.util.List;
import java.util.Stack;

/**
 * Created by joshheinrichs on 15-05-22.
 */
public class DelaunayTriangulationGraphAdapter {

    DelaunayTriangulation delaunayTriangulation;

    UndirectedGraph<Vertex, Edge> graph;
    Transformer<Edge, Double> transformer = edge -> edge.getSegment().length();
    DijkstraShortestPath<Vertex, Edge> shortestPathAlgorithm;

    Double[][] distances;

    /**
     * Constructs a DelaunayTriangulationGraphAdapter.
     * @param delaunayTriangulation
     */
    public DelaunayTriangulationGraphAdapter(DelaunayTriangulation delaunayTriangulation) {
        this.delaunayTriangulation = delaunayTriangulation;
        update();
    }

    /**
     * Updates the graph, accounting for any changes that have occurred in the Delaunay triangulation.
     * If it is possible that changes have occurred, this method should be called before computing any properties.
     */
    public void update() {
        List<Edge> edges = delaunayTriangulation.getDelaunayEdges();
        List<Vertex> vertexes = delaunayTriangulation.getDelaunayVertexes();

        if(vertexes.size() >= 3) {
            graph = new UndirectedSparseGraph<>();
            shortestPathAlgorithm = new DijkstraShortestPath<>(graph, transformer);

            for (Vertex vertex : vertexes) {
                graph.addVertex(vertex);
            }
            for (Edge edge : edges) {
                graph.addEdge(edge, edge.from, edge.to);
            }

            distances = new Double[vertexes.size()][vertexes.size()];

//            if (!vertexes.isEmpty()) {
//                System.out.print("\t");
//                for (int i = 0; i < vertexes.size(); i++) {
//                    System.out.print(i + "\t");
//                }
//                System.out.print("\n");
//                for (int i = 0; i < vertexes.size(); i++) {
//                    System.out.print(i + "\t");
//                    for (int j = 0; j < vertexes.size(); j++) {
//                        distances[i][j] =
//                                ((Double) distanceAlgorithm.getShortestPathDistance(vertexes.get(i), vertexes.get(j)))
//                                        / (vertexes.get(i).getPoint().distance(vertexes.get(j).getPoint()));
//                        System.out.print(distances[i][j] + "\t");
//                    }
//                    System.out.print("\n");
//                }
//                System.out.print("\n");
//            }
        }
    }

    /**
     * Returns the distance of the shortest path from a to b in the Delaunay triangulation.
     * @param a
     * @param b
     * @return
     */
    public double getShortestPathDistance(Vertex a, Vertex b) {
        return (Double) shortestPathAlgorithm.getDistance(a, b);
    }

    /**
     * Returns the list of edges involved in the shortest path from a to b in the Delaunay triangulation.
     * @param a
     * @param b
     * @return
     */
    public List<Edge> getShortestPath(Vertex a, Vertex b) {
        return shortestPathAlgorithm.getPath(a, b);
    }

    public List<Edge> getCompassRoute(Vertex a, Vertex b) {

        if (a == b) {
            return new Stack<>();
        } else {
            List<Edge> edges = (List<Edge>) graph.getIncidentEdges(a);

            Double bestAngle = Double.POSITIVE_INFINITY;
            Vertex bestVertex = null;
            Edge bestEdge = null;

            for (Edge edge : edges) {
                Vertex vertex = graph.getOpposite(a, edge);
                ///check angle
            }

            Stack<Edge> path = (Stack<Edge>) getCompassRoute(bestVertex, b);
            path.push(bestEdge);

            return path;
        }
    }

}
