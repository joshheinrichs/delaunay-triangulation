package settings;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import modelAdapters.DelaunayTriangulationAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class VoronoiEdgeSetting extends DelaunayTriangulationSetting {

    public VoronoiEdgeSetting(DelaunayTriangulationAdapter adapter) {
        super(adapter);

        root = new Group();
        CheckBox checkBox = new CheckBox("Voronoi Edges");
        checkBox.setSelected(dt.isVoronoiEdgesVisible());
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                dt.setVoronoiEdgesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        root.getChildren().add(checkBox);
    }
}
