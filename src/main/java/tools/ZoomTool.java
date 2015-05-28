package tools;

import javafx.scene.input.MouseEvent;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class ZoomTool extends Tool {

    public ZoomTool(UiAdapter uiAdapter) {
        super(uiAdapter);
    }


    public String getName() {
        return "Zoom";
    }

    @Override
    public void onMouseClicked(MouseEvent t) {

    }

    @Override
    public void onMousePressed(MouseEvent t) {
        switch (t.getButton()) {
            case PRIMARY:
                uiAdapter.setCameraZoom(uiAdapter.getCameraZoom() * 2.0);
                break;
            case SECONDARY:
                uiAdapter.setCameraZoom(uiAdapter.getCameraZoom() * 0.5);
                break;
        }
        uiAdapter.draw();
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

    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }
}
