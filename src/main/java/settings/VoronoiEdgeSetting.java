package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class VoronoiEdgeSetting extends DelaunayTriangulationSetting {

    public VoronoiEdgeSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Voronoi Edges");
        checkBox.setSelected(dt.isVoronoiEdgesVisible());
        checkBox.setOnMouseClicked(t -> dt.setVoronoiEdgesVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }
}
