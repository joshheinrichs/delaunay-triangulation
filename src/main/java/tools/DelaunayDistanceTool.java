package tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import uiAdapters.DelaunayTriangulationUiAdapter;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-29.
 */
public class DelaunayDistanceTool extends Tool {


    public DelaunayDistanceTool(UiAdapter uiAdapter) {
        super(uiAdapter);
    }

    @Override
    public String getName() {
        return "Delaunay Distance";
    }

    @Override
    public void onMouseClicked(MouseEvent t) {

    }

    @Override
    public void onMousePressed(MouseEvent t) {

    }

    @Override
    public void onMouseReleased(MouseEvent t) {

    }

    @Override
    public void onMouseDragged(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        String id = ((Circle) t.getSource()).getId();

        if (uiAdapter.getSelectedVertexes().size() == 0) {
            uiAdapter.selectVertex(id);
            uiAdapter.draw();
        } else if (uiAdapter.getSelectedVertexes().size() == 1) {
            if (!uiAdapter.isVertexSelected(id)) {
                uiAdapter.selectVertex(id);

                String id1 = uiAdapter.getSelectedVertexes().get(0);
                String id2 = uiAdapter.getSelectedVertexes().get(1);

                double delaunayDistance = ((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayDistance(id1, id2);
                double straightDistance = ((DelaunayTriangulationUiAdapter) uiAdapter).getStraightDistance(id1, id2);
                double distanceRatio = delaunayDistance/straightDistance;

                uiAdapter.deselectAllEdges();
                uiAdapter.selectEdges(((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayPath(id1, id2));

                uiAdapter.appendToOutput("\n");
                uiAdapter.appendToOutput("Delaunay Distance: " + delaunayDistance + "\n");
                uiAdapter.appendToOutput("Straight Distance: " + straightDistance + "\n");
                uiAdapter.appendToOutput("Distance Ratio: " + distanceRatio + "\n");
            }
            uiAdapter.draw();
        } else if (uiAdapter.getSelectedVertexes().size() == 2) {
            uiAdapter.deselectAllEdges();
            uiAdapter.deselectAllVertexes();
            uiAdapter.selectVertex(id);
            uiAdapter.draw();
        }
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void edgeOnMousePressed(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void angleOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void angleOnMousePressed(MouseEvent t) {

    }

    @Override
    public void angleOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void angleOnMouseDragged(MouseEvent t) {

    }
}
