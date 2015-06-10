package uiAdapters;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import geometry.Point;
import graph.Vertex;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import models.Model;
import org.apache.commons.io.FilenameUtils;
import settings.Setting;
import tools.Tool;
import ui.App;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * A {@link UiAdapter} provides an interface between a {@link Model} and the UI. The {@link UiAdapter} handles
 * {@link Tool} interactions with the model, as well as how the {@link Model} is displayed inside the window.
 */
public abstract class UiAdapter {

    App app;

    Model model;

    ArrayList<Tool> tools = new ArrayList<>();
    Tool selectedTool;

    ArrayList<Setting> settings = new ArrayList<>();

    final Group root = new Group();

    Stack<String> undo = new Stack<>();
    String state = "[]";
    Stack<String> redo = new Stack<>();

    /** Used to mark whether or not the state should be overwritten */
    boolean tempState = false;

    Menu editMenu = new Menu("Edit");

    MenuItem undoMenuItem = new MenuItem("Undo");
    MenuItem redoMenuItem = new MenuItem("Redo");
    MenuItem clearMenuItem = new MenuItem("Clear");
    MenuItem resetCameraMenuItem = new MenuItem("Reset Camera");

    ArrayList<Integer> selectedVertexes = new ArrayList<>();
    ArrayList<Integer> selectedEdges = new ArrayList<>();

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

