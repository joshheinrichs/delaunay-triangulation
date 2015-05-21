package tools;

import geometry.Point;
import javafx.scene.input.MouseEvent;
import modelAdapters.DelaunayTriangulationAdapter;
import modelAdapters.ModelAdapter;
import models.Model;
import ui.IndexedCircle;

import java.util.ArrayList;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class MoveVertexTool extends Tool {

    int index;

    double selectStartX, selectStartY;
    double moveStartX, moveStartY;

    ArrayList<Integer> indexes = new ArrayList<Integer>();

    public MoveVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Move Vertex";
    }

    @Override
    public void onMousePressed(MouseEvent t) { }

    @Override
    public void onMouseDragged(MouseEvent t) { }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {
        selectStartX = t.getX();
        selectStartY = t.getY();
    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {
        System.out.println(selectStartX + " " + selectStartY + " " + t.getX() + " " + t.getY());
        ((DelaunayTriangulationAdapter) modelAdapter).selectVertexes(selectStartX, selectStartY, t.getX(), t.getY());
        modelAdapter.draw();
    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        index = ((IndexedCircle) t.getSource()).getIndex();
        if(!((DelaunayTriangulationAdapter) modelAdapter).isSelected(index)) {
            ((DelaunayTriangulationAdapter) modelAdapter).selectVertex(index);
        }
        moveStartX = t.getX();
        moveStartY = t.getY();

        modelAdapter.draw();
    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
        double moveX = t.getX() - moveStartX;
        double moveY = t.getY() - moveStartY;

        moveStartX = t.getX();
        moveStartY = t.getY();

        ((DelaunayTriangulationAdapter) modelAdapter).moveSelectedVertexes(moveX, moveY);

        modelAdapter.draw();
    }

}
