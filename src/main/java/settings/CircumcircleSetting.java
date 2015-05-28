package settings;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import uiAdapters.DelaunayTriangulationUiAdapter;

/**
 * Created by joshheinrichs on 15-05-27.
 */
public class CircumcircleSetting extends DelaunayTriangulationSetting {

    public CircumcircleSetting(DelaunayTriangulationUiAdapter adapter) {
        super(adapter);

        root = new Group();
        CheckBox checkBox = new CheckBox("Circumcircles");
        checkBox.setSelected(dt.isCircumcirclesVisible());
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                dt.setCircumcirclesVisible(((CheckBox) t.getSource()).isSelected());
            }
        });

        root.getChildren().add(checkBox);
    }
}
