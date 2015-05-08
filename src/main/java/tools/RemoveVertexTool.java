package tools;

import graph.Vertex;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;

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
    public void sceneOnMousePressed(MouseEvent t) {

    }

    @Override
    public void sceneOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
//        modelAdapter.removeVertex((Vertex) t.getSource());
    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }
}