    EventHandler<MouseEvent> vertexOnMouseClickedEventHandler =
            new EventHandler<MouseEvent>() {

                public void handle(MouseEvent t) {
                    selectedTool.vertexOnMouseClicked(t);
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

    public UiAdapter(App app) {

        this.app = app;

        editMenu.getItems().addAll(undoMenuItem, redoMenuItem, clearMenuItem, resetCameraMenuItem);

        undoMenuItem.setOnAction(event -> {
            if (canUndo()) {
                undo();
                draw();
            }
        });
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));

        redoMenuItem.setOnAction(event -> {
            if (canRedo()) {
                redo();
                draw();
            }
        });
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN));

        clearMenuItem.setOnAction(event -> {
            clearVertexes();
            draw();
        });
        clearMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN));

        resetCameraMenuItem.setOnAction(event -> {
            setCameraPosition(new Point(0,0));
            setCameraZoom(1.0);
        });

        undoMenuItem.setDisable(true);
        redoMenuItem.setDisable(true);
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void addTool(Tool tool) {
        this.tools.add(tool);
        this.root.getChildren().add(tool.getGraphRoot());
    }

    public Tool getSelectedTool() {
        return this.selectedTool;
    }

    public void setSelectedTool(Tool tool) {
        deselectAllVertexes();
        deselectAllEdges();
        this.selectedTool = tool;
        draw();
    }

    public List<Setting> getSettings() {
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

    public Vertex getVertex(String id) {
        return model.getVertex(Integer.parseInt(id));
    }

    public void addVertex(double x, double y) {
        model.addVertex(new Point(x, y));
        saveState();
    }

    public void removeVertex(String id) {
        model.removeVertex(Integer.parseInt(id));
        saveState();
    }

    public void moveVertex(String id, double x, double y) {
        model.moveVertex(Integer.parseInt(id), new Point(x, y));
        saveTempState();
    }

    public void clearVertexes() {
        model.clearVertexes();
        saveState();
    }

    /**
     * Adjusts the selected vertexes positions by the given x and y values.
     * @param x
     * @param y
     */
    public void moveSelectedVertexes(double x, double y) {
        model.moveVertexes(selectedVertexes, x, y);
        saveTempState();
    }

    public void removeSelectedVertexes() {
        model.removeVertexes(selectedVertexes);
        selectedVertexes.clear();
        saveState();
    }

    public List<String> getVertexes(double startX, double startY, double endX, double endY) {
        return model.getVertexes(startX, startY, endX, endY).stream().map(Object::toString).collect(Collectors.toList());
    }

    public List<String> getSelectedVertexes() {
        return selectedVertexes.stream().map(Object::toString).collect(Collectors.toList());
    }

    public void selectVertex(String id) {
        if (!isVertexSelected(id)) {
            int index = Integer.parseInt(id);
            boolean inserted = false;
            for (int i = 0; i < selectedVertexes.size(); i++) {
                if (index < selectedVertexes.get(i)) {
                    selectedVertexes.add(i, index);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                selectedVertexes.add(index);
            }
            tempState = false;
        }
    }

    public void selectVertexes(List<String> ids) {
        ids.forEach(this::selectVertex);
    }

    public void selectAllVertexes() {
        selectedVertexes.clear();
        for (int i = 0; i < model.getVertexes().size(); i++) {
            selectedVertexes.add(i);
        }
        tempState = false;
    }

    public void deselectVertex(String id) {
        selectedVertexes.remove(new Integer(id));
        tempState = false;
    }

    public void deselectVertexes(List<String> ids) {
        ids.forEach(this::deselectVertex);
        tempState = false;
    }

    public void deselectAllVertexes() {
        selectedVertexes.clear();
        tempState = false;
    }

    /**
     * Returns true if the vertex with the given ID is selected, false otherwise
     * @param id ID of the vertex
     * @return
     */
    public boolean isVertexSelected(String id) {
        return selectedVertexes.contains(new Integer(id));
    }

    public List<String> getSelectedEdges() {
        return selectedEdges.stream().map(Object::toString).collect(Collectors.toList());
    }

    public void selectEdge(String id) {
        if (!isEdgeSelected(id)) {
            int index = Integer.parseInt(id);
            boolean inserted = false;
            for (int i = 0; i < selectedEdges.size(); i++) {
                if (index < selectedEdges.get(i)) {
                    selectedEdges.add(i, index);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                selectedEdges.add(index);
            }
        }
    }

    public void selectEdges(List<String> ids) {
        ids.forEach(this::selectEdge);
    }

    public void selectAllEdges() {
        selectedEdges.clear();
        for (int i = 0; i < model.getEdges().size(); i++) {
            selectedEdges.add(i);
        }
    }

    public void deselectEdge(String id) {
        selectedEdges.remove(new Integer(id));
    }

    public void deselectEdges(List<String> ids) {
        ids.forEach(this::deselectEdge);
    }

    public void deselectAllEdges() {
        selectedEdges.clear();
    }

    public boolean isEdgeSelected(String id) {
        return selectedEdges.contains(new Integer(id));
    }

    public String getOutput() {
        return this.app.console.getText();
    }

    public void setOutput(String string) {
        this.app.console.setText(string);
    }

    public void appendToOutput(String string) {
        this.app.console.appendText(string);
    }

    public void undo() {
        redo.push(state);
        state = undo.pop();
        model.clearVertexes();
        model.addVertexes(fromJson(state));
        redoMenuItem.setDisable(!canRedo());
        undoMenuItem.setDisable(!canUndo());
    }

    public void redo() {
        undo.push(state);
        state = redo.pop();
        model.clearVertexes();
        model.addVertexes(fromJson(state));
        redoMenuItem.setDisable(!canRedo());
        undoMenuItem.setDisable(!canUndo());
    }

    public boolean canUndo() {
        return !undo.isEmpty();
    }

    public boolean canRedo() {
        return !redo.isEmpty();
    }

    void saveState() {
        redo.clear();
        undo.push(state);
        state = toJson();
        redoMenuItem.setDisable(!canRedo());
        undoMenuItem.setDisable(!canUndo());
        tempState = false;
    }

    void saveTempState() {
        if (tempState) {
            state = toJson();
        } else {
            saveState();
            tempState = true;
        }
        redoMenuItem.setDisable(!canRedo());
        undoMenuItem.setDisable(!canUndo());
    }

    public void loadVertexes(File file) throws FileNotFoundException {
        model.clearVertexes();
        JsonReader jsonReader = new JsonReader(new FileReader(file.getPath()));
        Gson gson = new Gson();
        Point[] points = gson.fromJson(jsonReader, Point[].class);
        ArrayList<Point> pointList = new ArrayList<>(Arrays.asList(points));
        model.addVertexes(pointList);
        draw();
        redoMenuItem.setDisable(!canRedo());
        undoMenuItem.setDisable(!canUndo());
    }

    public void saveVertexes(File file) {
        try {
            if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
                file = new File(file.toString() + ".json");
            }
            Writer writer = new FileWriter(file.getPath());
            Gson gson = new Gson();
            List<Vertex> vertexes = model.getVertexes();
            List<Point> points = vertexes.stream().map(Vertex::getPoint).collect(Collectors.toList());
            gson.toJson(points, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String toJson() {
        List<Vertex> vertexes = model.getVertexes();
        List<Point> points = vertexes.stream().map(Vertex::getPoint).collect(Collectors.toList());
        Gson gson = new Gson();
        return gson.toJson(points);
    }

    ArrayList<Point> fromJson(String json) {
        Gson gson = new Gson();
        Point[] points = gson.fromJson(json, Point[].class);
        return new ArrayList<>(Arrays.asList(points));
    }

    public abstract void draw();

    public Group getRoot() {
        return root;
    }

    public Menu getEditMenu() {
        return editMenu;
    }
}
