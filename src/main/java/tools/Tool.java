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

    public abstract void sceneOnMousePressed(MouseEvent t);
    public abstract void sceneOnMouseDragged(MouseEvent t);

    public abstract void vertexOnMousePressed(MouseEvent t);
    public abstract void vertexOnMouseDragged(MouseEvent t);
}
