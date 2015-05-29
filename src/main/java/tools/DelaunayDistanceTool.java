package tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
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
            if (!uiAdapter.isSelected(index)) {
                uiAdapter.selectVertex(index);
                //TODO: Calculate distance
            }
            uiAdapter.draw();
        } else if (uiAdapter.getSelectedVertexes().size() == 2) {
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
