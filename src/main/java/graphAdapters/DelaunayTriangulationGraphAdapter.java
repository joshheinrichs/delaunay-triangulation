package graphAdapters;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import graph.Edge;
import graph.Vertex;
import models.DelaunayTriangulation;
import org.apache.commons.collections15.Transformer;

import java.util.ArrayList;

/**
 * Created by joshheinrichs on 15-05-22.
 */
public class DelaunayTriangulationGraphAdapter {

    DelaunayTriangulation delaunayTriangulation;

    UndirectedGraph<Vertex, Edge> graph;
    Transformer<Edge, Double> transformer = new Transformer<Edge, Double>() {
        public Double transform(Edge edge) {
            return edge.getSegment().length();
        }
    };
    DijkstraDistance<Vertex, Edge> algorithm;

    Double[][] distances;

    public DelaunayTriangulationGraphAdapter(DelaunayTriangulation delaunayTriangulation) {
        this.delaunayTriangulation = delaunayTriangulation;
        update();
    }

    public void update() {
        ArrayList<Edge> edges = delaunayTriangulation.getDelaunayEdges();
        ArrayList<Vertex> vertexes = delaunayTriangulation.getDelaunayVertexes();

        if(vertexes.size() >= 3) {
            graph = new UndirectedSparseGraph<Vertex, Edge>();
            algorithm = new DijkstraDistance<Vertex, Edge>(graph, transformer);

            for (Vertex vertex : vertexes) {
                assert graph.addVertex(vertex);
            }
            for (Edge edge : edges) {
                assert graph.addEdge(edge, edge.from, edge.to);
            }

            distances = new Double[vertexes.size()][vertexes.size()];

            if (!vertexes.isEmpty()) {
                System.out.print("\t");
                for (int i = 0; i < vertexes.size(); i++) {
                    System.out.print(i + "\t");
                }
                System.out.print("\n");
                for (int i = 0; i < vertexes.size(); i++) {
                    System.out.print(i + "\t");
                    for (int j = 0; j < vertexes.size(); j++) {
                        distances[i][j] =
                                ((Double) algorithm.getDistance(vertexes.get(i), vertexes.get(j)))
                                        / (vertexes.get(i).getPoint().distance(vertexes.get(j).getPoint()));
                        System.out.print(distances[i][j] + "\t");
                    }
                    System.out.print("\n");
                }
                System.out.print("\n");
            }
        }
    }

}
