package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class DelaunayEdgeSetting extends DelaunayTriangulationSetting {

    public DelaunayEdgeSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Delaunay Edges");
        checkBox.setSelected(dt.isDelaunayEdgesVisible());
        checkBox.setOnMouseClicked(t -> dt.setDelaunayEdgesVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }
}
