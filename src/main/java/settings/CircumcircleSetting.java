package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Enables or disables the display of circumcircles in a Delaunay Triangulation.
 */
public class CircumcircleSetting extends DelaunayTriangulationSetting {

    /**
     * Constructs a new {@link CircumcircleSetting}, enables or disables the display of circumcircles in the given
     * {@link DelaunayTriangulationUiAdapter}
     * @param adapter The adapter which this setting modifies.
     */
    public CircumcircleSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Circumcircles");
        checkBox.setSelected(dt.isCircumcirclesVisible());
        checkBox.setOnMouseClicked(t -> dt.setCircumcirclesVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }
}
