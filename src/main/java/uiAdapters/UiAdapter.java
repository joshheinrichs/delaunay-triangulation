package uiAdapters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import geometry.Point;
import graph.Vertex;
import javafx.event.ActionEvent;
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

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

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

    Stack<String> undo = new Stack<String>();
    String state = "[]";
    Stack<String> redo = new Stack<String>();

    /** Used to mark whether or not the state should be overwritten */
    boolean tempState = false;

    Menu editMenu = new Menu("Edit");

    MenuItem undoMenuItem = new MenuItem("Undo");
    MenuItem redoMenuItem = new MenuItem("Redo");
    MenuItem clearMenuItem = new MenuItem("Clear");

    ArrayList<Integer> selectedVertexes = new ArrayList<Integer>();
    ArrayList<Integer> selectedEdges = new ArrayList<Integer>();

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

    public UiAdapter() {
        editMenu.getItems().addAll(undoMenuItem, redoMenuItem, clearMenuItem);

        undoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (canUndo()) {
                    undo();
                    draw();
                }
            }
        });
        undoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));

        redoMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (canRedo()) {
                    redo();
                    draw();
                }
            }
        });
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN));

        clearMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                clearVertexes();
                draw();
            }
        });
        redoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN));

        undoMenuItem.setDisable(true);
        redoMenuItem.setDisable(true);
    }

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
        deselectAllEdges();
        selectedTool = tools.get(i);
        draw();
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
        saveState();
    }

    public void removeVertex(int index) {
        model.removeVertex(index);
        saveState();
    }

    public void moveVertex(int index, double x, double y) {
        model.moveVertex(index, new Point(x, y));
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

    public ArrayList<Integer> getVertexes(double startX, double startY, double endX, double endY) {
        return model.getVertexes(startX, startY, endX, endY);
    }

    public ArrayList<Integer> getSelectedVertexes() {
        return selectedVertexes;
    }

    public void selectVertex(int index) {
        if (!isVertexSelected(index)) {
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
        tempState = false;
    }

    public void deselectVertex(int index) {
        selectedVertexes.remove(new Integer(index));
        tempState = false;
    }

    public void deselectVertexes(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            deselectVertex(index);
        }
        tempState = false;
    }

    public void deselectAllVertexes() {
        selectedVertexes.clear();
        tempState = false;
    }

    /**
     * Returns true if the vertex at the given index is selected, false otherwise
     * @param index Index of the vertex
     * @return
     */
    public boolean isVertexSelected(int index) {
        return selectedVertexes.contains(new Integer(index));
    }

    public ArrayList<Integer> getSelectedEdges() {
        return selectedEdges;
    }

    public void selectEdge(int index) {
        if (!isEdgeSelected(index)) {
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

    public void selectEdges(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            selectEdge(index);
        }
    }

    public void selectAllEdges() {
        selectedEdges.clear();
        for (int i = 0; i < model.getEdges().size(); i++) {
            selectedEdges.add(i);
        }
    }

    public void deselectEdge(int index) {
        selectedEdges.remove(new Integer(index));
    }

    public void deselectEdges(ArrayList<Integer> indexes) {
        for (Integer index : indexes) {
            deselectEdge(index);
        }
    }

    public void deselectAllEdges() {
        selectedEdges.clear();
    }

    public boolean isEdgeSelected(int index) {
        return selectedEdges.contains(new Integer(index));
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
        JsonObject jsonObject = new JsonObject();
        file.getAbsolutePath();
        JsonReader jsonReader = new JsonReader(new FileReader(file.getPath()));
        Gson gson = new Gson();
        Point[] points = gson.fromJson(jsonReader, Point[].class);
        ArrayList<Point> pointList = new ArrayList<Point>(Arrays.asList(points));
        model.addVertexes(pointList);
        draw();
        redoMenuItem.setDisable(!canRedo());
        undoMenuItem.setDisable(!canUndo());
    }

    public void saveVertexes(File file) {
        try {
            if (!FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {
                file = new File(file.getName() + ".json");
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

    String toJson() {
        ArrayList<Vertex> vertexes = model.getVertexes();
        ArrayList<Point> points = new ArrayList<Point>(vertexes.size());
        for (Vertex vertex : vertexes) {
            points.add(vertex.getPoint());
        }
        Gson gson = new Gson();
        return gson.toJson(points);
    }

    ArrayList<Point> fromJson(String json) {
        Gson gson = new Gson();
        Point[] points = gson.fromJson(json, Point[].class);
        return new ArrayList<Point>(Arrays.asList(points));
    }

    public abstract void draw();

    public Group getRoot() {
        return root;
    }

    public Menu getEditMenu() {
        return editMenu;
    }
}
