package settings;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.DelaunayTriangulation;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-06-08.
 */
public class DelaunayModeSetting extends DelaunayTriangulationSetting {

    public DelaunayModeSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        HBox hBox = new HBox(5);
        final ToggleGroup toggleGroup = new ToggleGroup();
        hBox.setPadding(new Insets(5));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(new Text("Triangulation Mode:"));


        for (final DelaunayTriangulation.Mode mode : DelaunayTriangulation.Mode.values()) {
            RadioButton radioButton = new RadioButton(mode.toString());
            radioButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                public void handle(javafx.event.ActionEvent event) {
                    dt.setMode(mode);
                }
            });
            radioButton.setSelected(dt.getMode() == mode);
            radioButton.setToggleGroup(toggleGroup);
            hBox.getChildren().add(radioButton);
        }

        root.getChildren().add(hBox);
    }
}
