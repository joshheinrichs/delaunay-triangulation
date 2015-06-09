package settings;

import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class DelaunayEdgeSetting extends DelaunayTriangulationSetting {

    public DelaunayEdgeSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        CheckBox checkBox = new CheckBox("Delaunay Edges");
        checkBox.setSelected(dt.isDelaunayEdgesVisible());
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                dt.setDelaunayEdgesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        root.getChildren().add(checkBox);
    }
}
