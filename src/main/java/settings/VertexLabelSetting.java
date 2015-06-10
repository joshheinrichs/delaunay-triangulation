package settings;

import javafx.scene.control.CheckBox;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-28.
 */
public class VertexLabelSetting extends DelaunayTriangulationSetting {

    public VertexLabelSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Vertex Labels");
        checkBox.setSelected(dt.isVertexLabelsVisible());
        checkBox.setOnMouseClicked(t -> dt.setVertexLabelsVisible(((CheckBox) t.getSource()).isSelected()));

        root.getChildren().add(checkBox);
    }


}
