package tools;

import geometry.Point;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class PanTool extends Tool {

    double startX, startY;

    public PanTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Pan";
    }

    @Override
    public void onMouseClicked(MouseEvent t) {

    }

    @Override
    public void onMousePressed(MouseEvent t) {
        startX = t.getScreenX() - modelAdapter.getCameraPosition().x;
        startY = t.getScreenY() - modelAdapter.getCameraPosition().y;
    }

    @Override
    public void onMouseReleased(MouseEvent t) {

    }

    @Override
    public void onMouseDragged(MouseEvent t) {
        System.out.println(t.getScreenX());
        modelAdapter.setCameraPosition(new Point(t.getScreenX() - startX, t.getScreenY() - startY));
    }

    @Override
    public void backgroundOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) { }

    @Override
    public void backgroundOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) { }

    @Override
    public void vertexOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) { }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) { }
}
