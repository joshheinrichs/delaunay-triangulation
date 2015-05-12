package tools;

import geometry.Point;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;

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
    public void onMousePressed(MouseEvent t) {
        startX = t.getScreenX() - modelAdapter.getCameraPosition().x;
        startY = t.getScreenY() - modelAdapter.getCameraPosition().y;
        modelAdapter.dragDraw();
    }

    @Override
    public void onMouseDragged(MouseEvent t) {
        System.out.println(t.getScreenX());
        modelAdapter.setCameraPosition(new Point(t.getScreenX() - startX, t.getScreenY() - startY));
        modelAdapter.dragDraw();
    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) { }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) { }

    @Override
    public void vertexOnMousePressed(MouseEvent t) { }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) { }
}
