package ui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import settings.Setting;
import tools.Tool;
import uiAdapters.DelaunayTriangulationUiAdapter;

import java.io.File;
import java.io.FileNotFoundException;

public class App extends Application {

    DelaunayTriangulationUiAdapter modelAdapter = new DelaunayTriangulationUiAdapter(this);

    Group root = new Group();

    static final double DEFAULT_STAGE_HEIGHT = 800.d;
    static final double DEFAULT_STAGE_WIDTH = DEFAULT_STAGE_HEIGHT * (16.d/10.d);

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

        save.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            File file1 = fileChooser.showSaveDialog(stage);
            modelAdapter.saveVertexes(file1);
        });
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));

        open.setOnAction(event -> {
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
        });
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));

        userManual.setOnAction(event -> getHostServices().showDocument(USER_MANUAL_LINK));
        github.setOnAction(event -> getHostServices().showDocument(GITHUB_LINK));

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, modelAdapter.getEditMenu(), help);
        menuBar.setUseSystemMenuBar(true);
        menuBar.setPrefWidth(scene.getWidth());
        menuBar.setMaxWidth(Double.MAX_VALUE);

        ToolBar toolBar = new ToolBar();
        toolBar.setPrefWidth(scene.getWidth());
        toolBar.setMaxWidth(Double.MAX_VALUE);
        for (Tool tool : modelAdapter.getTools()) {
            toolBar.getItems().add(tool.getToolBarRoot());
        }

        ToolBar optionsBar = new ToolBar();
        optionsBar.setPrefWidth(scene.getWidth());
        toolBar.setMaxWidth(Double.MAX_VALUE);
        for (Setting setting : modelAdapter.getSettings()) {
            optionsBar.getItems().add(setting.getRoot());
        }

        final VBox barBox = new VBox();
        barBox.getChildren().addAll(menuBar, toolBar, optionsBar);


        console.setEditable(false);
        console.textProperty().addListener((observable, oldValue, newValue) -> {
            console.setScrollTop(Double.MAX_VALUE);
        });

        SplitPane splitPane = new SplitPane();
        StackPane stackPanePlane = new StackPane(modelAdapter.getRoot());
        stackPanePlane.alignmentProperty().setValue(Pos.TOP_LEFT);
        stackPanePlane.setMinHeight(0.d);
        stackPanePlane.setMinWidth(0.d);
        stackPanePlane.setPrefWidth(DEFAULT_STAGE_WIDTH);

        StackPane stackPaneConsole = new StackPane(console);
        stackPaneConsole.setMinHeight(0.d);
        splitPane.getItems().addAll(stackPanePlane, stackPaneConsole);

        splitPane.setDividerPositions(0.8f);
        splitPane.setOrientation(Orientation.VERTICAL);

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(DEFAULT_STAGE_HEIGHT);
        borderPane.setTop(barBox);
        borderPane.setCenter(splitPane);

        root.getChildren().addAll(borderPane);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            barBox.setMaxWidth(newValue.doubleValue());
            borderPane.setPrefWidth(newValue.doubleValue());
            stackPanePlane.setPrefWidth(newValue.doubleValue());
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            borderPane.setPrefHeight(newValue.doubleValue());
        });

        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

}