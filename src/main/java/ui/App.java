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
import settings.Setting;
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

        for (Setting setting : modelAdapter.getSettings()) {
            optionsBar.getItems().add(setting.getRoot());
        }

        optionsBar.setTranslateY(38);


        root.getChildren().addAll(modelAdapter.getRoot(), toolBar, optionsBar);
        modelAdapter.draw();
    }

    public static void main(String[] args) {
        launch(args);
    }

}