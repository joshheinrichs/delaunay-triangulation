package modelAdapters;

import javafx.scene.Group;
import models.Model;
import tools.Tool;

import java.util.ArrayList;

/**
 * A {@link ModelAdapter} provides an interface between a {@link Model} and the UI. The {@link ModelAdapter} handles
 * {@link Tool} interactions with the model, as well as how the {@link Model} is displayed inside the window.
 */
public abstract class ModelAdapter {

    Model model;

    ArrayList<Tool> tools = new ArrayList<Tool>();
    Tool selectedTool;

    public ArrayList<Tool> getTools() {
        return tools;
    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(int i) {
        selectedTool = tools.get(i);
    }

    /**
     * Returns the name of the model. This is used as the title for the window it appears in.
     * @return
     */
    public abstract String getName();

    public abstract void addVertex(double x, double y);
    public abstract void removeVertex(int i);
    public abstract void moveVertex(int i, double x, double y);

    public abstract Group draw();
    public abstract Group dragDraw();
}
