package tools;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;

import static javafx.scene.input.MouseButton.PRIMARY;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class ZoomTool extends Tool {

    public ZoomTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }


    public String getName() {
        return "Zoom";
    }

    @Override
    public void onMousePressed(MouseEvent t) {
        switch (t.getButton()) {
            case PRIMARY:
                modelAdapter.setCameraZoom(modelAdapter.getCameraZoom() * 2.0);
                break;
            case SECONDARY:
                modelAdapter.setCameraZoom(modelAdapter.getCameraZoom() * 0.5);
                break;
        }
        modelAdapter.draw();
    }

    @Override
    public void onMouseDragged(MouseEvent t) {

    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }
}
