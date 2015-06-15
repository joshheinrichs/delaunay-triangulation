package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Enables or disables the display of Voronoi edges in the graph.
 */
public class VoronoiEdgeSetting extends DelaunayTriangulationSetting {

    /**
     * Constructs a new {@link VoronoiEdgeSetting}, which can enable or disable the display of Voronoi edges in the
     * given adapter.
     * @param adapter The adapter which the setting modifies.
     */
    public VoronoiEdgeSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Voronoi Edges");
        checkBox.setSelected(dt.isVoronoiEdgesVisible());
        checkBox.setOnMouseClicked(t -> dt.setVoronoiEdgesVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }
}
