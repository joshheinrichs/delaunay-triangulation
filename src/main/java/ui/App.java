package ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelAdapters.DelaunayTriangulationAdapter;
import settings.Setting;
import tools.Tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @web http://java-buddy.blogspot.com/
 */
public class App extends Application {

    DelaunayTriangulationAdapter modelAdapter = new DelaunayTriangulationAdapter();

    Group root = new Group();

    static final double DEFAULT_STAGE_HEIGHT = 600.d;
    static final double DEFAULT_STAGE_WIDTH = DEFAULT_STAGE_HEIGHT * (16.d/9.d);

    @Override
    public void start(final Stage stage) {
        Scene scene = new Scene(root, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);

        stage.setScene(scene);
        stage.setTitle(modelAdapter.getName());

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");

        file.getItems().addAll(save, load);

        MenuItem clear = new MenuItem("Clear");
        edit.getItems().addAll(clear);
        clear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                modelAdapter.clearVertexes();
                modelAdapter.draw();
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File file = fileChooser.showSaveDialog(stage);
                modelAdapter.saveVertexes(file);
            }
        });

        load.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load File");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (selectedFile != null) {
                    try {
                        modelAdapter.loadVertexes(selectedFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, edit, help);
        menuBar.setUseSystemMenuBar(true);

        root.getChildren().add(menuBar);

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

        final VBox vBox = new VBox();
        vBox.getChildren().addAll(toolBar, optionsBar);

        root.getChildren().addAll(modelAdapter.getRoot(), vBox);

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vBox.setPrefWidth(newValue.doubleValue());
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}