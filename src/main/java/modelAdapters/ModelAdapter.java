package modelAdapters;

import javafx.scene.Group;
import tools.Tool;

import java.util.ArrayList;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public abstract class ModelAdapter {

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

    public abstract String getName();

    public abstract void addVertex(double x, double y);
    public abstract void removeVertex(int i);
    public abstract void moveVertex(int i, double x, double y);

    public abstract Group draw();
    public abstract Group dragDraw();
}
