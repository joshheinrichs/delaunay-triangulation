package ui;

import geometry.Point;
import geometry.Segment;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modelAdapters.DelaunayTriangulationAdapter;
import models.CompleteGraph;
import models.DelaunayTriangulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class App extends Application {

    DelaunayTriangulationAdapter modelAdapter = new DelaunayTriangulationAdapter();

    Group root = new Group();

    ToolsWindow toolsWindow;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, 600/9*16, 600);

        toolsWindow = new ToolsWindow(modelAdapter);

        stage.setScene(scene);
        stage.setX(ToolsWindow.WIDTH);
        stage.setY(0);
        stage.setTitle(modelAdapter.getName());
        stage.show();

        root.getChildren().add(modelAdapter.draw());
    }

    public static void main(String[] args) {
        launch(args);
    }

}