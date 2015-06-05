package tools;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public abstract class Tool {

    final UiAdapter uiAdapter;

    final Group graphRoot = new Group();
    final Group toolBarRoot = new Group();

    public Tool(UiAdapter adapter) {
        this.uiAdapter = adapter;

        final Tool thisTool = this;
        Button button = new Button(this.getName());
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                uiAdapter.setSelectedTool(thisTool);
            }
        });

        toolBarRoot.getChildren().add(button);
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
    
    public Group getGraphRoot() {
        return graphRoot;
    }
    
    public Group getToolBarRoot() {
        return toolBarRoot;
    }
}
