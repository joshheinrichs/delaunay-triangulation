package ui;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import modelAdapters.ModelAdapter;
import tools.Tool;

import java.util.ArrayList;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class ToolsWindow {

    public static final int WIDTH = 75;
    public static final int HEIGHT = 600;

    ModelAdapter modelAdapter;

    public ToolsWindow(ModelAdapter model) {

        this.modelAdapter = model;

        ToolBar toolBar = new ToolBar();

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

        toolBar.setOrientation(Orientation.VERTICAL);

        Stage toolStage = new Stage();
        Scene toolScene = new Scene(new Group(toolBar), WIDTH, HEIGHT);
        toolStage.setScene(toolScene);
        toolStage.setTitle("Tools");
        toolStage.setX(0);
        toolStage.setY(0);

        toolStage.show();
    }
}
