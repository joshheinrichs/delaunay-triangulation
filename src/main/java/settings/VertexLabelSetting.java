package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Enables or disables the display of Vertex labels in the graph.
 */
public class VertexLabelSetting extends DelaunayTriangulationSetting {

    /**
     * Constructs a new {@link VertexLabelSetting}, which can enable or disable the display of vertex labels in the
     * adapter.
     * @param adapter The adapter which this setting modifies.
     */
    public VertexLabelSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Vertex Labels");
        checkBox.setSelected(dt.isVertexLabelsVisible());
        checkBox.setOnMouseClicked(t -> dt.setVertexLabelsVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }


}
