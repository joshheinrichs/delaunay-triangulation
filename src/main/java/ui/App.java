package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelAdapters.DelaunayTriangulationAdapter;
import tools.Tool;

import java.awt.*;
import java.util.ArrayList;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class App extends Application {

    DelaunayTriangulationAdapter modelAdapter = new DelaunayTriangulationAdapter();

    Group root = new Group();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, 600/9*16, 600);

        stage.setScene(scene);
        stage.setTitle(modelAdapter.getName());
        stage.show();

        ToolBar toolBar = new ToolBar();
        toolBar.setPrefWidth(600 / 9 * 16);
        toolBar.setMaxWidth(Double.MAX_VALUE);

        ArrayList<Tool> tools = modelAdapter.getTools();
        for (int i = 0; i < tools.size(); i++) {
            Button button = new Button(tools.get(i).getName());
            final int tool = i;
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent t) {
                    modelAdapter.setSelectedTool(tool);
                }
            });
            toolBar.getItems().add(button);
        }

        ToolBar optionsBar = new ToolBar();
        optionsBar.setPrefWidth(600 / 9 * 16);

        CheckBox delaunayEdges = new CheckBox("Delaunay Edges");
        delaunayEdges.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                modelAdapter.setDelaunayEdgesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });
        CheckBox voronoiEdges = new CheckBox("Voronoi Edges");
        voronoiEdges.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                modelAdapter.setVoronoiEdgesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });
        CheckBox circumcircles = new CheckBox("Circumcircles");
        circumcircles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                modelAdapter.setCircumcirclesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });
        CheckBox angles = new CheckBox("Delaunay Angles");
        angles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                modelAdapter.setDelaunayAnglesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        optionsBar.getItems().add(delaunayEdges);
        optionsBar.getItems().add(voronoiEdges);
        optionsBar.getItems().add(circumcircles);
        optionsBar.getItems().add(angles);
        optionsBar.getItems().add(new TextField("180"));
        optionsBar.getItems().add(new TextField("360"));

        optionsBar.setTranslateY(38);


        root.getChildren().add(modelAdapter.draw());
        root.getChildren().add(toolBar);
        root.getChildren().add(optionsBar);
    }

    public static void main(String[] args) {
        launch(args);
    }

}