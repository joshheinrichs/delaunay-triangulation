package tools;

import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class PanTool extends Tool {

    public PanTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Pan";
    }

    @Override
    public void sceneOnMousePressed(MouseEvent t) {

    }

    @Override
    public void sceneOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }
}
