package ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import settings.Setting;
import tools.Tool;
import uiAdapters.DelaunayTriangulationUiAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class App extends Application {

    DelaunayTriangulationUiAdapter modelAdapter = new DelaunayTriangulationUiAdapter(this);

    Group root = new Group();

    static final double DEFAULT_STAGE_HEIGHT = 800.d;
    static final double DEFAULT_STAGE_WIDTH = DEFAULT_STAGE_HEIGHT * (16.d/9.d);

    static final double DEFAULT_CONSOLE_HEIGHT = 200.d;
    static final double DEFAULT_CONSOLE_WIDTH = DEFAULT_STAGE_WIDTH;

    static final String USER_MANUAL_LINK = "https://github.com/Decateron/delaunay-triangulation/wiki/User-Manual";
    static final String GITHUB_LINK = "https://github.com/Decateron/delaunay-triangulation";

    public final TextArea console = new TextArea();

    @Override
    public void start(final Stage stage) {
        Scene scene = new Scene(root, DEFAULT_STAGE_WIDTH, DEFAULT_STAGE_HEIGHT);

        stage.setScene(scene);
        stage.setTitle(modelAdapter.getName());

        Menu file = new Menu("File");
        Menu help = new Menu("Help");

        final MenuItem save = new MenuItem("Save");
        final MenuItem open = new MenuItem("Open...");
        final MenuItem userManual = new MenuItem("User Manual");
        final MenuItem github = new MenuItem("GitHub Repository");

        file.getItems().addAll(save, open);
        help.getItems().addAll(userManual, github);

        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File file = fileChooser.showSaveDialog(stage);
                modelAdapter.saveVertexes(file);
            }
        });
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));

        open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open File");
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
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));

        userManual.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                getHostServices().showDocument(USER_MANUAL_LINK);
            }
        });

        github.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                getHostServices().showDocument(GITHUB_LINK);
            }
        });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, modelAdapter.getEditMenu(), help);
        menuBar.setUseSystemMenuBar(true);

        root.getChildren().add(menuBar);

        ToolBar toolBar = new ToolBar();
        toolBar.setPrefWidth(scene.getWidth());
        toolBar.setMaxWidth(Double.MAX_VALUE);

        ArrayList<Tool> tools = modelAdapter.getTools();
        for (Tool tool : tools) {
            toolBar.getItems().add(tool.getToolBarRoot());
        }


        ToolBar optionsBar = new ToolBar();
        optionsBar.setPrefWidth(scene.getWidth());

        for (Setting setting : modelAdapter.getSettings()) {
            optionsBar.getItems().add(setting.getRoot());
        }

        final VBox vBox = new VBox();
        vBox.getChildren().addAll(toolBar, optionsBar);

        console.setPrefWidth(DEFAULT_CONSOLE_WIDTH);
        console.setPrefHeight(DEFAULT_CONSOLE_HEIGHT);
        console.setTranslateY(DEFAULT_STAGE_HEIGHT - DEFAULT_CONSOLE_HEIGHT);
        console.setEditable(false);

        console.setText(modelAdapter.getOutput());

        root.getChildren().addAll(modelAdapter.getRoot(), console, vBox);

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                vBox.setPrefWidth(newValue.doubleValue());
                console.setPrefWidth(newValue.doubleValue());
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                console.setTranslateY(newValue.doubleValue() - console.getPrefHeight());
            }
        });

        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

}