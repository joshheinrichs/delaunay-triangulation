package settings;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import modelAdapters.DelaunayTriangulationAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class DelaunayAngleSetting extends Setting {

    final DelaunayTriangulationAdapter dt;

    public DelaunayAngleSetting(DelaunayTriangulationAdapter adapter) {
        this.dt = adapter;

        root = new Group();
        CheckBox checkBox = new CheckBox("Angles");
        checkBox.setSelected(dt.isDelaunayAnglesVisible());
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                dt.setDelaunayAnglesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        root.getChildren().add(checkBox);
    }
}
