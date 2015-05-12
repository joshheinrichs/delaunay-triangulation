package tools;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public abstract class Tool {

    ModelAdapter modelAdapter;

    public Tool(ModelAdapter modelAdapter) {
        this.modelAdapter = modelAdapter;
    }

    /**
     * Returns the name of the tool.
     */
    public abstract String getName();

    public abstract void onMousePressed(MouseEvent t);
    public abstract void onMouseDragged(MouseEvent t);

    public abstract void backgroundOnMousePressed(MouseEvent t);
    public abstract void backgroundOnMouseDragged(MouseEvent t);

    public abstract void vertexOnMousePressed(MouseEvent t);
    public abstract void vertexOnMouseDragged(MouseEvent t);
}
