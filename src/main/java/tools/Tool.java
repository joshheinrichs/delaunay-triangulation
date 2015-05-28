package tools;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public abstract class Tool {

    public final Group root = new Group();

    UiAdapter uiAdapter;

    public Tool(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    /**
     * Returns the name of the tool.
     */
    public abstract String getName();

    public abstract void onMouseClicked(MouseEvent t);
    public abstract void onMousePressed(MouseEvent t);
    public abstract void onMouseReleased(MouseEvent t);
    public abstract void onMouseDragged(MouseEvent t);

    public abstract void backgroundOnMouseClicked(MouseEvent t);
    public abstract void backgroundOnMousePressed(MouseEvent t);
    public abstract void backgroundOnMouseReleased(MouseEvent t);
    public abstract void backgroundOnMouseDragged(MouseEvent t);

    public abstract void vertexOnMouseClicked(MouseEvent t);
    public abstract void vertexOnMousePressed(MouseEvent t);
    public abstract void vertexOnMouseReleased(MouseEvent t);
    public abstract void vertexOnMouseDragged(MouseEvent t);
}
