package tools;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import ui.IndexedCircle;

/**
 * The add tool adds points
 */
public class AddVertexTool extends Tool {

    MoveVertexTool moveTool = new MoveVertexTool(modelAdapter);

    public AddVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Add Vertex";
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
        modelAdapter.addVertex(t.getX(), t.getY());
        modelAdapter.draw();
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
        if(t.getButton() == MouseButton.PRIMARY) {
            moveTool.vertexOnMousePressed(t);
        } else if (t.getButton() == MouseButton.SECONDARY) {
            int index = ((IndexedCircle) t.getSource()).getIndex();
            modelAdapter.removeVertex(index);
            modelAdapter.draw();
        }
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
        moveTool.vertexOnMouseDragged(t);
    }


}
