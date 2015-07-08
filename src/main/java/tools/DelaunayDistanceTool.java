package tools;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import uiAdapters.DelaunayTriangulationUiAdapter;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-29.
 */
public class DelaunayDistanceTool extends Tool {

    MoveVertexTool moveTool = new MoveVertexTool(uiAdapter);
    boolean moving;

    String idFrom, idTo;

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
        moving = false;
        if (t.getButton() == MouseButton.PRIMARY) {
            idFrom = idTo = null;

            String id = ((Circle) t.getSource()).getId();

            if (uiAdapter.getSelectedVertexes().size() == 0) {
                uiAdapter.deselectAllEdges();
                uiAdapter.selectVertex(id);
                uiAdapter.draw();
            } else if (uiAdapter.getSelectedVertexes().size() == 1) {
                if (!uiAdapter.isVertexSelected(id)) {
                    uiAdapter.selectVertex(id);

                    idFrom = uiAdapter.getSelectedVertexes().get(0);
                    idTo = uiAdapter.getSelectedVertexes().get(1);

                    uiAdapter.deselectAllEdges();
                    uiAdapter.selectEdges(((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayPath(idFrom, idTo));
                    uiAdapter.deselectAllVertexes();
                }
                uiAdapter.draw();
            }
        } else if (t.getButton() == MouseButton.SECONDARY) {
            uiAdapter.deselectAllVertexes();
            moveTool.vertexOnMousePressed(t);
            moving = true;
        }
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {
        if (idFrom != null && idTo != null) {
            double delaunayDistance = ((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayDistance(idFrom, idTo);
            double straightDistance = ((DelaunayTriangulationUiAdapter) uiAdapter).getStraightDistance(idFrom, idTo);
            double distanceRatio = delaunayDistance / straightDistance;

            uiAdapter.appendToOutput("\n");
            uiAdapter.appendToOutput("Delaunay Distance: " + delaunayDistance + "\n");
            uiAdapter.appendToOutput("Straight Distance: " + straightDistance + "\n");
            uiAdapter.appendToOutput("Distance Ratio: " + distanceRatio + "\n");
        }
    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
        if (moving) {
            moveTool.vertexOnMouseDragged(t);
            if (idFrom != null && idTo != null) {
                uiAdapter.deselectAllEdges();
                uiAdapter.selectEdges(((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayPath(idFrom, idTo));
            }
            uiAdapter.draw();
        }
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
