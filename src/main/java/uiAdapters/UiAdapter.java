package uiAdapters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import geometry.Point;
import graph.Vertex;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import models.Model;
import org.apache.commons.io.FilenameUtils;
import settings.Setting;
import tools.Tool;

import java.io.*;
import java.util.ArrayList;

/**
 * A {@link UiAdapter} provides an interface between a {@link Model} and the UI. The {@link UiAdapter} handles
 * {@link Tool} interactions with the model, as well as how the {@link Model} is displayed inside the window.
 */
public abstract class UiAdapter {

    Model model;

    ArrayList<Tool> tools = new ArrayList<Tool>();
    Tool selectedTool;

    ArrayList<Setting> settings = new ArrayList<Setting>();

    final Group root = new Group();

    ArrayList<Integer> selectedVertexes = new ArrayList<Integer>();

    EventHandler<MouseEvent> onMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    selectedTool.onMousePressed(t);
                }
            };

    EventHandler<MouseEvent> onMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    selectedTool.onMouseDragged(t);
                }
            };

    EventHandler<MouseEvent> backgroundOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    selectedTool.backgroundOnMouseClicked(t);
                }
            };

    EventHandler<MouseEvent> backgroundOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    selectedTool.backgroundOnMousePressed(t);
                }
            };

    EventHandler<MouseEvent> backgroundOnMouseReleasedEventHandler =
            new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    selectedTool.backgroundOnMouseReleased(t);
                }
            };

    EventHandler<MouseEvent> backgroundOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    selectedTool.backgroundOnMouseDragged(t);
                }
            };

    EventHandler<MouseEvent> vertexOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    selectedTool.vertexOnMousePressed(t);
                }
            };

    EventHandler<MouseEvent> vertexOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    selectedTool.vertexOnMouseDragged(t);
                }
            };

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
        deselectAllVertexes();
        selectedTool = tools.get(i);
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void setCameraPosition(Point position) {
        this.root.setTranslateX(position.x);
        this.root.setTranslateY(position.y);
    }

    public Point getCameraPosition() {
        return new Point(this.root.getTranslateX(), this.root.getTranslateY());
    }

    public void setCameraZoom(double zoom) {
        this.root.setScaleX(zoom);
        this.root.setScaleY(zoom);
    }

    public double getCameraZoom() {
        return this.root.getScaleX();
    }

    /**
     * Returns the name of the model. This is used as the title for the window it appears in.
     * @return
     */
    public abstract String getName();

    public void addVertex(double x, double y) {
        model.addVertex(new Point(x, y));
    }

    public void removeVertex(int index) {
        model.removeVertex(index);
    }

    public void moveVertex(int index, double x, double y) {
        model.moveVertex(index, new Point(x, y));
    }

    public void clearVertexes() {
        model.clearVertexes();
    }

    /**
     * Adjusts the selected vertexes positions by the given x and y values.
     * @param x
     * @param y
     */
    public void moveSelectedVertexes(double x, double y) {
        model.moveVertexes(selectedVertexes, x, y);
    }

    public void removeSelectedVertexes() {
        model.removeVertexes(selectedVertexes);
        selectedVertexes.clear();
    }

    public ArrayList<Integer> getVertexes(double startX, double startY, double endX, double endY) {
        return model.getVertexes(startX, startY, endX, endY);
    }

    public ArrayList<Integer> getSelectedVertexes() {
        return selectedVertexes;
    }

    public void selectVertex(int index) {
        if (!isSelected(index)) {
            selectedVertexes.add(index);
        }
    }

    public void selectVertexes(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            selectVertex(index);
        }
    }

    public void selectAllVertexes() {
        selectedVertexes.clear();
        for (int i = 0; i < model.getVertexes().size(); i++) {
            selectedVertexes.add(i);
        }
    }

    public void deselectVertex(int index) {
        selectedVertexes.remove(new Integer(index));
    }

    public void deselectVertexes(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            deselectVertex(index);
        }
    }

    public void deselectAllVertexes() {
        selectedVertexes.clear();
    }

    /**
     * Returns true if the vertex at the given index is selected, false otherwise
     * @param index Index of the vertex
     * @return
     */
    public boolean isSelected(int index) {
        return selectedVertexes.contains(new Integer(index));
    }

    public void loadVertexes(File file) throws FileNotFoundException {
        clearVertexes();
        JsonObject jsonObject = new JsonObject();
        file.getAbsolutePath();
        JsonReader jsonReader = new JsonReader(new FileReader(file.getPath()));
        Gson gson = new Gson();
        Point[] points = gson.fromJson(jsonReader, Point[].class);
        for (Point point : points) {
            model.addVertex(point);
        }
        draw();
    }

    public void saveVertexes(File file) {
        try {
            if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
                file = new File(file.toString() + ".json");
            }
            Writer writer = new FileWriter(file.getPath());
            Gson gson = new Gson();
            ArrayList<Vertex> vertexes = model.getVertexes();
            ArrayList<Point> points = new ArrayList<Point>(vertexes.size());
            for (Vertex vertex : vertexes) {
                points.add(vertex.getPoint());
            }
            gson.toJson(points, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void draw();

    public Group getRoot() {
        return root;
    }
}
