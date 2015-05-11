package tools;

import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;

/**
 * The angle tool measures angles
 */
public class AngleTool extends Tool {

    public AngleTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Angle";
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
