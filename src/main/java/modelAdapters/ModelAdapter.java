package modelAdapters;

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
 * A {@link ModelAdapter} provides an interface between a {@link Model} and the UI. The {@link ModelAdapter} handles
 * {@link Tool} interactions with the model, as well as how the {@link Model} is displayed inside the window.
 */
public abstract class ModelAdapter {

    Model model;

    ArrayList<Tool> tools = new ArrayList<Tool>();
    Tool selectedTool;

    ArrayList<Setting> settings = new ArrayList<Setting>();

    final Group root = new Group();

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

    public abstract void addVertex(double x, double y);
    public abstract void removeVertex(int index);
    public abstract void moveVertex(int index, double x, double y);

    public void clearVertexes() {
        model.clearVertexes();
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
