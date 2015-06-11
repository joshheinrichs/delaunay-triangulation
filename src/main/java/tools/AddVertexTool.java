package tools;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import uiAdapters.UiAdapter;

/**
 * The add tool adds points
 */
public class AddVertexTool extends Tool {

    MoveVertexTool moveTool = new MoveVertexTool(uiAdapter);
    RemoveVertexTool removeTool = new RemoveVertexTool(uiAdapter);

    boolean moving = false;

    public AddVertexTool(UiAdapter uiAdapter) {
        super(uiAdapter);
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
        uiAdapter.addVertex(t.getX(), t.getY());
        uiAdapter.draw();
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
            this.moving = true;
        } else if (t.getButton() == MouseButton.SECONDARY) {
            uiAdapter.deselectAllVertexes();
            removeTool.vertexOnMousePressed(t);
            this.moving = false;
        }
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
        if (this.moving) {
            moveTool.vertexOnMouseDragged(t);
        }
    }

    @Override
    public void edgeOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void edgeOnMousePressed(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void angleOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void angleOnMousePressed(MouseEvent t) {

    }

    @Override
    public void angleOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void angleOnMouseDragged(MouseEvent t) {

    }


}
