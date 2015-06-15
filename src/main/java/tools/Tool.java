package tools;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import uiAdapters.UiAdapter;

/**
 * A tool is a UI element which has an effect upon the model, for example by adding or moving vertexes.
 */
public abstract class Tool {

    final UiAdapter uiAdapter;

    final Group graphRoot = new Group();
    final Group toolBarRoot = new Group();

    /**
     * Constructs a new {@link Tool}
     * @param adapter The {@link UiAdapter} this {@link Tool} modifies.
     */
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

    /**
     * An action that occurs when the mouse clicks on anything.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void onMouseClicked(MouseEvent t);

    /**
     * An action that occurs when the mouse presses on anything.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void onMousePressed(MouseEvent t);

    /**
     * An action that occurs when the mouse releases off of anything.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void onMouseReleased(MouseEvent t);

    /**
     * An action that occurs when the mouse drags after pressing on anything.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void onMouseDragged(MouseEvent t);

    /**
     * An action that occurs when the mouse clicks on the background.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void backgroundOnMouseClicked(MouseEvent t);

    /**
     * An action that occurs when the mouse presses on the background.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void backgroundOnMousePressed(MouseEvent t);

    /**
     * An action that occurs when the mouse releases off of the background
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void backgroundOnMouseReleased(MouseEvent t);

    /**
     * An action that occurs when the mouse drags after pressing the background.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void backgroundOnMouseDragged(MouseEvent t);

    /**
     * An action that occurs when the mouse click on a vertex.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void vertexOnMouseClicked(MouseEvent t);

    /**
     * An action that occurs when the mouse presses on a vertex.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void vertexOnMousePressed(MouseEvent t);

    /**
     * An action that occurs when the mouse releases off of a vertex.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void vertexOnMouseReleased(MouseEvent t);

    /**
     * An action that occurs when the mouse drags after pressing on a vertex.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void vertexOnMouseDragged(MouseEvent t);

    /**
     * An action that occurs when the mouse clicks on an edge.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void edgeOnMouseClicked(MouseEvent t);

    /**
     * An action that occurs when the mouse presses on an edge.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void edgeOnMousePressed(MouseEvent t);

    /**
     * An action that occurs when the mouse releases off of an edge.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void edgeOnMouseReleased(MouseEvent t);

    /**
     * An action that occurs when the mouse drags after pressing on an edge.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void edgeOnMouseDragged(MouseEvent t);

    /**
     * An action that occurs when the mouse clicks on an angle.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void angleOnMouseClicked(MouseEvent t);

    /**
     * An action that occurs when the mouse presses on an angle.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void angleOnMousePressed(MouseEvent t);

    /**
     * An action that occurs when the mouse releases off of an angle.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void angleOnMouseReleased(MouseEvent t);

    /**
     * An action that occurs when the mouse drags after pressing on an angle.
     * @param t The corresponding {@link MouseEvent}
     */
    public abstract void angleOnMouseDragged(MouseEvent t);

    /**
     * Returns the tool's UI to be displayed on top of the graph.
     */
    public Group getGraphRoot() {
        return graphRoot;
    }

    /**
     * Returns the tool's UI to be displayed in the toolbar.
     */
    public Group getToolBarRoot() {
        return toolBarRoot;
    }
}
