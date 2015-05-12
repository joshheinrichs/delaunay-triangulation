package tools;

import graph.Vertex;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;
import ui.IndexedCircle;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class RemoveVertexTool extends Tool {

    public RemoveVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Remove Vertex";
    }

    @Override
    public void onMousePressed(MouseEvent t) {

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
        int selectedVertexIndex = ((IndexedCircle) t.getSource()).getIndex();
        modelAdapter.removeVertex(selectedVertexIndex);
        modelAdapter.draw();
    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }
}
