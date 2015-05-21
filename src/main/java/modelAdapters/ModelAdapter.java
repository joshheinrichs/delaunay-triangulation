package modelAdapters;

import geometry.Point;
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

    Point cameraPosition = new Point(0,0);
    double cameraZoom = 1.0;

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

    public void setCameraPosition(Point position) {
        this.cameraPosition = position;
    }

    public Point getCameraPosition() {
        return this.cameraPosition;
    }

    public void setCameraZoom(double zoom) {
        this.cameraZoom = zoom;
    }

    public double getCameraZoom() {
        return this.cameraZoom;
    }

    /**
     * Returns the name of the model. This is used as the title for the window it appears in.
     * @return
     */
    public abstract String getName();

    public abstract void addVertex(double x, double y);
    public abstract void removeVertex(int index);
    public abstract void moveVertex(int index, double x, double y);

    public abstract Group draw();
}
