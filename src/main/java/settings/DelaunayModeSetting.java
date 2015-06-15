package settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.DelaunayTriangulation;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Sets the triangulation mode used, which effects the that are added in a non-unique Delaunay triangulation.
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
            radioButton.setOnAction(event -> dt.setMode(mode));
            radioButton.setSelected(dt.getMode() == mode);
            radioButton.setToggleGroup(toggleGroup);
            hBox.getChildren().add(radioButton);
        }

        root.getChildren().add(hBox);
    }
}
