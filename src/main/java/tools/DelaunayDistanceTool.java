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
        int index = Integer.parseInt(((Circle) t.getSource()).getId());

        if (uiAdapter.getSelectedVertexes().size() == 0) {
            uiAdapter.selectVertex(index);
            uiAdapter.draw();
        } else if (uiAdapter.getSelectedVertexes().size() == 1) {
            if (!uiAdapter.isVertexSelected(index)) {
                uiAdapter.selectVertex(index);

                int a = uiAdapter.getSelectedVertexes().get(0);
                int b = uiAdapter.getSelectedVertexes().get(1);

                double delaunayDistance = ((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayDistance(a, b);
                double straightDistance = ((DelaunayTriangulationUiAdapter) uiAdapter).getStraightDistance(a, b);
                double distanceRatio = delaunayDistance/straightDistance;

                uiAdapter.deselectAllEdges();
                uiAdapter.selectEdges(((DelaunayTriangulationUiAdapter) uiAdapter).getDelaunayPath(a, b));

                System.out.println("Delaunay Distance: " + delaunayDistance);
                System.out.println("Straight Distance: " + straightDistance);
                System.out.println("Distance Ratio: " + distanceRatio);
                System.out.println("");
                //TODO: Calculate distance
            }
            uiAdapter.draw();
        } else if (uiAdapter.getSelectedVertexes().size() == 2) {
            uiAdapter.deselectAllEdges();
            uiAdapter.deselectAllVertexes();
            uiAdapter.selectVertex(index);
            uiAdapter.draw();
        }
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }
}
